package com.example.dealrunner;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import models.Deal;
import utilities.FirebaseHandlers;

public class PushDeal extends Fragment{
    private View view;
    private final String TAG = "PushDeal";
    private Activity mParentActivity;
    private FragmentCommunicatorListener communicatorListener;


    private EditText dealAddress;
    private TextInputLayout dealAddressTIL;
    private EditText dealBusinessName;
    private TextInputLayout dealBusinessNameTIL;
    private EditText dealCategory;
    private TextInputLayout dealCategoryTIL;

    private EditText dealDescription;
    private TextInputLayout dealDescriptionTIL;
    private EditText dealDiscount;
    private TextInputLayout dealDiscountTIL;
    private EditText dealEmail;
    private TextInputLayout dealEmailTIL;
    private EditText dealExpiry;
    private TextInputLayout dealExpiryTIL;

    private EditText dealPhone;
    private TextInputLayout dealPhoneTIL;
    private EditText dealTitle;
    private TextInputLayout dealTitleTIL;
    private Button pushDealButton;

    /*for get location */
    private Double latitude;
    private Double longitude;
    private Location currentLocation;
    int REQUEST_CODE = 1;
    FusedLocationProviderClient fusedLocationProviderClient;
    boolean mLocationPermissionGranted;

    public PushDeal() {

    }

    public void setParentActivity(Activity parentActivity) {
        mParentActivity = parentActivity;
    }
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,@Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.push_deal, container, false);

        /**
         *
         * TODO: This code is required to setup the spinner in business signup page and needs to be moved to its fragment.
         setContentView(R.layout.signup_business_page2);
         Spinner mySpinner = (Spinner) findViewById(R.id.spinner_business_type);
         CustomAdapter customAdapter = new CustomAdapter(this);
         ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(Login.this,
         android.R.layout.simple_spinner_item,getResources().getStringArray(R.array.business_types));


         myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
         mySpinner.setAdapter(customAdapter);
         **/


        dealAddress = (EditText) view.findViewById(R.id.deal_address);
        dealAddressTIL = (TextInputLayout) view.findViewById(R.id.deal_address_text_input_layout);
        dealBusinessName = (EditText) view.findViewById(R.id.deal_businessName);
        dealBusinessNameTIL = (TextInputLayout) view.findViewById(R.id.deal_businessName_text_input_layout);
        dealCategory = (EditText) view.findViewById(R.id.deal_category);
        dealCategoryTIL = (TextInputLayout) view.findViewById(R.id.deal_category_text_input_layout);

        dealDescription = (EditText) view.findViewById(R.id.deal_description);
        dealDescriptionTIL = (TextInputLayout) view.findViewById(R.id.deal_description_text_input_layout);
        dealDiscount = (EditText) view.findViewById(R.id.deal_discount);
        dealDiscountTIL = (TextInputLayout) view.findViewById(R.id.deal_discount_text_input_layout);
        dealEmail = (EditText) view.findViewById(R.id.deal_email);
        dealEmailTIL = (TextInputLayout) view.findViewById(R.id.deal_email_text_input_layout);
        dealExpiry = (EditText) view.findViewById(R.id.deal_expiry);
        dealExpiryTIL = (TextInputLayout) view.findViewById(R.id.deal_expiry_text_input_layout);

        dealPhone = (EditText) view.findViewById(R.id.deal_phone);
        dealPhoneTIL = (TextInputLayout) view.findViewById(R.id.deal_phone_text_input_layout);
        dealTitle = (EditText) view.findViewById(R.id.deal_title);
        dealTitleTIL = (TextInputLayout) view.findViewById(R.id.deal_title_text_input_layout);
        pushDealButton = (Button) view.findViewById(R.id.push_deal_button);
//
//        dealAddress.setOnFocusChangeListener(this);
//        dealBusinessName.setOnFocusChangeListener(this);
//        dealCategory.setOnFocusChangeListener(this);
//        dealDealID.setOnFocusChangeListener(this);
//        dealDescription.setOnFocusChangeListener(this);
//        dealDiscount.setOnFocusChangeListener(this);
//        dealEmail.setOnFocusChangeListener(this);
//        dealExpiry.setOnFocusChangeListener(this);
//        dealLatitude.setOnFocusChangeListener(this);
//        dealLongitude.setOnFocusChangeListener(this);
//        dealPhone.setOnFocusChangeListener(this);
//        dealTitle.setOnFocusChangeListener(this);

        if (ActivityCompat.checkSelfPermission(mParentActivity.getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(mParentActivity, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE);
            if (ActivityCompat.checkSelfPermission(mParentActivity.getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(mParentActivity, R.string.location_permission_denied, Toast.LENGTH_LONG).show();
            }
        } else {
            mLocationPermissionGranted = true;
        }

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(mParentActivity);
        Task<Location> task = fusedLocationProviderClient.getLastLocation();

        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    currentLocation = location;
                    longitude = currentLocation.getLongitude();
                    latitude = currentLocation.getLatitude();
                }
            }
        });

        pushDealButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateAndPush();
            }
        });
        return view;
    }

    private void validateAndPush() {

        String address = dealAddress.getText().toString();
        String businessName = dealBusinessName.getText().toString();
        String category = dealCategory.getText().toString();
        String description = dealDescription.getText().toString();
        int discount = Integer.parseInt(dealDiscount.getText().toString());
        String email = dealEmail.getText().toString();
        String expiry = dealExpiry.getText().toString();
        String phone = dealPhone.getText().toString();
        String title = dealTitle.getText().toString();


        String randomLetters =  java.util.UUID.randomUUID().toString();
        String dealID = randomLetters;







        dealAddressTIL.setError(null);
        dealBusinessNameTIL.setError(null);
        dealCategoryTIL.setError(null);

        dealDescriptionTIL.setError(null);
        dealDiscountTIL.setError(null);
        dealEmailTIL.setError(null);
        dealExpiryTIL.setError(null);

        dealPhoneTIL.setError(null);
        dealTitleTIL.setError(null);



        Deal deal=new Deal(address,businessName,category,dealID,description,discount,email,expiry,latitude,longitude,phone,title);

        pushDealOnFirebase(deal);
    }

    public void setFragmentCommunicatorListener(FragmentCommunicatorListener communicatorListener) {
        this.communicatorListener = communicatorListener;
    }

    private void pushDealOnFirebase(Deal deal) {
        FirebaseHandlers.pushDeal(deal, new FirebaseHandlers.PushDealListener(){
            @Override
            public void onPushDealComplete(boolean success, String message) {
                if(success) {
                    //startActivity(new Intent(mParentActivity, DealsActivity.class));
                    Toast.makeText(mParentActivity, message, Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(mParentActivity, message, Toast.LENGTH_LONG).show();
                }
            }
        });
    }

// TODO: 2022/3/27  real validate check
//    @Override
//    public void onFocusChange(View view, boolean hasFocus) {
//        if(!hasFocus) {
//            if(view.getId() == R.id.consumerEmail) consumerEmailTIL.setError(null);
//            if(view.getId() == R.id.consumerFirstName) consumerFirstNameTIL.setError(null);
//            if(view.getId() == R.id.consumerLastName) consumerLastNameTIL.setError(null);
//            if(view.getId() == R.id.consumerPassword) consumerPasswordTIL.setError(null);
//            if(view.getId() == R.id.consumerConfirmPassword) consumerConfirmPasswordTIL.setError(null);
//        }
//    }
}

