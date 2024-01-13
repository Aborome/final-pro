package com.example.ecodine.callBack;

import com.example.ecodine.entity.Restaurant;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;

public interface RestaurantCallBack {
    void onUpdateComplete(Task<Void> task);
    void onFetchComplete(Restaurant restaurant);
    void onFetchRestaurantsComplete(ArrayList<Restaurant> restaurants);
}
