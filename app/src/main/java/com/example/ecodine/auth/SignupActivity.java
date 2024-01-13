package com.example.ecodine.auth;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.ecodine.R;
import com.example.ecodine.callBack.AuthCallBack;
import com.example.ecodine.callBack.UserCallBack;
import com.example.ecodine.controller.AuthController;
import com.example.ecodine.controller.UserController;
import com.example.ecodine.entity.AuthUser;
import com.example.ecodine.entity.User;
import com.google.android.gms.tasks.Task;
import com.google.android.material.progressindicator.CircularProgressIndicator;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;

public class SignupActivity extends AppCompatActivity {

    private TextInputLayout signup_TF_fullName;
    private TextInputLayout signup_TF_email;
    private TextInputLayout signup_TF_password;
    private TextInputLayout signup_TF_confirmPassword;
    private Button signup_BTN_createAccount;
    private CircularProgressIndicator signup_PB_loading;

    private AuthController authController;
    private UserController userController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        findViews();
        initVars();
    }

    private void initVars() {
        userController = new UserController();
        authController = new AuthController();
        authController.setAuthCallBack(new AuthCallBack() {
            @Override
            public void onCreateAccountComplete(boolean status, String msg) {

                if(status){
                    String email = signup_TF_email.getEditText().getText().toString();
                    String fullName = signup_TF_fullName.getEditText().getText().toString();
                    User user = new User()
                            .setEmail(email)
                            .setFullName(fullName);
                    user.setUid(authController.getCurrentUser().getUid());
                    userController.saveUserData(user);
                }else{
                    signup_PB_loading.setVisibility(View.INVISIBLE);
                    Toast.makeText(SignupActivity.this, msg, Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onLoginComplete(Task<AuthResult> task) {

            }

        });

       userController.setUserCallBack(new UserCallBack() {
           @Override
           public void onUserDataSaveComplete(boolean status, String msg) {
               signup_PB_loading.setVisibility(View.INVISIBLE);
               Toast.makeText(SignupActivity.this, msg, Toast.LENGTH_SHORT).show();
               if(status){
                   authController.logout();
                   finish();
               }
           }

           @Override
           public void onFetchUserDataComplete(User user) {

           }
       });

        signup_BTN_createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = signup_TF_email.getEditText().getText().toString();
                String password = signup_TF_password.getEditText().getText().toString();
                AuthUser authUser = new AuthUser()
                                .setEmail(email)
                                .setPassword(password);

                authController.createAccount(authUser);
                signup_PB_loading.setVisibility(View.VISIBLE);

            }
        });

    }


    private void findViews() {
        signup_BTN_createAccount = findViewById(R.id.signup_BTN_createAccount);
        signup_TF_fullName = findViewById(R.id.signup_TF_fullName);
        signup_TF_email = findViewById(R.id.signup_TF_email);
        signup_TF_password = findViewById(R.id.signup_TF_password);
        signup_TF_confirmPassword = findViewById(R.id.signup_TF_confirmPassword);
        signup_PB_loading = findViewById(R.id.signup_PB_loading);
    }
}