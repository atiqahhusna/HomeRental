package com.example.homerental;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.homerental.landlords.MainActivityLandlord;
import com.example.homerental.landlords.fragment.YourPropertyFragment;
import com.google.android.material.textfield.TextInputEditText;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

import java.util.HashMap;
import java.util.Map;

public class ForgotPassword extends AppCompatActivity {
    Button buttonForgetPassword;
    TextInputEditText textInputEditTextEmailFP, textInputEditTextNewPasswordFP, textInputEditTextConfirmPasswordFP;
    String EmailFP, NewPassFP, ConfirmPassFP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        textInputEditTextEmailFP = findViewById(R.id.emailFP);
        textInputEditTextNewPasswordFP = findViewById(R.id.NewPasswordFP);
        textInputEditTextConfirmPasswordFP = findViewById(R.id.confirmPasswordFP);
        buttonForgetPassword = findViewById(R.id.buttonForgotPassword);

        buttonForgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EmailFP = String.valueOf(textInputEditTextEmailFP.getText());
                NewPassFP = String.valueOf(textInputEditTextNewPasswordFP.getText());
                ConfirmPassFP = String.valueOf(textInputEditTextConfirmPasswordFP.getText());

                // validating the text fields if empty or not.
                if (!textInputEditTextEmailFP.equals("") && !textInputEditTextNewPasswordFP.equals("")) {

                    String userID = User.getInstance().getUserID();
                    Handler handler = new Handler();
                    handler.post(() -> {
                        //Starting Write and Read data with URL
                        //Creating array for parameters
                        String[] field = new String[2];
                        field[0] = "Email";
                        field[1] = "Password";
                        field[11] = "userID";

                        //Creating array for data
                        String[] data = new String[2];
                        data[0] = EmailFP;
                        data[1] = NewPassFP;
                        data[2] = userID;

                        PutData putData = new PutData("http://172.20.10.4/HomeRental/forgotpassword.php", "POST", field, data);
                        if (putData.startPut()) {
                            if (putData.onComplete()) {

                              //  Log.e("try", Address);
                              //  Log.e("try", Posscode);
                              //  Log.e("try", Price);
                                String result = putData.getResult();
                                if (result.equals("Update Password Success")) {
                                    Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();

                                    Intent intent = new Intent(getApplicationContext(), Login.class);
                                    startActivity(intent);
                                   /* AppCompatActivity activity = (AppCompatActivity) v.getContext();
                                    Fragment myFragment = new YourPropertyFragment();
                                    activity.getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, myFragment).addToBackStack(null).commit();

                                    /*Intent intent = new Intent(getContext(), YourPropertyFragment.class);
                                    startActivity(intent);*/



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