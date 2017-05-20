package com.samir.andrew.orchestra.Data;

import java.io.Serializable;

/**
 * Created by andre on 06-May-17.
 */

public class ChatMSGtoFirebase implements Serializable {

    public String _id;
    public String createdAt;
    public String text;


    public user user;

    public ChatMSGtoFirebase() {

    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setUser(user user) {
        this.user = user;
    }
}
