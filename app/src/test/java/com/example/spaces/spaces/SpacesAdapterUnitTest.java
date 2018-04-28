package com.example.spaces.spaces;

import com.example.spaces.spaces.models.StudyLocation;

import org.junit.Test;
import static org.junit.Assert.*;
import java.util.ArrayList;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class SpacesAdapterUnitTest {

    private StudyLocation[] testLocations = {new StudyLocation("test1"), new StudyLocation("test2")};
    private SpacesAdapter testAdapter = new SpacesAdapter(testLocations);

    //private ArrayList<SpacesAdapter.ViewHolder> testHolders = testAdapter.getTestHolders();

    @Test
    public void checkCardColor() {
        for (int i = 0; i < testHolders.size(); i++) {
            if (i % 2 == 0) {
                assertTrue("odd cards should be dark gray", testHolders.get(i).mCardView.getCardBackgroundColor().equals(Color.parseColor("#cfd8dc")));
            }
            else {
                assertTrue("even cards should be light gray", testHolders.get(i).mCardView.getCardBackgroundColor().equals(Color.parseColor("#efebe9")));
            }
        }
    }

    @Test
    public void checkSpaceListLength() {
        assertTrue("fetching correct list length", testLocations.length == testAdapter.getItemCount());
    }

    @Test
    public void checkCardText() {
        for (int i = 0; i < testHolders.size(); i++) {
            assertTrue("displayed text matches space name", testLocations[i].getLocationName() == testHolders.get(i).SpaceName.getText().toString());
        }
    }

    @Test
    public void checkRatingsColor() {

    }

    @Test
    public void checkRatingsLength() {

    }

}
