package com.example.android.schoolapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.android.schoolapp.QueAnsActivty;
import com.example.android.schoolapp.R;
import com.example.android.schoolapp.model.Question;

import java.util.List;


public class KnitAdapter extends RecyclerView.Adapter<KnitAdapter.ViewHolder> {
    private Context context;
    private List<Question> questionList;

    public KnitAdapter(Context context, List<Question> questionList) {
        this.context = context;
        this.questionList = questionList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.knit_list, viewGroup, false);
        return new KnitAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

        final Question question = questionList.get(i);
        viewHolder.mQuestion.setText(question.getQuestion());
        viewHolder.mName.setText(question.getName());

        viewHolder.btnAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, QueAnsActivty.class);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return questionList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView mQuestion, mName;
        Button btnAnswer;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            mQuestion = itemView.findViewById(R.id.question);
            mName = itemView.findViewById(R.id.kName);
            btnAnswer = itemView.findViewById(R.id.answerBtn);
        }
    }
}
