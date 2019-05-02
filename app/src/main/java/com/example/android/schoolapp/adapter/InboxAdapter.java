package com.example.android.schoolapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.schoolapp.DetailInbox;
import com.example.android.schoolapp.R;
import com.example.android.schoolapp.model.InboxData;

import java.util.List;

public class InboxAdapter extends RecyclerView.Adapter<InboxAdapter.ViewHolder> {

    private Context context;
    private List<InboxData> dataList;

    public InboxAdapter(Context context, List<InboxData> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.inbox_list, viewGroup, false);
        return new InboxAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        InboxData data = dataList.get(i);
        viewHolder.mName.setText(data.getmName());
        viewHolder.mMessage.setText(data.getmMessage());

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), DetailInbox.class);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    // ViewHolder
    class ViewHolder extends RecyclerView.ViewHolder {

        TextView mName;
        TextView mMessage;

        ViewHolder(@NonNull View itemView) {
            super(itemView);

            mName = itemView.findViewById(R.id.userName);
            mMessage = itemView.findViewById(R.id.userMsg);
        }
    }
}
