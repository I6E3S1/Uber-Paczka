package com.example.dominik.uberpaczka.my_account.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.dominik.uberpaczka.R;
import com.example.dominik.uberpaczka.utils.UsernameFirestore;
import com.example.dominik.uberpaczka.validators_patterns.NameValidator;

/**
 * Created by marek on 13.01.2019.
 */

public class PhoneChangeFragment extends DataFragment {


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        setNameFirestore(UsernameFirestore.phone);
        setValidator(new NameValidator(getTextInputLayout(), getString(R.string.error_phone_number), getString(R.string.error_blank)));
        setSubheaderText(getString(R.string.phone_change_layout));
        getTextInputLayout().getEditText().setHint(getString(R.string.phone));
        getTextInputLayout().getEditText().setInputType(InputType.TYPE_CLASS_PHONE);

        return view;
    }
}
