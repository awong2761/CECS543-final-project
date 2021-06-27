package com.example.project1;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.TextView;
import com.google.android.material.navigation.NavigationView;


import androidx.navigation.NavController;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.example.project1.databinding.ActivityNavigationBinding;


import static androidx.navigation.Navigation.findNavController;

public class Navigation extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityNavigationBinding binding;
    private DrawerLayout drawer;
    private TextView drawerUsername;
    private TextView drawerProfile;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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

        //call if profile txt is clicked
//        profileClick();

        View headerView = navigationView.getHeaderView(0);
        drawerUsername = headerView.findViewById(R.id.drawer_username);
        String user = getIntent().getStringExtra("displayName");
        drawerUsername.setText("Welcome " + user);

    }

    public void onClick(View v) {
        Intent profile = new Intent(getApplicationContext(), Profile.class);
        finish();
        startActivity(profile);
    }

    public void logoutClick() {
        navigationView.getMenu().findItem(R.id.nav_logout).setOnMenuItemClickListener(menuItem -> {
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