package com.example.medoguide;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

/*
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;
*/

public class RegisterActivity extends AppCompatActivity {

    LinearLayout doctorsMenu, patientMenu;
    CircleImageView profile, chngBtn;
    Spinner spinner;
    EditText doctorsName, doctorsAge, doctorsGender, doctorsSpl, doctorsExp;
    EditText patientName, patientAge, patientGender, patientBldGrp;
    TextView username;
    Button registerBtn;

    FirebaseAuth mAuth;
    DatabaseReference ref;
    String categoryType, phoneNo;
    Map<String, Object> map = new HashMap<>();
    private Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Intent intent = getIntent();
        phoneNo = intent.getStringExtra("phoneNo");

        mAuth = FirebaseAuth.getInstance();
        ref = FirebaseDatabase.getInstance().getReference().child("users");
        doctorsMenu = findViewById(R.id.doctorsMenu);
        patientMenu = findViewById(R.id.patientMenu);
        profile = findViewById(R.id.profileImg);
        chngBtn = findViewById(R.id.changeImage);
        spinner = findViewById(R.id.spinner);
        doctorsName = findViewById(R.id.doctorsName);
        doctorsAge = findViewById(R.id.doctorsAge);
        doctorsGender = findViewById(R.id.doctorsGender);
        doctorsSpl = findViewById(R.id.doctorsSpl);
        doctorsExp = findViewById(R.id.doctorsExp);
        patientName = findViewById(R.id.patientName);
        patientAge = findViewById(R.id.patientAge);
        patientGender = findViewById(R.id.patientGender);
        patientBldGrp = findViewById(R.id.patientBldGrp);
        username = findViewById(R.id.username);
        registerBtn = findViewById(R.id.register);

        ArrayAdapter<CharSequence> arrayAdapter = ArrayAdapter.createFromResource(this, R.array.category, android.R.layout.simple_spinner_item);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                categoryType = parent.getItemAtPosition(position).toString();

                if (categoryType.equalsIgnoreCase("doctor")) {
                    doctorsMenu.setVisibility(View.VISIBLE);
                    patientMenu.setVisibility(View.GONE);
                    registerBtn.setEnabled(true);
                } else if (categoryType.equalsIgnoreCase("patient")) {
                    patientMenu.setVisibility(View.VISIBLE);
                    doctorsMenu.setVisibility(View.GONE);
                    registerBtn.setEnabled(true);
                } else {
                    patientMenu.setVisibility(View.GONE);
                    doctorsMenu.setVisibility(View.GONE);
                    registerBtn.setEnabled(false);
                }

                /*chngBtn.setOnClickListener(v -> {
                    CropImage.activity().setCropShape(CropImageView.CropShape.RECTANGLE).start(RegisterActivity.this);
                });*/

                doctorsName.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        if (s.length() != 0)
                            username.setText(String.format("Hi %s", doctorsName.getText().toString()));
                        else
                            username.setText("Hi User");
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });

                patientName.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        if (s.length() != 0)
                            username.setText(String.format("Hi %s", doctorsName.getText().toString()));
                        else
                            username.setText("Hi User");
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });

                registerBtn.setOnClickListener(v -> {

                    if (imageUri != null) {
                        //storage reference code to be written to store the profile image
                    }
                    map.clear();
                    map.put("phoneNo", phoneNo);
                    if (categoryType.equalsIgnoreCase("doctor")) {
                        map.put("name", doctorsName.getText().toString());
                        map.put("age", doctorsAge.getText().toString());
                        map.put("gender", doctorsGender.getText().toString());
                        map.put("speciality", doctorsSpl.getText().toString());
                        map.put("experience", doctorsExp.getText().toString());
                        ref.child("doctors").child(mAuth.getCurrentUser().getUid()).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Toast.makeText(RegisterActivity.this, "Succesful", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(RegisterActivity.this, DashBoard.class));
                                finish();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(RegisterActivity.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    } else if (categoryType.equalsIgnoreCase("patient")) {
                        map.put("name", patientName.getText().toString());
                        map.put("age", patientAge.getText().toString());
                        map.put("gender", patientGender.getText().toString());
                        map.put("bloodGrp", patientBldGrp.getText().toString());
                        ref.child("patients").child(mAuth.getCurrentUser().getUid()).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Toast.makeText(RegisterActivity.this, "Successful", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(RegisterActivity.this, DashBoard.class));
                                finish();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(RegisterActivity.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                patientMenu.setVisibility(View.GONE);
                doctorsMenu.setVisibility(View.GONE);
                registerBtn.setEnabled(false);
            }
        });

    }

    /*@Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (result != null) {
                imageUri = result.getUri();
            }
            profile.setImageURI(imageUri);
        }
    }*/

    /*@Override
    protected void onStart() {
        super.onStart();
        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            startActivity(new Intent(RegisterActivity.this, DashBoard.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP));
            finish();
        }
    }*/
}