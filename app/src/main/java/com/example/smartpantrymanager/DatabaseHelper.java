package com.example.smartpantrymanager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    // ============================================================
    // DATABASE INFORMATION
    // ============================================================

    private static final String DATABASE_NAME = "SmartPantry.db";
    private static final int DATABASE_VERSION = 2;


    // ============================================================
    // PANTRY TABLE
    // ============================================================

    public static final String TABLE_PANTRY = "pantry_items";

    public static final String COLUMN_PANTRY_ID = "id";
    public static final String COLUMN_PANTRY_NAME = "name";
    public static final String COLUMN_PANTRY_QUANTITY = "quantity";
    public static final String COLUMN_PANTRY_UNIT = "unit";
    public static final String COLUMN_PANTRY_EXPIRY = "expiry_date";


    // ============================================================
    // RECIPES TABLE
    // ============================================================

    public static final String TABLE_RECIPES = "recipes";

    public static final String COLUMN_RECIPE_ID = "id";
    public static final String COLUMN_RECIPE_NAME = "name";
    public static final String COLUMN_RECIPE_INSTRUCTIONS =
            "instructions";


    // ============================================================
    // RECIPE INGREDIENTS TABLE
    // ============================================================

    public static final String TABLE_RECIPE_INGREDIENTS =
            "recipe_ingredients";

    public static final String COLUMN_RECIPE_INGREDIENT_ID =
            "id";

    public static final String COLUMN_RECIPE_ID_FK =
            "recipe_id";

    public static final String COLUMN_INGREDIENT_NAME =
            "ingredient_name";

    public static final String COLUMN_REQUIRED_QUANTITY =
            "required_quantity";

    public static final String COLUMN_INGREDIENT_UNIT =
            "unit";


    // ============================================================
    // SETTINGS TABLE
    // ============================================================

    public static final String TABLE_SETTINGS = "settings";

    public static final String COLUMN_SETTINGS_ID = "id";

    public static final String COLUMN_EXPIRY_ALERTS =
            "expiry_alerts";

    public static final String COLUMN_PREFERRED_UNIT =
            "preferred_unit";


    // ============================================================
    // CONSTRUCTOR
    // ============================================================

    public DatabaseHelper(Context context) {

        super(
                context,
                DATABASE_NAME,
                null,
                DATABASE_VERSION
        );
    }


    // ============================================================
    // CREATE DATABASE
    // ============================================================

    @Override
    public void onCreate(SQLiteDatabase db) {

        // --------------------------------------------------------
        // PANTRY TABLE
        // --------------------------------------------------------

        String createPantryTable =
                "CREATE TABLE " + TABLE_PANTRY + " (" +

                        COLUMN_PANTRY_ID +
                        " INTEGER PRIMARY KEY AUTOINCREMENT, " +

                        COLUMN_PANTRY_NAME +
                        " TEXT NOT NULL, " +

                        COLUMN_PANTRY_QUANTITY +
                        " REAL NOT NULL, " +

                        COLUMN_PANTRY_UNIT +
                        " TEXT NOT NULL, " +

                        COLUMN_PANTRY_EXPIRY +
                        " TEXT" +

                        ")";

        db.execSQL(createPantryTable);


        // --------------------------------------------------------
        // RECIPES TABLE
        // --------------------------------------------------------

        String createRecipesTable =
                "CREATE TABLE " + TABLE_RECIPES + " (" +

                        COLUMN_RECIPE_ID +
                        " INTEGER PRIMARY KEY AUTOINCREMENT, " +

                        COLUMN_RECIPE_NAME +
                        " TEXT NOT NULL, " +

                        COLUMN_RECIPE_INSTRUCTIONS +
                        " TEXT NOT NULL" +

                        ")";

        db.execSQL(createRecipesTable);


        // --------------------------------------------------------
        // RECIPE INGREDIENTS TABLE
        // --------------------------------------------------------

        String createRecipeIngredientsTable =
                "CREATE TABLE " +
                        TABLE_RECIPE_INGREDIENTS +
                        " (" +

                        COLUMN_RECIPE_INGREDIENT_ID +
                        " INTEGER PRIMARY KEY AUTOINCREMENT, " +

                        COLUMN_RECIPE_ID_FK +
                        " INTEGER NOT NULL, " +

                        COLUMN_INGREDIENT_NAME +
                        " TEXT NOT NULL, " +

                        COLUMN_REQUIRED_QUANTITY +
                        " REAL NOT NULL, " +

                        COLUMN_INGREDIENT_UNIT +
                        " TEXT NOT NULL, " +

                        "FOREIGN KEY (" +
                        COLUMN_RECIPE_ID_FK +
                        ") REFERENCES " +
                        TABLE_RECIPES +
                        "(" +
                        COLUMN_RECIPE_ID +
                        ")" +

                        ")";

        db.execSQL(createRecipeIngredientsTable);


        // --------------------------------------------------------
        // SETTINGS TABLE
        // --------------------------------------------------------

        String createSettingsTable =
                "CREATE TABLE " + TABLE_SETTINGS + " (" +

                        COLUMN_SETTINGS_ID +
                        " INTEGER PRIMARY KEY AUTOINCREMENT, " +

                        COLUMN_EXPIRY_ALERTS +
                        " INTEGER NOT NULL DEFAULT 1, " +

                        COLUMN_PREFERRED_UNIT +
                        " TEXT NOT NULL DEFAULT 'g'" +

                        ")";

        db.execSQL(createSettingsTable);


        // --------------------------------------------------------
        // SEED RECIPES
        // --------------------------------------------------------

        seedRecipes(db);
    }


    // ============================================================
    // DATABASE UPGRADE
    // ============================================================

    @Override
    public void onUpgrade(
            SQLiteDatabase db,
            int oldVersion,
            int newVersion) {

        /*
         * Do not delete the pantry table.
         *
         * The user already has pantry data.
         */

        if (oldVersion < 2) {

            // ----------------------------------------------------
            // RECIPES TABLE
            // ----------------------------------------------------

            String createRecipesTable =
                    "CREATE TABLE IF NOT EXISTS " +
                            TABLE_RECIPES +
                            " (" +

                            COLUMN_RECIPE_ID +
                            " INTEGER PRIMARY KEY AUTOINCREMENT, " +

                            COLUMN_RECIPE_NAME +
                            " TEXT NOT NULL, " +

                            COLUMN_RECIPE_INSTRUCTIONS +
                            " TEXT NOT NULL" +

                            ")";

            db.execSQL(createRecipesTable);


            // ----------------------------------------------------
            // RECIPE INGREDIENTS TABLE
            // ----------------------------------------------------

            String createRecipeIngredientsTable =
                    "CREATE TABLE IF NOT EXISTS " +
                            TABLE_RECIPE_INGREDIENTS +
                            " (" +

                            COLUMN_RECIPE_INGREDIENT_ID +
                            " INTEGER PRIMARY KEY AUTOINCREMENT, " +

                            COLUMN_RECIPE_ID_FK +
                            " INTEGER NOT NULL, " +

                            COLUMN_INGREDIENT_NAME +
                            " TEXT NOT NULL, " +

                            COLUMN_REQUIRED_QUANTITY +
                            " REAL NOT NULL, " +

                            COLUMN_INGREDIENT_UNIT +
                            " TEXT NOT NULL, " +

                            "FOREIGN KEY (" +
                            COLUMN_RECIPE_ID_FK +
                            ") REFERENCES " +
                            TABLE_RECIPES +
                            "(" +
                            COLUMN_RECIPE_ID +
                            ")" +

                            ")";

            db.execSQL(createRecipeIngredientsTable);


            // ----------------------------------------------------
            // SETTINGS TABLE
            // ----------------------------------------------------

            String createSettingsTable =
                    "CREATE TABLE IF NOT EXISTS " +
                            TABLE_SETTINGS +
                            " (" +

                            COLUMN_SETTINGS_ID +
                            " INTEGER PRIMARY KEY AUTOINCREMENT, " +

                            COLUMN_EXPIRY_ALERTS +
                            " INTEGER NOT NULL DEFAULT 1, " +

                            COLUMN_PREFERRED_UNIT +
                            " TEXT NOT NULL DEFAULT 'g'" +

                            ")";

            db.execSQL(createSettingsTable);


            // ----------------------------------------------------
            // SEED RECIPES
            // ----------------------------------------------------

            seedRecipes(db);
        }
    }


    // ============================================================
    // PANTRY - CREATE
    // ============================================================

    public long addPantryItem(PantryItem item) {

        SQLiteDatabase db =
                this.getWritableDatabase();

        ContentValues values =
                new ContentValues();

        values.put(
                COLUMN_PANTRY_NAME,
                item.getName()
        );

        values.put(
                COLUMN_PANTRY_QUANTITY,
                item.getQuantity()
        );

        values.put(
                COLUMN_PANTRY_UNIT,
                item.getUnit()
        );

        values.put(
                COLUMN_PANTRY_EXPIRY,
                item.getExpiryDate()
        );

        long id =
                db.insert(
                        TABLE_PANTRY,
                        null,
                        values
                );

        db.close();

        return id;
    }


    // ============================================================
    // PANTRY - READ
    // ============================================================

    public List<PantryItem> getAllPantryItems() {

        List<PantryItem> pantryItems =
                new ArrayList<>();

        SQLiteDatabase db =
                this.getReadableDatabase();

        Cursor cursor =
                db.query(
                        TABLE_PANTRY,
                        null,
                        null,
                        null,
                        null,
                        null,
                        COLUMN_PANTRY_NAME + " ASC"
                );

        if (cursor.moveToFirst()) {

            do {

                int id =
                        cursor.getInt(
                                cursor.getColumnIndexOrThrow(
                                        COLUMN_PANTRY_ID
                                )
                        );

                String name =
                        cursor.getString(
                                cursor.getColumnIndexOrThrow(
                                        COLUMN_PANTRY_NAME
                                )
                        );

                double quantity =
                        cursor.getDouble(
                                cursor.getColumnIndexOrThrow(
                                        COLUMN_PANTRY_QUANTITY
                                )
                        );

                String unit =
                        cursor.getString(
                                cursor.getColumnIndexOrThrow(
                                        COLUMN_PANTRY_UNIT
                                )
                        );

                String expiryDate =
                        cursor.getString(
                                cursor.getColumnIndexOrThrow(
                                        COLUMN_PANTRY_EXPIRY
                                )
                        );

                PantryItem item =
                        new PantryItem(
                                id,
                                name,
                                quantity,
                                unit,
                                expiryDate
                        );

                pantryItems.add(item);

            } while (cursor.moveToNext());
        }

        cursor.close();

        db.close();

        return pantryItems;
    }


    // ============================================================
    // PANTRY - UPDATE
    // ============================================================

    public int updatePantryItem(PantryItem item) {

        SQLiteDatabase db =
                this.getWritableDatabase();

        ContentValues values =
                new ContentValues();

        values.put(
                COLUMN_PANTRY_NAME,
                item.getName()
        );

        values.put(
                COLUMN_PANTRY_QUANTITY,
                item.getQuantity()
        );

        values.put(
                COLUMN_PANTRY_UNIT,
                item.getUnit()
        );

        values.put(
                COLUMN_PANTRY_EXPIRY,
                item.getExpiryDate()
        );

        int rowsUpdated =
                db.update(
                        TABLE_PANTRY,
                        values,
                        COLUMN_PANTRY_ID + " = ?",
                        new String[]{
                                String.valueOf(
                                        item.getId()
                                )
                        }
                );

        db.close();

        return rowsUpdated;
    }


    // ============================================================
    // PANTRY - DELETE
    // ============================================================

    public int deletePantryItem(int id) {

        SQLiteDatabase db =
                this.getWritableDatabase();

        int rowsDeleted =
                db.delete(
                        TABLE_PANTRY,
                        COLUMN_PANTRY_ID + " = ?",
                        new String[]{
                                String.valueOf(id)
                        }
                );

        db.close();

        return rowsDeleted;
    }


    // ============================================================
    // RECIPE SEEDING
    // ============================================================

    private void seedRecipes(SQLiteDatabase db) {

        /*
         * Prevent duplicate recipe insertion.
         */

        Cursor cursor =
                db.rawQuery(
                        "SELECT COUNT(*) FROM " +
                                TABLE_RECIPES,
                        null
                );

        int recipeCount = 0;

        if (cursor.moveToFirst()) {

            recipeCount =
                    cursor.getInt(0);
        }

        cursor.close();

        if (recipeCount > 0) {
            return;
        }


        // ========================================================
        // 1. EGG SANDWICH
        // ========================================================

        long recipeId =
                insertRecipe(
                        db,
                        "Egg Sandwich",
                        "1. Cook the eggs.\n" +
                                "2. Place the eggs between the bread.\n" +
                                "3. Add butter if desired.\n" +
                                "4. Serve."
                );

        insertRecipeIngredient(
                db,
                recipeId,
                "egg",
                2,
                "pieces"
        );

        insertRecipeIngredient(
                db,
                recipeId,
                "bread",
                2,
                "slices"
        );

        insertRecipeIngredient(
                db,
                recipeId,
                "butter",
                1,
                "tablespoon"
        );


        // ========================================================
        // 2. FRENCH TOAST
        // ========================================================

        recipeId =
                insertRecipe(
                        db,
                        "French Toast",
                        "1. Beat the eggs.\n" +
                                "2. Dip bread into the egg mixture.\n" +
                                "3. Cook in a pan until golden.\n" +
                                "4. Serve warm."
                );

        insertRecipeIngredient(
                db,
                recipeId,
                "egg",
                2,
                "pieces"
        );

        insertRecipeIngredient(
                db,
                recipeId,
                "bread",
                2,
                "slices"
        );

        insertRecipeIngredient(
                db,
                recipeId,
                "milk",
                100,
                "ml"
        );


        // ========================================================
        // 3. SCRAMBLED EGGS
        // ========================================================

        recipeId =
                insertRecipe(
                        db,
                        "Scrambled Eggs",
                        "1. Crack the eggs into a bowl.\n" +
                                "2. Beat the eggs.\n" +
                                "3. Cook in a pan while stirring.\n" +
                                "4. Serve."
                );

        insertRecipeIngredient(
                db,
                recipeId,
                "egg",
                3,
                "pieces"
        );

        insertRecipeIngredient(
                db,
                recipeId,
                "butter",
                1,
                "tablespoon"
        );

        insertRecipeIngredient(
                db,
                recipeId,
                "milk",
                50,
                "ml"
        );


        // ========================================================
        // 4. OMELETTE
        // ========================================================

        recipeId =
                insertRecipe(
                        db,
                        "Omelette",
                        "1. Beat the eggs.\n" +
                                "2. Cook the eggs in a pan.\n" +
                                "3. Add cheese.\n" +
                                "4. Fold and serve."
                );

        insertRecipeIngredient(
                db,
                recipeId,
                "egg",
                3,
                "pieces"
        );

        insertRecipeIngredient(
                db,
                recipeId,
                "cheese",
                50,
                "g"
        );

        insertRecipeIngredient(
                db,
                recipeId,
                "butter",
                1,
                "tablespoon"
        );


        // ========================================================
        // 5. TOMATO PASTA
        // ========================================================

        recipeId =
                insertRecipe(
                        db,
                        "Tomato Pasta",
                        "1. Cook the pasta.\n" +
                                "2. Prepare the tomatoes.\n" +
                                "3. Cook the tomatoes with garlic.\n" +
                                "4. Mix with the pasta.\n" +
                                "5. Serve."
                );

        insertRecipeIngredient(
                db,
                recipeId,
                "pasta",
                200,
                "g"
        );

        insertRecipeIngredient(
                db,
                recipeId,
                "tomato",
                3,
                "pieces"
        );

        insertRecipeIngredient(
                db,
                recipeId,
                "garlic",
                2,
                "cloves"
        );

        insertRecipeIngredient(
                db,
                recipeId,
                "oil",
                1,
                "tablespoon"
        );


        // ========================================================
        // 6. GARLIC PASTA
        // ========================================================

        recipeId =
                insertRecipe(
                        db,
                        "Garlic Pasta",
                        "1. Cook the pasta.\n" +
                                "2. Cook garlic in oil.\n" +
                                "3. Add the pasta.\n" +
                                "4. Mix well and serve."
                );

        insertRecipeIngredient(
                db,
                recipeId,
                "pasta",
                200,
                "g"
        );

        insertRecipeIngredient(
                db,
                recipeId,
                "garlic",
                3,
                "cloves"
        );

        insertRecipeIngredient(
                db,
                recipeId,
                "oil",
                1,
                "tablespoon"
        );


        // ========================================================
        // 7. TUNA PASTA
        // ========================================================

        recipeId =
                insertRecipe(
                        db,
                        "Tuna Pasta",
                        "1. Cook the pasta.\n" +
                                "2. Drain the tuna.\n" +
                                "3. Mix tuna with the pasta.\n" +
                                "4. Add cheese and serve."
                );

        insertRecipeIngredient(
                db,
                recipeId,
                "pasta",
                200,
                "g"
        );

        insertRecipeIngredient(
                db,
                recipeId,
                "tuna",
                1,
                "can"
        );

        insertRecipeIngredient(
                db,
                recipeId,
                "cheese",
                50,
                "g"
        );


        // ========================================================
        // 8. CHICKEN PASTA
        // ========================================================

        recipeId =
                insertRecipe(
                        db,
                        "Chicken Pasta",
                        "1. Cook the pasta.\n" +
                                "2. Cook the chicken.\n" +
                                "3. Add the tomatoes.\n" +
                                "4. Mix with the pasta.\n" +
                                "5. Serve."
                );

        insertRecipeIngredient(
                db,
                recipeId,
                "pasta",
                200,
                "g"
        );

        insertRecipeIngredient(
                db,
                recipeId,
                "chicken",
                200,
                "g"
        );

        insertRecipeIngredient(
                db,
                recipeId,
                "tomato",
                2,
                "pieces"
        );


        // ========================================================
        // 9. CHICKEN SANDWICH
        // ========================================================

        recipeId =
                insertRecipe(
                        db,
                        "Chicken Sandwich",
                        "1. Cook the chicken.\n" +
                                "2. Place chicken on the bread.\n" +
                                "3. Add tomato.\n" +
                                "4. Add lettuce and serve."
                );

        insertRecipeIngredient(
                db,
                recipeId,
                "chicken",
                150,
                "g"
        );

        insertRecipeIngredient(
                db,
                recipeId,
                "bread",
                2,
                "slices"
        );

        insertRecipeIngredient(
                db,
                recipeId,
                "tomato",
                1,
                "piece"
        );

        insertRecipeIngredient(
                db,
                recipeId,
                "lettuce",
                2,
                "leaves"
        );


        // ========================================================
        // 10. GRILLED CHEESE SANDWICH
        // ========================================================

        recipeId =
                insertRecipe(
                        db,
                        "Grilled Cheese Sandwich",
                        "1. Butter the bread.\n" +
                                "2. Add cheese between the slices.\n" +
                                "3. Grill until golden.\n" +
                                "4. Serve."
                );

        insertRecipeIngredient(
                db,
                recipeId,
                "bread",
                2,
                "slices"
        );

        insertRecipeIngredient(
                db,
                recipeId,
                "cheese",
                50,
                "g"
        );

        insertRecipeIngredient(
                db,
                recipeId,
                "butter",
                1,
                "tablespoon"
        );


        // ========================================================
        // 11. TOMATO SOUP
        // ========================================================

        recipeId =
                insertRecipe(
                        db,
                        "Tomato Soup",
                        "1. Chop the tomatoes.\n" +
                                "2. Cook the tomatoes.\n" +
                                "3. Add water and simmer.\n" +
                                "4. Blend until smooth.\n" +
                                "5. Serve."
                );

        insertRecipeIngredient(
                db,
                recipeId,
                "tomato",
                5,
                "pieces"
        );

        insertRecipeIngredient(
                db,
                recipeId,
                "onion",
                1,
                "piece"
        );

        insertRecipeIngredient(
                db,
                recipeId,
                "garlic",
                2,
                "cloves"
        );


        // ========================================================
        // 12. VEGETABLE SOUP
        // ========================================================

        recipeId =
                insertRecipe(
                        db,
                        "Vegetable Soup",
                        "1. Chop the vegetables.\n" +
                                "2. Add vegetables to a pot.\n" +
                                "3. Add water and simmer.\n" +
                                "4. Cook until tender.\n" +
                                "5. Serve."
                );

        insertRecipeIngredient(
                db,
                recipeId,
                "potato",
                2,
                "pieces"
        );

        insertRecipeIngredient(
                db,
                recipeId,
                "carrot",
                2,
                "pieces"
        );

        insertRecipeIngredient(
                db,
                recipeId,
                "onion",
                1,
                "piece"
        );


        // ========================================================
        // 13. PANCAKES
        // ========================================================

        recipeId =
                insertRecipe(
                        db,
                        "Pancakes",
                        "1. Mix the flour and milk.\n" +
                                "2. Add eggs.\n" +
                                "3. Cook portions in a pan.\n" +
                                "4. Serve."
                );

        insertRecipeIngredient(
                db,
                recipeId,
                "flour",
                200,
                "g"
        );

        insertRecipeIngredient(
                db,
                recipeId,
                "milk",
                250,
                "ml"
        );

        insertRecipeIngredient(
                db,
                recipeId,
                "egg",
                2,
                "pieces"
        );


        // ========================================================
        // 14. BANANA PANCAKES
        // ========================================================

        recipeId =
                insertRecipe(
                        db,
                        "Banana Pancakes",
                        "1. Mash the banana.\n" +
                                "2. Mix with eggs and flour.\n" +
                                "3. Cook in a pan.\n" +
                                "4. Serve."
                );

        insertRecipeIngredient(
                db,
                recipeId,
                "banana",
                2,
                "pieces"
        );

        insertRecipeIngredient(
                db,
                recipeId,
                "egg",
                2,
                "pieces"
        );

        insertRecipeIngredient(
                db,
                recipeId,
                "flour",
                100,
                "g"
        );


        // ========================================================
        // 15. EGG FRIED RICE
        // ========================================================

        recipeId =
                insertRecipe(
                        db,
                        "Egg Fried Rice",
                        "1. Cook the rice.\n" +
                                "2. Scramble the eggs.\n" +
                                "3. Add rice and vegetables.\n" +
                                "4. Stir-fry and serve."
                );

        insertRecipeIngredient(
                db,
                recipeId,
                "rice",
                200,
                "g"
        );

        insertRecipeIngredient(
                db,
                recipeId,
                "egg",
                2,
                "pieces"
        );

        insertRecipeIngredient(
                db,
                recipeId,
                "carrot",
                1,
                "piece"
        );

        insertRecipeIngredient(
                db,
                recipeId,
                "oil",
                1,
                "tablespoon"
        );


        // ========================================================
        // 16. CHICKEN FRIED RICE
        // ========================================================

        recipeId =
                insertRecipe(
                        db,
                        "Chicken Fried Rice",
                        "1. Cook the rice.\n" +
                                "2. Cook the chicken.\n" +
                                "3. Add vegetables.\n" +
                                "4. Stir-fry everything together.\n" +
                                "5. Serve."
                );

        insertRecipeIngredient(
                db,
                recipeId,
                "rice",
                200,
                "g"
        );

        insertRecipeIngredient(
                db,
                recipeId,
                "chicken",
                150,
                "g"
        );

        insertRecipeIngredient(
                db,
                recipeId,
                "carrot",
                1,
                "piece"
        );

        insertRecipeIngredient(
                db,
                recipeId,
                "oil",
                1,
                "tablespoon"
        );


        // ========================================================
        // 17. CHEESE OMELETTE
        // ========================================================

        recipeId =
                insertRecipe(
                        db,
                        "Cheese Omelette",
                        "1. Beat the eggs.\n" +
                                "2. Cook the eggs in a pan.\n" +
                                "3. Add cheese.\n" +
                                "4. Fold the omelette.\n" +
                                "5. Serve."
                );

        insertRecipeIngredient(
                db,
                recipeId,
                "egg",
                3,
                "pieces"
        );

        insertRecipeIngredient(
                db,
                recipeId,
                "cheese",
                75,
                "g"
        );


        // ========================================================
        // 18. TUNA SANDWICH
        // ========================================================

        recipeId =
                insertRecipe(
                        db,
                        "Tuna Sandwich",
                        "1. Drain the tuna.\n" +
                                "2. Mix tuna with mayonnaise.\n" +
                                "3. Place mixture on bread.\n" +
                                "4. Serve."
                );

        insertRecipeIngredient(
                db,
                recipeId,
                "tuna",
                1,
                "can"
        );

        insertRecipeIngredient(
                db,
                recipeId,
                "bread",
                2,
                "slices"
        );

        insertRecipeIngredient(
                db,
                recipeId,
                "mayonnaise",
                1,
                "tablespoon"
        );


        // ========================================================
        // 19. POTATO HASH
        // ========================================================

        recipeId =
                insertRecipe(
                        db,
                        "Potato Hash",
                        "1. Chop the potatoes.\n" +
                                "2. Cook the potatoes in oil.\n" +
                                "3. Add onion.\n" +
                                "4. Cook until golden.\n" +
                                "5. Serve."
                );

        insertRecipeIngredient(
                db,
                recipeId,
                "potato",
                3,
                "pieces"
        );

        insertRecipeIngredient(
                db,
                recipeId,
                "onion",
                1,
                "piece"
        );

        insertRecipeIngredient(
                db,
                recipeId,
                "oil",
                1,
                "tablespoon"
        );


        // ========================================================
        // 20. VEGETABLE STIR-FRY
        // ========================================================

        recipeId =
                insertRecipe(
                        db,
                        "Vegetable Stir-Fry",
                        "1. Chop the vegetables.\n" +
                                "2. Heat oil in a pan.\n" +
                                "3. Stir-fry the vegetables.\n" +
                                "4. Cook until tender.\n" +
                                "5. Serve."
                );

        insertRecipeIngredient(
                db,
                recipeId,
                "carrot",
                2,
                "pieces"
        );

        insertRecipeIngredient(
                db,
                recipeId,
                "onion",
                1,
                "piece"
        );

        insertRecipeIngredient(
                db,
                recipeId,
                "pepper",
                1,
                "piece"
        );

        insertRecipeIngredient(
                db,
                recipeId,
                "oil",
                1,
                "tablespoon"
        );
    }


    // ============================================================
    // INSERT RECIPE
    // ============================================================

    private long insertRecipe(
            SQLiteDatabase db,
            String name,
            String instructions) {

        ContentValues values =
                new ContentValues();

        values.put(
                COLUMN_RECIPE_NAME,
                name
        );

        values.put(
                COLUMN_RECIPE_INSTRUCTIONS,
                instructions
        );

        return db.insert(
                TABLE_RECIPES,
                null,
                values
        );
    }


    // ============================================================
    // INSERT RECIPE INGREDIENT
    // ============================================================

    private long insertRecipeIngredient(
            SQLiteDatabase db,
            long recipeId,
            String ingredientName,
            double requiredQuantity,
            String unit) {

        ContentValues values =
                new ContentValues();

        values.put(
                COLUMN_RECIPE_ID_FK,
                recipeId
        );

        values.put(
                COLUMN_INGREDIENT_NAME,
                ingredientName
        );

        values.put(
                COLUMN_REQUIRED_QUANTITY,
                requiredQuantity
        );

        values.put(
                COLUMN_INGREDIENT_UNIT,
                unit
        );

        return db.insert(
                TABLE_RECIPE_INGREDIENTS,
                null,
                values
        );
    }


    // ============================================================
    // GET ALL RECIPES
    // ============================================================

    public List<Recipe> getAllRecipes() {

        List<Recipe> recipes =
                new ArrayList<>();

        SQLiteDatabase db =
                this.getReadableDatabase();

        Cursor cursor =
                db.query(
                        TABLE_RECIPES,
                        null,
                        null,
                        null,
                        null,
                        null,
                        COLUMN_RECIPE_NAME + " ASC"
                );

        if (cursor.moveToFirst()) {

            do {

                int id =
                        cursor.getInt(
                                cursor.getColumnIndexOrThrow(
                                        COLUMN_RECIPE_ID
                                )
                        );

                String name =
                        cursor.getString(
                                cursor.getColumnIndexOrThrow(
                                        COLUMN_RECIPE_NAME
                                )
                        );

                String instructions =
                        cursor.getString(
                                cursor.getColumnIndexOrThrow(
                                        COLUMN_RECIPE_INSTRUCTIONS
                                )
                        );

                Recipe recipe =
                        new Recipe(
                                id,
                                name,
                                instructions
                        );

                recipes.add(recipe);

            } while (cursor.moveToNext());
        }

        cursor.close();

        db.close();

        return recipes;
    }


    // ============================================================
    // GET RECIPE INGREDIENTS
    // ============================================================

    public List<RecipeIngredient> getRecipeIngredients(
            int recipeId) {

        List<RecipeIngredient> ingredients =
                new ArrayList<>();

        SQLiteDatabase db =
                this.getReadableDatabase();

        String selection =
                COLUMN_RECIPE_ID_FK + " = ?";

        String[] selectionArgs =
                new String[]{
                        String.valueOf(recipeId)
                };

        Cursor cursor =
                db.query(
                        TABLE_RECIPE_INGREDIENTS,
                        null,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        COLUMN_INGREDIENT_NAME + " ASC"
                );

        if (cursor.moveToFirst()) {

            do {

                int id =
                        cursor.getInt(
                                cursor.getColumnIndexOrThrow(
                                        COLUMN_RECIPE_INGREDIENT_ID
                                )
                        );

                int recipeIdFromDatabase =
                        cursor.getInt(
                                cursor.getColumnIndexOrThrow(
                                        COLUMN_RECIPE_ID_FK
                                )
                        );

                String ingredientName =
                        cursor.getString(
                                cursor.getColumnIndexOrThrow(
                                        COLUMN_INGREDIENT_NAME
                                )
                        );

                double requiredQuantity =
                        cursor.getDouble(
                                cursor.getColumnIndexOrThrow(
                                        COLUMN_REQUIRED_QUANTITY
                                )
                        );

                String unit =
                        cursor.getString(
                                cursor.getColumnIndexOrThrow(
                                        COLUMN_INGREDIENT_UNIT
                                )
                        );

                RecipeIngredient ingredient =
                        new RecipeIngredient(
                                id,
                                recipeIdFromDatabase,
                                ingredientName,
                                requiredQuantity,
                                unit
                        );

                ingredients.add(ingredient);

            } while (cursor.moveToNext());
        }

        cursor.close();

        db.close();

        return ingredients;
    }


    // ============================================================
    // GET ONE RECIPE
    // ============================================================

    public Recipe getRecipeById(int recipeId) {

        SQLiteDatabase db =
                this.getReadableDatabase();

        Cursor cursor =
                db.query(
                        TABLE_RECIPES,
                        null,
                        COLUMN_RECIPE_ID + " = ?",
                        new String[]{
                                String.valueOf(recipeId)
                        },
                        null,
                        null,
                        null
                );

        Recipe recipe = null;

        if (cursor.moveToFirst()) {

            int id =
                    cursor.getInt(
                            cursor.getColumnIndexOrThrow(
                                    COLUMN_RECIPE_ID
                            )
                    );

            String name =
                    cursor.getString(
                            cursor.getColumnIndexOrThrow(
                                    COLUMN_RECIPE_NAME
                            )
                    );

            String instructions =
                    cursor.getString(
                            cursor.getColumnIndexOrThrow(
                                    COLUMN_RECIPE_INSTRUCTIONS
                            )
                    );

            recipe =
                    new Recipe(
                            id,
                            name,
                            instructions
                    );
        }

        cursor.close();

        db.close();

        return recipe;
    }
}