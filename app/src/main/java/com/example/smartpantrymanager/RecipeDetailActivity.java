package com.example.smartpantrymanager;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class RecipeDetailActivity extends AppCompatActivity {

    private TextView tvRecipeDetailName;
    private TextView tvRecipeIngredients;
    private TextView tvRecipeInstructions;

    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(
                R.layout.activity_recipe_detail
        );

        tvRecipeDetailName =
                findViewById(
                        R.id.tvRecipeDetailName
                );

        tvRecipeIngredients =
                findViewById(
                        R.id.tvRecipeIngredients
                );

        tvRecipeInstructions =
                findViewById(
                        R.id.tvRecipeInstructions
                );

        databaseHelper =
                new DatabaseHelper(this);

        int recipeId =
                getIntent().getIntExtra(
                        "recipe_id",
                        -1
                );

        if (recipeId != -1) {

            loadRecipe(recipeId);

        } else {

            tvRecipeDetailName.setText(
                    "Recipe not found"
            );

            tvRecipeIngredients.setText(
                    ""
            );

            tvRecipeInstructions.setText(
                    ""
            );
        }
    }

    private void loadRecipe(int recipeId) {

        Recipe recipe =
                databaseHelper.getRecipeById(
                        recipeId
                );

        if (recipe == null) {

            tvRecipeDetailName.setText(
                    "Recipe not found"
            );

            return;
        }

        tvRecipeDetailName.setText(
                recipe.getName()
        );

        tvRecipeInstructions.setText(
                recipe.getInstructions()
        );

        loadIngredients(recipeId);
    }

    private void loadIngredients(int recipeId) {

        List<RecipeIngredient> ingredients =
                databaseHelper.getRecipeIngredients(
                        recipeId
                );

        StringBuilder ingredientText =
                new StringBuilder();

        for (RecipeIngredient ingredient :
                ingredients) {

            ingredientText
                    .append("• ")
                    .append(
                            ingredient.getRequiredQuantity()
                    )
                    .append(" ")
                    .append(
                            ingredient.getUnit()
                    )
                    .append(" ")
                    .append(
                            ingredient.getIngredientName()
                    )
                    .append("\n");
        }

        tvRecipeIngredients.setText(
                ingredientText.toString()
        );
    }
}