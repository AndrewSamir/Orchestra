package com.samir.andrew.orchestra.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.samir.andrew.orchestra.R;

public class Profile extends AppCompatActivity {

    TextView name, mail, mobile, birthdate;
    RelativeLayout relativeLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initiateViews();

        getProfileDataFromFirebase();

    }

    private void initiateViews() {

        name = (TextView) findViewById(R.id.textViewNameProfile);
        mail = (TextView) findViewById(R.id.textViewMailProfile);
        mobile = (TextView) findViewById(R.id.textViewMobileProfile);
        birthdate = (TextView) findViewById(R.id.textViewBirthdateProfile);
        relativeLayout = (RelativeLayout) findViewById(R.id.relativeLayoutProfile);

    }

    private void getProfileDataFromFirebase() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Users/" + FirebaseAuth.getInstance().getCurrentUser().getUid()
                + "/UserProfile");


        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                name.setText(dataSnapshot.child("displayName").getValue().toString());
                mail.setText(dataSnapshot.child("email").getValue().toString());
                mobile.setText(dataSnapshot.child("mobileNumber").getValue().toString());
                birthdate.setText(dataSnapshot.child("birthDate").getValue().toString());
                relativeLayout.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
            }
        });
    }
}
