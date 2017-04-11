package com.samir.andrew.orchestra.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.samir.andrew.orchestra.Adapters.ExpandableListMyUnitsAdapter;
import com.samir.andrew.orchestra.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by andre on 11-Apr-17.
 */

public class MessagesFragment extends Fragment {




    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_messages, container,false);



        return v;
    }


}
