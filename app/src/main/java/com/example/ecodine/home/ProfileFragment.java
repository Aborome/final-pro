package com.example.ecodine.home;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.ecodine.R;
import com.example.ecodine.auth.LoginActivity;
import com.example.ecodine.callBack.UserCallBack;
import com.example.ecodine.controller.AuthController;
import com.example.ecodine.controller.UserController;
import com.example.ecodine.entity.User;

import de.hdodenhof.circleimageview.CircleImageView;


public class ProfileFragment extends Fragment {
    public static final String CURRENT_USER = "CURRENT_USER";
    private Activity activity;
    private AuthController authController;
    private UserController userController;

    private CircleImageView fProfile_IV_profileImage;
    private TextView fProfile_TV_name;
    private CardView fProfile_CV_editDetails;
    private CardView fProfile_CV_restaurant;
    private CardView fProfile_CV_logout;

    private User currentUser;

    public ProfileFragment(Activity activity) {
        this.activity = activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        findViews(view);
        initVars();
        fetchCurrentUserData();
        return view;
    }

    private void initVars() {

        authController = new AuthController();
        userController = new UserController();
        fProfile_CV_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(activity, LoginActivity.class);
                startActivity(intent);
                authController.logout();
                activity.finish();
            }
        });

        fProfile_CV_restaurant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity, MyRestaurantActivity.class);

                startActivity(intent);
            }
        });

        fProfile_CV_editDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity, EditAccountActivity.class);
                intent.putExtra(CURRENT_USER, currentUser);
                startActivity(intent);
            }
        });
    }

    private void findViews(View view) {
        fProfile_IV_profileImage = view.findViewById(R.id.fProfile_IV_profileImage);
        fProfile_TV_name = view.findViewById(R.id.fProfile_TV_name);
        fProfile_CV_editDetails = view.findViewById(R.id.fProfile_CV_editDetails);
        fProfile_CV_restaurant = view.findViewById(R.id.fProfile_CV_restaurant);
        fProfile_CV_logout = view.findViewById(R.id.fProfile_CV_logout);
    }

    private void fetchCurrentUserData(){
        String uid = authController.getCurrentUser().getUid();
        userController.setUserCallBack(new UserCallBack() {
            @Override
            public void onUserDataSaveComplete(boolean statue, String msg) {

            }
            @Override
            public void onFetchUserDataComplete(User user) {
                currentUser = user;
                fProfile_TV_name.setText(user.getFullName());
                if(user.getImageUrl() != null){
                    Glide.with(activity).load(user.getImageUrl()).into(fProfile_IV_profileImage);
                }
            }
        });


        userController.getUserData(uid);

    }
}