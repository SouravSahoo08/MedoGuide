package com.example.medoguide;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.medoguide.VideoChatFragment.DoctorVCLFragment;
import com.example.medoguide.VideoChatFragment.PatientVCLFragment;

public class VideoChatActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_chat);

        String isDoctor = getIntent().getStringExtra("isDoctor");

        if(isDoctor == null){
            /** user vcl fragment*/
            getSupportFragmentManager().beginTransaction().replace(R.id.type_of_user_chat_fragment,new PatientVCLFragment()).commit();
        }else{
            /** doctor vcl fragment*/
            getSupportFragmentManager().beginTransaction().replace(R.id.type_of_user_chat_fragment,new DoctorVCLFragment()).commit();
        }

        findViewById(R.id.v_backBtn).setOnClickListener(v->{
            startActivity(new Intent(this, DashBoard.class));
            finish();
        });

    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(VideoChatActivity.this, DashBoard.class));
        finish();
    }


}