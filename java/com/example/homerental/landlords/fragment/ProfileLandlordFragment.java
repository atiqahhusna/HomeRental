package com.example.homerental.landlords.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
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
import com.google.android.gms.maps.MapFragment;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ProfileLandlordFragment extends Fragment {

    TextInputEditText FullName, Email, Username, PhoneNumber, Gender;
    Button EditButton, LogoutButton;
    String url = "http://172.20.10.4/HomeRental/retrieveLandlordProfile.php";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_profile_landlord, container, false);

        FullName = v.findViewById(R.id.PFullName);
        Email = v.findViewById(R.id.PEmail);
        Username = v.findViewById(R.id.PUsername);
        PhoneNumber = v.findViewById(R.id.PPhoneNum);
        Gender = v.findViewById(R.id.PGender);
        EditButton = v.findViewById(R.id.editBtn);
        LogoutButton = v.findViewById(R.id.logoutBtn);

        LogoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity().getApplicationContext(), Login.class);
                startActivity(intent);
            }
        });

        EditButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               Intent intent = new Intent(getActivity().getApplicationContext(), EditProfileLandlordActivity.class);
                startActivity(intent);
            }
        });

        Log.e("huwa", "huwa");

        retrieveData();

        return v;
    }

    private void retrieveData() {

        Log.e("huwa", "huwa");
        StringRequest request = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("huwa", "huwa");
                        try {
                            Log.e("Error", response);
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                            JSONArray jsonArray = jsonObject.getJSONArray("profilelandlord");

                            if (success.equals("1")) {

                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject object = jsonArray.getJSONObject(i);

                                    String fullname = object.getString("FullName");
                                    String Emails = object.getString("Email");
                                    String usernameP = object.getString("Username");
                                    String PhoneNum = object.getString("PhoneNum");
                                    String GenderP = object.getString("Gender");

                                    FullName.setText(fullname);
                                    Email.setText(Emails);
                                    Username.setText(usernameP);
                                    PhoneNumber.setText(PhoneNum);
                                    Gender.setText(GenderP);

                                    User.getInstance().setFullName(fullname);
                                    User.getInstance().setEmail(Emails);
                                    User.getInstance().setUsername(usernameP);
                                    User.getInstance().setPhoneNumber(PhoneNum);
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