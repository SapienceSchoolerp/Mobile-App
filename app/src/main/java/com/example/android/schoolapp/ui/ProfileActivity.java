package com.example.android.schoolapp.ui;

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

import com.example.android.schoolapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;


import java.util.Calendar;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends AppCompatActivity {

    private static final int GALLERY_PICK = 1;


    CircleImageView circleImageView;
    TextView mName, mMobile, date_ofBirth, class2;
    Button img_btn;
    ImageButton btnDate;

    Calendar c;
    DatePickerDialog dpd;

    private FirebaseFirestore db;
    private StorageReference img_storageRef;

    private String name, mobile, image, date, mClass;
    private String currentUser_id;

    DocumentReference docRef;


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_s);

        db = FirebaseFirestore.getInstance();

        circleImageView = findViewById(R.id.disImg);
        mName = findViewById(R.id.profileUsername2);
        mMobile = findViewById(R.id.mobile2);
        img_btn = findViewById(R.id.imageBtn);
        btnDate = findViewById(R.id.dateBtn);
        date_ofBirth = findViewById(R.id.dateOfBirth2);
        class2 = findViewById(R.id.class2);

        img_storageRef = FirebaseStorage.getInstance().getReference();

        //Toolbar for ProfileActivity Setting
        Toolbar toolbar = findViewById(R.id.bar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle("ProfileActivity");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });




        //Get currentUser Id.
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        assert firebaseUser != null;
        currentUser_id = firebaseUser.getUid();

        setUpDetail();

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

        btnDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setDate();
            }
        });



    }

    //Crop and Upload image
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GALLERY_PICK && resultCode == RESULT_OK) {
            assert data != null;
            Uri imageUrl = data.getData();

            CropImage.activity(imageUrl)
                    .setGuidelines(CropImageView.Guidelines.ON)
                    .setAspectRatio(1, 1)
                    .start(this);
        }
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);

            if (resultCode == RESULT_OK) {
                assert result != null;
                Uri resultUri = result.getUri();

                FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                assert firebaseUser != null;
                final String currentUser_id = firebaseUser.getUid();

                final StorageReference ref = img_storageRef.child("profile_images").child(currentUser_id + ".jpg");

                ref.putFile(resultUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(final Uri uri) {
                                db.collection("Students").document(currentUser_id)
                                        .update("image", uri.toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            Toast.makeText(ProfileActivity.this, "Image loaded successfully", Toast.LENGTH_SHORT).show();
                                            setUpImage(uri.toString());
                                        }
                                    }
                                });
                            }
                        });
                    }
                });
            }
        }
    }

    //Set date to Firebase.
    private void setDate() {
        c = Calendar.getInstance();
        int day = c.get(Calendar.DAY_OF_MONTH);
        int month = c.get(Calendar.MONTH);
        int year = c.get(Calendar.YEAR);
        dpd = new DatePickerDialog(ProfileActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int mYear, int mMonth, int mDay) {

                date_ofBirth.setText(mDay + "/" + (mMonth + 1) + "/" + mYear);
                String date = date_ofBirth.getText().toString();
                Log.d("****","date"+date);

                storeDate(date);
            }
        }, day, month, year);
        dpd.show();
    }

    private void setUpImage(final String image) {
        if (image.equals("default")) {
            circleImageView.setImageResource(R.drawable.ic_person);
        } else {

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

    private void setUpDetail() {
        docRef = db.collection("Students").document(currentUser_id);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    assert document != null;
                    if (document.exists()) {
                        name = document.getString("name");
                        mobile = document.getString("mobile");
                        image = document.getString("image");
                        date = document.getString("dateBirth");
                        mClass = document.getString("class");

                        mName.setText(name);
                        mMobile.setText(mobile);
                        date_ofBirth.setText(date);
                        class2.setText(mClass);

                        assert image != null;
                        setUpImage(image);
                    }
                }
            }

        });

    }

    private void setDateOfBirth(){
        docRef = db.collection("Students").document(currentUser_id);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){

                    DocumentSnapshot doc = task.getResult();
                    assert doc != null;
                    if(doc.exists()){
                        date = doc.getString("dateBirth");
                        date_ofBirth.setText(date);
                    }
                }
            }
        });
    }

    private void storeDate(String uDate){
        db.collection("Students").document(currentUser_id)
                .update("dateBirth", uDate).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    setDateOfBirth();
                    Toast.makeText(ProfileActivity.this,"Successfully Upload",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

}
