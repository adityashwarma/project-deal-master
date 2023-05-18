package com.example.dealrunner;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.pressBack;
import static androidx.test.espresso.action.ViewActions.pressKey;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

import android.os.Build;
import android.util.Log;

import androidx.test.core.app.ActivityScenario;
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

//import utilities.FirebaseHandlers;

@RunWith(AndroidJUnit4ClassRunner.class)
@LargeTest

public class LoginTest {
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
    public void testCorrectLogin(){

        String email = "dealrunnerw22@gmail.com";
        String password  = "TheDealTeam@w22";

        onView(withId(R.id.user_login_email)).perform(typeText(email));
        onView(withId(R.id.password_edit_text)).perform(typeText(password)).perform(closeSoftKeyboard());


        onView(withId(R.id.log_in_button)).perform(click());
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        allowPermissionsIfNeeded();
        onView(withId(R.id.bottom_navigation_bar)).check(matches(isDisplayed()));

        //test if toast is displayed
        //onView(withText(R.string.welcome)).inRoot(new ToastMatcher()).check(matches(isDisplayed()));
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

    @Test
    public void testWrongEmail(){

        String email = "wrongemail@dccomics.com";
        String password  = "iamsuperman";

        onView(withId(R.id.user_login_email)).perform(typeText(email));
        onView(withId(R.id.password_edit_text)).perform(typeText(password)).perform(closeSoftKeyboard());


        onView(withId(R.id.log_in_button)).perform(click());
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void testNoEmail(){
        //need to create a custom matcher to check if no email entered error is caught correctly
        String email = "";
        //String password  = "iamsuperman";

        onView(withId(R.id.user_login_email)).perform(typeText(email));
        //onView(withId(R.id.password_edit_text)).perform(typeText(password)).perform(closeSoftKeyboard());


        onView(withId(R.id.log_in_button)).perform(click());
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void testNoPass(){
        //need to create a custom matcher to check if no password entered error is caught correctly
        String email = "wrongemail@dccomics.com";
        String password  = "";

        onView(withId(R.id.user_login_email)).perform(typeText(email));
        onView(withId(R.id.password_edit_text)).perform(typeText(password)).perform(closeSoftKeyboard());


        onView(withId(R.id.log_in_button)).perform(click());
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void testWrongPass(){

        String email = "superman@dccomics.com";
        String password  = "wrongpassword";

        onView(withId(R.id.user_login_email)).perform(typeText(email));
        onView(withId(R.id.password_edit_text)).perform(typeText(password)).perform(closeSoftKeyboard());


        onView(withId(R.id.log_in_button)).perform(click());
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void testForgetPass(){
        String email = "dealrunnerw22@gmail.com";

        onView(withId(R.id.forgot_password_button)).perform(click());
        onView(withId(5546)).perform(typeText(email));
        onView(withText("Confirm")).perform(click());
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //checks if we return to login page
        onView(withId(R.id.login_fragment)).check(matches(isDisplayed()));
    }

    @Test
    public void testForgetPassWrong(){
        String email = "doesnotexist@gmail.com";

        onView(withId(R.id.forgot_password_button)).perform(click());
        onView(withId(5546)).perform(typeText(email));
        onView(withText("Confirm")).perform(click());
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        onView(withId(R.id.login_fragment)).check(matches(isDisplayed()));
    }

    @After
    public void tearDown() throws Exception{

    }
}
