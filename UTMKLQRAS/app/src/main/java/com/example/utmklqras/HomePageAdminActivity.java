package com.example.utmklqras;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

public class HomePageAdminActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private Button logout;
    TextView month, day, underlined1;
    CardView myProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page_admin);

        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setBackgroundDrawable(getResources().getDrawable(R.drawable.mygradient));
        }

        firebaseAuth = FirebaseAuth.getInstance();

        //underlined1 = findViewById(R.id.textView6);
        myProfile = findViewById(R.id.myProfile);

        //month = findViewById(R.id.month);
        //day = findViewById(R.id.day);

        //Date currentTime = Calendar.getInstance().getTime();
        //String formattedDate = DateFormat.getDateInstance(DateFormat.FULL).format(currentTime);

        //String[] splitDate = formattedDate.split(",");

        //Log.d("myLOG", currentTime.toString());
        //Log.d("myLOG", formattedDate);

        //month.setText(splitDate[1]);
        //day.setText(splitDate[0]);

        //Log.d("myLOG", splitDate[0].trim());
        //Log.d("myLOG", splitDate[1].trim());

        //underlined1.setText(Html.fromHtml("<u>My Profile</u>"));

        myProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomePageAdminActivity.this, ProfileAdminActivity.class));
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
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menuprofile:
                startActivity(new Intent(HomePageAdminActivity.this, ProfileAdminActivity.class));
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
        startActivity(new Intent(HomePageAdminActivity.this, LoginActivity.class));
    }
}