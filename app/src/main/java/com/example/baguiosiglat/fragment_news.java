package com.example.baguiosiglat;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class fragment_news extends Fragment {

    private WebView facebookWebView;
    private FrameLayout fullscreenContainer;
    private View customView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news, container, false);

        facebookWebView = view.findViewById(R.id.newsView1);
        fullscreenContainer = view.findViewById(R.id.fullscreenContainer);

        WebSettings webSettings = facebookWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setUseWideViewPort(true);

        // Enable mixed content mode for Android 7.0 (API 24) and above
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }


        ArrayList<GovernmentDepartment> services;services = new ArrayList<>();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        //Populate array with the data for each government office
        db.collection("services").get().addOnSuccessListener(queryDocumentSnapshots -> {
            for(DocumentSnapshot snapshot : queryDocumentSnapshots){
                if(snapshot.exists()){
                    GovernmentDepartment service = snapshot.toObject(GovernmentDepartment.class);
                    services.add(service);
                    RecyclerView servicesView = view.findViewById(R.id.services_view);
                    ServicesAdapter adapter = new ServicesAdapter(getActivity(), services);
                    servicesView.setAdapter(adapter);
                    servicesView.setLayoutManager(new LinearLayoutManager(getContext()));
                }
            }
        }).addOnFailureListener(e -> {
            Log.w("Error", "Document does not exist");
        });

        facebookWebView.setWebViewClient(new WebViewClient());
        facebookWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onShowCustomView(View view, CustomViewCallback callback) {
                fullscreenContainer.setVisibility(View.VISIBLE);
                fullscreenContainer.addView(view);
                customView = view;
            }

            @Override
            public void onHideCustomView() {
                if (customView != null) {
                    fullscreenContainer.removeView(customView);
                    customView = null;
                    fullscreenContainer.setVisibility(View.GONE);
                }
            }
        });

        // HTML string with an iframe
        String htmlData = "<!DOCTYPE html>" +
                "<html>" +
                "<head>" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">" +
                "</head>" +
                "<body>" +
                "    <div style=\"width:100%; max-width:560px; margin:auto;\">" +
                "        <iframe " +
                "            src=\"https://www.facebook.com/plugins/video.php?href=https://www.facebook.com/100063733010388/videos/1624475718501248\"" +
                "            width=\"100%\" height=\"315\"" +
                "            style=\"border:none;overflow:hidden\"" +
                "            scrolling=\"no\"" +
                "            frameborder=\"0\"" +
                "            allow=\"autoplay; clipboard-write; encrypted-media; picture-in-picture; web-share\"" +
                "            allowfullscreen>" +
                "        </iframe>" +
                "    </div>" +
                "</body>" +
                "</html>";

        // Load the HTML string in WebView
        facebookWebView.loadDataWithBaseURL(null, htmlData, "text/html", "UTF-8", null);

        return view;
    }
}
