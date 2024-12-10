package com.example.baguiosiglat;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

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
        loadFragment(emergency);

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