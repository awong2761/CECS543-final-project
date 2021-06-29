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

import com.google.android.material.slider.Slider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

public class UserInfo extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    String[] gen = {"Male", "Female"};
    String[] act_lvl = {"Low", "Moderate", "High"};
    private Button finished;
    private Context context;
    private EditText feet;
    private EditText inches;
    private Slider currentweight;
    private Slider goalweight;

    String feetNum;
    String inchNum;
    String curWeight;
    String gWeight;
    String aLevel;
    String gender;
    double height;
    double kgWeight;
    int caloriesLeft;

    FirebaseDatabase firebaseDatabase;
    FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);

        feet = findViewById(R.id.feet);
        inches = findViewById(R.id.inches);
        currentweight = findViewById(R.id.current_weight_slider);
        goalweight = findViewById(R.id.goal_weight_slider);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();




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
                simple_spinner_dropdown_item, gen);
        levels.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        genderspin.setAdapter(genders);

        context = getApplicationContext();

        finished = findViewById(R.id.finish_user);

        finished.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                feetNum = feet.getText().toString();
                inchNum = inches.getText().toString();
                curWeight = String.valueOf(currentweight.getValue());
                gWeight = String.valueOf(goalweight.getValue());
                aLevel = actspin.getSelectedItem().toString();
                gender = genderspin.getSelectedItem().toString();

                height = (Integer.parseInt(feetNum)*30.48) + (Integer.parseInt(inchNum)*2.54);
                kgWeight = (Integer.parseInt(curWeight)*0.453592);



                if(gender == "Male") {
                    caloriesLeft = (int)(13.397*(Integer.parseInt(gWeight)) + (4.799));
                    if(aLevel == "Low") {
                        caloriesLeft += 100;
                    }
                    if(aLevel == "Moderate") {
                        caloriesLeft += 200;
                    }
                    if(aLevel == "High") {
                        caloriesLeft += 300;
                    }
                }


                if(TextUtils.isEmpty(feetNum) || TextUtils.isEmpty(inchNum) ||
                        TextUtils.isEmpty(curWeight) || TextUtils.isEmpty(gWeight)){
                    Toast.makeText(context, "Please fill all fields."
                            , Toast.LENGTH_SHORT).show();
                }
                else{
                    sendUserData();
                    //addDatatoFirebase(feetNum, inchNum, curWeight, gWeight, aLevel, gender);
                    Intent done = new Intent(context, Navigation.class);
                    FirebaseUser user = firebaseAuth.getInstance().getCurrentUser();
                    String displayName = user.getDisplayName();
                    done.putExtra("displayName", displayName);
                    finish();
                    startActivity(done);
                    Toast.makeText(context, "Your account has been created!"
                            , Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private void sendUserData() {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference myRef = firebaseDatabase.getReference(firebaseAuth.getUid());
        UserProfile userProfile = new UserProfile(feetNum, inchNum, curWeight, gWeight ,aLevel, gender, caloriesLeft);
        myRef.setValue(userProfile);
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