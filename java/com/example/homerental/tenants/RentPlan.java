package com.example.homerental.tenants;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
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
import com.example.homerental.SignUp;
import com.example.homerental.User;
import com.google.android.material.textfield.TextInputEditText;
import com.stripe.android.PaymentConfiguration;
import com.stripe.android.paymentsheet.PaymentSheet;
import com.stripe.android.paymentsheet.PaymentSheetResult;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RentPlan extends AppCompatActivity {
    ImageView leftImageView, rightImageView;
    TextView titleTV;
    Button cancelButton, proceedPayment;
    String PublishableKey = "pk_test_51NI5nDJW7fQCApNbJej785ZpXsDvTG2pOZjDWCJ9gtoiJuPafPDhorGcZthQ0RecHW7pO3ZBLXUTaFHAVQcY03io00vq4vYuRd";
    String SecretKey = "sk_test_51NI5nDJW7fQCApNb5rsPYOhZ2sRnT00PnzjlRwsdTFVkphJ8eOi1XMW8a9KITxwrsKQ9RlDZY6jd4MIynxu57fOI00jEvrVBfI";
    String CustomerId, username;
    String EphericalKey;
    String ClientSecret;
    PaymentSheet paymentSheet;
    TextInputEditText textInputEditTextName, textInputEditTextEmailAddress, textInputEditTextNP, textInputEditTextMID, textInputEditTextTD, textInputEditTextN;
    String url = "http://172.20.10.4/HomeRental/retrieveBooking.php";
    String url2 = "http://172.20.10.4/HomeRental/deleteBooking.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rent_plan);

        titleTV = findViewById(R.id.toolbar_title);
        cancelButton = findViewById(R.id.btnCancelBooking);
        proceedPayment = findViewById(R.id.btnProceedPayment2);
        textInputEditTextName = findViewById(R.id.tName);
        textInputEditTextEmailAddress = findViewById(R.id.tEmail);
        textInputEditTextNP = findViewById(R.id.tPhoneNum);
        textInputEditTextMID = findViewById(R.id.tMID);
        textInputEditTextTD = findViewById(R.id.tenantDuration);
        textInputEditTextN = findViewById(R.id.tNationality);

        retrieveData();
        //deleteData();

        PaymentConfiguration.init(this,PublishableKey);

        paymentSheet = new PaymentSheet(this, paymentSheetResult -> {

            onPaymentResult(paymentSheetResult);
        });


        proceedPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                paymentFlow();

            }
        });

       /* leftImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Booking2.class);
                startActivity(intent);
                //Toast.makeText(RentPlan.this, "You clicked in left", Toast.LENGTH_SHORT).show();
            }
        });

        rightImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(RentPlan.this, "You clicked in right", Toast.LENGTH_SHORT).show();
            }
        });*/

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               /* AlertDialog.Builder builder = new AlertDialog.Builder(RentPlan.this);
                CharSequence[] items = {"Cancel Booking"};
                builder.setTitle(dataClassArrayList.get(i).getName());

*/
                deleteData();
                Intent intent = new Intent(getApplicationContext(), Booking2.class);
                startActivity(intent);
                //Toast.makeText(RentPlan.this, "Booking Cancel", Toast.LENGTH_SHORT).show();
            }
        });

       // titleTV.setText("Rent Plan");

        StringRequest request = new StringRequest(Request.Method.POST, "https://api.stripe.com/v1/customers",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject object = new JSONObject(response);

                            CustomerId = object.getString("id");

                            Toast.makeText(RentPlan.this,CustomerId, Toast.LENGTH_SHORT).show();

                            getEmphericalKey();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(RentPlan.this,error.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {

                Map<String, String> header = new HashMap<>();
                header.put("Authorization","Bearer "+SecretKey);
                return header;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }

    private void deleteData() {
        StringRequest request = new StringRequest(Request.Method.POST, url2,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(response.equals("Data Delete")){
                            Toast.makeText(RentPlan.this, "Data Deleted Successfully", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(RentPlan.this, "Data Not Deleted", Toast.LENGTH_SHORT).show();

                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(RentPlan.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                String name = textInputEditTextName.getText().toString();
                String emailAddress = textInputEditTextEmailAddress.getText().toString();
                String pn = textInputEditTextNP.getText().toString();
                String mid = textInputEditTextMID.getText().toString();
                String td = textInputEditTextTD.getText().toString();
                String n = textInputEditTextN.getText().toString();


                /*Map<String, String> params = new HashMap< >();
                params.put("Name", name);
                params.put("Email", emailAddress);
                params.put("Phonenumber", pn);
                params.put("MoveInDate", mid);
                params.put("Duration", td);
                params.put("Nationality", n);*/
                String userID = User.getInstance().getUserID();

                Map<String, String> params = new HashMap<>();
                params.put("username", userID);

                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);

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
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
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

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(request);
    }

    private void paymentFlow() {

        paymentSheet.presentWithPaymentIntent(ClientSecret, new PaymentSheet.Configuration("Learn with Arvind", new PaymentSheet.CustomerConfiguration(
                CustomerId,
                EphericalKey
        )));
    }

    private void onPaymentResult(PaymentSheetResult paymentSheetResult) {

        if (paymentSheetResult instanceof PaymentSheetResult.Completed){
            Toast.makeText(this, "Payment Success", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getApplicationContext(), Invoice.class);
            startActivity(intent);
        }
    }

    private void getEmphericalKey() {

        StringRequest request = new StringRequest(Request.Method.POST, "https://api.stripe.com/v1/ephemeral_keys",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject object = new JSONObject(response);

                            EphericalKey = object.getString("id");

                            Toast.makeText(RentPlan.this,CustomerId, Toast.LENGTH_SHORT).show();

                            getClientSecret(CustomerId, EphericalKey);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(RentPlan.this,error.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {

                Map<String, String> header = new HashMap<>();
                header.put("Authorization","Bearer "+SecretKey);
                header.put("Stripe-Version", "2022-11-15");
                return header;
            }

            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();
                params.put("customer", CustomerId);

                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }

    private void getClientSecret(String customerId, String ephericalKey) {

        StringRequest request = new StringRequest(Request.Method.POST, "https://api.stripe.com/v1/payment_intents",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject object = new JSONObject(response);

                            ClientSecret = object.getString("client_secret");

                            Toast.makeText(RentPlan.this,ClientSecret, Toast.LENGTH_SHORT).show();



                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(RentPlan.this,error.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {

                Map<String, String> header = new HashMap<>();
                header.put("Authorization","Bearer "+SecretKey);
                return header;
            }

            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();
                params.put("customer", CustomerId);
                params.put("amount", "100"+"00");
                params.put("currency", "MYR");
                params.put("automatic_payment_methods[enabled]", "true");

                sendData();

                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }


    public void sendData() {
        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {

                //String username = User.getInstance().getUsername();
                    String status = "Paid";
                    String username = User.getInstance().getUserID();
                    String amount = "RM100.00";
                    //Starting Write and Read data with URL
                    //Creating array for parameters
                    String[] field = new String[3];
                    field[0] = "amount";
                    field[1] = "status";
                    field[2] = "username";
                    //Creating array for data
                    String[] data = new String[3];
                    data[0] = amount;
                    data[1] = status;
                    data[2] = username;

                    PutData putData = new PutData("http://172.20.10.4/HomeRental/payment.php", "POST", field, data);
                    if (putData.startPut()) {
                        if (putData.onComplete()) {
                            String result = putData.getResult();
                            if(result.equals("Data save")){
                                //Toast.makeText(getApplicationContext(),result, Toast.LENGTH_SHORT).show();

                            }
                            else{
                                Toast.makeText(getApplicationContext(),result, Toast.LENGTH_SHORT).show();
                                Log.e("anyText", result);
                            }
                        }
                    }
                    //End Write and Read data with URL
                }
            });

        }

}
