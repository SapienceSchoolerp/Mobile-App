package com.example.android.schoolapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {

    EditText username, email, password;
    Button btn, btnLogin;

    FirebaseAuth auth;
    FirebaseUser firebaseUser;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

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

        btn=findViewById(R.id.registerBtn);
        btnLogin=findViewById(R.id.login_btn);

        auth=FirebaseAuth.getInstance();

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String txtUserName=username.getText().toString();
                String txtEmail=email.getText().toString();
                String txtPassword=password.getText().toString();

                if(TextUtils.isEmpty(txtUserName) || TextUtils.isEmpty(txtEmail) || TextUtils.isEmpty(txtPassword)){
                    Toast.makeText(RegisterActivity.this,"All fields are required",Toast.LENGTH_SHORT).show();
                }else if(txtPassword.length()<6){
                    Toast.makeText(RegisterActivity.this,"password must be at least 6 characters",Toast.LENGTH_SHORT).show();
                }else{
                    register(txtUserName,txtEmail,txtPassword);
                }
            }
        });
    }

    private void register(final String username,String email,String password){
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
                    }
                });
    }

}
