package com.example.project1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

public class UserInfo extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    String[] act_lvl = {"Low", "Moderate", "High"};
    private Button finish;
    private Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);

        // Spinner
        setContentView(R.layout.activity_user_info);
        Spinner actspin = findViewById(R.id.act_level);
        actspin.setOnItemSelectedListener(this);
        ArrayAdapter<String> levels = new ArrayAdapter<>(this, android.R.layout.
                simple_spinner_dropdown_item, act_lvl);
        levels.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        actspin.setAdapter(levels);

        context = getApplicationContext();
        finish = findViewById(R.id.finish_user);
        finish.setBackgroundColor(getResources().getColor(R.color.green));

        finish.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent done = new Intent(context, Login.class);
                startActivity(done);
                Toast.makeText(context, "Account has been created" +
                        "you can now login", Toast.LENGTH_SHORT).show();
            }
        });
    }



    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }
    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}