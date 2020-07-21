package com.jqz.easeui.adapter;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hyphenate.chat.EMMessage;
import com.jqz.easeui.R;

import java.util.ArrayList;

public class MessageAdapter extends RecyclerView.Adapter {
    private ArrayList<EMMessage> list;
    private Context context;
    private String name;

    public MessageAdapter(ArrayList<EMMessage> list, Context context, String name) {
        this.list = list;
        this.context = context;
        this.name = name;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == 0) {
            //右边布局
            View view = LayoutInflater.from(context).inflate(R.layout.message_item1, parent, false);
            ViewHolder1 holder = new ViewHolder1(view);
            return holder;
        } else {
            //左边布局
            View view = LayoutInflater.from(context).inflate(R.layout.message_item, parent, false);
            ViewHolder holder = new ViewHolder(view);
            return holder;
        }


    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        EMMessage message = list.get(position);

        if (getItemViewType(position) == 0) {
            //右边
            //左边

            ViewHolder1 viewHolder = (ViewHolder1) holder;
            String from = message.getFrom();//消息来源
            String body = message.getBody().toString();//消息体

            viewHolder.message_tv_desc2.setText("用户" + from + "发送的:" + body);

        } else {
            //左边
            ViewHolder viewHolder = (ViewHolder) holder;
            String from = message.getFrom();//消息来源
            String body = message.getBody().toString();//消息体

            viewHolder.message_tv_desc1.setText("用户" + from + "发送的:" + body);

        }


    }

    @Override
    public int getItemCount() {
        return list.size();
    }



    public static
    class ViewHolder extends MyRecyclerAdapter.ViewHolder {
        public View rootView;
        public TextView message_tv_desc1;

        public ViewHolder(View rootView) {
            super(rootView);
            this.rootView = rootView;
            this.message_tv_desc1 = (TextView) rootView.findViewById(R.id.message_tv_desc1);
        }

    }

    @Override
    public int getItemViewType(int position) {
        EMMessage message = list.get(position);
        String from = message.getFrom();

        if (from.equals(name)) {
            //加载右边布局
            return 0;
        } else {
            //加载左边布局
            return -1;
        }

    }

    public static class ViewHolder1 extends RecyclerView.ViewHolder {
        public View rootView;
        public TextView message_tv_desc2;

        public ViewHolder1(View rootView) {
            super(rootView);
            this.rootView = rootView;
            this.message_tv_desc2 = (TextView) rootView.findViewById(R.id.message_tv_desc2);
        }

    }
}
