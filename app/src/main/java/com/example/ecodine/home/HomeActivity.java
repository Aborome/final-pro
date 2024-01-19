package com.example.ecodine.home;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import com.example.ecodine.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class HomeActivity extends AppCompatActivity {

    private FrameLayout home_FL_home, home_FL_posts, home_FL_ideas, home_FL_profile;
    private HomeFragment homeFragment;
    private ProfileFragment profileFragment;
    private IdeasFragment ideasFragment;
    private PostsFragment postsFragment;

    private BottomNavigationView home_BN;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        findViews();
        initFragments();
        initVars();

    }

    private void initVars() {

        home_BN.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if(item.getItemId() == R.id.home){
                    // home page
                    home_FL_home.setVisibility(View.VISIBLE);
                    home_FL_posts.setVisibility(View.INVISIBLE);
                    home_FL_ideas.setVisibility(View.INVISIBLE);
                    home_FL_profile.setVisibility(View.INVISIBLE);
                }else if(item.getItemId() == R.id.posts) {
                    // posts page
                    home_FL_home.setVisibility(View.INVISIBLE);
                    home_FL_posts.setVisibility(View.VISIBLE);
                    home_FL_ideas.setVisibility(View.INVISIBLE);
                    home_FL_profile.setVisibility(View.INVISIBLE);
                }else if (item.getItemId() == R.id.ideas){
                    //ideas page
                    home_FL_home.setVisibility(View.INVISIBLE);
                    home_FL_posts.setVisibility(View.INVISIBLE);
                    home_FL_ideas.setVisibility(View.VISIBLE);
                    home_FL_profile.setVisibility(View.INVISIBLE);
                }else {
                    //profile page
                    home_FL_home.setVisibility(View.INVISIBLE);
                    home_FL_posts.setVisibility(View.INVISIBLE);
                    home_FL_ideas.setVisibility(View.INVISIBLE);
                    home_FL_profile.setVisibility(View.VISIBLE);
                }
                return true;
            }
        });


    }

    private void findViews() {
        home_FL_home = findViewById(R.id.home_FL_home);
        home_FL_posts = findViewById(R.id.home_FL_posts);
        home_FL_ideas = findViewById(R.id.home_FL_ideas);
        home_FL_profile = findViewById(R.id.home_FL_profile);
        home_BN = findViewById(R.id.home_BN);
    }

    public void initFragments(){
        homeFragment = new HomeFragment(this);
        profileFragment = new ProfileFragment(this);
        ideasFragment = new IdeasFragment();
        postsFragment = new PostsFragment(this);

        getSupportFragmentManager().beginTransaction().add(R.id.home_FL_home, homeFragment).commit();
        getSupportFragmentManager().beginTransaction().add(R.id.home_FL_posts, postsFragment).commit();
        getSupportFragmentManager().beginTransaction().add(R.id.home_FL_ideas, ideasFragment).commit();
        getSupportFragmentManager().beginTransaction().add(R.id.home_FL_profile, profileFragment).commit();

        home_FL_home.setVisibility(View.VISIBLE);
        home_FL_posts.setVisibility(View.INVISIBLE);
        home_FL_ideas.setVisibility(View.INVISIBLE);
        home_FL_profile.setVisibility(View.INVISIBLE);
    }
}