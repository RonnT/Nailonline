package com.nailonline.client;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Roman T. on 11.12.2016.
 */

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        synchronizeData();
    }

    private void synchronizeData(){

    }
}
