package com.example.spaces.spaces.models;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.io.Serializable;
import java.text.DecimalFormat;


/**
 * Created by Steven on 3/25/2018.
 */

@IgnoreExtraProperties
public class Review implements Serializable {

    // Firebase requires model classes to have public fields
    public String locationName = "";
    public String comment = "";
    public String userID = "";
    public String submissionDate = "";
    public float overall = 0;
    public float quietness = 0;
    public float busyness = 0;
    public float comfort = 0;
    public boolean whiteboards;
    public boolean outlets;
    public boolean computers;
    public String accessibilityComment = "";

    public Review() {
        // Default constructor required for calls to DataSnapshot.getValue(Review.class)
    }

    public Review(String locationName, String userID, String submissionDate) {
        this.locationName = locationName;
        this.userID = userID;
        this.submissionDate = submissionDate;
    }

    public Review(String locationName, String comment, float quietness, float busyness,
                  float comfort, boolean whiteboards, boolean outlets, boolean computers, String date) {
        this.locationName = locationName;
        this.comment = comment;
        this.quietness = quietness;
        this.busyness = busyness;
        this.comfort = comfort;
        this.whiteboards = whiteboards;
        this.outlets = outlets;
        this.computers = computers;
        this.submissionDate = date;
        this.overall = Float.parseFloat(
                new DecimalFormat("#.#") // round to one decimal place
                        .format((quietness + busyness + comfort) / 3)
        );
    }

    @Exclude
    public String getLocationName() {
        return locationName;
    }

    @Exclude
    public void setLocationName(String locationName) {
        this.locationName = locationName;
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
    public float getOverall() {
        return overall;
    }

    @Exclude
    public void setOverall(float overall) {
        this.overall = overall;
    }

    @Exclude
    public float getQuietness() {
        return quietness;
    }

    @Exclude
    public void setQuietness(float quietness) {
        this.quietness = quietness;
    }

    @Exclude
    public float getBusyness() {
        return busyness;
    }

    @Exclude
    public void setBusyness(float busyness) {
        this.busyness = busyness;
    }

    @Exclude
    public float getComfort() {
        return comfort;
    }

    @Exclude
    public void setComfort(float comfort) {
        this.comfort = comfort;
    }

    @Exclude
    public boolean getWhiteboards() {
        return whiteboards;
    }

    @Exclude
    public void setWhiteboards(boolean whiteboards) {
        this.whiteboards = whiteboards;
    }

    @Exclude
    public boolean getOutlets() {
        return outlets;
    }

    @Exclude
    public void setOutlets(boolean outlets) {
        this.outlets = outlets;
    }

    @Exclude
    public boolean getComputers() {
        return computers;
    }

    @Exclude
    public void setComputers(boolean computers) {
        this.computers= computers;
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
