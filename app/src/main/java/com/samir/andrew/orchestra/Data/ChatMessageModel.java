package com.samir.andrew.orchestra.Data;

/**
 * Created by andre on 06-May-17.
 */

public class ChatMessageModel {

    String KEY;
    String BODY;
    Boolean DELIVERED;
    Boolean SEEN;
    Boolean SENDER;
    String TIME;

    public ChatMessageModel(String KEY, String BODY, Boolean DELIVERED, Boolean SEEN, Boolean SENDER, String TIME) {
        this.KEY = KEY;
        this.BODY = BODY;
        this.DELIVERED = DELIVERED;
        this.SEEN = SEEN;
        this.SENDER = SENDER;
        this.TIME = TIME;
    }

    public String getKEY() {
        return KEY;
    }

    public void setKEY(String KEY) {
        this.KEY = KEY;
    }

    public String getBODY() {
        return BODY;
    }

    public void setBODY(String BODY) {
        this.BODY = BODY;
    }

    public Boolean getDELIVERED() {
        return DELIVERED;
    }

    public void setDELIVERED(Boolean DELIVERED) {
        this.DELIVERED = DELIVERED;
    }

    public Boolean getSEEN() {
        return SEEN;
    }

    public void setSEEN(Boolean SEEN) {
        this.SEEN = SEEN;
    }

    public Boolean getSENDER() {
        return SENDER;
    }

    public void setSENDER(Boolean SENDER) {
        this.SENDER = SENDER;
    }

    public String getTIME() {
        return TIME;
    }

    public void setTIME(String TIME) {
        this.TIME = TIME;
    }
}
