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
import com.example.dominik.uberpaczka.validators_patterns.EmailValidator;
import com.example.dominik.uberpaczka.validators_patterns.PasswordValidator;
import com.example.dominik.uberpaczka.validators_patterns.Validable;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * Created by marek on 13.01.2019.
 */

public class EmailChangeFragment extends Fragment implements Validable, RegistrationFragmentInterface {


    private Button submitButton;

    private EmailValidator emailValidator;
    private PasswordValidator passwordValidator;
    private TextInputLayout newEmailLayout;
    private TextInputLayout passwordLayout;
    private TextInputLayout repeatPasswordLayout;
    private TextView headerView;
    private TextView subheaderView;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_username_password, container, false);


        submitButton = view.findViewById(R.id.button_next_detail3);
        headerView = view.findViewById(R.id.createAcoount3);
        subheaderView = view.findViewById(R.id.username_password);


        newEmailLayout = view.findViewById(R.id.email_textinput_layout);
        passwordLayout = view.findViewById(R.id.password_textinput_layout);
        repeatPasswordLayout = view.findViewById(R.id.repeatpassword_textinput_layout);
        repeatPasswordLayout.setVisibility(View.GONE);

        newEmailLayout.setHint(null);
        newEmailLayout.setHint(getString(R.string.new_email));
        headerView.setText(getString(R.string.update_account));
        subheaderView.setText(getString(R.string.email_change_layout));


        createValidationPatterns();
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
        emailValidator = new EmailValidator(newEmailLayout, getString(R.string.error_email), getString(R.string.error_blank));
        passwordValidator = new PasswordValidator(passwordLayout, getString(R.string.error_password), getString(R.string.error_blank));

    }


    @Override
    public boolean validate() {


        return emailValidator.validate() & passwordValidator.validate();
    }

    @Override
    public void setInformation() throws Exception {

        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        AuthCredential credential = EmailAuthProvider
                .getCredential(user.getEmail(), passwordLayout.getEditText().getText().toString());

// Prompt the user to re-provide their sign-in credentials
        user.reauthenticate(credential)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            user.updateEmail(newEmailLayout.getEditText().getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(getActivity().getBaseContext(), "Updated Successfully",
                                                Toast.LENGTH_LONG).show();
                                        closeFragment();
                                    } else {
                                        Toast.makeText(getActivity().getBaseContext(), "Email not updated",
                                                Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                        } else {
                            Toast.makeText(getActivity().getBaseContext(), "Auth failed",
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                });


    }


    public void closeFragment() {
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.popBackStack();


    }
}
