package com.example.rechee.sharkfeed;

import android.app.Application;

import com.example.rechee.sharkfeed.dagger.application.ApplicationComponent;
import com.example.rechee.sharkfeed.dagger.application.ApplicationContextModule;
import com.example.rechee.sharkfeed.dagger.application.DaggerApplicationComponent;

/**
 * Created by reche on 1/1/2018.
 */

public class SharkFeedApplication extends Application {

    private ApplicationComponent applicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        applicationComponent = DaggerApplicationComponent.builder()
                .applicationContextModule(new ApplicationContextModule(this))
                .build();
    }

    public ApplicationComponent getApplicationComponent() {
        return applicationComponent;
    }
}
