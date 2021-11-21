package com.example.medoguide;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class DashBoard extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);
        Button button = findViewById(R.id.logout);
        Button reminder = findViewById(R.id.reminder);
        Button tracker = findViewById(R.id.tracker);
        Button chat = findViewById(R.id.chat);
        Button settings = findViewById(R.id.settings);

        reminder.setOnClickListener(v -> {
            startActivity(new Intent(this, MedicineActivity.class));
            finish();
        });

        tracker.setOnClickListener(v -> {
            startActivity(new Intent(this, TrackingActivity.class));
            finish();
        });
        chat.setOnClickListener(v -> {
            //startActivity(new Intent(this, MedicineActivity.class));
            finish();
        });
        settings.setOnClickListener(v -> {
            //startActivity(new Intent(this, MedicineActivity.class));
            finish();
        });

        button.setOnClickListener(v -> {
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(DashBoard.this, LoginScreen.class));
            finish();
        });
    }
}