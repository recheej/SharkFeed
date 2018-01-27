package com.example.rechee.sharkfeed.dagger.application;


import com.example.rechee.sharkfeed.dagger.viewmodel.RepositoryModule;
import com.example.rechee.sharkfeed.dagger.viewmodel.ViewModelComponent;

import dagger.Component;

/**
 * Created by reche on 1/1/2018.
 */

@ApplicationScope
@Component(modules={ApplicationContextModule.class})
public interface ApplicationComponent {
    ViewModelComponent plus(RepositoryModule repositoryModule);
}
