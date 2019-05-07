package com.example.android.schoolapp.ui;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.android.schoolapp.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
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


                        HashMap<String, Object> postMap = new HashMap<>();
                        postMap.put("question", question);
                        postMap.put("name", username);
                        postMap.put("time",FieldValue.serverTimestamp());

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
    }
}