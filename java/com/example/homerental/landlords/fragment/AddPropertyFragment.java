package com.example.homerental.landlords.fragment;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.homerental.R;
import com.example.homerental.User;
import com.example.homerental.tenants.Booking2;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.textfield.TextInputEditText;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

import java.util.Calendar;
import java.util.Map;


public class AddPropertyFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    TextInputEditText AddressEdt, PosscodeEdt, DAVEdt, PriceEdt, NoteEdt;
    Button saveBtn, mAddImage;
    String BuildingType, Address, Posscode, DAV, Price, Note;
    Spinner BuildingTypeSP, NumOfRoomSP, NumOfToiletsSP, CitySP, StateSP, StatusSP;
    ImageView ivHouse;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_add_property, container, false);

        ivHouse = v.findViewById(R.id.imageView);
        BuildingTypeSP = v.findViewById(R.id.spinnerBuildingType);
        NumOfRoomSP = v.findViewById(R.id.numOfRoom);
        NumOfToiletsSP = v.findViewById(R.id.numOfToilets);
        AddressEdt = v.findViewById(R.id.address);
        CitySP = v.findViewById(R.id.city);
        StateSP = v.findViewById(R.id.state);
        PosscodeEdt = v.findViewById(R.id.posscode);
        DAVEdt = v.findViewById(R.id.dav);
        StatusSP = v.findViewById(R.id.status);
        PriceEdt = v.findViewById(R.id.price);
        NoteEdt = v.findViewById(R.id.note);
     //   mAddImage = v.findViewById(R.id.uploadBtn);
        saveBtn = v.findViewById(R.id.idBtnSave);



        ArrayAdapter<CharSequence> adapter;
        adapter = ArrayAdapter.createFromResource(getActivity(), R.array.NumOfRoom, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        NumOfRoomSP.setOnItemSelectedListener(this);
        NumOfRoomSP.setAdapter(adapter);

        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(getActivity(), R.array.NumOfToilets, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        NumOfToiletsSP.setOnItemSelectedListener(this);
        NumOfToiletsSP.setAdapter(adapter2);

        ArrayAdapter<CharSequence> adapter3 = ArrayAdapter.createFromResource(getActivity(), R.array.City, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        CitySP.setOnItemSelectedListener(this);
        CitySP.setAdapter(adapter3);

        ArrayAdapter<CharSequence> adapter4 = ArrayAdapter.createFromResource(getActivity(), R.array.State, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        adapter4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        StateSP.setOnItemSelectedListener(this);
        StateSP.setAdapter(adapter4);

        ArrayAdapter<CharSequence> adapter5 = ArrayAdapter.createFromResource(getActivity(), R.array.Status, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        adapter5.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        StatusSP.setOnItemSelectedListener(this);
        StatusSP.setAdapter(adapter5);

        ArrayAdapter<CharSequence> adapter6 = ArrayAdapter.createFromResource(getActivity(), R.array.BuildingType, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        adapter5.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        BuildingTypeSP.setOnItemSelectedListener(this);
        BuildingTypeSP.setAdapter(adapter6);

        DAVEdt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();

                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        // on below line we are passing context.
                        getContext(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                // on below line we are setting date to our edit text.
                                DAVEdt.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
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

        saveBtn.setOnClickListener(v12 -> {

            Address = String.valueOf(AddressEdt.getText());
            Posscode = String.valueOf(PosscodeEdt.getText());
            DAV = String.valueOf(DAVEdt.getText());
            Price = String.valueOf(PriceEdt.getText());
            Note = String.valueOf(NoteEdt.getText());

            String Image = String.valueOf(ivHouse.getTag());

            String NumOfRoom = NumOfRoomSP.getSelectedItem().toString();
            String NumOfToilets = NumOfToiletsSP.getSelectedItem().toString();
            String City = CitySP.getSelectedItem().toString();
            String State = StateSP.getSelectedItem().toString();
            String Status = StatusSP.getSelectedItem().toString();
            String BuildingType = BuildingTypeSP.getSelectedItem().toString();

            // validating the text fields if empty or not.
            if (!AddressEdt.equals("") && !PosscodeEdt.equals("")) {

                String userID = User.getInstance().getUserID();
                Handler handler = new Handler();
                handler.post(() -> {
                    //Starting Write and Read data with URL
                    //Creating array for parameters
                    String[] field = new String[13];
                    field[0] = "BuildingType";
                    field[1] = "Address";
                    field[2] = "Posscode";
                    field[3] = "DAV";
                    field[4] = "Price";
                    field[5] = "Note";
                    field[6] = "NumOfRoom";
                    field[7] = "NumOfToilets";
                    field[8] = "City";
                    field[9] = "State";
                    field[10] = "Status";
                    field[11] = "username";
                    field[12] = "Image";
                    //Creating array for data
                    String[] data = new String[13];
                    data[0] = BuildingType;
                    data[1] = Address;
                    data[2] = Posscode;
                    data[3] = DAV;
                    data[4] = Price;
                    data[5] = Note;
                    data[6] = NumOfRoom;
                    data[7] = NumOfToilets;
                    data[8] = City;
                    data[9] = State;
                    data[10] = Status;
                    data[11] = userID;
                    data[12] = Image;
                    PutData putData = new PutData("http://172.20.10.4/HomeRental/addProperty.php", "POST", field, data);
                    if (putData.startPut()) {
                        if (putData.onComplete()) {

                            String result = putData.getResult();
                            if (result.equals("Add Property Success")) {
                                Toast.makeText(getContext(), result, Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getContext(), YourPropertyFragment.class);
                                startActivity(intent);

                            } else {
                                Toast.makeText(getContext(), result, Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                    //End Write and Read data with URL
                });
            } else {
                Toast.makeText(getContext(), "All field are required", Toast.LENGTH_SHORT).show();
            }

        });


        return v;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}