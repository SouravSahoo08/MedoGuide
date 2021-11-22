package com.example.medoguide;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.medoguide.Model.Users;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DashBoard extends AppCompatActivity {

    TextView username;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);

        username = findViewById(R.id.name_of_user);
        Button button = findViewById(R.id.logout);
        Button reminder = findViewById(R.id.reminder);
        Button tracker = findViewById(R.id.tracker);
        Button chat = findViewById(R.id.chat);
        Button settings = findViewById(R.id.settings);

        FirebaseDatabase.getInstance().getReference().child("users").child("patients")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                /*for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                }*/
                    Users users = snapshot.getValue(Users.class);
                    username.setText(String.format("Hi %s", users.getName()));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        reminder.setOnClickListener(v -> {
            startActivity(new Intent(this, MedicineActivity.class));
            //finish();
        });

        tracker.setOnClickListener(v -> {
            startActivity(new Intent(this, TrackingActivity.class));
            //finish();
        });
        chat.setOnClickListener(v -> {
            //startActivity(new Intent(this, MedicineActivity.class));
            //finish();
        });
        settings.setOnClickListener(v -> {
            //startActivity(new Intent(this, MedicineActivity.class));
            //finish();
        });

        button.setOnClickListener(v -> {
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(DashBoard.this, LoginScreen.class));
            finish();
        });
    }
}