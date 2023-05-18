package com.example.dealrunner;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

public class MainActivity extends FragmentActivity implements FragmentCommunicatorListener {

    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addFragment();
    }

    public void addFragment() {
        Login fragment = new Login();
        fragment.setCallbackFragment(this);
        fragment.setParentActivity(this);
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.fragment_manager, fragment);
        fragmentTransaction.commit();
    }

    @Override
    public void loginButtonClicked() {
        addFragment();
    }

    @Override
    public void signupButtonClicked() {
        Decision fragment = new Decision();
        fragment.setCallbackFragment(this);
        replaceWithFragment(fragment);

    }


    @Override
    public void continueButtonClicked(Bundle bundle) {
        BusinessSignupFragment signupFragment = new BusinessSignupFragment();
        signupFragment.setParentActivity(this);
        signupFragment.setArguments(bundle);
        replaceWithFragment(signupFragment);
    }

    @Override
    public void grabDealsButtonClicked() {
        SignupConsumer fragment = new SignupConsumer();
        fragment.setFragmentCommunicatorListener(this);
        fragment.setParentActivity(this);
        replaceWithFragment(fragment);

    }

    @Override
    public void postDealsButtonClicked() {
        BusinessDetailsFragment detailsFragment = new BusinessDetailsFragment();
        detailsFragment.setFragmentCommunicatorListener(this);
        detailsFragment.setParentActivity(this);
        replaceWithFragment(detailsFragment);
    }

    @Override
    public void pushDealButtonClicked() {
        PushDeal detailsFragment = new PushDeal();
        detailsFragment.setFragmentCommunicatorListener(this);
        detailsFragment.setParentActivity(this);
        replaceWithFragment(detailsFragment);
    }

    private void replaceWithFragment(Fragment fragment) {
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.replace(R.id.fragment_manager, fragment);
        fragmentTransaction.commit();
    }

}