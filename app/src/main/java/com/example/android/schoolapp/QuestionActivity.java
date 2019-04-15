package com.example.android.schoolapp;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class QuestionActivity extends AppCompatActivity {

    EditText mQuestion;
    Button btn;
    // DatabaseReference UserRef, PostRef;
   // FirebaseAuth mAuth;
      FirebaseUser fUser;
   // private String currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        //currentUser = mAuth.getCurrentUser().getUid();
        //PostRef =FirebaseDatabase.getInstance().getReference().child("Post");
        //UserRef = FirebaseDatabase.getInstance().getReference().child("Users");
        Toolbar toolbar = findViewById(R.id.bar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle("Question");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        mQuestion = findViewById(R.id.editTextQues);
        btn = findViewById(R.id.askBtn);
        fUser=FirebaseAuth.getInstance().getCurrentUser();

        //Get Message
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String msg = mQuestion.getText().toString();
                if(!msg.equals("")){
                    postQuestion(fUser.getUid(),msg);
                }else{
                    Toast.makeText(QuestionActivity.this,"You can't send empty message",Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    //Send question/Post question
    private void postQuestion(final String userName, final String question){
        FirebaseUser currentUser=FirebaseAuth.getInstance().getCurrentUser();
        String uid = currentUser.getUid();
        DatabaseReference reference =FirebaseDatabase.getInstance().getReference().child("Post").child(uid);

        HashMap<String,Object> postMap = new HashMap<>();

        postMap.put("username",userName);
        postMap.put("question",question);

        //reference.child("Post").push().setValue(postMap);
        reference.child("Post").setValue(postMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(QuestionActivity.this,"Post successfully Upload",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(QuestionActivity.this,"Error in Post",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}
