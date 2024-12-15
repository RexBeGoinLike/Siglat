package com.example.baguiosiglat.recyclerviewadapters;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.example.baguiosiglat.R;
import com.example.baguiosiglat.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class ParticipantsAdapter extends RecyclerView.Adapter<ParticipantsAdapter.ViewWrapper>{


    private ArrayList<User> participatedUsers;
    private boolean show = false;

    private Bundle postArgs;

    public ParticipantsAdapter(ArrayList<User> participatedUsers){
        this.participatedUsers = participatedUsers;
    }

    @NonNull
    @Override
    public ParticipantsAdapter.ViewWrapper onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.participants_recyclerview_rows, parent, false);
        return new ParticipantsAdapter.ViewWrapper(view);
    }

    public void showButton(boolean show){
        this.show = show;
    }

    public void setPostArgs(Bundle postArgs){
        this.postArgs = postArgs;
    }

    @Override
    public void onBindViewHolder(@NonNull ParticipantsAdapter.ViewWrapper holder, int position) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        holder.name.setText(participatedUsers.get(position).getName());
        holder.email.setText(participatedUsers.get(position).getEmail());
        holder.phone.setText(participatedUsers.get(position).getPhoneNumber());

        if(show)
            holder.remove.setVisibility(View.VISIBLE);
        else
            holder.remove.setVisibility(View.GONE);

        holder.remove.setOnClickListener(v -> {
            participatedUsers.remove(position);
            db.collection("posts").document(postArgs.getString("postID")).collection("participants").document(postArgs.getString("uid")).delete();
            db.collection("users").document(postArgs.getString("uid")).collection("joined_events").document(postArgs.getString("postID")).delete();
            notifyItemRemoved(position);
        });
    }

    @Override
    public int getItemCount() {
        return participatedUsers.size();
    }

    public static class ViewWrapper extends RecyclerView.ViewHolder{

        TextView name, email, phone;
        ImageButton remove;

        public ViewWrapper(@NonNull View itemView) {
            super(itemView);
            remove = itemView.findViewById(R.id.remove_button);
            name = itemView.findViewById(R.id.name_notification);
            email = itemView.findViewById(R.id.email_notification);
            phone = itemView.findViewById(R.id.phone_notification);
        }
    }
}
