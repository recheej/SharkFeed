package com.example.rechee.sharkfeed;

import com.example.rechee.sharkfeed.MainScreen.SearchResult;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Rechee on 1/1/2018.
 */

public interface FlickrService {
    @GET("/services/rest/?method=flickr.photos.search&format=json&nojsoncallback=1" +
            "&extras=url_t,url_c,url_l,url_o")
    Call<SearchResult> getSearchResult(@Query("text") String searchText,
                                       @Query("api_key") String apiKey,
                                       @Query("page") int page);
}
