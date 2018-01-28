package com.example.rechee.sharkfeed.MainScreen;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.rechee.sharkfeed.R;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;

import java.util.List;

/**
 * Created by Rechee on 1/1/2018.
 */

public class PhotoListAdapter extends RecyclerView.Adapter<PhotoListAdapter.ViewHolder> {

    public static final String IMAGE_DIALOG_FRAGMENT = "ImageDialogFragment";
    private final List<Photo> photos;
    private final Picasso picasso;
    private OnBottomReachedListener onBottomReachedListener;

    public PhotoListAdapter(List<Photo> photos, Picasso picasso){
        this.photos = photos;
        this.picasso = picasso;
    }

    public PhotoListAdapter(List<Photo> photos, Picasso picasso,
                            OnBottomReachedListener onBottomReachedListener){
        this.photos = photos;
        this.picasso = picasso;
        this.onBottomReachedListener = onBottomReachedListener;
    }

    public void setOnBottomReachedListener(OnBottomReachedListener onBottomReachedListener){
        this.onBottomReachedListener = onBottomReachedListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView sharkImageView;
        public ViewHolder(View itemView) {
            super(itemView);

            sharkImageView = itemView.findViewById(R.id.imageView_shark);
            sharkImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if(position != RecyclerView.NO_POSITION){
                        Photo photo = photos.get(position);

                        FragmentManager manager = ((Activity) view.getContext()).getFragmentManager();

                        String downloadUrl = photo.getUrlO();
                        if(downloadUrl == null){
                            downloadUrl = photo.getUrlC();
                        }

                        ImageDialogFragment.newInstance(downloadUrl, photo.getUrlN())
                                .show(manager, IMAGE_DIALOG_FRAGMENT);
                    }
                }
            });
        }

        public void bindData(Photo photo){

            final RequestCreator request = picasso.load(photo.getUrlC());
            //for some reason the api returns widths as strings

            if(photo.getWidthC() != null){
                request.resize(300, 300);
            }

            request.centerCrop().into(sharkImageView);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(R.layout.layout_shark_photo, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        if(position == photos.size() - 1){
            if(this.onBottomReachedListener != null){
                this.onBottomReachedListener.onBottomReached(position);
            }
        }

        holder.bindData(photos.get(position));
    }

    @Override
    public int getItemCount() {
        return photos.size();
    }

    public interface OnBottomReachedListener {
        //https://medium.com/@ayhamorfali/android-detect-when-the-recyclerview-reaches-the-bottom-43f810430e1e
        void onBottomReached(int position);
    }
}
