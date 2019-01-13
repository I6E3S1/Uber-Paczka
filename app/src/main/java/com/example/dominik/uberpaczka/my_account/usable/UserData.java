package com.example.dominik.uberpaczka.my_account.usable;

import android.support.v4.app.Fragment;

/**
 * Created by marek on 06.01.2019.
 */

public class UserData {

    String description;
    String value;
    Fragment fragment;


    public UserData(String description, String value) {
        this.description = description;
        this.value = value;
    }


    public UserData(String description, String value, Fragment fragment) {
        this.description = description;
        this.value = value;
        this.fragment = fragment;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Fragment getFragment() {
        return fragment;
    }
}
