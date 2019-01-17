package com.example.dominik.uberpaczka.my_account.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
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
import com.example.dominik.uberpaczka.maps.usable.MapsInterface;
import com.example.dominik.uberpaczka.my_account.usable.CustomClickListener;
import com.example.dominik.uberpaczka.my_account.usable.MyAccountAdapter;
import com.example.dominik.uberpaczka.my_account.usable.UserData;
import com.example.dominik.uberpaczka.registration.usable.UserInfo;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

/**
 * Created by marek on 04.01.2019.
 */

public class MyAccountFragment extends Fragment {


    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private Toolbar toolbar;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private FirebaseFirestore db;
    private UserInfo userInfo;
    private List<UserData> list;
    private ProgressBar progressBar;
    private MyAccountAdapter myAccountAdapter;
    private TextView errorTextView;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        db = FirebaseFirestore.getInstance();
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        Log.d(TAG, firebaseAuth.getCurrentUser().getUid());
        DocumentReference docRef = db.collection("users").document(firebaseAuth.getCurrentUser().getUid());
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {

            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                        userInfo = document.toObject(UserInfo.class);
                        updateRecycleView();

                        Log.d(TAG, userInfo.getName());
                    } else {
                        Log.d(TAG, "No such document");
                        showErrorTextView();
                        hideProgressBar();
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                    showErrorTextView();
                    hideProgressBar();
                }

            }

        });


        View view = inflater.inflate(R.layout.fragment_myaccount, container, false);
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
        collapsingToolbarLayout.setTitle(getString(R.string.my_account));
        mRecyclerView = view.findViewById(R.id.collapsing_listview);
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);


        hideRecycleView();
        showProgressBar();


//this line shows back button

        list = new ArrayList<>();

        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        CustomClickListener clickListener = new CustomClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                Toast.makeText(getContext(), list.get(position).getDescription(), Toast.LENGTH_LONG).show();
                swapFragment(position);


            }
        };
        myAccountAdapter = new MyAccountAdapter(list, getContext(), clickListener);
        mRecyclerView.setAdapter(myAccountAdapter);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));

        return view;


    }


    public void updateRecycleView() {

        FirebaseAuth auth = FirebaseAuth.getInstance();

        StringBuilder adressStringBuilder = new StringBuilder()
                .append(userInfo.getAdress().getFlat())
                .append(" ")
                .append(userInfo.getAdress().getStreet())
                .append(", ")
                .append(userInfo.getAdress().getCity());

        list.clear();
        list.add(new UserData(getString(R.string.name), userInfo.getName(), new NameChangeFragment()));
        list.add(new UserData(getString(R.string.surname), userInfo.getSurname(), new SurnameChangeFragment()));
        list.add(new UserData(getString(R.string.phone), userInfo.getPhone(), new PhoneChangeFragment()));
        list.add(new UserData(getString(R.string.email), auth.getCurrentUser().getEmail(), new EmailChangeFragment()));
        list.add(new UserData(getString(R.string.password), "**************", new PasswordChangeFragment()));
        list.add(new UserData(getString(R.string.card), "***********" + userInfo.getCard().getCreditCardNumber().substring(11, 16), new CardChangedFragment()));
        list.add(new UserData(getString(R.string.adress), adressStringBuilder.toString(), new AdressChangedFragment()));
        showRecycleView();
        hideProgressBar();

        myAccountAdapter.notifyDataSetChanged();

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
        fragmentManager.beginTransaction()
//                        .setCustomAnimations(R.anim, R.anim.push_up_out)
                .remove(this)
                .commit();

        ((MapsActivity) getActivity()).showPlaceAutoCompletePickUpFragment();
    }

    public void swapFragment(int position) {
        getFragmentManager().beginTransaction()
                .replace(R.id.flContent, list.get(position).getFragment())
                .addToBackStack(null)
                .commit();
    }

    public interface MyAccountFragmentCallback extends MapsInterface {

    }


}
