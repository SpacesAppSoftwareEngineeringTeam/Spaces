package com.example.spaces.spaces.models;

import android.net.Uri;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.IgnoreExtraProperties;

import java.io.Serializable;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Map;

/**
 * Created by Steven on 3/25/2018.
 */

@IgnoreExtraProperties
public class StudyLocation implements Serializable {

    // Firebase requires model classes to have public fields
    public String locationName = "";
    public ArrayList<String> pictureIds = new ArrayList<>();
    public double overallReviewAvg = 0;
    public double quietnessAvg = 0;
    public double busynessAvg = 0;
    public double comfortAvg = 0;
    public double whiteboardAvg = 0;
    public double outletAvg = 0;
    public double computerAvg = 0;
    public Map<String, Object> reviews = new HashMap<>();
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
    public double getBusynessAvg() {
        return busynessAvg;
    }

    @Exclude
    public void setBusynessAvg(double busynessAvg) {
        this.busynessAvg = busynessAvg;
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
    public double getComputerAvg() {
        return computerAvg;
    }

    @Exclude
    public void setComputerAvg(double computerAvg) {
        this.computerAvg = computerAvg;
    }

    @Exclude
    public Map<String, Object> getReviews() {
        return reviews;
    }

    @Exclude
    public void setReviews(Map<String, Object> reviews) {
        this.reviews = reviews;
    }

    @Exclude
    public void addReview(DatabaseReference dref, Review review){
        int size = reviews.size();

        setOverallReviewAvg(calcNewAvg(getOverallReviewAvg(), size, review.getOverall()));
        setQuietnessAvg(calcNewAvg(getQuietnessAvg(), size, review.getQuietness()));
        setBusynessAvg(calcNewAvg(getBusynessAvg(), size, review.getBusyness()));
        setComfortAvg(calcNewAvg(getComfortAvg(), size, review.getComfort()));

        int hasWhiteboards = (review.getWhiteboards()) ? 1 : 0;
        int hasOutlets = (review.getOutlets()) ? 1 : 0;
        int hasComputers = (review.getComputers()) ? 1 : 0;
        setWhiteboardAvg(calcNewAvg(getWhiteboardAvg(), size, hasWhiteboards));
        setOutletAvg(calcNewAvg(getOutletAvg(), size, hasOutlets));
        setComputerAvg(calcNewAvg(getComputerAvg(), size, hasComputers));

        reviews.put(dref.toString(), review);
    }

    @Exclude
    public void addReview(Review review){
        DatabaseReference dref = FirebaseDatabase.getInstance().getReference().
                child("locations").child(locationName).child("reviews").push();
        addReview(dref, review);
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
    private double calcNewAvg(double oldAvg, int numReviews, float newReviewScore){
        return (oldAvg * (numReviews - 1)/ numReviews) + (newReviewScore / numReviews);
    }

}
