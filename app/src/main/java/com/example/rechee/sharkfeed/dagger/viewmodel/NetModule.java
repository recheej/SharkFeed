package com.example.rechee.sharkfeed.dagger.viewmodel;

import android.content.Context;

import com.example.rechee.sharkfeed.FlickrService;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by reche on 1/1/2018.
 */

@Module
public class NetModule {
    private static final String FLICKR_BASE_URL = "https://api.flickr.com/";

    @Provides
    @ViewModelScope
    public FlickrService flickrService() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(FLICKR_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit.create(FlickrService.class);
    }
}