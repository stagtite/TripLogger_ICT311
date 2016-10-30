package com.example.caj015.triplogger;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

public class LocatorFragment extends SupportMapFragment
{
    private static final String TAG = "LocatorFragment";

    private GoogleApiClient tClient;
    private GoogleMap tMap;
    private Bitmap tMapImage;
    private GalleryItem tMapItem;
    private Location tCurrentLocation;

    public static LocatorFragment newInstance()
    {
        return new LocatorFragment(); //MAKE OPTIONS MENU LIKE THIS
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View v = inflater.inflate(R.layout.activity_map, container, false);
        return v;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        tClient = new GoogleApiClient.Builder(getActivity())
                .addApi(LocationServices.API)
                .addConnectionCallbacks(new GoogleApiClient.ConnectionCallbacks() {
                    @Override
                    public void onConnected(Bundle bundle) {
                        getActivity().invalidateOptionsMenu();
                    }

                    @Override
                    public void onConnectionSuspended(int i) {

                    }
                })
                .build();

        getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                tMap = googleMap;
                //updateUI();
            }
        });
    }

    /*@Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);


        tClient = new GoogleApiClient.Builder(getActivity())
        .addApi(LocationServices.API)
                .addConnectionCallbacks(new GoogleApiClient.ConnectionCallbacks() {
                    @Override
                    public void onConnected(Bundle bundle) {
                        getActivity().invalidateOptionsMenu();
                    }

                    @Override
                    public void onConnectionSuspended(int i) {

                    }
                })
        .build();
    }*/

    @Override
    public void onStart()
    {
        getActivity().invalidateOptionsMenu();

        tClient.connect();
    }

    @Override
    public void onResume()
    {
        super.onResume();

        /*if (ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_FINE_LOCATION)))
        {
            Log.i(TAG, "Got a fix: " + location);
            return;
        }*/
    }

    public void onConnected(Bundle connectionHint)
    {
        LocationRequest request = LocationRequest.create();
        request.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        request.setNumUpdates(1);
        request.setInterval(0);

        LocationServices.FusedLocationApi.requestLocationUpdates(tClient, request, new LocationListener()
        {
            @Override
            public void onLocationChanged(Location location)
            {
                Log.i(TAG, "Got a fix: " + location);
            }
        });
    }

    @Override
    public void onStop()
    {
        super.onStop();

        tClient.disconnect();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_locator, menu);

        MenuItem searchItem = menu.findItem(R.id.action_locate);
        searchItem.setEnabled(tClient.isConnected());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_locate:
                findImage();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void findImage() {
        LocationRequest request = LocationRequest.create();
        request.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        request.setNumUpdates(1);
        request.setInterval(0);
        Log.i(TAG, "Request: " + request);
        LocationServices.FusedLocationApi
                .requestLocationUpdates(tClient, request, new LocationListener() {
                    @Override
                    public void onLocationChanged(Location location) {
                        Log.i("LocatrFragment", "Got a fix: " + location);
                        new SearchTask().execute(location);
                    }
                });
    }

    private void updateUI() {
        if (tMap == null || tMapImage == null) {
            return;
        }

        LatLng itemPoint = new LatLng(tMapItem.getLat(), tMapItem.getLon());
        LatLng myPoint = new LatLng(
                tCurrentLocation.getLatitude(), tCurrentLocation.getLongitude());

        BitmapDescriptor itemBitmap = BitmapDescriptorFactory.fromBitmap(tMapImage);
        MarkerOptions itemMarker = new MarkerOptions()
                .position(itemPoint)
                .icon(itemBitmap);
        MarkerOptions myMarker = new MarkerOptions()
                .position(myPoint);

        tMap.clear();
        tMap.addMarker(itemMarker);
        tMap.addMarker(myMarker);

        LatLngBounds bounds = new LatLngBounds.Builder()
                .include(itemPoint)
                .include(myPoint)
                .build();

        int margin = getResources().getDimensionPixelSize(R.dimen.map_inset_margin);
        CameraUpdate update = CameraUpdateFactory.newLatLngBounds(bounds, margin);
        tMap.animateCamera(update);
    }

    private class SearchTask extends AsyncTask<Location,Void,Void> {
        private Bitmap mBitmap;
        private GalleryItem mGalleryItem;
        private Location mLocation;

        @Override
        protected Void doInBackground(Location... params)
        {
            mLocation = params[0];
            FlickerFetcher fetchr = new FlickerFetcher();
            List<GalleryItem> items = fetchr.searchPhotos(params[0]);

            if (items.size() == 0) {
                return null;
            }

            mGalleryItem = items.get(0);

            try
            {
                byte[] bytes = fetchr.getUrlBytes(mGalleryItem.getUrl());
                mBitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
            }
            catch (IOException ioe)
            {
                Log.i("SearchTask", "Unable to download bitmap", ioe);
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            tMapImage = mBitmap;
            tMapItem = mGalleryItem;
            tCurrentLocation = mLocation;

            updateUI();
        }
    }
}
