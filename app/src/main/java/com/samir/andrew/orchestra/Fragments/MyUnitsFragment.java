package com.samir.andrew.orchestra.Fragments;

import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.crash.FirebaseCrash;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;
import com.samir.andrew.orchestra.Activities.SplashScreen;
import com.samir.andrew.orchestra.Adapters.ExpandableListMyUnitsAdapter;
import com.samir.andrew.orchestra.Data.UnitDataSingleton;
import com.samir.andrew.orchestra.Activities.Home;
import com.samir.andrew.orchestra.R;
import com.samir.andrew.orchestra.SQLiteDatabase.DBhelper;

import org.w3c.dom.Text;

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
    LinearLayout relativeLayout;
    Dialog loadingDialog;
    TextView empty;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_my_units, null);

        // get the listview
        expListView = (ExpandableListView) v.findViewById(R.id.lvExpFragmentMyUnits);
        empty = (TextView) v.findViewById(R.id.tvEmptyMyUnits);
        relativeLayout = (LinearLayout) v.findViewById(R.id.progressLayout);

        PageListener pageListener = new PageListener();
        Home.mViewPager.setOnPageChangeListener(pageListener);

        if (Home.project != null)
            getMyUnitDetailsFromFirebase(Home.project, Home.unit);

        if (Home.fragmentPosition == 1)
            getMyUnitsFromFirebase();

        expListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {

                getMyUnitDetailsFromFirebase(listDataHeader.get(groupPosition),
                        listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition));
                return true;
            }
        });


        expListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                ExpandableListView listView = (ExpandableListView) parent;
                long pos = listView.getExpandableListPosition(position);

                // get type and correct positions
                int itemType = ExpandableListView.getPackedPositionType(pos);
                int groupPos = ExpandableListView.getPackedPositionGroup(pos);
                int childPos = ExpandableListView.getPackedPositionChild(pos);

                // if child is long-clicked
                if (itemType == ExpandableListView.PACKED_POSITION_TYPE_CHILD) {

                    dialogtoDeleteUnit(listDataHeader.get(groupPos),
                            listDataChild.get(listDataHeader.get(groupPos)).get(childPos));

                }


                return true;
            }
        });


        return v;
    }

    private void transaction(int fm) {
        // Create new fragment and transaction
        Fragment newFragment;
        if (fm == 0) {
            newFragment = new AllUnitDetails();
            Home.intoUnitDetails = true;
        } else
            newFragment = new MyUnitsFragment();

        FragmentTransaction transaction = getFragmentManager().beginTransaction();

        // Replace whatever is in the fragment_container view with this fragment,
        // and add the transaction to the back stack if needed
        transaction.replace(R.id.fragmentUnitsFragment, newFragment);

        transaction.addToBackStack(null);
        // Commit the transaction
        transaction.commit();

    }

    private void getMyUnitsFromFirebase() {

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Users/" + FirebaseAuth.getInstance().getCurrentUser().getUid()
                + "/Projects/"
        );
        myRef.keepSynced(true);

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

                    empty.setVisibility(View.GONE);
                    myChild = myChildren.iterator().next();
                    try {
                        listDataHeader.add(myChild.getKey());


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
                if (i == 0)
                    empty.setVisibility(View.VISIBLE);

                listAdapter = new ExpandableListMyUnitsAdapter(getActivity(), listDataHeader, listDataChild);

                // setting list adapter
                expListView.setAdapter(listAdapter);

                //view the result
                relativeLayout.setVisibility(View.GONE);

            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.d("error", error.toString());
                // Failed to read value
            }
        });
    }

    private void loadingdialogFunction() {

        loadingDialog = new Dialog(getActivity());
        loadingDialog.setContentView(R.layout.loading_dialog);

        loadingDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        loadingDialog.show();

    }

    private void getMyUnitDetailsFromFirebase(String project, String unit) {

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Users/" + FirebaseAuth.getInstance().getCurrentUser().getUid()
                + "/Projects/"
                + project
                + "/"
                + unit
                + "/UnitDetails"
        );


        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.

                try {
                    // owner data
                    UnitDataSingleton.getInstance().setMail(dataSnapshot.child("Email").getValue().toString());
                    UnitDataSingleton.getInstance().setEnglishName(dataSnapshot.child("ClientNameEnglish").getValue().toString());
                    UnitDataSingleton.getInstance().setArabicName(dataSnapshot.child("ClientNameArabic").getValue().toString());
                    UnitDataSingleton.getInstance().setId(dataSnapshot.child("ClientId").getValue().toString());
                    UnitDataSingleton.getInstance().setMobile(dataSnapshot.child("Mobile").getValue().toString());

                    // unit details data
                    UnitDataSingleton.getInstance().setProjectName(dataSnapshot.child("ProjectName").getValue().toString());
                    UnitDataSingleton.getInstance().setReservationDate(dataSnapshot.child("ReservationDate").getValue().toString());
                    UnitDataSingleton.getInstance().setUnitCode(dataSnapshot.child("UnitCode").getValue().toString());
                    UnitDataSingleton.getInstance().setUnitTotalPrice(dataSnapshot.child("UnitTotalPrice").getValue().toString());
                    UnitDataSingleton.getInstance().setUnitType(dataSnapshot.child("UnitType").getValue().toString());

                    // subscribe to the project and the unit code
                    FirebaseMessaging.getInstance().subscribeToTopic(dataSnapshot.child("UnitCode").getValue().toString());
                    FirebaseMessaging.getInstance().subscribeToTopic(dataSnapshot.child("ProjectName").getValue().toString().replace(' ', '_'));
                } catch (Exception e) {
                    e.printStackTrace();

                    FirebaseCrash.log("read unit data failed from firebase for UID : " + FirebaseAuth.getInstance().getCurrentUser().getUid());

                }


                try {
                    transaction(0);
                } catch (Exception e) {
                    FirebaseCrash.log("transaction for UID : " + FirebaseAuth.getInstance().getCurrentUser().getUid());

                    e.printStackTrace();
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.d("error", error.toString());
                // Failed to read value
            }
        });
    }

    private class PageListener extends ViewPager.SimpleOnPageChangeListener {
        public void onPageSelected(int position) {
            if (position == 1) {
                getMyUnitsFromFirebase();
            }

            if (Home.intoUnitDetails) {
                Home.intoUnitDetails = false;
                transaction(1);
            }
            Home.fragmentPosition = position;
        }
    }

    private void dialogtoDeleteUnit(final String projectName, final String unitCode) {
        new AlertDialog.Builder(getContext())
                .setTitle("Delete Unit")
                .setMessage("Are you sure you want to delete this Unit?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // continue with delete
                        deleteUnitFromFirebase(projectName, unitCode);
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // do nothing
                        dialog.dismiss();
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    private void deleteUnitFromFirebase(String projectName, String unitCode) {


        FirebaseMessaging.getInstance().unsubscribeFromTopic(unitCode);
        FirebaseMessaging.getInstance().unsubscribeFromTopic(projectName.replace(' ', '_'));

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Users/" + FirebaseAuth.getInstance().getCurrentUser().getUid()
                + "/Projects/"
        );
        myRef.child(projectName).child(unitCode).removeValue();

        // myDB.deleteUnitTable(unitCode);
    }

}
