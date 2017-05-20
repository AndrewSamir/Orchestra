package com.samir.andrew.orchestra.SingleTon;

/**
 * Created by andre on 15-May-17.
 */

public class AdvertSingleTon {

    private static AdvertSingleTon mInstance = null;

    private String title, body, imageUri,date;

    private AdvertSingleTon() {
    }

    public static AdvertSingleTon getInstance() {
        if (mInstance == null) {
            mInstance = new AdvertSingleTon();
        }
        return mInstance;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getImageUri() {
        return imageUri;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
