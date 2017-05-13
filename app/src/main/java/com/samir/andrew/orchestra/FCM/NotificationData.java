package com.samir.andrew.orchestra.FCM;

public class NotificationData {

    public static final String TEXT = "TEXT";

    private String imageName;
    private int id; // identificador da notificação
    private String title;
    private String textMessage;
    private String sound;

    private String project;
    private String unit;


    public NotificationData() {}

    public NotificationData(String imageName, int id, String title, String textMessage, String sound) {
        this.imageName = imageName;
        this.id = id;
        this.title = title;
        this.textMessage = textMessage;
        this.sound = sound;
    }

    public NotificationData(String imageName, int id, String title, String textMessage, String sound, String project, String unit) {
        this.imageName = imageName;
        this.id = id;
        this.title = title;
        this.textMessage = textMessage;
        this.sound = sound;
        this.project = project;
        this.unit = unit;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTextMessage() {
        return textMessage;
    }

    public void setTextMessage(String textMessage) {
        this.textMessage = textMessage;
    }

    public String getSound() {
        return sound;
    }

    public void setSound(String sound) {
        this.sound = sound;
    }

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }
}
