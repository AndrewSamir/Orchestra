package com.samir.andrew.orchestra.Data;

/**
 * Created by andre on 20-Mar-17.
 */

public class FeedsData {

    String body, date, image, title;

    public FeedsData(String body, String date, String image, String title) {
        this.body = body;
        this.date = date;
        this.image = image;
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
