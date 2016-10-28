package com.example.caj015.triplogger;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateFormat;
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
    private static final String ARG_TRIP_ID = "trip_id";
    private static final String DIALOG_DATE = "DialogDate";

    private static final int REQUEST_DATE = 0;
    private static final int REQUEST_CONTACT = 1;
    private static final int REQUEST_PHOTO = 2;

    private Trip tTrip;
    private File tPhotoFile;
    private EditText tTitleField;
    private Button tDateButton;
    private Button tShareButton;
    private Button tPicButton;
    private ImageButton tPhotoButton;
    private ImageView tPhotoView;
    private Spinner tTripSpinner;

    public static TripFragment newInstance(UUID tripId)
    {
        Bundle args = new Bundle();
        args.putSerializable(ARG_TRIP_ID, tripId);

        TripFragment fragment = new TripFragment();
        fragment.setArguments(args);
        return fragment;
    }

    //onCreate Setup
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

    //View setup
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
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
        tDateButton = (Button) v.findViewById(R.id.trip_date);
        updateDate();
        tDateButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                FragmentManager manager = getFragmentManager();
                DatePickerFragment dialog = DatePickerFragment.newInstance(tTrip.gettDate());
                dialog.setTargetFragment(TripFragment.this, REQUEST_DATE);
                dialog.show(manager, DIALOG_DATE);
            }
        });

        //This lets us share the trip in a message
        tShareButton = (Button) v.findViewById(R.id.trip_share);
        tShareButton.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("text/plain");
                i.putExtra(Intent.EXTRA_TEXT, getTripShare());
                i.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.trip_share_subject));
                i = Intent.createChooser(i, getString(R.string.share_trip));
                startActivity(i);
            }
        });

        //This lets us pick a contact
        final Intent pickContact = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);

        //TESTS AGAINST NO-CONTACT PROTECTION
        //pickContact.addCategory(Intent.CATEGORY_HOME);

        //Back to normal code
        tPicButton = (Button) v.findViewById(R.id.trip_pic);
        tPicButton.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                startActivityForResult(pickContact, REQUEST_CONTACT);
            }
        });

        if (tTrip.gettPic() != null)
        {
            tPicButton.setText(tTrip.gettPic());
        }

        //Stops no-contacts error - Disables contact button
        PackageManager packageManager = getActivity().getPackageManager();
        if (packageManager.resolveActivity(pickContact, PackageManager.MATCH_DEFAULT_ONLY) == null)
        {
            tPicButton.setEnabled(false);
        }

        tPhotoButton = (ImageButton) v.findViewById(R.id.trip_camera);
        final Intent captureImage = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

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

        //Spinner - Adapted from Android API: https://developer.android.com/guide/topics/ui/controls/spinner.html
        //
        tTripSpinner = (Spinner) v.findViewById(R.id.trip_type);

        ArrayAdapter<CharSequence> tAdapter = ArrayAdapter.createFromResource(this.getActivity(), R.array.trip_array, android.R.layout.simple_spinner_item);
        tAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        tTripSpinner.setAdapter(tAdapter);

        //Delete
        //tDeleteButton = (Button) v.findViewById(R.id.trip_delete);
        //tDeleteButton.setOnClickListener(new View.OnClickListener()
        //{
        //    @Override
        //    public void onClick(View v)
        //    {
        //        TripLab.get(getContext()).deleteTrip(tTrip);
        //        finish();
        //    }
        //});

        return v;
    }

    private void updateDate()
    {
        tDateButton.setText(tTrip.gettDate().toString());
    }

    private String getTripShare()
    {
        String finishedString = null;
        if (tTrip.istFinish())
        {
            finishedString = getString(R.string.trip_share_complete);
        }
        else
        {
            finishedString = getString(R.string.trip_share_uncomplete);
        }

        String dateFormat = "EEE, MMM dd";
        String dateString = DateFormat.format(dateFormat, tTrip.gettDate()).toString();

        String pic = tTrip.gettPic();
        if (pic == null)
        {
            pic = getString(R.string.trip_share_no_pic);
        }
        else
        {
            pic = getString(R.string.trip_share_pic, pic);
        }

        String share = getString(R.string.trip_share, tTrip.gettTitle(), dateString, finishedString, pic);

        return share;
    }

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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (resultCode != Activity.RESULT_OK)
        {
            return;
        }

        if (requestCode == REQUEST_DATE)
        {
            Date date = (Date) data.getSerializableExtra(DatePickerFragment.EXTRA_DATE);
            tTrip.settDate(date);
            updateDate();
        }

        //This finds the contact, THIS DOES NOT HAVE TO BE HERE
        else if (requestCode == REQUEST_CONTACT && data != null)
        {
            Uri contactUri = data.getData();

            //Specify which fields you want your query to return values for
            String[] queryFields = new String[]
            {
                    ContactsContract.Contacts.DISPLAY_NAME
            };

            //Perform the query, the contactUri locates the data
            Cursor c = getActivity().getContentResolver().query(contactUri, queryFields, null, null, null);

            try
            {
                //Verifies the results
                if (c.getCount() == 0)
                {
                    return;
                }

                //Finds the first column of where it is to find the name
                c.moveToFirst();
                String pic = c.getString(0);
                tTrip.settPic(pic);
                tPicButton.setText(pic);
            }
            finally
            {
                c.close();
            }
        }
        else if (requestCode == REQUEST_PHOTO)
        {
            updatePhotoView();
        }
    }
}