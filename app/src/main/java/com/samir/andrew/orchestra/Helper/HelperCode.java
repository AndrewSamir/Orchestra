package com.samir.andrew.orchestra.Helper;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;

import com.samir.andrew.orchestra.Fragments.AllUnitDetails;
import com.samir.andrew.orchestra.R;

/**
 * Created by andre on 22-Mar-17.
 */

public class HelperCode {

    public static  void getAllUnits(FragmentActivity activity){
      //  Home.detilasBoolean = false;
        // Create new fragment and transaction
        Fragment newFragment = new AllUnitDetails();
        FragmentTransaction transaction = activity.getSupportFragmentManager().beginTransaction();

        // Replace whatever is in the fragment_container view with this fragment,
        // and add the transaction to the back stack if needed
       // transaction.replace(R.id.fragmentTestTT, newFragment);
        transaction.addToBackStack(null);

        // Commit the transaction
        transaction.commit();
    }

}
