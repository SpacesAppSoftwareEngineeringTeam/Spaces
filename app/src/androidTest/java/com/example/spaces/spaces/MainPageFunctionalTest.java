package com.example.spaces.spaces;

import android.app.Activity;
import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.NoMatchingViewException;
import android.support.test.espresso.ViewAssertion;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.swipeUp;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isRoot;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class MainPageFunctionalTest {

    @Rule
    public ActivityTestRule<MainActivity> testActivityRule = new ActivityTestRule<>(MainActivity.class);

    private MainActivity testActivity = null;
    private DatabaseReference mDatabase;
    private StorageReference mStorageRef;
    private DatabaseReference locationRef;
    private String locationName = "A";

    @Before
    public void setActivity() {
        testActivity = testActivityRule.getActivity();
        mStorageRef = FirebaseStorage.getInstance().getReference();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        locationRef = mDatabase.child("locations").child(locationName);
    }

    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();
        assertEquals("com.example.spaces.spaces", appContext.getPackageName());
    }

    @Test
    public void openPage() {
        // open space page for the first space in the list
        onView(withId(R.id.spaceList)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        // check displayed values match those stored in the database for the space
        onView(ViewMatchers.withId(R.id.spaceName)).check(matches(withText(locationName)));
        onView(ViewMatchers.withId(R.id.overallValue)).check(matches(withText("0.5")));
        onView(ViewMatchers.withId(R.id.comfortValue)).check(matches(withText("0.5")));
        onView(ViewMatchers.withId(R.id.busynessValue)).check(matches(withText("0.5")));
        onView(ViewMatchers.withId(R.id.quietnessValue)).check(matches(withText("0.5")));
    }

    @Test
    public void openGalleryPage() {
        // open space page for the first space in the list
        onView(withId(R.id.spaceList)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        // open the gallery page
        onView(withId(R.id.ratingsCard)).perform(swipeUp());
        onView(withId(R.id.seeMorePhotos)).perform(click());
    }

    @Test
    public void openReviewPage() {
        // open space page for the first space in the list
        onView(withId(R.id.spaceList)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        // open the review page
        onView(withId(R.id.fab)).perform(click());
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
