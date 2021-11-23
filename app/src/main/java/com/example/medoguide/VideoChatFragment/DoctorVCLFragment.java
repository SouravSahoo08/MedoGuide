package com.example.medoguide.VideoChatFragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.medoguide.R;

import org.jitsi.meet.sdk.JitsiMeetActivity;
import org.jitsi.meet.sdk.JitsiMeetConferenceOptions;

import java.net.MalformedURLException;
import java.net.URL;


public class DoctorVCLFragment extends Fragment {

    EditText docNumber;
    Button joinCall;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_doctor_v_c_l, container, false);

        docNumber = view.findViewById(R.id.D_doc_num_txt);
        joinCall = view.findViewById(R.id.doc_join);

        joinCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String inputDoctor = docNumber.getText().toString();
                try {
                    JitsiMeetConferenceOptions options = new JitsiMeetConferenceOptions.Builder()
                            .setServerURL(new URL("https://meet.jit.si"))
                            .setRoom(inputDoctor)
                            .setAudioMuted(true)
                            .setVideoMuted(true)
                            .setWelcomePageEnabled(false)
                            .build();
                    JitsiMeetActivity.launch(getContext(), options);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                    Toast.makeText(getContext(), e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
        return view;
    }

}
