package com.example.homerental;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.homerental.landlords.MainActivityLandlord;
import com.example.homerental.tenants.HomeFragment;
import com.google.android.material.textfield.TextInputEditText;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Login extends AppCompatActivity {

    TextInputEditText textInputEditTextUsername, textInputEditTextPassword;
    Button buttonLogin;
    TextView textViewSignUp, textViewForgotPassword;
    ProgressBar progressBar;
    String username, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        textInputEditTextUsername = findViewById(R.id.username);
        textInputEditTextPassword = findViewById(R.id.password);
        buttonLogin = findViewById(R.id.buttonLogin);
        textViewSignUp = findViewById(R.id.signUpText);
        textViewForgotPassword = findViewById(R.id.forgotPass);
        progressBar = findViewById(R.id.progress);

        textViewForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ForgotPassword.class);
                startActivity(intent);
            }
        });
        textViewSignUp.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), SignUp.class);
            startActivity(intent);
            finish();
        });

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fnAddToREST();
            }
        });
    }
    private void fnAddToREST() {

        String strURL = "http://172.20.10.4/HomeRental/login.php";
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, strURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    Log.e("anyText",response);
                    //Toast.makeText(getApplicationContext(), "Getting some respond here", Toast.LENGTH_SHORT).show();
                    JSONArray jsonArray = new JSONArray(response);

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject obj = jsonArray.getJSONObject(i);

                        String userName = textInputEditTextUsername.getText().toString();
                        String passWord = textInputEditTextPassword.getText().toString();

                        String username = obj.getString("Username");
                        String password = obj.getString("Password");
                        String role = obj.getString("Role");
                        String userID = obj.getString("userID");
                        String Fullname = obj.getString("FullName");
                        String Email = obj.getString("Email");

                        User.getInstance().setUserID(username);

                        if(userName.equals(username) && passWord.equals(password)){
                            if(role.equals("Landlord")) {
                                Toast.makeText(getApplicationContext(),"Successfully Login!", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(), MainActivityLandlord.class);
                                startActivity(intent);
                            } else if(role.equals("Tenant")) {
                                Toast.makeText(getApplicationContext(),"Successfully Login!", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                startActivity(intent);
                            }
                        }else if(userName.equals("") && passWord.equals(""))
                            Toast.makeText(getApplicationContext(),"Wrong Username or Password", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getApplicationContext(), "error", Toast.LENGTH_SHORT).show();

            }

        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                String userName = textInputEditTextUsername.getText().toString();
                String passWord = textInputEditTextPassword.getText().toString();


                Map<String, String> params = new HashMap< >();
                params.put("selectFn", "fnLogin");
                params.put("Username", userName);
                params.put("Password", passWord);

                return params;

            }
        };
        requestQueue.add(stringRequest);
    }


}