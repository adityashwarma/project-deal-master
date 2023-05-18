package com.example.dealrunner;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputLayout;

import models.Consumer;
import models.User;
import utilities.FirebaseHandlers;
import utilities.Validator;

public class SignupConsumer extends Fragment implements View.OnFocusChangeListener {
    private View view;
    private final String TAG = "LoginAndSignup";
    private Activity mParentActivity;
    private FragmentCommunicatorListener communicatorListener;

    private EditText consumerFirstName;
    private TextInputLayout consumerFirstNameTIL;
    private EditText consumerLastName;
    private TextInputLayout consumerLastNameTIL;
    private EditText consumerEmail;
    private TextInputLayout consumerEmailTIL;
    private EditText consumerPassword;
    private TextInputLayout consumerPasswordTIL;
    private EditText consumerConfirmPassword;
    private TextInputLayout consumerConfirmPasswordTIL;
    private Button consumerSignupButton;
    private Button consumerLogInButton;

    public SignupConsumer() {

    }

    public void setParentActivity(Activity parentActivity) {
        mParentActivity = parentActivity;
    }
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,@Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.signup_consumer, container, false);

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


        consumerFirstName = (EditText) view.findViewById(R.id.consumerFirstName);
        consumerFirstNameTIL = (TextInputLayout) view.findViewById(R.id.consumer_first_name_text_input_layout);
        consumerLastName = (EditText) view.findViewById(R.id.consumerLastName);
        consumerLastNameTIL = (TextInputLayout) view.findViewById(R.id.consumer_last_name_text_input_layout);
        consumerEmail = (EditText) view.findViewById(R.id.consumerEmail);
        consumerEmailTIL = (TextInputLayout) view.findViewById(R.id.consumer_email_text_input_layout);
        consumerPassword = (EditText) view.findViewById(R.id.consumerPassword);
        consumerPasswordTIL = (TextInputLayout) view.findViewById(R.id.consumer_password_text_input_layout);
        consumerConfirmPassword = (EditText) view.findViewById(R.id.consumerConfirmPassword);
        consumerConfirmPasswordTIL = (TextInputLayout) view.findViewById(R.id.confirm_password_text_input_layout);
        consumerSignupButton = (Button) view.findViewById(R.id.consumerSignUpButton);
        consumerLogInButton = (Button) view.findViewById(R.id.consumerLogInButton);

        consumerFirstName.setOnFocusChangeListener(this);
        consumerLastName.setOnFocusChangeListener(this);
        consumerEmail.setOnFocusChangeListener(this);
        consumerPassword.setOnFocusChangeListener(this);
        consumerConfirmPassword.setOnFocusChangeListener(this);


        consumerLogInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(communicatorListener != null) {
                    communicatorListener.loginButtonClicked();
                }
            }
        });

        consumerSignupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateAndSignup();
            }
        });
        return view;
    }

    private void validateAndSignup() {
        String firstName = consumerFirstName.getText().toString();
        String lastName = consumerLastName.getText().toString();
        String email = consumerEmail.getText().toString();
        String password = consumerPassword.getText().toString();
        String confirmPassword = consumerConfirmPassword.getText().toString();

        consumerEmailTIL.setError(null);
        consumerFirstNameTIL.setError(null);
        consumerLastNameTIL.setError(null);
        consumerPasswordTIL.setError(null);
        consumerConfirmPasswordTIL.setError(null);

        if (!Validator.validateFirstName(firstName)) {
            consumerFirstNameTIL.setError(mParentActivity.getApplicationContext().getString(R.string.error_first_name));
            return;
        }

        if (!Validator.validateLastName(lastName)) {
            consumerLastNameTIL.setError(mParentActivity.getApplicationContext().getString(R.string.error_last_name));
            return;
        }

        if (!Validator.validateEmail(email)) {
            consumerEmailTIL.setError(mParentActivity.getApplicationContext().getString(R.string.error_email));
            return;
        }
        if (!Validator.validatePassword(password)) {
            consumerPasswordTIL.setError(mParentActivity.getApplicationContext().getString(R.string.error_password));
            return;
        }

        if (!Validator.validateConfirmPassword(password, confirmPassword)) {
            consumerConfirmPasswordTIL.setError(mParentActivity.getApplicationContext().getString(R.string.error_confirm_password));
            return;
        }
        Consumer consumer = new Consumer(firstName + " " + lastName, email);
        signUpUserOnFirebase(consumer, password);
    }

    public void setFragmentCommunicatorListener(FragmentCommunicatorListener communicatorListener) {
        this.communicatorListener = communicatorListener;
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

    @Override
    public void onFocusChange(View view, boolean hasFocus) {
        if(!hasFocus) {
            if(view.getId() == R.id.consumerEmail) consumerEmailTIL.setError(null);
            if(view.getId() == R.id.consumerFirstName) consumerFirstNameTIL.setError(null);
            if(view.getId() == R.id.consumerLastName) consumerLastNameTIL.setError(null);
            if(view.getId() == R.id.consumerPassword) consumerPasswordTIL.setError(null);
            if(view.getId() == R.id.consumerConfirmPassword) consumerConfirmPasswordTIL.setError(null);
        }
    }
}

