package com.example.android.schoolapp.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.schoolapp.QuestionActivity;
import com.example.android.schoolapp.R;
import com.example.android.schoolapp.adapter.KnitAdapter;
import com.example.android.schoolapp.model.Question;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class KnitFragment extends Fragment {

    public KnitFragment() {
    }

    private List<Question> questionList;
    RecyclerView recyclerView;
    private KnitAdapter adapter;

    FirebaseFirestore db;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.knit_fragment, container, false);
        recyclerView = view.findViewById(R.id.recyclerViewKnit);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        db = FirebaseFirestore.getInstance();

        questionList = new ArrayList<>();

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
        db.collection("Question").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for (DocumentChange doc : queryDocumentSnapshots.getDocumentChanges()) {
                            String questionId = doc.getDocument().getId();
                            Question question = doc.getDocument().toObject(Question.class).withId(questionId);
                            questionList.add(question);
                        }
                        adapter = new KnitAdapter(getContext(), questionList);
                        recyclerView.setAdapter(adapter);
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
            }
        });
/*
        db.collection("Question").
                get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for(QueryDocumentSnapshot document : task.getResult()){
                                Log.d("***",document.getId() + "=>" +document.getData());
                            }
                        }else{
                            Log.d("***", "Error getting documents",task.getException());
                        }
                    }
                });

        /*
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Post");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Question question = snapshot.getValue(Question.class);
                    questionList.add(question);
                }
                adapter = new KnitAdapter(getContext(), questionList);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
*/
    }
}
