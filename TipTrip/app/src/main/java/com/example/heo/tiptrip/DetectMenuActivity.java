package com.example.heo.tiptrip;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

public class DetectMenuActivity extends AppCompatActivity {

    ImageView menuImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detect_menu);

        menuImageView = (ImageView)findViewById(R.id.imageView);

        // 이전 인텐트에서 이미지 URI를 받아와서 imageView에 띄우기
        Intent intent = getIntent();
        String getUri = intent.getStringExtra("imageUri");
        Uri imageUri = Uri.parse(getUri);

        menuImageView.setImageURI(imageUri);
    }

}
