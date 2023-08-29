package com.example.homerental;

public class User {

    private String userID, address, buildingType, posscode, DAV, price, note, numOfRoom, numOfToilets, city, state, status;

    public String getFullName() {
        return fullName;
    }

    public String getEmail() {
        return email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getTotalOfKids() {
        return totalOfKids;
    }

    public void setTotalOfKids(String totalOfKids) {
        this.totalOfKids = totalOfKids;
    }

    private String fullName, email, username, phoneNumber, age, totalOfKids;

    private static final User instance = new User();

    public static User getInstance() {
        return instance;

    }

    public User() {
        super();
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }


    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPosscode() {
        return posscode;
    }

    public void setPosscode(String posscode) {
        this.posscode = posscode;
    }

    public String getDAV() {
        return DAV;
    }

    public void setDAV(String DAV) {
        this.DAV = DAV;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getNumOfRoom() {
        return numOfRoom;
    }

    public void setNumOfRoom(String numOfRoom) {
        this.numOfRoom = numOfRoom;
    }

    public String getNumOfToilets() {
        return numOfToilets;
    }

    public void setNumOfToilets(String numOfToilets) {
        this.numOfToilets = numOfToilets;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getBuildingType() {
        return buildingType;
    }

    public void setBuildingType(String buildingType) {
        this.buildingType = buildingType;
    }

    public void setFullName(String fullName) {
    }

    public void setEmail(String email) {
    }

}
