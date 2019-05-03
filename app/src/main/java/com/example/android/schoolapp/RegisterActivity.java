package com.example.android.schoolapp;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.android.schoolapp.fragment.StuRegisterFragment;
import com.example.android.schoolapp.fragment.TeacherRegFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegisterActivity extends AppCompatActivity {

    FirebaseUser firebaseUser;
    Button btnStudent, btnTeacher;
    TextView login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        setFragment(new StuRegisterFragment());

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        //check user is already exist.
        if (firebaseUser != null) {
            Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }

        btnStudent = findViewById(R.id.btnStudent);
        btnTeacher = findViewById(R.id.btnTeacher);
        login = findViewById(R.id.loginTxt);

        //start student registration page.
        btnStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setFragment(new StuRegisterFragment());
            }
        });

        //start teacher registration page.
        btnTeacher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setFragment(new TeacherRegFragment());
            }
        });

        //start login page.
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            //Error not solved
            public void onClick(View view) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }

    //Start Fragment.
    public void setFragment(Fragment f) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
        ft.replace(R.id.frame, f);
        ft.commit();
    }

}
