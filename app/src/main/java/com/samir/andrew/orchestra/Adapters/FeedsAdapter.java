package com.samir.andrew.orchestra.Adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.samir.andrew.orchestra.Data.FeedsData;
import com.samir.andrew.orchestra.R;

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

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflater.inflate(R.layout.item_listview_fragment_feeds, null);
        TextView New = (TextView) view.findViewById(R.id.textViewItemListViewFragmentFeeds);

        FeedsData dataNews = list.get(i);

        New.setText(dataNews.getString());


        return view;
    }
}
