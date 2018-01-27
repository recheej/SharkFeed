package com.example.rechee.sharkfeed;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.content.Context;
import android.support.annotation.NonNull;

import com.example.rechee.sharkfeed.MainScreen.MainViewModel;
import com.example.rechee.sharkfeed.MainScreen.PhotoRepository;

import javax.inject.Inject;
import javax.inject.Named;

import dagger.Lazy;

/**
 * Created by Rechee on 1/2/2018.
 */

public class ViewModelFactory implements ViewModelProvider.Factory {

    private final Lazy<PhotoRepository> photoRepositoryLazy;

    @Inject
    public ViewModelFactory(Lazy<PhotoRepository> photoRepositoryLazy) {
        this.photoRepositoryLazy = photoRepositoryLazy;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {

        if(modelClass == MainViewModel.class){
            return (T) new MainViewModel(photoRepositoryLazy.get());
        }

        throw new RuntimeException("could not find that view model");
    }
}
