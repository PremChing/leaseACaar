package com.example.vehiclerental;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;

public class UserActivityProfile extends AppCompatActivity {

    TextInputLayout fullname, email, phoneNo, password;
    TextView fullnameLabel, usernamelabel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        fullname = findViewById(R.id.fullname_profile);
        fullname = findViewById(R.id.email_profile);
        fullname = findViewById(R.id.phonenumber_profile);
        fullname = findViewById(R.id.password_profile);
        fullnameLabel = findViewById(R.id.FullName_field);
        usernamelabel = findViewById(R.id.username_field);

        showAllUserData();
    }

    private void showAllUserData() {

        Intent intent = getIntent();
        String user_username = intent.getStringExtra("username");
        String user_name = intent.getStringExtra("name");
        String user_email = intent.getStringExtra("email");
        String user_phoneNo = intent.getStringExtra("phoneNo");
        String user_password = intent.getStringExtra("password");

        fullnameLabel.setText(user_name);
        usernamelabel.setText(user_username);
        fullname.getEditText().setText(user_name);
        email.getEditText().setText(user_email);
        phoneNo.getEditText().setText(user_phoneNo);
        password.getEditText().setText(user_password);
    }
}