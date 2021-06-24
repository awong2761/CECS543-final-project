package com.example.project1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class Welcome extends AppCompatActivity {

    private TextView txtMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        //TESTER TESTING
        // Passes info from previous activity and outputs welcome message
        txtMessage = findViewById(R.id.message);
        String username = getIntent().getStringExtra("username");
        txtMessage.setText("Welcome " + username + "!");
    }
}