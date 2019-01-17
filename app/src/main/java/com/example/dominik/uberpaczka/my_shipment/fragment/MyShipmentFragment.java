package com.example.dominik.uberpaczka.my_shipment.fragment;

/**
 * Created by marek on 15.01.2019.
 */

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dominik.uberpaczka.R;
import com.example.dominik.uberpaczka.maps.MapsActivity;
import com.example.dominik.uberpaczka.my_account.usable.CustomClickListener;
import com.example.dominik.uberpaczka.my_shipment.usable.MyShipmentAdapter;
import com.example.dominik.uberpaczka.order.usable.OrderInfo;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.LinkedList;
import java.util.List;

import static android.content.ContentValues.TAG;


public class MyShipmentFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private Toolbar toolbar;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private FirebaseFirestore db;
    private List<OrderInfo> shipmentList = new LinkedList<>();
    private ProgressBar progressBar;
    private MyShipmentAdapter myShipmentAdapter;
    private TextView errorTextView;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        db = FirebaseFirestore.getInstance();
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        Log.d(TAG, firebaseAuth.getCurrentUser().getUid());


        db.collection("packages")
                .whereEqualTo("userID", firebaseAuth.getCurrentUser().getUid())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                shipmentList.add(document.toObject(OrderInfo.class));
                            }
                            updateRecycleView();
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                            showErrorTextView();
                        }
                    }
                });


        View view = inflater.inflate(R.layout.fragment_my_shipment, container, false);
        toolbar = view.findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_arrow_back_black_24dp, null));
        progressBar = view.findViewById(R.id.myaccount_progressbar);
        errorTextView = view.findViewById(R.id.error_data_loading_textview);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeFragment();
            }
        });

        collapsingToolbarLayout = view.findViewById(R.id.collapsing_toolbar);
        collapsingToolbarLayout.setTitle(getString(R.string.my_shipment));
        mRecyclerView = view.findViewById(R.id.collapsing_listview);
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);


        hideRecycleView();
        showProgressBar();


//this line shows back button
        CustomClickListener clickListener = new CustomClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                Toast.makeText(getContext(), "Clicked", Toast.LENGTH_LONG);
            }
        };

        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        myShipmentAdapter = new MyShipmentAdapter(shipmentList, getContext(), clickListener, getString(R.string.to));
        mRecyclerView.setAdapter(myShipmentAdapter);

        return view;


    }


    public void updateRecycleView() {


        showRecycleView();
        hideProgressBar();

        myShipmentAdapter.notifyDataSetChanged();

    }

    public void showProgressBar() {
        progressBar.setVisibility(View.VISIBLE);
    }

    public void hideProgressBar() {
        progressBar.setVisibility(View.GONE);
    }

    public void showRecycleView() {
        mRecyclerView.setVisibility(View.VISIBLE);
    }

    public void hideRecycleView() {
        mRecyclerView.setVisibility(View.GONE);
    }

    public void showErrorTextView() {
        errorTextView.setVisibility(View.VISIBLE);
    }

    public void hideErrorTextView() {
        errorTextView.setVisibility(View.GONE);
    }

    public void closeFragment() {
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.popBackStack();

        ((MapsActivity) getActivity()).showPlaceAutoCompletePickUpFragment();
    }


}