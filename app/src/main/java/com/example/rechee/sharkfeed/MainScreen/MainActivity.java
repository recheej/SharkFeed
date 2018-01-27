package com.example.rechee.sharkfeed.MainScreen;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
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
    @Inject
    ViewModelFactory viewModelFactory;
    private MainViewModel viewModel;
    private PhotoListAdapter photoListAdapter;
    private List<Photo> photos;
    private ProgressBar progressBar;
    private SwipeRefreshLayout refreshLayout;

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
                    int errorID;
                    switch (error){
                        case GENERIC_NETWORK_ERROR:
                            errorID = R.string.generic_network_error;
                            break;
                        default:
                            errorID = R.string.generic_error;
                    }

                    Toast.makeText(MainActivity.this,
                            errorID, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onBottomReached(int position) {
        progressBar.setVisibility(View.VISIBLE);
        viewModel.updatePage();
    }
}
