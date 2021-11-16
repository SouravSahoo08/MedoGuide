package com.example.medoguide;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class DashBoard extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);
        Button button = findViewById(R.id.logout);
        button.setOnClickListener(v->{
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(DashBoard.this,LoginScreen.class));
            finish();
        });
    }
}