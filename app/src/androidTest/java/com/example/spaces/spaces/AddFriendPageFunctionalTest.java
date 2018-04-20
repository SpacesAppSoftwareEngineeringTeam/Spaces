package com.example.spaces.spaces;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.assertEquals;

/**
 * Created by Steven on 4/12/2018.
 */
/*
@RunWith(AndroidJUnit4.class)
public class AddFriendPageFunctionalTest {

    @Rule
    public ActivityTestRule<AddFriendActivity> mActivityRule = new ActivityTestRule<>(AddFriendActivity.class);

    private AddFriendActivity mActivity = null;

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
    public void ensureNoInvalidEmail() {
        // type email text (empty string is an invalid email)
        onView(withId(R.id.addFriendEmailText)).perform(typeText(""), closeSoftKeyboard());
        // press submit button
        onView(withId(R.id.submitFriendRequest)).perform(click());
        // check that the space was not submitted
        onView(withId(R.id.submitFriendRequest)).check(matches(isDisplayed()));

        // test an email without @case.edu
        onView(withId(R.id.addFriendEmailText)).perform(typeText("testemail@gmail.com"), closeSoftKeyboard());
        // press submit button
        onView(withId(R.id.submitFriendRequest)).perform(click());
        // check that the space was not submitted
        onView(withId(R.id.submitFriendRequest)).check(matches(isDisplayed()));
    }
}
*/