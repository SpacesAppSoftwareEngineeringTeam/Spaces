package com.example.spaces.spaces;

import android.os.Bundle;
import android.view.View;
import com.example.spaces.spaces.models.StudyLocation;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.support.v7.widget.CardView;
import android.widget.ImageView;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.graphics.Color;

/**
 * Created by Matt on 4/11/2018.
 */

public class SpacePageActivity extends BaseActivity {

    // Location structure for the view
    private StudyLocation location;

    // General space information
    private TextView spaceName;

    // Space photos layout information
    private CardView photosCard;
    private TextView photosTitle;
    private ImageView thumb1;
    private ImageView thumb2;
    private ImageView thumb3;
    private ImageButton seeMorePhotos;

    // Space reviews layout information
    private CardView reviewsCard;
    private TextView reviewsTitle;

    // Space ratings layout information
    private CardView ratingsCard;
    private TextView ratingsTitle;
    private TextView overallTitle;
    private TextView quietnessTitle;
    private TextView busynessTitle;
    private TextView comfortTitle;
    private TextView overallValue;
    private TextView quietnessValue;
    private TextView busynessValue;
    private TextView comfortValue;
    private RatingBar overallStars;
    private RatingBar quietnessStars;
    private RatingBar busynessStars;
    private RatingBar comfortStars;

    // General constructor
    public SpacePageActivity(StudyLocation location) {
        super();
        this.location = location;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.space_page);

        // Set the associated values for the page
        spaceName = findViewById(R.id.spaceName);
        photosCard = findViewById(R.id.photosCard);
        photosTitle = findViewById(R.id.photosTitle);
        thumb1 = findViewById(R.id.thumb1);
        thumb2 = findViewById(R.id.thumb2);
        thumb3 = findViewById(R.id.thumb3);
        seeMorePhotos = findViewById(R.id.seeMorePhotos);
        reviewsCard = findViewById(R.id.reviewsCard);
        reviewsTitle = findViewById(R.id.reviewsTitle);
        ratingsCard = findViewById(R.id.ratingsCard);
        ratingsTitle = findViewById(R.id.ratingsTitle);
        overallTitle = findViewById(R.id.overallTitle);
        quietnessTitle = findViewById(R.id.quietnessTitle);
        busynessTitle = findViewById(R.id.busynessTitle);
        comfortTitle = findViewById(R.id.comfortTitle);
        overallValue = findViewById(R.id.overallValue);
        quietnessValue = findViewById(R.id.quietnessValue);
        busynessValue = findViewById(R.id.busynessValue);
        comfortValue = findViewById(R.id.comfortValue);
        overallStars = findViewById(R.id.overallStars);
        quietnessStars = findViewById(R.id.quietnessStars);
        busynessStars = findViewById(R.id.busynessStars);
        comfortStars = findViewById(R.id.comfortStars);

        // Set the specific contents of each view based on the location
        specifyViewInfo();
    }

    private void specifyViewInfo() {
        spaceName.setText(location.getLocationName());
        overallValue.setText(Double.toString(location.getOverallReviewAvg()));
        quietnessValue.setText(Double.toString(location.getQuietnessAvg()));
        busynessValue.setText(Double.toString(location.getBusinessAvg()));
        comfortValue.setText(Double.toString(location.getComfortAvg()));
        overallStars.setNumStars((int)location.getOverallReviewAvg());
        quietnessStars.setNumStars((int)location.getQuietnessAvg());
        busynessStars.setNumStars((int)location.getBusinessAvg());
        comfortStars.setNumStars((int)location.getComfortAvg());
    }

}
