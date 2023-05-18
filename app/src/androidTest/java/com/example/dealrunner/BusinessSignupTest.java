package com.example.dealrunner;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;

import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;

import android.os.Build;
import android.util.Log;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.matcher.RootMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.filters.LargeTest;
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.uiautomator.UiDevice;
import androidx.test.uiautomator.UiObject;
import androidx.test.uiautomator.UiObjectNotFoundException;
import androidx.test.uiautomator.UiSelector;

import com.google.firebase.auth.FirebaseAuth;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.regex.Pattern;

import utilities.FirebaseHandlers;

@RunWith(AndroidJUnit4ClassRunner.class)
@LargeTest
public class BusinessSignupTest {

    ActivityScenario<MainActivity> activityScenario;

    @Rule
    public ActivityScenarioRule<MainActivity> activityRule =
            new ActivityScenarioRule<>(MainActivity.class);

    @Before
    public void setUp(){
        FirebaseAuth.getInstance().signOut();
        activityScenario = ActivityScenario.launch(MainActivity.class);
    }


    @Test
    public void testSignUpBusiness(){
        String name = "Test Business";
        String address = "Fairway Park, King Street";
        String floor = "0";
        String province = "Ontario";
        String postalCode = "A1B2C3D4";
        String phone = "+11234567890";
        String email = "test.business@gmail.com";
        String password  = "TestPassword@123";
        String confirmPassword = password;

        onView(withId(R.id.sign_up_button)).perform(click());
        onView(withId(R.id.post_deals_button)).perform(click());
        onView(withId(R.id.business_signup_fragment)).check(matches(isDisplayed()));

        onView(withId(R.id.business_name)).perform(typeText(name));
        onView(withId(R.id.business_address)).perform(typeText(address));
        onView(withId(R.id.floor)).perform(typeText(floor)).perform(closeSoftKeyboard());
        onView(withId(R.id.province)).perform(typeText(province)).perform(closeSoftKeyboard());
        onView(withId(R.id.postal_code)).perform(typeText(postalCode)).perform(closeSoftKeyboard());
        onView(withId(R.id.business_phone_number)).perform(typeText(phone)).perform(closeSoftKeyboard());

        onView(withId(R.id.business_continue_button)).perform(click());
        onView(withId(R.id.business_signup_fragment_2)).check(matches(isDisplayed()));

        onView(withId(R.id.spinner_business_type)).perform(click());
        onView(withText("Cinema")).inRoot(RootMatchers.isPlatformPopup()).perform(click());

        onView(withId(R.id.businessEmail)).perform(typeText(email));
        onView(withId(R.id.business_password)).perform(typeText(password)).perform(closeSoftKeyboard());
        onView(withId(R.id.business_confirm_password)).perform(typeText(confirmPassword)).perform(closeSoftKeyboard());

        onView(withId(R.id.businessSignUpButton)).perform(click());
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        allowPermissionsIfNeeded();
        onView(withId(R.id.push_deals_fragment)).check(matches(isDisplayed()));
    }

    private void allowPermissionsIfNeeded()  {
        if (Build.VERSION.SDK_INT >= 23) {
            UiDevice mDevice = UiDevice.getInstance(getInstrumentation());
            UiObject allowPermissions = mDevice.findObject(new UiSelector().textStartsWith("While"));
            UiObject allowPermissionsCaps = mDevice.findObject(new UiSelector().textStartsWith("WHILE"));
            if (allowPermissions.exists()) {
                try {
                    allowPermissions.click();
                    return;
                } catch (UiObjectNotFoundException e) {
                    Log.e( "Business Signup Test","Trying All Caps permission");
                }
            }
            if (allowPermissionsCaps.exists()) {
                try {
                    allowPermissionsCaps.click();
                } catch (UiObjectNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @After
    public void deleteTestUser(){
        FirebaseHandlers.deleteCurrentUser();
    }
}
