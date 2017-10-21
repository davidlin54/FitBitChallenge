package com.davidlin54.fitbitchallenge;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class RGBAdapter extends RecyclerView.Adapter<RGBAdapter.ViewHolder> {
    private List<RGBItem> mItems;

    public RGBAdapter(List<RGBItem> items) {
        mItems = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        // inflate RGBItem layout
        View RGBView = inflater.inflate(R.layout.item_rgb, parent, false);

        // return viewholder item
        return new ViewHolder(RGBView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        RGBItem item = mItems.get(position);

        // set the text for the recyclerview item
        holder.mRGBTextView.setText("r: " + item.getDr() + " g: " + item.getDg() + " b: " + item.getDb());

        // set the background colour for the recyclerview item
        holder.mBackgroundLayout.setBackgroundColor(Color.rgb(item.getR(), item.getG(), item.getB()));
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView mRGBTextView;
        public View mBackgroundLayout;

        public ViewHolder(View itemView) {
            super(itemView);

            mRGBTextView = (TextView) itemView.findViewById(R.id.tvRGB);
            mBackgroundLayout = itemView.findViewById(R.id.layoutItem);
        }
    }
}
