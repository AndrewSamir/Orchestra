package com.samir.andrew.orchestra.Activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.samir.andrew.orchestra.Data.RegisterationData;
import com.samir.andrew.orchestra.Fragments.DatePickerFragment;
import com.samir.andrew.orchestra.R;
import com.sdsmdg.tastytoast.TastyToast;

public class Profile extends AppCompatActivity {

    Toolbar toolbar;
    private Menu menu;

    TextView name, mail, mobile, birthdate;
    RelativeLayout relativeLayout;
    LinearLayout linearLayoutEditProfile;

    EditText etname, etmail, etphone, etpassword, etconfirmPassword;
    Button register;
   public static TextView etsignin, etbirthdate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
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
        linearLayoutEditProfile = (LinearLayout) findViewById(R.id.llEditProfile);


        // edit profile

        TextView tx = (TextView) findViewById(R.id.textViewOrchestraRegister);
        tx.setVisibility(View.GONE);

        etname = (EditText) findViewById(R.id.editTextNameRegister);
        etmail = (EditText) findViewById(R.id.editTextMailRegister);
        etphone = (EditText) findViewById(R.id.editTextPhoneRegister);
        etbirthdate = (TextView) findViewById(R.id.editTextBirthdate);
        etpassword = (EditText) findViewById(R.id.editTextPasswordRegister);
        etconfirmPassword = (EditText) findViewById(R.id.editTextConfirmPAsswordRegister);
        register = (Button) findViewById(R.id.buttonRegister);
        etsignin = (TextView) findViewById(R.id.textViewHaveAccountRegister);

        etpassword.setVisibility(View.GONE);
        etconfirmPassword.setVisibility(View.GONE);
        etsignin.setVisibility(View.GONE);
        register.setVisibility(View.GONE);

        etmail.setClickable(false);
        etmail.setFocusable(false);
        etmail.setAlpha(0.5f);


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

                etname.setText(dataSnapshot.child("displayName").getValue().toString());
                etmail.setText(dataSnapshot.child("email").getValue().toString());
                etphone.setText(dataSnapshot.child("mobileNumber").getValue().toString());
                etbirthdate.setText(dataSnapshot.child("birthDate").getValue().toString());

                relativeLayout.setVisibility(View.GONE);
                linearLayoutEditProfile.setVisibility(View.GONE);

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.menu = menu;
        getMenuInflater().inflate(R.menu.profile, menu);
        hideOption(R.id.action_edit_profile_Done);
        hideOption(R.id.action_edit_profile_cancel);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_edit_profile_edit:
                toolbar.setTitle("Edit Profile");
                showOption(R.id.action_edit_profile_Done);
                showOption(R.id.action_edit_profile_cancel);
                hideOption(R.id.action_edit_profile_edit);

                linearLayoutEditProfile.setVisibility(View.VISIBLE);

                return true;
            case R.id.action_edit_profile_Done:
                toolbar.setTitle("Profile");

                if (validateForm()) {
                    relativeLayout.setVisibility(View.VISIBLE);
                    editProfimeOnFireBase();
                }
                return true;
            case R.id.action_edit_profile_cancel:
                toolbar.setTitle("Profile");
                hideOption(R.id.action_edit_profile_Done);
                hideOption(R.id.action_edit_profile_cancel);
                showOption(R.id.action_edit_profile_edit);

                linearLayoutEditProfile.setVisibility(View.GONE);

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void showDatePickerDialog(View v) {
        SplashScreen.fromDate = 1;
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    private void hideOption(int id) {
        MenuItem item = menu.findItem(id);
        item.setVisible(false);
    }

    private void showOption(int id) {
        MenuItem item = menu.findItem(id);
        item.setVisible(true);
    }

    private void editProfimeOnFireBase() {

        final RegisterationData registerationData = new RegisterationData();
        registerationData.setDisplayName(etname.getText().toString());
        registerationData.setMail(etmail.getText().toString());
        registerationData.setMobile(etphone.getText().toString());
        registerationData.setBirthDate(etbirthdate.getText().toString());


        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Users/" + FirebaseAuth.getInstance().getCurrentUser().getUid() + "/UserProfile");

        myRef.setValue(registerationData, new DatabaseReference.CompletionListener() {
            public void onComplete(DatabaseError error, DatabaseReference ref) {
                if (error != null) {
                    Log.d("ERROR", error.toString());
                    TastyToast.makeText(getApplicationContext(), "Please Try Later", TastyToast.LENGTH_LONG, TastyToast.ERROR);

                    relativeLayout.setVisibility(View.GONE);
                } else {
                    hideOption(R.id.action_edit_profile_Done);
                    hideOption(R.id.action_edit_profile_cancel);
                    showOption(R.id.action_edit_profile_edit);
                    getProfileDataFromFirebase();
                }
            }
        });


    }

    private boolean validateForm() {

        Boolean valid = true;
        EditText editText = null;


        if (etbirthdate.getText().toString().length() == 0) {
            etbirthdate.setError("Please Enter Your Birthdate");
            etbirthdate.requestFocus();
        }

        if (etphone.getText().toString().length() == 0) {
            etphone.setError("Please Enter Your Mobile Number");
            etphone.requestFocus();
            valid = false;

            editText = etphone;
        } else if (etphone.getText().toString().length() != 11) {
            etphone.setError("Please Enter a Valid Mobile Number");
            etphone.requestFocus();
            valid = false;

            editText = etphone;
        } else {
            int number;
            try {
                number = Integer.parseInt(etphone.getText().toString());
            } catch (NumberFormatException e) {
                //error
                etphone.setError("Please Enter only Numbers");
                etphone.requestFocus();
                valid = false;

                editText = etphone;
            }
        }


        if (etname.getText().toString().length() == 0) {
            etname.setError("Please Enter Your Name");
            etname.requestFocus();
            valid = false;

            editText = etname;
        }

        if (!valid) {
            try {
                openKeyboard(editText);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return valid;
    }

    private void openKeyboard(EditText editText) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT);
    }
}
