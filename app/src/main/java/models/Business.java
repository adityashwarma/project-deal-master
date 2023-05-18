package models;

import com.google.firebase.database.IgnoreExtraProperties;

import java.util.ArrayList;

import utilities.UserTypes.UserType;

@IgnoreExtraProperties
public class Business extends User {
    String address;
    String phoneNumber;
    String type;
    ArrayList<String> categories;
    ArrayList<String> postedDeals;

    public Business(){
    }

    public Business(String username, String userEmail, String mAddress, String mPhoneNumber, String type) {
        super(username, userEmail, UserType.BUSINESS);
        this.address = mAddress;
        this.phoneNumber = mPhoneNumber;
        this.type = type;
        new Business(username, userEmail, 0.0,0.0, mAddress, type,mPhoneNumber, null, null);
    }

    public Business(String mUsername, String mUserEmail, double mLatitude, double mLongitude, String mAddress, String mPhoneNumber, String type, ArrayList<String> mCategories, ArrayList<String> mPostedDeals) {
        super(mUsername, mUserEmail, mLatitude, mLongitude, UserType.BUSINESS);
        this.address = mAddress;
        this.phoneNumber = mPhoneNumber;
        this.type = type;
        this.categories = mCategories;
        this.postedDeals = mPostedDeals;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public ArrayList<String> getCategories() {
        return categories;
    }

    public void setCategories(ArrayList<String> categories) {
        this.categories = categories;
    }

    public ArrayList<String> getPostedDeals() {
        return postedDeals;
    }

    public void setPostedDeals(ArrayList<String> postedDeals) {
        this.postedDeals = postedDeals;
    }
}
