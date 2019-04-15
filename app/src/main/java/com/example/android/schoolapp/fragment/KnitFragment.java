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

import java.util.ArrayList;
import java.util.List;

public class KnitFragment extends Fragment {

    public KnitFragment(){
    }
    private List<KnitData> dataList=new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.knit_fragment,container,false);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerViewKnit);
        KnitAdapter adapter = new KnitAdapter(getContext(),dataList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

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
}
