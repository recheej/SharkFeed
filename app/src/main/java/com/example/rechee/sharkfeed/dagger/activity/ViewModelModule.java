package com.example.rechee.sharkfeed.dagger.activity;

import android.content.Context;

import com.example.rechee.sharkfeed.ViewModelFactory;

import javax.inject.Named;

import dagger.Lazy;
import dagger.Module;
import dagger.Provides;

/**
 * Created by reche on 1/1/2018.
 */

@Module
public class ViewModelModule {

//    @Provides
//    @ActivityScope
//    ViewModelFactory viewModelFactory(Lazy<UserRepository> userRepositoryLazy,
//                                      @Named("applicationContext") Context context,
//                                      Lazy<RepoRepository> repoRepositoryLazy) {
//        return new ViewModelFactory(userRepositoryLazy, context, repoRepositoryLazy);
//    }
}