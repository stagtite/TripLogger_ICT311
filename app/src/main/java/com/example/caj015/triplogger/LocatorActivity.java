package com.example.caj015.triplogger;


import android.Manifest;
import android.app.Dialog;
import android.content.DialogInterface;
import android.nfc.Tag;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;

public class LocatorActivity extends SingleFragmentActivity
{
    private static final int REQUEST_ERROR = 0;

    @Override
    protected Fragment createFragment()
    {
        return LocatorFragment.newInstance();
    }

    @Override
    protected void onResume()
    {
        super.onResume();

        int errorCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);

        if (errorCode != ConnectionResult.SUCCESS)
        {
            Dialog errorDialog = GooglePlayServicesUtil.getErrorDialog(errorCode, this, REQUEST_ERROR, new DialogInterface.OnCancelListener()
            {
                @Override
                public void onCancel(DialogInterface dialog)
                {
                    finish();
                }
            });
            errorDialog.show();
        }
    }
}
