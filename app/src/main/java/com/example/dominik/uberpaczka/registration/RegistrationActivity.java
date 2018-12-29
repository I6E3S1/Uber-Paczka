package com.example.dominik.uberpaczka.registration;

import android.graphics.Rect;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

import com.example.dominik.uberpaczka.R;
import com.example.dominik.uberpaczka.registration.registration_usable.CustomRegistrationAdapter;
import com.example.dominik.uberpaczka.registration.registration_usable.CustomViewPager;
import com.example.dominik.uberpaczka.registration.registration_usable.UserInfo;

import static com.example.dominik.uberpaczka.util.ActivityUtils.hideKeyboard;

public class RegistrationActivity extends AppCompatActivity {

    private CustomViewPager viewPager;
    private UserInfo userInfo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        userInfo = new UserInfo();

        viewPager = findViewById(R.id.registration_viewpager);
        CustomRegistrationAdapter customRegistrationAdapter = new CustomRegistrationAdapter(getSupportFragmentManager(), userInfo);
        viewPager.setAdapter(customRegistrationAdapter);
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

    // hiding soft keyboard on lost textbox focus
    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (v instanceof EditText) {
                Rect outRect = new Rect();
                v.getGlobalVisibleRect(outRect);
                if (!outRect.contains((int) event.getRawX(), (int) event.getRawY())) {
                    v.clearFocus();
                    hideKeyboard(this, v);
                }
            }
        }
        return super.dispatchTouchEvent(event);
    }
}
