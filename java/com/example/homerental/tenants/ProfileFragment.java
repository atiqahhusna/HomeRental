package com.example.homerental.tenants;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.homerental.Login;
import com.example.homerental.R;
import com.example.homerental.User;
import com.example.homerental.landlords.EditProfileLandlordActivity;
import com.example.homerental.landlords.fragment.YourPropertyFragment;
import com.google.android.material.textfield.TextInputEditText;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class ProfileFragment extends Fragment {
    TextInputEditText TProfileFullName, TEmail, TUsername, TPhoneNumber, TAge, TNOK, TOccupation;
    RadioGroup radioGroup1, radioGroup2;
    Button btnEditProfile, btnLogout, BackButton;
    String url = "http://172.20.10.4/HomeRental/retrieveProfileTenants.php";
    String fullname, emailProfile, username, phonenumber, age, occupation, tok, radiobtn1, radiobtn2;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_profile, container, false);

        TProfileFullName = v.findViewById(R.id.TPFullName);
        TEmail = v.findViewById(R.id.TPEmail);
        TUsername = v.findViewById(R.id.TPUsername);
        TPhoneNumber = v.findViewById(R.id.TPPhoneNum);
        TAge = v.findViewById(R.id.TPAge);
        TOccupation = v.findViewById(R.id.TPOccupation);
        TNOK = v.findViewById(R.id.TPNOK);
        btnEditProfile = v.findViewById(R.id.editBtnTenants);
        btnLogout = v.findViewById(R.id.logoutBtn);

        retrieveData();

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity().getApplicationContext(), Login.class);
                startActivity(intent);
            }
        });

        btnEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity().getApplicationContext(), EditProfileTenants.class);
                startActivity(intent);
            }

        });

        return v;
    }

    private void retrieveData() {
        StringRequest request = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {

                            Log.e("Error", response);
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                            JSONArray jsonArray = jsonObject.getJSONArray("profiletenants");

                            if (success.equals("1")) {

                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject object = jsonArray.getJSONObject(i);

                                    String FullName = object.getString("FullName");
                                    String Email = object.getString("Email");
                                    String Username = object.getString("Username");
                                    String PhoneNumber = object.getString("PhoneNumber");
                                    String Age = object.getString("Age");
                                    String Occupation = object.getString("Occupation");
                                    String TotalKids = object.getString("TotalOfKids");
                                    /*String State = object.getString("State");
                                    String Posscode = object.getString("Posscode");
                                    String DAV = object.getString("DAV");
                                    String Status = object.getString("Status");
                                    String Note = object.getString("Note");*/

                                    TProfileFullName.setText(FullName);
                                    TEmail.setText(Email);
                                    TUsername.setText(Username);
                                    TPhoneNumber.setText(PhoneNumber);
                                    TAge.setText(Age);
                                    TOccupation.setText(Occupation);
                                    TNOK.setText(TotalKids);



                                    User.getInstance().setFullName(FullName);
                                    User.getInstance().setEmail(Email);
                                    User.getInstance().setUsername(Username);
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();
            }

        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {


                String userID = User.getInstance().getUserID();

                Map<String, String> params = new HashMap<>();
                params.put("username", userID);

                return params;

            }

        };
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(request);
    }
}
