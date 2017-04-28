package com.samir.andrew.orchestra.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.samir.andrew.orchestra.Data.UnitDataSingleton;
import com.samir.andrew.orchestra.R;

/**
 * Created by andre on 11-Apr-17.
 */

public class UnitDetailsFragment extends Fragment {

    TextView projectName, reservationDate, unitCode, unitTotalPrice, unitType;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_unit_details, null);

        projectName = (TextView) v.findViewById(R.id.textViewProjectNameFragmentUnitDetails);
        reservationDate = (TextView) v.findViewById(R.id.textViewReservationDateFragmentUnitDetails);
        unitCode = (TextView) v.findViewById(R.id.textViewUnitCodeFragmentUnitDetails);
        unitTotalPrice = (TextView) v.findViewById(R.id.textViewUnitTotalPriceFragmentUnitDetails);
        unitType = (TextView) v.findViewById(R.id.textViewUnitTypeFragmentUnitDetails);

        setDataTotextViews();

        return v;
    }

    private void setDataTotextViews() {

        projectName.setText(UnitDataSingleton.getInstance().getProjectName());
        reservationDate.setText(UnitDataSingleton.getInstance().getReservationDate());
        unitCode.setText(UnitDataSingleton.getInstance().getUnitCode());
        unitTotalPrice.setText(UnitDataSingleton.getInstance().getUnitTotalPrice());
        unitType.setText(UnitDataSingleton.getInstance().getUnitType());

    }
}

