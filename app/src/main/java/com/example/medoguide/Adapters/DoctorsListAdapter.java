package com.example.medoguide.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.medoguide.Model.Doctors;
import com.example.medoguide.R;

import org.jitsi.meet.sdk.JitsiMeetActivity;
import org.jitsi.meet.sdk.JitsiMeetConferenceOptions;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class DoctorsListAdapter extends RecyclerView.Adapter<DoctorsListAdapter.DoctorsListViewHolder>{

    private final Context mContext;
    private final List<Doctors> doctors;

    public DoctorsListAdapter(Context mContext, List<Doctors> doctors) {
        this.mContext = mContext;
        this.doctors = doctors;
    }

    @NonNull
    @Override
    public DoctorsListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.doctors_list_layout, parent, false);
        return new DoctorsListAdapter.DoctorsListViewHolder(listItem);
    }

    @Override
    public void onBindViewHolder(@NonNull DoctorsListViewHolder holder, int position) {
        Doctors dData = doctors.get(position);
        holder.docName.setText(String.format("Dr. %s", dData.getName()));
        holder.docSpeciality.setText(dData.getSpeciality());
        holder.docExp.setText(String.format("%s yrs of experience",dData.getExperience()));

        holder.joinCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String inputPatient = dData.getPhoneNo();
                try {
                    JitsiMeetConferenceOptions options = new JitsiMeetConferenceOptions.Builder()
                            .setServerURL(new URL("https://meet.jit.si"))
                            .setRoom(inputPatient)
                            .setVideoMuted(true)
                            .setAudioMuted(true)
                            .setWelcomePageEnabled(false)
                            .build();
                    JitsiMeetActivity.launch(mContext, options);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                    Toast.makeText(mContext, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return doctors.size();
    }

    public class DoctorsListViewHolder extends RecyclerView.ViewHolder{

        public TextView docName, docSpeciality, docExp;
        public ImageView joinCall;

        public DoctorsListViewHolder(@NonNull View itemView) {
            super(itemView);

            docName = itemView.findViewById(R.id.name_doctor);
            docSpeciality = itemView.findViewById(R.id.speciality);
            docExp = itemView.findViewById(R.id.exp);
            joinCall = itemView.findViewById(R.id.join_call);

        }
    }
}
