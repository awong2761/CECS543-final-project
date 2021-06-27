package com.example.project1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

public class UserInfo extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    String[] gender = {"Male", "Female"};
    String[] act_lvl = {"Low", "Moderate", "High"};
    private Button finished;
    private Context context;
    private EditText feet;
    private EditText inches;
    private EditText currentweight;
    private EditText goalweight;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    UserGoalInfo userInfo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);

        feet = findViewById(R.id.feet);
        inches = findViewById(R.id.inches);
        currentweight = findViewById(R.id.currentweighttest);
        goalweight = findViewById(R.id.goal_weight_input);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("UserGoalInfo");

        userInfo = new UserGoalInfo();


        // Spinner
        Spinner actspin = findViewById(R.id.act_level);
        actspin.setOnItemSelectedListener(this);
        ArrayAdapter<String> levels = new ArrayAdapter<>(this, android.R.layout.
                simple_spinner_dropdown_item, act_lvl);
        levels.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        actspin.setAdapter(levels);

        Spinner genderspin = findViewById(R.id.gender_spin);
        actspin.setOnItemSelectedListener(this);
        ArrayAdapter<String> genders= new ArrayAdapter<>(this, android.R.layout.
                simple_spinner_dropdown_item, gender);
        levels.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        genderspin.setAdapter(genders);

        context = getApplicationContext();

        finished = findViewById(R.id.finish_user);

        finished.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                String feetNum = feet.getText().toString();
                String inchNum = inches.getText().toString();
                String curWeight = currentweight.getText().toString();
                String gWeight = goalweight.getText().toString();
                String aLevel = actspin.getSelectedItem().toString();
                String gender = genderspin.getSelectedItem().toString();

                if(TextUtils.isEmpty(feetNum) || TextUtils.isEmpty(inchNum) ||
                        TextUtils.isEmpty(curWeight) || TextUtils.isEmpty(gWeight)){
                    Toast.makeText(context, "Please fill all fields."
                            , Toast.LENGTH_SHORT).show();
                }
                else{
                    addDatatoFirebase(feetNum, inchNum, curWeight, gWeight, aLevel, gender);
                    Intent done = new Intent(context, Login.class);
                    startActivity(done);
                    Toast.makeText(context, "Account has been created, " +
                            "you can now login", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
    private void addDatatoFirebase(String feetNum, String inchNum, String curWeight, String gWeight,
                                   String aLevel, String gender){
        userInfo.setFeet(feetNum);
        userInfo.setInches(inchNum);
        userInfo.setCurrentweight(curWeight);
        userInfo.setGoalweight(gWeight);
        userInfo.setActivityLevel(aLevel);
        userInfo.setGender(gender);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                databaseReference.setValue(userInfo);
                Toast.makeText(context, "success", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onCancelled(@NonNull  DatabaseError error) {
                Toast.makeText(context, "fail", Toast.LENGTH_SHORT).show();
            }
        });
    }



    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }
    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
    private boolean isEmpty(EditText etText) {
        return etText.getText().toString().trim().length() == 0;
    }
}