package com.example.rechee.sharkfeed;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Rechee on 1/1/2018.
 */

public interface FlickrService {
    @GET("/rest/?method=flickr.photos.search&api_key={apiKey}" +
            "&text={searchText}&format=json&nojsoncallback=1&page={page}" +
            "&extras=url_t,url_c,url_l,url_o")
    Call<SearchResult> getSearchResult(@Path("searchText") String searchText,
                                       @Path("apiKey") String apiKey,
                                       @Path("page") int page);
}
