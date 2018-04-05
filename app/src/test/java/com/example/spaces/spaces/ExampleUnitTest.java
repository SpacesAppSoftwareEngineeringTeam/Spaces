package com.example.spaces.spaces;

import com.example.spaces.spaces.models.StudyLocation;
import com.example.spaces.spaces.models.User;
import com.example.spaces.spaces.models.Review;

import java.util.ArrayList;
import java.util.Date;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void valid_averages() {
        StudyLocation sl = new StudyLocation("testLocation");
        double[] averages = new double[] {
                sl.getBusinessAvg(), sl.getComfortAvg(),
                sl.getOutletAvg(), sl.getOverallReviewAvg(), sl.getQuietnessAvg(),
                sl.getSeatingAvg(), sl.getWhiteboardAvg()
        };
        for (double avg : averages) {
            assertTrue("averages should fall between 0 and 5", avg >= 0 && avg <= 5);
        }
    }

    @Test
    public void valid_friend_list() {
        User u = new User("testUser", "testUser@test.edu");
        for (String friend : u.getFriendList()) {
            assertFalse("user should not be on their own friend list", friend.equals(u.getUserID()));
        }
    }

    @Test
    public void add_review_averages_update(){
        Review r = new Review("testLocation", "testUserID", new Date().toString());
        r.setOverall(1);
        r.setBusiness(1);
        r.setQuietness(1);
        r.setComfort(1);
        r.setOutlets(1);
        r.setWhiteboards(1);
        r.setSeating(1);

        StudyLocation sl = new StudyLocation("testLocation");
        sl.addReview(r);
        double[] averages = new double[] {
                sl.getBusinessAvg(), sl.getComfortAvg(),
                sl.getOutletAvg(), sl.getOverallReviewAvg(), sl.getQuietnessAvg(),
                sl.getSeatingAvg(), sl.getWhiteboardAvg()
        };

        for (double avg : averages) {
            assertTrue("averages should be all be 1", avg == 1 );
        }
    }

    @Test
    public void add_multiple_reviews_averages_update(){
        Review r1 = new Review("testLocation", "testUserID", new Date().toString());
        r1.setOverall(1);
        r1.setBusiness(1);
        r1.setQuietness(1);
        r1.setComfort(1);
        r1.setOutlets(1);
        r1.setWhiteboards(1);
        r1.setSeating(1);

        Review r2 = new Review("testLocation", "testUserID", new Date().toString());
        r2.setOverall(2);
        r2.setBusiness(2);
        r2.setQuietness(2);
        r2.setComfort(2);
        r2.setOutlets(2);
        r2.setWhiteboards(2);
        r2.setSeating(2);

        StudyLocation sl = new StudyLocation("testLocation");
        sl.addReview(r1);
        sl.addReview(r2);

        double[] averages = new double[] {
                sl.getBusinessAvg(), sl.getComfortAvg(),
                sl.getOutletAvg(), sl.getOverallReviewAvg(), sl.getQuietnessAvg(),
                sl.getSeatingAvg(), sl.getWhiteboardAvg()
        };

        for (double avg : averages) {
            assertTrue("averages should be all be 1.5", avg == 1.5 );
        }
    }

}