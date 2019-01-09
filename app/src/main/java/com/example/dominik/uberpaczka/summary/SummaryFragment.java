package com.example.dominik.uberpaczka.summary;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.dominik.uberpaczka.R;
import com.example.dominik.uberpaczka.util.DistanceLoader;

import java.util.HashMap;


/**
 * Created by marek on 10.12.2018.
 * frontend
 */

public class SummaryFragment extends Fragment implements LoaderManager.LoaderCallbacks<Long> {

    private Button cancelButton;
    private Button acceptanceButton;
    private Button grandeButton;
    private TextView from;
    private TextView to;
    private String fromString;
    private String destinationString;
    private HashMap<Integer, String> hash = new HashMap<>();

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        Bundle bundle = getArguments();
        fromString = bundle.getString("from");
        destinationString = bundle.getString("destination");
//        Log.i("Fragment From", fromString);
//        Log.i("Fragment Destination", destinationString);


        final View view = inflater.inflate(R.layout.fragment_summary, container, false);
        from = view.findViewById(R.id.from);
        to = view.findViewById(R.id.destination);
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

        grandeButton = view.findViewById(R.id.grande);
        grandeButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                getLoader();

            }
        });

        return view;


    }





    public void closeFragment() {

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.remove(this);
        fragmentTransaction.commit();
        FloatingActionButton button = (FloatingActionButton) getActivity().findViewById(R.id.fab);
        button.show();

    }


    @NonNull
    @Override
    public Loader<Long> onCreateLoader(int i, @Nullable Bundle bundle) {

        Context context=getContext();
        if(context!=null) {
            return new DistanceLoader(context, fromString, destinationString);
        }

        return null;
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Long> loader, Long aLong) {

        Log.i("Loader", "Finished working");
        updateUI(aLong);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Long> loader) {
        updateUI(Long.valueOf(0));
    }


    public void updateUI(Long aLong){
        TextView textView=getView().findViewById(R.id.price);
        textView.setText(aLong.toString());
    }


    public void getLoader(){
        if (getActivity().getSupportLoaderManager().getLoader(1) == null)
            getActivity().getSupportLoaderManager().initLoader(1, null, this);
        else
            getActivity().getSupportLoaderManager().restartLoader(1, null, this);
    }
}
