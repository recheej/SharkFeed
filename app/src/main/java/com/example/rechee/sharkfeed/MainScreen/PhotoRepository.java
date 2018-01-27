package com.example.rechee.sharkfeed.MainScreen;

import android.arch.lifecycle.LiveData;

import com.example.rechee.sharkfeed.BaseRepository;

/**
 * Created by Rechee on 1/1/2018.
 */

public interface PhotoRepository extends BaseRepository {
    LiveData<SearchResult> getSearchResult(String searchText, int page);
}
