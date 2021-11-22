package com.example.medoguide.MedicineFragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.medoguide.Adapters.MedicineListAdapter;
import com.example.medoguide.Model.MedicineDataFB;
import com.example.medoguide.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MedicineListFragment extends Fragment {

    private RecyclerView medicineList;
    private MedicineListAdapter medicineListAdapter;
    private List<MedicineDataFB> medicines;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_medicine_list, container, false);

        FloatingActionButton fabAddMedicine = view.findViewById(R.id.fab_add_task);
        fabAddMedicine.setOnClickListener(v -> {
            requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_Container, new AddMedicineFragment()).commit();
        });

        medicineList = view.findViewById(R.id.medicine_list);
        medicineList.setHasFixedSize(false);
        medicineList.setLayoutManager(new LinearLayoutManager(getContext()));
        medicines = new ArrayList<>();
        medicineListAdapter = new MedicineListAdapter(getContext(), medicines);
        medicineList.setAdapter(medicineListAdapter);
        getMedicines();
        return view;
    }

    private void getMedicines() {

        FirebaseDatabase.getInstance().getReference().child("medicineList").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        medicines.clear();
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            MedicineDataFB mDB = dataSnapshot.getValue(MedicineDataFB.class);
                            medicines.add(mDB);
                        }
                        medicineListAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

    }

}