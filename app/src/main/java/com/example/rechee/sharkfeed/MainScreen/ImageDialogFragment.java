package com.example.rechee.sharkfeed.MainScreen;

import android.Manifest;
import android.app.Dialog;
import android.app.DialogFragment;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.rechee.sharkfeed.Error;
import com.example.rechee.sharkfeed.R;
import com.example.rechee.sharkfeed.SharkFeedApplication;
import com.example.rechee.sharkfeed.ViewModelFactory;
import com.example.rechee.sharkfeed.dagger.activity.ViewModelModule;
import com.example.rechee.sharkfeed.dagger.viewmodel.RepositoryModule;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.io.File;

import javax.inject.Inject;

/**
 * Created by Rechee on 1/27/2018.
 */

public class ImageDialogFragment extends DialogFragment {

    public static final String IMAGE_URL_DOWNLOAD = "download_image_url";
    public static final String IMAGE_URL_MEDIUM = "c_image_url";
    private String originalImageUrl;
    private String mediumImageUrl;

    @Inject
    ViewModelFactory viewModelFactory;

    private ImageView backgroundImage;
    private ImageButton downloadImageButton;
    private MainViewModel viewModel;
    private ProgressBar progressBar;
    private MainActivity mainActivity;
    private File storageDir;

    public static ImageDialogFragment newInstance(String downloadImageUrl, String mediumImageUrl){
        ImageDialogFragment fragment = new ImageDialogFragment();

        Bundle args = new Bundle();

        //the image that we want to download
        args.putString(IMAGE_URL_DOWNLOAD, downloadImageUrl);
        args.putString(IMAGE_URL_MEDIUM, mediumImageUrl);
        fragment.setArguments(args);

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.layout_image_dialog, container, false);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mainActivity = (MainActivity) getActivity();

        SharkFeedApplication.getApplicationComponent(mainActivity)
                .plus(new RepositoryModule(mainActivity.getApplicationContext()))
                .plus(new ViewModelModule())
                .inject(this);

        viewModel = ViewModelProviders.of(mainActivity).get(MainViewModel.class);
        viewModel.getError().observe(mainActivity, new Observer<Error>() {
            @Override
            public void onChanged(@Nullable Error error) {
                progressBar.setVisibility(View.GONE);
                ErrorHandler.getErrorToast(mainActivity, error).show();
            }
        });

        viewModel.downloadFinished().observe(mainActivity, new Observer<Void>() {
            @Override
            public void onChanged(@Nullable Void aVoid) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(mainActivity, R.string.download_finished, Toast.LENGTH_SHORT).show();
            }
        });

        downloadImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(originalImageUrl == null){
                    Toast.makeText(view.getContext(), R.string.cannot_download, Toast.LENGTH_SHORT)
                            .show();
                }
                else{
                    storageDir = new File(Environment.getExternalStoragePublicDirectory(
                            Environment.DIRECTORY_PICTURES), "SharkFeed");

                    progressBar.setVisibility(View.VISIBLE);

                    // Here, thisActivity is the current activity
                    if (ContextCompat.checkSelfPermission(mainActivity,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE)
                            == PackageManager.PERMISSION_GRANTED) {

                        viewModel.downloadPhoto(originalImageUrl, storageDir);
                    }
                    else{
                        // Should we show an explanation?
                        if (ActivityCompat.shouldShowRequestPermissionRationale(mainActivity,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                            Toast.makeText(mainActivity, R.string.permission_write_storage_explanation,
                                    Toast.LENGTH_LONG).show();

                            ActivityCompat.requestPermissions(mainActivity,
                                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                    MainActivity.WRITE_EXTERNAL_STORAGE_RESULT);
                        } else {

                            ActivityCompat.requestPermissions(mainActivity,
                                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                    MainActivity.WRITE_EXTERNAL_STORAGE_RESULT);
                        }
                    }
                }
            }
        });
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        backgroundImage = view.findViewById(R.id.imageView_shark_background);
        downloadImageButton = view.findViewById(R.id.button_download_image);
        progressBar = view.findViewById(R.id.progress_bar);

        Bundle args = getArguments();

        if(args != null){
            originalImageUrl = args.getString(IMAGE_URL_DOWNLOAD);
            mediumImageUrl = args.getString(IMAGE_URL_MEDIUM);

            progressBar.setVisibility(View.VISIBLE);
            Picasso.with(view.getContext()).load(mediumImageUrl)
                    .centerCrop()
                    .fit()
                    .into(backgroundImage, new Callback() {
                        @Override
                        public void onSuccess() {
                            progressBar.setVisibility(View.GONE);
                        }

                        @Override
                        public void onError() {
                            ErrorHandler.getErrorToast(mainActivity, Error.LOAD_IMAGE_FAILED).show();
                        }
                    });
        }
    }

    public void writePermissionAccepted() {
        viewModel.downloadPhoto(originalImageUrl, storageDir);
    }
}
