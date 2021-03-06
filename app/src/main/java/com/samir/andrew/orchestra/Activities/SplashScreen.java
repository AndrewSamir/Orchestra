package com.samir.andrew.orchestra.Activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.crashlytics.android.Crashlytics;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.crash.FirebaseCrash;
import com.google.firebase.database.FirebaseDatabase;
import com.samir.andrew.orchestra.R;
import io.fabric.sdk.android.Fabric;

public class SplashScreen extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    public static String path;

    public static int fromDate;
    static boolean calledAlready = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
      //  setContentView(R.layout.activity_splash_screen);


        if (!calledAlready) {
            FirebaseDatabase.getInstance().setPersistenceEnabled(true);
            calledAlready = true;
        }

        mAuth = FirebaseAuth.getInstance();

        // to send crash repost to firebase
        // FirebaseCrash.report(new Exception("Splash Screen"));

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    startActivity(new Intent(SplashScreen.this, Home.class));
                } else {
                    // User is signed out
                    if (false) {
                        // to work without auth on emulator

                        path = "Users/GPaLxYkiaVPROyvqFQ9hUr8HmTt1";

                        startActivity(new Intent(SplashScreen.this, Home.class));

                    } else {
                        // work on real device

                        startActivity(new Intent(SplashScreen.this, LogIn.class));

                    }

                }
            }
        };

    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

}

