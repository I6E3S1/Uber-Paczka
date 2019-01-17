package com.example.dominik.uberpaczka.driver.registration.usable;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.dominik.uberpaczka.driver.registration.fragment.ConfirmationFragment;
import com.example.dominik.uberpaczka.driver.registration.fragment.IntroFragment;
import com.example.dominik.uberpaczka.driver.registration.fragment.LicenseFragment;

/**
 * Created by snadev on 10.01.2019.
 */

public class CustomDriverRegistrationAdapter extends FragmentPagerAdapter {

    private static final Integer NUM_ITEMS = 3;
    private Bundle bundle;

    public CustomDriverRegistrationAdapter(FragmentManager fm) {
        super(fm);
        this.bundle = new Bundle();
    }

    @Override
    public Fragment getItem(int i) {

        Fragment fragment;

        switch (i) {
            case 0:
                fragment=new IntroFragment();
                break;
            case 1:
                fragment = new LicenseFragment();
                break;
            case 2:
                fragment = new ConfirmationFragment();
                break;
            default:
                fragment = null;
                break;
        }

        if (fragment != null) {
            fragment.setArguments(bundle);
        }

        return fragment;
    }

    @Override
    public int getCount() {
        return NUM_ITEMS;
    }
}