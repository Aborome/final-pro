package com.example.ecodine.controller;

import com.example.ecodine.entity.Restaurant;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class RestaurantController {

    private FirebaseFirestore database;

    public RestaurantController(){
        database = FirebaseFirestore.getInstance();
    }

    public void updateRestaurant(Restaurant restaurant){

        DocumentReference ref = restaurant.save(this.database);
        UserController userController = new UserController();
        AuthController authController = new AuthController();
        String uid = authController.getCurrentUser().getUid();
        userController.updateRestaurantId(uid, ref.getId());
    }
}
