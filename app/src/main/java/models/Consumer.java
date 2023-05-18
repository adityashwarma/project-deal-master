package models;

import com.google.firebase.database.IgnoreExtraProperties;

import java.util.ArrayList;

import utilities.UserTypes.UserType;

/**
 * Data model to hold the fields for Consumer type User.
 */
@IgnoreExtraProperties
public class Consumer extends User {

    ArrayList<String> claimedCoupons;
    ArrayList<String> categories;

    public Consumer() {
        super();
    }

    /**
     * Constructor. Use this when there is no permission for location and values are not confirmed
     * @param mUsername = user first name  + user last name
     * @param mUserEmail = user registered email address
     */
    public Consumer(String mUsername, String mUserEmail) {
        super(mUsername, mUserEmail, UserType.CONSUMER);
    }

    /**
     * Constructor. User this when user grants location permission
     */
    public Consumer(String mUsername, String mUserEmail, double mLatitude, double mLongitude) {
        super(mUsername, mUserEmail, mLatitude, mLongitude, UserType.CONSUMER);
    }

    public Consumer(String mUsername, String mUserEmail, double mLatitude, double mLongitude, ArrayList<String> claimedCoupons, ArrayList<String> categories) {
        super(mUsername, mUserEmail, mLatitude, mLongitude, UserType.CONSUMER);
        this.claimedCoupons = claimedCoupons;
        this.categories = categories;
    }

    public void setClaimedCoupons(ArrayList<String> claimedCoupons) {
        this.claimedCoupons = claimedCoupons;
    }

    public void setCategories(ArrayList<String> categories) {
        this.categories = categories;
    }

    public ArrayList<String> getClaimedCoupons() {
        return claimedCoupons;
    }

    public ArrayList<String> getCategories() {
        return categories;
    }
}
