package com.davidlin54.fitbitchallenge;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements OnDataUpdateListener{
    private RecyclerView mRGBRecyclerView;
    private RGBAdapter mAdapter;
    private List<RGBItem> mItems;

    private FitBitFetchTask mTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRGBRecyclerView = (RecyclerView) findViewById(R.id.rvRGBList);
        mItems = new ArrayList<>();
        mAdapter = new RGBAdapter(mItems);
        mRGBRecyclerView.setAdapter(mAdapter);
        mRGBRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void onResume() {
        super.onResume();

        mTask = new FitBitFetchTask(this);
        mTask.execute();
    }

    @Override
    protected void onPause() {
        if (mTask != null)
            mTask.cancel(true);

        // reset items and clear list
        int oldItemsSize = mItems.size();
        mItems.clear();
        mAdapter.notifyItemRangeRemoved(0, oldItemsSize);

        super.onPause();
    }

    @Override
    public void onDataUpdate(RGBItem rgbItem) {
        mItems.add(rgbItem);
        mAdapter.notifyItemInserted(mItems.size() - 1);
        mRGBRecyclerView.scrollToPosition(mItems.size() - 1);
    }
}
