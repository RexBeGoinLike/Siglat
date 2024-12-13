package com.example.baguiosiglat;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.baguiosiglat.referenceclasses.GovernmentDepartment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link fragment_notifications#newInstance} factory method to
 * create an instance of this fragment.
 */
public class fragment_notifications extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public fragment_notifications() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment fragment_notifications.
     */
    // TODO: Rename and change types and number of parameters
    public static fragment_notifications newInstance(String param1, String param2) {
        fragment_notifications fragment = new fragment_notifications();
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

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_notifications, container, false);


        ArrayList<Notification> notifications = new ArrayList<>();
        FirebaseFirestore db = FirebaseFirestore.getInstance();


        db.collection("notifications").document(user.getUid()).collection("userNotifications").get().addOnSuccessListener(queryDocumentSnapshots -> {
            for(DocumentSnapshot snapshot : queryDocumentSnapshots){
                if(snapshot.exists()){
                    Notification notification = snapshot.toObject(Notification.class);
                    notifications.add(notification);
                    RecyclerView notifView = view.findViewById(R.id.notification_view);
                    NotificationsAdapter adapter = new NotificationsAdapter(notifications);
                    notifView.setAdapter(adapter);
                   notifView.setLayoutManager(new LinearLayoutManager(getContext()));
                }
            }
        }).addOnFailureListener(e -> {
            Log.w("Error", "Document does not exist");
        });

        return view;
    }
}