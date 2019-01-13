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

public class SurnameChangeFragment extends DataFragment {


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        setNameFirestore(UsernameFirestore.surname);
        setValidator(new NameValidator(getTextInputLayout(), getString(R.string.error_surname), getString(R.string.error_blank)));
        setSubheaderText(getString(R.string.surname_change_layout));
        getTextInputLayout().getEditText().setHint(getString(R.string.surname));
        getTextInputLayout().getEditText().setInputType(InputType.TYPE_CLASS_TEXT);

        return view;
    }

}
