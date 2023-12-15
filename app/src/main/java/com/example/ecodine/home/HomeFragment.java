package com.example.ecodine.home;

import android.app.Activity;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ecodine.R;
import com.example.ecodine.callBack.UserCallBack;
import com.example.ecodine.controller.AuthController;
import com.example.ecodine.controller.UserController;
import com.example.ecodine.entity.User;


public class HomeFragment extends Fragment {
    private Activity activity;
    private TextView fHome_TV_title;
    private RecyclerView fHome_RV_restaurants;
    private UserController userController;
    private AuthController authController;
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
    }

    private void findViews(View view) {
        fHome_TV_title = view.findViewById(R.id.fHome_TV_title);
        fHome_RV_restaurants = view.findViewById(R.id.fHome_RV_restaurants);
    }
}