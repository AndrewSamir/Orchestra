package com.samir.andrew.orchestra.Data;

/**
 * Created by andre on 06-May-17.
 */

public class ChatMessageModel {

    String KEY;
    String _id;
    String createdAt;
    String text;
    String SENDER;


    public ChatMessageModel(String KEY, String _id, String createdAt, String text, String SENDER) {
        this.KEY = KEY;
        this._id = _id;
        this.createdAt = createdAt;
        this.text = text;
        this.SENDER = SENDER;
    }

    public String getKEY() {
        return KEY;
    }

    public String get_id() {
        return _id;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getText() {
        return text;
    }

    public String getSENDER() {
        return SENDER;
    }
}
