package com.example.spaces.spaces;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.support.v7.widget.CardView;

/**
 * Created by Steven on 3/14/2018.
 */

public class SpacesAdapter extends RecyclerView.Adapter<SpacesAdapter.ViewHolder> {

    private String[] mDataset;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        //We will probably want more views to be here (like a CardView maybe?)
        public TextView mTextView;
        public CardView mCardView;

        public ViewHolder(View v) {
            super(v);
            mTextView = v.findViewById(R.id.spaceName);
            mCardView = v.findViewById(R.id.cardView);

            //Sound example code I found used below line to set some stuff for listening to clicks
            //mTextView.setOnClickListener(this);
        }

    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public SpacesAdapter(String[] myDataset) {
        mDataset = myDataset;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public SpacesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.spaces_cardview_item, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.mTextView.setText(mDataset[position]);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.length;
    }
}
