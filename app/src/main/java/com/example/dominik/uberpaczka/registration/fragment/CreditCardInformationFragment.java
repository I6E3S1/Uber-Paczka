package com.example.dominik.uberpaczka.registration.fragment;

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
import android.widget.TextView;

import com.example.dominik.uberpaczka.R;
import com.example.dominik.uberpaczka.registration.RegistrationActivity;
import com.example.dominik.uberpaczka.registration.usable.RegistrationFragmentInterface;
import com.example.dominik.uberpaczka.registration.usable.UserInfo;
import com.example.dominik.uberpaczka.validators_patterns.CCVValidator;
import com.example.dominik.uberpaczka.validators_patterns.CreditCardNumberValidator;
import com.example.dominik.uberpaczka.validators_patterns.Validable;

/**
 * Created by marek on 27.12.2018.
 */

public class CreditCardInformationFragment extends Fragment implements Validable, RegistrationFragmentInterface {


    private int NEXTPAGE=3;
    private Button nextButton;

    private CreditCardNumberValidator creditCardNumberValidator;
    private CCVValidator ccvValidator;

    private TextInputLayout creditCardLayout;
    private TextInputLayout ccvLayout;
    private UserInfo userInfo;
    private TextView headerView;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_creditcard_info, container, false);

        Bundle bundle = getArguments();
        if (bundle != null) {

            userInfo = (UserInfo) bundle.getSerializable("user_info");
        }


        nextButton=view.findViewById(R.id.button_next_detail2);
        creditCardLayout=view.findViewById(R.id.cardnumber_textinput_layout);
        ccvLayout=view.findViewById(R.id.ccv_textinput_layout);
        headerView = view.findViewById(R.id.createAcoount3);


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
                        Log.i("CreditCardFragment", "exception");
                    }
                }
            }
        });
    }

    @Override
    public void createValidationPatterns(){
        creditCardNumberValidator=new CreditCardNumberValidator(creditCardLayout, getString(R.string.error_card_number), getString(R.string.error_blank));
        ccvValidator=new CCVValidator(ccvLayout, getString(R.string.error_ccv), getString(R.string.error_blank));
    }

    @Override
    public boolean validate() {


        creditCardLayout.setError(null);
        ccvLayout.setError(null);

        return ccvValidator.validate()&creditCardNumberValidator.validate();
    }


    @Override
    public void setInformation() throws Exception {
        if (userInfo == null) return;

        userInfo.getCard().setCreditCardNumber(creditCardLayout.getEditText().getText().toString());
        userInfo.getCard().setCcv(ccvLayout.getEditText().getText().toString());

    }


    public Button getNextButton() {
        return nextButton;
    }

    public TextInputLayout getCreditCardLayout() {
        return creditCardLayout;
    }

    public TextInputLayout getCcvLayout() {
        return ccvLayout;
    }

    public TextView getHeaderView() {
        return headerView;
    }
}
