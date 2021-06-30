package com.example.project1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.os.Bundle;
import android.text.TextPaint;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
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
    private TextView loginlogotext;

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
        loginlogotext = findViewById(R.id.loginlogotext);
        FirebaseUser user = firebaseAuth.getCurrentUser();

        if(user != null) {
            Intent login = new Intent(context, Navigation.class);
            user = firebaseAuth.getInstance().getCurrentUser();
            String displayName = user.getDisplayName();
            login.putExtra("displayName", displayName);
            finish();
            startActivity(login);
        }

        loginlogotext = findViewById(R.id.loginlogotext);
        TextPaint paint = loginlogotext.getPaint();
        float width = paint.measureText(loginlogotext.getText().toString());
        Shader textShader = new LinearGradient(0, 0, width, loginlogotext.getTextSize(),
                new int[]{
                        Color.parseColor("#3DBDB0"),
                        Color.parseColor("#04B5CE"),
                }, null, Shader.TileMode.CLAMP);
        loginlogotext.getPaint().setShader(textShader);
        loginlogotext.setTextColor(Color.parseColor("#3DBDB0"));


        // Login button leads to welcome activity page
        btnLogin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                validate (email.getText().toString(), password.getText().toString());
            }

        });

        // Sign up button leads to signup activity page
        btnSignup.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent signUp = new Intent(context, Signup.class);
                finish();
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
                    finish();
                    startActivity(login);
                } else {
                    Toast.makeText(Login.this, "Login Failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


}