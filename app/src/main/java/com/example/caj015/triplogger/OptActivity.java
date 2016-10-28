package com.example.caj015.triplogger;

import android.support.v4.app.Fragment;

//Creates the new fragment
public class OptActivity extends SingleFragmentActivity
{
    @Override
    protected Fragment createFragment()
    {
        return new OptFragment();
    }
}
