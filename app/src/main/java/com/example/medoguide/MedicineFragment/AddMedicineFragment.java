package com.example.medoguide.MedicineFragment;


import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.medoguide.Notification.Notification_reciever;
import com.example.medoguide.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;


public class AddMedicineFragment extends Fragment {

    private EditText medicineName;
    private TextView timeTxt;
    private EditText noOfDoses;
    private Spinner doseTypeList;
    private int hour;
    private int minute;
    private String doseType;

    private FirebaseAuth mUser;
    private DatabaseReference medRef;
    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_medicine, container, false);

        mUser = FirebaseAuth.getInstance();
        medRef = FirebaseDatabase.getInstance().getReference().child("medicineList");

        medicineName = view.findViewById(R.id.medicine_name);
        timeTxt = view.findViewById(R.id.time);
        noOfDoses = view.findViewById(R.id.doses);
        doseTypeList = view.findViewById(R.id.dose_type);
        setCurrentTime();
        setSpinner();

        timeTxt.setOnClickListener(v -> {
            showTimePicker();
        });

        doseTypeList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                doseType = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        FloatingActionButton fabAddMedicine = view.findViewById(R.id.fab_add_task2);
        fabAddMedicine.setOnClickListener(v->{
            String mName = medicineName.getText().toString();
            String mTime = timeTxt.getText().toString();
            String mDoseno = noOfDoses.getText().toString();

            Map<String, Object> map = new HashMap<>();
            map.put("medicineName",mName);
            map.put("doseType", doseType);
            map.put("time",mTime);
            map.put("no_of_doses", mDoseno);
            map.put("interval", "everyday");

            /** save medicine list to Firebase database*/
            medRef.child(mUser.getCurrentUser().getUid()).child(mName).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {

                    /** set reminder for the medicine*/
                    Calendar calendar = Calendar.getInstance();
                    Intent intent = new Intent(requireActivity().getApplicationContext(), Notification_reciever.class);
                    intent.putExtra("medicineName", mName);
                    intent.putExtra("no_of_doses", mDoseno);
                    intent.putExtra("type", doseType);
                    PendingIntent pendingIntent = PendingIntent.getBroadcast(requireActivity().getApplicationContext(),
                            801,intent,PendingIntent.FLAG_UPDATE_CURRENT);

                    calendar.set(Calendar.HOUR_OF_DAY, hour);
                    calendar.set(Calendar.MINUTE, minute);
                    calendar.set(Calendar.SECOND, 0);
                    calendar.set(Calendar.MILLISECOND, 0);
                    long alarm_time = calendar.getTimeInMillis();
                    AlarmManager alarmManager = (AlarmManager) requireContext().getSystemService(Context.ALARM_SERVICE);
                    assert alarmManager != null;
                    alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,alarm_time,AlarmManager.INTERVAL_DAY,pendingIntent);

                    Toast.makeText(getContext(), "Alarm set", Toast.LENGTH_SHORT).show();
                    getActivity().getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragment_Container,new MedicineListFragment()).commit();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getContext(), e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                }
            });
        });

        return view;
    }

    private void setCurrentTime() {
        Calendar mCurrentTime = Calendar.getInstance();
        hour = mCurrentTime.get(Calendar.HOUR_OF_DAY);
        minute = mCurrentTime.get(Calendar.MINUTE);

        timeTxt.setText(String.format(Locale.getDefault(), "%d:%d", hour, minute));
    }

    private void showTimePicker() {
        Calendar mCurrentTime = Calendar.getInstance();
        hour = mCurrentTime.get(Calendar.HOUR_OF_DAY);
        minute = mCurrentTime.get(Calendar.MINUTE);
        TimePickerDialog mTimePicker;
        mTimePicker = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                hour = selectedHour;
                minute = selectedMinute;
                timeTxt.setText(String.format(Locale.getDefault(), "%d:%d", selectedHour, selectedMinute));
            }
        }, hour, minute, false);//No 24 hour time
        mTimePicker.setTitle("Select Time");
        mTimePicker.show();
    }

    private void setSpinner() {
        ArrayAdapter<CharSequence> arrayAdapter = ArrayAdapter.createFromResource(getContext(), R.array.medications_shape_array, android.R.layout.simple_spinner_item);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        doseTypeList.setAdapter(arrayAdapter);
    }

    /*private void saveMedicineData(View view){

        String mName = medicineName.getText().toString();
        String mTime = timeTxt.getText().toString();
        String mDoseno = noOfDoses.getText().toString();

        Map<String, Object> map = new HashMap<>();
        map.put("doseType", doseType);
        map.put("time",mTime);
        map.put("no_of_doses", mDoseno);

        *//** save medicine list to Firebase database*//*
        medRef.child(mUser.getCurrentUser().getUid()).child(mName).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                *//** set reminder for the medicine*//*
                Calendar calendar = Calendar.getInstance();
                Intent intent = new Intent(requireActivity().getApplicationContext(), Notification_reciever.class);
                intent.putExtra("medicineName", mName);
                intent.putExtra("no_of_doses", mDoseno);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(requireActivity().getApplicationContext(),
                        801,intent,PendingIntent.FLAG_UPDATE_CURRENT);

                calendar.set(Calendar.HOUR_OF_DAY, hour);
                calendar.set(Calendar.MINUTE, minute);
                calendar.set(Calendar.SECOND, 0);
                calendar.set(Calendar.MILLISECOND, 0);
                long alarm_time = calendar.getTimeInMillis();
                AlarmManager alarmManager = (AlarmManager) requireContext().getSystemService(Context.ALARM_SERVICE);
                assert alarmManager != null;
                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,alarm_time,AlarmManager.INTERVAL_DAY,pendingIntent);

                Toast.makeText(getContext(), "Alarm set", Toast.LENGTH_SHORT).show();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_Container,new MedicineListFragment()).commit();
            }
        });

    }*/

}