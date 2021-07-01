package com.example.project1;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextPaint;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.project1.ui.food.FoodFragmentDirections;
import com.example.project1.ui.help.HelpFragment;
import com.example.project1.ui.home.HomeFragment;
import com.google.android.material.navigation.NavigationView;


import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.example.project1.databinding.ActivityNavigationBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import org.jetbrains.annotations.NotNull;

import static androidx.navigation.Navigation.findNavController;

public class Navigation extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityNavigationBinding binding;
    private DrawerLayout drawer;
    private TextView drawerUsername;
    private NavigationView navigationView;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase userData;
    private String user;
    public static ImageView profilepic;
    private Uri profilepicset;
    UserProfileChangeRequest profileUpdates;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        firebaseAuth = FirebaseAuth.getInstance();

        binding = ActivityNavigationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarNavigation.toolbar);

        drawer = binding.drawerLayout;
        navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_help, R.id.nav_food, R.id.nav_logout)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = findNavController(this, R.id.nav_host_fragment_content_navigation);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        // call if logout is clicked
        logoutClick();

        // call if food is clicked
//        foodClick();

        View headerView = navigationView.getHeaderView(0);
        drawerUsername = headerView.findViewById(R.id.drawer_username);
        user = getIntent().getStringExtra("displayName");
        drawerUsername.setText("Welcome " + user);
        profilepic = headerView.findViewById(R.id.navProfilePic);


        userData = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = userData.getReference(firebaseAuth.getUid());
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                UserProfile userProfile = snapshot.getValue(UserProfile.class);
                profilepic.setImageURI(Uri.parse(userProfile.getProfilePic()));
                HomeFragment.calorieDisplay.setText(String.valueOf(Math.abs(Integer.parseInt(userProfile.getCaloriesLeft()))));

                if(Integer.parseInt(userProfile.getCaloriesLeft()) < 0) {
                    TextPaint paint = HomeFragment.calorieDisplay.getPaint();
                    float width = paint.measureText(HomeFragment.calorieDisplay.getText().toString());
                    Shader textShader = new LinearGradient(0, 0, width, HomeFragment.calorieDisplay.getTextSize(),
                            new int[]{
                                    Color.parseColor("#FF0000"),
                                    Color.parseColor("#FFD79C"),
                            }, null, Shader.TileMode.CLAMP);
                    HomeFragment.calorieDisplay.getPaint().setShader(textShader);
                    HomeFragment.calorieDisplay.setTextColor(Color.parseColor("#FF0000"));
                    HomeFragment.caloriesLeftMessage.setText("calories over today");
                }
                else{
                    TextPaint paint = HomeFragment.calorieDisplay.getPaint();
                    float width = paint.measureText(HomeFragment.calorieDisplay.getText().toString());
                    Shader textShader = new LinearGradient(0, 0, width, HomeFragment.calorieDisplay.getTextSize(),
                            new int[]{
                                    Color.parseColor("#3DBDB0"),
                                    Color.parseColor("#04B5CE"),
                            }, null, Shader.TileMode.CLAMP);
                    HomeFragment.calorieDisplay.getPaint().setShader(textShader);
                    HomeFragment.calorieDisplay.setTextColor(Color.parseColor("#3DBDB0"));
                    HomeFragment.caloriesLeftMessage.setText("calories left today");

                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
            }
        });





//        String setImagePic = getIntent().getStringExtra("image");
//        if(setImagePic != null){
//            switch(setImagePic){
//                case "super": profilepic.setImageResource(R.drawable.pic1); break;
//                case "bat": profilepic.setImageResource(R.drawable.pic2); break;
//                case "wonder": profilepic.setImageResource(R.drawable.pic3); break;
//            }
//        }



    }

    public void onClick(View v) {
        Intent profile = new Intent(getApplicationContext(), Profile.class);
        profile.putExtra("displayName", user);
        finish();
        startActivity(profile);
    }

//    public void foodClick() {
//        navigationView.getMenu().findItem(R.id.nav_food).setOnMenuItemClickListener(menuItem -> {
//           Intent food = new Intent(getApplicationContext(), Food.class);
//           food.putExtra("displayName", user);
//           finish();
//           startActivity(food);
//           return true;
//        });
//    }

    public void logoutClick() {
        navigationView.getMenu().findItem(R.id.nav_logout).setOnMenuItemClickListener(menuItem -> {
            firebaseAuth.signOut();
            Intent logout = new Intent(getApplicationContext(), Login.class);
            finish();
            startActivity(logout);
            return true;
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navigation, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Context context = getApplicationContext();
        switch(item.getItemId()) {
            case R.id.action_settings:
                String selection = item.getTitle().toString();
                if(selection.equals("Developers")) {
                    Intent devInfo = new Intent(context, DevInfo.class);
                    startActivity(devInfo);
                    break;
                }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = findNavController(this, R.id.nav_host_fragment_content_navigation);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }


}