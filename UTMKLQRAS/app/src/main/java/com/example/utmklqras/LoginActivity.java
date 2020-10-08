package com.example.utmklqras;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {

    private EditText Name;
    private EditText Password;
    private Button Login;
    private int counter = 5;
    private TextView userRegistration;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;
    private TextView forgotPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();}

        Name = (EditText)findViewById(R.id.etName);
        Password = (EditText)findViewById(R.id.etPasswordEmail);
        Login = (Button)findViewById(R.id.btnLogin);
        userRegistration = (TextView)findViewById(R.id.tvRegister);
        forgotPassword = findViewById(R.id.tvForgotPassword);

        Name.setHintTextColor(getResources().getColor(R.color.white));
        Password.setHintTextColor(getResources().getColor(R.color.white));

        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validate(Name.getText().toString(), Password.getText().toString());
            }
        });

        userRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegistrationActivity.class));
            }
        });

        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, PasswordActivity.class));
            }
        });
    }

    private void validate(String userName, String userPassword){
        firebaseAuth = FirebaseAuth.getInstance();

        progressDialog.setMessage("Welcome to UTMKLQRAS");
        progressDialog.show();

        firebaseAuth.signInWithEmailAndPassword(userName, userPassword)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    progressDialog.dismiss();
                    checkEmailVerification();

                    String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                    DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference().child("Users");
                    DatabaseReference uidRef = rootRef.child(uid);

                    uidRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            String ut = snapshot.child("userType").getValue(String.class);
                                    //UserProfileActivity userProfile = snapshot.getValue(UserProfileActivity.class);
                                if (ut.equals("Admin")) {
                                    startActivity(new Intent(LoginActivity.this, HomePageAdminActivity.class));
                                } else if (ut.equals("Lecturer")) {
                                    startActivity(new Intent(LoginActivity.this, HomePageLecturerActivity.class));
                                } else if (ut.equals("Student")) {
                                    startActivity(new Intent(LoginActivity.this, HomePageStudentActivity.class));
                                } else
                                    Toast.makeText(LoginActivity.this, "gg ", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Toast.makeText(LoginActivity.this, "ggggggg ", Toast.LENGTH_SHORT).show();
                        }
                    });
                }

                else {
                    Toast.makeText(LoginActivity.this, "Login Failed", Toast.LENGTH_SHORT).show();
                    counter--;
                    Toast.makeText(LoginActivity.this, "No of attempts remaining: " + counter, Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                    if(counter == 0){
                        Login.setEnabled(false);
                    }
                }
            }
        });
    }

    private void checkEmailVerification(){
        FirebaseUser firebaseUser = firebaseAuth.getInstance().getCurrentUser();
        Boolean emailflag = firebaseUser.isEmailVerified();

        if(emailflag){
            finish();
        }
        else {
            Toast.makeText(this,"Verify your email",Toast.LENGTH_SHORT).show();
            firebaseAuth.signOut();
        }
    }
}
