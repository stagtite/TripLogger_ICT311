package com.example.caj015.triplogger;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

public class TripListFragment extends Fragment
{
    private RecyclerView tTripRecyclerView;
    private TripAdapter tAdapter;
    private Button oOptButton;
    private Button tTripButton;

    /*@Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }*/

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View v = inflater.inflate(R.layout.fragment_trip_list, container, false);

        tTripRecyclerView = (RecyclerView) v.findViewById(R.id.trip_recycler_view);
        tTripRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        oOptButton = (Button) v.findViewById(R.id.menu_options);
        oOptButton.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                //Opt opt = new Opt();
                //TripLab.get(getActivity()).addOpt(opt);
                //Log.i("Options", "Yea Options");
                Intent intent = new Intent(getActivity(), OptActivity.class);
                startActivity(intent);
            }
        });

        tTripButton = (Button) v.findViewById(R.id.menu_item_new_trip);
        tTripButton.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                Trip trip = new Trip();
                TripLab.get(getActivity()).addTrip(trip);
                Intent tIntent = TripPagerActivity.newIntent(getActivity(), trip.gettId());
                startActivity(tIntent);
            }
        });

        updateUI();

        return v;
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
    }

    /*
   @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
    {
        super.onCreateOptionsMenu(layout, inflater);
        inflater.inflate(R.layout.fragment_trip_list, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.menu_item_new_trip:
                Trip trip = new Trip();
                TripLab.get(getActivity()).addTrip(trip);
                Intent tIntent = TripPagerActivity.newIntent(getActivity(), trip.gettId());
                startActivity(tIntent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }



        /*oOptButton = (Button) v.findViewById(R.id.trip_pic);
        oOptButton.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                startActivity();
            }
        });
    }*/

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
    }

    private class TripHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        private TextView tTitleTextView;
        private TextView tDateTextView;
        //private CheckBox tFinishedCheckBox;
        private Trip tTrip;

        public TripHolder (View itemView)
        {
            super(itemView);
            itemView.setOnClickListener(this);

            tTitleTextView = (TextView) itemView.findViewById(R.id.list_item_trip_title_text_view);
            tDateTextView = (TextView) itemView.findViewById(R.id.list_item_trip_date_text_view);
            //tFinishedCheckBox = (CheckBox) itemView.findViewById(R.id.list_item_trip_finished_check_box);
        }

        public void bindTrip(Trip trip)
        {
            tTrip = trip;
            tTitleTextView.setText(tTrip.gettTitle());
            tDateTextView.setText(tTrip.gettDate().toString());
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
