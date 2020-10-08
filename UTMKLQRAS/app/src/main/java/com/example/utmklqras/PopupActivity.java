package com.example.utmklqras;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.media.audiofx.EnvironmentalReverb;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.spec.EncodedKeySpec;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class PopupActivity extends AppCompatActivity {

    private TextView statusAttendance, masa, day, month, user, matricno;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    Button screenshotButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popup);

        verifyStoragePermission(this);

        ActivityCompat.requestPermissions(this, new String[]{WRITE_EXTERNAL_STORAGE, READ_EXTERNAL_STORAGE}, PackageManager.PERMISSION_GRANTED);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();

        DatabaseReference databaseReference = firebaseDatabase.getReference("Users").child(firebaseAuth.getUid());

        masa = findViewById(R.id.time);
        day = findViewById(R.id.day);
        month = findViewById(R.id.month);
        user = findViewById(R.id.user);
        matricno = findViewById(R.id.matricno);
        screenshotButton = findViewById(R.id.screenshotButton);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                UserProfileActivity userProfile = snapshot.getValue(UserProfileActivity.class);
                user.setText(userProfile.getUserName());
                matricno.setText(userProfile.getUserMatric());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        screenshotButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takeScreenshot(getWindow().getDecorView().getRootView(), "result");
                Toast.makeText(PopupActivity.this, "Screenshot saved to gallery", Toast.LENGTH_SHORT).show();
            }
        });

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat("hh:mm aaa");
        String thistime = format.format(calendar.getTime());

        Date currentTime = Calendar.getInstance().getTime();
        String formattedDate = DateFormat.getDateInstance(DateFormat.FULL).format(currentTime);

        String[] splitDate = formattedDate.split(",");

        Log.d("myLOG", currentTime.toString());
        Log.d("myLOG", formattedDate);

        month.setText(splitDate[1]);
        day.setText(splitDate[0]);
        masa.setText(thistime);

        Log.d("myLOG", splitDate[0].trim());
        Log.d("myLOG", splitDate[1].trim());

        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setBackgroundDrawable(getResources().getDrawable(R.drawable.mygradient));
        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        statusAttendance = findViewById(R.id.statusAttendance);

        String key = super.getIntent().getExtras().getString("key");
        statusAttendance.setText(key);
    }

    protected static File takeScreenshot(View view, String fileName){
        Date date = new Date();

        try{
            String dirPath = Environment.getExternalStorageDirectory().toString() +"/Download/";
            File fileDir = new File(dirPath);
            if(!fileDir.exists()){
                boolean mkdir = fileDir.mkdir();
            }
            String path = dirPath + "/" + fileName + "-" + ".jpeg";

            view.setDrawingCacheEnabled(true);
            Bitmap bitmap = Bitmap.createBitmap(view.getDrawingCache());
            view .setDrawingCacheEnabled(false);

            File imageFile =  new File(path);

            FileOutputStream fileOutputStream = new FileOutputStream(imageFile);
            int quality = 100;
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();
            return imageFile;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        }

        return null;
    }

    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSION_STORAGE = {
            WRITE_EXTERNAL_STORAGE, READ_EXTERNAL_STORAGE
    };

    public static void verifyStoragePermission(Activity activity){
        int permission = ActivityCompat.checkSelfPermission(activity, WRITE_EXTERNAL_STORAGE);

        if(permission != PackageManager.PERMISSION_DENIED){
            ActivityCompat.requestPermissions(activity, PERMISSION_STORAGE, REQUEST_EXTERNAL_STORAGE);
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_student, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menuprofile:
                startActivity(new Intent(PopupActivity.this, ProfileStudentActivity.class));
                return true;
            case R.id.menulogout:{
                Logout();
            }
            case R.id.menuhomepagestudent:
                startActivity(new Intent(PopupActivity.this, HomePageStudentActivity.class));
                return true;
            case android.R.id.home:
                onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    private void Logout(){
        firebaseAuth.signOut();
        finish();
        startActivity(new Intent(PopupActivity.this, LoginActivity.class));
    }
}