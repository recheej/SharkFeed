package com.example.rechee.sharkfeed;

import android.arch.lifecycle.LiveData;

import com.example.rechee.sharkfeed.Error;

/**
 * Created by Rechee on 1/27/2018.
 */

public interface BaseRepository {
    LiveData<Error> getError();
}
