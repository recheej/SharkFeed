package com.example.rechee.sharkfeed.dagger.viewmodel;


import android.content.Context;

import com.example.rechee.sharkfeed.FlickrService;
import com.example.rechee.sharkfeed.MainScreen.PhotoRepository;
import com.example.rechee.sharkfeed.MainScreen.PhotoRepositoryNetwork;
import com.example.rechee.sharkfeed.R;

import dagger.Module;
import dagger.Provides;

/**
 * Created by reche on 1/1/2018.
 */

@Module
public class RepositoryModule {

    private final Context context;

    public RepositoryModule(Context context){
        this.context = context;
    }

    @Provides
    @ViewModelScope
    PhotoRepository userRepository(FlickrService flickrService) {
        return new PhotoRepositoryNetwork(flickrService, context.getString(R.string.flickr_api_key));
    }
}