package com.example.dealrunner;

import android.Manifest;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import models.Deal;
import utilities.FirebaseHandlers;

public class DealsFragment extends Fragment {
    private View view;

    TextView customerAddressTextView;
    private Activity mParentActivity;
    private LocationCallback locationCallback;

    int REQUEST_CODE = 1;
    FusedLocationProviderClient fusedLocationProviderClient; //set global variable
    Location currentLocation;//set global var
    private boolean mLocationPermissionGranted; //set global var

    private DealsAdapter newDealsAdapter;

    public void setParentActivity(Activity parentActivity) {
        mParentActivity = parentActivity;
    }

    private void fetchLastLocation() {

        if (ActivityCompat.checkSelfPermission(mParentActivity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(mParentActivity, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE);
            if (ActivityCompat.checkSelfPermission(mParentActivity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                customerAddressTextView.setText(mParentActivity.getString(R.string.location_permission_denied));
            }
            return;
        } else {
            mLocationPermissionGranted = true;
        }
        LocationRequest request = LocationRequest.create();
        fusedLocationProviderClient.requestLocationUpdates(request,locationCallback, Looper.getMainLooper());
    }

    private String getAddressText(Address address) {
        String addressText = "";
        final int maxAddressLineIndex = address.getMaxAddressLineIndex();
        for (int i = 0; i <= maxAddressLineIndex; i++) {
            addressText += address.getAddressLine(i);
            if (i != maxAddressLineIndex) {
                addressText += "\n";
            }
        }
        String[] simpleAddress = addressText.split(",", 2);
        return simpleAddress[0];
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.deals_consumer, container, false);

        RecyclerView dealsView = view.findViewById(R.id.deals_list);
        customerAddressTextView = view.findViewById(R.id.customer_address);
        dealsView.setLayoutManager(new LinearLayoutManager(mParentActivity));

        FirebaseRecyclerOptions<Deal> newDealsRecyclerOptions = new FirebaseRecyclerOptions.Builder<Deal>()
                                                                    .setQuery(FirebaseHandlers.getDealsReference(), Deal.class)
                                                                    .build();

        newDealsAdapter = new DealsAdapter(newDealsRecyclerOptions);
        dealsView.setAdapter(newDealsAdapter);
        if(!isServiceRunning()) {
            Intent intent = new Intent(mParentActivity, DatabaseListenerService.class);
            mParentActivity.startService(intent);
        }

        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                for (Location location : locationResult.getLocations()) {
                    currentLocation = location;
                    Geocoder geocoder = new Geocoder(mParentActivity, Locale.getDefault());
                    try {
                        List<Address> addresses = geocoder.getFromLocation(currentLocation.getLatitude(), currentLocation.getLongitude(), 1);
                        if (addresses != null && addresses.size() > 0)
                            customerAddressTextView.setText(getAddressText(addresses.get(0)));
                        newDealsAdapter.setUserCurrentLocation(currentLocation);
                        newDealsAdapter.notifyDataSetChanged();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        };

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        newDealsAdapter.startListening();
    }

    @Override
    public void onResume() {
        super.onResume();
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(mParentActivity); //write this oncreate
        fetchLastLocation();  // call the funct for current location
    }

    public void onStop() {
        super.onStop();
        //newDealsAdapter.stopListening();
    }

    private boolean isServiceRunning(){
        ActivityManager manager = (ActivityManager) mParentActivity.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (DatabaseListenerService.class.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }
}
