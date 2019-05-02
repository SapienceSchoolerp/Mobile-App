package com.example.android.schoolapp;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;


import java.util.Calendar;
import java.util.Map;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileS extends AppCompatActivity {

    private static final int GALLERY_PICK = 1;
    public static final String TAG = "MyActivity";
    CircleImageView circleImageView;
    TextView mName, mMobile, date_ofBirth;
    Button img_btn;
    ImageButton btnDate;

    Calendar c;
    DatePickerDialog dpd;


    private FirebaseFirestore db;
    private StorageReference img_storageRef;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_s);

        db=FirebaseFirestore.getInstance();

        circleImageView = findViewById(R.id.disImg);
        mName = findViewById(R.id.profileUsername2);
        mMobile = findViewById(R.id.mobile2);
        img_btn = findViewById(R.id.imageBtn);
        FirebaseFirestore.getInstance();
        //sp=getSharedPreferences(USER_PREF, Context.MODE_PRIVATE);

        btnDate = findViewById(R.id.dateBtn);
        date_ofBirth = findViewById(R.id.dateOfBirth2);
        btnDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                c = Calendar.getInstance();
                int day = c.get(Calendar.DAY_OF_MONTH);
                int month = c.get(Calendar.MONTH);
                int year = c.get(Calendar.YEAR);
                dpd = new DatePickerDialog(ProfileS.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int mYear, int mMonth, int mDay) {

                        date_ofBirth.setText(mDay + "/" + (mMonth + 1) + "/" + mYear);
                        // save(date_ofBirth.getText().toString());
                        //loadData();
                    }
                }, day, month, year);
                dpd.show();
            }
        });

        //Toolbar for Profile Setting
        Toolbar toolbar = findViewById(R.id.bar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Profile");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        img_storageRef = FirebaseStorage.getInstance().getReference();

        //private DatabaseReference reference;
        /*FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        String currentUser = firebaseUser.getUid();*/

        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        String currentUser_id =firebaseUser.getUid();

        Log.d("***","User ID" + currentUser_id);

        DocumentReference docRef = db.collection("Students").document(currentUser_id);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        String name = document.getString("name");
                        String mobile = document.getString("mobile");
                        final String image = document.getString("image");
                        mName.setText(name);
                        mMobile.setText(mobile);

                        if(image.equals("default")){
                            circleImageView.setImageResource(R.drawable.ic_person);
                        }else{
                            Picasso.get().load(image).networkPolicy(NetworkPolicy.OFFLINE).into(circleImageView, new Callback() {
                                @Override
                                public void onSuccess() {

                                }

                                @Override
                                public void onError(Exception e) {
                                    Picasso.get().load(image).into(circleImageView);
                                }
                            });
                        }

                    }
                }
            }
        });
        /*
        db.collection("Students").document(currentUser)
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    if(task.getResult().exists()){
                        String name = task.getResult().getString("name");
                        mName.setText(name);
                    }
                }
            }
        });

        /*
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //String name= Objects.requireNonNull(dataSnapshot.child("name").getValue()).toString();
//                String mobileNo=dataSnapshot.child("mobile").getValue().toString();
                final String image=dataSnapshot.child("image").getValue().toString();
                //mName.setText(name);
              //  mMobile.setText(mobileNo);

                //Work on Progress.
                if(image.equals("default")){
                    circleImageView.setImageResource(R.drawable.ic_person);
                }else{
                    Picasso.get().load(image).networkPolicy(NetworkPolicy.OFFLINE).into(circleImageView, new Callback() {
                        @Override
                        public void onSuccess() {

                        }

                        @Override
                        public void onError(Exception e) {
                            Picasso.get().load(image).into(circleImageView);
                        }
                    });
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
*/
        //Select image from gallery.
        img_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);

                startActivityForResult(Intent.createChooser(intent, "SELECT IMAGE"), GALLERY_PICK);
            }
        });
    }

    /*
        private void save(String date ){
            sp=getSharedPreferences("SP_DATE",MODE_PRIVATE);

            SharedPreferences.Editor editor = sp.edit();
            editor.putString("DATE", date);
            editor.apply();

            Toast.makeText(this,"Date Saved",Toast.LENGTH_SHORT).show();
        }

        private void loadData(){
            String date = sp.getString("DATE",date_ofBirth.getText().toString());
            date_ofBirth.setText(date);
        }
    */

    //Crop and Upload image
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GALLERY_PICK && resultCode == RESULT_OK) {
            assert data != null;
            Uri imageUrl = data.getData();

            CropImage.activity(imageUrl)
                    .setAspectRatio(1, 1)
                    .start(this);
        }
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);

            if (resultCode == RESULT_OK) {
                assert result != null;
                Uri resultUri = result.getUri();

                FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                final String currentUser_id =firebaseUser.getUid();

                final StorageReference ref = img_storageRef.child("profile_images").child(currentUser_id + ".jpg");
                StorageTask uploadTask = ref.putFile(resultUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                db.collection("Students").document(currentUser_id)
                                        .update("image",uri.toString());
                            }
                        });
                    }
                });
                        /*
                        addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                        if(task.isSuccessful()){

                        }else{
                            String error = task.getException().getMessage();
                            Toast.makeText(ProfileS.this,"Error:" + error,Toast.LENGTH_SHORT).show();
                        }
                    }
                });

              /*  uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                        if (!task.isSuccessful()) {
                            throw task.getException();
                        }
                        // Continue with the task to get the download URL
                        return ref.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if (task.isSuccessful()) {
                            Uri downloadUri = task.getResult();
                            String mUrl = downloadUri.toString();
                            reference.child("image").setValue(mUrl).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    Toast.makeText(ProfileS.this, "Image Loaded SuccessFully", Toast.LENGTH_SHORT).show();
                                }
                            });
                        } else {
                            Toast.makeText(ProfileS.this, "Error Loading Image", Toast.LENGTH_SHORT).show();
                        }
                    }
                });*/
            }
        }
    }
}
