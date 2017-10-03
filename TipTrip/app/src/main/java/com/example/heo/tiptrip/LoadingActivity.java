package com.example.heo.tiptrip;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class LoadingActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //2초 유지
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Intent intent = new Intent(this, TriplistActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);       //다음 액티비티를 스택에 넣지 않는다.
        startActivity(intent);
        finish();
    }
}