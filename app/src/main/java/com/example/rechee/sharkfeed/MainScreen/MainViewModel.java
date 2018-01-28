package com.example.rechee.sharkfeed.MainScreen;

import android.arch.core.util.Function;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.example.rechee.sharkfeed.Error;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;

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
    private MutableLiveData<Void> downloadFinishedData;

    public MainViewModel(final PhotoRepository photoRepository) {
        this.photoRepository = photoRepository;
        currentPage = new MutableLiveData<>();
        error = new MutableLiveData<>();
        downloadFinishedData = new MutableLiveData<>();

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
    }

    public LiveData<Void> downloadFinished() {
        return downloadFinishedData;
    }

    public void downloadPhoto(String originalImageUrl, File storageDirectory) {
        new DownloadImageTask().execute(new DownloadTaskArgs(storageDirectory, originalImageUrl,
                this.downloadFinishedData));
    }

    private class DownloadTaskArgs {

        public File storageDirectory;
        public String url;
        public MutableLiveData<Void> downloadFinishedData;

        public DownloadTaskArgs(File storageDirectory, String url, MutableLiveData<Void> downloadFinishedData) {
            this.storageDirectory = storageDirectory;
            this.url = url;
            this.downloadFinishedData = downloadFinishedData;
        }
    }

    private class DownloadImageTask extends AsyncTask<DownloadTaskArgs, Void, Void> {

        private MutableLiveData<Void> downloadFinishedData;

        @Override
        protected Void doInBackground(DownloadTaskArgs... downloadTaskArgs) {
            DownloadTaskArgs args = downloadTaskArgs[0];
            this.downloadFinishedData = args.downloadFinishedData;

            URL url;
            try {
                url = new URL(args.url);

                try {
                    String extension = args.url.substring(args.url.lastIndexOf("."));
                    Bitmap image = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                    this.saveImage(image, args.storageDirectory, extension);
                } catch (IOException e) {
                    error.postValue(Error.DOWNLOAD_IMAGE_FAILED);
                }
            } catch (MalformedURLException e) {
                error.postValue(Error.DOWNLOAD_IMAGE_FAILED);
            }
            return null;
        }

        private void saveImage(Bitmap bitmap, File storageDirectory, String extension) {
            //https://stackoverflow.com/questions/11846108/android-saving-bitmap-to-sd-card

            String root = storageDirectory.toString();

            if (! storageDirectory.exists()){
                if (! storageDirectory.mkdir()){
                    error.postValue(Error.DOWNLOAD_IMAGE_FAILED);
                    return;
                }
            }

            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());

            String fileName = "Image-"+ timeStamp + extension;

            File file = new File (storageDirectory, fileName);
            if (file.exists ()) file.delete ();
            try {
                FileOutputStream out = new FileOutputStream(file);
                bitmap.compress(extension.equals("jpg") ? Bitmap.CompressFormat.JPEG: Bitmap.CompressFormat.PNG,
                        90, out);
                out.flush();
                out.close();

                downloadFinishedData.postValue(null);
            } catch (Exception e) {
                error.postValue(Error.DOWNLOAD_IMAGE_FAILED);
            }
        }
    }
}
