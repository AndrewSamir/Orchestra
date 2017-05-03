package com.samir.andrew.orchestra.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.samir.andrew.orchestra.Adapters.PaymentAdapter;
import com.samir.andrew.orchestra.Data.PaymentData;
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
        PaymentData paymentData = new PaymentData(64, "Reservation Fees", 0, "Mar 12 2017 12:00AM", true, 10000, "Mar 12 2017 12:00AM");
        paymentDataList.add(paymentData);

        paymentData = new PaymentData(64, "Reservation Fees", 0, "Mar 12 2017 12:00AM", true, 10000, "Mar 12 2017 12:00AM");
        paymentDataList.add(paymentData);

        paymentData = new PaymentData(64, "Reservation Fees", 0, "Mar 12 2017 12:00AM", true, 10000, "Mar 12 2017 12:00AM");
        paymentDataList.add(paymentData);

        mAdapter.notifyDataSetChanged();
    }
}

