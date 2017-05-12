package com.samir.andrew.orchestra.Data;

import java.io.Serializable;

/**
 * Created by andre on 06-May-17.
 */

public class ChatMSGtoFirebase implements Serializable {

    public String body;
    public String delivered;
    public String seen;
    public String sender;
    public String time;

    public ChatMSGtoFirebase() {

    }

    public void setBODY(String BODY) {
        this.body = BODY;
    }

    public void setDELIVERED(String DELIVERED) {
        this.delivered = DELIVERED;
    }

    public void setSEEN(String SEEN) {
        this.seen = SEEN;
    }

    public void setSENDER(String SENDER) {
        this.sender = SENDER;
    }

    public void setTIME(String TIME) {
        this.time = TIME;
    }
}
