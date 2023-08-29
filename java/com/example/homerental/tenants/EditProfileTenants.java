package com.example.homerental.tenants;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.homerental.Login;
import com.example.homerental.MainActivity;
import com.example.homerental.R;
import com.example.homerental.User;
import com.example.homerental.landlords.fragment.ProfileLandlordFragment;
import com.example.homerental.landlords.fragment.YourPropertyFragment;
import com.google.android.material.textfield.TextInputEditText;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

public class EditProfileTenants extends AppCompatActivity {

    TextInputEditText TProfileFullName, TEmail, TUsername, TPhoneNumber, TAge, TOccupation, TNOK;
    RadioGroup radioGroup1, radioGroup2;
    Button btnSave, buttonBackET, btnLogout;
    String FullName, Email, Username, PhoneNumber, Age, Occupation, TNOfKids, Gender, Status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile_tenants);

        TProfileFullName = findViewById(R.id.TPFullName);
        TEmail = findViewById(R.id.TPEmail);
        TUsername = findViewById(R.id.TPUsername);
        TPhoneNumber = findViewById(R.id.TPPhoneNum);
        TAge = findViewById(R.id.TPAge);
        TOccupation = findViewById(R.id.TPOccupation);
        TNOK = findViewById(R.id.TPNOK);
        btnSave = findViewById(R.id.buttonSave);
        buttonBackET = findViewById(R.id.backBtn);

        buttonBackET.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int intValue = 1;

                Intent intent = new Intent(EditProfileTenants.this, MainActivity.class);
                intent.putExtra("intVariableName", intValue);
                startActivity(intent);
                finish();
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FullName = String.valueOf(TProfileFullName.getText());
                Email = String.valueOf(TEmail.getText());
                Username = String.valueOf(TUsername.getText());
                PhoneNumber = String.valueOf(TPhoneNumber.getText());
                Age = String.valueOf(TAge.getText());
                Occupation = String.valueOf(TOccupation.getText());
                TNOfKids = String.valueOf(TNOK.getText());


                if (!TProfileFullName.equals("") && !TEmail.equals("")) {

                    String userID = User.getInstance().getUserID();
                    Handler handler = new Handler();
                    handler.post(() -> {
                        //Starting Write and Read data with URL
                        //Creating array for parameters
                        String[] field = new String[7];
                        field[0] = "FullName";
                        field[1] = "Email";
                        field[2] = "Username";
                        field[3] = "PhoneNumber";
                        field[4] = "Age";
                        field[5] = "Occupation";
                        field[6] = "TotalOfKids";
                        //Creating array for data
                        String[] data = new String[7];
                        data[0] = FullName;
                        data[1] = Email;
                        data[2] = Username;
                        data[3] = PhoneNumber;
                        data[4] = Age;
                        data[5] = Occupation;
                        data[6] = TNOfKids;

                        PutData putData = new PutData("http://172.20.10.4/HomeRental/updateTenantsProfile.php", "POST", field, data);
                        if (putData.startPut()) {
                            if (putData.onComplete()) {

                                String result = putData.getResult();
                                if (result.equals("Update Profile Success")) {
                                    Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(getApplicationContext(), EditProfileTenants.class);
                                    startActivity(intent);

                                } else {
                                    Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
                                    Log.e("Error", result);
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
}