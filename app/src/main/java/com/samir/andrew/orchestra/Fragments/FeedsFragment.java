package com.samir.andrew.orchestra.Fragments;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.samir.andrew.orchestra.Adapters.AdsPagerAdapter;
import com.samir.andrew.orchestra.Adapters.FeedsAdapter;
import com.samir.andrew.orchestra.Data.FeedsData;
import com.samir.andrew.orchestra.Helper.CustomViewPager;
import com.samir.andrew.orchestra.R;
import com.samir.andrew.orchestra.Activities.TestTransActivity;
import com.viewpagerindicator.CirclePageIndicator;

import java.util.ArrayList;

import in.srain.cube.views.GridViewWithHeaderAndFooter;

/**
 * Created by andre on 11-Apr-17.
 */

public class FeedsFragment extends Fragment {

    private GridViewWithHeaderAndFooter gridView;

    ArrayList<FeedsData> arrayListFeedData;
    FeedsAdapter feedsAdapter;
    private CustomViewPager mViewPager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_feeds, null);

       // HelperCode.getAllUnits(getActivity());
        gridView = (GridViewWithHeaderAndFooter) v.findViewById(R.id.gridViewFeedsFragment);
        setGridViewHeaderAndFooter();


   /*     gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getActivity(), TestTransActivity.class);
                // transition
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    Bundle bundle = ActivityOptions.makeSceneTransitionAnimation(getActivity())
                            .toBundle();
                    getContext().startActivity(intent, bundle);
                }
            }
        });*/


        arrayListFeedData = new ArrayList<>();
        arrayListFeedData.add(new FeedsData("string 1"));
        arrayListFeedData.add(new FeedsData("string 2"));
        arrayListFeedData.add(new FeedsData("string 3"));
        arrayListFeedData.add(new FeedsData("string 4"));
        arrayListFeedData.add(new FeedsData("string 5"));
        arrayListFeedData.add(new FeedsData("string 6"));

        feedsAdapter = new FeedsAdapter(getActivity(), arrayListFeedData);


        // listView.setAdapter(feedsAdapter);
        gridView.setAdapter(feedsAdapter);
        //  scroll();


        return v;
    }


    private void scroll() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                mViewPager.setCurrentItem((mViewPager.getCurrentItem() + 1) % 3, true);

                scroll();

            }
        }, 3000);
    }

    private void setGridViewHeaderAndFooter() {
        LayoutInflater layoutInflater = LayoutInflater.from(getActivity());

        View headerView = layoutInflater.inflate(R.layout.test_grid_header, null, false);

        ArrayList<String> list = new ArrayList<>();
        list.add("1");
        list.add("1");
        list.add("1");

        // locate views
        mViewPager = (CustomViewPager) headerView.findViewById(R.id.viewPagerFeeds);
        mViewPager.setPagingEnabled(false);
        AdsPagerAdapter adsPagerAdapter = new AdsPagerAdapter(getActivity(), list);
        mViewPager.setAdapter(adsPagerAdapter);

        CirclePageIndicator mIndicator = (CirclePageIndicator) headerView.findViewById(R.id.indicator);
        mIndicator.setViewPager(mViewPager);

        scroll();
        gridView.addHeaderView(headerView);
    }
}
