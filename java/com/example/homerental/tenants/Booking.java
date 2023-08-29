package com.example.homerental.tenants;


import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;


import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.Toast;

import com.example.homerental.Login;
import com.example.homerental.R;
import com.google.android.material.textfield.TextInputEditText;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

public class Booking extends AppCompatActivity {

    TextInputEditText textInputEditTextName, textInputEditTextEmailAddress, textInputEditTextNP, textInputEditTextMID, textInputEditTextTD, inputEditTextN, inputEditTextO;
    Button buttonNext;
    CalendarView calendar;

   // String[] item = {"Student", "Workers", "Others" };
    String occupation;

   // AutoCompleteTextView autoCompleteTextView;

    //ArrayAdapter<String> adapterItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);

        textInputEditTextName = findViewById(R.id.tName);
        textInputEditTextEmailAddress = findViewById(R.id.tEmail);
        textInputEditTextNP = findViewById(R.id.tPhoneNum);
        textInputEditTextMID = findViewById(R.id.tMID);
        textInputEditTextTD = findViewById(R.id.tenantDuration);
        inputEditTextN = findViewById(R.id.tNationality);
       // inputEditTextO = findViewById(R.id.tOccupations);
        calendar = findViewById(R.id.calendar);
      //  autoCompleteTextView = findViewById(R.id.auto_complete_txt);
       // adapterItems = new ArrayAdapter<>(this, R.layout.item_list, item);
        buttonNext = findViewById(R.id.next);


/*
            autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    occupation = parent.getItemAtPosition(position).toString();
                    Log.e(occupation, "occupation:");
                }
               });
            //String item = adapterView.getItemAtPosition(i).toString();
            //occupation = adapterView.getItemAtPosition(i).toString();
            //Toast.makeText(Booking.this, "Item: " + item, Toast.LENGTH_SHORT).show();
*/



        calendar.setOnDateChangeListener((view, year, month, dayOfMonth) -> {
            String Date = dayOfMonth + "-" + (month + 1) + "-" + year;

            // set this date in TextView for Display
            textInputEditTextMID.setText(Date);
        });

        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name, email, phonenumber, moveindate, duration, nationality, occu;
                name = String.valueOf(textInputEditTextName.getText());
                email = String.valueOf(textInputEditTextEmailAddress.getText());
                phonenumber = String.valueOf(textInputEditTextNP.getText());
                moveindate = String.valueOf(textInputEditTextMID.getText());
                duration = String.valueOf(textInputEditTextTD.getText());
                nationality = String.valueOf(inputEditTextN.getText());
              //
                //  occu = occupation;
                //occupation = String.valueOf(autoCompleteTextView.getText());

                if (!name.equals("") && !email.equals("") && !phonenumber.equals("") && !moveindate.equals("")) {

                    Handler handler = new Handler();
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            //Starting Write and Read data with URL
                            //Creating array for parameters
                            String[] field = new String[6];
                            field[0] = "name";
                            field[1] = "email";
                            field[2] = "phonenumber";
                            field[3] = "moveindate";
                            field[4] = "duration";
                            field[5] = "nationality";
                           // field[6] = "occupation";
                            //Creating array for data
                            String[] data = new String[6];
                            data[0] = name;
                            data[1] = email;
                            data[2] = phonenumber;
                            data[3] = moveindate;
                            data[4] = duration;
                            data[5] = nationality;
                          //  data[6] = occu;
                            PutData putData = new PutData("http://172.20.10.4/HomeRental/booking.php", "POST", field, data);
                            if (putData.startPut()) {
                                if (putData.onComplete()) {
                                    //progressBar.setVisibility(View.GONE);
                                    String result = putData.getResult();
                                    if (result.equals("Booking Success")) {
                                        Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(getApplicationContext(), RentPlan.class);
                                        startActivity(intent);
                                        finish();
                                    } else {
                                        Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                            //End Write and Read data with URL
                        }
                    });
                } else {
                    Toast.makeText(getApplicationContext(), "All field are required", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}