package com.samir.andrew.orchestra.Fragments;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.samir.andrew.orchestra.Activities.Home;
import com.samir.andrew.orchestra.R;

import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEvent;
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEventListener;

/**
 * Created by andre on 11-Apr-17.
 */

public class AllUnitDetails extends Fragment {

    FrameLayout frameLayout;
    LinearLayout.LayoutParams lp;
    float px;

     BottomNavigationView navigation;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_unit_all_details, null);
        frameLayout = (FrameLayout) v.findViewById(R.id.framLayoutFragmentUnitAllDetails);

        Resources r = getResources();
        px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 15, r.getDisplayMetrics());
        lp = new LinearLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);


       navigation = (BottomNavigationView) v.findViewById(R.id.navigationFragmentUnitAllDetails);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        if (Home.project != null) {
            View view = navigation.findViewById(R.id.navigation_Chat);
            view.performClick();
          //  transaction(3);
        }
        else
            transaction(0);
        KeyboardVisibilityEvent.setEventListener(
                getActivity(),
                new KeyboardVisibilityEventListener() {
                    @Override
                    public void onVisibilityChanged(boolean isOpen) {
                        // some code depending on keyboard visiblity status
                        if (isOpen)
                            navigation.setVisibility(View.GONE);
                        else
                            navigation.setVisibility(View.VISIBLE);

                    }
                });

        return v;
    }


    private void transaction(int fragment) {

        Fragment newFragment;
        // Create new fragment and transaction
        if (fragment == 0) {
            newFragment = new OwnerFragment();
            lp.setMargins((int) px, (int) px, (int) px, (int) px);
            frameLayout.setLayoutParams(lp);
        } else if (fragment == 1) {
            newFragment = new UnitDetailsFragment();
            lp.setMargins((int) px, (int) px, (int) px, (int) px);
            frameLayout.setLayoutParams(lp);
        } else if (fragment == 2) {
            newFragment = new AccountingFragment();
            lp.setMargins((int) px, (int) px, (int) px, (int) px);
            frameLayout.setLayoutParams(lp);
        } else if (fragment == 3) {
            Home.project = null;
            Home.unit = null;
            newFragment = new ChatUnitFragment();
            lp.setMargins(0, 0, 0, 0);
            frameLayout.setLayoutParams(lp);
        } else {
            newFragment = new OwnerFragment();
            lp.setMargins((int) px, (int) px, (int) px, (int) px);
            frameLayout.setLayoutParams(lp);
        }


        FragmentTransaction transaction = getFragmentManager().beginTransaction();

        // Replace whatever is in the fragment_container view with this fragment,
        // and add the transaction to the back stack if needed
        transaction.replace(R.id.framLayoutFragmentUnitAllDetails, newFragment);
        //transaction.addToBackStack(null);
        //transaction.disallowAddToBackStack();

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
                case R.id.navigation_Chat:
                    transaction(3);
                    return true;
            }
            return false;
        }

    };

}
