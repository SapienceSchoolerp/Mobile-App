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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.HashMap;

public class CommentActivity extends AppCompatActivity {

    private DatabaseReference UserRef, PostRef;
    String currentUser;
    FirebaseAuth auth;

    FirebaseFirestore db;

    EditText mComment;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_que_ans_activty);

        db=FirebaseFirestore.getInstance();
       //CollectionReference cr = db.collection("Comments");

        auth = FirebaseAuth.getInstance();
        currentUser = auth.getCurrentUser().getUid();

//      UserRef = FirebaseDatabase.getInstance().getReference().child("Users");
//      PostRef = FirebaseDatabase.getInstance().getReference().child("Post").child(currentUser).child("Comments");

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

        //Work on username
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
                     Toast.makeText(CommentActivity.this,"Error in Loading data",Toast.LENGTH_SHORT).show();
                 }
             });

            /*
            UserRef.child(currentUser).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            String username = dataSnapshot.child("name").getValue().toString();
                            ValidateComment(username);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                */
            }
        });
    }


    @Override
    protected void onStart() {
        super.onStart();

        Query query = db.collection("Comments");

        FirestoreRecyclerOptions<Comments> options =
                new FirestoreRecyclerOptions.Builder<Comments>()
                        .setQuery(query,Comments.class)
                        .build();

        FirestoreRecyclerAdapter<Comments, CommentViewHolder> firestoreRecyclerAdapter =
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
        firestoreRecyclerAdapter.startListening();

    }
/*
private void getComment(){
    db=FirebaseFirestore.getInstance();
    Query query = db.collection("");

    FirestoreRecyclerOptions<Comments> response=new FirestoreRecyclerOptions.Builder<>()
            .setQuery(query,Comments.class)
            .build();

}
*/

    public static class CommentViewHolder extends RecyclerView.ViewHolder {

        TextView name, comment;

        CommentViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.comment_username);
            comment = itemView.findViewById(R.id.comment);
        }
    }

    //Work on that
    private void ValidateComment(String username) {
        String comment = mComment.getText().toString();

        if (TextUtils.isEmpty(comment)) {
            Toast.makeText(this, "Please Write Comment", Toast.LENGTH_SHORT).show();
        } else {
            HashMap<String, Object> commentMap = new HashMap<>();
            commentMap.put("comment", comment);
            commentMap.put("name", username);

            db.collection("Comments").add(commentMap)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Toast.makeText(CommentActivity.this, "Commented successfully", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(CommentActivity.this, "Error in commenting", Toast.LENGTH_SHORT).show();
                }
            });

            /*
            PostRef.child(currentUser).setValue(commentMap)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(CommentActivity.this, "Comment Successfully", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(CommentActivity.this, "Error in Comment Posting", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });*/
        }
    }
}
