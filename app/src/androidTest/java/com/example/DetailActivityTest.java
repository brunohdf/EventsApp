package com.example;


import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.example.eventsapp.R;
import com.example.model.EventModel;
import com.example.model.ScheduleModel;
import com.example.ui.DetailActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.clearText;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.doesNotExist;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isEnabled;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.not;

@RunWith(AndroidJUnit4.class)
public class DetailActivityTest {

    @Rule
    public ActivityTestRule<DetailActivity> mActivityRule = new ActivityTestRule(DetailActivity.class, false, false);

    @Test
    public void shouldRegisterAnEvent() {

        EventModel event = getSampleEventDay();

        Intent intent = new Intent();
        intent.putExtra(DetailActivity.PARAM_EVENT, event);

        mActivityRule.launchActivity(intent);

        onView(withText("1 inscrito")).check(matches(isDisplayed()));

        onView(withId(R.id.button_register)).check(matches(isEnabled())).perform(scrollTo(), click());
        onView(withId(R.id.edittext_fullname)).perform(typeText("Jose Silva"));
        onView(withId(R.id.edittext_email)).perform(typeText("jose@mail.com"));
        onView(withText(R.string.ok)).perform(click());

        onView(withId(R.id.button_register)).check(matches(not(isEnabled())));

        onView(withText("2 inscritos")).perform(scrollTo()).check(matches(isDisplayed()));
    }

    @Test
    public void shouldValidadeEmailAddress() {

        EventModel event = getSampleEventDay();

        Intent intent = new Intent();
        intent.putExtra(DetailActivity.PARAM_EVENT, event);

        mActivityRule.launchActivity(intent);
        onView(withId(R.id.button_register)).check(matches(isEnabled())).perform(scrollTo(), click());
        onView(withId(R.id.edittext_fullname)).perform(typeText("Jose Silva"));
        onView(withId(R.id.edittext_email)).perform(typeText("um email invalido"));
        onView(withText(R.string.ok)).perform(click());

        onView(withText("Informe um e-mail válido")).check(matches(isDisplayed()));

        onView(withId(R.id.edittext_email)).perform(clearText(), typeText("jose@gmail.com"));
        onView(withText(R.string.ok)).perform(click());

        onView(withText("Informe um e-mail válido")).check(doesNotExist());
    }

    @Test
    public void shouldDisplayEventData() {

        EventModel event = getSampleEventDay();

        Intent intent = new Intent();
        intent.putExtra(DetailActivity.PARAM_EVENT, event);

        mActivityRule.launchActivity(intent);

        onView(withText("Droid Talks S02E01")).check(matches(isDisplayed())).perform(scrollTo());
        // ..
        onView(
            allOf(
                withId(R.id.linearlayout_schedule_container), hasDescendant(withText("Introdução a Testes de UI com Espresso"))
            )
        ).check(matches(isDisplayed()));
    }

    @NonNull
    private EventModel getSampleEventDay() {
        EventModel event = new EventModel();
        event.setId(1);
        event.setDate("13/01/2017");
        event.setTitle("Droid Talks S02E01");
        event.setTime("19:00");
        event.setLocation("Ci&T");
        event.setEnrolledUsers(1);
        event.setSchedule(getSampleScheduleList());
        event.setDescription("Olá Droiders! Para iniciar 2017, teremos nosso Droid Talks S02E01");
        return event;
    }

    @NonNull
    private List getSampleScheduleList() {
        List schedule = new ArrayList();
        schedule.add(new ScheduleModel("Introdução a Testes de UI com Espresso", "Bruno"));
        schedule.add(new ScheduleModel("Introdução ao Android Things", "Vilmar"));
        return schedule;
    }

    @Test
    public void shouldNoDisplayEnrrolments() {
        EventModel e = getSampleEventDay();
        e.setEnrolledUsers(0);
        Intent intent = new Intent();
        intent.putExtra(DetailActivity.PARAM_EVENT, e);
        mActivityRule.launchActivity(intent);
        onView(withId(R.id.textview_enrolled_users)).check(matches(not(isDisplayed())));
    }

    @Test
    public void shouldDisplayOneEnrrolment() {
        EventModel e = getSampleEventDay();
        e.setEnrolledUsers(1);
        Intent intent = new Intent();
        intent.putExtra(DetailActivity.PARAM_EVENT, e);
        mActivityRule.launchActivity(intent);
        onView(withText("1 inscrito")).check(matches(isDisplayed()));
    }

    @Test
    public void shouldDisplaySomeEnrolments() {
        EventModel e = getSampleEventDay();
        e.setEnrolledUsers(2);
        Intent intent = new Intent();
        intent.putExtra(DetailActivity.PARAM_EVENT, e);
        mActivityRule.launchActivity(intent);
        onView(withText("2 inscritos")).check(matches(isDisplayed()));
    }


}
