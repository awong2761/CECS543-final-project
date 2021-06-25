package com.example.project1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class Welcome extends AppCompatActivity {

    private TextView txtMessage;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Passes info from previous activity and outputs welcome message
        txtMessage = findViewById(R.id.message);
        String username = getIntent().getStringExtra("username");
        txtMessage.setText(username);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        Context context = getApplicationContext();
        String selection = item.getTitle().toString();
        if(selection.equals("developer info")) {
            Intent devInfo = new Intent(context, DevInfo.class);
            startActivity(devInfo);
        }
        return true;
    }
}