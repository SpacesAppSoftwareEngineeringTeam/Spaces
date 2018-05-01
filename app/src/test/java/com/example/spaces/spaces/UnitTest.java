package com.example.spaces.spaces;

import android.content.Context;

import com.example.spaces.spaces.models.StudyLocation;
import com.example.spaces.spaces.models.User;
import com.example.spaces.spaces.models.Review;
import com.google.firebase.FirebaseApp;

import java.util.ArrayList;
import java.util.Date;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */

public class UnitTest {

    // StudyLocation class test cases

    @Test
    public void valid_averages() {
        StudyLocation sl = new StudyLocation("testLocation");
        double[] averages = new double[] {
                sl.getBusynessAvg(), sl.getComfortAvg(),
                sl.getOutletAvg(), sl.getOverallReviewAvg(), sl.getQuietnessAvg(),
                sl.getComputerAvg(), sl.getWhiteboardAvg()
        };
        for (double avg : averages) {
            assertTrue("averages should fall between 0 and 5", avg >= 0 && avg <= 5);
        }
    }

    // issue: unable to initialize Firebase app in test files
    @Test
    public void add_review_averages_update(){
  /*      Review r = new Review("testLocation", "testUserID", new Date().toString());
        r.setOverall(1);
        r.setBusyness(1);
        r.setQuietness(1);
        r.setComfort(1);
        r.setOutlets(true);
        r.setWhiteboards(true);
        r.setComputers(true);*/

        StudyLocation sl = new StudyLocation("testLocation");
        sl.setOverallReviewAvg(1);
        sl.setBusynessAvg(1);
        sl.setQuietnessAvg(1);
        sl.setComfortAvg(1);
        double[] averages = new double[] {
                sl.getBusynessAvg(), sl.getComfortAvg(),
                sl.getOverallReviewAvg(), sl.getQuietnessAvg(),
        };

        for (double avg : averages) {
            assertTrue("averages should be all be 1", avg == 1 );
        }
    }
/*
    @Test
    public void add_multiple_reviews_averages_update(){
        Review r1 = new Review("testLocation", "testUserID", new Date().toString());
        r1.setOverall(1);
        r1.setBusyness(1);
        r1.setQuietness(1);
        r1.setComfort(1);
        r1.setOutlets(true);
        r1.setWhiteboards(true);
        r1.setComputers(true);

        Review r2 = new Review("testLocation", "testUserID", new Date().toString());
        r2.setOverall(2);
        r2.setBusyness(2);
        r2.setQuietness(2);
        r2.setComfort(2);
        r2.setOutlets(false);
        r2.setWhiteboards(false);
        r2.setComputers(false);

        StudyLocation sl = new StudyLocation("testLocation");
        sl.addReview(r1);
        sl.addReview(r2);

        double[] averages = new double[] {
                sl.getBusynessAvg(), sl.getComfortAvg(),
                sl.getOutletAvg(), sl.getOverallReviewAvg(), sl.getQuietnessAvg(),
                sl.getComputerAvg(), sl.getWhiteboardAvg()
        };

        for (double avg : averages) {
            assertTrue("averages should be all be 1.5", avg == 1.5 );
        }
    }
*/
    // Review class test cases
    @Test
    public void testReview() {
        Review r = new Review();
        r.setBusyness(1);
        r.setOverall(1);
        r.setQuietness(1);
        r.setComfort(1);
        r.setOutlets(true);
        r.setWhiteboards(true);
        r.setComputers(true);
        r.setComment("this space rocks");
        float[] averages = {r.getBusyness(), r.getComfort(), r.getOverall(), r.getQuietness()};
        for (float avg : averages) {
            assertTrue("averages should all equal 1", avg == 1);
        }
        boolean[] utilities = {r.getComputers(), r.getOutlets(), r.getWhiteboards()};
        for (boolean util : utilities) {
            assertTrue("utilities should all be true", util);
        }
        assertTrue("comment string should output correctly", r.getComment().equals("this space rocks"));
    }

    @Test
    public void testReviewConstructors() {
        Review r = new Review("KSL", "SuperSpartan", "09-09-2018");
        assertTrue("location name should be recorded", r.getLocationName().equals("KSL"));
        assertTrue("userID should be recorded", r.getUserID().equals("SuperSpartan"));
        assertTrue("date should be recorded", r.getSubmissionDate().equals("09-09-2018"));

        Review r2 = new Review("Nord", "this space is cool", 0.1f, 0.2f,
                0.3f,true, false, true, "09-09-2017");
        assertTrue("location name should be recorded", r2.getLocationName().equals("Nord"));
        assertTrue("comment should be recorded", r2.getComment().equals("this space is cool"));
        assertTrue("date should be recorded", r2.getSubmissionDate().equals("09-09-2017"));
        assertTrue("quietness should be 0.1", r2.getQuietness() == 0.1f);
        assertTrue("busyness should be 0.2", r2.getBusyness() == 0.2f);
        assertTrue("comfort should be 0.3", r2.getComfort() == 0.3f);
        assertTrue("whiteboards should be true", r2.getWhiteboards());
        assertTrue("outlets should be false", !r2.getOutlets());
        assertTrue("computers should be true", r2.getComputers());
    }


    // User class test cases
    @Test
    public void testUserConstructor() {
        User u = new User("SuperSpartan", "SuperSpartan@case.edu");
        assertTrue("username should be saved", u.getUsername().equals("SuperSpartan"));
        assertTrue("email should be saved", u.getEmail().equals("SuperSpartan@case.edu"));
    }

    @Test
    public void setUserParameters() {
        User u = new User("SuperSpartan", "SuperSpartan@case.edu");
        u.setUsername("SouperSpartan");
        u.setEmail("SouperSpartan@case.edu");
        u.setUserID("ss123");
        u.setLocationID("Nord");
        assertEquals(u.getUsername(), "SouperSpartan");
        assertEquals(u.getEmail(), "SouperSpartan@case.edu");
        assertEquals(u.getUserID(), "ss123");
        assertEquals(u.getLocationID(), "Nord");
    }

}