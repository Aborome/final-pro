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

import com.bumptech.glide.Glide;
import com.example.ecodine.R;
import com.example.ecodine.callBack.UserCallBack;
import com.example.ecodine.controller.AuthController;
import com.example.ecodine.controller.StorageController;
import com.example.ecodine.controller.UserController;
import com.example.ecodine.entity.Restaurant;
import com.example.ecodine.entity.User;
import com.example.ecodine.utils.Generic;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;

import java.io.FileNotFoundException;
import java.io.InputStream;

import de.hdodenhof.circleimageview.CircleImageView;

public class EditAccountActivity extends AppCompatActivity {
    private CircleImageView editAccount_IV_image;
    private FloatingActionButton editAccount_FBTN_uploadImage;
    private TextInputLayout editAccount_TF_fullName;
    private TextInputLayout editAccount_TF_phone;
    private Button editAccount_BTN_updateAccount;
    private User currentUser;
    private Uri selectedImageUri;
    private UserController userController;
    private StorageController storageController;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_account);

        Intent intent = getIntent();
        currentUser = (User) intent.getSerializableExtra(ProfileFragment.CURRENT_USER);

        if(!checkPermissions()){
            requestPermissions();
        }

        findViews();
        initVars();
        displayUser();
    }

    private void displayUser() {
        editAccount_TF_fullName.getEditText().setText(currentUser.getFullName());
        if(currentUser.getImageUrl() != null){
            Glide.with(this).load(currentUser.getImageUrl()).into(editAccount_IV_image);
        }
        editAccount_TF_phone.getEditText().setText(currentUser.getPhone());
    }

    private void findViews() {
        editAccount_IV_image = findViewById(R.id.editAccount_IV_image);
        editAccount_FBTN_uploadImage = findViewById(R.id.editAccount_FBTN_uploadImage);
        editAccount_TF_fullName = findViewById(R.id.editAccount_TF_fullName);
        editAccount_TF_phone = findViewById(R.id.editAccount_TF_phone);
        editAccount_BTN_updateAccount = findViewById(R.id.editAccount_BTN_updateAccount);
    }

    private void initVars() {
        storageController = new StorageController();
        userController = new UserController();
        userController.setUserCallBack(new UserCallBack() {
            @Override
            public void onUserDataSaveComplete(boolean status, String msg) {
                if(status){
                    Toast.makeText(EditAccountActivity.this, "Account update successfully", Toast.LENGTH_SHORT).show();
                    finish();
                }else{
                    Toast.makeText(EditAccountActivity.this, msg, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFetchUserDataComplete(User user) {

            }
        });
        editAccount_BTN_updateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateAccount();
            }
        });

        editAccount_FBTN_uploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // select image
                showImageSourceDialog();
            }
        });
    }

    private void updateAccount() {
        currentUser.setFullName(editAccount_TF_fullName.getEditText().getText().toString());
        currentUser.setPhone(editAccount_TF_phone.getEditText().getText().toString());
        if(selectedImageUri != null){
            // upload image
            String ex = Generic.getFileExtension(this, selectedImageUri);
            String imagePath = User.UserTable + "/" + currentUser.getUid() + "." + ex;
            if(!storageController.uploadImage(selectedImageUri, imagePath)){
                Toast.makeText(this, "Failed to upload image! please try again", Toast.LENGTH_SHORT).show();
                return;
            }
            currentUser.setImagePath(imagePath);
        }
        userController.saveUserData(currentUser);
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
        AlertDialog.Builder builder = new AlertDialog.Builder(EditAccountActivity.this);
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
                                final InputStream imageStream = EditAccountActivity.this.getContentResolver().openInputStream(selectedImageUri);
                                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                                editAccount_IV_image.setImageBitmap(selectedImage);
                            }
                            catch (FileNotFoundException e) {
                                e.printStackTrace();
                                Toast.makeText(EditAccountActivity.this, "Something went wrong", Toast.LENGTH_LONG).show();
                            }
                            break;
                        case Activity.RESULT_CANCELED:
                            Toast.makeText(EditAccountActivity.this, "Gallery canceled", Toast.LENGTH_SHORT).show();
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
                            editAccount_IV_image.setImageBitmap(bitmap);
                            selectedImageUri = Generic.getImageUri(EditAccountActivity.this, bitmap);
                            break;
                        case Activity.RESULT_CANCELED:
                            Toast.makeText(EditAccountActivity.this, "Camera canceled", Toast.LENGTH_SHORT).show();
                            break;
                    }
                }
            });
}