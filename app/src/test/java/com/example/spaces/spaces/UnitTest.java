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
public class UnitTest {

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
        r.setBusyness(1);
        r.setQuietness(1);
        r.setComfort(1);
        r.setOutlets(true);
        r.setWhiteboards(true);
        r.setComputers(true);

        StudyLocation sl = new StudyLocation("testLocation");
        sl.addReview(r);
        double[] averages = new double[] {
                sl.getBusynessAvg(), sl.getComfortAvg(),
                sl.getOutletAvg(), sl.getOverallReviewAvg(), sl.getQuietnessAvg(),
                sl.getComputerAvg(), sl.getWhiteboardAvg()
        };

        for (double avg : averages) {
            assertTrue("averages should be all be 1", avg == 1 );
        }
    }

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

}