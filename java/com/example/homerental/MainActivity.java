package com.example.homerental;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.homerental.landlords.fragment.ProfileLandlordFragment;
import com.example.homerental.tenants.ChatFragment;
import com.example.homerental.tenants.HomeFragment;
import com.example.homerental.tenants.ProfileFragment;
import com.example.homerental.tenants.SearchFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import javax.sql.RowSet;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        navigationView = findViewById(R.id.bottomNavigationView);
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, new HomeFragment()).commit();
        navigationView.setSelectedItemId(R.id.home);

        Intent mIntent = getIntent();
        int intValue = mIntent.getIntExtra("intVariableName", 0);

        if (intValue == 1) {

            getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout,
                    new ProfileFragment()).commit();

        }
            navigationView.setOnItemSelectedListener(new BottomNavigationView.OnItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                    // Using switch case for the bottom navigation bar.
                    Fragment fragment = null;
                    switch (item.getItemId()) {
                        case R.id.home:
                            fragment = new HomeFragment();
                            getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, fragment).commit();
                            break;
                        case R.id.search:
                            fragment = new SearchFragment();
                            getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, fragment).commit();
                            break;
                        case R.id.chat:
                            fragment = new ChatFragment();
                            getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, fragment).commit();
                            break;
                        case R.id.profile:
                            fragment = new ProfileFragment();
                            getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, fragment).commit();
                            break;
                    }
                    return true;
                }
            });
        }
    }