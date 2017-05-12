package com.samir.andrew.orchestra.Data;

/**
 * Created by andre on 05-May-17.
 */

public class NotificationModel {

    String text, time;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }



    public NotificationModel(String text,  String time) {

        this.text = text;
        this.time = time;
    }
}
