package com.example.spaces.spaces.models;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.Date;


/**
 * Created by Steven on 3/25/2018.
 */

@IgnoreExtraProperties
class Review {

    // Firebase requires model classes to have public fields
    public String locationID = "";
    public String userID = "";
    public String submissionDate;
    public int overall = 0;
    public int quietness = 0;
    public int business = 0;
    public int comfort = 0;
    public int whiteboards = 0;
    public int outlets = 0;
    public int seating = 0;
    public String comment = "";
    public String accessibilityComment = "";

    public Review() {
        // Default constructor required for calls to DataSnapshot.getValue(Review.class)
    }

    public Review(String locationID, String userID, String submissionDate) {
        this.locationID = locationID;
        this.userID = userID;
        this.submissionDate = submissionDate;
    }

    @Exclude
    public String getLocationID() {
        return locationID;
    }

    @Exclude
    public void setLocationID(String locationID) {
        this.locationID = locationID;
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
    public String getSubmissionDate() {
        return submissionDate;
    }

    @Exclude
    public void setSubmissionDate(String submissionDate) {
        this.submissionDate = submissionDate;
    }

    @Exclude
    public int getOverall() {
        return overall;
    }

    @Exclude
    public void setOverall(int overall) {
        this.overall = overall;
    }

    @Exclude
    public int getQuietness() {
        return quietness;
    }

    @Exclude
    public void setQuietness(int quietness) {
        this.quietness = quietness;
    }

    @Exclude
    public int getBusiness() {
        return business;
    }

    @Exclude
    public void setBusiness(int business) {
        this.business = business;
    }

    @Exclude
    public int getComfort() {
        return comfort;
    }

    @Exclude
    public void setComfort(int comfort) {
        this.comfort = comfort;
    }

    @Exclude
    public int getWhiteboards() {
        return whiteboards;
    }

    @Exclude
    public void setWhiteboards(int whiteboards) {
        this.whiteboards = whiteboards;
    }

    @Exclude
    public int getOutlets() {
        return outlets;
    }

    @Exclude
    public void setOutlets(int outlets) {
        this.outlets = outlets;
    }

    @Exclude
    public int getSeating() {
        return seating;
    }

    @Exclude
    public void setSeating(int seating) {
        this.seating = seating;
    }

    @Exclude
    public String getComment() {
        return comment;
    }

    @Exclude
    public void setComment(String comment) {
        this.comment = comment;
    }

    @Exclude
    public String getAccessibilityComment() {
        return accessibilityComment;
    }

    @Exclude
    public void setAccessibilityComment(String accessibilityComment) {
        this.accessibilityComment = accessibilityComment;
    }
}
