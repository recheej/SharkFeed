package com.example.rechee.sharkfeed.MainScreen;

import android.content.Context;
import android.widget.Toast;

import com.example.rechee.sharkfeed.Error;
import com.example.rechee.sharkfeed.R;

/**
 * Created by Rechee on 1/27/2018.
 */

public class ErrorHandler {
    private final Context context;

    public ErrorHandler(Context context){
        this.context = context;
    }

    public static ErrorHandler newInstance(Context context){
        return new ErrorHandler(context);
    }

    public static Toast getErrorToast(Context context, Error error){
        ErrorHandler errorHandler = new ErrorHandler(context);
        return errorHandler.getErrorToast(error);
    }

    public Toast getErrorToast(Error error){
        int errorID;
        switch (error){
            case GENERIC_NETWORK_ERROR:
                errorID = R.string.generic_network_error;
                break;
            case DOWNLOAD_IMAGE_FAILED:
                errorID = R.string.cannot_download;
                break;
            case LOAD_IMAGE_FAILED:
                errorID = R.string.load_image_failed;
                break;
            case GENERIC_ERROR:
            default:
                errorID = R.string.generic_error;
        }

        return Toast.makeText(context,
                errorID, Toast.LENGTH_SHORT);
    }
}
