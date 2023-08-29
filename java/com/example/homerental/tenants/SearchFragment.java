package com.example.homerental.tenants;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.homerental.User;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.example.homerental.R;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.textfield.TextInputEditText;
import com.stripe.android.PaymentConfiguration;
import com.stripe.android.paymentsheet.PaymentSheet;
import com.stripe.android.paymentsheet.PaymentSheetResult;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SearchFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SearchFragment extends Fragment {
    ImageView leftImageView, rightImageView;
    TextView titleTV, statusTv;
    Button cancelButton, proceedPayment;
    String PublishableKey = "pk_test_51NI5nDJW7fQCApNbJej785ZpXsDvTG2pOZjDWCJ9gtoiJuPafPDhorGcZthQ0RecHW7pO3ZBLXUTaFHAVQcY03io00vq4vYuRd";
    String SecretKey = "sk_test_51NI5nDJW7fQCApNb5rsPYOhZ2sRnT00PnzjlRwsdTFVkphJ8eOi1XMW8a9KITxwrsKQ9RlDZY6jd4MIynxu57fOI00jEvrVBfI";
    String CustomerId;
    String EphericalKey;
    String ClientSecret;
    PaymentSheet paymentSheet;
    TextInputEditText textInputEditTextName, textInputEditTextEmailAddress, textInputEditTextNP, textInputEditTextMID, textInputEditTextTD, textInputEditTextN, textInputEditTextO;
    Button buttonProceedPayment;
    String name, emailT, phoneNum, MID, duration, nationality;
    String url = "http://172.20.10.4/HomeRental/retrieveBooking.php";

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SearchFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SearchFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SearchFragment newInstance(String param1, String param2) {
        SearchFragment fragment = new SearchFragment();
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
        // Initialize view
        View v = inflater.inflate(R.layout.fragment_search, container, false);

       // leftImageView = v.findViewById(R.id.left_icon);
       // rightImageView = v.findViewById(R.id.right_icon);
        titleTV = v.findViewById(R.id.toolbar_title);
        //cancelButton = v.findViewById(R.id.btnCancelBooking);
        textInputEditTextName = v.findViewById(R.id.tName2);
        textInputEditTextEmailAddress = v.findViewById(R.id.tEmail);
        textInputEditTextNP = v.findViewById(R.id.tPhoneNum);
        textInputEditTextMID = v.findViewById(R.id.tMID);
        textInputEditTextTD = v.findViewById(R.id.tenantDuration);
        textInputEditTextN = v.findViewById(R.id.tNationality);
        titleTV = v.findViewById(R.id.toolbar_title);
      //  proceedPayment = v.findViewById(R.id.btnProceedPayment2);

        // PaymentConfiguration.init(this,PublishableKey);
        retrieveData();

        paymentSheet = new PaymentSheet(this, paymentSheetResult -> {

            onPaymentResult(paymentSheetResult);
        });


/*        proceedPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                paymentFlow();
            }
        });
*/
     /*   leftImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), Booking2.class);
                startActivity(intent);
                //Toast.makeText(RentPlan.this, "You clicked in left", Toast.LENGTH_SHORT).show();
            }
        });

        rightImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(RentPlan.this, "You clicked in right", Toast.LENGTH_SHORT).show();
            }
        });

/*        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), Booking2.class);
                startActivity(intent);
                Toast.makeText(getContext(), "Booking Cancel", Toast.LENGTH_SHORT).show();
            }
        });
*/
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
                            JSONArray jsonArray = jsonObject.getJSONArray("booking");

                            if(success.equals("1")) {

                                for(int i=0; i<jsonArray.length();i++) {
                                    JSONObject object = jsonArray.getJSONObject(i);

                                    String name= object.getString("Name");
                                    String email = object.getString("Email");
                                    String phoneNum = object.getString("PhoneNumber");
                                    String mid = object.getString("MoveInDate");
                                    String duration = object.getString("Duration");
                                    String nationality = object.getString("Nationality");

                                    textInputEditTextName.append(name);
                                    textInputEditTextEmailAddress.append(email);
                                    textInputEditTextNP.append(phoneNum);
                                    textInputEditTextMID.append(mid);
                                    textInputEditTextTD.append(duration);
                                    textInputEditTextN.append(nationality);

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

    public void paymentFlow() {

        paymentSheet.presentWithPaymentIntent(ClientSecret, new PaymentSheet.Configuration("Learn with Arvind", new PaymentSheet.CustomerConfiguration(
                CustomerId,
                EphericalKey
        )));
    }

    private void onPaymentResult(PaymentSheetResult paymentSheetResult) {

        if (paymentSheetResult instanceof PaymentSheetResult.Completed){
            Toast.makeText(getContext(), "Payment Success", Toast.LENGTH_SHORT).show();
        }
    }
}
