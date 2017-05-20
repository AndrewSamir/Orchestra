package com.samir.andrew.orchestra.Adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.samir.andrew.orchestra.Data.FeedsData;
import com.samir.andrew.orchestra.R;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


/**
 * Created by andre on 20-Mar-17.
 */

public class FeedsAdapter extends BaseAdapter {

    Activity activity;
    ArrayList<FeedsData> list;
    LayoutInflater inflater;

    public FeedsAdapter(Activity activity, ArrayList<FeedsData> list) {
        this.activity = activity;
        this.list = list;
        inflater = activity.getLayoutInflater();

    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    static class ViewHolder {

        private TextView title;
        private TextView date;
        private TextView body;
        private ImageView imageView;

    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        ViewHolder mViewHolder = null;
        FeedsData dataNews = list.get(i);


        if (view == null) {

            mViewHolder = new ViewHolder();

            LayoutInflater vi = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            view = vi.inflate(R.layout.item_listview_fragment_feeds, null);
            mViewHolder.title = (TextView) view.findViewById(R.id.textViewItemListViewFragmentFeeds); // title
            mViewHolder.date = (TextView) view.findViewById(R.id.tvDateItemNewFeeds);
            mViewHolder.body = (TextView) view.findViewById(R.id.tvBodyItemNewFeeds);
            mViewHolder.imageView = (ImageView) view.findViewById(R.id.imageView2); // title


            view.setTag(mViewHolder);


        } else {

            mViewHolder = (ViewHolder) view.getTag();

        }

        mViewHolder.title.setText(dataNews.getTitle());
        mViewHolder.body.setText(dataNews.getBody());
        mViewHolder.date.setText(dataNews.getDate());
        Picasso.with(activity).
                load(dataNews.getImage())
                .error(R.drawable.orchestra_icon)
                .networkPolicy(NetworkPolicy.OFFLINE)
                .placeholder(R.drawable.orchestra_icon)
                .into(mViewHolder.imageView);

        return view;


    }
}
