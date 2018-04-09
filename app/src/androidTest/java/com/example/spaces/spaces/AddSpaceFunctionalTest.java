package com.example.spaces.spaces;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.NoMatchingViewException;
import android.support.test.espresso.ViewAssertion;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.RootMatchers.withDecorView;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isRoot;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class AddSpaceFunctionalTest {

    @Rule
    public ActivityTestRule<AddSpaceActivity> mActivityRule = new ActivityTestRule<>(AddSpaceActivity.class);

    private AddSpaceActivity mActivity = null;

    @Before
    public void setActivity() {
        mActivity = mActivityRule.getActivity();
    }

    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();
        assertEquals("com.example.spaces.spaces", appContext.getPackageName());
    }

    @Test
    public void ensureNoInvalidSpaceNames() {
        // type name text (empty string is an invalid name)
        onView(withId(R.id.add_space_name)).perform(typeText(""), closeSoftKeyboard());
        // press submit button
        onView(withId(R.id.add_space_submit)).perform(click());
        // check that the space was not submitted
        onView(withId(R.id.add_space_submit)).check(matches(isDisplayed()));

        // check for names that are too long
        onView(withId(R.id.add_space_name))
                .perform(typeText("this is a name with more than fifty characters-- it shouldn't be allowed"), closeSoftKeyboard());
        onView(withId(R.id.add_space_submit)).perform(click());
        onView(withId(R.id.add_space_submit)).check(matches(isDisplayed()));
    }


    private Activity getCurrentActivity() {
        final Activity[] activity = new Activity[1];
        onView(isRoot()).check(new ViewAssertion() {
            @Override
            public void check(View view, NoMatchingViewException noViewFoundException) {
                activity[0] = (Activity) view.getContext();
            }
        });
        return activity[0];
    }

}
