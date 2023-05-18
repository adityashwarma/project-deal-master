package com.example.dealrunner;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;

import models.Consumer;
import utilities.FirebaseHandlers;

public class SettingsAccount extends Fragment implements FirebaseHandlers.ConsumerDetailsListener {
    private View view;
    private Consumer consumer;

    Button logoutBtn;
    private Activity mParentActivity;
    TextView name, email;
    private TextView locationPreferences;

    public void setParentActivity(Activity parentActivity) {
        mParentActivity = parentActivity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.account_settings, container, false);

        logoutBtn = view.findViewById(R.id.log_out_button);
        locationPreferences = view.findViewById(R.id.location_preferences);
        locationPreferences.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS), 0);
            }
        });

        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent i = new Intent(mParentActivity, MainActivity.class);
                startActivity(i);
                mParentActivity.finish();
            }
        });

        FirebaseHandlers.getConsumerDetails(this);
        name = view.findViewById(R.id.user_first_name);
        email = view.findViewById(R.id.user_email);

        return view;
    }


    @Override
    public void onConsumerDetailFetched(Consumer consumer) {
        name.setText(consumer.getUsername());
        email.setText(consumer.getUserEmail());
    }
}
