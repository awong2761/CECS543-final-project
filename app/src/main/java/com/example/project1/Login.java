package com.example.project1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.Serializable;

/**
 *  CECS453 Project #1
 *  Richard Nguyen, SID: 26698215
 *  Andy Wong     , SID: 26682641
 */

public class Login extends AppCompatActivity {

    private Button btnLogin;
    private Button btnSignup;
    private EditText username;
    private EditText password;
    private Context context;
    Data data = new Data();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        context = getApplicationContext();
        btnLogin = findViewById(R.id.Login);
        btnSignup = findViewById(R.id.Signup);
        username = findViewById(R.id.Username);
        password = findViewById(R.id.Password);

        // Login button leads to welcome activity page
        btnLogin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String user = username.getText().toString();
                String pass = password.getText().toString();

                Intent login = new Intent(context, Welcome.class);

                // Checks to see if username and passwords checks out, if so it starts next activity
                if(data.CheckCredentials(user, pass) == true){
                    login.putExtra("username", username.getText().toString());
                    startActivity(login);
                }
                // Checks user name, if false send Toast that user does not exist
                else if(data.CheckUsername(user) == false) {
                    Toast.makeText(context, "Username does not exist", Toast.LENGTH_SHORT).show();
                    password.setText("");
                }
                // Notifies that password is incorrect if username checks out
                else {
                    Toast.makeText(context, "Incorrect password", Toast.LENGTH_SHORT).show();
                    password.setText("");
                }
            }
        });

        // Sign up button leads to signup activity page
        btnSignup.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent signUp = new Intent(context, Signup.class);
                startActivity(signUp);
            }
        });
    }
}