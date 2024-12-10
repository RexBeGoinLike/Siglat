package com.example.baguiosiglat;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.firestore.FirebaseFirestore;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link fragment_event_details#newInstance} factory method to
 * create an instance of this fragment.
 */
public class fragment_event_details extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public fragment_event_details() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment fragment_event_details.
     */
    // TODO: Rename and change types and number of parameters
    public static fragment_event_details newInstance(String param1, String param2) {
        fragment_event_details fragment = new fragment_event_details();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        
        View view = inflater.inflate(R.layout.fragment_event_details, container, false);

        TextView eventHeader, eventLocation, eventContact, eventDateTime, eventDescription;
        Button signUp;

        eventHeader = view.findViewById(R.id.event_header);
        eventLocation = view.findViewById(R.id.event_location_text);
        eventContact = view.findViewById(R.id.event_contact);
        eventDateTime = view.findViewById(R.id.event_datetime);
        eventDescription = view.findViewById(R.id.event_description_text);

        if(getArguments() != null){
            eventHeader.setText(getArguments().getString("title"));
            eventLocation.setText(getArguments().getString("location"));
            eventContact.setText(String.format("%s - %s - %s", getArguments().getString("contactName"), getArguments().getString("contactNumber"), getArguments().getString("contactEmail")));
            eventDateTime.setText(String.format("%s - %s", getArguments().getString("date"), getArguments().get("time")));
            eventDescription.setText(getArguments().getString("description"));
        }


        return view;
    }
}