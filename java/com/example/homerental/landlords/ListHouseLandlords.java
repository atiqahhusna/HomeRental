package com.example.homerental.landlords;

import android.content.Intent;
import android.view.View;

public class ListHouseLandlords {

    String Address;
    String Price;
    int image;


    public ListHouseLandlords(String Price, String Address) {

        this.Price = Price;
        this.Address = Address;

    }

    public String getPrice() {
        return Price;
    }

    public String getAddress() {
        return Address;
    }

    public int getImage() {
        return image;
    }


}
