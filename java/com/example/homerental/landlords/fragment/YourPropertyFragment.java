package com.example.homerental.landlords.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.example.homerental.R;
import com.example.homerental.User;
import com.example.homerental.landlords.ListHouseAdapterLandlords;
import com.example.homerental.landlords.ListHouseLandlords;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class YourPropertyFragment extends Fragment{

    private TextView BuildingTypeTV, NumOfRoomTV, NumOfToiletsTV, AddressTV, CityTV, StateTV, PostcodeTV, davTV, StatusTV, PriceTV, NotesTV;
    RecyclerView recyclerView;
    ListHouseAdapterLandlords adapter;
    String price, address, buildingType, nor, not, city, state, postcode, dav, status, note;
    //String BuildingType, Address, Posscode, DAV, Price, Note;
    ArrayList<ListHouseLandlords> listHousesLandlords = new ArrayList<>();
    String url = "http://172.20.10.4/HomeRental/retrievedata2.php";
    ListHouseLandlords listHouseLandlords;
    Button btnEdit;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_your_property, container, false);

        //setUpListHouses();
       // recyclerView = v.findViewById(R.id.recyclerviewLandlords);
        //adapter = new ListHouseAdapterLandlords(getContext(), listHousesLandlords);
       // recyclerView.setAdapter(adapter);
       // recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        BuildingTypeTV = v.findViewById(R.id.buildingTypeTV);
        NumOfRoomTV = v.findViewById(R.id.numOfRoomTextView);
        NumOfToiletsTV = v.findViewById(R.id.numOfToiletsTextView);
        AddressTV = v.findViewById(R.id.AddressTextView);
        CityTV = v.findViewById(R.id.cityTextView);
        StateTV = v.findViewById(R.id.stateTextView);
        PostcodeTV = v.findViewById(R.id.posscodeTextView);
        davTV = v.findViewById(R.id.davTextView);
        StatusTV = v.findViewById(R.id.statusTextView);
        PriceTV = v.findViewById(R.id.priceTextView);
        NotesTV = v.findViewById(R.id.notesTextView);
        btnEdit = v.findViewById(R.id.EditBtnHouse);

        retrieveData();

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Fragment fragment = new EditListHouseFragment();

                AppCompatActivity activity = (AppCompatActivity) v.getContext();
                Fragment myFragment = new EditListHouseFragment();
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, myFragment).addToBackStack(null).commit();
            }
        });

        return v;
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

                            if(success.equals("1")) {

                                for(int i=0; i<jsonArray.length();i++) {
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
                        }
                        catch (JSONException e){
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }){

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


    private void setUpListHouses() {
    }


}