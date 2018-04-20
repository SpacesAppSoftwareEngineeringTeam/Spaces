package com.example.spaces.spaces.models;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.ArrayList;

/**
 * Created by Steven on 3/25/2018.
 */

@IgnoreExtraProperties
public class User {

    // Firebase requires model classes to have public fields
    public String userID = "";
    public ArrayList<String> friendList = new ArrayList<String>();
    public ArrayList<String> friendRequestList = new ArrayList<String>();
    public String locationID = "";
    public String username;
    public String email;

    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public User(String username, String email) {
        this.username = username;
        this.email = email;
    }

    @Exclude
    public String getUsername() {
        return username;
    }

    @Exclude
    public void setUsername(String username) {
        this.username = username;
    }

    @Exclude
    public String getEmail() {
        return email;
    }

    @Exclude
    public void setEmail(String email) {
        this.email = email;
    }

    @Exclude
    public String getUserID() {
        return userID;
    }

    @Exclude
    public void setUserID(String userID) {
        this.userID = userID;
    }

    @Exclude
    public ArrayList<String> getFriendList() {
        return friendList;
    }

    @Exclude
    public void setFriendList(ArrayList<String> friendList) {
        this.friendList = friendList;
    }

    @Exclude
    public void addFriend(String friend) {
        friendList.add(friend);
    }

    @Exclude
    public boolean removeFriend(String friend) {
        return friendList.remove(friend);
    }

    @Exclude
    public ArrayList<String> getFriendRequestList() {
        return friendRequestList;
    }

    @Exclude
    public void setFriendRequestList(ArrayList<String> friendRequestList) {
        this.friendRequestList = friendRequestList;
    }

    @Exclude
    public void addRequestFriend(String friendRequest) {
        friendRequestList.add(friendRequest);
    }

    @Exclude
    public boolean removeRequestFriend(String friendRequest) {
        return friendRequestList.remove(friendRequest);
    }

    @Exclude
    public String getLocationID() {
        return locationID;
    }

    @Exclude
    public void setLocationID(String locationID) {
        this.locationID = locationID;
    }


}
