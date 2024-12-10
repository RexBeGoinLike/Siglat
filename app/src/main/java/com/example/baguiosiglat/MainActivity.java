package com.example.baguiosiglat;

import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    private Timer timer;
    final private int interval = 3000;
    private void startRepeatingTask() {
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {

                FirebaseFirestore db = FirebaseFirestore.getInstance();
                db.collection("posts").get().addOnSuccessListener(queryDocumentSnapshots -> {
                    for (DocumentSnapshot snapshot : queryDocumentSnapshots){
                        SimpleDateFormat inputDate = new SimpleDateFormat("dd-MM-yyyy HH:mm");
                        String input = snapshot.getString("date") + " " + snapshot.getString("time");
                        try {
                            Date dateOfPost = inputDate.parse(input);
                            Date currentDate = new Date();
                            if(currentDate.after(dateOfPost)){
                                db.collection("posts").document(snapshot.getId()).delete();
                            }
                        } catch (ParseException e) {
                            throw new RuntimeException(e);
                        }
                    }

                });
            }
        }, 0, interval);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (timer != null) {
            timer.cancel();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        Fragment news = new fragment_news();
        Fragment services = new fragment_services();
        Fragment volunteer = new fragment_volunteer();
        Fragment profile = new fragment_profile();
        Fragment emergency = new fragment_emergency();

        ;
        //Load default fragment
        loadFragment(news);
        //Bottom Navigation
        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);

        bottomNav.setOnItemSelectedListener(item -> {

            Fragment fragment = null;
            switch (item.getItemId()){
                case R.id.page_1:
                    fragment = news;
                    break;
                case R.id.page_2:
                    fragment = services;
                    break;
                case R.id.page_3:
                    fragment = emergency;
                    break;
                case R.id.page_4:
                    fragment = volunteer;
                    break;
                case R.id.page_5:
                    fragment = profile;
                    break;
                default:
            }

            return replaceFragment(fragment);

        });

        startRepeatingTask();

    }


    public void loadFragment(Fragment fragment){
        if (fragment != null) {
            getSupportFragmentManager().beginTransaction()
                    .setReorderingAllowed(true)
                    .add(R.id.fragment_container, fragment)
                    .commit();
        }
    }

    public boolean replaceFragment(Fragment fragment){
        if(fragment != null){
            getSupportFragmentManager().beginTransaction().setCustomAnimations(
                    R.anim.slide_up, R.anim.slide_down
            ).setReorderingAllowed(true)
                    .replace(R.id.fragment_container, fragment).commit();
            return true;
        }
        return false;
    }



}