package com.example.dominik.uberpaczka;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class Launch extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);
        Context context = getApplicationContext();
        if (networkCheck(context)) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (FirebaseAuth.getInstance().getCurrentUser() == null) {
                        Intent homeIntent = new Intent(Launch.this, Logowanie.class);
                        startActivity(homeIntent);
                        finish();
                    } else {
                        Intent homeIntent1 = new Intent(Launch.this, MapsActivity.class);
                        startActivity(homeIntent1);
                        finish();
                    }

                }
            }, SPLASH_TIME_OUT);
        }
    }

    public static boolean networkCheck(Context context) {

        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = Objects.requireNonNull(connectivityManager).getActiveNetworkInfo();
        boolean result = activeNetworkInfo != null && activeNetworkInfo.isConnected();
        if (!result) {
            Toast.makeText(context, "No internet connection.",
                    Toast.LENGTH_LONG).show();
            return false;
        } else {
            return true;
        }
    }
}
