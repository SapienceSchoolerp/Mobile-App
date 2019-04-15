package com.example.android.schoolapp.fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.example.android.schoolapp.R;
import com.example.android.schoolapp.adapter.InboxAdapter;
import com.example.android.schoolapp.model.InboxData;

import java.util.ArrayList;
import java.util.List;

public class InboxFragment extends Fragment  {

    public InboxFragment() {
    }
    private List<InboxData> list;
    InboxAdapter inboxAdapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.inbox_fragment, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerViewInbox);
        inboxAdapter = new InboxAdapter(getContext(), list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(inboxAdapter);
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        list = new ArrayList<>();
        list.add(new InboxData("Ms. Tio", " A narrative is a report of connected events, real or imaginary, presented in a sequence of written or spoken words, or still or moving images, or both.", ""));
        list.add(new InboxData("Ms. Leo", " A narrative is a report of connected events, real or imaginary, presented in a sequence of written or spoken words, or still or moving images, or both.", ""));
    }
}

