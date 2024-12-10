package com.example.baguiosiglat;

import android.animation.Animator;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.Locale;

public class VolunteerPreviewAdapter extends RecyclerView.Adapter<VolunteerPreviewAdapter.ViewHolder> {

    ArrayList<PostData> posts;
    Context context;
    
    FragmentManager fragmentManager;

    public VolunteerPreviewAdapter(ArrayList<PostData> posts, Context context, FragmentManager fragmentManager) {
        this.posts = posts;
        this.context = context;
        this.fragmentManager = fragmentManager;
    }
    
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.volunteer_recyclerview_rows, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        //Prioritize dates closer to the current date



        posts.sort((p1, p2) -> {
            SimpleDateFormat inputDateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
            Date p1Date, p2Date;
            try {
                p1Date = inputDateFormatter.parse(p1.getDate());
                p2Date = inputDateFormatter.parse(p2.getDate());
                return p1Date.compareTo(p2Date);

            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        });


        holder.title.setText(posts.get(position).getTitle());
        Bundle bundle = getBundle(position);
        holder.owner.setText(posts.get(position).getContactName());

        SimpleDateFormat inputDate = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
        SimpleDateFormat formatDate = new SimpleDateFormat("MMM d yyyy", Locale.ENGLISH);

        String textDate = "";
        try {
            Date date = inputDate.parse(posts.get(position).getDate());
            textDate = formatDate.format(date);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        holder.date.setText(textDate);

        holder.viewPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Fragment fragment = new fragment_event_details();
                fragment.setArguments(bundle);
                
                fragmentManager.beginTransaction().setCustomAnimations(R.anim.slide_up, R.anim.slide_down).replace(R.id.fragment_container, fragment).setReorderingAllowed(true).addToBackStack(null).commit();
            }
        });
    }

    @NonNull
    private Bundle getBundle(int position) {
        Bundle bundle = new Bundle();
        bundle.putString("title", posts.get(position).getTitle());
        bundle.putString("description", posts.get(position).getDescription());
        bundle.putString("date", posts.get(position).getDate());
        bundle.putString("time", posts.get(position).getTime());
        bundle.putString("location", posts.get(position).getLocation());
        bundle.putString("contactName", posts.get(position).getContactName());
        bundle.putString("contactNumber", posts.get(position).getContactNumber());
        bundle.putString("contactEmail", posts.get(position).getContactEmail());
        bundle.putString("postID", posts.get(position).getPostID());
        bundle.putString("owner", posts.get(position).getOwner());
        return bundle;
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        TextView title, owner, date;
        AppCompatButton viewPost;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.event_title);
            viewPost = itemView.findViewById(R.id.view_full_details);
            owner = itemView.findViewById(R.id.event_owner_preview);
            date = itemView.findViewById(R.id.event_date_preview);
        }
    }
}
