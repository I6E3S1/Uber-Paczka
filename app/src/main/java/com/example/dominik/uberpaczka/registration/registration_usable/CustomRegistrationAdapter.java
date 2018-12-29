package com.example.dominik.uberpaczka.registration.registration_usable;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.dominik.uberpaczka.registration.registration_fragment.BasicInformationFragment;
import com.example.dominik.uberpaczka.registration.registration_fragment.ConfirmationFragment;
import com.example.dominik.uberpaczka.registration.registration_fragment.UsernamePasswordFragment;
import com.example.dominik.uberpaczka.registration.registration_fragment.PostalAdressFragment;
import com.example.dominik.uberpaczka.registration.registration_fragment.CreditCardInformationFragment;

/**
 * Created by marek on 27.12.2018.
 */

public class CustomRegistrationAdapter extends FragmentPagerAdapter {

    private int NUM_ITEMS=5;
    private UserInfo userInfo;
    private Bundle bundle;

    public CustomRegistrationAdapter(FragmentManager fm, UserInfo userInfo) {
        super(fm);
        this.userInfo=userInfo;
        this.bundle=new Bundle();
        bundle.putSerializable("user_info", userInfo);
    }

    @Override
    public Fragment getItem(int i) {

        Fragment fragment;


        switch (i){
            case 0:
                fragment=new BasicInformationFragment();
                break;
            case 1:
                fragment=new PostalAdressFragment();
                break;
            case 2:
                fragment=new CreditCardInformationFragment();
                break;
            case 3:
                fragment=new UsernamePasswordFragment();
                break;
            case 4:
                fragment=new ConfirmationFragment();
                break;
            default:
                fragment=null;
                break;
        }

        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    public int getCount() {
        return NUM_ITEMS;
    }



}
