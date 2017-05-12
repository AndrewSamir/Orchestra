package com.samir.andrew.orchestra.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.samir.andrew.orchestra.Adapters.NewFeedsAdapter;
import com.samir.andrew.orchestra.Adapters.PaymentAdapter;
import com.samir.andrew.orchestra.Data.FeedsData;
import com.samir.andrew.orchestra.Data.PaymentData;
import com.samir.andrew.orchestra.Data.UnitDataSingleton;
import com.samir.andrew.orchestra.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by andre on 11-Apr-17.
 */

public class AccountingFragment extends Fragment {

    private List<PaymentData> paymentDataList = new ArrayList<>();
    private RecyclerView recyclerView;
    private PaymentAdapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_accounting, null);


        recyclerView = (RecyclerView) v.findViewById(R.id.recyclerviewFragmentAccounting);

        mAdapter = new PaymentAdapter(paymentDataList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        preparePaymentData();

        return v;
    }


    private void preparePaymentData() {


        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Users/"
                + FirebaseAuth.getInstance().getCurrentUser().getUid()
                + "/Projects/"
                + UnitDataSingleton.getInstance().getProjectName()
                + "/"
                + UnitDataSingleton.getInstance().getUnitCode()
                + "/UnitPayment/");
        myRef.keepSynced(true);

        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
//                String value = dataSnapshot.getValue(String.class);
                paymentDataList = new ArrayList<>();

                Iterable<DataSnapshot> myChildren = dataSnapshot.getChildren();

                while (myChildren.iterator().hasNext()) {

                    DataSnapshot myChild = myChildren.iterator().next();

                    int unitclientcode = 0;
                    String paymentTypeName = "-";
                    int installmentAmount = 0;
                    String dueDate = "-";
                    boolean paid = false;
                    int paidAmount = 0;
                    String dateOfPayment = "-";

                    if (myChild.hasChild("unitclientcode"))
                        unitclientcode = Integer.parseInt(myChild.child("unitclientcode").getValue().toString());

                    if (myChild.hasChild("PaymentTypeName"))
                        paymentTypeName = myChild.child("PaymentTypeName").getValue().toString();

                    if (myChild.hasChild("InstallmentAmount"))
                        installmentAmount = Integer.parseInt(myChild.child("InstallmentAmount").getValue().toString());

                    if (myChild.hasChild("DueDate"))
                        dueDate = myChild.child("DueDate").getValue().toString();

                    if (myChild.hasChild("Paid"))
                        paid = (myChild.child("Paid").getValue().toString().equals("true"));

                    if (myChild.hasChild("PaidAmount"))
                        paidAmount = Integer.parseInt(myChild.child("PaidAmount").getValue().toString());

                    if (myChild.hasChild("DateOfPayment"))
                        dateOfPayment = myChild.child("DateOfPayment").getValue().toString();

                    paymentDataList.add(new PaymentData(unitclientcode,
                            paymentTypeName,
                            installmentAmount,
                            dueDate,
                            paid,
                            paidAmount,
                            dateOfPayment));
                }
                mAdapter = new PaymentAdapter(paymentDataList);
                Log.d("test", " test");

                recyclerView.setAdapter(mAdapter);
                //  scroll();

            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.d("error", error.toString());
                // Failed to read value
            }
        });

        /////////////////////////////////////////////////////////////////////////


    }
}

