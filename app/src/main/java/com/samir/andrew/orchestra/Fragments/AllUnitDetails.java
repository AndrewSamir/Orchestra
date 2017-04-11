package com.samir.andrew.orchestra.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.samir.andrew.orchestra.R;

/**
 * Created by andre on 11-Apr-17.
 */

public class AllUnitDetails extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_unit_all_details, null);
        transaction(0);

        BottomNavigationView navigation = (BottomNavigationView) v.findViewById(R.id.navigationFragmentUnitAllDetails);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        return v;
    }

    private void transaction(int fragment) {

        Fragment newFragment;
        // Create new fragment and transaction
        if (fragment == 0)
            newFragment = new OwnerFragment();
        else if (fragment == 1)
            newFragment = new UnitDetailsFragment();
        else
            newFragment = new AccountingFragment();

        FragmentTransaction transaction = getFragmentManager().beginTransaction();

        // Replace whatever is in the fragment_container view with this fragment,
        // and add the transaction to the back stack if needed
        transaction.replace(R.id.framLayoutFragmentUnitAllDetails, newFragment);
        transaction.addToBackStack(null);

        // Commit the transaction
        transaction.commit();

    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    transaction(0);
                    return true;
                case R.id.navigation_dashboard:
                    transaction(1);
                    return true;
                case R.id.navigation_notifications:
                    transaction(2);
                    return true;
            }
            return false;
        }

    };

}
