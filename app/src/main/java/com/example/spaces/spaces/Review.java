package com.example.spaces.spaces;

import java.util.Date;

/**
 * Created by Steven on 3/25/2018.
 */

class Review {

    String locationID = "";
    String userID = "";
    Date submissionDate;
    int overall = 0;
    int quietness = 0;
    int business = 0;
    int comfort = 0;
    int whiteboards = 0;
    int outlets = 0;
    int seating = 0;
    String comment = "";
    String accessibilityComment = "";

    String getLocationID() {
        return locationID;
    }

    void setLocationID(String locationID) {
        this.locationID = locationID;
    }

    String getUserID() {
        return userID;
    }

    void setUserID(String userID) {
        this.userID = userID;
    }

    Date getSubmissionDate() {
        return submissionDate;
    }

    void setSubmissionDate(Date submissionDate) {
        this.submissionDate = submissionDate;
    }

    int getOverall() {
        return overall;
    }

    void setOverall(int overall) {
        this.overall = overall;
    }

    int getQuietness() {
        return quietness;
    }

    void setQuietness(int quietness) {
        this.quietness = quietness;
    }

    int getBusiness() {
        return business;
    }

    void setBusiness(int business) {
        this.business = business;
    }

    int getComfort() {
        return comfort;
    }

    void setComfort(int comfort) {
        this.comfort = comfort;
    }

    int getWhiteboards() {
        return whiteboards;
    }

    void setWhiteboards(int whiteboards) {
        this.whiteboards = whiteboards;
    }

    int getOutlets() {
        return outlets;
    }

    void setOutlets(int outlets) {
        this.outlets = outlets;
    }

    int getSeating() {
        return seating;
    }

    void setSeating(int seating) {
        this.seating = seating;
    }

    String getComment() {
        return comment;
    }

    void setComment(String comment) {
        this.comment = comment;
    }

    String getAccessibilityComment() {
        return accessibilityComment;
    }

    void setAccessibilityComment(String accessibilityComment) {
        this.accessibilityComment = accessibilityComment;
    }
}
