package com.samir.andrew.orchestra.Adapters;

import android.app.Activity;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;


import com.samir.andrew.orchestra.R;

import java.util.ArrayList;

/**
 * Created by andre on 26-Mar-17.
 */

public class AdsPagerAdapter extends PagerAdapter {

    Activity activity;
    ArrayList<String> list;
    LayoutInflater inflater;

    int[] mResources = {
            R.drawable.p1,
            R.drawable.p2,
            R.drawable.p3
    };

    public AdsPagerAdapter(Activity activity, ArrayList<String> list) {
        this.activity = activity;
        this.list = list;
        inflater=activity.getLayoutInflater();
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((LinearLayout) object);
    }
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View itemView = inflater.inflate(R.layout.pager_item, container, false);

        ImageView imageView = (ImageView) itemView.findViewById(R.id.imageView4);
        imageView.setImageResource(mResources[position]);

        container.addView(itemView);

        return itemView;
    }
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout) object);
    }
}
