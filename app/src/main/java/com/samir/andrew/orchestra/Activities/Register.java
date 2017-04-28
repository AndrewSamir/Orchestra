package com.samir.andrew.orchestra.Activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.samir.andrew.orchestra.Data.RegisterationData;
import com.samir.andrew.orchestra.Fragments.DatePickerFragment;
import com.samir.andrew.orchestra.R;
import com.sdsmdg.tastytoast.TastyToast;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Register extends AppCompatActivity {

    EditText name, mail, phone, password, confirmPassword;
    Button register;
    public static TextView signin, birthdate;
    RelativeLayout relativeLayout;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();


        TextView tx = (TextView) findViewById(R.id.textViewOrchestraRegister);
        Typeface custom_font = Typeface.createFromAsset(getAssets(), "oleo_script.ttf");
        tx.setTypeface(custom_font);

        relativeLayout = (RelativeLayout) findViewById(R.id.progressbarRegister);
        relativeLayout.setVisibility(View.GONE);

        name = (EditText) findViewById(R.id.editTextNameRegister);
        mail = (EditText) findViewById(R.id.editTextMailRegister);
        phone = (EditText) findViewById(R.id.editTextPhoneRegister);
        birthdate = (TextView) findViewById(R.id.editTextBirthdate);
        password = (EditText) findViewById(R.id.editTextPasswordRegister);
        confirmPassword = (EditText) findViewById(R.id.editTextConfirmPAsswordRegister);
        register = (Button) findViewById(R.id.buttonRegister);
        signin = (TextView) findViewById(R.id.textViewHaveAccountRegister);


        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goToSignin = new Intent(Register.this, LogIn.class);
                startActivity(goToSignin);
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (validateForm()) {

                    relativeLayout.setVisibility(View.VISIBLE);
                    createAccount(mail.getText().toString(), password.getText().toString());

                }
            }
        });
    }

    private void openKeyboard(EditText editText) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT);
    }

    private boolean validateForm() {

        Boolean valid = true;
        EditText editText = null;

        if (confirmPassword.getText().toString().length() == 0) {
            confirmPassword.setError("Please Confirm Your Password");
            confirmPassword.requestFocus();
            valid = false;
            editText = confirmPassword;
        } else if (!confirmPassword.getText().toString().equals(password.getText().toString())) {
            confirmPassword.requestFocus();
            valid = false;

            editText = confirmPassword;
            confirmPassword.setText("");
            confirmPassword.setError("Please Enter The Password Again");
        }

        if (password.getText().toString().length() == 0) {
            password.setError("Please Enter Your Password");
            password.requestFocus();
            valid = false;

            editText = password;
        } else if (password.getText().toString().length() < 6) {
            password.setError("Password must be at least 6 character");
            password.requestFocus();
            valid = false;

            editText = password;
        }


        if (birthdate.getText().toString().length() == 0) {
            birthdate.setError("Please Enter Your Birthdate");
            birthdate.requestFocus();
        }

        if (phone.getText().toString().length() == 0) {
            phone.setError("Please Enter Your Mobile Number");
            phone.requestFocus();
            valid = false;

            editText = phone;
        } else if (phone.getText().toString().length() != 11) {
            phone.setError("Please Enter a Valid Mobile Number");
            phone.requestFocus();
            valid = false;

            editText = phone;
        } else {
            int number;
            try {
                number = Integer.parseInt(phone.getText().toString());
            } catch (NumberFormatException e) {
                //error
                phone.setError("Please Enter only Numbers");
                phone.requestFocus();
                valid = false;

                editText = phone;
            }
        }


        if (mail.getText().toString().length() == 0) {
            mail.setError("Please Enter Your Mail");
            mail.requestFocus();
            valid = false;

            editText = mail;
        } else if (!isEmailValid(mail.getText().toString())) {
            mail.setError("Please Enter a Valid Mail");
            mail.requestFocus();
            valid = false;

            editText = mail;
        }


        if (name.getText().toString().length() == 0) {
            name.setError("Please Enter Your Name");
            name.requestFocus();
            valid = false;

            editText = mail;
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

    private void createAccount(final String email, String password) {


        //  showProgressDialog();

        // [START create_user_with_email]
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //set data to database
                        final RegisterationData registerationData = new RegisterationData();
                        registerationData.setDisplayName(name.getText().toString());
                        registerationData.setMail(mail.getText().toString());
                        registerationData.setMobile(phone.getText().toString());
                        registerationData.setBirthDate(birthdate.getText().toString());

                        try {
                            FirebaseDatabase database = FirebaseDatabase.getInstance();
                            DatabaseReference myRef = database.getReference("Users/" + mAuth.getCurrentUser().getUid() + "/UserProfile");

                            myRef.setValue(registerationData, new DatabaseReference.CompletionListener() {
                                public void onComplete(DatabaseError error, DatabaseReference ref) {
                                    if (error != null) {
                                        Log.d("ERROR", error.toString());
                                        TastyToast.makeText(getApplicationContext(), "Please Try Later", TastyToast.LENGTH_LONG, TastyToast.ERROR);

                                        relativeLayout.setVisibility(View.GONE);
                                    } else {
                                        SplashScreen.path = "Users/" + FirebaseAuth.getInstance().getCurrentUser().getUid();
                                        TastyToast.makeText(getApplicationContext(), "your account created successfully", TastyToast.LENGTH_LONG, TastyToast.SUCCESS);
                                        startActivity(new Intent(Register.this, Home.class));
                                    }
                                }
                            });


                            // If sign in fails, display a message to the user. If sign in succeeds
                            // the auth state listener will be notified and logic to handle the
                            // signed in user can be handled in the listener.
                            if (!task.isSuccessful()) {
                                Toast.makeText(Register.this, "failed",
                                        Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            TastyToast.makeText(getApplicationContext(), "This Mail Is Already Exist ", TastyToast.LENGTH_LONG, TastyToast.CONFUSING);
                            relativeLayout.setVisibility(View.GONE);
                        }

                        // [START_EXCLUDE]
                        //  hideProgressDialog();
                        // [END_EXCLUDE]
                    }
                });
        // [END create_user_with_email]
    }


    /**
     * method is used for checking valid email id format.
     *
     * @param email
     * @return boolean true for valid false for invalid
     */
    public static boolean isEmailValid(String email) {
        boolean isValid = false;

        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        CharSequence inputStr = email;

        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);
        if (matcher.matches()) {
            isValid = true;
        }
        return isValid;
    }

    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }
}
