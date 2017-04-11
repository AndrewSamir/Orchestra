package com.samir.andrew.orchestra.Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.samir.andrew.orchestra.Fragments.FeedsFragment;
import com.samir.andrew.orchestra.Fragments.FragmentUnitsFragments;
import com.samir.andrew.orchestra.Fragments.MessagesFragment;
import com.samir.andrew.orchestra.Fragments.MyUnitsFragment;


public class HomePagerAdapter extends FragmentPagerAdapter {

    public HomePagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        switch (i){
            case 0:
                return new FeedsFragment();
            case 1:
                return new FragmentUnitsFragments();
            case 2:
                return new MessagesFragment();
           /* case 3:
                return new ContactFragment();*/

        }
        return null;
    }

    @Override
    public int getCount() {
        return 3;
    }


}
