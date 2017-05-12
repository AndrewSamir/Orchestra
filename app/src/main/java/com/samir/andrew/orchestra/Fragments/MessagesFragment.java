package com.samir.andrew.orchestra.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.samir.andrew.orchestra.Adapters.NotificationMessagesAdapter;
import com.samir.andrew.orchestra.Data.NotificationModel;
import com.samir.andrew.orchestra.R;

import java.util.ArrayList;

/**
 * Created by andre on 11-Apr-17.
 */

public class MessagesFragment extends Fragment {

    ArrayList<NotificationModel> notificationModelArrayList;

    NotificationMessagesAdapter teetArrayAdapter;
    ListView listView;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_messages, container, false);
        listView = (ListView) v.findViewById(R.id.lvMessagesFragment);
        getNotificationData();
        return v;
    }

    private void getNotificationData() {

        notificationModelArrayList = new ArrayList<>();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Users/" + FirebaseAuth.getInstance().getCurrentUser().getUid()
                + "/Notifications/"
        );
        myRef.keepSynced(true);

        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
//                String value = dataSnapshot.getValue(String.class);
                notificationModelArrayList = new ArrayList<>();

                Iterable<DataSnapshot> myChildren = dataSnapshot.getChildren();

                while (myChildren.iterator().hasNext()) {

                    DataSnapshot myChild = myChildren.iterator().next();

                    notificationModelArrayList.add(new NotificationModel(myChild.child("text").getValue().toString(),

                            myChild.child("time").getValue().toString()));
                }

                teetArrayAdapter = new NotificationMessagesAdapter(getActivity(), R.layout.item_notification_message, notificationModelArrayList);
                listView.setAdapter(teetArrayAdapter);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.d("error", error.toString());
                // Failed to read value
            }
        });
    }

}
