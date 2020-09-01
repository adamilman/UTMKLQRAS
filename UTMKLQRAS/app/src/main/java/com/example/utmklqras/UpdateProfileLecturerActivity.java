package com.example.utmklqras;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;

public class UpdateProfileLecturerActivity extends AppCompatActivity {

    private EditText newUserName, newUserEmail, newUserMatric, newUserPassword, newUserType;
    private Button save;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    private FirebaseUser firebaseUser;
    private FirebaseStorage firebaseStorage;
    private StorageReference storageReference;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile_lecturer);

        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setBackgroundDrawable(getResources().getDrawable(R.drawable.mygradient));
        }

        newUserName = findViewById(R.id.etNameUpdate);
        newUserEmail = findViewById(R.id.etEmailUpdate);
        newUserMatric = findViewById(R.id.etMatricUpdate);
        newUserPassword = findViewById(R.id.etPasswordUpdate);
        newUserType = findViewById(R.id.etTypeUpdate);
        save = findViewById(R.id.btnSave);
        progressDialog = new ProgressDialog(this);

        newUserName.setHintTextColor(getResources().getColor(R.color.white));
        newUserEmail.setHintTextColor(getResources().getColor(R.color.white));
        newUserMatric.setHintTextColor(getResources().getColor(R.color.white));
        newUserPassword.setHintTextColor(getResources().getColor(R.color.white));
        newUserType.setHintTextColor(getResources().getColor(R.color.white));

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        final DatabaseReference databaseReference = firebaseDatabase.getReference("Users").child(firebaseAuth.getUid());

        storageReference = firebaseStorage.getReference();

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                UserProfileActivity userProfile = snapshot.getValue(UserProfileActivity.class);
                newUserEmail.setText(userProfile.getUserEmail());
                newUserType.setText(userProfile.getUserType());
                newUserPassword.setText(userProfile.getUserPassword());
                newUserName.setText(userProfile.getUserName());
                newUserMatric.setText(userProfile.getUserMatric());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(UpdateProfileLecturerActivity.this, error.getCode(), Toast.LENGTH_SHORT).show();
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = newUserEmail.getText().toString();
                String type = newUserType.getText().toString();
                String password = newUserPassword.getText().toString();
                String name = newUserName.getText().toString();
                String matric = newUserMatric.getText().toString();

                UserProfileActivity userProfile = new UserProfileActivity(email, type, password, name, matric);

                databaseReference.setValue(userProfile);
                Toast.makeText(UpdateProfileLecturerActivity.this,"Updated Successfully!", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(UpdateProfileLecturerActivity.this, ProfileLecturerActivity.class));
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}