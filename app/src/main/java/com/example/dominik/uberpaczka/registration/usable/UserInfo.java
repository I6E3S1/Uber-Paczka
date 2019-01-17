package com.example.dominik.uberpaczka.registration.usable;


import com.example.dominik.uberpaczka.utils.UsernameFirestore;

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
    private AdressInfo adress;
    private CardInfo card;
    private Boolean driverAccount;


    public UserInfo() {
        adress = new AdressInfo();
        card = new CardInfo();
    }


    public Map<String, Object> getUserInfo() {


        Map<String, Object> userInfoMap = new HashMap<>();
        userInfoMap.put(UsernameFirestore.userID.name(), userID);

        userInfoMap.put(UsernameFirestore.name.name(), name);

        userInfoMap.put(UsernameFirestore.surname.name(), surname);

        userInfoMap.put(UsernameFirestore.date.name(), date);

        userInfoMap.put(UsernameFirestore.phone.name(), phone);

        userInfoMap.put(UsernameFirestore.adress.name(), adress.getAdressMap());

        userInfoMap.put(UsernameFirestore.card.name(), card.getCardInfoMap());

        userInfoMap.put(UsernameFirestore.driverAccount.name(), driverAccount);


        return userInfoMap;
    }


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

    public AdressInfo getAdress() {
        return adress;
    }

    public CardInfo getCard() {
        return card;
    }

    public Boolean getDriverAccount() {
        return driverAccount;
    }

    public void setDriverAccount(Boolean driverAccount) {
        this.driverAccount = driverAccount;
    }
}


