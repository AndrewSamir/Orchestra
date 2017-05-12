package com.samir.andrew.orchestra.Adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.samir.andrew.orchestra.Data.ChatMessageModel;
import com.samir.andrew.orchestra.Data.NotificationModel;
import com.samir.andrew.orchestra.R;

import java.util.List;

/**
 * Created by andre on 06-May-17.
 */

public class ChatMessageAdapter extends ArrayAdapter<ChatMessageModel> {

    private Activity activity;
    private List<ChatMessageModel> chatMessageModelList;

    public ChatMessageAdapter(Activity context, int resource, List<ChatMessageModel> objects) {
        super(context, resource, objects);
        this.activity = context;
        this.chatMessageModelList = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

        int layoutResource = 0; // determined by view type
        ChatMessageModel chatMessageModel = getItem(position);
        int viewType = getItemViewType(position);

        if (chatMessageModel.getSENDER()) {
            layoutResource = R.layout.item_chat_send_msg;
        } else {
            layoutResource = R.layout.item_chat_received_msg;
        }

        // layoutResource = R.layout.item_notification_message;

        if (convertView != null) {
            holder = (ViewHolder) convertView.getTag();
        } else {
            convertView = inflater.inflate(layoutResource, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }

        //set message content
        holder.msg.setText(chatMessageModel.getBODY());
        holder.time.setText(chatMessageModel.getTIME());


        return convertView;
    }

    @Override
    public int getViewTypeCount() {
        // return the total number of view types. this value should never change
        // at runtime
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        // return a value between 0 and (getViewTypeCount - 1)
        return position % 2;
    }

    private class ViewHolder {
        private TextView msg, time;

        public ViewHolder(View v) {
            msg = (TextView) v.findViewById(R.id.tvChatMsg);
            time = (TextView) v.findViewById(R.id.tvDateChatMsg);

        }
    }
}