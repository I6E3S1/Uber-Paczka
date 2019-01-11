package com.example.dominik.uberpaczka.registration.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.dominik.uberpaczka.R;

/**
 * Created by marek on 29.12.2018.
 */

public class NoInternetConnectrionDialogFragment extends DialogFragment {


    private Button understandButton;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_nointernet_connection, container, false);
        understandButton=view.findViewById(R.id.check_connection_button);

        understandButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeFragment();
            }
        });

        return view;

    }

    public void closeFragment(){
        this.dismiss();
    }
}
