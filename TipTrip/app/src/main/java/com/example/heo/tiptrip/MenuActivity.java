package com.example.heo.tiptrip;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import java.io.File;
import java.io.IOException;


public class MenuActivity extends AppCompatActivity {

//    public static final int CAMERA_CODE = 0;
//    public static final int GALLERY_CODE = 1;
//
//    ImageView imageView = (ImageView)findViewById(R.id.image_view);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
    }

    // 카메라 버튼 눌렀을 때
    public void onButtonClick_camera(View v){
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivity(cameraIntent);
    }

    // 갤러리 버튼 눌렀을 때
    public void onButtonClick_gallery(View v){
        Intent galleryIntent = new Intent(Intent.ACTION_PICK);
        galleryIntent.setType(MediaStore.Images.Media.CONTENT_TYPE);
        startActivity(galleryIntent);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }

}