package com.example.rechee.sharkfeed.MainScreen;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.example.rechee.sharkfeed.Error;
import com.example.rechee.sharkfeed.FlickrService;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Rechee on 1/1/2018.
 */

public class PhotoRepositoryNetwork implements PhotoRepository {

    private final FlickrService flickrService;
    private final String apiKey;
    private MutableLiveData<Error> error;

    @Inject
    public PhotoRepositoryNetwork(FlickrService githubService, String apiKey){
        this.flickrService = githubService;
        this.apiKey = apiKey;
        error = new MutableLiveData<>();
    }

    public LiveData<Error> getError(){
        return error;
    }

    @Override
    public LiveData<SearchResult> getSearchResult(String searchText, int page) {
        final MutableLiveData<SearchResult> data = new MutableLiveData<>();

        Call<SearchResult> searchResultCall = flickrService.getSearchResult(searchText, apiKey, page);
        searchResultCall.enqueue(new Callback<SearchResult>() {
            @Override
            public void onResponse(Call<SearchResult> call, Response<SearchResult> response) {
                if(response.isSuccessful()){
                    data.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<SearchResult> call, Throwable t) {
                error.setValue(Error.GENERIC_NETWORK_ERROR);
            }
        });

        return data;
    }
}
