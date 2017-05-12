package com.samir.andrew.orchestra.Fragments;

import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.samir.andrew.orchestra.Adapters.AdsPagerAdapter;
import com.samir.andrew.orchestra.Adapters.NewFeedsAdapter;
import com.samir.andrew.orchestra.Data.FeedsData;
import com.samir.andrew.orchestra.Helper.CustomViewPager;
import com.samir.andrew.orchestra.R;
import com.viewpagerindicator.CirclePageIndicator;

import java.util.ArrayList;

/**
 * Created by andre on 11-Apr-17.
 */

public class FeedsFragment extends Fragment {

    private RecyclerView gridView;

    ArrayList<FeedsData> arrayListFeedData;
    NewFeedsAdapter feedsAdapter;
    private CustomViewPager mViewPager;
    CirclePageIndicator mIndicator;
    ArrayList<String> listImages;
    LinearLayout prgressLayout;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_feeds, null);

        // HelperCode.getAllUnits(getActivity());
        gridView = (RecyclerView) v.findViewById(R.id.gridViewFeedsFragment);

        mViewPager = (CustomViewPager) v.findViewById(R.id.viewPagerFeeds);
        mIndicator = (CirclePageIndicator) v.findViewById(R.id.indicator);
        prgressLayout = (LinearLayout) v.findViewById(R.id.progressLayout);

        setSlidingImages();


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

        getAdsDataFromFirebase();

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

    private void setSlidingImages() {


        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("SlidingImages/");
        myRef.keepSynced(true);

        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
//                String value = dataSnapshot.getValue(String.class);
                listImages = new ArrayList<>();

                Iterable<DataSnapshot> myChildren = dataSnapshot.getChildren();

                while (myChildren.iterator().hasNext()) {

                    DataSnapshot myChild = myChildren.iterator().next();

                    listImages.add(myChild.getValue().toString());
                }

                mViewPager.setPagingEnabled(false);
                AdsPagerAdapter adsPagerAdapter = new AdsPagerAdapter(getActivity(), listImages);
                mViewPager.setAdapter(adsPagerAdapter);

                mIndicator.setViewPager(mViewPager);

                scroll();

            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.d("error", error.toString());
                // Failed to read value
            }
        });


    }

    private void getAdsDataFromFirebase() {

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("advertises/");
        myRef.keepSynced(true);

        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
//                String value = dataSnapshot.getValue(String.class);
                arrayListFeedData = new ArrayList<>();

                Iterable<DataSnapshot> myChildren = dataSnapshot.getChildren();

                while (myChildren.iterator().hasNext()) {

                    DataSnapshot myChild = myChildren.iterator().next();

                    arrayListFeedData.add(new FeedsData(myChild.child("body").getValue().toString(),
                            myChild.child("date").getValue().toString(),
                            myChild.child("image").getValue().toString(),
                            myChild.child("title").getValue().toString()));
                }


                feedsAdapter = new NewFeedsAdapter(arrayListFeedData, getActivity());

                RecyclerView.LayoutManager mLayoutManager;

                //  mRecycler.hideProgress();
                if (isTablet()) {
                    mLayoutManager = new GridLayoutManager(getContext(), 3);

                } else {
                    mLayoutManager = new GridLayoutManager(getContext(), 2);
                }
                gridView.setLayoutManager(mLayoutManager);

                prgressLayout.setVisibility(View.GONE);
                // listView.setAdapter(feedsAdapter);
                gridView.setAdapter(feedsAdapter);
                //  scroll();

            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.d("error", error.toString());
                // Failed to read value
            }
        });
    }

    private boolean isTablet() {
        return (getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK)
                >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }
}

