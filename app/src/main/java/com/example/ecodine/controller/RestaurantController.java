package com.example.ecodine.controller;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.ecodine.callBack.RestaurantCallBack;
import com.example.ecodine.entity.Restaurant;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class RestaurantController {

    private FirebaseFirestore database;
    private RestaurantCallBack restaurantCallBack;

    public RestaurantController(){
        database = FirebaseFirestore.getInstance();
    }
    public void setRestaurantCallBack(RestaurantCallBack restaurantCallBack){
        this.restaurantCallBack = restaurantCallBack;
    }
    public void updateRestaurant(Restaurant restaurant){
        restaurant.save(this.database).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                restaurantCallBack.onUpdateComplete(task);
            }
        });
    }

    public void fetchRestaurant(String uid){
        this.database.collection(Restaurant.RestaurantTable).document(uid)
                .addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                Restaurant restaurant = value.toObject(Restaurant.class);
                if(restaurant == null){
                    restaurantCallBack.onFetchComplete(null);
                }else{
                    if(restaurant.getImagePath() != null){
                        StorageController storageController = new StorageController();
                        String imageUrl = storageController.downloadImageUrl(restaurant.getImagePath());
                        restaurant.setImageUrl(imageUrl);
                    }
                    restaurant.setUid(value.getId());
                    restaurantCallBack.onFetchComplete(restaurant);
                }
            }
        });
    }

    public void fetchRestaurants(){
        this.database.collection(Restaurant.RestaurantTable)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                ArrayList<Restaurant> restaurants = new ArrayList<>();
                if(value == null){
                    return;
                }
                for(DocumentSnapshot snapshot : value.getDocuments()){
                    Restaurant restaurant = snapshot.toObject(Restaurant.class);

                    if(restaurant.getImagePath() != null){
                        StorageController storageController = new StorageController();
                        String imageUrl = storageController.downloadImageUrl(restaurant.getImagePath());
                        restaurant.setImageUrl(imageUrl);
                    }
                    restaurant.setUid(snapshot.getId());

                    restaurants.add(restaurant);
                }

                restaurantCallBack.onFetchRestaurantsComplete(restaurants);
            }
        });
    }
}
