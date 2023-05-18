package com.example.dealrunner;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class Decision extends Fragment {
    private View view;
    private Button grabDealsButton;
    private Button postDealsButton;
    private Button goBackToLoginButton;

    private FragmentCommunicatorListener callbackFragment;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.decision, container, false);

        grabDealsButton = (Button) view.findViewById(R.id.grab_deals_button);
        postDealsButton = (Button) view.findViewById(R.id.post_deals_button);
        goBackToLoginButton = (Button) view.findViewById(R.id.log_in_button);

        grabDealsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(callbackFragment != null) {
                    callbackFragment.grabDealsButtonClicked();
                }
            }
        });

        postDealsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(callbackFragment != null) {
                    callbackFragment.postDealsButtonClicked();
                }

            }
        });

        goBackToLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        goBackToLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(callbackFragment != null) {
                    callbackFragment.loginButtonClicked();
                }
            }
        });

        return view;
    }

    public void setCallbackFragment(FragmentCommunicatorListener callbackFragment) {
        this.callbackFragment = callbackFragment;
    }
}