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
public class FragmentNavigationTest {

    ActivityScenario<MainActivity> activityScenario;

    @Rule
    public ActivityScenarioRule<MainActivity> activityRule =
            new ActivityScenarioRule<>(MainActivity.class);

    @Before
    public void setUp() {
        FirebaseAuth.getInstance().signOut();
        activityScenario = ActivityScenario.launch(MainActivity.class);
    }

    @Test
    public void testLoginFragment() {
        onView(withId(R.id.login_fragment)).check(matches(isDisplayed()));
    }

    @Test
    public void testSignupButton() {
        onView(withId(R.id.login_fragment)).check(matches(isDisplayed()));
        onView(withId(R.id.sign_up_button)).perform(click());
        onView(withId(R.id.decision_fragment)).check(matches(isDisplayed()));
    }

    @Test
    public void testSignupDecisionFragment() {
        onView(withId(R.id.login_fragment)).check(matches(isDisplayed()));
        onView(withId(R.id.sign_up_button)).perform(click());
        onView(withId(R.id.decision_fragment)).check(matches(isDisplayed()));
        onView(withId(R.id.grab_deals_button)).perform(click());
        onView(withId(R.id.signup_consumer_parent)).check(matches(isDisplayed()));
        pressBack();
        onView(withId(R.id.decision_fragment)).check(matches(isDisplayed()));
        onView(withId(R.id.post_deals_button)).perform(click());
        onView(withId(R.id.business_signup_fragment)).check(matches(isDisplayed()));
        pressBack();
    }

    @Test
    public void testGoBackToLoginButton() {
        onView(withId(R.id.login_fragment)).check(matches(isDisplayed()));
        onView(withId(R.id.sign_up_button)).perform(click());
        onView(withId(R.id.decision_fragment)).check(matches(isDisplayed()));
        onView(withId(R.id.grab_deals_button)).perform(click());
        onView(withId(R.id.signup_consumer_parent)).check(matches(isDisplayed()));
        pressBack();
        onView(withId(R.id.decision_fragment)).check(matches(isDisplayed()));
        onView(withId(R.id.post_deals_button)).perform(click());
        onView(withId(R.id.business_signup_fragment)).check(matches(isDisplayed()));
        pressBack();
        onView(withId(R.id.log_in_button)).perform(click());
        onView(withId(R.id.login_fragment)).check(matches(isDisplayed()));
    }

}
