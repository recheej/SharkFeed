package com.example.rechee.sharkfeed.MainScreen;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.rechee.sharkfeed.Error;
import com.example.rechee.sharkfeed.R;
import com.example.rechee.sharkfeed.SharkFeedApplication;
import com.example.rechee.sharkfeed.ViewModelFactory;
import com.example.rechee.sharkfeed.dagger.activity.ViewModelModule;
import com.example.rechee.sharkfeed.dagger.viewmodel.RepositoryModule;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class MainActivity extends AppCompatActivity implements PhotoListAdapter.OnBottomReachedListener {
    public static final int WRITE_EXTERNAL_STORAGE_RESULT = 1;
    public static final int READ_EXTERNAL_STORAGE_RESULT = 2;

    @Inject
    ViewModelFactory viewModelFactory;
    private MainViewModel viewModel;
    private PhotoListAdapter photoListAdapter;
    private List<Photo> photos;
    private ProgressBar progressBar;
    private SwipeRefreshLayout refreshLayout;
    private ImageDialogFragment dialogFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        photos = new ArrayList<>();

        SharkFeedApplication.getApplicationComponent(this)
                .plus(new RepositoryModule(getApplicationContext()))
                .plus(new ViewModelModule())
                .inject(this);

        viewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(MainViewModel.class);
        viewModel.setPhotos(photos);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);
        final RecyclerView recyclerView = findViewById(R.id.recyclerView_sharks);
        recyclerView.setLayoutManager(gridLayoutManager);

        progressBar = findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.VISIBLE);

        refreshLayout = findViewById(R.id.layout_refresh);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                viewModel.updatePage(1);
            }
        });

        viewModel.getSearchResult().observe(this, new Observer<SearchResult>() {
            @Override
            public void onChanged(@Nullable SearchResult searchResult) {
                progressBar.setVisibility(View.GONE);
                refreshLayout.setRefreshing(false);

                if(searchResult != null){
                    List<Photo> newPhotos = searchResult.getPhotos().getPhoto();
                    photos.addAll(newPhotos);

                    if(photoListAdapter == null){
                        photoListAdapter = new PhotoListAdapter(photos,
                                Picasso.with(MainActivity.this), MainActivity.this);

                        recyclerView.setAdapter(photoListAdapter);
                    }
                    else{
                        photoListAdapter.notifyDataSetChanged();
                    }
                }
            }
        });

        viewModel.getError().observe(this, new Observer<Error>() {
            @Override
            public void onChanged(@Nullable Error error) {
                progressBar.setVisibility(View.GONE);
                refreshLayout.setRefreshing(false);

                if(error != null){
                    ErrorHandler.getErrorToast(MainActivity.this, error).show();
                }
            }
        });
    }

    @Override
    public void onBottomReached(int position) {
        progressBar.setVisibility(View.VISIBLE);
        viewModel.updatePage();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case MainActivity.WRITE_EXTERNAL_STORAGE_RESULT:
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    this.dialogFragment.writePermissionAccepted();
                }
            break;
        }
    }

    public void setDialogFragment(ImageDialogFragment dialogFragment) {
        this.dialogFragment = dialogFragment;
    }
}
