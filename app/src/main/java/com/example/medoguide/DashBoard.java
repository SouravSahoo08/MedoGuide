package com.example.medoguide;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.medoguide.Model.Doctors;
import com.example.medoguide.Model.Users;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DashBoard extends AppCompatActivity {

    TextView username;
    private FirebaseUser firebaseUser;
    private DatabaseReference ref;

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

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        ref = FirebaseDatabase.getInstance().getReference().child("users");

        ref.child("patients").child(firebaseUser.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                try {
                    Users users = snapshot.getValue(Users.class);
                    username.setText(String.format("Hi %s", users.getName()));
                } catch (Exception e) {
                    e.printStackTrace();
                    ref.child("doctors").child(firebaseUser.getUid()).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            Doctors doctors = snapshot.getValue(Doctors.class);
                            username.setText(String.format("Hi %s", doctors.getName()));
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
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