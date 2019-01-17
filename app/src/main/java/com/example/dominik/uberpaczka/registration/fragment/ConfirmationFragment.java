package com.example.dominik.uberpaczka.registration.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.example.dominik.uberpaczka.R;
import com.example.dominik.uberpaczka.maps.MapsActivity;
import com.example.dominik.uberpaczka.registration.usable.UserInfo;
import com.example.dominik.uberpaczka.utils.Checker;
import com.example.dominik.uberpaczka.validators_patterns.Validable;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import static android.content.ContentValues.TAG;

/**
 * Created by marek on 28.12.2018.
 */

public class ConfirmationFragment extends Fragment implements Validable {

    private UserInfo userInfo;
    private Button registerButton;
    private CheckBox checkBox;
    private LinearLayout linearLayout;
    private ProgressBar progressBar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_confirm_registration, container, false);

        userInfo=(UserInfo)getArguments().getSerializable("user_info");

        registerButton=view.findViewById(R.id.creat_account_button);
        checkBox=view.findViewById(R.id.account_checkbox);
        linearLayout=view.findViewById(R.id.checkbox_layout);
        progressBar=view.findViewById(R.id.progress_bar_registration);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validate()){
                    if(Checker.checkInternetConnection(getContext(), getFragmentManager())) {
                        registerUser();
                        changeVisibiltyDuringRegistration();
                    }

                }
            }
        });

        return view;
    }


    public void registerUser() {


        FirebaseAuth mAuth;
        mAuth = FirebaseAuth.getInstance();
        mAuth.createUserWithEmailAndPassword(userInfo.getEmail(), userInfo.getPassword())
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "createUserWithEmail:success");
                            userInfo.setUserID(FirebaseAuth.getInstance().getCurrentUser().getUid());
                            sendUserInfo();

                            android.content.Intent myIntent = new android.content.Intent(getView().getRootView().getContext(), MapsActivity.class);
                            startActivity(myIntent);

                        } else {
                            //fail
                            changeVisibilityOnRegistrationFail();
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Snackbar snackbar = Snackbar
                                    .make(getView().getRootView(), getString(R.string.on_registration_fail), Snackbar.LENGTH_LONG);
                            snackbar.show();

                        }
                    }
                });

    }

    public void sendUserInfo() {

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("users").document(userInfo.getUserID())
                .set(userInfo.getUserInfo())
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.w(TAG, "DocumentSnapshot succesfully added");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document", e);
                    }
                });
    }

    @Override
    public boolean validate() {

        checkBox.setError(null);
        if(checkBox.isChecked())
            return true;
        else{
            checkBox.setError(getString(R.string.error_terms_checkbox));
            return false;
        }

    }

    public void changeVisibiltyDuringRegistration(){

        linearLayout.setVisibility(View.GONE);
        registerButton.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
    }

    public void changeVisibilityOnRegistrationFail(){
        linearLayout.setVisibility(View.VISIBLE);
        registerButton.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);
    }
}
