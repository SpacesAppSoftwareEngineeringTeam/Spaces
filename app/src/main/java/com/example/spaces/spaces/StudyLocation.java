package com.example.spaces.spaces;

import android.graphics.drawable.Drawable;

import java.util.ArrayList;

/**
 * Created by Steven on 3/25/2018.
 */

class StudyLocation {

    private String locationName = "";
    private ArrayList<Drawable> pictures = new ArrayList<Drawable>();
    private double overallReviewAvg = 0;
    private double quietnessAvg = 0;
    private double businessAvg = 0;
    private double comfortAvg = 0;
    private double whiteboardAvg = 0;
    private double outletAvg = 0;
    private double seatingAvg = 0;
    private ArrayList<Review> reviews = new ArrayList<Review>();
    private boolean accessibilityFlag = false;

    ArrayList<Drawable> getPictures() {
        return pictures;
    }

    void setPictures(ArrayList<Drawable> pictures) {
        this.pictures = pictures;
    }

    void addPicture(Drawable picture){
        pictures.add(picture);
    }

    double getOverallReviewAvg() {
        return overallReviewAvg;
    }

    void setOverallReviewAvg(double overallReviewAvg) {
        this.overallReviewAvg = overallReviewAvg;
    }

    double getQuietnessAvg() {
        return quietnessAvg;
    }

    void setQuietnessAvg(double quietnessAvg) {
        this.quietnessAvg = quietnessAvg;
    }

    double getBusinessAvg() {
        return businessAvg;
    }

    void setBusinessAvg(double businessAvg) {
        this.businessAvg = businessAvg;
    }

    double getComfortAvg() {
        return comfortAvg;
    }

    void setComfortAvg(double comfortAvg) {
        this.comfortAvg = comfortAvg;
    }

    double getWhiteboardAvg() {
        return whiteboardAvg;
    }

    void setWhiteboardAvg(double whiteboardAvg) {
        this.whiteboardAvg = whiteboardAvg;
    }

    double getOutletAvg() {
        return outletAvg;
    }

    void setOutletAvg(double outletAvg) {
        this.outletAvg = outletAvg;
    }

    double getSeatingAvg() {
        return seatingAvg;
    }

    void setSeatingAvg(double seatingAvg) {
        this.seatingAvg = seatingAvg;
    }

    ArrayList<Review> getReviews() {
        return reviews;
    }

    void setReviews(ArrayList<Review> reviews) {
        this.reviews = reviews;
    }

    void addReview(Review review){
        reviews.add(review);
    }

    boolean isAccessibilityFlag() {
        return accessibilityFlag;
    }

    void setAccessibilityFlag(boolean accessibilityFlag) {
        this.accessibilityFlag = accessibilityFlag;
    }

    String getLocationName() {

        return locationName;
    }

    void setLocationName(String locationName) {
        this.locationName = locationName;
    }
}
