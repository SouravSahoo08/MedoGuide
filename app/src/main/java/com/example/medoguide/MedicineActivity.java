package com.example.medoguide;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.medoguide.MedicineFragment.MedicineListFragment;

public class MedicineActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicine);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_Container, new MedicineListFragment()).commit();

        /**
         * code for back button to be written to move back to @DashBoard.java
         * */
        findViewById(R.id.med_rem_backBtn).setOnClickListener(v->{
            startActivity(new Intent(this, DashBoard.class));
            finish();
        });

    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(MedicineActivity.this, DashBoard.class));
        finish();
    }
}