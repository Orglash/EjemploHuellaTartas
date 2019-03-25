package com.an.biometric.sample;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.an.biometric.BiometricCallback;
import com.an.biometric.BiometricManager;
import com.google.android.material.textfield.TextInputLayout;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements BiometricCallback {

    private EditText name, lastname, age, competition;
    private TextInputLayout layoutName, layoutLastname, layoutAge, layoutCompetition;
    private Button sign;
    private CheckBox veteran;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inicializeUI();

        veteran.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                checkParticipation();
            }
        });
        sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            if (validateName() && validateLastname() && validateAge() && validateCompetition()) {
                    new BiometricManager.BiometricBuilder(MainActivity.this)
                            .setTitle(getString(R.string.biometric_title))
                            .setSubtitle(getString(R.string.biometric_subtitle))
                            .setDescription(getString(R.string.biometric_description))
                            .setNegativeButtonText(getString(R.string.biometric_negative_button_text))
                            .build()
                            .authenticate(MainActivity.this);
                }

            }
        });
    }

    private void inicializeUI(){
        name = findViewById(R.id.name);
        lastname = findViewById(R.id.lastname);
        age = findViewById(R.id.age);
        layoutName = findViewById(R.id.layout_name);
        layoutLastname = findViewById(R.id.layout_lastname);
        layoutAge = findViewById(R.id.layout_age);
        sign = findViewById(R.id.sign);
        veteran = findViewById(R.id.veteran);
        competition = findViewById(R.id.contest);
        layoutCompetition = findViewById(R.id.layout_contest);
        layoutCompetition.setVisibility(View.GONE);
    }

    private boolean validateName(){
        String naming = name.getText().toString().trim();
        if (naming.isEmpty()) {
            Toast.makeText(getApplicationContext(), R.string.name_error, Toast.LENGTH_SHORT).show();
            return false;
        } else {
            layoutName.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validateLastname(){
        String naming = lastname.getText().toString().trim();
        if (naming.isEmpty()) {
            Toast.makeText(getApplicationContext(), R.string.lastname_error, Toast.LENGTH_SHORT).show();

            return false;
        } else {
            layoutName.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validateAge(){
        String naming = age.getText().toString().trim();

        if (naming.isEmpty()) {
            Toast.makeText(getApplicationContext(), getString(R.string.age_format_error), Toast.LENGTH_SHORT).show();
            return false;
        } else {
            if(Integer.parseInt(naming) < 18){
                Toast.makeText(getApplicationContext(), getString(R.string.age_error), Toast.LENGTH_SHORT).show();
                return false;
            }else{
                layoutAge.setErrorEnabled(false);
            }
        }

        return true;
    }

    private boolean validateCompetition(){
        String naming = competition.getText().toString().trim();

        if((naming.isEmpty() && veteran.isChecked())){
            Toast.makeText(getApplicationContext(), getString(R.string.contest_error), Toast.LENGTH_SHORT).show();
            return false;
        }else{
            layoutCompetition.setErrorEnabled(false);
        }

        return true;
    }

    private void checkParticipation(){
        if(veteran.isChecked()){
            layoutCompetition.setVisibility(View.VISIBLE);
        }else{
            layoutCompetition.setVisibility(View.GONE);
        }
    }

    @Override
    public void onSdkVersionNotSupported() {
        Toast.makeText(getApplicationContext(), getString(R.string.biometric_error_sdk_not_supported), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onBiometricAuthenticationNotSupported() {
        Toast.makeText(getApplicationContext(), getString(R.string.biometric_error_hardware_not_supported), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onBiometricAuthenticationNotAvailable() {
        Toast.makeText(getApplicationContext(), getString(R.string.biometric_error_fingerprint_not_available), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onBiometricAuthenticationPermissionNotGranted() {
        Toast.makeText(getApplicationContext(), getString(R.string.biometric_error_permission_not_granted), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onBiometricAuthenticationInternalError(String error) {
        Toast.makeText(getApplicationContext(), error, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onAuthenticationFailed() {
//        Toast.makeText(getApplicationContext(), getString(R.string.biometric_failure), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onAuthenticationCancelled() {
        Toast.makeText(getApplicationContext(), getString(R.string.biometric_cancelled), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onAuthenticationSuccessful() {
        Toast.makeText(getApplicationContext(), getString(R.string.biometric_success), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onAuthenticationHelp(int helpCode, CharSequence helpString) {
//        Toast.makeText(getApplicationContext(), helpString, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onAuthenticationError(int errorCode, CharSequence errString) {
//        Toast.makeText(getApplicationContext(), errString, Toast.LENGTH_LONG).show();
    }
}
