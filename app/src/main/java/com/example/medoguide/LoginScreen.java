package com.example.medoguide;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.hbb20.CountryCodePicker;

import java.util.concurrent.TimeUnit;

public class LoginScreen extends AppCompatActivity {

    LinearLayout LL1, LL2;
    CountryCodePicker codePicker;
    EditText numberText, otpTxt;
    Button submitBtn;
    FirebaseAuth mAuth;
    String phNumber = null;
    String otpId = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();
        LL1 = findViewById(R.id.LL1);
        LL2 = findViewById(R.id.LL2);
        numberText = findViewById(R.id.number);
        codePicker = findViewById(R.id.countryCode);
        codePicker.registerCarrierNumberEditText(numberText);
        otpTxt = findViewById(R.id.Otp);
        submitBtn = findViewById(R.id.submit);

        updateUI(View.VISIBLE, View.GONE, "Send Otp");


        if (submitBtn.getText().toString().equals("Send Otp")) {
            submitBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    updateUI(View.GONE, View.VISIBLE, "Verify & Proceed");
                    if (!numberText.getText().toString().isEmpty()) {
                        phNumber = codePicker.getFullNumberWithPlus().trim().replace(" ","");
                        generateOtp(phNumber);
                    }
                }
            });

        } else {
            submitBtn.setOnClickListener(v -> {
                if (otpTxt.getText().toString().isEmpty()) {
                    Toast.makeText(this, "Please enter the otp", Toast.LENGTH_SHORT).show();
                } else if (otpTxt.getText().toString().length() != 6) {
                    Toast.makeText(this, "Invalid otp", Toast.LENGTH_SHORT).show();
                } else {
                    PhoneAuthCredential credential = PhoneAuthProvider.getCredential(otpId, otpTxt.getText().toString());
                    signInWithPhoneAuthCredential(credential);
                }
            });
        }
    }

    private void updateUI(int LL1Visbility, int LL2Visbility, String btnTxt) {
        LL1.setVisibility(LL1Visbility);
        LL2.setVisibility(LL2Visbility);
        submitBtn.setText(btnTxt);
    }

    private void generateOtp(String phoneNumber) {

        PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            @Override
            public void onVerificationCompleted(PhoneAuthCredential credential) {
                // This callback will be invoked in two situations:
                // 1 - Instant verification. In some cases the phone number can be instantly
                //     verified without needing to send or enter a verification code.
                // 2 - Auto-retrieval. On some devices Google Play services can automatically
                //     detect the incoming verification SMS and perform verification without
                //     user action.
                //Log.d(TAG, "onVerificationCompleted:" + credential);

                signInWithPhoneAuthCredential(credential);
            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                // This callback is invoked in an invalid request for verification is made,
                // for instance if the the phone number format is not valid.
                // Show a message and update the UI
                Toast.makeText(LoginScreen.this, e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                Log.d("message", e.getLocalizedMessage());
            }

            @Override
            public void onCodeSent(@NonNull String verificationId,
                                   @NonNull PhoneAuthProvider.ForceResendingToken token) {
                // The SMS verification code has been sent to the provided phone number, we
                // now need to ask the user to enter the code and then construct a credential
                // by combining the code with a verification ID.
                //Log.d(TAG, "onCodeSent:" + verificationId);

                // Save verification ID and resending token so we can use them later
                /*mVerificationId = verificationId;
                mResendToken = token;*/
                otpId = verificationId;
            }
        };
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber(phoneNumber)       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(this)                 // Activity (for callback binding)
                        .setCallbacks(mCallbacks)          // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);

    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
//                            Log.d(TAG, "signInWithCredential:success");
//
//                            FirebaseUser user = task.getResult().getUser();
                            // Update UI

                            Intent intent = new Intent(LoginScreen.this, RegisterActivity.class);
                            intent.putExtra("phoneNo", phNumber);
                            startActivity(intent);
                            finish();
                        } else {
                            // Sign in failed, display a message and update the UI
                            //Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Toast.makeText(LoginScreen.this, "Some Error Occured", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(FirebaseAuth.getInstance().getCurrentUser() != null) {
            startActivity(new Intent(LoginScreen.this, DashBoard.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP));
            finish();
        }
    }
}