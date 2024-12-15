package com.example.baguiosiglat.recyclerviewadapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.baguiosiglat.R;
import com.example.baguiosiglat.referenceclasses.GovernmentDepartment;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ServicesAdapter extends RecyclerView.Adapter<ServicesAdapter.MyViewHolder> {

    Context context;
    ArrayList<GovernmentDepartment> services;
    public ServicesAdapter(Context context, ArrayList<GovernmentDepartment> services){
        this.services = services;
        this.context = context;
    }

    @NonNull
    @Override
    public ServicesAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.services_recyclerview_rows, parent, false);
        return new ServicesAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ServicesAdapter.MyViewHolder holder, int position) {
        holder.header.setText(services.get(position).getName());
        holder.description.setText(services.get(position).getDescription());
        Picasso.get().load(services.get(position).getLogoLink()).into(holder.logo);
        String url = services.get(position).getSiteLink();
        holder.linkToWebsite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
            }
        });
    }

    @Override
    public int getItemCount() {
        return services.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        ImageView logo;
        TextView header, description;
        CardView linkToWebsite;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            linkToWebsite = itemView.findViewById(R.id.link_to_website);
            logo = itemView.findViewById(R.id.logo_department);
            header = itemView.findViewById(R.id.department_header);
            description = itemView.findViewById(R.id.department_description);
        }
    }
}
