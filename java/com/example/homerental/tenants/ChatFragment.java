package com.example.homerental.tenants;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.homerental.R;
import com.example.homerental.User;
import com.example.homerental.landlords.EditProfileLandlordActivity;
import com.example.homerental.landlords.fragment.YourPropertyFragment;
import com.google.android.material.textfield.TextInputEditText;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

public class ChatFragment extends Fragment {

}

  /*  private TextView textBooking, moveInDate, tenancyDuration, nationality, occupation;
    private TextInputEditText MoveInDate, TenancyDuration, NationalityTenants, OccupationTenants;
    private Button ChatButton;
    String MID, TD, NT, OT;

    @Override
 /*   public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_chat, container, false);

     /*   MoveInDate = v.findViewById(R.id.date);
        TenancyDuration = v.findViewById(R.id.tduration);
        NationalityTenants = v.findViewById(R.id.NatTenants);
        OccupationTenants = v.findViewById(R.id.occupationTenants);*/
     //   ChatButton = v.findViewById(R.id.chat);

      /*  ChatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean installed = appInstallOrNot("com.whatsapp");

                if (installed){
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse("http://api.whatsapp.com/send?phone="+"+60"+mobileNumber+"&text="+message));
                    startActivity(intent);
                }
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
        });

             //   Intent intent = new Intent(getContext(), ChatWithOwner.class);
              //  startActivity(intent);
              /*  MID = String.valueOf(MoveInDate.getText());
                TD = String.valueOf(TenancyDuration.getText());
                NT = String.valueOf(NationalityTenants.getText());
                OT = String.valueOf(OccupationTenants.getText());

                // validating the text fields if empty or not.
                if (!MID.equals("") && !TD.equals("")) {

                    String userID = User.getInstance().getUserID();
                    Handler handler = new Handler();
                    handler.post(() -> {
                        //Starting Write and Read data with URL
                        //Creating array for parameters
                        String[] field = new String[4];
                        field[0] = "MoveInDate";
                        field[1] = "TenancyDuration";
                        field[2] = "NationalityTenants";
                        field[3] = "OccupationTenants";

                        //Creating array for data
                        String[] data = new String[4];
                        data[0] = MID;
                        data[1] = TD;
                        data[2] = NT;
                        data[3] = OT;
                        PutData putData = new PutData("http://172.20.10.4/HomeRental/booking.php", "POST", field, data);
                        if (putData.startPut()) {
                            if (putData.onComplete()) {

                                String result = putData.getResult();
                                if (result.equals("Booking Success")) {
                                    Toast.makeText(getContext(), result, Toast.LENGTH_SHORT).show();
                                   // Intent intent = new Intent(getContext(), ChatWithOwner.class);
                                    //startActivity(intent);

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

            }
        });
                return v;

    }

}*/