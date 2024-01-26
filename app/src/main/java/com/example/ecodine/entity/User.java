package com.example.ecodine.entity;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.Exclude;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.Serializable;

public class User extends FirebaseUid implements Serializable{
    public static final String UserTable = "Users";
    private String fullName;
    private String email;
    private String imagePath;
    private String imageUrl;
    private String phone;

    public User(){}

    public String getFullName() {
        return fullName;
    }

    public User setFullName(String fullName) {
        this.fullName = fullName;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public User setEmail(String email) {
        this.email = email;
        return this;
    }

    @Exclude
    public String getImageUrl() {
        return imageUrl;
    }

    public User setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }

    public String getImagePath() {
        return imagePath;
    }

    public User setImagePath(String imagePath) {
        this.imagePath = imagePath;
        return this;
    }
    public Task<Void> save(FirebaseFirestore db){
        return db.collection(UserTable).document(this.uid).set(this);
    }

    public String getPhone(){
        return this.phone;
    }
    public void setPhone(String phone){
        this.phone = phone;
    }
}
