package com.example;


import android.app.Activity;
import android.app.Instrumentation;
import android.content.Intent;
import android.support.test.espresso.intent.Intents;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import com.example.eventsapp.R;
import com.example.ui.HomeActivity;
import com.example.ui.SplashActivity;

import org.hamcrest.Matcher;
import org.junit.After;
import org.junit.Before;
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
public class SplashActivityTest {

    // activity a ser testada
    @Rule
    public ActivityTestRule<SplashActivity> mActivityRule = new ActivityTestRule<>(SplashActivity.class, false, true);

    @Before
    public void tearUp() {
    }

    @After
    public void tearDown(){
    }

    @Test
    public void shouldDisplayInitialState() {
        Log.w("TESTE", " shouldDisplayInitialState");
        // id unico, situacao ideal!
        onView(withId(R.id.textview_punch_line)).check(matches(isDisplayed()));
        onView(withId(R.id.button_signup)).check(matches(isDisplayed()));

        onView(withText(R.string.punch_line)).check(matches(isDisplayed()));
        onView(withText(R.string.sign_up)).check(matches(isDisplayed()));
        onView(withText(R.string.title)).check(matches(isDisplayed()));
        onView(withText(R.string.subtitle)).check(matches(isDisplayed()));

        // por boa pratica,e ate mesmo por semantica, nao utilizar onView para assercoes, preferor .check()
        onView(withId(R.id.textview_punch_line)).check(matches(withText(R.string.punch_line)));
    }

    @Test
    public void shouldStartHomeScreen() {
        Log.w("TESTE", " shouldGoHome");
        // inicia gravao intent
        Intents.init();
        Matcher<Intent> matcher = hasComponent(HomeActivity.class.getName());

        // simula o resultado da activity
        Instrumentation.ActivityResult result = new Instrumentation.ActivityResult(Activity.RESULT_OK, null);
        // retorna resultado, sem que a activity seja lançada
        intending(matcher).respondWith(result);

        onView(withId(R.id.button_signup)).perform(click());
        intended(matcher);

        // limpa os estados da intent
        Intents.release();
    }

}
