package com.example.ecodine.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.ecodine.R;
import com.example.ecodine.callBack.RestaurantListener;
import com.example.ecodine.entity.Restaurant;

import java.util.ArrayList;

public class RestaurantAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private ArrayList<Restaurant> restaurants;
    private Context context;
    private RestaurantListener restaurantListener;

    public RestaurantAdapter(Context context, ArrayList<Restaurant> restaurants){
        this.context = context;
        this.restaurants = restaurants;
    }

    public void setRestaurantListener(RestaurantListener restaurantListener){
        this.restaurantListener = restaurantListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.restaurant, parent, false);
        return new RestaurantViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        RestaurantViewHolder restaurantViewHolder = (RestaurantViewHolder) holder;
        Restaurant restaurant = getItem(position);
        Log.d("tes", "ss");
        restaurantViewHolder.restaurantItem_TV_name.setText(restaurant.getName());
        restaurantViewHolder.restaurantItem_TV_points.setText(restaurant.getPoints() + "");
        Glide.with(context)
                .load(restaurant.getImageUrl())
                .into(restaurantViewHolder.restaurantItem_IV_image);
    }

    @Override
    public int getItemCount() {
        return restaurants.size();
    }

    public Restaurant getItem(int i){
        return this.restaurants.get(i);
    }

    public class RestaurantViewHolder extends  RecyclerView.ViewHolder {
        public ImageView restaurantItem_IV_image;
        public TextView restaurantItem_TV_name;
        public TextView restaurantItem_TV_points;

        public RestaurantViewHolder(@NonNull View itemView) {
            super(itemView);
            restaurantItem_TV_points = itemView.findViewById(R.id.restaurantItem_TV_points);
            restaurantItem_TV_name = itemView.findViewById(R.id.restaurantItem_TV_name);
            restaurantItem_IV_image = itemView.findViewById(R.id.restaurantItem_IV_image);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    Restaurant restaurant = getItem(position);
                    restaurantListener.onClick(restaurant, position);
                }
            });
        }

    }
}
