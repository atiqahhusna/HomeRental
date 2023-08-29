package com.example.homerental.tenants;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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
import com.example.homerental.MainActivity;
import com.example.homerental.R;
import com.example.homerental.User;
import com.example.homerental.landlords.EditProfileLandlordActivity;
import com.example.homerental.landlords.MainActivityLandlord;
import com.example.homerental.landlords.fragment.YourPropertyFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class TenantsChoice extends AppCompatActivity {

    private TextView BuildingTypeTV, NumOfRoomTV, NumOfToiletsTV, AddressTV, CityTV, StateTV, PostcodeTV, davTV, StatusTV, PriceTV, NotesTV;
    Button btnBooking, contact, BackBtn;
    String url = "http://172.20.10.4/HomeRental/retrievedata2.php";
    String price, address;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tenants_choice);

        BuildingTypeTV = findViewById(R.id.buildingTypeTV);
        NumOfRoomTV = findViewById(R.id.numOfRoomTextView);
        NumOfToiletsTV = findViewById(R.id.numOfToiletsTextView);
        AddressTV = findViewById(R.id.AddressTextView);
        CityTV = findViewById(R.id.cityTextView);
        StateTV = findViewById(R.id.stateTextView);
        PostcodeTV = findViewById(R.id.posscodeTextView);
        davTV = findViewById(R.id.davTextView);
        StatusTV = findViewById(R.id.statusTextView);
        PriceTV = findViewById(R.id.priceTextView);
        NotesTV = findViewById(R.id.notesTextView);
        btnBooking = findViewById(R.id.BookingBtnHouse);
        contact = findViewById(R.id.chatBtn);

        retrieveData();

        contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                boolean installed = appInstallOrNot("com.whatsapp");

                if (installed){
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse("http://api.whatsapp.com/send?phone="+"+60"+"&text="));
                    startActivity(intent);
                }
            }
        });

        btnBooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Booking2.class);
                startActivity(intent);
            }
        });
    }

    private boolean appInstallOrNot(String s) {
        PackageManager packageManager = getPackageManager();
        boolean app_installed = true;
        try{
            packageManager.getPackageInfo(url,PackageManager.GET_ACTIVITIES);
            app_installed = true;
        }catch (PackageManager.NameNotFoundException e) {
            app_installed = false;
        }
        return app_installed;

    }

    private void retrieveData() {
        StringRequest request = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        // listHousesLandlords.clear();
                        try {

                            Log.e("Error", response);
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                            JSONArray jsonArray = jsonObject.getJSONArray("propertyhouse");

                            if (success.equals("1")) {

                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject object = jsonArray.getJSONObject(i);

                                    price = String.valueOf(PriceTV.getText());
                                    address = String.valueOf(AddressTV.getText());

                                    String Price = object.getString("Price");
                                    String Address = object.getString("Address");
                                    String BuildingType = object.getString("BuildingType");
                                    String NumOfRoom = object.getString("NumOfRoom");
                                    String NumOfToilets = object.getString("NumOfToilets");
                                    String City = object.getString("City");
                                    String State = object.getString("State");
                                    String Posscode = object.getString("Posscode");
                                    String DAV = object.getString("DAV");
                                    String Status = object.getString("Status");
                                    String Note = object.getString("Note");

                                    BuildingTypeTV.setText(BuildingType);
                                    NumOfRoomTV.setText(NumOfRoom);
                                    NumOfToiletsTV.setText(NumOfToilets);
                                    AddressTV.setText(Address);
                                    CityTV.setText(City);
                                    StateTV.setText(State);
                                    PostcodeTV.setText(Posscode);
                                    davTV.setText(DAV);
                                    StatusTV.setText(Status);
                                    PriceTV.setText(Price);
                                    NotesTV.setText(Note);


                                    User.getInstance().setPrice(Price);
                                    User.getInstance().setAddress(Address);
                                    User.getInstance().setBuildingType(BuildingType);
                                    User.getInstance().setNumOfRoom(NumOfRoom);
                                    User.getInstance().setNumOfToilets(NumOfToilets);
                                    User.getInstance().setCity(City);
                                    User.getInstance().setState(State);
                                    User.getInstance().setPosscode(Posscode);
                                    User.getInstance().setDAV(DAV);
                                    User.getInstance().setStatus(Status);
                                    User.getInstance().setNote(Note);
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
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
    }


}
