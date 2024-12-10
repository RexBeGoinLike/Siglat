package com.example.baguiosiglat;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

public class fragment_emergency extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_emergency, container, false);

        // Find the ImageButton by its ID
        ImageButton policeButton = view.findViewById(R.id.sos);

        // Set an OnClickListener
        policeButton.setOnClickListener(v -> {
            // Create an Intent to dial the emergency number
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:09053195976")); // Replace "911" with the appropriate number
            startActivity(intent);
        });

        return view;

    }
}