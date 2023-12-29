package com.example.ecodine.home;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.ecodine.R;
import com.example.ecodine.controller.RestaurantController;
import com.example.ecodine.controller.StorageController;
import com.example.ecodine.entity.Restaurant;
import com.example.ecodine.utils.Generic;
import com.google.android.material.textfield.TextInputLayout;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Date;

public class MyRestaurantActivity extends AppCompatActivity {
    private ImageView myRestaurant_IV_image;
    private TextInputLayout myRestaurant_TF_restaurantName;
    private TextInputLayout myRestaurant_TF_restaurantPhone;
    private TextInputLayout myRestaurant_TF_restaurantLocation;
    private TextInputLayout myRestaurant_TF_restaurantDescription;
    private Button myRestaurant_BTN_update;
    private Uri selectedImageUri;
    private RestaurantController restaurantController;
    private StorageController storageController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_restaurant);

        if(!checkPermissions()) {
            requestPermissions();
        }
        findViews();
        initVars();
    }
    private void findViews() {
        myRestaurant_IV_image = findViewById(R.id.myRestaurant_IV_image);
        myRestaurant_TF_restaurantName = findViewById(R.id.myRestaurant_TF_restaurantName);
        myRestaurant_TF_restaurantPhone = findViewById(R.id.myRestaurant_TF_restaurantPhone);
        myRestaurant_TF_restaurantLocation = findViewById(R.id.myRestaurant_TF_restaurantLocation);
        myRestaurant_TF_restaurantDescription = findViewById(R.id.myRestaurant_TF_restaurantDescription);
        myRestaurant_BTN_update = findViewById(R.id.myRestaurant_BTN_update);
    }
    private void initVars() {
        restaurantController = new RestaurantController();
        storageController = new StorageController();
        myRestaurant_IV_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkPermissions()){
                    showImageSourceDialog();
                }else{
                    Toast.makeText(MyRestaurantActivity.this, "Please provide camera and gallery permissions!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        myRestaurant_BTN_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkInput()){
                    updateRestaurant();
                }

            }
        });
    }

    private boolean checkInput() {

        String restaurantName = myRestaurant_TF_restaurantName.getEditText().getText().toString();
        String restaurantPhone = myRestaurant_TF_restaurantPhone.getEditText().getText().toString();
        String location = myRestaurant_TF_restaurantLocation.getEditText().getText().toString();
        String description = myRestaurant_TF_restaurantDescription.getEditText().getText().toString();


        if(selectedImageUri == null){
            Toast.makeText(this, "Please upload an image!", Toast.LENGTH_SHORT).show();
            return false;

        }
        if(restaurantName.isEmpty()){
            Toast.makeText(this, "Please provide restaurant name!", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(restaurantPhone.isEmpty()){
            Toast.makeText(this, "Please provide restaurant phone!", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(location.isEmpty()){
            Toast.makeText(this, "Please provide restaurant location!", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(description.isEmpty()){
            Toast.makeText(this, "Please provide restaurant description!", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    public void updateRestaurant(){
        String restaurantName = myRestaurant_TF_restaurantName.getEditText().getText().toString();
        String restaurantPhone = myRestaurant_TF_restaurantPhone.getEditText().getText().toString();
        String location = myRestaurant_TF_restaurantLocation.getEditText().getText().toString();
        String description = myRestaurant_TF_restaurantDescription.getEditText().getText().toString();

        double time = new Date().getTime();
        String ex = Generic.getFileExtension(this, selectedImageUri);
        String imagePath = Restaurant.RestaurantTable + "/" + time + "." + ex;

        if( storageController.uploadImage(selectedImageUri, imagePath)){
            Restaurant restaurant = new Restaurant()
                    .setName(restaurantName)
                    .setDescription(description)
                    .setLocation(location)
                    .setImagePath(imagePath)
                    .setPhone(restaurantPhone);
            restaurantController.updateRestaurant(restaurant);
        }

    }

    private void requestPermissions() {
        ActivityCompat.requestPermissions(
                this,
                new String[]{
                        Manifest.permission.CAMERA,
                        Manifest.permission.READ_EXTERNAL_STORAGE
                },
                100
        );
    }

    public  boolean checkPermissions() {
        return (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.CAMERA
        ) == PackageManager.PERMISSION_GRANTED);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 100) {
            if (!(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                requestPermissions();
            }
        }
    }

    private void showImageSourceDialog() {
        CharSequence[] items = {"Camera", "Gallery"};
        AlertDialog.Builder builder = new AlertDialog.Builder(MyRestaurantActivity.this);
        builder.setTitle("Choose Image Source");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                switch (which) {
                    case 0:
                        openCamera();
                        break;
                    case 1:
                        openGallery();
                        break;
                }
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void openCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraResults.launch(intent);
    }

    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        gallery_results.launch(intent);
    }


    private final ActivityResultLauncher<Intent> gallery_results = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {

                    switch (result.getResultCode()) {
                        case Activity.RESULT_OK:
                            try {
                                Intent intent = result.getData();
                                selectedImageUri = intent.getData();
                                final InputStream imageStream = MyRestaurantActivity.this.getContentResolver().openInputStream(selectedImageUri);
                                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                                myRestaurant_IV_image.setImageBitmap(selectedImage);
                                myRestaurant_IV_image.setScaleType(ImageView.ScaleType.FIT_XY);
                            }
                            catch (FileNotFoundException e) {
                                e.printStackTrace();
                                Toast.makeText(MyRestaurantActivity.this, "Something went wrong", Toast.LENGTH_LONG).show();
                            }
                            break;
                        case Activity.RESULT_CANCELED:
                            Toast.makeText(MyRestaurantActivity.this, "Gallery canceled", Toast.LENGTH_SHORT).show();
                            break;
                    }
                }
            });

    private final ActivityResultLauncher<Intent> cameraResults = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {

                    switch (result.getResultCode()) {
                        case Activity.RESULT_OK:
                            Intent intent = result.getData();
                            Bitmap bitmap = (Bitmap)  intent.getExtras().get("data");
                            myRestaurant_IV_image.setImageBitmap(bitmap);
                            selectedImageUri = Generic.getImageUri(MyRestaurantActivity.this, bitmap);
                            myRestaurant_IV_image.setScaleType(ImageView.ScaleType.FIT_XY);
                            break;
                        case Activity.RESULT_CANCELED:
                            Toast.makeText(MyRestaurantActivity.this, "Camera canceled", Toast.LENGTH_SHORT).show();
                            break;
                    }
                }
            });

}