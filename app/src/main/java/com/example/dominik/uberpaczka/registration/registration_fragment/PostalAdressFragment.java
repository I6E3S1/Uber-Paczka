package com.example.dominik.uberpaczka.registration.registration_fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.dominik.uberpaczka.R;
import com.example.dominik.uberpaczka.registration.registration_usable.UserInfo;
import com.example.dominik.uberpaczka.registration.RegistrationActivity;
import com.example.dominik.uberpaczka.registration.registration_usable.RegistrationFragmentInterface;
import com.example.dominik.uberpaczka.validators_patterns.CityValidator;
import com.example.dominik.uberpaczka.validators_patterns.FlatValidator;
import com.example.dominik.uberpaczka.validators_patterns.StreetValidator;
import com.example.dominik.uberpaczka.validators_patterns.Validate;

/**
 * Created by marek on 27.12.2018.
 */

public class PostalAdressFragment extends Fragment implements Validate, RegistrationFragmentInterface {


    private final int NEXT_PAGE=2;
    private Button nextButton;

    private FlatValidator flatValidator;
    private StreetValidator streetValidator;
    private CityValidator cityValidator;

    private TextInputLayout flatInputLayout;
    private TextInputLayout streetInputLayout;
    private TextInputLayout cityInputLayout;

    private UserInfo userInfo;




    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_postaladress, container, false);

        userInfo=(UserInfo)getArguments().getSerializable("user_info");

        flatInputLayout=view.findViewById(R.id.flat_textinput_layout);
        streetInputLayout=view.findViewById(R.id.street_textinput_layout);
        cityInputLayout=view.findViewById(R.id.city_textinput_layout);

        nextButton=view.findViewById(R.id.button_next_detail1);


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
                        ((RegistrationActivity) getActivity()).changeFragment(NEXT_PAGE);
                    } catch (Exception e) {
                        Log.i("PostalAdressFragment", "exception");
                    }

                }
            }
        });
    }

    @Override
    public void createValidationPatterns(){
        flatValidator=new FlatValidator(flatInputLayout, getString(R.string.error_flat), getString(R.string.error_blank));
        streetValidator=new StreetValidator(streetInputLayout, getString(R.string.error_street), getString(R.string.error_blank));
        cityValidator=new CityValidator(cityInputLayout, getString(R.string.error_city), getString(R.string.error_blank));
    }

    @Override
    public boolean validate() {


        flatInputLayout.setError(null);
        streetInputLayout.setError(null);
        cityInputLayout.setError(null);


       return flatValidator.validate()&streetValidator.validate()&cityValidator.validate();
    }

    @Override
    public void setInformation() throws Exception {
        if (userInfo == null) return;

        userInfo.setFlat(flatInputLayout.getEditText().getText().toString());
        userInfo.setStreet(streetInputLayout.getEditText().getText().toString());
        userInfo.setCity(cityInputLayout.getEditText().getText().toString());

    }
}
