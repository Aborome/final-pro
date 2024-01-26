package com.example.ecodine.entity;

public class Idea extends FirebaseUid{
    public static final String IDEAS_TABLE = "Ideas";
    private String title;
    private String description;

    public Idea(){}
    public String getTitle() {
        return title;
    }

    public Idea setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Idea setDescription(String description) {
        this.description = description;
        return this;
    }
}
