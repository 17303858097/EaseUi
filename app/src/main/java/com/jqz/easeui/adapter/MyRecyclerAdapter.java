package com.jqz.easeui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jqz.easeui.Frends;
import com.jqz.easeui.MainActivity;
import com.jqz.easeui.R;

import java.util.ArrayList;

public class MyRecyclerAdapter extends RecyclerView.Adapter {
    private ArrayList<Frends> list;
    private Context context;

    public MyRecyclerAdapter(ArrayList<Frends> list, Context context) {
        this.list = list;
        this.context = context;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_rlv, parent, false);
        final ViewHolder holder = new ViewHolder(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(onClickListenner!=null){
                    onClickListenner.onClick(holder.getLayoutPosition());//返回下标
                }
            }
        });
        return holder;
    }

    private OnClickListenner onClickListenner;

    public void setOnClickListenner(OnClickListenner onClickListenner) {
        this.onClickListenner = onClickListenner;
    }

    public interface OnClickListenner{
        void onClick(int position);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Frends frends = list.get(position);
        ViewHolder holder1= (ViewHolder) holder;
        holder1.rlv_tv_name.setText(frends.getName());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static
    class ViewHolder extends RecyclerView.ViewHolder {
        public View rootView;
        public TextView rlv_tv_name;

        public ViewHolder(View rootView) {
            super(rootView);
            this.rootView = rootView;
            this.rlv_tv_name = (TextView) rootView.findViewById(R.id.rlv_tv_name);
        }

    }
}
