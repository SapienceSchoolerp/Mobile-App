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
    private List<InboxData> list = new ArrayList<>();
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


    //SearchBar Code
   /* @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.search_menu, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                inboxAdapter.getFilter().filter(s);
                return false;
            }
        });

//        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
//        searchView= (android.support.v7.widget.SearchView) menu.findItem(R.id.search_menu).getActionView();
//        searchView.setSearchableInfo(searchManager.getSearchableInfo());
//        SearchView searchView
//
       /* if(searchItem != null){
            searchView= (SearchView) searchItem.getActionView();
        }

        if(searchView !=null){
            searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));

            queryTextListener=new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String s) {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String s) {
                    return false;
                }
            };
            searchView.setOnQueryTextListener(queryTextListener);

        }
        //searchView.setQueryHint("Search");
        super.onCreateOptionsMenu(menu, inflater);
    }


   /* @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        return false;
    }*/
    }

