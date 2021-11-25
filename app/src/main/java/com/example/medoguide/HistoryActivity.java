package com.example.medoguide;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.medoguide.Adapters.HistoryAdapter;
import com.example.medoguide.Model.HistoryData;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class HistoryActivity extends AppCompatActivity {

    private RecyclerView medicineHistoryList;
    private HistoryAdapter historyAdapter;
    private List<HistoryData> medData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        medicineHistoryList = findViewById(R.id.history_recyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setStackFromEnd(true);
        linearLayoutManager.setReverseLayout(true);
        medicineHistoryList.setLayoutManager(linearLayoutManager);
        medicineHistoryList.setHasFixedSize(false);
        medData = new ArrayList<>();
        historyAdapter = new HistoryAdapter(this, medData);
        medicineHistoryList.setAdapter(historyAdapter);

        getHistory();

        findViewById(R.id.history_backBtn).setOnClickListener(v -> {
            startActivity(new Intent(this, MedicineActivity.class));
            finish();
        });
    }

    private void getHistory() {
        FirebaseUser mUser = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("history");
        ref.child(mUser.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    HistoryData data = dataSnapshot.getValue(HistoryData.class);
                    medData.add(data);
                }
                historyAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(HistoryActivity.this, MedicineActivity.class));
        finish();
    }
}