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

import java.util.ArrayList;

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
        holder.title.setText(posts.get(position).getTitle());
        Bundle bundle = getBundle(position);
        holder.viewPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Fragment fragment = new fragment_event_details();
                fragment.setArguments(bundle);
                
                fragmentManager.beginTransaction().setCustomAnimations(R.anim.slide_up, R.anim.slide_down).add(R.id.fragment_container, fragment).setReorderingAllowed(true).commit();
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
        bundle.putString("contactName", posts.get(position).getContactName());
        bundle.putString("contactNumber", posts.get(position).getContactNumber());
        bundle.putString("contactEmail", posts.get(position).getContactEmail());
        return bundle;
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        TextView title;
        AppCompatButton viewPost;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.event_title);
            viewPost = itemView.findViewById(R.id.view_full_details);
        }
    }
}
