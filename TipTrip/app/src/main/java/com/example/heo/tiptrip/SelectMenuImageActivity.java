package com.example.heo.tiptrip;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;


public class SelectMenuImageActivity extends AppCompatActivity {

    public static final String FILE_NAME = "temp.jpg";

    private static final int GALLERY_PERMISSIONS_REQUEST = 0;
    private static final int GALLERY_CODE = 1;
    public static final int CAMERA_PERMISSIONS_REQUEST = 2;
    public static final int CAMERA_CODE = 3;

    ImageView imageView;

    Uri imageURI;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_menu_image);

        imageView = (ImageView) findViewById(R.id.imageView);
    }


    // 카메라 버튼 눌렀을 때
    public void onButtonClick_camera(View v){
        startCamera();
    }

    public void startCamera() {
        if (PermissionUtils.requestPermission(this, CAMERA_PERMISSIONS_REQUEST, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA)) {
            Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            Uri photoUri = FileProvider.getUriForFile(this, getApplicationContext().getPackageName() + ".provider", getCameraFile());

            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
            cameraIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

            startActivityForResult(cameraIntent, CAMERA_CODE);
        }
    }

    public File getCameraFile() {
        File dir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);

        return new File(dir, FILE_NAME);
    }


    // 갤러리 버튼 눌렀을 때
    public void onButtonClick_gallery(View v){
        startGalleryChooser();
    }

    public void startGalleryChooser() {
        if (PermissionUtils.requestPermission(this, GALLERY_PERMISSIONS_REQUEST, Manifest.permission.READ_EXTERNAL_STORAGE)) {
            Intent galleryIntent = new Intent();

            galleryIntent.setType("image/*");
            galleryIntent.setAction(Intent.ACTION_PICK);

            startActivityForResult(galleryIntent, GALLERY_CODE);
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GALLERY_CODE && resultCode == RESULT_OK && data != null) {
            imageURI = data.getData();
            imageView.setImageURI(imageURI);
        }
        else if (requestCode == CAMERA_CODE && resultCode == RESULT_OK) {
            Uri photoUri = FileProvider.getUriForFile(this, getApplicationContext().getPackageName() + ".provider", getCameraFile());
            imageURI = photoUri;
            imageView.setImageURI(photoUri);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case CAMERA_PERMISSIONS_REQUEST:
                if (PermissionUtils.permissionGranted(requestCode, CAMERA_PERMISSIONS_REQUEST, grantResults)) {
                    startCamera();
                }
                break;

            case GALLERY_PERMISSIONS_REQUEST:
                if (PermissionUtils.permissionGranted(requestCode, GALLERY_PERMISSIONS_REQUEST, grantResults)) {
                    startGalleryChooser();
                }
                break;
        }
    }


    // 선택완료 버튼 눌렀을 때
    public void onButtonClick_select(View v){
        if(imageURI != null){   // 이미지뷰에 이미지가 있을 때
            Intent intent = new Intent(getApplicationContext(), DetectMenuActivity.class);
            intent.putExtra("imageUri", imageURI.toString());
            startActivity(intent);
        }
        else{   // 이미지뷰에 이미지가 없을 때
            Toast.makeText(this, "이미지가 없습니다..!", Toast.LENGTH_SHORT).show();
        }
    }

}