package com.example.dominik.uberpaczka;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class LaunchActivity extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 1000;


    //show logo
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);
        Context context = getApplicationContext();
        if (Checker.checkInternetConnection(context, getSupportFragmentManager())) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (FirebaseAuth.getInstance().getCurrentUser() == null) {
                        Intent homeIntent = new Intent(LaunchActivity.this, LoginActivity.class);
                        startActivity(homeIntent);
                        finish();
                    } else {
                        Intent homeIntent1 = new Intent(LaunchActivity.this, MapsActivity.class);
                        startActivity(homeIntent1);
                        finish();
                    }

                }
            }, SPLASH_TIME_OUT);
        }
        else {
            finish();
        }
    }
}
