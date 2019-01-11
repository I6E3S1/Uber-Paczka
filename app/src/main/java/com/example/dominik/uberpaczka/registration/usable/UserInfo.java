package com.example.dominik.uberpaczka.registration.usable;


import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class UserInfo implements Serializable {


    private String password;
    private String email;
    private String userID;
    private String name;
    private String surname;
    private String date;
    private String phone;
    private String creditCardNumber;
    private String ccv;
    private String street;
    private String flat;
    private String city;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Map<String, String> getUserInfo() {


        Map<String, String> userInfoMap = new HashMap<>();
        userInfoMap.put("userID", userID);

        userInfoMap.put("name", name);

        userInfoMap.put("surname", surname);

        userInfoMap.put("date", date);

        userInfoMap.put("phone", phone);

        userInfoMap.put("creditCardNumber", creditCardNumber);

        userInfoMap.put("ccv", ccv);

        userInfoMap.put("street", street);

        userInfoMap.put("flat", flat);

        userInfoMap.put("city", city);

        return userInfoMap;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCreditCardNumber() {
        return creditCardNumber;
    }

    public void setCreditCardNumber(String creditCardNumber) {
        this.creditCardNumber = creditCardNumber;
    }

    public String getCcv() {
        return ccv;
    }

    public void setCcv(String ccv) {
        this.ccv = ccv;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getFlat() {
        return flat;
    }

    public void setFlat(String flat) {
        this.flat = flat;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}


