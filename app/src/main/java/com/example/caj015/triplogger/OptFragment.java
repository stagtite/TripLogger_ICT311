package com.example.caj015.triplogger;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.UUID;

public class OptFragment extends Fragment implements AdapterView.OnItemSelectedListener
{
    private static final String ARG_OPT_ID = "opt_id";

    private Opt oOpt;

    private EditText oNameField;
    private EditText oIdField;
    private EditText oEmailField;
    private EditText oCommentField;

    private Spinner oGenderSpinner;

    private Button oFinishedButton;

    public static OptFragment newInstance(UUID oId)
    {
        return new OptFragment();
        /*Bundle args = new Bundle();
        args.putSerializable(ARG_OPT_ID, oId);

        OptFragment fragment = new OptFragment();
        fragment.setArguments(args);
        return fragment;*/
    }

    //onCreate Setup
    @Override //CRASHES BECAUSE I'M TRYING TO PASS IT A UUID THAT IS BLANK, NEEDS TO BE CALLED ON NEWINSTANCE
    public void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        //UUID optId = oOpt.getoOpt(); //(UUID) getArguments().getSerializable(ARG_OPT_ID);
        //oOpt = TripLab.get(getActivity()).getoOpt(optId);
    }

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