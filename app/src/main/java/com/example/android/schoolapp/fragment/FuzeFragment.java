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

import com.example.android.schoolapp.ApiInterface;
import com.example.android.schoolapp.R;
import com.example.android.schoolapp.RetrofitClientInstance;
import com.example.android.schoolapp.adapter.FuzeAdapter;
import com.example.android.schoolapp.model.FuzePhoto;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FuzeFragment extends Fragment {

    private FuzeAdapter adapter;
    private RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fuze_fragment,container,false);

        recyclerView = view.findViewById(R.id.fuzeRecyclerView);

        ApiInterface apiInterface = RetrofitClientInstance.getRetrofit().create(ApiInterface.class);
        Call<List<FuzePhoto>> call = apiInterface.getAllPhotos();
        call.enqueue(new Callback<List<FuzePhoto>>() {
            @Override
            public void onResponse(Call<List<FuzePhoto>> call, Response<List<FuzePhoto>> response) {
                loadData(response.body());
            }

            @Override
            public void onFailure(Call<List<FuzePhoto>> call, Throwable t) {

            }
        });

        return view;
    }

    private void loadData(List<FuzePhoto> photoList){
        adapter =new FuzeAdapter(photoList,getContext());
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }


}
