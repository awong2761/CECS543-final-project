package com.example.project1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


import org.jetbrains.annotations.NotNull;

/**
 *  CECS453 Project #1
 *  Richard Nguyen, SID: 26698215
 *  Andy Wong     , SID: 26682641
 */

public class Login extends AppCompatActivity {

    private Button btnLogin;
    private Button btnSignup;
    private EditText email;
    private EditText password;
    private FirebaseAuth firebaseAuth;
    private Context context;
    private ProgressDialog progressDialog;
    Data data = new Data();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        context = getApplicationContext();
        btnLogin = findViewById(R.id.Login);
        btnSignup = findViewById(R.id.Signup);
        email = findViewById(R.id.email);
        password = findViewById(R.id.Password);
        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);
        FirebaseUser user = firebaseAuth.getCurrentUser();

        if(user == null) {
            Intent login = new Intent(context, Navigation.class);
            finish();
            startActivity(login);
        }



        // Login button leads to welcome activity page
        btnLogin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                validate (email.getText().toString(), password.getText().toString());
//                String email = username.getText().toString();
//                String pass = password.getText().toString();
//
//                Intent login = new Intent(context, Navigation.class);
//
//
//                // Checks to see if username and passwords checks out, if so it starts next activity
//                if(data.CheckCredentials(user, pass) == true){
//                    login.putExtra("username", username.getText().toString());
//                    startActivity(login);
//                }
//                // Checks user name, if false send Toast that user does not exist
//                else if(data.CheckUsername(user) == false) {
//                    Toast.makeText(context, "Username does not exist", Toast.LENGTH_SHORT).show();
//                    password.setText("");
//                }
//                // Notifies that password is incorrect if username checks out
//                else {
//                    Toast.makeText(context, "Incorrect password", Toast.LENGTH_SHORT).show();
//                    password.setText("");
//                }
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

    private void validate(String userName, String pass) {

        progressDialog.setMessage("Logging in");
        progressDialog.show();

        firebaseAuth.signInWithEmailAndPassword(userName, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<AuthResult> task) {
                progressDialog.dismiss();
                if(task.isSuccessful()) {
                    Intent login = new Intent(context, Navigation.class);
                    FirebaseUser user = firebaseAuth.getInstance().getCurrentUser();
                    String displayName = user.getDisplayName();
                    login.putExtra("displayName", displayName);
                    Toast.makeText(Login.this, "Login Successful", Toast.LENGTH_SHORT).show();
                    finish();
                    startActivity(login);
                } else {
                    Toast.makeText(Login.this, "Login Failed", Toast.LENGTH_SHORT).show();

                }
            }
        });
    }


}