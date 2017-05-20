package com.samir.andrew.orchestra.Adapters;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.samir.andrew.orchestra.Data.ChatMessageModel;
import com.samir.andrew.orchestra.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by andre on 07-May-17.
 */

public class ChatMsgAdapter extends RecyclerView.Adapter<ChatMsgAdapter.MyViewHolder> {

    private List<ChatMessageModel> chatMessageModelList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView body, time;

        public MyViewHolder(View view) {
            super(view);
            body = (TextView) view.findViewById(R.id.tvChatMsg);
            time = (TextView) view.findViewById(R.id.tvDateChatMsg);
        }
    }


    public ChatMsgAdapter(List<ChatMessageModel> chatMessageModels) {
        this.chatMessageModelList = chatMessageModels;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        int layoutRes = 0;
        switch (viewType) {
            case 1:
                layoutRes = R.layout.item_chat_send_msg;
                break;
            case 0:
                layoutRes = R.layout.item_chat_received_msg;
                break;
        }

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(layoutRes, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        ChatMessageModel chatMessageModel = chatMessageModelList.get(position);

        SimpleDateFormat format = new SimpleDateFormat("dd-MMM-yy  hh:mm a");
        String date = format.format(Date.parse(chatMessageModel.getCreatedAt()));


        holder.body.setText(chatMessageModel.getText());
        holder.time.setText(date);
    }

    @Override
    public int getItemCount() {
        return chatMessageModelList.size();
    }

    @Override
    public int getItemViewType(int position) {

        int sender;

        if (chatMessageModelList.get(position).getSENDER().equals("1"))
            sender = 1;
        else
            sender = 0;

        return sender;
    }
}
