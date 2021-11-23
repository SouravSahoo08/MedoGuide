package com.example.medoguide;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.medoguide.SettingsFragment.DoctorSettingFragment;
import com.example.medoguide.SettingsFragment.PatientSettingFragment;
import com.google.firebase.auth.FirebaseAuth;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        String isDoctor = getIntent().getStringExtra("isDoctor");

        if(isDoctor == null){
            /** user vcl fragment*/
            getSupportFragmentManager().beginTransaction().replace(R.id.settings_fragment,new PatientSettingFragment()).commit();
        }else{
            /** doctor vcl fragment*/
            getSupportFragmentManager().beginTransaction().replace(R.id.settings_fragment,new DoctorSettingFragment()).commit();
        }

        findViewById(R.id.set_backBtn).setOnClickListener(v->{
            startActivity(new Intent(this, DashBoard.class));
            finish();
        });

        findViewById(R.id.logOut).setOnClickListener(v->{
            new AlertDialog.Builder(this)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle("Logout")
                    .setMessage("Are you sure want to logout?")
                    .setNegativeButton("No", null)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            FirebaseAuth.getInstance().signOut();
                            startActivity(new Intent(SettingsActivity.this, LoginScreen.class));
                            finish();
                        }
                    }).show();
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(SettingsActivity.this, DashBoard.class));
        finish();
    }
}