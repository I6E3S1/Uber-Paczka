package com.example.dominik.uberpaczka.driver.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.example.dominik.uberpaczka.R;
import com.example.dominik.uberpaczka.maps.MapsActivity;
import com.example.dominik.uberpaczka.utils.Checker;
import com.example.dominik.uberpaczka.validators_patterns.Validable;

/**
 * Created by snadev on 10.01.2019.
 */

public class ConfirmationFragment extends Fragment implements Validable {

    private Button applyButton;
    private CheckBox checkBox;
    private LinearLayout linearLayout;
    private ProgressBar progressBar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_driver_registration_confirm, container, false);

        applyButton = view.findViewById(R.id.driver_apply_button);
        checkBox = view.findViewById(R.id.driver_terms_checkbox);
        linearLayout = view.findViewById(R.id.checkbox_layout);
        progressBar = view.findViewById(R.id.driver_registration_progressbar);

        applyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validate()) {
                    if (Checker.checkInternetConnection(getContext(), getFragmentManager())) {
                        applyUser();
                        changeVisibilityDuringRegistration();
                    }
                }
            }
        });

        return view;
    }


    public void applyUser() {
        //TODO
        android.content.Intent myIntent = new android.content.Intent(getView().getRootView().getContext(), MapsActivity.class);
        startActivity(myIntent);
    }

    public void setUserDriver() {
        //TODO
        //FirebaseFirestore db = FirebaseFirestore.getInstance();
    }

    @Override
    public boolean validate() {
        checkBox.setError(null);
        if (checkBox.isChecked())
            return true;
        else {
            checkBox.setError(getString(R.string.error_terms_checkbox));
            return false;
        }

    }

    public void changeVisibilityDuringRegistration() {
        linearLayout.setVisibility(View.GONE);
        applyButton.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
    }

    public void changeVisibilityOnRegistrationFail() {
        linearLayout.setVisibility(View.VISIBLE);
        applyButton.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);
    }
}