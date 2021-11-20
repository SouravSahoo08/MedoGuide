package com.example.medoguide;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.medoguide.CovidTrackAndVaxFragment.TrackFragment;
import com.example.medoguide.CovidTrackAndVaxFragment.VaxFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class TrackingActivity extends AppCompatActivity {

    private final int navTrack = R.id.nav_track;
    private final int navVax = R.id.nav_vax;
    private BottomNavigationView buttomNavigationView;
    private Fragment selectorFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tracking);

        buttomNavigationView =findViewById(R.id.buttom_navigation);
        buttomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()){
                    case navTrack:
                        selectorFragment = new TrackFragment();
                        break;

                    case navVax:
                        selectorFragment = new VaxFragment();
                        break;
                }

                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectorFragment).commit();
                return true;
            }
        });

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new TrackFragment()).commit();
    }
}