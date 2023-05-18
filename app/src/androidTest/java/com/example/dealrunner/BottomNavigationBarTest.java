package com.example.dealrunner;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.pressBack;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.doesNotExist;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.RootMatchers.withDecorView;
import static androidx.test.espresso.matcher.ViewMatchers.isClickable;
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
public class BottomNavigationBarTest {

    ActivityScenario<BarNavigationBottom> activityScenario;

    @Rule
    public ActivityScenarioRule<BarNavigationBottom> activityRule =
            new ActivityScenarioRule<>(BarNavigationBottom.class);

    @Before
    public void setUp() {
        FirebaseAuth.getInstance().signOut();
        activityScenario = ActivityScenario.launch(BarNavigationBottom.class); }

    @Test
    public void pressHomeToRefresh(){
        onView(withId(R.id.deals_home_button)).perform(click());
        onView(withId(R.id.view_home_page)).check(matches(isDisplayed()));
        onView(withId(R.id.deals_home_button)).perform(click());
        onView(withId(R.id.view_home_page)).check(matches(isDisplayed()));
    }

    @Test
    public void switchToSettingsFromHome(){
        onView(withId(R.id.deals_home_button)).perform(click());
        onView(withId(R.id.deals_settings_button)).perform(click());
        onView(withId(R.id.settings_fragment)).check(matches(isDisplayed()));
    }

    @Test
    public void switchToHomeFromSettings(){
        onView(withId(R.id.deals_settings_button)).perform(click());
        onView(withId(R.id.deals_home_button)).perform(click());
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        onView(withId(R.id.view_home_page)).check(matches(isDisplayed()));
    }

    @Test
    public void pressSettingsToRefresh(){
        onView(withId(R.id.deals_settings_button)).perform(click());
        onView(withId(R.id.settings_fragment)).check(matches(isDisplayed()));
        onView(withId(R.id.deals_settings_button)).perform(click());
        onView(withId(R.id.settings_fragment)).check(matches(isDisplayed()));
    }

    @Test
    public void testLogout(){
        onView(withId(R.id.deals_settings_button)).perform(click());
        onView(withId(R.id.log_out_button)).perform(click());
        onView(withId(R.id.login_fragment)).check(matches(isDisplayed()));
    }

    @Test
    public void testLocationPreferences(){
        onView(withId(R.id.deals_settings_button)).perform(click());
        onView(withId(R.id.location_preferences)).check(matches(isClickable()));
        onView(withId(R.id.location_preferences)).perform(click());
    }
}