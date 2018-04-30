package com.example.spaces.spaces;

import com.example.spaces.spaces.models.StudyLocation;
import com.google.firebase.FirebaseApp;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.*;
import java.util.ArrayList;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.content.Context;

public class SpacesAdapterUnitTest {

    private StudyLocation[] testLocations = {new StudyLocation("test1"), new StudyLocation("test2")};
    private SpacesAdapter testAdapter;
    private SpacesAdapter.ViewHolder[] testHolders;

    @Before
    public void setup() {
        testAdapter = new SpacesAdapter(testLocations);
        testHolders = testAdapter.getHolders();
    }

    @Test
    public void checkCardColor() {
        for (int i = 0; i < testHolders.length; i++) {
            if (i % 2 == 0) {
                assertTrue("odd cards should be dark gray",
                        testHolders[i].mCardView.getCardBackgroundColor().getDefaultColor() == Color.parseColor("#cfd8dc"));
            }
            else {
                assertTrue("even cards should be light gray",
                        testHolders[i].mCardView.getCardBackgroundColor().getDefaultColor() == Color.parseColor("#efebe9"));
            }
        }
    }

    @Test
    public void checkSpaceListLength() {
        assertTrue("fetching correct list length", testLocations.length == testAdapter.getItemCount());
    }

    @Test
    public void checkCardText() {
        for (int i = 0; i < testHolders.length; i++) {
            assertTrue("displayed text matches space name",
                    testLocations[i].getLocationName().equals(testHolders[i].spaceName.getText().toString()));
        }
    }

    @Test
    public void checkRatingsColor() {

    }

    @Test
    public void checkRatingsLength() {

    }

}
