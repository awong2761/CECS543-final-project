package com.example.project1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.project1.ui.help.HelpFragment;
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
    private EditText age;

    String profilePic = "nice";
    String feetNum;
    String inchNum;
    String curWeight;
    String gWeight;
    String aLevel;
    String gender;
    String userAge;
    String caloriesLeft;
    int caloriesLeftNum;
    double height;
    double kgWeight;

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
        age = findViewById(R.id.age_input);
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

        actspin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id){
                ((TextView) view).setTextColor(Color.WHITE);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent){
            }
        });

        genderspin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id){
                ((TextView) view).setTextColor(Color.WHITE);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent){
            }
        });

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
                userAge = age.getText().toString();
                profilePic = "android.resource://com.example.project1/2131165272";

                height = (Integer.parseInt(feetNum)*30.48) + (Integer.parseInt(inchNum)*2.54);
                kgWeight = (Double.parseDouble(curWeight)*0.453592);


                if(gender == "Male") {
                    caloriesLeftNum = (int)(10*(Double.parseDouble(gWeight)) + (6.25*height) - (5*(Integer.parseInt(userAge))) + 5);
                }
                else {
                    caloriesLeftNum = (int)((10*(Double.parseDouble(gWeight))) + (6.25*height) - (5*(Integer.parseInt(userAge))) - 161);
                }

                if(aLevel == "Low") {
                    caloriesLeftNum += 100;
                }
                if(aLevel == "Moderate") {
                    caloriesLeftNum += 150;
                }
                if(aLevel == "High") {
                    caloriesLeftNum += 200;
                }
                //lose 1 lb per week
                if(Double.parseDouble(gWeight) < Double.parseDouble(curWeight)) {
                    caloriesLeft = String.valueOf(caloriesLeftNum -= 500);
                }
                //gain 1lb per week
                else
                    caloriesLeft = String.valueOf(caloriesLeftNum += 500);



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
        UserProfile userProfile = new UserProfile(feetNum, inchNum, curWeight, gWeight ,aLevel, gender, caloriesLeft, userAge, profilePic);
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