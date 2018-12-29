package com.example.dominik.uberpaczka.registration.registration_fragment;

import android.app.DatePickerDialog;
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
import android.widget.DatePicker;
import android.widget.EditText;

import com.example.dominik.uberpaczka.R;
import com.example.dominik.uberpaczka.registration.registration_usable.UserInfo;
import com.example.dominik.uberpaczka.registration.RegistrationActivity;
import com.example.dominik.uberpaczka.registration.registration_usable.RegistrationFragmentInterface;
import com.example.dominik.uberpaczka.validators_patterns.NameValidator;
import com.example.dominik.uberpaczka.validators_patterns.PhoneValidator;
import com.example.dominik.uberpaczka.validators_patterns.SurnameValidator;

import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;

/**
 * Created by marek on 27.12.2018.
 */

    public class BasicInformationFragment extends Fragment implements DatePickerDialog.OnDateSetListener, RegistrationFragmentInterface {

    private final int NEXT_PAGE=1;
    private Button nextButton;

    private NameValidator nameValidator;
    private PhoneValidator phoneValidator;
    private SurnameValidator surnameValidator;

    private EditText birthdayView;
    private TextInputLayout nameInputLayout;
    private TextInputLayout surnameInputLayout;
    private TextInputLayout phoneInputLayout;
    private TextInputLayout dateInputLayout;
    private String date;

    private UserInfo userInfo;

    private DatePickerFragment dateDialog;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_basic_information, container, false);


        userInfo=(UserInfo)getArguments().getSerializable("user_info");

        nameInputLayout=view.findViewById(R.id.name_textinput_layout);
        surnameInputLayout=view.findViewById(R.id.surname_textinput_layout);
        phoneInputLayout=view.findViewById(R.id.phonenumber_textinput_layout);
        dateInputLayout=view.findViewById(R.id.birthday_textinput_layout);

        birthdayView=view.findViewById(R.id.birthday_edittext_layout);

        nextButton=view.findViewById(R.id.button_next_detail);
        dateDialog=new DatePickerFragment();
        dateDialog.onDateSet(this);

        initValidationPatterns();
        initButtonListeners();

        return view;
    }

    /**
     * seting up listners for button used in fragments
     */
    @Override
    public void initButtonListeners(){
        birthdayView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dateDialog.show(getFragmentManager(), "DatePicker");
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validate()) {
                    try {
                        setInformation();
                        ((RegistrationActivity) getActivity()).changeFragment(NEXT_PAGE);
                    } catch (Exception e) {
                       Log.i("BasicInformation", e.getMessage());
                    }

                }
            }
        });
    }


    /**
     * creating reegex pattern used for validating information from this fragment
     */
    @Override
    public void initValidationPatterns(){
        nameValidator=new NameValidator(nameInputLayout, getString(R.string.error_name), getString(R.string.error_blank));
        surnameValidator=new SurnameValidator(surnameInputLayout, getString(R.string.error_surname), getString(R.string.error_blank));
        phoneValidator=new PhoneValidator(phoneInputLayout, getString(R.string.error_phone_number), getString(R.string.error_blank));
    }


    /**
     * Validating information
     * @return
     */

    @Override
    public boolean validate() {

        nameInputLayout.setError(null);
        surnameInputLayout.setError(null);
        phoneInputLayout.setError(null);
        dateInputLayout.setError(null);

        boolean dateValidation=false;
        dateValidation=dateInputLayout.getEditText().getText().toString().length()!=0;

        if(!dateValidation){
            dateInputLayout.setError(getString(R.string.error_blank));
        }

        return nameValidator.validate()&surnameValidator.validate()&phoneValidator.validate()&dateValidation;

    }


    @Override
    public void setInformation() throws Exception {
        if (userInfo == null) return;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");


        userInfo.setName(nameInputLayout.getEditText().getText().toString());
        userInfo.setSurname(surnameInputLayout.getEditText().getText().toString());
        userInfo.setPhone(phoneInputLayout.getEditText().getText().toString());
        userInfo.setDate(date);

    }


    /**
     * returning information about choosen date from calendar
     * @param view
     * @param year
     * @param month
     * @param dayOfMonth
     */

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("dd MMMM yyyy");
        SimpleDateFormat simpleDateFormat1=new SimpleDateFormat("dd/MM/yyyy");

        if(birthdayView!=null) {
            birthdayView.setText(simpleDateFormat.format(new GregorianCalendar(year, month, dayOfMonth).getTime()));
            date=simpleDateFormat1.format(new GregorianCalendar(year, month, dayOfMonth).getTime());
        }


    }


}
