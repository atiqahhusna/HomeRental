package com.example.homerental.landlords;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.example.homerental.R;
import com.example.homerental.SignUp;
import com.example.homerental.landlords.fragment.AddPropertyFragment;
import com.example.homerental.landlords.fragment.ChatLandlordFragment;
import com.example.homerental.landlords.fragment.DashboardLandlordFragment;
import com.example.homerental.landlords.fragment.EditListHouseFragment;
import com.example.homerental.landlords.fragment.HomeLandlordFragment;
import com.example.homerental.landlords.fragment.ProfileLandlordFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivityLandlord extends AppCompatActivity  {

    BottomNavigationView navigationView;
    private AddPropertyFragment addPropertyFragment;
    private HomeLandlordFragment homeLandlordFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_landlord);

        navigationView = findViewById(R.id.bottomNavigationView);
        getSupportFragmentManager().beginTransaction()
               .replace(R.id.frame_layout, new HomeLandlordFragment())
                .commit();
        navigationView.setSelectedItemId(R.id.home);

        Intent mIntent = getIntent();
        int intValue = mIntent.getIntExtra("intVariableName", 0);

        if(intValue == 1){

            getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout,
                    new ProfileLandlordFragment()).commit();

        }




        navigationView.setOnItemSelectedListener(new BottomNavigationView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                // Using switch case for the bottom navigation bar.
                Fragment fragment = null;
                switch (item.getItemId()) {
                    case R.id.home:
                        fragment = new HomeLandlordFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, fragment).commit();
                        break;
                    case R.id.chat:
                        fragment = new ChatLandlordFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, fragment).commit();
                        break;
                   // case R.id.dashboard:
                     //   fragment = new DashboardLandlordFragment();
                       // getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, fragment).commit();
                       // break;
                    case R.id.profile:
                        fragment = new ProfileLandlordFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, fragment).commit();
                        break;
                }
                return true;
            }
        });
    }
}