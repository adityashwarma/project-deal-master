package com.example.dealrunner;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.pressBack;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.RootMatchers.withDecorView;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

import android.os.Build;

import androidx.lifecycle.Lifecycle;
import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
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

import utilities.FirebaseHandlers;

@RunWith(AndroidJUnit4ClassRunner.class)
@LargeTest
public class SignupConsumerTest {

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
    public void testSignUpCustomer(){
        String firstName = "Deal";
        String lastName = "Runner";
        String email = "thedealrunnerw22@gmail.com";
        String password  = "TestPassword@123";
        String confirmPassword = password;

        onView(withId(R.id.sign_up_button)).perform(click());
        onView(withId(R.id.grab_deals_button)).perform(click());

        onView(withId(R.id.consumerFirstName)).perform(typeText(firstName));
        onView(withId(R.id.consumerLastName)).perform(typeText(lastName));
        onView(withId(R.id.consumerEmail)).perform(typeText(email));
        onView(withId(R.id.consumerPassword)).perform(typeText(password)).perform(closeSoftKeyboard());
        onView(withId(R.id.consumerConfirmPassword)).perform(typeText(confirmPassword)).perform(closeSoftKeyboard());


        onView(withId(R.id.consumerSignUpButton)).perform(click());
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        allowPermissionsIfNeeded();
        onView(withId(R.id.bottom_navigation_bar)).check(matches(isDisplayed()));
    }

    private void allowPermissionsIfNeeded()  {
        if (Build.VERSION.SDK_INT >= 23) {
            UiDevice mDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
            UiObject allowPermissions = mDevice.findObject(new UiSelector().text("While using the app"));
            if (allowPermissions.exists()) {
                try {
                    allowPermissions.click();
                } catch (UiObjectNotFoundException e) {
                    //Timber.e(e, "There is no permissions dialog to interact with ");
                }
            }
        }
    }
    @Test
    public void testAlreadySignedUp(){
        String firstName = "Deal";
        String lastName = "Runner";
        String email = "thedealrunnerw22@gmail.com";
        String password  = "TestPassword@123";
        String confirmPassword = password;

        onView(withId(R.id.sign_up_button)).perform(click());
        onView(withId(R.id.grab_deals_button)).perform(click());

        onView(withId(R.id.consumerFirstName)).perform(typeText(firstName));
        onView(withId(R.id.consumerLastName)).perform(typeText(lastName));
        onView(withId(R.id.consumerEmail)).perform(typeText(email)).perform(closeSoftKeyboard());
        onView(withId(R.id.consumerPassword)).perform(typeText(password)).perform(closeSoftKeyboard());
        onView(withId(R.id.consumerConfirmPassword)).perform(typeText(confirmPassword)).perform(closeSoftKeyboard());

        onView(withId(R.id.consumerSignUpButton)).perform(click());
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        allowPermissionsIfNeeded();
        onView(withId(R.id.bottom_navigation_bar)).check(matches(isDisplayed()));
        pressBack();
        onView(withId(R.id.signup_consumer_parent)).check(matches(isDisplayed()));
        onView(withId(R.id.consumerSignUpButton)).perform(click());
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        onView(withId(R.id.signup_consumer_parent)).check(matches(isDisplayed()));
    }

    @After
    public void deleteTestUser(){
        FirebaseHandlers.deleteCurrentUser();
    }
}