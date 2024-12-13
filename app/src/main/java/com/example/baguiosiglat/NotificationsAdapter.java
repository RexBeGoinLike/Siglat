package com.example.baguiosiglat;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class NotificationsAdapter extends RecyclerView.Adapter<NotificationsAdapter.ViewWrapper>{


    private ArrayList<Notification> notifications;

    public NotificationsAdapter(ArrayList<Notification> notifications){
        this.notifications = notifications;
    }

    @NonNull
    @Override
    public NotificationsAdapter.ViewWrapper onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.notifications_recyclerview_rows, parent, false);
        return new NotificationsAdapter.ViewWrapper(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationsAdapter.ViewWrapper holder, int position) {
        holder.name.setText(notifications.get(position).getFrom());
        holder.email.setText(notifications.get(position).getEmail());
        holder.phone.setText(notifications.get(position).getNumber());
        holder.message.setText(notifications.get(position).getMessage());
        holder.dateTime.setText(String.format(notifications.get(position).getDateSent()));

    }

    @Override
    public int getItemCount() {
        return notifications.size();
    }

    public static class ViewWrapper extends RecyclerView.ViewHolder{

        TextView name, email, phone, message, dateTime;

        public ViewWrapper(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.name_notification);
            email = itemView.findViewById(R.id.email_notification);
            phone = itemView.findViewById(R.id.phone_notification);
            message = itemView.findViewById(R.id.message_notification);
            dateTime = itemView.findViewById(R.id.date_notification);
        }
    }
}
