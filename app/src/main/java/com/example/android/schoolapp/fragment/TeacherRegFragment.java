package com.example.android.schoolapp.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.android.schoolapp.R;

public class TeacherRegFragment extends Fragment {

    public TeacherRegFragment() {
        // Required empty public constructor
    }

    EditText name,email,mobile,password;
    Button submitBtn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_teacher_reg,container,false);
        name=view.findViewById(R.id.edit_TeacherName);
        return view;
    }
}
