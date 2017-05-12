package com.samir.andrew.orchestra.Activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.messaging.FirebaseMessaging;
import com.samir.andrew.orchestra.R;
import com.sdsmdg.tastytoast.TastyToast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LogIn extends AppCompatActivity {

    EditText mail, password;
    Button signin;
    TextView register;
    LinearLayout relative;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        final FirebaseAuth mAuth = FirebaseAuth.getInstance();

        TextView tx = (TextView) findViewById(R.id.textViewOrchestraLogin);
        Typeface custom_font = Typeface.createFromAsset(getAssets(), "oleo_script.ttf");
        tx.setTypeface(custom_font);

        mail = (EditText) findViewById(R.id.editTextUserNameLogin);
        password = (EditText) findViewById(R.id.editTextPasswordLogin);

        signin = (Button) findViewById(R.id.buttonSignIn);
        register = (TextView) findViewById(R.id.textViewRegisterLogin);
        relative = (LinearLayout) findViewById(R.id.progressLayout);

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                if (vaildForm()) {
                    hideKeyboard();
                    relative.setVisibility(View.VISIBLE);
                    mAuth.signInWithEmailAndPassword(mail.getText().toString(), password.getText().toString())
                            .addOnSuccessListener(LogIn.this, new OnSuccessListener<AuthResult>() {
                                @Override
                                public void onSuccess(AuthResult authResult) {
                                    FirebaseMessaging.getInstance().subscribeToTopic(FirebaseAuth.getInstance().getCurrentUser().getUid());
                                    TastyToast.makeText(getApplicationContext(), "your logged in successfully", TastyToast.LENGTH_LONG, TastyToast.SUCCESS);

                                    Intent goToHome = new Intent(LogIn.this, Home.class);
                                    startActivity(goToHome);
                                }
                            }).addOnFailureListener(LogIn.this, new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            relative.setVisibility(View.GONE);
                            Log.d("errorLog", e.toString());
                            TastyToast.makeText(getApplicationContext(), "Email or Password is Incorrect", TastyToast.LENGTH_LONG, TastyToast.CONFUSING);

                        }
                    });

                }

            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goToRegister = new Intent(LogIn.this, Register.class);
                startActivity(goToRegister);
            }
        });

    }

    private void openKeyboard(EditText editText) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT);
    }

    private boolean vaildForm() {

        Boolean vaild = true;
        EditText editText = null;

        if (password.getText().toString().length() == 0) {
            password.setError("Please Write Your Password");
            password.requestFocus();
            editText = password;
            vaild = false;

        }
        if (mail.getText().toString().length() == 0) {
            mail.setError("Please Write Your Email");
            mail.requestFocus();
            vaild = false;
            editText = mail;
        } else if (!isEmailValid(mail.getText().toString())) {
            mail.setError("Please Write a Vaild Email");
            mail.requestFocus();
            vaild = false;
            editText = mail;
        }

        if (!vaild) {
            try {
                openKeyboard(editText);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return vaild;
    }

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

    private void hideKeyboard() {
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
        );
    }

}
