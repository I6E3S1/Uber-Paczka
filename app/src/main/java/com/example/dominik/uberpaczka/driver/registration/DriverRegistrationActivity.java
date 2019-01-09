package com.example.dominik.uberpaczka.driver.registration;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.dominik.uberpaczka.R;
import com.example.dominik.uberpaczka.map.MapsActivity;
import com.example.dominik.uberpaczka.util.HasActionButtons;
import com.example.dominik.uberpaczka.util.HasValidation;


public class DriverRegistrationActivity extends AppCompatActivity implements HasValidation, HasActionButtons {

    private Button approveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_registration);

        approveButton=findViewById(R.id.driver_registration_approve);

        initValidationPatterns();
        initButtonListeners();
    }



    @Override
    public boolean validate() {
        return true;
    }

    @Override
    public void initValidationPatterns() {
    }

    @Override
    public void initButtonListeners() {
        approveButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                android.content.Intent myIntent = new android.content.Intent(v.getContext(), MapsActivity.class);
                startActivity(myIntent);
            }
        });
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
