package com.example.dominik.uberpaczka;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by marek on 10.12.2018.
 */

public class SummaryFragment extends Fragment {


    private Button cancelButton;
    private Button acceptanceButton;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_summary, container, false);
        cancelButton = (Button) view.findViewById(R.id.cancel_button);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeFragment();
            }
        });

        acceptanceButton = view.findViewById(R.id.acceptance_button);
        acceptanceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar snackbar = Snackbar
                        .make(v.getRootView(), "Order in progress", Snackbar.LENGTH_LONG);
                snackbar.show();
            }
        });

        return view;


    }


    public void closeFragment() {

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.remove(this);
        FloatingActionButton button = (FloatingActionButton) getActivity().findViewById(R.id.fab);
        fragmentTransaction.commit();
        button.show();
    }
}
