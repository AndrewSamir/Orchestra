package com.samir.andrew.orchestra.Fragments;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.samir.andrew.orchestra.Adapters.ChatMsgAdapter;
import com.samir.andrew.orchestra.Data.ChatMSGtoFirebase;
import com.samir.andrew.orchestra.Data.ChatMessageModel;
import com.samir.andrew.orchestra.Data.UnitDataSingleton;
import com.samir.andrew.orchestra.R;
import com.samir.andrew.orchestra.SQLiteDatabase.DBhelper;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by andre on 05-May-17.
 */

public class ChatUnitFragment extends Fragment {

    DBhelper myDB;
    EditText etMsg;
    de.hdodenhof.circleimageview.CircleImageView btnSend;
    RecyclerView recyclerView;
    boolean dataSent = false;
    LinearLayoutManager mLayoutManager;

    ArrayList<ChatMessageModel> chatMessageModels = new ArrayList<>();

    ChatMsgAdapter chatMsgAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_chat_unit, null);

        myDB = new DBhelper(getContext());


        //    myDB.deleteUnitTable(UnitDataSingleton.getInstance().getUnitCode().replace('-', '_'));

        myDB.createNewTable(UnitDataSingleton.getInstance().getUnitCode().replace('-', '_'));

        etMsg = (EditText) v.findViewById(R.id.etFragmentChatUnitMsg);
        btnSend = (de.hdodenhof.circleimageview.CircleImageView) v.findViewById(R.id.ivSendMsg);
        recyclerView = (RecyclerView) v.findViewById(R.id.chatRecyclerView);

        setMessages();

        chatMsgAdapter = new ChatMsgAdapter(chatMessageModels);
        mLayoutManager = new LinearLayoutManager(this.getActivity());
        mLayoutManager.setStackFromEnd(true);
        recyclerView.scrollToPosition(chatMessageModels.size() - 1);

        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(chatMsgAdapter);

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etMsg.getText().toString().trim().length() != 0)
                    addnewMSG();
            }
        });

        getNewMsg();
        return v;
    }

    private void getNewMsg() {


        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Users/" +
                FirebaseAuth.getInstance().getCurrentUser().getUid()
                + "/Projects/" +
                UnitDataSingleton.getInstance().getProjectName() +
                "/" + UnitDataSingleton.getInstance().getUnitCode() +
                "/ChatMessages/"
        );
        myRef.keepSynced(true);


        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {


                // Cursor c = myDB.getAllMSgs(UnitDataSingleton.getInstance().getUnitCode().replace('-', '_'));

                if (chatMessageModels.size() == 0) {
                    String BODY = dataSnapshot.child("body").getValue().toString();
                    String DELIVERED = dataSnapshot.child("delivered").getValue().toString();
                    String SEEN = dataSnapshot.child("seen").getValue().toString();
                    String SENDER = dataSnapshot.child("sender").getValue().toString();
                    String TIME = dataSnapshot.child("time").getValue().toString();


                    chatMessageModels.add(new ChatMessageModel(dataSnapshot.getKey(),
                            BODY,
                            (Integer.parseInt(DELIVERED) == 1),
                            (Integer.parseInt(SEEN) == 1),
                            (Integer.parseInt(SENDER) == 1),
                            TIME));

                    chatMsgAdapter.notifyItemInserted(chatMessageModels.size() - 1);
                    mLayoutManager.setStackFromEnd(true);
                    recyclerView.scrollToPosition(chatMessageModels.size() - 1);

                    addMessageToDatabase(dataSnapshot.getKey(), BODY, DELIVERED, SEEN, SENDER, TIME);


                } else {
                    if (chatMessageModels.get(chatMessageModels.size() - 1).getKEY().equals(s)) {

                        String BODY = dataSnapshot.child("body").getValue().toString();
                        String DELIVERED = dataSnapshot.child("delivered").getValue().toString();
                        String SEEN = dataSnapshot.child("seen").getValue().toString();
                        String SENDER = dataSnapshot.child("sender").getValue().toString();
                        String TIME = dataSnapshot.child("time").getValue().toString();


                        chatMessageModels.add(new ChatMessageModel(dataSnapshot.getKey(),
                                BODY,
                                (Integer.parseInt(DELIVERED) == 1),
                                (Integer.parseInt(SEEN) == 1),
                                (Integer.parseInt(SENDER) == 1),
                                TIME));

                        chatMsgAdapter.notifyItemInserted(chatMessageModels.size() - 1);
                        mLayoutManager.setStackFromEnd(true);
                        recyclerView.scrollToPosition(chatMessageModels.size() - 1);

                        addMessageToDatabase(dataSnapshot.getKey(), BODY, DELIVERED, SEEN, SENDER, TIME);


                    }

                }

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });
    }

    private void addMessageToDatabase(String key, String msg, String deliverd, String seen, String sender, String time) {
        myDB.ADD_NEW_MSG(key,
                msg,
                Integer.parseInt(deliverd),
                Integer.parseInt(seen),
                Integer.parseInt(sender),
                time,
                UnitDataSingleton.getInstance().getUnitCode().replace('-', '_'));
    }

    private void setMessages() {
        chatMessageModels = new ArrayList<>();

        Cursor c = myDB.getAllMSgs(UnitDataSingleton.getInstance().getUnitCode().replace('-', '_'));
        if (c.moveToFirst()) {
//            np_eftkad.clear();

            do {

                String KEY = c.getString(0);
                String BODY = c.getString(1);
                Boolean DELIVERED = (c.getInt(2) == 1);
                Boolean SEEN = (c.getInt(3) == 1);
                Boolean SENDER = (c.getInt(4) == 1);
                String TIME = c.getString(5);


                chatMessageModels.add(new ChatMessageModel(KEY, BODY, DELIVERED, SEEN, SENDER, TIME));

            } while (c.moveToNext());

        }

    }

    private void addnewMSG() {

        String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());
        Log.d("currentDateTimeString", currentDateTimeString);

        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Users/" +
                FirebaseAuth.getInstance().getCurrentUser().getUid() +
                "/Projects/" +
                UnitDataSingleton.getInstance().getProjectName() +
                "/" +
                UnitDataSingleton.getInstance().getUnitCode() +
                "/ChatMessages/");
        String key = myRef.push().getKey();

        ChatMSGtoFirebase chatMessageModel = new ChatMSGtoFirebase();
        chatMessageModel.setBODY(etMsg.getText().toString());
        chatMessageModel.setDELIVERED("0");
        chatMessageModel.setSEEN("0");
        chatMessageModel.setSENDER("1");
        chatMessageModel.setTIME(currentDateTimeString);
        myRef.child(key).setValue(chatMessageModel);


        chatMessageModels.add(new ChatMessageModel(key,
                etMsg.getText().toString(),
                false,
                false,
                true,
                currentDateTimeString));

        dataSent = true;

        chatMsgAdapter.notifyItemInserted(chatMessageModels.size() - 1);
        mLayoutManager.setStackFromEnd(true);
        recyclerView.scrollToPosition(chatMessageModels.size() - 1);

        addMessageToDatabase(key, etMsg.getText().toString(), "0", "0", "1", currentDateTimeString);


        etMsg.setText("");
    }
}
