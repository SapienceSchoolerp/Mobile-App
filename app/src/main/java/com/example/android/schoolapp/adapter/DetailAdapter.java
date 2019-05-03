package com.example.android.schoolapp.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.schoolapp.R;
import com.example.android.schoolapp.model.DetailData;
import com.example.android.schoolapp.model.InboxData;

import java.util.List;

public class DetailAdapter extends RecyclerView.Adapter<DetailAdapter.ViewHolder> {
    private Context context;
    private List<DetailData> dataList;

    public DetailAdapter(Context context, List<DetailData> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.detail_list, viewGroup, false);
        return new DetailAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        DetailData data = dataList.get(i);
        viewHolder.name.setText(data.getmName());
        viewHolder.msg.setText(data.getmMessage());
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView name, msg;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.deUserName);
            msg = itemView.findViewById(R.id.deUserMsg);
        }
    }
}
