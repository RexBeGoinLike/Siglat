package com.example.baguiosiglat;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.icu.util.Calendar;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TimePicker;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.sql.Time;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link fragment_event_creator#newInstance} factory method to
 * create an instance of this fragment.
 */
public class fragment_event_creator extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public fragment_event_creator() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment fragment_event_creator.
     */
    // TODO: Rename and change types and number of parameters
    public static fragment_event_creator newInstance(String param1, String param2) {
        fragment_event_creator fragment = new fragment_event_creator();
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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_event_creator, container, false);

        TextInputEditText eventTitle, eventDate, eventTime, eventLocation, eventDesc, contactName, contactNum, contactEmail;

        AppCompatButton createEvent;

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        eventTitle = view.findViewById(R.id.event_title);
        eventDate = view.findViewById(R.id.event_date);
        eventTime = view.findViewById(R.id.event_time);
        eventLocation = view.findViewById(R.id.event_location);
        eventDesc = view.findViewById(R.id.event_description);
        contactName = view.findViewById(R.id.contact_name);
        contactNum = view.findViewById(R.id.contact_number);
        contactEmail = view.findViewById(R.id.contact_email);
        createEvent = view.findViewById(R.id.create_event);

        //Date and time picker dialogues
        eventDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();

                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        getActivity(), (view1, year1, monthOfYear, dayOfMonth) -> eventDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year1), year, month, day);
                datePickerDialog.show();
            }
        });

        eventTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);

                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(getActivity(), (timePicker, selectedHour, selectedMinute) -> eventTime.setText(selectedHour + ":" + selectedMinute), hour, minute, true);
                mTimePicker.show();
            }
        });


        createEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String title, date, time, location, desc, name, num, email;

                title = String.valueOf(eventTitle.getText());
                date = String.valueOf(eventDate.getText());
                time = String.valueOf(eventTime.getText());
                location = String.valueOf(eventLocation.getText());
                desc = String.valueOf(eventDesc.getText());
                name = String.valueOf(contactName.getText());
                num = String.valueOf(contactNum.getText());
                email = String.valueOf(contactEmail.getText());

                if(!title.isEmpty() && !date.isEmpty() && !time.isEmpty()
                        && !location.isEmpty() && !desc.isEmpty()
                        & !name.isEmpty() && !num.isEmpty() && !email.isEmpty()){
                    PostData post = new PostData(title, date, time, location, desc, name, num, email, user.getUid());

                    db.collection("posts").document().set(post);

                    ((MainActivity) getActivity()).replaceFragment(new fragment_volunteer());

                }
            }
        });



        return view;
    }


}