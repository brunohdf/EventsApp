package com.example;


import android.app.Activity;
import android.app.Instrumentation.ActivityResult;
import android.content.Intent;
import android.support.annotation.ColorInt;
import android.support.test.espresso.intent.Intents;
import android.support.test.espresso.matcher.BoundedMatcher;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.example.eventsapp.R;
import com.example.ui.DetailActivity;
import com.example.ui.HomeActivity;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static android.support.test.espresso.contrib.RecyclerViewActions.scrollToPosition;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.Intents.intending;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasExtraWithKey;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

@RunWith(AndroidJUnit4.class)
public class HomeActivityTest {

    @Rule
    public ActivityTestRule<HomeActivity> mActivityRule = new ActivityTestRule<>(HomeActivity.class, false, true);

    @Test
    public void shouldDisplayInitialState() {

        onView(
            allOf(
                isAssignableFrom(TextView.class),
                withParent(isAssignableFrom(Toolbar.class))
            )
        ).check(matches(withText(R.string.app_name)));

        onView(withId(R.id.recyclerview_events)).check(matches(hasDescendant(withText("Meetup #01"))));

        onView(isAssignableFrom(Toolbar.class))
            .check(matches(
                withToolBarTitle(
                    is(mActivityRule.getActivity().getString(R.string.app_name))
                    // mesma assercao acima por meio de String.equals
                    // mActivityRule.getActivity().getString(R.string.app_name)
                ))
            );

    }

    @Test
    public void shouldStartDetailScreen() {
        Intents.init();
        Matcher<Intent> matcher = allOf(
                hasComponent(DetailActivity.class.getName()),
                hasExtraWithKey(DetailActivity.PARAM_EVENT)
        );

        ActivityResult result = new ActivityResult(Activity.RESULT_OK, null);
        intending(matcher).respondWith(result);

        onView(withId(R.id.recyclerview_events)).perform(actionOnItemAtPosition(0, click()));

        intended(matcher);
        Intents.release();
    }

    @Test
    public void shouldDisplayEventData() {

        // assercoes com uso de RecyclerViiewActions
        int position = 0;
        onView(withId(R.id.recyclerview_events))
                .perform(scrollToPosition(position))
                .check(matches(atPosition(position, hasDescendant(withText("12/05/2016")))))
                .check(matches(atPosition(position, hasDescendant(withText("19:00")))))
                .check(matches(atPosition(position, hasDescendant(withText("CI&T")))))
                .check(matches(atPosition(position, hasDescendant(withText("Meetup #01")))));


        position = 7;
        onView(withId(R.id.recyclerview_events))
                .perform(scrollToPosition(position))
                .check(matches(atPosition(position, hasDescendant(withText("29/01/2017")))))
                .check(matches(atPosition(position, hasDescendant(withText("19:00")))))
                .check(matches(atPosition(position, hasDescendant(withText("CI&T")))))
                .check(matches(atPosition(position, hasDescendant(withText("Meetup #08")))));


        onView(allOf(
                withText("Meetup #08"),
                withTextColor(ContextCompat.getColor(mActivityRule.getActivity(), R.color.black)))
        ).check(matches(isDisplayed()));
    }

    // custom macther sem uso da lib hamcrest
    private static Matcher<View> withToolBarTitle(final String textMatcher) {
        return new BoundedMatcher<View, Toolbar>(Toolbar.class) {

            String toolbarTitle;
            @Override
            public void describeTo(Description description) {
                description.appendText("with toolbar title: ")
                    .appendText(toolbarTitle);

            }

            @Override
            protected boolean matchesSafely(Toolbar toolbar) {
                if (toolbarTitle == null) {
                    toolbarTitle = toolbar.getTitle().toString();
                }
                return textMatcher.equals(toolbarTitle);
            }
        };
    }

    // mesmo custom macher com uso da lib hamcrest
    private static Matcher<View> withToolBarTitle(final Matcher<String> textMatcher) {

        return new BoundedMatcher<View, Toolbar>(Toolbar.class) {

            @Override
            public void describeTo(Description description) {
                description.appendText("with toolbar title: ");
                textMatcher.describeTo(description);
            }

            @Override
            protected boolean matchesSafely(Toolbar toolbar) {
                return textMatcher.matches(toolbar.getTitle());
            }
        };
    }

    private static Matcher<View> atPosition(final int position, final Matcher<View> itemMatcher) {
        return new BoundedMatcher<View, RecyclerView>(RecyclerView.class) {
            @Override
            protected boolean matchesSafely(RecyclerView recyclerView) {
                RecyclerView.ViewHolder viewHolder = recyclerView.findViewHolderForAdapterPosition(position);

                return itemMatcher.matches(viewHolder.itemView);
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("has item at position " + position + ": ");
                itemMatcher.describeTo(description);
            }
        };
    }

    public static Matcher<View> withTextColor(@ColorInt final int expectedColor) {
        return new BoundedMatcher<View, TextView>(TextView.class) {
            int currentColor = 0;

            @Override
            public void describeTo(Description description) {
                description.appendText("expected TextColor: ")
                        .appendValue(Integer.toHexString(expectedColor));
                description.appendText(" current TextColor: ")
                        .appendValue(Integer.toHexString(currentColor));
            }

            @Override
            protected boolean matchesSafely(TextView item) {
                if (currentColor == 0)
                    currentColor = item.getCurrentTextColor();

                return currentColor == expectedColor;
            }
        };
    }
}