package com.example.medoguide.VideoChatFragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.medoguide.Adapters.DoctorsListAdapter;
import com.example.medoguide.Model.Doctors;
import com.example.medoguide.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class PatientVCLFragment extends Fragment {

    private RecyclerView listOfDoctors;
    private DoctorsListAdapter doctorsListAdapter;
    private List<Doctors> doctorsList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_patient_v_c_l, container, false);

        listOfDoctors = view.findViewById(R.id.list_of_doctors);
        doctorsList = new ArrayList<>();
        doctorsListAdapter = new DoctorsListAdapter(getContext(), doctorsList);
        listOfDoctors.setLayoutManager(new LinearLayoutManager(getContext()));
        listOfDoctors.setHasFixedSize(false);
        listOfDoctors.setAdapter(doctorsListAdapter);

        getDoctorList();
        return view;
    }

    private void getDoctorList() {

        FirebaseDatabase.getInstance().getReference().child("users").child("doctors")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        doctorsList.clear();
                        for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                            Doctors doctors = dataSnapshot.getValue(Doctors.class);
                            doctorsList.add(doctors);
                        }
                        doctorsListAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

    }
}