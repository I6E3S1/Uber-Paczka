package com.example.dominik.uberpaczka.registration.registration_fragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

import java.util.Calendar;

/**
 * Created by marek on 27.12.2018.
 */

public class DatePickerFragment extends DialogFragment {


    private DatePickerDialog.OnDateSetListener callback;

    public void onDateSet(DatePickerDialog.OnDateSetListener callback){
        this.callback=callback;
    }
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker
        final Calendar c = Calendar.getInstance();
        c.setFirstDayOfWeek(Calendar.MONDAY);
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);


        // Create a new instance of DatePickerDialog and return it
        return new DatePickerDialog(getActivity(), callback , year, month, day);
    }




}