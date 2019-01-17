package com.example.dominik.uberpaczka.my_account.fragment;

import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.Toast;

import com.example.dominik.uberpaczka.R;
import com.example.dominik.uberpaczka.registration.fragment.PostalAdressFragment;
import com.example.dominik.uberpaczka.registration.usable.AdressInfo;
import com.example.dominik.uberpaczka.utils.Checker;
import com.example.dominik.uberpaczka.utils.UsernameFirestore;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

/**
 * Created by marek on 13.01.2019.
 */

public class AdressChangedFragment extends PostalAdressFragment {


    @Override
    public void setUpButtonListeners() {

        getHeaderView().setText(getString(R.string.update_account));
        getNextButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Checker.checkInternetConnection(getContext(), getFragmentManager())) {
                    if (validate()) {
                        try {
                            setInformation();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                }
            }
        });
    }

    @Override
    public void setInformation() throws Exception {


        AdressInfo adressInfo = new AdressInfo();
        adressInfo.setCity(getCityInputLayout().getEditText().getText().toString());
        adressInfo.setStreet(getStreetInputLayout().getEditText().getText().toString());
        adressInfo.setFlat(getFlatInputLayout().getEditText().getText().toString());

        DocumentReference adress = FirebaseFirestore.getInstance().collection("users")
                .document(FirebaseAuth.getInstance().getCurrentUser().getUid());
        adress.update(UsernameFirestore.adress.name(), adressInfo.getAdressMap())
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(getContext(), "Updated Successfully",
                                Toast.LENGTH_LONG).show();
                        closeFragment();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getContext(), "Error updating",
                                Toast.LENGTH_LONG).show();
                    }
                });
    }


    public void closeFragment() {
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.popBackStack();


    }
}
