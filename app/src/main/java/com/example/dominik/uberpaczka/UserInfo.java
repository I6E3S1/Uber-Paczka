package com.example.dominik.uberpaczka;


import android.widget.EditText;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserInfo {


    private String userID;
    private String name;
    private String surname;
    private String date;
    private String phone;
    private String karta;
    private String ccv;
    private String street;
    private String flat;
    private String city;

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setKarta(String karta) {
        this.karta = karta;
    }

    public void setCcv(String ccv) {
        this.ccv = ccv;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public void setFlat(String flat) {
        this.flat = flat;
    }

    public void setCity(String city) {
        this.city = city;
    }


    public void setUserID(String userID) {
        this.userID = userID;
    }


    public Map<String, String> getUserInfo() {


        Map<String, String> userInfoMap = new HashMap<>();
        userInfoMap.put("userID", userID);

        userInfoMap.put("name", name);

        userInfoMap.put("surname", surname);

        userInfoMap.put("date", date);

        userInfoMap.put("phone", phone);

        userInfoMap.put("karta", karta);

        userInfoMap.put("ccv", ccv);

        userInfoMap.put("street", street);

        userInfoMap.put("flat", flat);

        userInfoMap.put("city", city);

        return userInfoMap;
    }


    boolean check(String message, EditText editText, String error, String errorMessage) {

        Pattern pattern = Pattern.compile(error);
        Matcher mat = pattern.matcher(message);

        if (message.equalsIgnoreCase("")) {
            editText.setError("Pole nie może pozostać puste");
            return false;
        } else {
            if (mat.matches()) {
                return true;
            } else {
                editText.setError(errorMessage);
                return false;
            }
        }
    }

}


