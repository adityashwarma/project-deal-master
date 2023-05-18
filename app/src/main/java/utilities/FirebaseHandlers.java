package utilities;

import static utilities.FirebaseKeys.FIREBASE_KEY_BUSINESS;
import static utilities.FirebaseKeys.FIREBASE_KEY_CONSUMER;
import static utilities.FirebaseKeys.FIREBASE_KEY_DEALS;
import static utilities.FirebaseKeys.FIREBASE_KEY_USERS;

import android.app.Activity;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import models.Business;
import models.Consumer;
import models.User;
import models.Deal;

public class FirebaseHandlers {

    static SignUpListener mSignUpListener;
    static PushDealListener mPushDealListener;

    static FirebaseAuth mAuth;
    static FirebaseDatabase database = FirebaseDatabase.getInstance();
    static DatabaseReference dbRef = database.getReference();

    static DatabaseReference dealsReference = database.getReference("deals");

    private final static String TAG = "FirebaseHandler";

    public static FirebaseAuth getOrCreateFirebaseAuth(){
        if (mAuth == null) {
            mAuth = FirebaseAuth.getInstance();
        }
        return mAuth;
    }
    public static void signupUser(Activity activity, User user, String password, SignUpListener signUpListener) {
        mSignUpListener = signUpListener;
        getOrCreateFirebaseAuth().createUserWithEmailAndPassword(user.getUserEmail(), password)
                .addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser fbUser = mAuth.getCurrentUser();
                            assert fbUser != null;
                            Log.d(TAG, "createUserWithEmail: User created with UUID: " + fbUser.getUid());
                            user.setId(fbUser.getUid());
                            createUserInDB(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            signUpListener.onSignupComplete(false, "Authentication failed." + task.getException().getMessage());
                        }
                    }
                });
    }

    private static void createUserInDB(User user) {
        dbRef.child(FIREBASE_KEY_USERS).child(user.getId()).setValue(user.getUserType().toString().toLowerCase());
        if (user.getUserType() == UserTypes.UserType.CONSUMER) {
            Consumer consumer = (Consumer) user;
            dbRef.child(FIREBASE_KEY_CONSUMER).child(consumer.getId()).setValue(consumer);
            mSignUpListener.onSignupComplete(true, "Customer created");
        } else {
            Business business = (Business) user;
            dbRef.child(FIREBASE_KEY_BUSINESS).child(business.getId()).setValue(business);
            mSignUpListener.onSignupComplete(true, "Business created");
        }
    }

    public static void pushDeal(Deal deal, PushDealListener pushDealListener) {
        mPushDealListener = pushDealListener;
        createDealInDB(deal);
    }
    private static void createDealInDB(Deal deal){
            dbRef.child(FIREBASE_KEY_DEALS).child(deal.getDealID()).setValue(deal);
            mPushDealListener.onPushDealComplete(true, "Deal created");
    }

    public static void deleteCurrentUser() {
        FirebaseUser user = getOrCreateFirebaseAuth().getCurrentUser();
        if(user != null) {
            deleteUserFromDB(user.getUid());
            user.delete()
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Log.d(TAG, "User account deleted.");
                            }
                        }
                    });
        }
    }

    private static void deleteUserFromDB(String uID){
        dbRef.child(FIREBASE_KEY_USERS).child(uID).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if(task.isSuccessful()){
                    String userType = String.valueOf(task.getResult().getValue());
                    dbRef.child(userType).child(uID).removeValue();
                }
            }
        });

        dbRef.child(FIREBASE_KEY_USERS).child(uID).removeValue();
    }

    public static void getConsumerDetails(ConsumerDetailsListener consumerDetailsListener) {
        FirebaseUser user = getOrCreateFirebaseAuth().getCurrentUser();
        if(user != null) {
            dbRef.child(FIREBASE_KEY_CONSUMER).child(user.getUid()).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    consumerDetailsListener.onConsumerDetailFetched(snapshot.getValue(Consumer.class));
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
    }

    public static void getUserType(UserTypeListener usertypeListener) {
        FirebaseUser user = getOrCreateFirebaseAuth().getCurrentUser();
        if(user != null) {
            dbRef.child(FIREBASE_KEY_USERS).child(user.getUid()).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    usertypeListener.usertypeFetched(snapshot.getValue(String.class));
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
    }

    public interface SignUpListener {
        public void onSignupComplete(boolean success, String message);
    }
    public interface PushDealListener {
        public void onPushDealComplete(boolean success, String message);
    }
    public static DatabaseReference getDealsReference() {
        return dealsReference;
    }

    public interface ConsumerDetailsListener {
        public void onConsumerDetailFetched(Consumer consumer);
    }

    public interface UserTypeListener {
        public void usertypeFetched(String usertype);
    }
}

