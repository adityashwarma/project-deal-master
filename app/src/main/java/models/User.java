package models;

import com.google.firebase.database.IgnoreExtraProperties;

import utilities.UserTypes.UserType;

@IgnoreExtraProperties
public class User {

     String id;
     String username;
     String userEmail;
     private UserType userType;
     double latitude;
     double longitude;

     public User() {

     }
     public User(String username, String userEmail, UserType uType) {
          this.username = username;
          this.userEmail = userEmail;
          userType = uType;
          new User(this.username, this.userEmail, 0.0, 0.0, userType);
     }

     public void setUsername(String username) {
          this.username = username;
     }

     public void setUserEmail(String userEmail) {
          this.userEmail = userEmail;
     }

     public void setUserType(UserType userType) {
          this.userType = userType;
     }

     public void setLatitude(double latitude) {
          this.latitude = latitude;
     }

     public void setLongitude(double longitude) {
          this.longitude = longitude;
     }

     public User(String mUsername, String mUserEmail, double mLatitude, double mLongitude, UserType userType) {
          this.username = mUsername;
          this.userEmail = mUserEmail;
          this.latitude = mLatitude;
          this.longitude = mLongitude;
          //mUserType = userType;
     }

     public void setId(String id) {
          this.id = id;
     }

     public String getId() {
          return id;
     }

     public String getUsername() {
          return username;
     }

     public String getUserEmail() {
          return userEmail;
     }

     public double getLatitude() {
          return latitude;
     }

     public double getLongitude() {
          return longitude;
     }

     public UserType getUserType() {
          return userType;
     }
}
