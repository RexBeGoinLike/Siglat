package com.example.baguiosiglat;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class VolunteerPreviewAdapter extends RecyclerView.Adapter<VolunteerPreviewAdapter.ViewHolder> {

    ArrayList<PostData> posts;
    Context context;

    public VolunteerPreviewAdapter(ArrayList<PostData> posts, Context context) {
        this.posts = posts;
        this.context = context;
    }

    @NonNull
    @Override
    public VolunteerPreviewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.volunteer_recyclerview_rows, parent, false);
        return new VolunteerPreviewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VolunteerPreviewAdapter.ViewHolder holder, int position) {
        holder.title.setText(posts.get(position).getTitle());
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        TextView title;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.event_title);
        }
    }
}
