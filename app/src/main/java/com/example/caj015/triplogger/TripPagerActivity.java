package com.example.caj015.triplogger;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import java.util.List;
import java.util.UUID;

public class TripPagerActivity extends AppCompatActivity
{
    private static final String EXTRA_TRIP_ID = "com.example.caj015.triplogger.trip_id";

    private ViewPager tViewPager;
    private List<Trip> tTrips;

    public static Intent newIntent(Context packageContext, UUID tripID)
    {
        Intent intent = new Intent(packageContext, TripPagerActivity.class);
        intent.putExtra(EXTRA_TRIP_ID, tripID);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_pager);

        UUID tripID = (UUID) getIntent().getSerializableExtra(EXTRA_TRIP_ID);

        tViewPager = (ViewPager) findViewById(R.id.activity_trip_pager_view_pager);

        tTrips = TripLab.get(this).gettTrips();
        FragmentManager fragmentManager = getSupportFragmentManager();

        tViewPager.setAdapter(new FragmentStatePagerAdapter(fragmentManager)
        {
            @Override
            public Fragment getItem(int position)
            {
                Trip trip = tTrips.get(position);
                return TripFragment.newInstance(trip.gettId());
            }

            @Override
            public int getCount()
            {
                return tTrips.size();
            }
        });

        for (int i = 0; i < tTrips.size(); i++)
        {
            if (tTrips.get(i).gettId().equals(tripID))
            {
                tViewPager.setCurrentItem(i);
                break;
            }
        }
    }
}
