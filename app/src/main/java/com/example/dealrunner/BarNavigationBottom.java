package com.example.dealrunner;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import utilities.FirebaseHandlers;

public class BarNavigationBottom extends AppCompatActivity {

    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    private TextView dealsActivity, settingsPage;
    private DealsFragment fragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bottom_navigation_bar);

        addInitFragment();

        dealsActivity = findViewById(R.id.deals_home_button);
        settingsPage = findViewById(R.id.deals_settings_button);

        dealsActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(fragment != null && !fragment.isVisible()) {
                    DealsFragment dealsFrag = new DealsFragment();
                    dealsFrag.setParentActivity(BarNavigationBottom.this);
                    replaceFragment(dealsFrag);
                }
            }
        });

        settingsPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SettingsAccount settingsFrag = new SettingsAccount();
                settingsFrag.setParentActivity(BarNavigationBottom.this);
                replaceFragment(settingsFrag);
            }
        });
    }

    private void replaceFragment(Fragment fragment) {
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.home_screen_manager,fragment);
        fragmentTransaction.commit();
    }

    private void addInitFragment() {
        if (FirebaseAuth.getInstance().getCurrentUser() == null) {
            fragment = new DealsFragment();
            fragment.setParentActivity(BarNavigationBottom.this);
            fragmentManager = getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.add(R.id.home_screen_manager, fragment);
            fragmentTransaction.commit();
            return;
        }
        String userID = FirebaseHandlers.getOrCreateFirebaseAuth().getCurrentUser().getUid();
        FirebaseHandlers.getUserType(new FirebaseHandlers.UserTypeListener() {
            @Override
            public void usertypeFetched(String usertype) {
                fragmentManager = getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                if(usertype != null && usertype.equalsIgnoreCase("business")) {
                    PushDeal pushDealFragment = new PushDeal();
                    pushDealFragment.setParentActivity(BarNavigationBottom.this);
                    fragmentTransaction.add(R.id.home_screen_manager, pushDealFragment);
                } else {
                    fragment = new DealsFragment();
                    fragment.setParentActivity(BarNavigationBottom.this);
                    fragmentTransaction.add(R.id.home_screen_manager, fragment);
                }
                fragmentTransaction.commit();
            }
        });

    }

}
