package com.example.spaces.spaces;

import java.util.ArrayList;
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
    public void valid_averages(StudyLocation sl) {
        double[] averages = new double[] {
                sl.getBusinessAvg(), sl.getComfortAvg(),
                sl.getOutletAvg(), sl.getOverallReviewAvg(), sl.getQuietnessAvg(),
                sl.getSeatingAvg(), sl.getWhiteboardAvg()
        };
        for (double avg : averages) {
            assertTrue("averages should fall between 0 and 5", avg > 0 && avg < 5);
        }
    }

    @Test
    public void valid_friend_list(User u) {
        for (String friend : u.getFriendList()) {
            assertFalse("user should not be on their own friend list", friend.equals(u.getUserID()));
        }
    }
}