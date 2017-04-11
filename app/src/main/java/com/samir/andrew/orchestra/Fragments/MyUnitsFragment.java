package com.samir.andrew.orchestra.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.samir.andrew.orchestra.Adapters.ExpandableListMyUnitsAdapter;
import com.samir.andrew.orchestra.Home;
import com.samir.andrew.orchestra.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by andre on 11-Apr-17.
 */

public class MyUnitsFragment extends Fragment {

    ExpandableListMyUnitsAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;
    DataSnapshot myChild, myChildList;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_my_units, null);

        // get the listview
        expListView = (ExpandableListView) v.findViewById(R.id.lvExpFragmentMyUnits);

        // preparing list data
        //   prepareListData();
        getDataFromFirebase();

        expListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                Toast.makeText(getActivity(), "Correct", Toast.LENGTH_SHORT).show();
                transaction();
                return true;
            }
        });

        return v;
    }

    private void prepareListData() {
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();

        // Adding child data
        listDataHeader.add("Top 250");
        listDataHeader.add("Now Showing");
        listDataHeader.add("Coming Soon..");

        // Adding child data
        List<String> top250 = new ArrayList<String>();
        top250.add("The Shawshank Redemption");
        top250.add("The Godfather");
        top250.add("The Godfather: Part II");
        top250.add("Pulp Fiction");
        top250.add("The Good, the Bad and the Ugly");
        top250.add("The Dark Knight");
        top250.add("12 Angry Men");

        List<String> nowShowing = new ArrayList<String>();
        nowShowing.add("The Conjuring");
        nowShowing.add("Despicable Me 2");
        nowShowing.add("Turbo");
        nowShowing.add("Grown Ups 2");
        nowShowing.add("Red 2");
        nowShowing.add("The Wolverine");

        List<String> comingSoon = new ArrayList<String>();
        comingSoon.add("2 Guns");
        comingSoon.add("The Smurfs 2");
        comingSoon.add("The Spectacular Now");
        comingSoon.add("The Canyons");
        comingSoon.add("Europa Report");

        listDataChild.put(listDataHeader.get(0), top250); // Header, Child data
        listDataChild.put(listDataHeader.get(1), nowShowing);
        listDataChild.put(listDataHeader.get(2), comingSoon);
    }

    private void transaction() {
        // Create new fragment and transaction
        Fragment newFragment = new AllUnitDetails();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();

        // Replace whatever is in the fragment_container view with this fragment,
        // and add the transaction to the back stack if needed
        transaction.replace(R.id.fragmentUnitsFragment, newFragment);
        transaction.addToBackStack(null);

        // Commit the transaction
        transaction.commit();

    }


    private void getDataFromFirebase() {

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Users/"
                + "GPaLxYkiaVPROyvqFQ9hUr8HmTt1" +
                "/Projects/"
        );

        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
//                String value = dataSnapshot.getValue(String.class);

                Iterable<DataSnapshot> myChildren = dataSnapshot.getChildren();

                listDataHeader = new ArrayList<String>();
                listDataChild = new HashMap<String, List<String>>();
                int i = 0;
                while (myChildren.iterator().hasNext()) {

                    myChild = myChildren.iterator().next();
                    try {
                        listDataHeader.add(myChild.getKey());

                        Log.d("child", myChild.getChildren().toString());

                        Iterable<DataSnapshot> myChildrenList = myChild.getChildren();


                        List<String> stringArrayList = new ArrayList<String>();


                        while (myChildrenList.iterator().hasNext()) {
                            myChildList = myChildrenList.iterator().next();
                            stringArrayList.add(myChildList.getKey());

                        }
                        listDataChild.put(listDataHeader.get(i), stringArrayList); // Header, Child data


                    } catch (Exception e) {
                    }
                    i++;
                }

                listAdapter = new ExpandableListMyUnitsAdapter(getActivity(), listDataHeader, listDataChild);

                // setting list adapter
                expListView.setAdapter(listAdapter);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.d("error", error.toString());
                // Failed to read value
            }
        });
    }
}
