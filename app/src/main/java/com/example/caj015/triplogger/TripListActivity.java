package com.example.caj015.triplogger;


import android.support.v4.app.Fragment;

public class TripListActivity extends SingleFragmentActivity
{
    @Override
    protected Fragment createFragment()
    {
        return new TripListFragment();
    }
}
