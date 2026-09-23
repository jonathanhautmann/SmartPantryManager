package com.example.smartpantrymanager;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class SuggestedRecipesActivity extends AppCompatActivity {

    private RecyclerView recyclerViewRecipes;
    private TextView tvNoRecipes;

    private DatabaseHelper databaseHelper;
    private RecipeAdapter recipeAdapter;

    private List<Recipe> suggestedRecipes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(
                R.layout.activity_suggested_recipes
        );

        recyclerViewRecipes =
                findViewById(
                        R.id.recyclerViewRecipes
                );

        tvNoRecipes =
                findViewById(
                        R.id.tvNoRecipes
                );

        databaseHelper =
                new DatabaseHelper(this);

        suggestedRecipes =
                new ArrayList<>();

        recyclerViewRecipes.setLayoutManager(
                new LinearLayoutManager(this)
        );

        recipeAdapter =
                new RecipeAdapter(
                        this,
                        suggestedRecipes
                );

        recyclerViewRecipes.setAdapter(
                recipeAdapter
        );

        loadSuggestedRecipes();
    }

    private void loadSuggestedRecipes() {

        suggestedRecipes.clear();

        List<PantryItem> pantryItems =
                databaseHelper.getAllPantryItems();

        List<Recipe> allRecipes =
                databaseHelper.getAllRecipes();

        for (Recipe recipe : allRecipes) {

            List<RecipeIngredient> ingredients =
                    databaseHelper.getRecipeIngredients(
                            recipe.getId()
                    );

            if (canMakeRecipe(
                    ingredients,
                    pantryItems
            )) {

                suggestedRecipes.add(recipe);
            }
        }

        recipeAdapter.notifyDataSetChanged();

        if (suggestedRecipes.isEmpty()) {

            recyclerViewRecipes.setVisibility(
                    RecyclerView.GONE
            );

            tvNoRecipes.setVisibility(
                    TextView.VISIBLE
            );

        } else {

            recyclerViewRecipes.setVisibility(
                    RecyclerView.VISIBLE
            );

            tvNoRecipes.setVisibility(
                    TextView.GONE
            );
        }
    }

    private boolean canMakeRecipe(
            List<RecipeIngredient> recipeIngredients,
            List<PantryItem> pantryItems) {

        for (RecipeIngredient requiredIngredient :
                recipeIngredients) {

            boolean ingredientAvailable = false;

            String requiredName =
                    normalizeIngredientName(
                            requiredIngredient
                                    .getIngredientName()
                    );

            String requiredUnit =
                    normalizeUnit(
                            requiredIngredient.getUnit()
                    );

            double requiredQuantity =
                    requiredIngredient
                            .getRequiredQuantity();

            for (PantryItem pantryItem :
                    pantryItems) {

                String pantryName =
                        normalizeIngredientName(
                                pantryItem.getName()
                        );

                String pantryUnit =
                        normalizeUnit(
                                pantryItem.getUnit()
                        );

                if (requiredName.equals(
                        pantryName
                )) {

                    if (requiredUnit.equals(
                            pantryUnit
                    )) {

                        if (pantryItem.getQuantity()
                                >= requiredQuantity) {

                            ingredientAvailable = true;
                            break;
                        }
                    }
                }
            }

            if (!ingredientAvailable) {

                return false;
            }
        }

        return true;
    }

    private String normalizeIngredientName(
            String name) {

        if (name == null) {
            return "";
        }

        String normalized =
                name.trim()
                        .toLowerCase(
                                Locale.ROOT
                        );

        if (normalized.equals("tomatoes")) {
            return "tomato";
        }

        if (normalized.equals("potatoes")) {
            return "potato";
        }

        if (normalized.equals("onions")) {
            return "onion";
        }

        if (normalized.equals("carrots")) {
            return "carrot";
        }

        if (normalized.equals("bananas")) {
            return "banana";
        }

        if (normalized.equals("eggs")) {
            return "egg";
        }

        if (normalized.equals("cloves")) {
            return "clove";
        }

        return normalized;
    }

    private String normalizeUnit(String unit) {

        if (unit == null) {
            return "";
        }

        String normalized =
                unit.trim()
                        .toLowerCase(
                                Locale.ROOT
                        );

        if (normalized.equals("grams")) {
            return "g";
        }

        if (normalized.equals("gram")) {
            return "g";
        }

        if (normalized.equals("millilitres")) {
            return "ml";
        }

        if (normalized.equals("milliliters")) {
            return "ml";
        }

        if (normalized.equals("millilitre")) {
            return "ml";
        }

        if (normalized.equals("milliliter")) {
            return "ml";
        }

        if (normalized.equals("pieces")) {
            return "piece";
        }

        if (normalized.equals("piece")) {
            return "piece";
        }

        if (normalized.equals("slices")) {
            return "slice";
        }

        if (normalized.equals("slice")) {
            return "slice";
        }

        if (normalized.equals("tablespoons")) {
            return "tablespoon";
        }

        if (normalized.equals("tablespoon")) {
            return "tablespoon";
        }

        if (normalized.equals("cloves")) {
            return "clove";
        }

        if (normalized.equals("clove")) {
            return "clove";
        }

        if (normalized.equals("cans")) {
            return "can";
        }

        if (normalized.equals("can")) {
            return "can";
        }

        return normalized;
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (databaseHelper != null &&
                recipeAdapter != null) {

            loadSuggestedRecipes();
        }
    }
}