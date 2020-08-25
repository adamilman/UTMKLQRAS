package com.example.utmklqras;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
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

import java.util.ArrayList;

public class ProfileAdminActivity extends AppCompatActivity {

    private ImageView profilePic;
    private TextView profileName, profileMatric, profileEmail;
    private Button changePassword;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    private Spinner EditType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_admin);

        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setBackgroundDrawable(getResources().getDrawable(R.drawable.mygradient));
        }

        profilePic = findViewById(R.id.ivProfilePic);
        profileName = findViewById(R.id.tvProfileName);
        profileMatric= findViewById(R.id.tvProfileMatric);
        profileEmail = findViewById(R.id.tvProfileEmail);
        changePassword = findViewById(R.id.btnChangePassword);
        EditType = findViewById(R.id.spnrEditType);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.custom_spinner, getResources().getStringArray(R.array.edittype));

        adapter.setDropDownViewResource(R.layout.custom_spinner_dropdown);
        EditType.setAdapter(adapter);

        EditType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                String selectedItem = EditType.getItemAtPosition(position).toString();

                if(EditType.getItemAtPosition(position).equals("EDIT")) {
                }

                else {
                    if (EditType.getItemAtPosition(position).equals("EDIT PROFILE PICTURE")) {
                        Intent intent = new Intent(ProfileAdminActivity.this, UpdatePictureAdminActivity.class);
                        startActivity(intent);
                    } else if (EditType.getItemAtPosition(position).equals("EDIT PROFILE INFORMATION")) {
                        Intent intent = new Intent(ProfileAdminActivity.this, UpdateProfileAdminActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(getApplicationContext(), "An error occured. Please restart the application", Toast.LENGTH_LONG).show();
                        finish();
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        DatabaseReference databaseReference = firebaseDatabase.getReference(firebaseAuth.getUid());

        StorageReference mImageRef = FirebaseStorage.getInstance().getReference(firebaseAuth.getUid()).child("Profile Pic");
        final long ONE_MEGABYTE = 1024 * 1024;

        mImageRef.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                Bitmap bm = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                DisplayMetrics dm = new DisplayMetrics();
                getWindowManager().getDefaultDisplay().getMetrics(dm);

                profilePic.setMinimumHeight(dm.heightPixels);
                profilePic.setMinimumWidth(dm.widthPixels);
                profilePic.setImageBitmap(bm);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
            }
        });

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                UserProfileActivity userProfile = snapshot.getValue(UserProfileActivity.class);
                profileName.setText(userProfile.getUserName());
                profileEmail.setText(userProfile.getUserEmail());
                profileMatric.setText(userProfile.getUserMatric());
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Toast.makeText(ProfileAdminActivity.this, error.getCode(), Toast.LENGTH_SHORT).show();

            }
        });

        changePassword.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                startActivity(new Intent(ProfileAdminActivity.this, UpdatePasswordActivity.class));
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_admin, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.menuhomepageadmin:
                startActivity(new Intent(ProfileAdminActivity.this, HomePageAdminActivity.class));
                return true;
            case R.id.menulogout:{
                Logout();
            }
            case android.R.id.home:
                onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    private void Logout(){
        firebaseAuth.signOut();
        finish();
        startActivity(new Intent(ProfileAdminActivity.this, LoginActivity.class));
    }
}