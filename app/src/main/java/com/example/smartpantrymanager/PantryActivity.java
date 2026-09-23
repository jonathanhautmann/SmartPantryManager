package com.example.smartpantrymanager;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class PantryActivity extends AppCompatActivity {

    private RecyclerView recyclerViewPantry;
    private Button btnAddIngredient;
    private TextView tvEmptyPantry;

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

        tvEmptyPantry =
                findViewById(R.id.tvEmptyPantry);

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

        // Show an appropriate message when the pantry is empty
        if (pantryItems.isEmpty()) {

            recyclerViewPantry.setVisibility(
                    RecyclerView.GONE
            );

            tvEmptyPantry.setVisibility(
                    TextView.VISIBLE
            );

        } else {

            recyclerViewPantry.setVisibility(
                    RecyclerView.VISIBLE
            );

            tvEmptyPantry.setVisibility(
                    TextView.GONE
            );
        }
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