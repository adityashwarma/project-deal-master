package com.example.dealrunner;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


public class BusinessDetailsFragment extends Fragment {

    private Activity mParentActivity;
    private FragmentCommunicatorListener communicatorListener;

    private EditText businessName;
    private EditText businessAddress;
    private EditText businessFloor;
    private EditText businessProvince;
    private EditText businessPostalCode;
    private EditText businessPhone;

    private Button businessContinueButton;

    private TextView loginTextView;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View detailsView = inflater.inflate(R.layout.signup_business, container, false);
        businessName =  detailsView.findViewById(R.id.business_name);
        businessAddress =  detailsView.findViewById(R.id.business_address);
        businessFloor =  detailsView.findViewById(R.id.floor);
        businessProvince =  detailsView.findViewById(R.id.province);
        businessPostalCode =  detailsView.findViewById(R.id.postal_code);
        businessPhone =  detailsView.findViewById(R.id.business_phone_number);

        loginTextView = detailsView.findViewById(R.id.business_log_in_button);
        loginTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                communicatorListener.loginButtonClicked();
            }
        });

        businessContinueButton = detailsView.findViewById(R.id.business_continue_button);
        businessContinueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateAndContinue();
            }
        });
        return  detailsView;
    }

    private void validateAndContinue(){
        Bundle bundle = new Bundle();
        bundle.putString("name", businessName.getText().toString());
        bundle.putString("address", businessAddress.getText().toString());
        bundle.putString("floor", businessFloor.getText().toString());
        bundle.putString("province", businessProvince.getText().toString());
        bundle.putString("postalCode", businessPostalCode.getText().toString());
        bundle.putString("phone", businessPhone.getText().toString());
        communicatorListener.continueButtonClicked(bundle);
    }

    public void setFragmentCommunicatorListener(FragmentCommunicatorListener communicatorListener) {
        this.communicatorListener = communicatorListener;
    }

    public void setParentActivity(Activity activity){
        mParentActivity = activity;
    }
}
