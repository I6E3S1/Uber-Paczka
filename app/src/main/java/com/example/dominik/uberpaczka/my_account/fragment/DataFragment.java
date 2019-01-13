package com.example.dominik.uberpaczka.my_account.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dominik.uberpaczka.R;
import com.example.dominik.uberpaczka.registration.usable.RegistrationFragmentInterface;
import com.example.dominik.uberpaczka.utils.Checker;
import com.example.dominik.uberpaczka.utils.UsernameFirestore;
import com.example.dominik.uberpaczka.validators_patterns.AbstractValidator;
import com.example.dominik.uberpaczka.validators_patterns.Validable;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

/**
 * Created by marek on 13.01.2019.
 */

public abstract class DataFragment extends Fragment implements Validable, RegistrationFragmentInterface {


    private TextView headerView;
    private TextView subheaderView;
    private TextInputLayout textInputLayout;
    private UsernameFirestore nameFirestore;
    private AbstractValidator validator;
    private Button submitButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_data_change, container, false);

        headerView = view.findViewById(R.id.header);
        subheaderView = view.findViewById(R.id.subheader);
        textInputLayout = view.findViewById(R.id.textinput_layout);
        submitButton = view.findViewById(R.id.button_data_fragment);
        headerView.setText(getString(R.string.update_account));

        setUpButtonListeners();


        return view;

    }


    @Override
    public void setUpButtonListeners() {
        submitButton.setOnClickListener(new View.OnClickListener() {
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
    public void createValidationPatterns() {

    }

    @Override
    public void setInformation() throws Exception {

        DocumentReference document = FirebaseFirestore.getInstance().collection("users")
                .document(FirebaseAuth.getInstance().getCurrentUser().getUid());

        document.update(nameFirestore.name(), textInputLayout.getEditText().getText().toString())
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(getActivity().getBaseContext(), "Updated Successfully",
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

    @Override
    public boolean validate() {
        return validator.validate();
    }


    public void setSubheaderText(String subheaderText) {
        subheaderView.setText(subheaderText);
    }

    public void setTextInputLayoutHint(String textInputLayoutHint) {
        textInputLayout.getEditText().setHint(textInputLayoutHint);
    }

    public UsernameFirestore getNameFirestore() {
        return nameFirestore;
    }

    public void setNameFirestore(UsernameFirestore nameFirestore) {
        this.nameFirestore = nameFirestore;
    }

    public AbstractValidator getValidator() {
        return validator;
    }

    public void setValidator(AbstractValidator validator) {
        this.validator = validator;
    }

    public TextInputLayout getTextInputLayout() {
        return textInputLayout;
    }

    public void setTextInputLayout(TextInputLayout textInputLayout) {
        this.textInputLayout = textInputLayout;
    }
}
