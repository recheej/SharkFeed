package com.example.rechee.sharkfeed.dagger.activity;

import com.example.rechee.sharkfeed.MainScreen.MainActivity;

import dagger.Subcomponent;

/**
 * Created by reche on 1/1/2018.
 */

@ActivityScope
@Subcomponent(modules={ViewModelModule.class})
public interface ActivityComponent {
    void inject(MainActivity mainActivity);
}
