package com.samir.andrew.orchestra.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.samir.andrew.orchestra.R;

import java.util.zip.Inflater;

/**
 * Created by andre on 11-Apr-17.
 */

public class FragmentUnitsFragments extends Fragment {


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_units_fragment, null);

        transaction();
        return v;
    }



    private void transaction() {
        // Create new fragment and transaction
        Fragment newFragment = new MyUnitsFragment();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();

        // Replace whatever is in the fragment_container view with this fragment,
        // and add the transaction to the back stack if needed
        transaction.replace(R.id.fragmentUnitsFragment, newFragment);
        transaction.addToBackStack(null);

        // Commit the transaction
        transaction.commit();

    }
}
