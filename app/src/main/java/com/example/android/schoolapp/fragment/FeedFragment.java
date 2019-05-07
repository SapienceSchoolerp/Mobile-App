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

import com.example.android.schoolapp.Networking.ApiInterface;
import com.example.android.schoolapp.R;
import com.example.android.schoolapp.Networking.RetrofitClientInstance;
import com.example.android.schoolapp.adapter.FeedAdapter;
import com.example.android.schoolapp.model.Feed;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FeedFragment extends Fragment {

    private RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fuze_fragment, container, false);

        recyclerView = view.findViewById(R.id.fuzeRecyclerView);

        ApiInterface apiInterface = RetrofitClientInstance.getRetrofit().create(ApiInterface.class);
        Call<List<Feed>> call = apiInterface.getAllPhotos();
        call.enqueue(new Callback<List<Feed>>() {
            @Override
            public void onResponse(Call<List<Feed>> call, Response<List<Feed>> response) {
                loadData(response.body());
            }

            @Override
            public void onFailure(Call<List<Feed>> call, Throwable t) {

            }
        });

        return view;
    }

    private void loadData(List<Feed> photoList) {
        FeedAdapter adapter = new FeedAdapter(photoList, getContext());
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }


}
