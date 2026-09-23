package com.example.smartpantrymanager;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class PantryActivity extends AppCompatActivity {

    private RecyclerView recyclerViewPantry;
    private Button btnAddIngredient;

    private DatabaseHelper databaseHelper;
    private PantryAdapter pantryAdapter;

    private List<PantryItem> pantryItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_pantry);

        recyclerViewPantry =
                findViewById(R.id.recyclerViewPantry);

        btnAddIngredient =
                findViewById(R.id.btnAddIngredient);

        databaseHelper =
                new DatabaseHelper(this);

        pantryItems =
                new ArrayList<>();

        recyclerViewPantry.setLayoutManager(
                new LinearLayoutManager(this)
        );

        pantryAdapter =
                new PantryAdapter(
                        this,
                        pantryItems
                );

        recyclerViewPantry.setAdapter(
                pantryAdapter
        );

        loadPantryItems();

        btnAddIngredient.setOnClickListener(v -> {

            Intent intent = new Intent(
                    PantryActivity.this,
                    AddEditIngredientActivity.class
            );

            startActivity(intent);
        });
    }

    private void loadPantryItems() {

        pantryItems.clear();

        pantryItems.addAll(
                databaseHelper.getAllPantryItems()
        );

        pantryAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onResume() {

        super.onResume();

        if (databaseHelper != null &&
                pantryAdapter != null) {

            loadPantryItems();
        }
    }
}