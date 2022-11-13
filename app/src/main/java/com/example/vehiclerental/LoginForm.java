package com.example.vehiclerental;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class LoginForm extends AppCompatActivity {
    TextInputLayout logemail, logpassword;
    Button callSignUp, submit;
    private FirebaseAuth mAuth;
    FirebaseUser uAuth;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_form);

        logemail = findViewById(R.id.Login_email);
        logpassword = findViewById(R.id.Login_password);
        callSignUp = findViewById(R.id.create_signup);
        submit = findViewById(R.id.submitBtn);
        mAuth = FirebaseAuth.getInstance();
        uAuth = mAuth.getCurrentUser();
        progressDialog = new ProgressDialog(LoginForm.this);
        callSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginForm.this, SignUpForm.class);
                startActivity(intent);
                finish();
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkCreditials();
            }
        });
    }
    private Boolean validateEmail() {
        String val = logemail.getEditText().getText().toString();
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        if (val.isEmpty()) {
            logemail.setError("Field cannot be empty");
            return false;
        } else if (!val.matches(emailPattern)) {
            logemail.setError("Invalid email address");
            return false;
        } else {
            logemail.setError(null);
            logemail.setErrorEnabled(false);
            return true;
        }
    }
    private Boolean validatePassword() {
        String val = logpassword.getEditText().getText().toString();
        String passwordVal = "^" +
                //"(?=.*[0-9])" +         //at least 1 digit
                //"(?=.*[a-z])" +         //at least 1 lower case letter
                //"(?=.*[A-Z])" +         //at least 1 upper case letter
                "(?=.*[a-zA-Z])" +      //any letter
                "(?=.*[@#$%^&+=])" +    //at least 1 special character
                "(?=\\S+$)" +           //no white spaces
                ".{4,}" +               //at least 4 characters
                "$";
        if (val.isEmpty()) {
            logpassword.setError("Field cannot be empty");
            return false;
        } else if (!val.matches(passwordVal)) {
            logpassword.setError("Password is too weak");
            return false;
        } else {
            logpassword.setError(null);
            logpassword.setErrorEnabled(false);
            return true;
        }
    }

    private void checkCreditials(){
        String checkemail = logemail.getEditText().getText().toString();
        String checkpassword = logpassword.getEditText().getText().toString();
        if(!validateEmail() | !validatePassword()){
            return;
        }else{
            progressDialog.setTitle("Login");
            progressDialog.setMessage("Please wait while Login...");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();

            mAuth.signInWithEmailAndPassword(checkemail, checkpassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        Toast.makeText(LoginForm.this, "Registration Successfull", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                        Intent intent = new Intent(LoginForm.this, MainPageActivity.class);
                        intent.setFlags(intent.FLAG_ACTIVITY_CLEAR_TASK | intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }else{
                        logemail.setError("Email is not register");
                        logemail.requestFocus();
                        logpassword.setError("Password is not register");
                        logpassword.requestFocus();
                        progressDialog.dismiss();
                    }
                }
            });
        }
    }
}