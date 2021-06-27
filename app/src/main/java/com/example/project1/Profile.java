package com.example.project1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

public class Profile extends AppCompatActivity {

    private TextView height;
    private TextView currentWeight;
    private TextView goalWeight;
    private TextView activityLevel;
    private TextView gender;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase userData;
    private Toolbar toolbar;
    private String user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        height = findViewById(R.id.profile_height);
        currentWeight = findViewById(R.id.profile_current_weight);
        goalWeight = findViewById(R.id.profile_goal_weight);
        activityLevel = findViewById(R.id.profile_activity_level);
        gender = findViewById(R.id.profile_gender);
        toolbar = findViewById(R.id.profile_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        user = getIntent().getStringExtra("displayName");

        firebaseAuth = FirebaseAuth.getInstance();
        userData = FirebaseDatabase.getInstance();

        DatabaseReference databaseReference = userData.getReference(firebaseAuth.getUid());

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                UserProfile userProfile = snapshot.getValue(UserProfile.class);
                height.setText("Height: " + userProfile.getFeetNum() + "'" + userProfile.getInchNum());
                currentWeight.setText("Current Weight: " + userProfile.getCurWeight());
                goalWeight.setText("Goal Weight: " + userProfile.getGoalWeight());
                activityLevel.setText("Activity Level: " + userProfile.getaLevel());
                gender.setText("Gender: " + userProfile.getGender());


            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
                Toast.makeText(Profile.this, "Error", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case android.R.id.home:
                Intent home = new Intent(getApplicationContext(), Navigation.class);
                home.putExtra("displayName", user);
                finish();
                startActivity(home);
        }
        return true;
    }
}