package com.example.dominik.uberpaczka.registration.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.dominik.uberpaczka.R;
import com.example.dominik.uberpaczka.registration.RegistrationActivity;
import com.example.dominik.uberpaczka.registration.usable.RegistrationFragmentInterface;
import com.example.dominik.uberpaczka.registration.usable.UserInfo;
import com.example.dominik.uberpaczka.validators_patterns.EmailValidator;
import com.example.dominik.uberpaczka.validators_patterns.PasswordValidator;
import com.example.dominik.uberpaczka.validators_patterns.Validable;

/**
 * Created by marek on 28.12.2018.
 */

public class UsernamePasswordFragment extends Fragment implements Validable, RegistrationFragmentInterface {



    private int NEXTPAGE=4;
    private Button nextButton;

    private PasswordValidator passwordValidator;
    private EmailValidator emailValidator;

    private TextInputLayout passwordLayout;
    private TextInputLayout emailLayout;
    private TextInputLayout repeatPasswordLayout;

    private UserInfo userInfo;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_username_password, container, false);

        userInfo=(UserInfo)getArguments().getSerializable("user_info");

        nextButton=view.findViewById(R.id.button_next_detail3);
        passwordLayout =view.findViewById(R.id.password_textinput_layout);
        emailLayout =view.findViewById(R.id.email_textinput_layout);
        repeatPasswordLayout =view.findViewById(R.id.repeatpassword_textinput_layout);

        createValidationPatterns();
        setUpButtonListeners();

        return view;
    }


    @Override
    public void setUpButtonListeners(){
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validate()) {
                    try {
                        setInformation();
                        ((RegistrationActivity) getActivity()).changeFragment(NEXTPAGE);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            }
        });
    }

    @Override
    public void createValidationPatterns(){
        passwordValidator =new PasswordValidator(passwordLayout, getString(R.string.error_password), getString(R.string.error_blank));
        emailValidator =new EmailValidator(repeatPasswordLayout, getString(R.string.error_password), getString(R.string.error_blank));
    }


    @Override
    public boolean validate() {

        passwordLayout.setError(null);
        repeatPasswordLayout.setError(null);
        emailLayout.setError(null);

        boolean passwordMatches=false;
        String password=passwordLayout.getEditText().getText().toString();
        String repeatPassword=repeatPasswordLayout.getEditText().getText().toString();

        passwordMatches=password.equals(repeatPassword);

        if(!passwordMatches){
            repeatPasswordLayout.setError(getString(R.string.error_repeat_password));
        }

        return passwordValidator.validate()&passwordValidator.validate()&passwordMatches;
    }

    @Override
    public void setInformation() throws Exception {
        if (userInfo == null) return;

        userInfo.setPassword(passwordLayout.getEditText().getText().toString());
        userInfo.setEmail(emailLayout.getEditText().getText().toString());


    }
}
