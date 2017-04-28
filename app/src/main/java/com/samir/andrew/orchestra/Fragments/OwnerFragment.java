package com.samir.andrew.orchestra.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.samir.andrew.orchestra.Data.UnitDataSingleton;
import com.samir.andrew.orchestra.R;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by andre on 11-Apr-17.
 */

public class OwnerFragment extends Fragment {

    TextView englishName, arabicName, id, mail, mobile;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_owner, null);

        englishName = (TextView) v.findViewById(R.id.textViewEnglishNameFragmentOwner);
        arabicName = (TextView) v.findViewById(R.id.textViewArabicNameFragmentOwner);
        id = (TextView) v.findViewById(R.id.textViewClientIdFragmentOwner);
        mail = (TextView) v.findViewById(R.id.textViewMailFragmentOwner);
        mobile = (TextView) v.findViewById(R.id.textViewMobileFragmentOwner);

        setDataTotextViews();
        return v;
    }

    private void setDataTotextViews() {

        englishName.setText(UnitDataSingleton.getInstance().getEnglishName());
        arabicName.setText(UnitDataSingleton.getInstance().getArabicName());
        id.setText(UnitDataSingleton.getInstance().getId());
        mail.setText(UnitDataSingleton.getInstance().getMail());
        mobile.setText(UnitDataSingleton.getInstance().getMobile());

    }

}

