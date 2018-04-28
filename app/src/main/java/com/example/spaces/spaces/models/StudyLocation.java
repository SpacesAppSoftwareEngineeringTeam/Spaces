package com.example.spaces.spaces.models;

import android.net.Uri;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.IgnoreExtraProperties;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Steven on 3/25/2018.
 */

@IgnoreExtraProperties
public class StudyLocation implements Serializable {

    // Firebase requires model classes to have public fields
    public String locationName = "";
    public double overallReviewAvg = 0;
    public double quietnessAvg = 0;
    public double busynessAvg = 0;
    public double comfortAvg = 0;
    public double whiteboardAvg = 0;
    public double outletAvg = 0;
    public double computerAvg = 0;
    public Map<String, String> pictureIds = new HashMap<>();
    public Map<String, Object> reviews = new HashMap<>();
    public boolean accessibilityFlag = false;

    public StudyLocation() {
        // Default constructor required for calls to DataSnapshot.getValue(StudyLocation.class)
    }

    public StudyLocation(String locationName) {
        this.locationName = locationName;
    }

    /**
     * Creates a StudyLocation with the given name
     * and populates it with info stored under that name in the database
     * @param locationName  name of the location to get
     * @param snapshot    snapshot from a valuelistener on a database reference to a location
     */
    public StudyLocation(String locationName, DataSnapshot snapshot) {
        this.locationName = locationName;
        this.setAverages(snapshot);
        this.setPictureIds(snapshot);
    }


    @Exclude
    public Map<String, String> getPictureIds() {
        return pictureIds;
    }

    @Exclude
    public void addPicture(Uri pictureId, DataSnapshot ds){
        addPicture(pictureId.toString(), ds);
    }

    /**
     * Adds the specified picture uri to this location in the object and in the database
     * @param pictureId  uri to add
     * @param locationSnapshot  DataSnapshot of all locations or of the location to add to
     */
    @Exclude
    public void addPicture(String pictureId, DataSnapshot locationSnapshot) {
        DatabaseReference locationRef = FirebaseDatabase.getInstance().getReference().
                child("locations").child(locationName);
        DatabaseReference newPicRef = locationRef.child("pictureIds").push();

        boolean alreadyAdded = false;
        Iterable<DataSnapshot> oldPicRefs = locationSnapshot.child("pictureIds").getChildren();
        for (DataSnapshot picRef : oldPicRefs) {
            try {
                if (((Map<String, String>) picRef.getValue()).values().contains(pictureId))
                    alreadyAdded = true;
            } catch (ClassCastException e) { // this runs if locationSnapshot is of a single location
                if (((String) picRef.getValue()).contains(pictureId))
                    alreadyAdded = true;
            }
        }

        if (!alreadyAdded) {
            Log.d("StudyLocation.addPic", "pictureId = "+pictureId);
            Log.d("StudyLocation.addPic", "newPicRef = "+newPicRef.toString());
            pictureIds.put(newPicRef.toString(), pictureId);
            newPicRef.setValue(pictureId);
        }  else Log.d("StudyLocation.addPic", "tried to add a picture that was already added");
    }

    @Exclude
    public double getOverallReviewAvg() {
        return round(overallReviewAvg);
    }

    @Exclude
    public void setOverallReviewAvg(double overallReviewAvg) {
        this.overallReviewAvg = round(overallReviewAvg);
    }

    @Exclude
    public double getQuietnessAvg() {
        return round(quietnessAvg);
    }

    @Exclude
    public void setQuietnessAvg(double quietnessAvg) {
        this.quietnessAvg = quietnessAvg;
    }

    @Exclude
    public double getBusynessAvg() {
        return round(busynessAvg);
    }

    @Exclude
    public void setBusynessAvg(double busynessAvg) {
        this.busynessAvg = busynessAvg;
    }

    @Exclude
    public double getComfortAvg() {
        return round(comfortAvg);
    }

    @Exclude
    public void setComfortAvg(double comfortAvg) {
        this.comfortAvg = comfortAvg;
    }

    @Exclude
    public double getWhiteboardAvg() {
        return round(whiteboardAvg);
    }

    @Exclude
    public  void setWhiteboardAvg(double whiteboardAvg) {
        this.whiteboardAvg = whiteboardAvg;
    }

    @Exclude
    public double getOutletAvg() {
        return round(outletAvg);
    }

    @Exclude
    public void setOutletAvg(double outletAvg) {
        this.outletAvg = outletAvg;
    }

    @Exclude
    public double getComputerAvg() {
        return round(computerAvg);
    }

