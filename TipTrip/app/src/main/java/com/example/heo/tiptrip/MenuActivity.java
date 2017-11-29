package com.example.heo.tiptrip;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class MenuActivity extends AppCompatActivity {

    public static final int PERMISSION_CAMERA = 0;
    public static final int CAMERA_CODE = 1;
    public static final int GALLERY_CODE = 2;
    public static final int CROP_CODE = 3;

    ImageView imageView;

    String currentPhotoPath;

    Uri imageURI, photoURI, albumURI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        imageView = (ImageView)findViewById(R.id.image_view);

        checkPermission();
    }

    // 카메라 버튼 눌렀을 때
    public void onButtonClick_camera(View v){
//        String state = Environment.getExternalStorageState();
//
//        // 외장 메모리 검사
//        if(Environment.MEDIA_MOUNTED.equals(state)){
//            Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//
//            if(cameraIntent.resolveActivity(getPackageManager()) != null){
//                File photoFile = null;
//
//                try{
//                    photoFile = createImageFile();
//                } catch (IOException ex){
//                    Log.e("Camera Error!", ex.toString());
//                }
//
//                if(photoFile != null){
//                    Uri providerURI = FileProvider.getUriForFile(this, getPackageName(), photoFile);
//                    imageURI = providerURI;
//
//                    // 인텐트에 전달할 때는 FileProvider의 리턴값인 content://로만!
//                    // providerURI 값에 카메라 데이터를 넣어 보낸다
//                    cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, providerURI);
//
//                    startActivityForResult(cameraIntent, CAMERA_CODE);
//                }
//            }
//        }
//        else{
//            Toast.makeText(this, "저장공간이 접근 불가능한 기기입니다", Toast.LENGTH_SHORT).show();
//            return;
//        }
    }

    public File createImageFile() throws IOException{
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Datone());
        String imageFileName = "JPEG_" + timeStamp + ".jpg";

        File imageFile = null;
        File storageDir = new File(Environment.getExternalStorageDirectory() + "/Pictures", "gyeom");

        if(!storageDir.exists()){
            Log.i("currentPhotoPath1", storageDir.toString());
            storageDir.mkdirs();
        }

        imageFile = new File(storageDir, imageFileName);
        currentPhotoPath = imageFile.getAbsolutePath();

        return imageFile;
    }


    // 갤러리 버튼 눌렀을 때
    public void onButtonClick_gallery(View v){
        Log.i("getAlbum", "Call");
        Intent galleryIntent = new Intent(Intent.ACTION_PICK);
        galleryIntent.setType("image/*");
        galleryIntent.setType(MediaStore.Images.Media.CONTENT_TYPE);
        startActivityForResult(galleryIntent, GALLERY_CODE);
    }

    private void galleryAddPic(){
        Log.i("galleryAddPic", "Call");
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);

        // 해당 경로에 있는 파일 객체화
        File file = new File(currentPhotoPath);
        Uri contentURI = Uri.fromFile(file);

        mediaScanIntent.setData(contentURI);
//        sendBroadcast(mediaScanIntent);

//        Toast.makeText(this, "사진이 앨범에 저장되었습니다!", Toast.LENGTH_SHORT).show();
    }

    // 카메라 전용 CROP
    public void cropImage(){
        Log.i("cropImage", "Call");
        Log.i("cropImage", "photoURI: " + photoURI + " / albumURI: " + albumURI);

        Intent cropIntent = new Intent("com.android.camera.action.CROP");

        // 50x50 픽셀 미만은 편집할 수 없다는 문구처리
        // 갤러리, 포토 둘다 호환
        cropIntent.setFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        cropIntent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

        cropIntent.setDataAndType(photoURI, "image/*");

//        cropIntent.putExtra("outputX", 200);   // CROP한 이미지의 x축 크기
//        cropIntent.putExtra("outputY", 200);   // CROP한 이미지의 y축 크기
        cropIntent.putExtra("aspectX", 1);     // CROP box의 x축 비율
        cropIntent.putExtra("aspectY", 1);     // CROP box의 y축 비율
        cropIntent.putExtra("scale", false);
        cropIntent.putExtra("output", albumURI);   // 크랍된 이미지를 해당 경로에 저장

        startActivityForResult(cropIntent, CROP_CODE);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case CAMERA_CODE:
                if(resultCode == Activity.RESULT_OK){
                    try{
                        Log.i("CAMERA_CODE", "OK");
                        galleryAddPic();

                        imageView.setImageURI(imageURI);
                    } catch (Exception e){
                        Log.e("CAMERA_CODE", e.toString());
                    }
                }
                else{
                    Toast.makeText(MenuActivity.this, "카메라를 취소하였습니다.", Toast.LENGTH_SHORT).show();
                }
                break;

            case GALLERY_CODE:
                if(resultCode == Activity.RESULT_OK){
                    if(data.getData() != null){
                        try{
                            File albumFile = null;
                            albumFile = createImageFile();
                            photoURI = data.getData();
                            albumURI = Uri.fromFile(albumFile);

                            cropImage();
                        } catch (Exception e){
                            Log.e("GALLERY_CODE", e.toString());
                        }
                    }
                }
                break;

            case CROP_CODE:
                if(resultCode == Activity.RESULT_OK){
                    galleryAddPic();
                    imageView.setImageURI(albumURI);
                }
                break;
        }
    }


    // 권한 허용
    private void checkPermission(){
        // 처음 호출시엔 false로 리턴 -> else로 넘어감
        if(ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            if((ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)) ||
                    (ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.CAMERA))){
                new AlertDialog.Builder(this)
                        .setTitle("알림")
                        .setMessage("저장소 권한이 거부되었습니다. 사용을 원하시면 설정에서 해당 권한을 직접 허용하셔야 합니다.")
                        .setNeutralButton("설정", new DialogInterface.OnClickListener(){
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i){
                                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                intent.setData(Uri.parse("package: " + getPackageName()));

                                startActivity(intent);
                            }
                        })
                        .setPositiveButton("확인", new DialogInterface.OnClickListener(){
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i){
                                finish();
                            }
                        })
                        .setCancelable(false)
                        .create()
                        .show();
            }
            else{
                ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE, android.Manifest.permission.CAMERA}, PERMISSION_CAMERA);
            }
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults){
        switch (requestCode){
            case PERMISSION_CAMERA:
                for(int i=0; i<grantResults.length; i++){
                    // grantResults[] : 허용된 권한은 0, 거부한 권한은 -1
                    if(grantResults[i] < 0){
                        Toast.makeText(MenuActivity.this, "해당 권한을 활성화 하셔야 합니다.", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                // 허용했다면 이부분에서..
                break;
        }
    }
}