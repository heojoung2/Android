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
import java.text.SimpleDateFormat;
import java.util.Date;


public class SelectMenuImageActivity extends AppCompatActivity {

    public static final String FILE_NAME = "temp.jpg";

    private static final int GALLERY_PERMISSIONS_REQUEST = 0;
    private static final int GALLERY_CODE = 1;
    public static final int CAMERA_PERMISSIONS_REQUEST = 2;
    public static final int CAMERA_CODE = 3;
    public static final int CROP_CODE = 4;

    ImageView imageView;

    Uri imageURI, photoURI;


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
        // 카메라 권한 허용
        if (PermissionUtils.requestPermission(this, CAMERA_PERMISSIONS_REQUEST, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA)) {
            Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);   // 카메라 인텐트 연결
            Uri photoUri = FileProvider.getUriForFile(this, getApplicationContext().getPackageName() + ".provider", getCameraFile());   // 사진이 저장되는 주소 저장

            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
            cameraIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

            startActivityForResult(cameraIntent, CAMERA_CODE);
        }
    }

    // 사진이 저장되는 주소를 가져오는 함수
    public File getCameraFile() {
        File dir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);

        return new File(dir, FILE_NAME);
    }

    // 사진을 저장하는 포멧 설정
    public Uri createImageFile(){
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + ".jpg";

        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        Uri uri = Uri.fromFile(new File(storageDir, imageFileName));

        return uri;
    }


    // 갤러리 버튼 눌렀을 때
    public void onButtonClick_gallery(View v){
        startGalleryChooser();
    }

    public void startGalleryChooser() {
        // 갤러리 권한 허용
        if (PermissionUtils.requestPermission(this, GALLERY_PERMISSIONS_REQUEST, Manifest.permission.READ_EXTERNAL_STORAGE)) {
            Intent galleryIntent = new Intent();

            galleryIntent.setType("image/*");
            galleryIntent.setAction(Intent.ACTION_PICK);   // 갤러리 인텐트 연결

            startActivityForResult(galleryIntent, GALLERY_CODE);
        }
    }


    // 이미지 CROP하는 함수
    public void cropImage(){
        Intent cropIntent = new Intent("com.android.camera.action.CROP");

        // 50x50 픽셀 미만은 편집할 수 없다는 문구처리
        // 갤러리, 카메라 둘다 호환
        cropIntent.setFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        cropIntent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

        cropIntent.setDataAndType(photoURI, "image/*");   // 크롭할 이미지 연결
        imageURI = createImageFile();   // 크롭한 이미지 저장할 이미지 파일 생성

        cropIntent.putExtra("outputX", 200);   // CROP한 이미지의 x축 크기
        cropIntent.putExtra("outputY", 200);   // CROP한 이미지의 y축 크기
        cropIntent.putExtra("aspectX", 1);     // CROP box의 x축 비율 (CROP box의 크기를 사용자가 자유자재로 쓰게 하려면 주석처리!!)
        cropIntent.putExtra("aspectY", 1);     // CROP box의 y축 비율 (CROP box의 크기를 사용자가 자유자재로 쓰게 하려면 주석처리!!)
        cropIntent.putExtra("scale", false);
        cropIntent.putExtra("output", imageURI);   // 크랍된 이미지를 해당 경로에 저장

        startActivityForResult(cropIntent, CROP_CODE);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GALLERY_CODE && resultCode == RESULT_OK && data != null) {
            photoURI = data.getData();   // 갤러리에서 가져온 사진 연결

            cropImage();
        }
        else if (requestCode == CAMERA_CODE && resultCode == RESULT_OK) {
            photoURI = FileProvider.getUriForFile(this, getApplicationContext().getPackageName() + ".provider", getCameraFile());   // 카메라로 찍은 사진 연결

            cropImage();
        }
        else if (requestCode == CROP_CODE && resultCode == RESULT_OK) {
            imageView.setImageURI(imageURI);   // 크롭한 사진을 뷰로 띄우기
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
            Intent nextIntent = new Intent(getApplicationContext(), DetectMenuActivity.class);
            nextIntent.putExtra("imageUri", imageURI.toString());   // 이미지의 uri값을 스트링으로 바꿔서 보내기

            startActivity(nextIntent);
        }
        else{   // 이미지뷰에 이미지가 없을 때
            Toast.makeText(this, "이미지가 없습니다..!", Toast.LENGTH_SHORT).show();
        }
    }

}