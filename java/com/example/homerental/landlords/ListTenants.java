package com.example.homerental.landlords;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.homerental.R;

public class ListTenants {

    String Username;
    String Status;


    public ListTenants(String Username, String Status) {

        this.Username = Username;
        this.Status = Status;
    }


    public String getUsername() {
        return Username;
    }

    public String getStatus() {
        return Status;
    }
}