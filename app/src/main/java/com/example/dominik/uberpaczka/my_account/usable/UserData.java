package com.example.dominik.uberpaczka.my_account.usable;

/**
 * Created by marek on 06.01.2019.
 */

public class UserData {

    String description;
    String value;


    public UserData(String description, String value) {
        this.description = description;
        this.value = value;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
