package com.example.utmklqras;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegistrationActivity extends AppCompatActivity {

    private EditText userName, userPassword, userEmail, userMatric;
    private Button regButton;
    private TextView userLogin;
    private FirebaseAuth firebaseAuth;
    private ImageView userProfilePic;
    String email, name, matric, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        setupUIViews();

        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setBackgroundDrawable(getResources().getDrawable(R.drawable.mygradient));
        }

        firebaseAuth = FirebaseAuth.getInstance();

        regButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(validate()){

                    //upload data to database
                    String user_email = userEmail.getText().toString().trim();
                    String user_password = userPassword.getText().toString().trim();

                    firebaseAuth.createUserWithEmailAndPassword(user_email, user_password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()) {
                                sendEmailVerification();
                                sendUserData();
                                Toast.makeText(RegistrationActivity.this, "Succesfully Registered, Upload Completed!", Toast.LENGTH_SHORT).show();
                                //firebaseAuth.signOut();
                                finish();
                                startActivity(new Intent(RegistrationActivity.this, LoginActivity.class));
                            }
                            else{
                                Toast.makeText(RegistrationActivity.this, "Registration Failed", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });

        userLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegistrationActivity.this, LoginActivity.class));
            }
        });
    }

    private void setupUIViews(){

        userName = findViewById(R.id.etUserName);
        userPassword = findViewById(R.id.etUserPassword);
        userEmail = findViewById(R.id.etUserEmail);
        userMatric = findViewById(R.id.etMatric);
        regButton = findViewById(R.id.btnRegister);
        userLogin = findViewById(R.id.tvUserLogin);
        //userProfilePic = findViewById(R.id.ivProfile);

        userName.setHintTextColor(getResources().getColor(R.color.white));
        userPassword.setHintTextColor(getResources().getColor(R.color.white));
        userEmail.setHintTextColor(getResources().getColor(R.color.white));
        userMatric.setHintTextColor(getResources().getColor(R.color.white));


    }

    private Boolean validate() {

        Boolean result = false;

        name = userName.getText().toString();
        password = userPassword.getText().toString();
        email = userEmail.getText().toString();
        matric = userMatric.getText().toString();

        if(name.isEmpty() || password.isEmpty() || email.isEmpty() || matric.isEmpty()) {

            Toast.makeText(this, "Please enter all the details", Toast.LENGTH_SHORT).show();
        }else{
            result = true;
        }

        return result;
    }

    private void sendEmailVerification(){
        FirebaseUser firebaseUser = firebaseAuth.getInstance().getCurrentUser();
        if(firebaseUser!= null){
            firebaseUser.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        sendUserData();
                        Toast.makeText(RegistrationActivity.this, "Succesfully Registered, Verification mail sent!", Toast.LENGTH_SHORT).show();
                        firebaseAuth.signOut();
                        finish();
                        startActivity(new Intent(RegistrationActivity.this, LoginActivity.class));
                    }
                    else{
                        Toast.makeText(RegistrationActivity.this, "Verification mail hasn't been sent!", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    private void sendUserData(){
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference myRef = firebaseDatabase.getReference(firebaseAuth.getUid());
        UserProfileActivity userProfile = new UserProfileActivity(matric, email, name);
        myRef.setValue(userProfile);
    }

}

