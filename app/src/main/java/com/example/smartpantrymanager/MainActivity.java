package com.example.smartpantrymanager;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private DatabaseHelper databaseHelper;

    private Button btnMyPantry;
    private Button btnSuggestedRecipes;
    private Button btnSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        databaseHelper = new DatabaseHelper(this);
        databaseHelper.getWritableDatabase();

        EdgeToEdge.enable(this);

        setContentView(R.layout.activity_main);

        btnMyPantry = findViewById(R.id.btnMyPantry);
        btnSuggestedRecipes =
                findViewById(R.id.btnSuggestedRecipes);
        btnSettings =
                findViewById(R.id.btnSettings);

        // Open My Pantry
        btnMyPantry.setOnClickListener(v -> {

            Intent intent =
                    new Intent(
                            MainActivity.this,
                            PantryActivity.class
                    );

            startActivity(intent);
        });

        // Open Suggested Recipes
        btnSuggestedRecipes.setOnClickListener(v -> {

            Intent intent =
                    new Intent(
                            MainActivity.this,
                            SuggestedRecipesActivity.class
                    );

            startActivity(intent);
        });

        // Open Settings
        btnSettings.setOnClickListener(v -> {

            Intent intent =
                    new Intent(
                            MainActivity.this,
                            SettingsActivity.class
                    );

            startActivity(intent);
        });

        ViewCompat.setOnApplyWindowInsetsListener(
                findViewById(R.id.main),
                (v, insets) -> {

                    Insets systemBars =
                            insets.getInsets(
                                    WindowInsetsCompat.Type.systemBars()
                            );

                    v.setPadding(
                            systemBars.left,
                            systemBars.top,
                            systemBars.right,
                            systemBars.bottom
                    );

                    return insets;
                }
        );
    }
}