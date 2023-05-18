package com.example.dealrunner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import utilities.FirebaseHandlers;

public class Login extends Fragment implements View.OnFocusChangeListener {
    private View view;
    private FragmentCommunicatorListener callbackFragment;

    private Activity mParentActivity;
    private EditText mEmail, mPassword;
    private Button mLoginBtn, mSignUpBtn, mForgetPass;
    private FirebaseAuth fAuth;

    public Login() {

    }

    public void setParentActivity(Activity parentActivity) {
        mParentActivity = parentActivity;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.login, container, false);

        mEmail = view.findViewById(R.id.user_login_email);
        mPassword = view.findViewById(R.id.password_edit_text);
        mLoginBtn = view.findViewById(R.id.log_in_button);
        mSignUpBtn = view.findViewById(R.id.sign_up_button);
        mForgetPass = view.findViewById(R.id.forgot_password_button);
        fAuth = FirebaseHandlers.getOrCreateFirebaseAuth();

        mLoginBtn.setOnClickListener(new View.OnClickListener() { //we use this method from Button class on object mLoginBtn to detect our login button click and execute a logic that we implement below
            @Override
            public void onClick(View view) {
                // Code here executes on main thread after user presses button

                String email = mEmail.getText().toString().trim();
                String password = mPassword.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    mEmail.setError("Email is Required");
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    mPassword.setError("Password is required");
                    return;
                }

                //more if conditions - Regex Email for format check, Regex password for pass requirments

                //aunthetication for fBase

                fAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            startActivity(new Intent(mParentActivity, BarNavigationBottom.class));
                            //start next activity here - startActivity()
                        } else {
                            Toast.makeText(mParentActivity, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });


            }
        });

        mForgetPass.setOnClickListener(new View.OnClickListener() {

            @SuppressLint("ResourceType")
            @Override
            public void onClick(View view) {

                //Generate an AlertDialog with an EditText field to allow user to input email

                AlertDialog.Builder passResetDialog = new AlertDialog.Builder(view.getContext());
                EditText resetMail = new EditText(view.getContext());
                resetMail.setId(5546); //@suppressLint lets you assign this number - no idea why this works


                //set title for this Alert Dialog
                passResetDialog.setTitle(R.string.pass_res_dialog);
                passResetDialog.setMessage(R.string.enter_res_email);

                passResetDialog.setView(resetMail); //Sets a custom view to be the contents of the alert dialog.

                //need to give the user option to close the Alert Dialog and go back to login page - 2 buttons must be added

                passResetDialog.setPositiveButton("Confirm", new DialogInterface.OnClickListener() { // the yes button
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        if (resetMail.getText().toString().trim().length() >0){ //if field is empty dismiss the dialog
                            //extract email and send reset link
                            String mail = resetMail.getText().toString();
                            fAuth.sendPasswordResetEmail(mail).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Toast.makeText(mParentActivity,"Reset link sent", Toast.LENGTH_SHORT).show();
                                }
                                }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(mParentActivity,"Invalid Email", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }
                });

                passResetDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() { //clicking no closes the dialog box
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //return to login screen, close dialog box
                    }
                });

                passResetDialog.create().show(); //create and show the dialog

            }
        });




        mSignUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // communicating to activity_main that register button is clicked through CallBackFragment interface
                if (callbackFragment != null) {
                    callbackFragment.signupButtonClicked();
                }

            }
        });

        return view;
    }

    // making sure that interface is storing the value so that it isn't null when called in activity_main
    public void setCallbackFragment(FragmentCommunicatorListener callbackFragment) {
        this.callbackFragment = callbackFragment;
    }


    @Override
    public void onFocusChange(View view, boolean b) {

    }
}