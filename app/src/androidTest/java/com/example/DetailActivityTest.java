package com.example;


import android.app.Activity;
import android.app.Instrumentation;
import android.content.Intent;
import android.support.test.espresso.intent.Intents;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import com.example.eventsapp.R;
import com.example.model.EventModel;
import com.example.ui.DetailActivity;
import com.example.ui.HomeActivity;
import com.example.ui.SplashActivity;

import org.hamcrest.Matcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.Intents.intending;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class DetailActivityTest {

    @Rule
    public ActivityTestRule<DetailActivity> mActivityRule = new ActivityTestRule(DetailActivity.class, false, false);

    @Test
    public void shouldDisplayInitialState() {

        EventModel event = new EventModel();
        Intent intent = new Intent();
        intent.putExtra(DetailActivity.PARAM_EVENT, event);

        mActivityRule.launchActivity(intent);

        onView(withText("MEETUP #01")).check(matches(isDisplayed()));

        onView(withId(R.id.button_register)).check(matches(isDisplayed())).perform(click());




    }

}
