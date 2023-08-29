package com.example.homerental.tenants;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.homerental.Login;
import com.example.homerental.R;
import com.example.homerental.User;
import com.google.android.material.textfield.TextInputEditText;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

import java.util.Calendar;

public class Booking2 extends AppCompatActivity {

    TextInputEditText textInputEditTextName, textInputEditTextEmailAddress, textInputEditTextNP, textInputEditTextMID, textInputEditTextTD, textInputEditTextN, textInputEditTextO;
    Button buttonProceedPayment, BackBtn;
    CalendarView calendar;
    String date;
    TextView titleTV;
    ImageView leftImageView, rightImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking2);

        textInputEditTextName = findViewById(R.id.tName);
        textInputEditTextEmailAddress = findViewById(R.id.tEmail);
        textInputEditTextNP = findViewById(R.id.tPhoneNum);
        textInputEditTextMID = findViewById(R.id.tMID);
        textInputEditTextTD = findViewById(R.id.tenantDuration);
        textInputEditTextN = findViewById(R.id.tNationality);
        BackBtn = findViewById(R.id.buttonBack);
        calendar = findViewById(R.id.calendar);
        titleTV = findViewById(R.id.toolbar_title);
        buttonProceedPayment = findViewById(R.id.btnProceedPayment);
       // buttonMAP = findViewById(R.id.proceedPayment);


        BackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // Intent intent = new Intent(Booking2.this, TenantsChoice.class);
                //startActivity(intent);

                Intent intent = new Intent(getApplicationContext(), TenantsChoice.class);
                startActivity(intent);
                //Toast.makeText(Booking2.this, "You clicked in left", Toast.LENGTH_SHORT).show();
            }
        });

        textInputEditTextMID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();

                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        // on below line we are passing context.
                        Booking2.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                // on below line we are setting date to our edit text.
                                textInputEditTextMID.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                            }
                        },
                        // on below line we are passing year,
                        // month and day for selected date in our date picker.
                        year, month, day);
                // at last we are calling show to
                // display our date picker dialog.
                datePickerDialog.show();
            }
        });


        buttonProceedPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name, email, phonenumber, moveindate, duration, nationality, username;
                name = String.valueOf(textInputEditTextName.getText());
                email = String.valueOf(textInputEditTextEmailAddress.getText());
                phonenumber = String.valueOf(textInputEditTextNP.getText());
                moveindate = String.valueOf(textInputEditTextMID.getText());
                duration = String.valueOf(textInputEditTextTD.getText());
                nationality = String.valueOf(textInputEditTextN.getText());
                username = User.getInstance().getUserID();
                if (!name.equals("") && !email.equals("") && !phonenumber.equals("") && !moveindate.equals("")) {

                    Handler handler = new Handler();
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            //Starting Write and Read data with URL
                            //Creating array for parameters
                            String[] field = new String[7];
                            field[0] = "Name";
                            field[1] = "Email";
                            field[2] = "PhoneNumber";
                            field[3] = "MoveInDate";
                            field[4] = "Duration";
                            field[5] = "Nationality";
                            field[6] = "username";
                            // field[6] = "occupation";
                            //Creating array for data
                            String[] data = new String[7];
                            data[0] = name;
                            data[1] = email;
                            data[2] = phonenumber;
                            data[3] = moveindate;
                            data[4] = duration;
                            data[5] = nationality;
                            data[6] = username;
                            //  data[6] = occu;
                            PutData putData = new PutData("http://172.20.10.4/HomeRental/booking.php", "POST", field, data);
                            if (putData.startPut()) {
                                if (putData.onComplete()) {
                                    //progressBar.setVisibility(View.GONE);

                                    String result = putData.getResult();
                                    Log.e("anyText", result);
                                    if (result.equals("Proceed to Payment")) {
                                        Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(getApplicationContext(), RentPlan.class);
                                        startActivity(intent);


                                        // finish();
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