package com.example.caj015.triplogger;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;

import java.io.File;
import java.util.Date;
import java.util.UUID;


public class TripFragment extends Fragment
{
    //Variables
    private static final String ARG_TRIP_ID = "trip_id";
    private static final String DIALOG_DATE = "DialogDate";

    private static final int REQUEST_DATE = 0;
    private static final int REQUEST_CONTACT = 1;
    private static final int REQUEST_PHOTO = 2;

    private Trip tTrip;
    private File tPhotoFile;
    private String tDest;
    private String tDuration;
    private String tComment;
    private EditText tTitleField;
    private EditText tDestField;
    private EditText tDurationField;
    private EditText tCommentField;
    private EditText tDateField;
    private Button tDeleteButton;
    private Button tSaveButton;
    private Button tLocationField;
    private ImageButton tPhotoButton;
    private ImageView tPhotoView;
    private Spinner tTypeSpinner;
    private Context context;

    //Creates a new trip fragment with a unique ID
    public static TripFragment newInstance(UUID tripId)
    {
        Bundle args = new Bundle();
        args.putSerializable(ARG_TRIP_ID, tripId);

        TripFragment fragment = new TripFragment();
        fragment.setArguments(args);
        return fragment;
    }

    //Sets up access to Trip
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        UUID tripID = (UUID) getArguments().getSerializable(ARG_TRIP_ID);
        tTrip = TripLab.get(getActivity()).gettTrip(tripID);
        tPhotoFile = TripLab.get(getActivity()).getPhotoFile(tTrip);
    }

    @Override
    public void onPause()
    {
        super.onPause();

        TripLab.get(getActivity()).updateTrip(tTrip);
    }

    //View setup - Adds listeners to all the boxes on the Trip screen
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        //Adds everything to the view
        View v = inflater.inflate(R.layout.fragment_trip, container, false);

        //Name of the Trip
        tTitleField = (EditText)v.findViewById(R.id.trip_title);
        tTitleField.setText(tTrip.gettTitle());
        tTitleField.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {
                // This space intentionally left blank
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                tTrip.settTitle(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s)
            {
                // This space intentionally left blank
            }
        });

        //Date Button
        tDateField = (EditText) v.findViewById(R.id.trip_date);
        tDateField.setText(tTrip.gettDate());
        tDateField.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {
                // This space intentionally left blank
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                tTrip.settDate(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s)
            {
                // This space intentionally left blank
            }
        });

        //Type of trip - IDK HOW TO MAKE IT SAVE
        //SPINNER - Adapted from Android API: https://developer.android.com/guide/topics/ui/controls/spinner.html

        tTypeSpinner = (Spinner) v.findViewById(R.id.trip_type);

        ArrayAdapter<CharSequence> tAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.trip_array, android.R.layout.simple_spinner_item);
        tAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        tTypeSpinner.setAdapter(tAdapter);


        //tTypeSpinner.setSelection(tTrip.gettType());
        //tTypeSpinner.setOnItemSelectedListener(tTrip.settType());

        //Destination of the trip
        tDestField = (EditText) v.findViewById(R.id.trip_destination);
        tDestField.setText(tTrip.gettDestination());
        tDestField.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                tTrip.settDestination(s.toString());

            }

            @Override
            public void afterTextChanged(Editable s)
            {

            }
        });

        //Location of the trip
        //Destination of the trip
        tLocationField = (Button) v.findViewById(R.id.trip_location);
        tLocationField.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                //Opt opt = new Opt();
                //TripLab.get(getActivity()).addOpt(opt);
                //Log.i("Location", "Yea Locator");
                Intent intent = new Intent(getActivity(), LocatorActivity.class);
                startActivity(intent);
            }
        });


        //Duration of the trip
        tDurationField = (EditText) v.findViewById(R.id.trip_duration);
        tDurationField.setText(tTrip.gettDuration());
        tDurationField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                tTrip.settDuration(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s)
            {

            }
        });

        //Comment on the trip
        tCommentField = (EditText) v.findViewById(R.id.trip_comment);
        tCommentField.setText(tTrip.gettComment());
        tCommentField.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                tTrip.settComment(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s)
            {

            }
        });

        //PHOTO BUTTON
        PackageManager packageManager = getActivity().getPackageManager();

        tPhotoButton = (ImageButton) v.findViewById(R.id.trip_camera);
        final Intent captureImage = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        //Enables photo button
        boolean canTakePhoto = tPhotoFile != null && captureImage.resolveActivity(packageManager) != null;
        tPhotoButton.setEnabled(canTakePhoto);

        if (canTakePhoto)
        {
            Uri uri = Uri.fromFile(tPhotoFile);
            captureImage.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        }

        tPhotoButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startActivityForResult(captureImage, REQUEST_PHOTO);
            }
        });

        tPhotoView = (ImageView) v.findViewById(R.id.trip_photo);
        updatePhotoView();

        //DELETE BUTTON
        tDeleteButton = (Button) v.findViewById(R.id.trip_delete);
        tDeleteButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                TripLab.get(getContext()).deleteTrip(tTrip);
                //ADD FINISH FUNCTION FINISH();
                getActivity().finish();
            }
        });

        //SAVE BUTTON
        tSaveButton = (Button) v.findViewById(R.id.trip_save);
        tSaveButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                getActivity().finish();
            }
        });

        return v;
    }

    //Shows selected picture
    private void updatePhotoView()
    {
        if (tPhotoFile == null || !tPhotoFile.exists())
        {
            tPhotoView.setImageDrawable(null);
        }
        else
        {
            Bitmap bitmap = PictureUtils.getScaledBitmap(tPhotoFile.getPath(), getActivity());
            tPhotoView.setImageBitmap(bitmap);
        }
    }

    //Checks to see what needs to be updated
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (resultCode != Activity.RESULT_OK)
        {
            return;
        }
        else if (requestCode == REQUEST_PHOTO)
        {
            updatePhotoView();
        }
    }

    //Gets context for some reason? Wouldn't let me use context otherwise
    public Context getContext()
    {
        return context;
    }
}