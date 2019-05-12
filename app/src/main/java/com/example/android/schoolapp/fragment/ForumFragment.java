package com.example.android.schoolapp.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.schoolapp.ui.QuestionActivity;
import com.example.android.schoolapp.R;
import com.example.android.schoolapp.adapter.ForumAdapter;
import com.example.android.schoolapp.model.Question;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class ForumFragment extends Fragment {

    public ForumFragment() {
    }

    private List<Question> questionList;
    RecyclerView recyclerView;
    private ForumAdapter adapter;

    String questionId;

    FirebaseFirestore db;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.knit_fragment, container, false);
        recyclerView = view.findViewById(R.id.recyclerViewKnit);
        LinearLayoutManager ll = new LinearLayoutManager(getContext());
        ll.setReverseLayout(true);
        ll.setStackFromEnd(true);
        recyclerView.setLayoutManager(ll);
        recyclerView.setHasFixedSize(true);

        db = FirebaseFirestore.getInstance();

        if(getArguments()!= null){
            questionId = getArguments().getString("questionId");
        }

        questionList = new ArrayList<>();

        commentCount();
        readQuestion();

        FloatingActionButton fab = view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(getActivity(), QuestionActivity.class);
                startActivity(in);
            }
        });
        return view;
    }

    // Read all question.
    private void readQuestion() {
        db.collection("Question").orderBy("time", Query.Direction.ASCENDING)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for (DocumentChange doc : queryDocumentSnapshots.getDocumentChanges()) {
                            String questionId = doc.getDocument().getId();
                            Question question = doc.getDocument().toObject(Question.class).withId(questionId);
                            questionList.add(question);
                        }
                        adapter = new ForumAdapter(getContext(),questionList);
                        recyclerView.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                        recyclerView.smoothScrollToPosition(adapter.getItemCount());
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
            }
        });
    }

    private void commentCount(){
        db.collection("Question/" + questionId + "/Comments")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {

                        assert queryDocumentSnapshots != null;
                        if (!queryDocumentSnapshots.isEmpty()) {
                            int count = queryDocumentSnapshots.size();
                            String countString = String.valueOf(count);
                            Log.d("********","countComment"+count);
                            db.collection("Question").document(questionId)
                                    .update("commentCount",countString);
                        }
                    }
                });
    }
}
