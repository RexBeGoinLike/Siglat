package com.example.baguiosiglat.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.baguiosiglat.R;
import com.example.baguiosiglat.recyclerviewadapters.VolunteerPreviewAdapter;
import com.example.baguiosiglat.referenceclasses.Login;
import com.example.baguiosiglat.referenceclasses.PostData;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link fragment_profile#newInstance} factory method to
 * create an instance of this fragment.
 */
public class fragment_profile extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public fragment_profile() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment fragment_profile.
     */
    // TODO: Rename and change types and number of parameters
    public static fragment_profile newInstance(String param1, String param2) {
        fragment_profile fragment = new fragment_profile();
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
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        TextView logout = view.findViewById(R.id.log_out);
        TextView name = view.findViewById(R.id.user_name);
        TextView number = view.findViewById(R.id.phone_number);
        TextView email = view.findViewById(R.id.email_address);
        TextView permissionLevel = view.findViewById(R.id.user_perms_level);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        //Read data of user
        email.setText(user.getEmail());
        name.setText(user.getDisplayName());
        db.collection("users").document(user.getUid()).get()
                .addOnSuccessListener(documentSnapshot -> {
                    if(documentSnapshot.exists()){
                        number.setText(documentSnapshot.getString("Phone"));
                        switch (documentSnapshot.getString("Permission Level")){
                            case "1":
                                permissionLevel.setText(R.string.organization);
                                break;
                            case "0":
                                permissionLevel.setText(R.string.administrator);
                                break;
                            default:
                                permissionLevel.setVisibility(View.GONE);
                        }
                    }
                });


        //Owned posts manager
        ArrayList<PostData> posts = new ArrayList<>();

        //Filter through owned posts
        db.collection("posts").get().addOnSuccessListener(queryDocumentSnapshots -> {
            for(DocumentSnapshot snapshot : queryDocumentSnapshots){
                if(snapshot.exists()){
                    if(snapshot.getString("owner").equals(user.getUid())){
                        PostData post = snapshot.toObject(PostData.class);
                        posts.add(post);
                    }
                }

                RecyclerView postPreview = view.findViewById(R.id.organized_events_view);
                VolunteerPreviewAdapter adapter = new VolunteerPreviewAdapter(posts, getActivity(), getParentFragmentManager());
                postPreview.setAdapter(adapter);
                postPreview.setLayoutManager(new LinearLayoutManager(getContext()));
            }
        });

        ArrayList<PostData> joinedPosts = new ArrayList<>();
        //Filter through owned posts

        db.collection("users").document(user.getUid()).collection("joined_events")
                .get().addOnSuccessListener(queryDocumentSnapshots -> {

                    for (DocumentSnapshot snapshot : queryDocumentSnapshots){
                        db.collection("posts").get().addOnSuccessListener(queryDocumentSnapshot1 -> {

                            for (DocumentSnapshot postData : queryDocumentSnapshot1){
                                if(postData.getId().equals(snapshot.getId())){

                                    PostData post = postData.toObject(PostData.class);
                                    joinedPosts.add(post);

                                }

                            }

                            RecyclerView postPreview = view.findViewById(R.id.joined_events_view);
                            VolunteerPreviewAdapter adapter = new VolunteerPreviewAdapter(joinedPosts, getActivity(), getParentFragmentManager());
                            postPreview.setAdapter(adapter);
                            postPreview.setLayoutManager(new LinearLayoutManager(getContext()));

                        });
                    }

                });

        //Logout account
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getContext(), Login.class));
            }
        });

        return view;

    }
}