package com.example.baguiosiglat;

import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link fragment_volunteer#newInstance} factory method to
 * create an instance of this fragment.
 */
public class fragment_volunteer extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public fragment_volunteer() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment fragment_volunteer.
     */
    // TODO: Rename and change types and number of parameters
    public static fragment_volunteer newInstance(String param1, String param2) {
        fragment_volunteer fragment = new fragment_volunteer();
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
        View view = inflater.inflate(R.layout.fragment_volunteer, container, false);
        AppCompatButton createEvent = view.findViewById(R.id.create_event_btn);

        ArrayList<PostData> posts = new ArrayList<>();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        //Populate array with the data for each government office
        db.collection("posts").get().addOnSuccessListener(queryDocumentSnapshots -> {
            for(DocumentSnapshot snapshot : queryDocumentSnapshots){
                if(snapshot.exists()){

                    PostData post = snapshot.toObject(PostData.class);
                    posts.add(post);

                    RecyclerView postPreview = view.findViewById(R.id.volunteer_view);
                    VolunteerPreviewAdapter adapter = new VolunteerPreviewAdapter(posts, getActivity(), getParentFragmentManager());
                    postPreview.setAdapter(adapter);
                    postPreview.setLayoutManager(new LinearLayoutManager(getContext()));
                }
            }
        }).addOnFailureListener(e -> {
            Log.w("Error", "Document does not exist");
        });

        createEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().setReorderingAllowed(true).addToBackStack(null)
                        .setCustomAnimations(R.anim.slide_up, R.anim.slide_down)
                        .replace(R.id.fragment_container, new fragment_event_creator()).commit();
            }
        });

        return view;
    }
}