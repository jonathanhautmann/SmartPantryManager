package com.example.smartpantrymanager;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SettingsActivity extends AppCompatActivity {

    private EditText etProfileName;
    private EditText etPreferredUnit;
    private Switch switchExpiryAlerts;
    private Button btnSaveSettings;

    private SharedPreferences sharedPreferences;

    private static final String PREFS_NAME =
            "SmartPantrySettings";

    private static final String KEY_PROFILE_NAME =
            "profile_name";

    private static final String KEY_EXPIRY_ALERTS =
            "expiry_alerts";

    private static final String KEY_PREFERRED_UNIT =
            "preferred_unit";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(
                R.layout.activity_settings
        );

        etProfileName =
                findViewById(
                        R.id.etProfileName
                );

        etPreferredUnit =
                findViewById(
                        R.id.etPreferredUnit
                );

        switchExpiryAlerts =
                findViewById(
                        R.id.switchExpiryAlerts
                );

        btnSaveSettings =
                findViewById(
                        R.id.btnSaveSettings
                );

        sharedPreferences =
                getSharedPreferences(
                        PREFS_NAME,
                        MODE_PRIVATE
                );

        loadSettings();

        btnSaveSettings.setOnClickListener(
                v -> saveSettings()
        );
    }

    private void loadSettings() {

        String profileName =
                sharedPreferences.getString(
                        KEY_PROFILE_NAME,
                        ""
                );

        boolean expiryAlerts =
                sharedPreferences.getBoolean(
                        KEY_EXPIRY_ALERTS,
                        false
                );

        String preferredUnit =
                sharedPreferences.getString(
                        KEY_PREFERRED_UNIT,
                        ""
                );

        etProfileName.setText(
                profileName
        );

        switchExpiryAlerts.setChecked(
                expiryAlerts
        );

        etPreferredUnit.setText(
                preferredUnit
        );
    }

    private void saveSettings() {

        String profileName =
                etProfileName
                        .getText()
                        .toString()
                        .trim();

        String preferredUnit =
                etPreferredUnit
                        .getText()
                        .toString()
                        .trim();

        boolean expiryAlerts =
                switchExpiryAlerts.isChecked();

        if (profileName.isEmpty()) {

            etProfileName.setError(
                    "Please enter your name"
            );

            etProfileName.requestFocus();

            return;
        }

        if (preferredUnit.isEmpty()) {

            etPreferredUnit.setError(
                    "Please enter a preferred unit"
            );

            etPreferredUnit.requestFocus();

            return;
        }

        SharedPreferences.Editor editor =
                sharedPreferences.edit();

        editor.putString(
                KEY_PROFILE_NAME,
                profileName
        );

        editor.putBoolean(
                KEY_EXPIRY_ALERTS,
                expiryAlerts
        );

        editor.putString(
                KEY_PREFERRED_UNIT,
                preferredUnit
        );

        editor.apply();

        Toast.makeText(
                this,
                "Settings saved successfully",
                Toast.LENGTH_SHORT
        ).show();
    }
}