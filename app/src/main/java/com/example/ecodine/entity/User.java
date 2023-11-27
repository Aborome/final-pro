package com.example.ecodine.entity;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class User extends FirebaseUid{
    private String fullName;
    private String email;
    private String imageUrl;
    private String restaurantId;

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

    public String getImageUrl() {
        return imageUrl;
    }

    public User setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }

    public String getRestaurantId() {
        return restaurantId;
    }

    public User setRestaurantId(String restaurantId) {
        this.restaurantId = restaurantId;
        return this;
    }

    public Task<DocumentReference> save(FirebaseFirestore db){
        return db.collection("Users").add(this);
    }
}
