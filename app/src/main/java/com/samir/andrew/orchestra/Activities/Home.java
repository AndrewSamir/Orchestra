package com.samir.andrew.orchestra.Activities;

import android.app.Dialog;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.constraint.solver.SolverVariable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.crash.FirebaseCrash;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.samir.andrew.orchestra.Adapters.HomePagerAdapter;
import com.samir.andrew.orchestra.Data.JsonDataPojo;
import com.samir.andrew.orchestra.Data.UnittDetails.UnitPayment;
import com.samir.andrew.orchestra.Fragments.AllUnitDetails;
import com.samir.andrew.orchestra.Fragments.MyUnitsFragment;
import com.samir.andrew.orchestra.R;
import com.samir.andrew.orchestra.SQLiteDatabase.DBhelper;
import com.sdsmdg.tastytoast.TastyToast;

import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEvent;
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEventListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class Home extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public static ViewPager mViewPager;

    public static boolean firstFragment = false;
    Dialog loadingDialog, dialog;
    EditText text;
    public static int fragmentPosition;
    public static Boolean intoUnitDetails = false;
    public static String project = null;
    public static String unit = null;

    DBhelper myDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        myDB = new DBhelper(this);
//        myDB.createNewTable("AS-sa-k".replace('-','_'));

        //================================//
        //   FirebaseCrash.report(new Exception("My first Android non-fatal error"));
        //===============================//

        mViewPager = (ViewPager) findViewById(R.id.containerHome);


        HomePagerAdapter adapter = new HomePagerAdapter(getSupportFragmentManager());

        mViewPager.setOffscreenPageLimit(1);
        mViewPager.setAdapter(adapter);


        final TabLayout tabLayout = (TabLayout) findViewById(R.id.tabsHome);
        tabLayout.setupWithViewPager(mViewPager);
        tabLayout.getTabAt(0).setText("New Feeds");
        tabLayout.getTabAt(1).setText("My Units");
        tabLayout.getTabAt(2).setText("Messages");

        tabLayout.getTabAt(0).setIcon(getResources().getDrawable(R.drawable.ic_dashboard_black_24dp));
        tabLayout.getTabAt(1).setIcon(getResources().getDrawable(R.drawable.ic_home_black_24dp));
        tabLayout.getTabAt(2).setIcon(getResources().getDrawable(R.drawable.ic_notifications_black_24dp));

        tabLayout.getTabAt(0).getIcon().setColorFilter(Color.BLACK, PorterDuff.Mode.SRC_IN);
        tabLayout.getTabAt(1).getIcon().setColorFilter(Color.parseColor("#FFFFFF"), PorterDuff.Mode.SRC_IN);
        tabLayout.getTabAt(2).getIcon().setColorFilter(Color.parseColor("#FFFFFF"), PorterDuff.Mode.SRC_IN);


        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                tab.getIcon().setColorFilter(Color.BLACK, PorterDuff.Mode.SRC_IN);

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                tab.getIcon().setColorFilter(Color.parseColor("#FFFFFF"), PorterDuff.Mode.SRC_IN);

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        navigationView.setItemIconTintList(null);

        KeyboardVisibilityEvent.setEventListener(
                this,
                new KeyboardVisibilityEventListener() {
                    @Override
                    public void onVisibilityChanged(boolean isOpen) {
                        // some code depending on keyboard visiblity status
                        if (isOpen)
                            tabLayout.setVisibility(View.GONE);
                        else
                            tabLayout.setVisibility(View.VISIBLE);

                    }
                });


        Intent intent = getIntent();
        if (intent.getIntExtra("fromNotification", 0) == 1) {

            project = intent.getStringExtra("project");
            unit = intent.getStringExtra("unit");
        }
        mViewPager.setCurrentItem(intent.getIntExtra("fromNotification", 0));

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if (fragmentPosition != 0) {

                if (fragmentPosition == 1 && intoUnitDetails) {
                    intoUnitDetails = false;
                    super.onBackPressed();
                } else
                    mViewPager.setCurrentItem(0);
            } else {
                finish();
            }
        }


    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_pass_code_) {

            passcodeDialog(null);
            // Handle the camera action
        } else if (id == R.id.nav_logout) {
            FirebaseMessaging.getInstance().unsubscribeFromTopic(FirebaseAuth.getInstance().getCurrentUser().getUid());

            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(this, LogIn.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        } else if (id == R.id.nav_profile) {
            startActivity(new Intent(Home.this, Profile.class));

        }/* else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }
*/
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void passcodeDialog(String edittext) {

        // custom dialog
        dialog = new Dialog(Home.this);
        dialog.setContentView(R.layout.custom_passcode_dialog);

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));


        // set the custom dialog components - text, image and button
        TextView orchestra = (TextView) dialog.findViewById(R.id.textViewOrchestraPassCodeDialog);
        Typeface custom_font = Typeface.createFromAsset(getAssets(), "oleo_script.ttf");
        orchestra.setTypeface(custom_font);
        text = (EditText) dialog.findViewById(R.id.editTextPassCodeCustomDialog);
        if (edittext != null) {
            text.setText(edittext);
            text.setError("Please Enter a Valid PassCode");

        }


        Button Cancel = (Button) dialog.findViewById(R.id.buttonCancelCustomDialog);
        // if button is clicked, close the custom dialog
        Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        Button Link = (Button) dialog.findViewById(R.id.buttonLinkAccountCustomDialog);
        Link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (text.getText().length() == 0) {
                    text.setError("Please Enter Your PassCode");
                    return;
                } else if (!text.getText().toString().contains("@")) {
                    text.setError("Please Enter a Vaild PassCode");
                    return;
                } else {

                    if (isOnline()) {
                        dialog.dismiss();
                        loadingdialogFunction();
                        String[] separated = text.getText().toString().split("@");

                        String dataText = text.getText().toString();
                        getUriFromFirebase(separated[1], dataText.replaceAll(" ", "%20")
                                , FirebaseAuth.getInstance().getCurrentUser().getUid());
                    } else {
                        TastyToast.makeText(getApplicationContext(), "Internet Connection Error", TastyToast.LENGTH_LONG, TastyToast.ERROR);
                    }

                }

            }
        });
        dialog.show();
    }

    private void getUriFromFirebase(final String Project, final String passCode, final String uid) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("CompaniesURI/" + Project);

        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String value = dataSnapshot.getValue(String.class);

                Log.d("value", "Value is: " + value);
                //  Log.d("value", "Value is: " + value.length());


                if (dataSnapshot.getValue() == null) {
                    loadingDialog.dismiss();
                    passcodeDialog(passCode.replaceAll("%20", " "));
                    TastyToast.makeText(getApplicationContext(), "Please Enter a Valid Passcode", TastyToast.LENGTH_LONG, TastyToast.CONFUSING);

                } else {
                    if (isOnline())
                        getUnitData(value + "/GetUnitInfo?PassCode=" + passCode + "&UserID=" + uid, Project, passCode);
                    else {
                        loadingDialog.dismiss();
                        passcodeDialog(passCode.replaceAll("%20", " "));
                        TastyToast.makeText(getApplicationContext(), "Connect your Internet Connection and try again", TastyToast.LENGTH_LONG, TastyToast.ERROR);

                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("value", "Failed to read value.", error.toException());
                loadingDialog.dismiss();
                Toast.makeText(Home.this, "Connect your Internet Connection and try again", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void loadingdialogFunction() {

        loadingDialog = new Dialog(Home.this);
        loadingDialog.setContentView(R.layout.loading_dialog);

        loadingDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        loadingDialog.show();

    }

    private void getUnitData(String url, final String project, final String passCode) {

        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(Home.this);

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.d("responseData", response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject.getInt("status") == 1) {
                                JSONObject object = jsonObject.getJSONObject("UnitData");
                                JSONArray jsonArray = jsonObject.getJSONArray("UnitPayment");


                                String unitCode = object.getString("UnitCode");
                                if (isOnline())
                                    pushDataToFirebase(unitCode, object.toString(), jsonArray, project);
                                else {
                                    loadingDialog.dismiss();
                                    TastyToast.makeText(getApplicationContext(), "Connect your Internet Connection and try again", TastyToast.LENGTH_LONG, TastyToast.ERROR);

                                }
                            } else if (jsonObject.getInt("status") == 0) {
                                loadingDialog.dismiss();
                                passcodeDialog(passCode.replaceAll("%20", " "));

                                TastyToast.makeText(getApplicationContext(), "Please Enter a Valid Passcode", TastyToast.LENGTH_LONG, TastyToast.CONFUSING);
                            } else {
                                loadingDialog.dismiss();
                                TastyToast.makeText(getApplicationContext(), "Connect your Internet Connection and try again", TastyToast.LENGTH_LONG, TastyToast.ERROR);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // get the data from firebase
                loadingDialog.dismiss();
                TastyToast.makeText(getApplicationContext(), "Connect your Internet Connection and try again", TastyToast.LENGTH_LONG, TastyToast.ERROR);

            }
        });
        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }

    private void pushDataToFirebase(final String unitKey, String value, final JSONArray jsonArray, final String project) {
        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference("Users/" + FirebaseAuth.getInstance().getCurrentUser().getUid()
                + "/Projects/"
                + project +
                "/" + unitKey
        );

        Gson gson = new GsonBuilder().serializeNulls().create();
        JsonDataPojo myPOJO = gson.fromJson(value, JsonDataPojo.class);


        List<UnitPayment> unitPaymentList = new ArrayList<>();

        String jsonOutput = jsonArray.toString();
        Type listType = new TypeToken<List<UnitPayment>>() {
        }.getType();
        List<UnitPayment> posts = (List<UnitPayment>) gson.fromJson(jsonOutput, listType);

        myRef.child("UnitPayment").setValue(posts);


        myRef.child("UnitDetails").setValue(myPOJO, new DatabaseReference.CompletionListener() {
            public void onComplete(DatabaseError error, DatabaseReference ref) {

                FirebaseMessaging.getInstance().subscribeToTopic(unitKey);
                FirebaseMessaging.getInstance().subscribeToTopic(project.replace(' ', '_'));
                TastyToast.makeText(getApplicationContext(), "Your Passcode Added Correctly", TastyToast.LENGTH_LONG, TastyToast.SUCCESS);

                loadingDialog.dismiss();
                dialog.dismiss();
            }
        });

    }

    public Boolean isOnline() {
        try {
            Process p1 = Runtime.getRuntime().exec("ping -c 1 www.google.com");
            int returnVal = p1.waitFor();
            boolean reachable = (returnVal == 0);
            return reachable;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return false;
    }

}
