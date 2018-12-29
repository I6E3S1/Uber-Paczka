package com.example.dominik.uberpaczka.launch;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.example.dominik.uberpaczka.R;
import com.example.dominik.uberpaczka.login.LoginActivity;
import com.example.dominik.uberpaczka.map.MapsActivity;
import com.example.dominik.uberpaczka.util.ConnectivityChecker;
import com.google.firebase.auth.FirebaseAuth;

public class LaunchActivity extends AppCompatActivity {

    private static final int SPLASH_TIME_OUT = 1000;

    //show logo
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);
        Context context = getApplicationContext();
        if (ConnectivityChecker.checkInternetConnection(context, getSupportFragmentManager())) {
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
