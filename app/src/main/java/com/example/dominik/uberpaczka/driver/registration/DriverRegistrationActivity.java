package com.example.dominik.uberpaczka.driver.registration;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.dominik.uberpaczka.R;
import com.example.dominik.uberpaczka.driver.registration.usable.CustomDriverRegistrationAdapter;
import com.example.dominik.uberpaczka.driver.registration.usable.DriverRegistrationViewPager;


public class DriverRegistrationActivity extends AppCompatActivity {

    private DriverRegistrationViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_registration);

        viewPager = findViewById(R.id.driver_registration_viewpager);
        CustomDriverRegistrationAdapter customAdapter = new CustomDriverRegistrationAdapter(getSupportFragmentManager());
        viewPager.setAdapter(customAdapter);
    }

    public void changeFragment(int position) {
    viewPager.setCurrentItem(position);
}

    @Override
    public void onBackPressed() {
        if (viewPager.getCurrentItem() == 0) {
            super.onBackPressed();
        } else {
            viewPager.setCurrentItem(viewPager.getCurrentItem() - 1);
        }
    }
}