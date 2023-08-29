package com.example.homerental.tenants;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.homerental.R;
import com.example.homerental.landlords.fragment.YourPropertyFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.

 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    RecyclerView recyclerView;
    ListHouseAdapterTenants adapter;
    ArrayList<ListHouseTenants> listHouses = new ArrayList<>();
    String url = "http://172.20.10.4/HomeRental/retrivedata.php";
    ListHouseTenants listHouse;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_home, container, false);

        recyclerView = v.findViewById(R.id.recyclerview1);
        setUpListHouses();
        adapter = new ListHouseAdapterTenants(getContext(), listHouses, new ListHouseAdapterTenants.ItemClickListener() {
            @Override
            public void onItemClick(ListHouseTenants listHouseTenants) {
                Intent intent = new Intent(getContext(), TenantsChoice.class);
                startActivity(intent);
            }
        });
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        retrieveData();
        return v;
    }
    private void retrieveData() {
        StringRequest request = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        listHouses.clear();
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                            JSONArray jsonArray = jsonObject.getJSONArray("propertyhouse");

                            if(success.equals("1")) {

                                for(int i=0; i<jsonArray.length();i++) {
                                    JSONObject object = jsonArray.getJSONObject(i);

                                    String Address = object.getString("Address");
                                    String Price = object.getString("Price");

                                    listHouse = new ListHouseTenants(Address, Price);
                                    listHouses.add(listHouse);
                                    adapter.notifyDataSetChanged();
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
        });

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(request);


    }
    private void setUpListHouses() {
    }

   // @Override
   /* public void onItemSelectedListener(ListHouseTenants listHouseTenants) {
        Toast.makeText(getContext(),
                listHouseTenants.getAddress(),
                Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getContext(), YourPropertyFragment.class);
        intent.putExtra("Price", listHouseTenants.getPrice());
        intent.putExtra("Address", listHouseTenants.getAddress());
        startActivity(intent);
    }*/
    private void showToast (String message){
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }
}