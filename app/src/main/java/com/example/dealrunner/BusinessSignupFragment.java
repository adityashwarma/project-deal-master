package com.example.dealrunner;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputLayout;

import models.Business;
import models.User;
import utilities.FirebaseHandlers;
import utilities.Validator;

public class BusinessSignupFragment extends Fragment {

    private final int[] itemSelected = new int[1];
    Activity mParentActivity;
    EditText businessEmail;
    EditText businessPassword;
    EditText businessConfirmPassword;

    Button businessSignUpButton;

    TextInputLayout businessEmailTIL;
    TextInputLayout businessPasswordTIL;
    TextInputLayout businessConfirmPasswordTIL;

    AutoCompleteTextView spinnerBusinessTextview;
    CustomAdapter mCustomAdapter;

    public BusinessSignupFragment() {
    }

    public void setParentActivity(Activity activity){
        mParentActivity = activity;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View signupView = inflater.inflate(R.layout.signup_business_page2, container, false);
        businessEmail = signupView.findViewById(R.id.businessEmail);
        businessPassword = signupView.findViewById(R.id.business_password);
        businessConfirmPassword = signupView.findViewById(R.id.business_confirm_password);

        businessEmailTIL = signupView.findViewById(R.id.business_email_text_input_layout);
        businessPasswordTIL = signupView.findViewById(R.id.business_password_text_input_layout);
        businessConfirmPasswordTIL = signupView.findViewById(R.id.business_confirm_password_text_input_layout);

        businessSignUpButton = signupView.findViewById(R.id.businessSignUpButton);
        spinnerBusinessTextview = signupView.findViewById(R.id.spinner_business_textview);
        businessSignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateAndSignUp();
            }
        });
         mCustomAdapter = new CustomAdapter(mParentActivity);
        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(mParentActivity,
                android.R.layout.simple_spinner_item,getResources().getStringArray(R.array.business_types));


        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerBusinessTextview.setAdapter(mCustomAdapter);
        spinnerBusinessTextview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                itemSelected[0] = i;
            }
        });

        return signupView;
    }

    private void validateAndSignUp() {
        String email = businessEmail.getText().toString();
        String password = businessPassword.getText().toString();
        String confirmPassword = businessConfirmPassword.getText().toString();

        businessEmailTIL.setError(null);
        businessPasswordTIL.setError(null);
        businessConfirmPasswordTIL.setError(null);


        if (!Validator.validateEmail(email)) {
            businessEmailTIL.setError(mParentActivity.getApplicationContext().getString(R.string.error_email));
            return;
        }
        if (!Validator.validatePassword(password)) {
            businessPasswordTIL.setError(mParentActivity.getApplicationContext().getString(R.string.error_password));
            return;
        }

        if (!Validator.validateConfirmPassword(password, confirmPassword)) {
            businessConfirmPasswordTIL.setError(mParentActivity.getApplicationContext().getString(R.string.error_confirm_password));
            return;
        }
        Bundle bundle = getArguments();


        String businessType = itemSelected[0]  == 0? "None" : mCustomAdapter.getItemValue(itemSelected[0]);
        Business business = new Business(bundle.getString("name"), email, bundle.getString("address"), bundle.getString("phone"), businessType);
        signUpUserOnFirebase(business, password);
    }
    private void signUpUserOnFirebase(User user, String password) {
        FirebaseHandlers.signupUser(mParentActivity, user, password, new FirebaseHandlers.SignUpListener() {
            @Override
            public void onSignupComplete(boolean success, String message) {
                if(success) {
                    startActivity(new Intent(mParentActivity, BarNavigationBottom.class));
                } else {
                    Toast.makeText(mParentActivity, message, Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
