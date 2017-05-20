package com.samir.andrew.orchestra.Activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.samir.andrew.orchestra.R;
import com.samir.andrew.orchestra.SingleTon.AdvertSingleTon;
import com.squareup.picasso.Picasso;

public class AdvertEndPage extends AppCompatActivity {

    ImageView imageView;
    TextView tvDate, tvTitle, tvBody;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advert_end_page);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        imageView = (ImageView) findViewById(R.id.ivAdvertEndPage);
        tvBody = (TextView) findViewById(R.id.tvBodyAdvertEndPage);
        tvDate = (TextView) findViewById(R.id.tvDateAdvertEndPage);
        tvTitle = (TextView) findViewById(R.id.tvTitleAdvertEndPage);

        String s = AdvertSingleTon.getInstance().getImageUri();

        Picasso.with(this).load(s).into(imageView);

        tvTitle.setText(AdvertSingleTon.getInstance().getTitle());
        tvBody.setText(AdvertSingleTon.getInstance().getBody());
        tvDate.setText(AdvertSingleTon.getInstance().getDate());

    }

}