    @Exclude
    public void setComputerAvg(double computerAvg) {
        this.computerAvg = computerAvg;
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
    public Map<String, Object> getReviews() {
        return reviews;
    }

    @Exclude
    public void setReviews(Map<String, Object> reviews) {
        this.reviews = reviews;
    }

    @Exclude
    public void addReview(Review review) {
        DatabaseReference locationRef = FirebaseDatabase.getInstance().getReference().
                child("locations").child(locationName);
        DatabaseReference reviewRef = locationRef.child("reviews").push();
        updateAllAverages(locationRef, review);
        reviews.put(reviewRef.toString(), review);
        reviewRef.setValue(review);
    }



    @Exclude
    private void setAverages(DataSnapshot snapshot) {
        DataSnapshot s = snapshot.child("overallReviewAvg");
        if (s.getValue(double.class) != null) {
            System.out.println("got "+locationName+"'s overallReviewAvg: "+s.getValue(double.class));
            setOverallReviewAvg(s.getValue(double.class));
        } else System.out.println(locationName+"'s overallReviewAvg was null");

        s = snapshot.child("quietnessAvg");
        if (s.getValue(double.class) != null)
            setQuietnessAvg(s.getValue(double.class));
        s = snapshot.child("busynessAvg");
        if (s.getValue(double.class) != null)
            setBusynessAvg(s.getValue(double.class));
        s = snapshot.child("comfortAvg");
        if (s.getValue(double.class) != null)
            setComfortAvg(s.getValue(double.class));

        s = snapshot.child("whiteboardAvg");
        if (s.getValue(double.class) != null)
            setWhiteboardAvg(s.getValue(double.class));
        s = snapshot.child("outletAvg");
        if (s.getValue(double.class) != null)
            setOutletAvg(s.getValue(double.class));

    }

    @Exclude
    public void setPictureIds(DataSnapshot snapshot) {
        Iterable<DataSnapshot> picSnapshots = snapshot.child("pictureIds").getChildren();
        for (DataSnapshot picSnap : picSnapshots) {
            if (picSnap.getValue(String.class) != null)
                this.pictureIds.put(picSnap.getRef().toString(), picSnap.getValue(String.class));
        }
    }


    @Exclude
    private void updateAllAverages(DatabaseReference locationRef, Review review) {
        int size = reviews.size() + 1;

        setQuietnessAvg(calcNewAvg(getQuietnessAvg(), size, review.getQuietness()));
        try {
            locationRef.child("quietnessAvg").setValue(calcNewAvg(getQuietnessAvg(), size, review.getQuietness()));
        } catch (Exception e) {e.printStackTrace();}
        setBusynessAvg(calcNewAvg(getBusynessAvg(), size, review.getBusyness()));
        locationRef.child("busynessAvg").setValue(calcNewAvg(getBusynessAvg(), size, review.getBusyness()));
        setComfortAvg(calcNewAvg(getComfortAvg(), size, review.getComfort()));
        locationRef.child("comfortAvg").setValue(calcNewAvg(getComfortAvg(), size, review.getComfort()));

        int hasWhiteboards = (review.getWhiteboards()) ? 1 : 0;
        int hasOutlets = (review.getOutlets()) ? 1 : 0;
        int hasComputers = (review.getComputers()) ? 1 : 0;
        setWhiteboardAvg(calcNewAvg(getWhiteboardAvg(), size, hasWhiteboards));
        locationRef.child("whiteboardAvg").setValue(calcNewAvg(getWhiteboardAvg(), size, hasWhiteboards));
        setOutletAvg(calcNewAvg(getOutletAvg(), size, hasOutlets));
        locationRef.child("outletAvg").setValue(calcNewAvg(getOutletAvg(), size, hasOutlets));
        setComputerAvg(calcNewAvg(getComputerAvg(), size, hasComputers));
        locationRef.child("computerAvg").setValue(calcNewAvg(getComputerAvg(), size, hasComputers));

        setOverallReviewAvg(calcNewAvg(getOverallReviewAvg(), size, review.getOverall()));
        locationRef.child("overallReviewAvg").setValue(calcNewAvg(getOverallReviewAvg(), size, review.getOverall()));
    }



    @Exclude
    private double calcNewAvg(double oldAvg, int numReviews, float newReviewScore) {
        if (numReviews < 2)
            return newReviewScore;
        else
            return (oldAvg * (numReviews - 1)/ numReviews) + (newReviewScore / numReviews);
    }

    @Exclude
    private double round(double value) {
        // round to one decimal place
        return Double.parseDouble(new DecimalFormat("#.#").format(value));
    }

}
