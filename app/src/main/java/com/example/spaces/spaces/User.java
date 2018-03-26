package com.example.spaces.spaces;

import java.util.ArrayList;

/**
 * Created by Steven on 3/25/2018.
 */

class User {
    String getUserID() {
        return userID;
    }

    void setUserID(String userID) {
        this.userID = userID;
    }

    ArrayList<String> getFriendList() {
        return friendList;
    }

    void setFriendList(ArrayList<String> friendList) {
        this.friendList = friendList;
    }

    String getLocationID() {
        return locationID;
    }

    void setLocationID(String locationID) {
        this.locationID = locationID;
    }

    String userID = "";
    ArrayList<String> friendList = new ArrayList<String>();
    String locationID = "";
}
