package com.example.dominik.uberpaczka.order.usable;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by marek on 09.01.2019.
 */

public class OrderInfo implements Serializable {


    private String userID;
    private Integer smallPackagesQuantity;
    private Integer mediumPackagesQuantity;
    private Integer bigPackagesQuantity;
    private String from;
    private String destination;
    private String date;
    private String recipient;
    private String recipientPhone;


    public OrderInfo() {
        smallPackagesQuantity = 0;
        mediumPackagesQuantity = 0;
        bigPackagesQuantity = 0;
    }

    public Map<String, String> getOrderInfoMap() {
        Map<String, String> orderInfoMap = new HashMap<>();

        orderInfoMap.put("user_id", userID);
        orderInfoMap.put("from_adress", from);
        orderInfoMap.put("destinatin adress", destination);
        orderInfoMap.put("small_packages_quantity", smallPackagesQuantity.toString());
        orderInfoMap.put("medium_packages_quantity", mediumPackagesQuantity.toString());
        orderInfoMap.put("big_packages_quantity", bigPackagesQuantity.toString());
        orderInfoMap.put("recipient", recipient);
        orderInfoMap.put("recipient_phone", recipientPhone);


        return orderInfoMap;

    }


    public Integer getSmallPackagesQuantity() {
        return smallPackagesQuantity;
    }

    public void setSmallPackagesQuantity(Integer smallPackagesQuantity) {
        this.smallPackagesQuantity = smallPackagesQuantity;
    }

    public Integer getMediumPackagesQuantity() {
        return mediumPackagesQuantity;
    }

    public void setMediumPackagesQuantity(Integer mediumPackagesQuantity) {
        this.mediumPackagesQuantity = mediumPackagesQuantity;
    }

    public Integer getBigPackagesQuantity() {
        return bigPackagesQuantity;
    }

    public void setBigPackagesQuantity(Integer bigPackagesQuantity) {
        this.bigPackagesQuantity = bigPackagesQuantity;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public String getRecipientPhone() {
        return recipientPhone;
    }

    public void setRecipientPhone(String recipientPhone) {
        this.recipientPhone = recipientPhone;
    }


    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }
}
