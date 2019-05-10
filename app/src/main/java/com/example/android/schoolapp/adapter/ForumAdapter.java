package com.example.android.schoolapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.android.schoolapp.fragment.ForumFragment;
import com.example.android.schoolapp.ui.CommentActivity;
import com.example.android.schoolapp.R;
import com.example.android.schoolapp.model.Question;
import com.example.android.schoolapp.ui.MainActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

import javax.annotation.Nullable;


public class ForumAdapter extends RecyclerView.Adapter<ForumAdapter.ViewHolder> {
    private Context context;
    private List<Question> questionList;

    //private FirebaseFirestore firebaseFirestore;

    public ForumAdapter(Context context, List<Question> questionList) {
        this.questionList = questionList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.knit_list, viewGroup, false);
        //firebaseFirestore = FirebaseFirestore.getInstance();
        return new ForumAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int i) {

        final String questionId = questionList.get(i).QuestionPPostId;

        final Question question = questionList.get(i);
        viewHolder.mQuestion.setText(question.getQuestion());
        viewHolder.mName.setText(question.getName());
        viewHolder.mCount.setText(question.getCommentCount());

        Bundle bundle1 = new Bundle();
        bundle1.putString("questionId",questionId);

        ForumFragment fm = new ForumFragment();
        fm.setArguments(bundle1);

        viewHolder.btnAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, CommentActivity.class);
                intent.putExtra("questionId", questionId);
                context.startActivity(intent);
            }
        });

        //Get Comment Count.
        /*firebaseFirestore.collection("Question/" + questionId + "/Comments")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {

                        assert queryDocumentSnapshots != null;
                        if (!queryDocumentSnapshots.isEmpty()) {
                            int count = queryDocumentSnapshots.size();
                            viewHolder.updateCount(count);
                            //viewHolder.mMessage.setText(count);
                        }else{
                            viewHolder.updateCount(0);
                        }
                    }
                });*/
    }

    @Override
    public int getItemCount() {
        return questionList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView mQuestion, mName, mCount;
        Button btnAnswer;

        ViewHolder(@NonNull View itemView) {
            super(itemView);

            mQuestion = itemView.findViewById(R.id.question);
            mName = itemView.findViewById(R.id.kName);
            mCount = itemView.findViewById(R.id.messageCount);
            btnAnswer = itemView.findViewById(R.id.answerBtn);
        }

        /*private void updateCount(int count) {
            mMessage = itemView.findViewWithTag(R.id.messageCount);
            mMessage.setText(String.valueOf(count));
        }*/
    }
}
