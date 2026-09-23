package com.example.smartpantrymanager;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class AddEditIngredientActivity extends AppCompatActivity {

    private EditText etIngredientName;
    private EditText etQuantity;
    private EditText etUnit;
    private EditText etExpiryDate;

    private Button btnSaveIngredient;
    private Button btnCancel;

    private TextView tvAddEditTitle;

    private DatabaseHelper databaseHelper;

    // -1 means a new ingredient is being added
    private int ingredientId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(
                R.layout.activity_add_edit_ingredient
        );

        // Connect XML views
        etIngredientName =
                findViewById(R.id.etIngredientName);

        etQuantity =
                findViewById(R.id.etQuantity);

        etUnit =
                findViewById(R.id.etUnit);

        etExpiryDate =
                findViewById(R.id.etExpiryDate);

        btnSaveIngredient =
                findViewById(R.id.btnSaveIngredient);

        btnCancel =
                findViewById(R.id.btnCancel);

        tvAddEditTitle =
                findViewById(R.id.tvAddEditTitle);

        /*
         * The database helper is not created here.
         *
         * This allows the Add/Edit screen to open
         * without first opening the SQLite database.
         */

        checkEditMode();

        btnSaveIngredient.setOnClickListener(
                v -> saveIngredient()
        );

        btnCancel.setOnClickListener(
                v -> finish()
        );
    }

    /**
     * Checks whether the activity is being used
     * to add a new ingredient or edit an existing one.
     */
    private void checkEditMode() {

        Intent intent = getIntent();

        if (intent.hasExtra("ingredient_id")) {

            // Edit mode
            ingredientId =
                    intent.getIntExtra(
                            "ingredient_id",
                            -1
                    );

            tvAddEditTitle.setText(
                    "Edit Ingredient"
            );

            btnSaveIngredient.setText(
                    "Update Ingredient"
            );

            // Get existing ingredient data
            String name =
                    intent.getStringExtra(
                            "ingredient_name"
                    );

            double quantity =
                    intent.getDoubleExtra(
                            "ingredient_quantity",
                            0
                    );

            String unit =
                    intent.getStringExtra(
                            "ingredient_unit"
                    );

            String expiry =
                    intent.getStringExtra(
                            "ingredient_expiry"
                    );

            // Display existing values
            if (name != null) {
                etIngredientName.setText(name);
            }

            etQuantity.setText(
                    String.valueOf(quantity)
            );

            if (unit != null) {
                etUnit.setText(unit);
            }

            if (expiry != null) {
                etExpiryDate.setText(expiry);
            }
        }
    }

    /**
     * Validates the form and either adds
     * or updates the ingredient.
     */
    private void saveIngredient() {

        String name =
                etIngredientName
                        .getText()
                        .toString()
                        .trim();

        String quantityText =
                etQuantity
                        .getText()
                        .toString()
                        .trim();

        String unit =
                etUnit
                        .getText()
                        .toString()
                        .trim();

        String expiryDate =
                etExpiryDate
                        .getText()
                        .toString()
                        .trim();

        // Validate ingredient name
        if (TextUtils.isEmpty(name)) {

            etIngredientName.setError(
                    "Please enter an ingredient name"
            );

            etIngredientName.requestFocus();

            return;
        }

        // Validate quantity
        if (TextUtils.isEmpty(quantityText)) {

            etQuantity.setError(
                    "Please enter a quantity"
            );

            etQuantity.requestFocus();

            return;
        }

        // Validate unit
        if (TextUtils.isEmpty(unit)) {

            etUnit.setError(
                    "Please enter a unit"
            );

            etUnit.requestFocus();

            return;
        }

        // Validate expiry date
        if (!TextUtils.isEmpty(expiryDate)) {

            if (!isValidExpiryDate(expiryDate)) {

                etExpiryDate.setError(
                        "Use format: dd MMMM yyyy"
                );

                etExpiryDate.requestFocus();

                return;
            }
        }

        // Convert quantity to a number
        double quantity;

        try {

            quantity =
                    Double.parseDouble(
                            quantityText
                    );

        } catch (NumberFormatException e) {

            etQuantity.setError(
                    "Please enter a valid number"
            );

            etQuantity.requestFocus();

            return;
        }

        // Quantity must be greater than zero
        if (quantity <= 0) {

            etQuantity.setError(
                    "Quantity must be greater than zero"
            );

            etQuantity.requestFocus();

            return;
        }

        // Create PantryItem
        PantryItem pantryItem =
                new PantryItem(
                        ingredientId,
                        name,
                        quantity,
                        unit,
                        expiryDate
                );

        /*
         * Create the database helper only when
         * the user actually saves the information.
         */
        databaseHelper =
                new DatabaseHelper(this);

        // Decide between Add and Update
        if (ingredientId == -1) {

            addIngredient(pantryItem);

        } else {

            updateIngredient(pantryItem);
        }
    }

    /**
     * Checks whether the expiry date follows
     * the format dd MMMM yyyy.
     */
    private boolean isValidExpiryDate(
            String expiryDate) {

        SimpleDateFormat dateFormat =
                new SimpleDateFormat(
                        "dd MMMM yyyy",
                        Locale.ENGLISH
                );

        dateFormat.setLenient(false);

        try {

            dateFormat.parse(expiryDate);

            return true;

        } catch (ParseException e) {

            return false;
        }
    }

    /**
     * Adds a new ingredient to the database.
     */
    private void addIngredient(
            PantryItem pantryItem) {

        long result =
                databaseHelper.addPantryItem(
                        pantryItem
                );

        if (result != -1) {

            Toast.makeText(
                    this,
                    "Ingredient added successfully",
                    Toast.LENGTH_SHORT
            ).show();

            finish();

        } else {

            Toast.makeText(
                    this,
                    "Failed to add ingredient",
                    Toast.LENGTH_SHORT
            ).show();
        }
    }

    /**
     * Updates an existing ingredient in the database.
     */
    private void updateIngredient(
            PantryItem pantryItem) {

        int result =
                databaseHelper.updatePantryItem(
                        pantryItem
                );

        if (result > 0) {

            Toast.makeText(
                    this,
                    "Ingredient updated successfully",
                    Toast.LENGTH_SHORT
            ).show();

            finish();

        } else {

            Toast.makeText(
                    this,
                    "Failed to update ingredient",
                    Toast.LENGTH_SHORT
            ).show();
        }
    }
}