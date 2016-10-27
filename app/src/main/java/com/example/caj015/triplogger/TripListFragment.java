package com.example.caj015.triplogger;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.List;

public class TripListFragment extends Fragment
{
    private RecyclerView tTripRecyclerView;
    private TripAdapter tAdapter;
    private boolean tSubtitleVisible;
    private static final String SAVED_SUBTITLE_VISIBLE = "subtitle";

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public android.view.View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_trip_list, container, false);

        tTripRecyclerView = (RecyclerView) view.findViewById(R.id.trip_recycler_view);
        tTripRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        if (savedInstanceState != null)
        {
            tSubtitleVisible = savedInstanceState.getBoolean(SAVED_SUBTITLE_VISIBLE);
        }

        updateUI();

        return view;
    }

    @Override
    public void onResume()
    {
        super.onResume();
        updateUI();
    }

    @Override
    public void onSaveInstanceState(Bundle outState)
    {
        super.onSaveInstanceState(outState);
        outState.putBoolean(SAVED_SUBTITLE_VISIBLE, tSubtitleVisible);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
    {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_trip_list, menu);

        MenuItem subtitleItem = menu.findItem(R.id.menu_item_show_subtitle);
        if (tSubtitleVisible)
        {
            subtitleItem.setTitle(R.string.hide_subtitle);
        }
        else
        {
            subtitleItem.setTitle(R.string.show_subtitle);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.menu_item_new_trip:
                Trip trip = new Trip();
                TripLab.get(getActivity()).addTrip(trip);
                Intent intent = TripPagerActivity.newIntent(getActivity(), trip.gettId());
                startActivity(intent);
                return true;
            case R.id.menu_item_show_subtitle:
                tSubtitleVisible = !tSubtitleVisible;
                getActivity().invalidateOptionsMenu();
                updateSubtitle();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void updateSubtitle()
    {
        TripLab tripLab = TripLab.get(getActivity());
        int tripCount = tripLab.gettTrips().size();
        String subtitle = getString(R.string.subtitle_format, tripCount);

        if (!tSubtitleVisible)
        {
            subtitle = null;
        }

        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.getSupportActionBar().setSubtitle(subtitle);
    }

    private void updateUI()
    {
        TripLab tripLab = TripLab.get(getActivity());
        List<Trip> trips = tripLab.gettTrips();

        if(tAdapter == null)
        {
            tAdapter = new TripAdapter(trips);
            tTripRecyclerView.setAdapter(tAdapter);
        }
        else
        {
            tAdapter.setTrips(trips);
            tAdapter.notifyDataSetChanged();
        }

        updateSubtitle();
    }

    private class TripHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        private TextView tTitleTextView;
        private TextView tDateTextView;
        private CheckBox tFinishedCheckBox;
        private Trip tTrip;

        public TripHolder (View itemView)
        {
            super(itemView);
            itemView.setOnClickListener(this);

            tTitleTextView = (TextView) itemView.findViewById(R.id.list_item_trip_title_text_view);
            tDateTextView = (TextView) itemView.findViewById(R.id.list_item_trip_date_text_view);
            tFinishedCheckBox = (CheckBox) itemView.findViewById(R.id.list_item_trip_finished_check_box);
        }

        public void bindTrip(Trip trip)
        {
            tTrip = trip;
            tTitleTextView.setText(tTrip.gettTitle());
            tDateTextView.setText(tTrip.gettDate().toString());
            tFinishedCheckBox.setChecked(tTrip.istFinish());
        }

        @Override
        public void onClick(View v)
        {
            Intent intent = TripPagerActivity.newIntent(getActivity(), tTrip.gettId());
            startActivity(intent);
        }
    }

    private class TripAdapter extends RecyclerView.Adapter<TripHolder>
    {
        private List<Trip> tTrips;

        public TripAdapter(List<Trip> trips)
        {
            tTrips = trips;
        }

        @Override
        public TripHolder onCreateViewHolder(ViewGroup parent, int viewType)
        {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());

            View view = layoutInflater.inflate(R.layout.list_item_trip, parent, false);

            return new TripHolder(view);
        }

        @Override
        public void onBindViewHolder(TripHolder holder, int position)
        {
            Trip trip = tTrips.get(position);
            holder.bindTrip(trip);
        }

        @Override
        public int getItemCount()
        {
            return tTrips.size();
        }

        public void setTrips(List<Trip> trips)
        {
            tTrips = trips;
        }
    }
}
