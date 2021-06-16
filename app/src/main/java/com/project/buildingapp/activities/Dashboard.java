package com.project.buildingapp.activities;

import android.os.Bundle;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.project.buildingapp.R;
import com.project.buildingapp.utils.BottomNavLocker;
import com.project.buildingapp.utils.ToolBarLocker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

public class Dashboard extends AppCompatActivity implements BottomNavLocker, ToolBarLocker {

    BottomNavigationView navView;
    AppBarConfiguration appBarConfiguration;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_dashboard);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        navView = findViewById(R.id.nav_view);

        appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_caretaker, R.id.navigation_settings)
                .build();

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, appBarConfiguration) || super.onSupportNavigateUp();
    }


    @Override
    public void BottomNavLocked(boolean enabled) {
        if (enabled) {
            navView.setVisibility(View.GONE);
        } else {
            navView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void ToolBarLocked(boolean enabled) {
        if (enabled) {
            toolbar.setVisibility(View.GONE);
        } else {
            toolbar.setVisibility(View.VISIBLE);
        }
    }
}
