package com.example.ecodine.entity;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.Exclude;
import com.google.firebase.firestore.FirebaseFirestore;

public class Restaurant extends FirebaseUid{
    public static final String RestaurantTable = "Restaurants";

    private String name;
    private String phone;
    private String location;
    private String description;
    private int points;
    private String imagePath;
    private String imageUrl;

    public Restaurant() {}

    public String getName() {
        return name;
    }

    public Restaurant setName(String name) {
        this.name = name;
        return this;
    }

    public String getPhone() {
        return phone;
    }

    public Restaurant setPhone(String phone) {
        this.phone = phone;
        return this;
    }

    public String getLocation() {
        return location;
    }

    public Restaurant setLocation(String location) {
        this.location = location;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Restaurant setDescription(String description) {
        this.description = description;
        return this;
    }

    public int getPoints() {
        return points;
    }

    public Restaurant setPoints(int points) {
        this.points = points;
        return this;
    }

    public String getImagePath() {
        return imagePath;
    }

    public Restaurant setImagePath(String imagePath) {
        this.imagePath = imagePath;
        return this;
    }

    @Exclude
    public String getImageUrl() {
        return imageUrl;
    }

    public Restaurant setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }

    public DocumentReference save(FirebaseFirestore db){
        DocumentReference ref ;
        if(this.uid != null){
            ref = db.collection(RestaurantTable).document(this.uid);
        }else{
            ref = db.collection(RestaurantTable).document();
        }
        Task<Void> task = ref.set(this);
        while (!task.isCanceled() || !task.isComplete());
        return ref;
    }
}
