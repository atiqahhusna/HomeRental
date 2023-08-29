package com.example.homerental.landlords;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.homerental.Login;
import com.example.homerental.R;
import com.example.homerental.User;
import com.example.homerental.landlords.fragment.ProfileLandlordFragment;
import com.example.homerental.landlords.fragment.YourPropertyFragment;
import com.example.homerental.tenants.EditProfileTenants;
import com.example.homerental.tenants.HomeFragment;
import com.example.homerental.tenants.TenantsChoice;
import com.google.android.material.textfield.TextInputEditText;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

public class EditProfileLandlordActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    ImageView imageView;
    Spinner gender;
    TextInputEditText fullnameEP, emailEP, usernameEP, phonenumEP, genderEP;
    Toolbar toolbar;
    Spinner spinner;
    Button saveButton, backButton;
    String fullname, email, username, phonenum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile_landlord);

        saveButton = findViewById(R.id.buttonSave);
        backButton = findViewById(R.id.backBtn);
        gender = findViewById(R.id.spinnerGender);
        fullnameEP = findViewById(R.id.PFullName);
        emailEP = findViewById(R.id.PEmail);
        usernameEP = findViewById(R.id.PUsername);
        phonenumEP = findViewById(R.id.PPhoneNum);

        toolbar = findViewById(R.id.toolbarEP);

        spinner = findViewById(R.id.spinnerGender);
        ArrayAdapter<CharSequence> adapter;
        adapter = ArrayAdapter.createFromResource(getApplicationContext(), R.array.gender, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        gender.setOnItemSelectedListener(this);
        gender.setAdapter(adapter);

      /*  final androidx.fragment.app.FragmentManager mFragmentManager = getSupportFragmentManager();
        final androidx.fragment.app.FragmentTransaction mFragmentTransaction = mFragmentManager.beginTransaction();
        final ProfileLandlordFragment mFragment = new ProfileLandlordFragment();*/
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int intValue = 1;

                Intent intent = new Intent(EditProfileLandlordActivity.this, MainActivityLandlord.class);
                intent.putExtra("intVariableName", intValue);
                startActivity(intent);
                finish();
               // AppCompatActivity activity = (AppCompatActivity).getContext();
                //FragmentManager fragmentManager = getSupportFragmentManager();
                //Fragment myFragment = new ProfileLandlordFragment();
                //fragmentManager.getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, myFragment).addToBackStack(null).commit();

               // Fragment fragment = new ProfileLandlordFragment();
               // FragmentManager fragmentManager = getSupportFragmentManager();
               // fragmentManager.beginTransaction().replace(R.id.fragment_container_view, fragment).commit();
                //Intent intent = new Intent(EditProfileLandlordActivity.this, ProfileLandlordFragment.class);
                //startActivity(intent);
                //Toast.makeText(Booking2.this, "You clicked in left", Toast.LENGTH_SHORT).show();
            }
        });

         saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                fullname = String.valueOf(fullnameEP.getText());
                email = String.valueOf(emailEP.getText());
                username = String.valueOf(usernameEP.getText());
                phonenum = String.valueOf(phonenumEP.getText());
                String genderMF = spinner.getSelectedItem().toString();

                if (!fullnameEP.equals("") && !phonenumEP.equals("")) {

                    String userID = User.getInstance().getUserID();
                    Handler handler = new Handler();
                    handler.post(() -> {
                        //Starting Write and Read data with URL
                        //Creating array for parameters
                        String[] field = new String[5];
                        field[0] = "FullName";
                        field[1] = "Email";
                        field[2] = "Username";
                        field[3] = "PhoneNum";
                        field[4] = "Gender";

                        //Creating array for data
                        String[] data = new String[5];
                        data[0] = fullname;
                        data[1] = email;
                        data[2] = username;
                        data[3] = phonenum;
                        data[4] = genderMF;


                        PutData putData = new PutData("http://172.20.10.4/HomeRental/updateLandlordProfile.php", "POST", field, data);
                        if (putData.startPut()) {
                            if (putData.onComplete()) {

                                String result = putData.getResult();
                                if (result.equals("Update Profile Success")) {
                                    Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(getApplicationContext(), EditProfileLandlordActivity.class);
                                    startActivity(intent);


                                   //ProfileLandlordFragment fragment = new ProfileLandlordFragment();
                                    //getSupportFragmentManager().beginTransaction()
                                    //      .replace(R.id.fragment_container_view, fragment, fragment.getClass().getSimpleName()).addToBackStack(null).commit();

                                } else {
                                    Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                        //End Write and Read data with URL
                    });
                } else {
                    Toast.makeText(getApplicationContext(), "All field are required", Toast.LENGTH_SHORT).show();
                }
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
