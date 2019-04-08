package com.example.android.schoolapp.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.schoolapp.R;
import com.example.android.schoolapp.model.KnitData;

import java.util.List;


public class KnitAdapter extends RecyclerView.Adapter<KnitAdapter.ViewHolder> {

    private Context context;
    private List<KnitData> dataList;

    public KnitAdapter(Context context, List<KnitData> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(context).inflate(R.layout.knit_list,viewGroup,false);
        return new KnitAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        KnitData data=dataList.get(i);
        viewHolder.mName.setText(data.getName());
        viewHolder.mTime.setText(data.getTime());
        viewHolder.mQuestion.setText(data.getQuestion());
        viewHolder.mAnswerList.setText(data.getAnswer_list());
        viewHolder.mViews.setText(data.getViews());

    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        TextView mName;
        TextView mTime;
        TextView mQuestion;
        TextView mAnswerList;
        TextView mViews;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            mName = itemView.findViewById(R.id.kName);
            mTime = itemView.findViewById(R.id.kTime);
            mQuestion = itemView.findViewById(R.id.question);
            mAnswerList = itemView.findViewById(R.id.kAnswer);
            mViews = itemView.findViewById(R.id.kViews);
        }
    }

}
