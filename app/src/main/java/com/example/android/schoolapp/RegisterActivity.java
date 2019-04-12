package com.example.android.schoolapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.schoolapp.fragment.StuRegisterFragment;
import com.example.android.schoolapp.fragment.TeacherRegFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {

    EditText username, email, password,userPhone;
    Button btn, btnLogin;

    FirebaseAuth auth;
    FirebaseUser firebaseUser;
    DatabaseReference reference;

    Button btnStudent, btnTeacher;
    TextView login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        btnStudent=findViewById(R.id.btnStudent);
        btnTeacher=findViewById(R.id.btnTeacher);
        login=findViewById(R.id.loginTxt);

        btnStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setFragment(new StuRegisterFragment());
            }
        });

        btnTeacher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setFragment(new TeacherRegFragment());
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });


       // final Spinner spinner = findViewById(R.id.spinner1);
        final Spinner spinner2 = findViewById(R.id.spinner2);
        /*
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String selectedClass = adapterView.getItemAtPosition(i).toString();
                switch(selectedClass)
                {
                    case "Student":
                        spinner2.setVisibility(View.VISIBLE);
                        spinner2.setAdapter(new ArrayAdapter<>(RegisterActivity.this,android.R.layout.simple_spinner_dropdown_item,
                                getResources().getStringArray(R.array.student_class)));
                        break;

                    case "Teacher":
                        spinner2.setVisibility(View.GONE);
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

       /* ArrayAdapter<String> adapter = new ArrayAdapter<String>(RegisterActivity.this,
                android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.names));

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);*/

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        //check user is already exist.
        if(firebaseUser !=null){
            Intent intent= new Intent(RegisterActivity.this,MainActivity.class);
            startActivity(intent);
            finish();
        }

        username=findViewById(R.id.edit_userName);
        email=findViewById(R.id.edit_userEmail);
        password=findViewById(R.id.edit_userPassword);
        userPhone=findViewById(R.id.edit_userPhone);

        btn=findViewById(R.id.registerBtn);
        btnLogin=findViewById(R.id.login_btn);

        auth=FirebaseAuth.getInstance();

       /* btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

       /* btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String txtUserName=username.getText().toString();
                String txtEmail=email.getText().toString();
                String txtPassword=password.getText().toString();
                String phone=userPhone.getText().toString();

                //Getting Data from spinner selection on Registration page.
               // String spinner1data = spinner.getSelectedItem().toString();
                String spinner2data = spinner2.getSelectedItem().toString();

                if(TextUtils.isEmpty(txtUserName) || TextUtils.isEmpty(txtEmail) || TextUtils.isEmpty(txtPassword) || TextUtils.isEmpty(phone)){
                    Toast.makeText(RegisterActivity.this,"All fields are required",Toast.LENGTH_SHORT).show();
                }else if(txtPassword.length()<6){
                    Toast.makeText(RegisterActivity.this,"password must be at least 6 characters",Toast.LENGTH_SHORT).show();
                }
                /*else if (!spinner2data.equals("1")) {
                    register2(txtUserName,txtEmail,txtPassword,phone,spinner1data);
                } else {
                    register(txtUserName,txtEmail,txtPassword,phone,spinner2data);
                }
            }
        });*/
    }


    private void register(final String username, String email, String password, final String phone, final String spinner2){
        auth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                       if(task.isSuccessful()){
                           FirebaseUser firebaseUser = auth.getCurrentUser();
                           String userid=firebaseUser.getUid();

                           reference = FirebaseDatabase.getInstance().getReference("Users").child(userid);

                           HashMap<String, String> hashMap=new HashMap<>();
                           hashMap.put("id",userid);
                           hashMap.put("username",username);
                           hashMap.put("phone",phone);
                           hashMap.put("profile2",spinner2);
                           hashMap.put("image","default");
                           hashMap.put("thumb_image","default");

                           reference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                               @Override
                               public void onComplete(@NonNull Task<Void> task) {
                                   if(task.isSuccessful()){
                                       Intent intent = new Intent(RegisterActivity.this,MainActivity.class);
                                       startActivity(intent);
                                       finish();
                                   }
                               }
                           });
                       }else{
                           Toast.makeText(RegisterActivity.this,"You Can't Register",Toast.LENGTH_SHORT).show();
                       }
                    }});
    }

    /*
    private void register2(final String username, String email, String password, final String phone, final String spinner1){
        auth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            FirebaseUser firebaseUser = auth.getCurrentUser();
                            String userid=firebaseUser.getUid();

                            reference = FirebaseDatabase.getInstance().getReference("Users").child(userid);

                            HashMap<String, String> hashMap=new HashMap<>();
                            hashMap.put("id",userid);
                            hashMap.put("username",username);
                            hashMap.put("phone",phone);
                            hashMap.put("profile",spinner1);
                            hashMap.put("image","default");
                            hashMap.put("thumb_image","default");

                            reference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        Intent intent = new Intent(RegisterActivity.this,MainActivity.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                }
                            });
                        }else{
                            Toast.makeText(RegisterActivity.this,"You Can't Register",Toast.LENGTH_SHORT).show();
                        }
                    }});
    }
    */

    public void setFragment(Fragment f){
        FragmentManager fm =getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.setCustomAnimations(android.R.anim.fade_in,android.R.anim.fade_out);
        ft.replace(R.id.frame,f);
        ft.commit();
    }

}
