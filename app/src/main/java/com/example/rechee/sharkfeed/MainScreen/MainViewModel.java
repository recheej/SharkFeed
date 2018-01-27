package com.example.rechee.sharkfeed.MainScreen;

import android.arch.core.util.Function;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.example.rechee.sharkfeed.Error;

import java.util.List;

/**
 * Created by Rechee on 1/1/2018.
 */

public class MainViewModel extends ViewModel {

    public static final String SEARCH_TEXT = "shark animal";
    private final PhotoRepository photoRepository;
    private MutableLiveData<Integer> currentPage;
    private LiveData<SearchResult> searchResult;
    private List<Photo> photos;
    private MutableLiveData<Error> error;

    public MainViewModel(final PhotoRepository photoRepository) {
        this.photoRepository = photoRepository;
        currentPage = new MutableLiveData<>();
        error = new MutableLiveData<>();

        this.photoRepository.getError().observeForever(new Observer<Error>() {
            @Override
            public void onChanged(@Nullable Error error) {
                MainViewModel.this.error.setValue(error);
            }
        });

        searchResult = Transformations.switchMap(currentPage, new Function<Integer, LiveData<SearchResult>>() {
            @Override
            public LiveData<SearchResult> apply(Integer page) {
                return photoRepository.getSearchResult(SEARCH_TEXT, page);
            }
        });

        updatePage();
    }

    public LiveData<Error> getError() {
        return error;
    }

    public LiveData<SearchResult> getSearchResult(){
        return searchResult;
    }

    public void updatePage() {
        Integer value = getCurrentPage();
        currentPage.setValue(value + 1);
    }

    @NonNull
    private Integer getCurrentPage() {
        Integer value = currentPage.getValue();
        if(value == null){
            value = 0;
        }
        return value;
    }

    public void updatePage(int pageNumber) {
        Integer value = getCurrentPage();
        if(pageNumber < value){
            //if new page is less than current page, reset
            photos.clear();
        }

        currentPage.setValue(pageNumber);
    }

    public void setPhotos(List<Photo> photos) {
        //we get the photos array from the activity in case we want to clear the items and not just add
        this.photos = photos;
        error.setValue(Error.GENERIC_NETWORK_ERROR);
    }
}
