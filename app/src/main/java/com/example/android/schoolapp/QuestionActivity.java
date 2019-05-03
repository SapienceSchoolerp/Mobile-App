package com.example.android.schoolapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class QuestionActivity extends AppCompatActivity {

    FirebaseFirestore db;
    EditText mQuestion;
    Button btn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        Toolbar toolbar = findViewById(R.id.bar);
        setSupportActionBar(toolbar);

        db = FirebaseFirestore.getInstance();

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

        //Get Message
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String msg = mQuestion.getText().toString();
                if (!msg.equals("")) {
                    postQuestion(msg);
                } else {
                    Toast.makeText(QuestionActivity.this, "You can't send empty message", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    //Send question/Post question
    private void postQuestion(final String question) {
        FirebaseAuth auth = FirebaseAuth.getInstance();

        String currentUser = auth.getCurrentUser().getUid();
        db.collection("Students").document(currentUser).get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        String username = documentSnapshot.getString("name");

                        HashMap<String, String> postMap = new HashMap<>();
                        postMap.put("question", question);
                        postMap.put("name", username);

                        db.collection("Question").add(postMap)
                                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                    @Override
                                    public void onSuccess(DocumentReference documentReference) {
                                        Toast.makeText(QuestionActivity.this, "Successfully posting question", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(QuestionActivity.this, MainActivity.class);
                                        startActivity(intent);
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(QuestionActivity.this, "Error in posting question", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });

        /*
        DocumentReference dRef = db.collection("Students").document(current);
        dRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                DocumentSnapshot document = task.getResult();
                String name = document.getString("name");

                HashMap<String,String> postMap=new HashMap<>();
                postMap.put("question",question);
                postMap.put("name",name);

                db.collection("Questions").add(postMap)
                       .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                           @Override
                           public void onSuccess(DocumentReference documentReference) {
                               Toast.makeText(QuestionActivity.this,"Successfully posting question",Toast.LENGTH_SHORT).show();
                           }
                       }).addOnFailureListener(new OnFailureListener() {
                   @Override
                   public void onFailure(@NonNull Exception e) {
                       Toast.makeText(QuestionActivity.this,"Error in posting question",Toast.LENGTH_SHORT).show();
                   }
               });
            }
        });*/

/*
        HashMap<String,String> quesMap = new HashMap<>();
        quesMap.put("question",question);
        quesMap.put("name", name[0]);
*/


   /* FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        String uid = currentUser.getUid();
        UserRef = FirebaseDatabase.getInstance().getReference().child("Users").child(uid);
        PostRef = FirebaseDatabase.getInstance().getReference().child("Post").child(uid);

        UserRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                String name = dataSnapshot.child("name").getValue().toString();

                HashMap<String, Object> postMap = new HashMap<>();
                postMap.put("name", name);
                postMap.put("question", question);

                PostRef.setValue(postMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(QuestionActivity.this, "Post Successfully uploaded", Toast.LENGTH_SHORT).show();
                        } else {
                           Toast.makeText(QuestionActivity.this, "Error in Uploading", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });*/
    }
}