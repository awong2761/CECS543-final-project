package com.example.project1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Signup extends AppCompatActivity {

    private Button btnSignMeUp;
    private EditText username;
    private EditText password;
    private EditText repassword;
    private EditText email;
    private EditText phone;
    private Context context;
    Data data = new Data();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        // Assigning buttons
        context = getApplicationContext();
        btnSignMeUp = findViewById(R.id.Next);
        username = findViewById(R.id.signUsername);
        password = findViewById(R.id.signPassword);
        repassword = findViewById(R.id.signRepassword);
        email = findViewById(R.id.signEmail);
        phone = findViewById(R.id.signPhone);
        setTitle("Signup");


        // Sign Me Up Button creation to add to database
        btnSignMeUp.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String user = username.getText().toString();
                String pass = password.getText().toString();
                String repass = repassword.getText().toString();
                int cell = phone.length();
                String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
                Intent toUserInfo = new Intent(context, UserInfo.class);


                boolean correct = true;
                // Checks to see if username already exists in database
                if (data.CheckUsername(user)) {
                    Toast.makeText(context, "Username already exists", Toast.LENGTH_SHORT).show();
                    correct = false;
                }
                // Checks to see if repass matches with pass
                if (!pass.equals(repass)) {
                    Toast.makeText(context, "Passwords do not match", Toast.LENGTH_SHORT).show();
                    correct = false;
                }
                // Checks input email address to see if email box is empty
                if (email.getText().toString().isEmpty()) {
                    Toast.makeText(context, "Enter an email address", Toast.LENGTH_SHORT).show();
                    correct = false;
                    // Checks email format and returns invalid if not in correct format
                } else if (!email.getText().toString().trim().matches(emailPattern)) {
                    Toast.makeText(context, "Invalid email address", Toast.LENGTH_SHORT).show();
                    correct = false;
                }
                // Formats cell number, keeping length to standard phone number length
                if (cell != 10 && cell != 11) {
                    if (cell > 0) {
                        Toast.makeText(context, "Invalid phone number", Toast.LENGTH_SHORT).show();
                    } else
                        Toast.makeText(context, "Enter a phone number", Toast.LENGTH_SHORT).show();

                    correct = false;
                }
                // If all checks out, credentials are added into database
                if (correct) {
                    data.AddCredential(user, pass);
                    startActivity(toUserInfo);
                }
            }
        });

    }
}