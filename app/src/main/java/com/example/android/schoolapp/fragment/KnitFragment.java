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
import android.widget.Button;

import com.example.android.schoolapp.QueAnsActivty;
import com.example.android.schoolapp.QuestionActivity;
import com.example.android.schoolapp.R;
import com.example.android.schoolapp.adapter.KnitAdapter;
import com.example.android.schoolapp.model.KnitData;
import com.example.android.schoolapp.model.Question;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.ls.LSInput;

import java.util.ArrayList;
import java.util.List;

public class KnitFragment extends Fragment {

    public KnitFragment(){
    }
    //private List<KnitData> dataList=new ArrayList<>();
    private List<Question> questionList;
    RecyclerView recyclerView;
    //DatabaseReference databaseReference;
    private  KnitAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.knit_fragment,container,false);
        recyclerView = view.findViewById(R.id.recyclerViewKnit);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        /*
        final KnitAdapter adapter = new KnitAdapter(getContext(),questionList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
*/
        questionList=new ArrayList<>();

        readQuestion();

        FloatingActionButton fab = view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(getActivity(),QuestionActivity.class);
                startActivity(in);
            }
        });
        return view;
    }


    private void readQuestion(){

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Post");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Question question = snapshot.getValue(Question.class);
                    questionList.add(question);
                }
                adapter=new KnitAdapter(getContext(),questionList);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    /*
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        dataList = new ArrayList<>();
        dataList.add(new KnitData("Rohan","3min","What is JSP","View 1 answer","97 Views"));
        dataList.add(new KnitData("Nikita","3min","What is JSP","View 1 answer","97 Views"));
        dataList.add(new KnitData("Alex","3min","What is JSP","View 1 answer","97 Views"));
        dataList.add(new KnitData("Suman","3min","What is JSP","View 1 answer","97 Views"));
        dataList.add(new KnitData("Gabrial","3min","What is JSP","View 1 answer","97 Views"));
    }
    */
}
