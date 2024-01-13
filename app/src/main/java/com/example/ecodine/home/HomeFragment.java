package com.example.ecodine.home;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ecodine.R;
import com.example.ecodine.adapter.RestaurantAdapter;
import com.example.ecodine.callBack.RestaurantCallBack;
import com.example.ecodine.callBack.RestaurantListener;
import com.example.ecodine.callBack.UserCallBack;
import com.example.ecodine.controller.AuthController;
import com.example.ecodine.controller.RestaurantController;
import com.example.ecodine.controller.UserController;
import com.example.ecodine.entity.Restaurant;
import com.example.ecodine.entity.User;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;


public class HomeFragment extends Fragment {
    public static final String SELECTED_RESTAURANT = "SELECTED_RESTAURANT";
    private Activity activity;
    private TextView fHome_TV_title;
    private RecyclerView fHome_RV_restaurants;
    private UserController userController;
    private AuthController authController;
    private RestaurantController restaurantController;
    public HomeFragment(Activity activity) {
        // Required empty public constructor
        this.activity = activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        findViews(view);
        initVars();
        return view;
    }

    private void initVars() {

        userController = new UserController();
        authController = new AuthController();
        restaurantController = new RestaurantController();

        restaurantController.setRestaurantCallBack(new RestaurantCallBack() {
            @Override
            public void onUpdateComplete(Task<Void> task) {

            }

            @Override
            public void onFetchComplete(Restaurant restaurant) {

            }

            @Override
            public void onFetchRestaurantsComplete(ArrayList<Restaurant> restaurants) {
                RestaurantAdapter restaurantAdapter = new RestaurantAdapter(activity, restaurants);
                restaurantAdapter.setRestaurantListener(new RestaurantListener() {
                    @Override
                    public void onClick(Restaurant restaurant, int position) {
                        Intent intent = new Intent(activity, RestaurantActivity.class);
                        intent.putExtra(SELECTED_RESTAURANT, restaurant);
                        startActivity(intent);
                    }
                });
                fHome_RV_restaurants.setLayoutManager(new GridLayoutManager(activity, 2));
                fHome_RV_restaurants.setHasFixedSize(true);
                fHome_RV_restaurants.setItemAnimator(new DefaultItemAnimator());
                fHome_RV_restaurants.setAdapter(restaurantAdapter);
            }
        });

        userController.setUserCallBack(new UserCallBack() {
            @Override
            public void onUserDataSaveComplete(boolean statue, String msg) {

            }

            @Override
            public void onFetchUserDataComplete(User user) {
                fHome_TV_title.setText("Hello " + user.getFullName());
            }
        });
        String uid = authController.getCurrentUser().getUid();
        userController.getUserData(uid);
        restaurantController.fetchRestaurants();
    }

    private void findViews(View view) {
        fHome_TV_title = view.findViewById(R.id.fHome_TV_title);
        fHome_RV_restaurants = view.findViewById(R.id.fHome_RV_restaurants);
    }
}