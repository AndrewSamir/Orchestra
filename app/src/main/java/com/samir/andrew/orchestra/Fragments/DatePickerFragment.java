package com.samir.andrew.orchestra.Fragments;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;

import com.samir.andrew.orchestra.Activities.Profile;
import com.samir.andrew.orchestra.Activities.Register;
import com.samir.andrew.orchestra.Activities.SplashScreen;

import java.util.Calendar;

/**
 * Created by andre on 26-Apr-17.
 */

public class DatePickerFragment extends DialogFragment
        implements DatePickerDialog.OnDateSetListener {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        // Create a new instance of DatePickerDialog and return it
        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {

        int printedMonth = month + 1;
        // Do something with the date chosen by the user

        if (SplashScreen.fromDate == 0)
            Register.birthdate.setText(day + "/" + printedMonth + "/" + year);
        else if (SplashScreen.fromDate == 1)
            Profile.etbirthdate.setText(day + "/" + printedMonth + "/" + year);

    }
}