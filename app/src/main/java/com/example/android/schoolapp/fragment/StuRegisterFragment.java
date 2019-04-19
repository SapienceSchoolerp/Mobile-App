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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class StuRegisterFragment extends Fragment {

    public StuRegisterFragment() {
        // Required empty public constructor
    }

    EditText name, email, mobile, mClass, password;
    Button registerBtn;
    FirebaseUser firebaseUser;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_stu_register, container, false);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        //
        name = v.findViewById(R.id.StuUserName);
        email = v.findViewById(R.id.StuUserEmail);
        mobile = v.findViewById(R.id.StuUserPhone);
        password = v.findViewById(R.id.StuUserPassword);

        registerBtn = v.findViewById(R.id.StuRegisterBtn);
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Get detail for user registration Page.
                String mName = name.getText().toString();
                String mEmail = email.getText().toString();
                String mMobile = mobile.getText().toString();
                String mPassword = password.getText().toString();

                if (mName.equals("") || mEmail.equals("") || mMobile.equals("") || mPassword.equals("")) {
                    Toast.makeText(getContext(), "All fields are required", Toast.LENGTH_SHORT).show();
                } else if (mPassword.length() < 6) {
                    Toast.makeText(getContext(), "password must be at least 6 characters", Toast.LENGTH_SHORT).show();
                } else {
                    registerUser(mName, mEmail, mMobile, mPassword);
                }
            }
        });
        return v;
    }

    //Register New User as Student.
    private void registerUser(final String name, String email, String password, final String mobile) {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
                            String uid = currentUser.getUid();

                            DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Users").child(uid);

                            HashMap<String, String> hashMap = new HashMap<>();
                            hashMap.put("name", name);
                            hashMap.put("mobile", mobile);
                            hashMap.put("image", "default");

                            reference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Intent intent = new Intent(getActivity(), MainActivity.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        startActivity(intent);
                                    }
                                }
                            });
                        }
                    }
                });
    }
}
