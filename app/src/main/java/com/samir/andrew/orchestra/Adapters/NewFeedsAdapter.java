package com.samir.andrew.orchestra.Adapters;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.samir.andrew.orchestra.Activities.AdvertEndPage;
import com.samir.andrew.orchestra.Data.FeedsData;
import com.samir.andrew.orchestra.R;
import com.samir.andrew.orchestra.SingleTon.AdvertSingleTon;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by andre on 09-May-17.
 */

public class NewFeedsAdapter extends RecyclerView.Adapter<NewFeedsAdapter.MyViewHolder> {

    private List<FeedsData> feedsDataList;
    private Activity activity;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView body, date, title;
        public ImageView imageView;
        public CardView cardView;

        public MyViewHolder(View view) {
            super(view);
            body = (TextView) view.findViewById(R.id.tvBodyItemNewFeeds);
            date = (TextView) view.findViewById(R.id.tvDateItemNewFeeds);
            title = (TextView) view.findViewById(R.id.textViewItemListViewFragmentFeeds);
            imageView = (ImageView) view.findViewById(R.id.imageView2);
            cardView = (CardView) view.findViewById(R.id.cvItemFeed);

        }
    }


    public NewFeedsAdapter(List<FeedsData> feedsDatas, Activity activity) {
        this.feedsDataList = feedsDatas;
        this.activity = activity;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_listview_fragment_feeds, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final FeedsData feedsData = feedsDataList.get(position);

        holder.title.setText(feedsData.getTitle());
        holder.body.setText(feedsData.getBody());
        holder.date.setText(feedsData.getDate());
        Picasso.with(activity).load(feedsData.getImage()).into(holder.imageView);

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AdvertSingleTon.getInstance().setBody(feedsData.getBody());
                AdvertSingleTon.getInstance().setDate(feedsData.getDate());
                AdvertSingleTon.getInstance().setImageUri(feedsData.getImage());
                AdvertSingleTon.getInstance().setTitle(feedsData.getTitle());

                Intent intent=new Intent(activity, AdvertEndPage.class);
                activity.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return feedsDataList.size();
    }

}

