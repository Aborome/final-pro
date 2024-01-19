package com.example.ecodine.home;

import android.adservices.topics.Topic;
import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.ecodine.R;
import com.example.ecodine.adapter.PostAdapter;
import com.example.ecodine.callBack.PostCallBack;
import com.example.ecodine.callBack.RestaurantCallBack;
import com.example.ecodine.controller.AuthController;
import com.example.ecodine.controller.PostController;
import com.example.ecodine.controller.RestaurantController;
import com.example.ecodine.entity.Post;
import com.example.ecodine.entity.Restaurant;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.Date;

public class PostsFragment extends Fragment {
    public static int POINTS_PER_POST = 100;
    private Context context;
    private TextInputLayout fPosts_TF_newPost;
    private FloatingActionButton fPosts_FAB_addPost;
    private RecyclerView fPosts_RV_posts;
    private PostController postController;
    private AuthController authController;
    RestaurantController restaurantController;
    public PostsFragment(Context context) {
        this.context = context;
        postController = new PostController();
        authController = new AuthController();
        restaurantController = new RestaurantController();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_posts, container, false);
        findViews(view);
        initVars();
        return view;
    }

    private void initVars() {
        restaurantController.setRestaurantCallBack(new RestaurantCallBack() {
            @Override
            public void onUpdateComplete(Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(context, "You got new points for your restaurant", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFetchComplete(Restaurant restaurant) {

            }

            @Override
            public void onFetchRestaurantsComplete(ArrayList<Restaurant> restaurants) {

            }
        });
        postController.setPostCallBack(new PostCallBack() {
            @Override
            public void onPostSaveComplete(Task<Void> task) {
                if(task.isSuccessful()){
                    String uid = authController.getCurrentUser().getUid();
                    Restaurant restaurant = restaurantController.fetchRestaurantSync(uid);
                    if(restaurant != null){
                        restaurant.setPoints(restaurant.getPoints() + POINTS_PER_POST);
                        restaurantController.updateRestaurant(restaurant);
                    }
                    Toast.makeText(context, "post saved successfully", Toast.LENGTH_SHORT).show();
                    fPosts_TF_newPost.getEditText().setText("");
                }else{
                    String err = task.getException().getMessage().toString();
                    Toast.makeText(context, err, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onPostsFetchComplete(ArrayList<Post> posts) {
                PostAdapter postAdapter = new PostAdapter(context, posts);
                fPosts_RV_posts.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
                fPosts_RV_posts.setHasFixedSize(true);
                fPosts_RV_posts.setItemAnimator(new DefaultItemAnimator());
                fPosts_RV_posts.setAdapter(postAdapter);
            }
        });

        fPosts_FAB_addPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String postData = fPosts_TF_newPost.getEditText().getText().toString();
                if(postData.isEmpty()){
                    Toast.makeText(context, "post is required!",Toast.LENGTH_SHORT).show();
                    return;
                }
                String uid = authController.getCurrentUser().getUid();
                Post post = new Post()
                        .setPost(postData)
                        .setDate(new Date())
                        .setUserId(uid);

                postController.savePost(post);
            }
        });

        postController.fetchPosts();
    }

    private void findViews(View view) {
        fPosts_TF_newPost = view.findViewById(R.id.fPosts_TF_newPost);
        fPosts_FAB_addPost = view.findViewById(R.id.fPosts_FAB_addPost);
        fPosts_RV_posts = view.findViewById(R.id.fPosts_RV_posts);

    }
}