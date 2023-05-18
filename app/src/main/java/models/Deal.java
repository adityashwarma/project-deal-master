package models;

import android.net.wifi.p2p.WifiP2pManager;

import java.io.Serializable;

import utilities.UserTypes;

public class Deal  implements Serializable {
    private String address;
    private String businessName;
    private String category;
    private String dealID;
    private String description;
    private int discount;
    private String email;
    private String expiry;
    private double latitude;
    private double longitude;
    private String phone;
    private String title;
    public Deal(){}
    public Deal(String maddress,
                String mbusinessName,
                String mcategory,
                String mdealID,
                String mdescription,
                int mdiscount,
                String memail,
                String mexpiry,
                double mlatitude,
                double mlongitude,
                String mphone,
                String mtitle) {
        this.setAddress(maddress);
        this.setBusinessName(mbusinessName);
        this.setCategory(mcategory);
        this.setDealID(mdealID);
        this.setDescription(mdescription);
        this.setDiscount(mdiscount);
        this.setEmail(memail);
        this.setExpiry(mexpiry);
        this.setLatitude(mlatitude);
        this.setLongitude(mlongitude);
        this.setPhone(mphone);
        this.setTitle(mtitle);
    }

    public String getAddress() {
        return address;
    }

    public String getBusinessName() {
        return businessName;
    }

    public String getCategory() {
        return category;
    }

    public String getDealID() {
        return dealID;
    }

    public String getDescription() {
        return description;
    }

    public int getDiscount() {
        return discount;
    }

    public String getEmail() {
        return email;
    }

    public String getExpiry() {
        return expiry;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public String getPhone() {
        return phone;
    }

    public String getTitle() {
        return title;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setDealID(String dealID) {
        this.dealID = dealID;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setExpiry(String expiry) {
        this.expiry = expiry;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
