package com.example.medoguide.SettingsFragment;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.medoguide.DashBoard;
import com.example.medoguide.Model.Users;
import com.example.medoguide.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class PatientSettingFragment extends Fragment {
    CircleImageView profile, chngBtn;
    EditText patientName, patientAge, patientGender, patientBldGrp;
    TextView username;
    Button update;

    FirebaseAuth mAuth;
    DatabaseReference ref;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_patient_setting, container, false);

        mAuth = FirebaseAuth.getInstance();
        ref = FirebaseDatabase.getInstance().getReference().child("users").child("patients");

        profile = view.findViewById(R.id.set_p_profileImg);
        chngBtn = view.findViewById(R.id.set_p_changeImage);
        patientName = view.findViewById(R.id.set_p_patientName);
        patientAge = view.findViewById(R.id.set_p_patientAge);
        patientGender = view.findViewById(R.id.set_p_patientGender);
        patientBldGrp = view.findViewById(R.id.set_p_patientBldGrp);
        username = view.findViewById(R.id.set_p_username);
        update = view.findViewById(R.id.set_p_update);

        getPatInfo();

        patientName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() != 0) {
                    username.setText(String.format("Hi %s", patientName.getText().toString()));
                } else
                    username.setText("");
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        update.setOnClickListener(v -> {
            updateInfo();
        });
        return view;
    }

    private void updateInfo() {
        final String value = patientName.getText().toString();
        final String value1 = patientAge.getText().toString();
        final String value2 = patientGender.getText().toString();
        final String value3 = patientBldGrp.getText().toString();

        if (!value.isEmpty() || !value1.isEmpty() || !value2.isEmpty() || !value3.isEmpty()) {
            Map<String, Object> map = new HashMap<>();
            map.put("name", value);
            map.put("age", value1);
            map.put("speciality", value2);
            map.put("experience", value3);
            ref.child(mAuth.getCurrentUser().getUid()).updateChildren(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    Toast.makeText(getContext(), "Account updated", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getActivity(), DashBoard.class));
                    getActivity().finish();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getContext(), e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                    startActivity(new Intent(getActivity(), DashBoard.class));
                    getActivity().finish();
                }
            });
        } else {
            Toast.makeText(getContext(), "Some field is empty", Toast.LENGTH_LONG).show();
        }
    }

    private void getPatInfo() {
        ref.child(mAuth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Users users = snapshot.getValue(Users.class);
                patientName.setText(users.getName());
                patientAge.setText(users.getAge());
                patientGender.setText(users.getGender());
                patientBldGrp.setText(users.getBloodGrp());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}