package com.example.baguiosiglat.fragments;

import android.app.DatePickerDialog;
import android.icu.util.Calendar;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.baguiosiglat.R;
import com.example.baguiosiglat.referenceclasses.PostData;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

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

        eventTitle = view.findViewById(R.id.event_title);
        eventDate = view.findViewById(R.id.event_date);
        eventTime = view.findViewById(R.id.event_time);
        eventLocation = view.findViewById(R.id.event_location);
        eventDesc = view.findViewById(R.id.event_description);
        contactName = view.findViewById(R.id.contact_name);
        contactNum = view.findViewById(R.id.contact_number);
        contactEmail = view.findViewById(R.id.contact_email);
        createEvent = view.findViewById(R.id.create_event);

        if(getArguments() != null){
            eventTitle.setText(getArguments().getString("title"));
            eventDate.setText(getArguments().getString("date"));
            eventTime.setText(getArguments().getString("time"));
            eventLocation.setText(getArguments().getString("location"));
            eventDesc.setText(getArguments().getString("description"));
            contactName.setText(getArguments().getString("contactName"));
            contactNum.setText(getArguments().getString("contactNumber"));
            contactEmail.setText(getArguments().getString("contactEmail"));
            createEvent.setText("Edit");
        }

        //Date and time picker dialogues
        eventDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar c = Calendar.getInstance();

                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        getActivity(), (view1, year1, monthOfYear, dayOfMonth) -> eventDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year1), year, month, day);

                c.set(year, month, day);
                datePickerDialog.getDatePicker().setMinDate(c.getTimeInMillis());


                c.add(Calendar.YEAR, 1);
                datePickerDialog.getDatePicker().setMaxDate(c.getTimeInMillis());

                datePickerDialog.show();
            }
        });


        createEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String title, date, time, location, desc, name, num, email;

                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

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
                        && !name.isEmpty() && !num.isEmpty() && !email.isEmpty()){

                    PostData post = new PostData("", title, date, time, location, desc, name, num, email, user.getUid());

                    if(getArguments() != null){
                        post.setPostID(getArguments().getString("postID"));
                        db.collection("posts").document(getArguments().getString("postID")).set(post);
                    }else {
                        db.collection("posts").add(post).addOnSuccessListener(documentReference -> {
                            String id = documentReference.getId();
                            post.setPostID(id);
                            documentReference.update("postID", id);
                        });
                    }

                    getActivity().getSupportFragmentManager().popBackStack();

                }else {
                    Toast.makeText(getActivity(), "Do not leave empty fields.",
                            Toast.LENGTH_SHORT).show();
                }


            }
        });



        return view;
    }


}