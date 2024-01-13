package com.example.ecodine.home;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.ecodine.R;
import com.example.ecodine.entity.Restaurant;

public class RestaurantActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant);

        Intent intent = getIntent();
        Restaurant restaurant = (Restaurant) intent.getSerializableExtra(HomeFragment.SELECTED_RESTAURANT);

    }
}