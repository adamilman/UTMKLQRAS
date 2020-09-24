package com.example.utmklqras;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class HomePageLecturerActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    private Button logout;
    TextView textView5;
    CardView myProfile, listData, clock, qr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage_lecturer);

        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setBackgroundDrawable(getResources().getDrawable(R.drawable.mygradient));
        }

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();

        DatabaseReference databaseReference = firebaseDatabase.getReference("Users").child(firebaseAuth.getUid());

        myProfile = findViewById(R.id.myProfile);
        listData = findViewById(R.id.listData);
        textView5 = findViewById(R.id.textView5);
        clock = findViewById(R.id.clock);
        qr = findViewById(R.id.qr);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                UserProfileActivity userProfile = snapshot.getValue(UserProfileActivity.class);
                textView5.setText(userProfile.getUserName());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        myProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomePageLecturerActivity.this, ProfileLecturerActivity.class));
            }
        });

        listData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomePageLecturerActivity.this, UserDataLecturerActivity.class));
            }
        });

        clock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomePageLecturerActivity.this, ClockActivity.class));
            }
        });

        qr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomePageLecturerActivity.this, QRGeneratorActivity.class));
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_lecturer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menuprofile:
                startActivity(new Intent(HomePageLecturerActivity.this, ProfileLecturerActivity.class));
                return true;
            case R.id.menulogout:{
                Logout();
            }
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void Logout(){
        firebaseAuth.signOut();
        finish();
        startActivity(new Intent(HomePageLecturerActivity.this, LoginActivity.class));
    }
}