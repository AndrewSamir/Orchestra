package com.samir.andrew.orchestra.Adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.samir.andrew.orchestra.Data.NotificationModel;
import com.samir.andrew.orchestra.R;

import java.util.List;

/**
 * Created by andre on 05-May-17.
 */

public class NotificationMessagesAdapter extends ArrayAdapter<NotificationModel> {

    private Activity activity;
    private List<NotificationModel> notificationModelList;

    public NotificationMessagesAdapter(Activity context, int resource, List<NotificationModel> objects) {
        super(context, resource, objects);
        this.activity = context;
        this.notificationModelList = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

        int layoutResource = 0; // determined by view type
        NotificationModel notificationModel = getItem(position);
        int viewType = getItemViewType(position);

       /* if (chatMessage.isMine()) {
            layoutResource = R.layout.item_chat_left;
        } else {
            layoutResource = R.layout.item_chat_right;
        }*/

        layoutResource = R.layout.item_notification_message;

        if (convertView != null) {
            holder = (ViewHolder) convertView.getTag();
        } else {
            convertView = inflater.inflate(layoutResource, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }

        //set message content
        holder.msg.setText(notificationModel.getText());
        holder.time.setText(notificationModel.getTime());


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
        private TextView msg,time;

        public ViewHolder(View v) {

            msg = (TextView) v.findViewById(R.id.tvNotificationMessage);
            time = (TextView) v.findViewById(R.id.tvNotificationTime);

        }
    }

}
