package com.example.ecodine.home;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.ecodine.R;
import com.example.ecodine.entity.Restaurant;

public class RestaurantActivity extends AppCompatActivity {

    private ImageView restaurant_IV_image;
    private TextView restaurant_TV_name;
    private TextView restaurant_TV_phone;
    private TextView restaurant_TV_points;
    private TextView restaurant_TV_location;
    private TextView restaurant_TV_description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant);

        Intent intent = getIntent();
        Restaurant restaurant = (Restaurant) intent.getSerializableExtra(HomeFragment.SELECTED_RESTAURANT);

        findViews();
        display(restaurant);
    }

    private void findViews() {
        restaurant_IV_image = findViewById(R.id.restaurant_IV_image);
        restaurant_TV_name = findViewById(R.id.restaurant_TV_name);
        restaurant_TV_phone = findViewById(R.id.restaurant_TV_phone);
        restaurant_TV_points = findViewById(R.id.restaurant_TV_points);
        restaurant_TV_location = findViewById(R.id.restaurant_TV_location);
        restaurant_TV_description = findViewById(R.id.restaurant_TV_description);

    }

    private void display(Restaurant restaurant) {
        if(restaurant.getImageUrl() != null){
            Glide.with(this).load(restaurant.getImageUrl()).into(restaurant_IV_image);
            restaurant_IV_image.setScaleType(ImageView.ScaleType.FIT_XY);
        }
        restaurant_TV_name.setText(restaurant.getName());
        restaurant_TV_phone.setText(restaurant.getPhone());
        restaurant_TV_points.setText(restaurant.getPoints() + "");
        restaurant_TV_location.setText(restaurant.getLocation());
        restaurant_TV_description.setText(restaurant.getDescription());
    }
}