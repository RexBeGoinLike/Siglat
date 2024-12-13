package com.example.baguiosiglat;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

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

    private FirebaseFirestore db;
    private AppCompatButton signUp;
    private FirebaseUser user;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        
        View view = inflater.inflate(R.layout.fragment_event_details, container, false);

        TextView eventHeader, eventLocation, eventContact, eventDateTime, eventDescription;

        ImageButton delete, edit;

        db = FirebaseFirestore.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();

        delete = view.findViewById(R.id.delete_btn);
        edit = view.findViewById(R.id.edit_post_btn);

        eventHeader = view.findViewById(R.id.event_header);
        eventLocation = view.findViewById(R.id.event_location_text);
        eventContact = view.findViewById(R.id.event_contact);
        eventDateTime = view.findViewById(R.id.event_datetime);
        eventDescription = view.findViewById(R.id.event_description_text);
        signUp = view.findViewById(R.id.sign_up_event_btn);

        if (user.getUid().equals(getArguments().get("owner"))){
            signUp.setVisibility(View.VISIBLE);
            delete.setVisibility(View.VISIBLE);
            edit.setVisibility(View.VISIBLE);
        }

        if(getArguments() != null){
            eventHeader.setText(getArguments().getString("title"));
            eventLocation.setText(getArguments().getString("location"));
            eventContact.setText(String.format("%s\n%s\n%s", getArguments().getString("contactName"), getArguments().getString("contactNumber"), getArguments().getString("contactEmail")));
            eventDateTime.setText(String.format("on %s at %s", getArguments().getString("date"), getArguments().get("time")));
            eventDescription.setText(getArguments().getString("description"));
        }


        //Onclick listeners

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.collection("users").document(user.getUid()).collection("joined_events").document(getArguments().getString("postID")).get().addOnCompleteListener(task -> {
                    if(task.isSuccessful()) {
                        signUp.setText(R.string.cancel);
                        db.collection("users").document(user.getUid()).collection("joined_events").document(getArguments().getString("postID")).delete();
                        getActivity().getSupportFragmentManager().popBackStack();
                    }else{
                        Map<String, Object> notification = new HashMap<>();
                        notification.put("from", user.getDisplayName());
                        notification.put("email", user.getEmail());
                        Date currentDate = new Date();
                        SimpleDateFormat inputDate = new SimpleDateFormat("dd-MM-yyyy 'at' HH:mm", Locale.ENGLISH);
                        notification.put("message", user.getDisplayName() + " has joined your event titled " + getArguments().getString("title"));
                        notification.put("dateSent", inputDate.format(currentDate));
                        notification.put("notificationType", "1");
                        notification.put("read", false);

                        db.collection("users").document(user.getUid()).get()
                                .addOnSuccessListener(documentSnapshot -> {
                                    if(documentSnapshot.exists())
                                        notification.put("number", documentSnapshot.getString("Phone"));
                                });

                        db.collection("notifications").document(getArguments().getString("owner")).collection("userNotifications").add(notification);

                        Map<String, String> uid = new HashMap<>();
                        uid.put("UID", getArguments().getString("postID"));

                        db.collection("users").document(user.getUid()).collection("joined_events")
                                .document(getArguments().getString("postID")).set(uid);

                        getActivity().getSupportFragmentManager().popBackStack();
                    }
                });
            }
        });

        //Edit the post
        edit.setOnClickListener(v -> {
            Fragment fragment = new fragment_event_creator();
            fragment.setArguments(getArguments());
            getActivity().getSupportFragmentManager().beginTransaction().setReorderingAllowed(true)
                    .setCustomAnimations(R.anim.slide_up, R.anim.slide_down).replace(R.id.fragment_container, fragment).commit();
        });


        //Delete the post
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

                builder.setTitle("Delete Post")
                        .setMessage("Do you want to proceed with deletion?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                db.collection("posts").document(getArguments().getString("postID")).delete();
                                getActivity().getSupportFragmentManager().popBackStack();
                                dialog.dismiss();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });

                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });


        return view;
    }
}