package com.example.homerental.tenants;

public class ListHouseTenants {

        String PropertyName;
        String Address;
        String Price;
        int image;


        public ListHouseTenants(String PropertyName, String Price, String Address, int image) {
            //this.PropertyName = PropertyName;
            this.Price = Price;
            this.Address = Address;
            this.image = image;
        }

        public ListHouseTenants(/*String propertyName,*/ String address, String price) {
           // this.PropertyName = propertyName;
            this.Price = price;
            this.Address = address;
        }



      /*  public String getPropertyName() {
            return PropertyName;
        }*/

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
