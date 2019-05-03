package com.example.android.schoolapp.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.android.schoolapp.MainActivity;
import com.example.android.schoolapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class TeacherRegFragment extends Fragment {

    public TeacherRegFragment() {
        // Required empty public constructor
    }

    EditText name, email, mobile, password;
    Button submitBtn;
    FirebaseFirestore db;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_teacher_reg, container, false);

        name = view.findViewById(R.id.edit_TeacherName);
        email = view.findViewById(R.id.edit_TeacherEmail);
        mobile = view.findViewById(R.id.edit_TeacherPhone);
        password = view.findViewById(R.id.edit_TeacherPassword);

        db = FirebaseFirestore.getInstance();

        submitBtn = view.findViewById(R.id.TeacherRegisterBtn);

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String teacherName = name.getText().toString();
                String teacherEmail = email.getText().toString();
                String teacherMobile = mobile.getText().toString();
                String teacherPassword = password.getText().toString();

                if (teacherName.equals("") || teacherEmail.equals("") || teacherMobile.equals("") || teacherPassword.equals("")) {
                    Toast.makeText(getContext(), "All fields are required", Toast.LENGTH_SHORT).show();
                } else if (teacherPassword.length() < 6) {
                    Toast.makeText(getContext(), "password must be at least 6 characters", Toast.LENGTH_SHORT).show();
                } else {
                    registerTeacher(teacherName, teacherEmail, teacherMobile, teacherName);
                }

            }
        });

        return view;
    }

    //Register New User as Teacher.
    private void registerTeacher(final String name, String email, String password, final String mobile) {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                            String teacherId = firebaseUser.getUid();

                            HashMap<String, String> hashMap = new HashMap<>();
                            hashMap.put("name", name);
                            hashMap.put("mobile", mobile);
                            hashMap.put("image", "default");

                            db.collection("Teacher").document(teacherId)
                                    .set(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Intent intent = new Intent(getContext(), MainActivity.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        startActivity(intent);
                                        Toast.makeText(getActivity(), "Register successfully", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(getActivity(), "Error in Registration", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }
                    }
                });
    }
}
