package com.example.android.schoolapp;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.schoolapp.model.Comments;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.HashMap;

public class CommentActivity extends AppCompatActivity {

    String currentUser;
    FirebaseAuth auth;

    FirebaseFirestore db;

    EditText mComment;
    RecyclerView recyclerView;

    String questionId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_que_ans_activty);

        db = FirebaseFirestore.getInstance();

        auth = FirebaseAuth.getInstance();
        currentUser = auth.getCurrentUser().getUid();

        recyclerView = findViewById(R.id.recyclerViewMsg);
        mComment = findViewById(R.id.msgEditText);

        Toolbar toolbar = findViewById(R.id.bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Message");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);

        ImageButton post = findViewById(R.id.btn_send);

        Bundle bundle = getIntent().getExtras();
        assert bundle != null;
        questionId = bundle.getString("questionId");

        //Get current Username.
        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DocumentReference docRef = db.collection("Students").document(currentUser);
                docRef.get()
                        .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                String name = documentSnapshot.getString("name");
                                ValidateComment(name);
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(CommentActivity.this, "Error in Loading data", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }


    //Get all data in firestore recycler view adapter.
    @Override
    protected void onStart() {
        super.onStart();


        Query query = db.collection("Question/" + questionId + "/Comments")
                .orderBy("time", Query.Direction.ASCENDING);

        FirestoreRecyclerOptions<Comments> options =
                new FirestoreRecyclerOptions.Builder<Comments>()
                        .setQuery(query, Comments.class)
                        .build();

        final FirestoreRecyclerAdapter<Comments, CommentViewHolder> firestoreRecyclerAdapter =
                new FirestoreRecyclerAdapter<Comments, CommentViewHolder>(options) {

                    @NonNull
                    @Override
                    public CommentViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.comment_layout, viewGroup, false);
                        return new CommentViewHolder(view);
                    }

                    @Override
                    protected void onBindViewHolder(@NonNull CommentViewHolder holder, int position, @NonNull Comments model) {
                        holder.name.setText(model.getName());
                        holder.comment.setText(model.getComment());
                    }
                };

        recyclerView.setAdapter(firestoreRecyclerAdapter);
        firestoreRecyclerAdapter.notifyDataSetChanged();
        firestoreRecyclerAdapter.startListening();
    }

    public static class CommentViewHolder extends RecyclerView.ViewHolder {

        TextView name, comment;

        CommentViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.comment_username);
            comment = itemView.findViewById(R.id.comment);
        }
    }

    //Put comment/answer on question.
    private void ValidateComment(String username) {
        String comment = mComment.getText().toString();

        if (TextUtils.isEmpty(comment)) {
            Toast.makeText(this, "Please write something", Toast.LENGTH_SHORT).show();
        } else {

            HashMap<String, Object> commentMap = new HashMap<>();
            commentMap.put("comment", comment);
            commentMap.put("name", username);
            commentMap.put("time", FieldValue.serverTimestamp());

            assert questionId != null;
            db.collection("Question/" + questionId + "/Comments").add(commentMap)
                    .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentReference> task) {
                            if (task.isSuccessful()) {
                                mComment.setText("");
                            } else {
                                Toast.makeText(CommentActivity.this, "Error in posting comment" + task.getException(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }
}
