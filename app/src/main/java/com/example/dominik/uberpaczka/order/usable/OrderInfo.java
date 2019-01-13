package com.example.dominik.uberpaczka.order.usable;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Created by marek on 09.01.2019.
 */

public class OrderInfo implements Serializable {



    private String userID;
    private String driverID = "";
    private Integer smallPackagesQuantity;
    private Integer mediumPackagesQuantity;
    private Integer bigPackagesQuantity;
    private String from;//with ID
    private String destination;//with ID
    private String fromName;//just name
    private String destinationName;//just name
    private String date;
    private String recipient;
    private String recipientPhone;
    private String price;
    private PackageStatus packageStatus;

    public OrderInfo() {
        smallPackagesQuantity = 0;
        mediumPackagesQuantity = 0;
        bigPackagesQuantity = 0;

    }


    public Double multiplier(){
        if(smallPackagesQuantity!=0) {
            return 0.16;
        }
        if(mediumPackagesQuantity!=0) {
            return 0.24;
        }
        if(bigPackagesQuantity!=0) {
            return 0.32;
        }
        return 0.00;
    }



    public Map<String, String> getOrderInfoMap() {
        Map<String, String> orderInfoMap = new HashMap<>();
        packageStatus = PackageStatus.waiting_for_driver;


        orderInfoMap.put("user_id", userID);
        orderInfoMap.put("driver_ID", driverID);
        orderInfoMap.put("from_adress", fromName);
        orderInfoMap.put("from_ID",from);
        orderInfoMap.put("package_status", packageStatus.name());
        orderInfoMap.put("destinatin adress", destinationName);
        orderInfoMap.put("destination_ID",destination);
        orderInfoMap.put("small_packages_quantity", smallPackagesQuantity.toString());
        orderInfoMap.put("medium_packages_quantity", mediumPackagesQuantity.toString());
        orderInfoMap.put("big_packages_quantity", bigPackagesQuantity.toString());
        orderInfoMap.put("recipient", recipient);
        orderInfoMap.put("recipient_phone", recipientPhone);
        orderInfoMap.put("price",price);

        return orderInfoMap;

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OrderInfo)) return false;
        OrderInfo orderInfo = (OrderInfo) o;
        return Objects.equals(getUserID(), orderInfo.getUserID()) &&
                Objects.equals(getSmallPackagesQuantity(), orderInfo.getSmallPackagesQuantity()) &&
                Objects.equals(getMediumPackagesQuantity(), orderInfo.getMediumPackagesQuantity()) &&
                Objects.equals(getBigPackagesQuantity(), orderInfo.getBigPackagesQuantity()) &&
                Objects.equals(getFrom(), orderInfo.getFrom()) &&
                Objects.equals(getDestination(), orderInfo.getDestination()) &&
                Objects.equals(getFromName(), orderInfo.getFromName()) &&
                Objects.equals(getDestinationName(), orderInfo.getDestinationName()) &&
                Objects.equals(getDate(), orderInfo.getDate()) &&
                Objects.equals(getRecipient(), orderInfo.getRecipient()) &&
                Objects.equals(getRecipientPhone(), orderInfo.getRecipientPhone()) &&
                Objects.equals(getPrice(), orderInfo.getPrice());
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

    public String getFromName() {
        return fromName;
    }

    public void setFromName(String fromName) {
        this.fromName = fromName;
    }

    public String getDestinationName() {
        return destinationName;
    }

    public void setDestinationName(String destinationName) {
        this.destinationName = destinationName;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
