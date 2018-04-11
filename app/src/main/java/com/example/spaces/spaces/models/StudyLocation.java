package com.example.spaces.spaces.models;

import android.net.Uri;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.ArrayList;

/**
 * Created by Steven on 3/25/2018.
 */

@IgnoreExtraProperties
public class StudyLocation {

    // Firebase requires model classes to have public fields
    public String locationName;
    public ArrayList<String> pictureIds = new ArrayList<>();
    public double overallReviewAvg = 0;
    public double quietnessAvg = 0;
    public double businessAvg = 0;
    public double comfortAvg = 0;
    public double whiteboardAvg = 0;
    public double outletAvg = 0;
    public double seatingAvg = 0;
    public ArrayList<Review> reviews = new ArrayList<>();
    public boolean accessibilityFlag = false;

    public StudyLocation() {
        // Default constructor required for calls to DataSnapshot.getValue(StudyLocation.class)
    }

    public StudyLocation(String locationName) {
        this.locationName = locationName;
    }


    @Exclude
    public ArrayList<String> getPictureIds() {
        return pictureIds;
    }

    @Exclude
    public void addPicture(String pictureIdString){
        pictureIds.add(pictureIdString);
    }

    @Exclude
    public void addPicture(Uri pictureId){
        pictureIds.add(pictureId.toString());
    }

    @Exclude
    public void addPictures(ArrayList<String> pictureIds) {
        this.pictureIds.addAll(pictureIds);
    }

    @Exclude
    public double getOverallReviewAvg() {
        return overallReviewAvg;
    }

    @Exclude
    public void setOverallReviewAvg(double overallReviewAvg) {
        this.overallReviewAvg = overallReviewAvg;
    }

    @Exclude
    public double getQuietnessAvg() {
        return quietnessAvg;
    }

    @Exclude
    public void setQuietnessAvg(double quietnessAvg) {
        this.quietnessAvg = quietnessAvg;
    }

    @Exclude
    public double getBusinessAvg() {
        return businessAvg;
    }

    @Exclude
    public void setBusinessAvg(double businessAvg) {
        this.businessAvg = businessAvg;
    }

    @Exclude
    public double getComfortAvg() {
        return comfortAvg;
    }

    @Exclude
    public void setComfortAvg(double comfortAvg) {
        this.comfortAvg = comfortAvg;
    }

    @Exclude
    public double getWhiteboardAvg() {
        return whiteboardAvg;
    }

    @Exclude
    public  void setWhiteboardAvg(double whiteboardAvg) {
        this.whiteboardAvg = whiteboardAvg;
    }

    @Exclude
    public double getOutletAvg() {
        return outletAvg;
    }

    @Exclude
    public void setOutletAvg(double outletAvg) {
        this.outletAvg = outletAvg;
    }

    @Exclude
    public double getSeatingAvg() {
        return seatingAvg;
    }

    @Exclude
    public void setSeatingAvg(double seatingAvg) {
        this.seatingAvg = seatingAvg;
    }

    @Exclude
    public ArrayList<Review> getReviews() {
        return reviews;
    }

    @Exclude
    public void setReviews(ArrayList<Review> reviews) {
        this.reviews = reviews;
    }

    @Exclude
    public void addReview(Review review){
        reviews.add(review);
        int size = reviews.size();
        setOverallReviewAvg(calcNewAvg(getOverallReviewAvg(), size, review.getOverall()));
        setQuietnessAvg(calcNewAvg(getQuietnessAvg(), size, review.getQuietness()));
        setBusinessAvg(calcNewAvg(getBusinessAvg(), size, review.getBusiness()));
        setComfortAvg(calcNewAvg(getComfortAvg(), size, review.getComfort()));
        setWhiteboardAvg(calcNewAvg(getWhiteboardAvg(), size, review.getWhiteboards()));
        setOutletAvg(calcNewAvg(getOutletAvg(), size, review.getOutlets()));
        setSeatingAvg(calcNewAvg(getSeatingAvg(), size, review.getSeating()));
    }

    @Exclude
    public boolean isAccessible() {
        return accessibilityFlag;
    }

    @Exclude
    public void setAccessibilityFlag(boolean accessibilityFlag) {
        this.accessibilityFlag = accessibilityFlag;
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
    private double calcNewAvg(double oldAvg, int numReviews, int newReviewScore){
        return (oldAvg * (numReviews - 1)/ numReviews) + (newReviewScore / numReviews);
    }

}
