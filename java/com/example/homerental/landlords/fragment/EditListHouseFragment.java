package com.example.homerental.landlords.fragment;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.util.Log;
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

import com.example.homerental.R;
import com.example.homerental.User;
import com.google.android.material.textfield.TextInputEditText;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

import java.util.Calendar;
import java.util.HashMap;

public class EditListHouseFragment extends Fragment  implements AdapterView.OnItemSelectedListener{

    TextInputEditText EditAddress, EditPosscode, EditDAV, EditPrice, EditNote;
    Button updateBtn, buttonBackE, mEditImage;
    String BuildingType, Address, Posscode, DAV, Price, Note;
    Spinner EditBuildingTypeSP, EditNumOfRoomSP, EditNumOfToiletsSP, EditCitySP, EditStateSP, EditStatusSP;
    ImageView ivHouse;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_edit_list_house, container, false);

        ivHouse = v.findViewById(R.id.imageView);
        EditBuildingTypeSP = v.findViewById(R.id.spinnerBuildingType);
        EditNumOfRoomSP = v.findViewById(R.id.numOfRoom);
        EditNumOfToiletsSP = v.findViewById(R.id.numOfToilets);
        EditAddress = v.findViewById(R.id.address);
        EditCitySP = v.findViewById(R.id.city);
        EditStateSP = v.findViewById(R.id.state);
        EditPosscode = v.findViewById(R.id.posscode);
        EditDAV = v.findViewById(R.id.dav);
        EditStatusSP = v.findViewById(R.id.status);
        EditPrice = v.findViewById(R.id.price);
        EditNote = v.findViewById(R.id.note);
        buttonBackE = v.findViewById(R.id.backBtn);
       // mEditImage = v.findViewById(R.id.id);
        updateBtn = v.findViewById(R.id.idBtnUpdate);

        ArrayAdapter<CharSequence> adapter;
        adapter = ArrayAdapter.createFromResource(getActivity(), R.array.NumOfRoom, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        EditNumOfRoomSP.setOnItemSelectedListener(this);
        EditNumOfRoomSP.setAdapter(adapter);

        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(getActivity(), R.array.NumOfToilets, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        EditNumOfToiletsSP.setOnItemSelectedListener(this);
        EditNumOfToiletsSP.setAdapter(adapter2);

        ArrayAdapter<CharSequence> adapter3 = ArrayAdapter.createFromResource(getActivity(), R.array.City, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        EditCitySP.setOnItemSelectedListener(this);
        EditCitySP.setAdapter(adapter3);

        ArrayAdapter<CharSequence> adapter4 = ArrayAdapter.createFromResource(getActivity(), R.array.State, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        adapter4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        EditStateSP.setOnItemSelectedListener(this);
        EditStateSP.setAdapter(adapter4);

        ArrayAdapter<CharSequence> adapter5 = ArrayAdapter.createFromResource(getActivity(), R.array.Status, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        adapter5.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        EditStatusSP.setOnItemSelectedListener(this);
        EditStatusSP.setAdapter(adapter5);

        ArrayAdapter<CharSequence> adapter6 = ArrayAdapter.createFromResource(getActivity(), R.array.BuildingType, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        adapter5.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        EditBuildingTypeSP.setOnItemSelectedListener(this);
        EditBuildingTypeSP.setAdapter(adapter6);


        buttonBackE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppCompatActivity activity = (AppCompatActivity) v.getContext();
                Fragment myFragment = new YourPropertyFragment();
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, myFragment).addToBackStack(null).commit();

            }
        });

        EditDAV.setOnClickListener(new View.OnClickListener() {
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
                                EditDAV.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
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

        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Address = String.valueOf(EditAddress.getText());
                Posscode = String.valueOf(EditPosscode.getText());
                DAV = String.valueOf(EditDAV.getText());
                Price = String.valueOf(EditPrice.getText());
                Note = String.valueOf(EditNote.getText());

                //String Image = String.valueOf(ivHouse.getTag());

                String NumOfRoom = EditNumOfRoomSP.getSelectedItem().toString();
                String NumOfToilets = EditNumOfToiletsSP.getSelectedItem().toString();
                String City = EditCitySP.getSelectedItem().toString();
                String State = EditStateSP.getSelectedItem().toString();
                String Status = EditStatusSP.getSelectedItem().toString();
                String BuildingType = EditBuildingTypeSP.getSelectedItem().toString();
                String username = User.getInstance().getUserID();

                // validating the text fields if empty or not.
                if (!EditAddress.equals("") && !EditPosscode.equals("")) {

                    String userID = User.getInstance().getUserID();
                    Handler handler = new Handler();
                    handler.post(() -> {
                        //Starting Write and Read data with URL
                        //Creating array for parameters
                        String[] field = new String[12];
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
                        //field[12] = "Image";
                        //Creating array for data
                        String[] data = new String[12];
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
                        data[11] = username;
                        //data[12] = Image;
                        PutData putData = new PutData("http://172.20.10.4/HomeRental/update2.php", "POST", field, data);
                        if (putData.startPut()) {
                            if (putData.onComplete()) {

                                Log.e("try", Address);

                                Log.e("try", Posscode);
                                Log.e("try", Price);
                                String result = putData.getResult();
                                if (result.equals("Update Property Success")) {
                                    Toast.makeText(getContext(), result, Toast.LENGTH_SHORT).show();

                                    AppCompatActivity activity = (AppCompatActivity) v.getContext();
                                    Fragment myFragment = new YourPropertyFragment();
                                    activity.getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, myFragment).addToBackStack(null).commit();

                                    /*Intent intent = new Intent(getContext(), YourPropertyFragment.class);
                                    startActivity(intent);*/

                                } else {
                                    Toast.makeText(getContext(), result, Toast.LENGTH_SHORT).show();
                                    Log.e("Error", result);
                                }
                            }
                        }
                        //End Write and Read data with URL
                    });
                } else {
                    Toast.makeText(getContext(), "All field are required", Toast.LENGTH_SHORT).show();
                }

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