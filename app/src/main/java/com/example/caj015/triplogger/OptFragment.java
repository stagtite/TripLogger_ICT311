package com.example.caj015.triplogger;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.caj015.triplogger.database.TripBaseHelper;
import com.example.caj015.triplogger.database.TripDbSchema;

import static android.R.attr.id;

public class OptFragment extends Fragment implements AdapterView.OnItemSelectedListener
{

    private Opt oOpt;
    private TripLab Lab;

    private EditText oNameField;
    private EditText oIdField;
    private EditText oEmailField;
    private EditText oCommentField;

    private Spinner oGenderSpinner;

    private Button oFinishedButton;

    //onCreate Setup
    @Override //How do i get it to save
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        oOpt = new Opt();


        //oOpt.setoName(findVal("NAME"));

        //Integer oIdent = (Integer) oOpt.getoIdent();
        //oOpt = TripLab.get(getActivity()).getoOpt(oIdent);
    }

    /*public String findVal(String column)
    {
        SQLiteDatabase oDatabase = new TripBaseHelper(this.getActivity()).getReadableDatabase();
        int id = 1;

        String result = Lab.queryOpts("TripDbSchema.OptTable.Cols." + column + " = ?", new String[] {String.valueOf(id)}).toString();

        return result;
    }*/

    @Override
    public void onPause()
    {
        super.onPause();

        TripLab.get(getActivity()).updateOpt(oOpt);
    }

    //View setup
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View v = inflater.inflate(R.layout.fragment_opt, container, false);

        //Name field
        oNameField = (EditText)v.findViewById(R.id.opt_name);
        oNameField.setText(oOpt.getoName());
        oNameField.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {
                // This space intentionally left blank
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                oOpt.setoName(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s)
            {
                // This space intentionally left blank
            }
        });

        //ID Field
        oIdField = (EditText) v.findViewById(R.id.opt_id);
        oIdField.setText(oOpt.getoId());
        oIdField.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {
                // This space intentionally left blank
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                oOpt.setoId(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s)
            {
                // This space intentionally left blank
            }
        });

        //Gender spinner - Adapted from Android API: https://developer.android.com/guide/topics/ui/controls/spinner.html
        oGenderSpinner = (Spinner) v.findViewById(R.id.opt_gender);

        ArrayAdapter<CharSequence> oAdapter = ArrayAdapter.createFromResource(this.getActivity(), R.array.gender_array, android.R.layout.simple_spinner_item);
        oAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        oGenderSpinner.setAdapter(oAdapter);

        //Email Field
        oEmailField = (EditText) v.findViewById(R.id.opt_email);
        oEmailField.setText(oOpt.getoEmail());
        oEmailField.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                oOpt.setoEmail(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s)
            {

            }
        });

        //Comment Field
        oCommentField = (EditText) v.findViewById(R.id.opt_comment);
        oCommentField.setText(oOpt.getoComment());
        oCommentField.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                oOpt.setoComment(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s)
            {

            }
        });

        //Finished Button
        oFinishedButton = (Button) v.findViewById(R.id.opt_fin);
        oFinishedButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                getActivity().finish();
            }
        });

        return v;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (resultCode != Activity.RESULT_OK)
        {
            return;
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}