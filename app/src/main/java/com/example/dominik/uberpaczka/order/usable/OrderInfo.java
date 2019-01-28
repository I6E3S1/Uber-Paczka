package com.example.dominik.uberpaczka.order.usable;

import com.google.firebase.firestore.Exclude;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Created by marek on 09.01.2019.
 */

public class OrderInfo implements Serializable {

    @Exclude
    private String id;
    private String userID;
    private String driverID = "";
    private String smallPackagesQuantity;
    private String mediumPackagesQuantity;
    private String bigPackagesQuantity;
    private String from;//with ID
    private String destination;//with ID
    private String fromName;//just name
    private String destinationName;//just name
    private String date;
    private String recipient;
    private String recipientPhone;
    private String price;
    private PackageStatus packageStatus = PackageStatus.waiting_for_driver;


    public OrderInfo() {
        smallPackagesQuantity = Integer.toString(0);
        mediumPackagesQuantity = Integer.toString(0);
        bigPackagesQuantity = Integer.toString(0);

    }


    public Double multiplier(){
        if (Integer.parseInt(smallPackagesQuantity) != 0) {
            return 0.16;
        }
        if (Integer.parseInt(mediumPackagesQuantity) != 0) {
            return 0.24;
        }
        if (Integer.parseInt(bigPackagesQuantity) != 0) {
            return 0.32;
        }
        return 0.00;
    }



    public Map<String, String> getOrderInfoMap() {
        Map<String, String> orderInfoMap = new HashMap<>();

        orderInfoMap.put("userID", userID);
        orderInfoMap.put("driverID", driverID);
        orderInfoMap.put("fromName", fromName);
        orderInfoMap.put("from", from);
        orderInfoMap.put("packageStatus", packageStatus.name());
        orderInfoMap.put("destinationName", destinationName);
        orderInfoMap.put("destination", destination);
        orderInfoMap.put("smallPackagesQuantity", smallPackagesQuantity);
        orderInfoMap.put("mediumPackagesQuantity", mediumPackagesQuantity);
        orderInfoMap.put("bigPackagesQuantity", bigPackagesQuantity);
        orderInfoMap.put("recipient", recipient);
        orderInfoMap.put("recipientPhone", recipientPhone);
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getDriverID() {
        return driverID;
    }

    public void setDriverID(String driverID) {
        this.driverID = driverID;
    }

    public String getSmallPackagesQuantity() {
        return smallPackagesQuantity;
    }

    public void setSmallPackagesQuantity(String smallPackagesQuantity) {
        this.smallPackagesQuantity = smallPackagesQuantity;
    }

    public String getMediumPackagesQuantity() {
        return mediumPackagesQuantity;
    }

    public void setMediumPackagesQuantity(String mediumPackagesQuantity) {
        this.mediumPackagesQuantity = mediumPackagesQuantity;
    }

    public String getBigPackagesQuantity() {
        return bigPackagesQuantity;
    }

    public void setBigPackagesQuantity(String bigPackagesQuantity) {
        this.bigPackagesQuantity = bigPackagesQuantity;
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

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public PackageStatus getPackageStatus() {
        return packageStatus;
    }

    public void setPackageStatus(PackageStatus packageStatus) {
        this.packageStatus = packageStatus;
    }

    @Override
    public String toString() {
        return "OrderInfo{" +
                "id='" + id + '\'' +
                ", userID='" + userID + '\'' +
                ", driverID='" + driverID + '\'' +
                ", smallPackagesQuantity='" + smallPackagesQuantity + '\'' +
                ", mediumPackagesQuantity='" + mediumPackagesQuantity + '\'' +
                ", bigPackagesQuantity='" + bigPackagesQuantity + '\'' +
                ", from='" + from + '\'' +
                ", destination='" + destination + '\'' +
                ", fromName='" + fromName + '\'' +
                ", destinationName='" + destinationName + '\'' +
                ", date='" + date + '\'' +
                ", recipient='" + recipient + '\'' +
                ", recipientPhone='" + recipientPhone + '\'' +
                ", price='" + price + '\'' +
                ", packageStatus=" + packageStatus +
                '}';
    }
}
