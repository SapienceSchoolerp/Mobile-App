package com.example.android.schoolapp.ui;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.android.schoolapp.R;
import com.example.android.schoolapp.adapter.DetailAdapter;
import com.example.android.schoolapp.model.DetailData;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class DetailInbox extends AppCompatActivity {

    DetailAdapter adapter;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_inbox);

        Toolbar toolbar = findViewById(R.id.bar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Inbox");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        List<DetailData> list = new ArrayList<>();
        list.add(new DetailData("Ms. Tio", " A narrative is a report of connected events, real or imaginary, presented in a sequence of written or spoken words, or still or moving images, or both.", ""));
        list.add(new DetailData("Ms. Leo", " A narrative is a report of connected events, real or imaginary, presented in a sequence of written or spoken words, or still or moving images, or both.", ""));

        RecyclerView recyclerView = findViewById(R.id.detailRecyclerView);
        adapter = new DetailAdapter(this, list);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }
}
