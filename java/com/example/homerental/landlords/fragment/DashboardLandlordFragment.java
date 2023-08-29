package com.example.homerental.landlords.fragment;

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
import com.example.homerental.Login;
import com.example.homerental.R;
import com.example.homerental.landlords.ListTenants;
import com.example.homerental.landlords.ListTenantsAdapter;
import com.example.homerental.tenants.ListHouseAdapterTenants;
import com.example.homerental.tenants.ListHouseTenants;
import com.example.homerental.tenants.TenantsChoice;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DashboardLandlordFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DashboardLandlordFragment extends Fragment {
    RecyclerView recyclerView;
    ListTenantsAdapter adapter;
    ArrayList<ListTenants> listTenants = new ArrayList<>();
    String url = "http://172.20.10.4/HomeRental/retrievePayment.php";
    ListTenants listTenant;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public DashboardLandlordFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DashboardLandlordFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DashboardLandlordFragment newInstance(String param1, String param2) {
        DashboardLandlordFragment fragment = new DashboardLandlordFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_dashboard_landlord, container, false);

        recyclerView = v.findViewById(R.id.recyclerview3);
        //setUpListTenants();
        adapter = new ListTenantsAdapter(getContext(), listTenants, new ListTenantsAdapter.ItemClickListener() {
            @Override
            public void onItemClick(ListTenants listTenants) {
                Intent intent = new Intent(getContext(), Login.class);
                startActivity(intent);
            }
        });
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        //retrieveData();

        return v;

    /*
    private void retrieveData() {
        StringRequest request = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        listTenants.clear();
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                            JSONArray jsonArray = jsonObject.getJSONArray("payment");

                            if(success.equals("1")) {

                                for(int i=0; i<jsonArray.length();i++) {
                                    JSONObject object = jsonArray.getJSONObject(i);

                                    String username = object.getString("username");
                                    String status = object.getString("status");

                                    listTenant = new ListTenants(username, status);
                                    listTenants.add(listTenant);
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

    private void setUpListTenants() {
    }
    private void showToast (String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();*/
    }
}