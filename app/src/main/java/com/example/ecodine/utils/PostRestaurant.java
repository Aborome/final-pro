package com.example.ecodine.utils;

import com.example.ecodine.entity.Post;
import com.example.ecodine.entity.Restaurant;

public class PostRestaurant extends Post {
    private Restaurant restaurant;

    public PostRestaurant(){}

    public PostRestaurant(Restaurant restaurant, Post post){
        this.restaurant = restaurant;
        this.setUid(post.getUid());
        this.setUserId(post.getUserId());
        this.setDate(post.getDate());
        this.setPost(post.getPost());
    }

    public Restaurant getRestaurant(){
        return restaurant;
    }

    public PostRestaurant setRestaurant(Restaurant res){
        this.restaurant = res;
        return this;
    }
}
