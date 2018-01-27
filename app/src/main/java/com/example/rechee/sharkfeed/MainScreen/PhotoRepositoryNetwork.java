package com.example.rechee.sharkfeed.MainScreen;

import android.arch.lifecycle.LiveData;

import com.example.rechee.sharkfeed.FlickrService;

import java.io.IOException;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by Rechee on 1/1/2018.
 */

public class PhotoRepositoryNetwork implements PhotoRepository {

    private final FlickrService flickrService;
    private final String apiKey;

    @Inject
    public PhotoRepositoryNetwork(FlickrService githubService, String apiKey){
        this.flickrService = githubService;
        this.apiKey = apiKey;
    }
}
