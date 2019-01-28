package com.example.dominik.uberpaczka.driver.orders.fragment;

/**
 * Created by snadev on 17.01.2019.
 */

import android.app.AlertDialog;
import android.content.DialogInterface;
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
import com.example.dominik.uberpaczka.driver.orders.usable.DriverOrdersAdapter;
import com.example.dominik.uberpaczka.driver.orders.usable.SimpleSectionedRecyclerViewAdapter;
import com.example.dominik.uberpaczka.maps.MapsActivity;
import com.example.dominik.uberpaczka.my_account.usable.CustomClickListener;
import com.example.dominik.uberpaczka.order.usable.OrderInfo;
import com.example.dominik.uberpaczka.order.usable.PackageStatus;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static android.content.ContentValues.TAG;

//TODO: cleanup
public class DriverOrdersFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private SimpleSectionedRecyclerViewAdapter mSectionedAdapter;
    private Toolbar toolbar;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private FirebaseFirestore db;
    private ProgressBar progressBar;
    private DriverOrdersAdapter driverOrdersAdapter;
    private TextView errorTextView;

    private List<OrderInfo> myOrders = new LinkedList<>();
    private List<OrderInfo> otherOrders = new LinkedList<>();
    private List<OrderInfo> allOrders = new LinkedList<>();

    private FirebaseUser currentUser;


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        db = FirebaseFirestore.getInstance();
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        currentUser = firebaseAuth.getCurrentUser();
        Log.d(TAG, currentUser.getUid());

        loadOrders();

        View view = inflater.inflate(R.layout.fragment_driver_orders, container, false);
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
        collapsingToolbarLayout.setTitle(getString(R.string.driver_orders));
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
                OrderInfo selectedOrder = allOrders.get(mSectionedAdapter.sectionedPositionToPosition(position));
                showChangeStatusDialog(selectedOrder);
            }
        };

        Log.i(TAG, "onCreateView: " + allOrders.toString());

        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        driverOrdersAdapter = new DriverOrdersAdapter(allOrders, getContext(), clickListener, getString(R.string.to));

        //Add your adapter to the sectionAdapter
        mSectionedAdapter = new SimpleSectionedRecyclerViewAdapter(this.getContext(), R.layout.section, R.id.section_text, driverOrdersAdapter);

        mRecyclerView.setAdapter(mSectionedAdapter);

        return view;
    }
    private void setSections() {
        //This is the code to provide a sectioned list
        List<SimpleSectionedRecyclerViewAdapter.Section> sections = new ArrayList<>();
        SimpleSectionedRecyclerViewAdapter.Section[] dummy = new SimpleSectionedRecyclerViewAdapter.Section[sections.size()];

        //Sections
        sections.add(new SimpleSectionedRecyclerViewAdapter.Section(0, getString(R.string.accepted_orders)));
        sections.add(new SimpleSectionedRecyclerViewAdapter.Section(myOrders.size(), getString(R.string.available_orders)));
        mSectionedAdapter.setSections(sections.toArray(dummy));
    }

    private void loadOrders() {
        //TODO: temporary dirty filter for specific status and driver id
        db.collection("packages")
                //.whereEqualTo("packageStatus", PackageStatus.driver_is_coming.name())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                OrderInfo orderInfo = document.toObject(OrderInfo.class);
                                if(!orderInfo.getPackageStatus().equals(PackageStatus.waiting_for_driver)
                                        && orderInfo.getDriverID().equals(currentUser.getUid()) && !orderInfo.getUserID().equals(currentUser.getUid())) {
                                    orderInfo.setId(document.getId());
                                    myOrders.add(orderInfo);
                                }
                            }
                            loadOtherOrders();
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                            showErrorTextView();
                        }
                    }
                });
    }

    private void loadOtherOrders() {
        db.collection("packages")
                .whereEqualTo("packageStatus", PackageStatus.waiting_for_driver.name())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "Other orders: ");
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                OrderInfo orderInfo = document.toObject(OrderInfo.class);
                                orderInfo.setId(document.getId());
                                if(!orderInfo.getUserID().equals(currentUser.getUid())) {
                                    otherOrders.add(orderInfo);
                                }
                            }
                            allOrders.addAll(myOrders);
                            allOrders.addAll(otherOrders);
                            Log.d(TAG, "My orders: " + myOrders.toString() + ", other orders: " + otherOrders.toString());
                            updateRecycleView();
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                            showErrorTextView();
                        }
                    }
                });
    }


    private void updateOrder(OrderInfo orderInfo) {
        db.collection("packages").document(orderInfo.getId())
                .update("driverID", orderInfo.getDriverID(), "packageStatus",
                        orderInfo.getPackageStatus().name())
                .addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(getContext(), R.string.common_updated_successfully,
                        Toast.LENGTH_LONG).show();
                closeFragment();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getContext(), R.string.common_error_updating,
                        Toast.LENGTH_LONG).show();
            }
        });
    }

    private void showChangeStatusDialog(final OrderInfo selectedOrder) {
        AlertDialog.Builder pictureDialog = new AlertDialog.Builder(getActivity());
        pictureDialog.setTitle(getString(R.string.change_package_status));
        String[] pictureDialogItems = {
                getString(R.string.driver_is_coming),
                getString(R.string.package_on_the_way),
                getString(R.string.package_delivered),};

        pictureDialog.setItems(pictureDialogItems,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                selectedOrder.setPackageStatus(PackageStatus.driver_is_coming);
                                selectedOrder.setDriverID(FirebaseAuth.getInstance().getCurrentUser().getUid());
                                updateOrder(selectedOrder);
                                break;
                            case 1:
                                selectedOrder.setPackageStatus(PackageStatus.package_on_the_way);
                                selectedOrder.setDriverID(FirebaseAuth.getInstance().getCurrentUser().getUid());
                                updateOrder(selectedOrder);
                                break;
                            case 2:
                                selectedOrder.setPackageStatus(PackageStatus.package_delivered);
                                selectedOrder.setDriverID(FirebaseAuth.getInstance().getCurrentUser().getUid());
                                updateOrder(selectedOrder);
                                break;
                        }
                    }
                });
        pictureDialog.show();
    }


    public void updateRecycleView() {
        showRecycleView();
        hideProgressBar();
        setSections();

        driverOrdersAdapter.notifyDataSetChanged();
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