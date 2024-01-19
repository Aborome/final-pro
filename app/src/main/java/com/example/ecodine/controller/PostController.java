package com.example.ecodine.controller;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.ecodine.callBack.PostCallBack;
import com.example.ecodine.entity.Post;
import com.example.ecodine.entity.Restaurant;
import com.example.ecodine.entity.User;
import com.example.ecodine.utils.PostRestaurant;
import com.example.ecodine.utils.PostUser;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collections;

public class PostController {
    private FirebaseFirestore database;
    private PostCallBack postCallBack;
    public PostController(){
        database = FirebaseFirestore.getInstance();
    }

    public void setPostCallBack(PostCallBack postCallBack){
        this.postCallBack = postCallBack;
    }

    public void savePost(Post post){
        post.save(this.database).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                postCallBack.onPostSaveComplete(task);
            }
        });
    }

    public void fetchPosts(){
        RestaurantController restaurantController = new RestaurantController();
        UserController userController = new UserController();
        this.database.collection(Post.POST_TABLE).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                ArrayList<Post> posts = new ArrayList<>();
                for(DocumentSnapshot snapshot: value.getDocuments()){
                    Post post = snapshot.toObject(Post.class);
                    Restaurant restaurant = restaurantController.fetchRestaurantSync(post.getUserId());
                    if(restaurant == null){
                        // no restaurant for this post
                        User user = userController.getUserDataSync(post.getUserId());
                        PostUser postUser = new PostUser(user, post);
                        posts.add(postUser);
                    }else{
                        PostRestaurant postRestaurant = new PostRestaurant(restaurant, post);
                        posts.add(postRestaurant);
                    }
                }

                Collections.sort(posts);
                postCallBack.onPostsFetchComplete(posts);
            }
        });
    }
}
