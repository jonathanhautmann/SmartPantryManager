# Smart Pantry Manager

Smart Pantry Manager is an Android application developed in Java for the Mobile App Development 700 practical assignment.

## Purpose

The application allows users to keep track of ingredients currently available at home and discover recipes that can be prepared using only the ingredients already in the pantry.

The application does not require the user to purchase additional ingredients.

## Main Features

- Add pantry ingredients
- Edit pantry ingredients
- Delete pantry ingredients
- Store pantry data using SQLite
- Display pantry ingredients using RecyclerView
- Custom RecyclerView Adapter
- Seeded recipe database
- Strict recipe matching
- Ingredient quantity checking
- Simple singular/plural ingredient matching
- Suggested Recipes screen
- Recipe details and instructions
- Settings and profile preferences
- Expiry alerts
- Friendly message when no recipes match the pantry
- Navigation between application screens using Intents

## Technologies Used

- Java
- Android Studio
- Android SDK
- SQLite
- SQLiteOpenHelper
- RecyclerView
- Custom Adapters
- Android Activities
- Intents
- SharedPreferences
- XML layouts
- Git and GitHub

## Application Screens

1. Main Screen
2. My Pantry
3. Add/Edit Ingredient
4. Suggested Recipes
5. Recipe Detail
6. Settings

## Recipe Matching

The application uses strict matching.

A recipe is suggested only when all required ingredients are available in the pantry in sufficient quantities.

If an ingredient is missing or the available quantity is insufficient, the recipe is not suggested.

## Database

The application uses SQLite to store:

- Pantry ingredients
- Recipes
- Recipe ingredients
- Application settings

## Project Structure

The main Java classes include:

- `MainActivity`
- `PantryActivity`
- `AddEditIngredientActivity`
- `SuggestedRecipesActivity`
- `RecipeDetailActivity`
- `SettingsActivity`
- `DatabaseHelper`
- `PantryAdapter`
- `RecipeAdapter`
- `PantryItem`
- `Recipe`
- `RecipeIngredient`

## Author

Jonathan Hautmann

## Academic Project

This project was developed as part of the Mobile App Development 700 practical assignment.

## System Architecture

The application follows a simple Android application structure using Activities, model classes, adapters and a database helper.

### Main Components

- **Activities** handle the different application screens and navigation.
- **Model classes** represent pantry items, recipes and recipe ingredients.
- **RecyclerView Adapters** display pantry ingredients and suggested recipes.
- **DatabaseHelper** manages SQLite database creation, storage and retrieval.
- **SharedPreferences** stores user settings such as the profile name, expiry-alert preference and preferred unit.

## Database Design

The SQLite database contains the following main tables:

### pantry_items

Stores ingredients currently available in the user's pantry.

Fields include:

- Ingredient ID
- Ingredient name
- Quantity
- Unit
- Expiry date

### recipes

Stores the available recipes.

Fields include:

- Recipe ID
- Recipe name
- Instructions

### recipe_ingredients

Stores the ingredients required by each recipe.

Fields include:

- Ingredient record ID
- Recipe ID
- Ingredient name
- Required quantity
- Unit

### settings

Stores application preference information.

## Recipe Matching Logic

Smart Pantry Manager uses strict recipe matching.

For each recipe, the application checks every required ingredient against the ingredients stored in the pantry.

A recipe is suggested only when:

1. The required ingredient exists in the pantry.
2. The available quantity is sufficient.
3. The ingredient can be matched using the application's simple ingredient-name normalisation.

If any required ingredient is missing or the available quantity is insufficient, the recipe is excluded from the Suggested Recipes list.

## Data Persistence

Pantry information is stored using SQLite so that ingredients remain available after the application is closed and reopened.

Application preferences are stored using Android SharedPreferences.

## Navigation

The application uses Android Intents to navigate between Activities:

- Main Activity → Pantry
- Main Activity → Suggested Recipes
- Main Activity → Settings
- Pantry → Add/Edit Ingredient
- Suggested Recipes → Recipe Detail

## Validation

The Add/Edit Ingredient screen validates:

- Ingredient name is provided.
- Quantity is provided.
- Quantity is a valid number.
- Quantity is greater than zero.
- Unit is provided.

This prevents invalid pantry records from being stored.

## Expiry Alerts

Users can enable expiry alerts in Settings.

When enabled, pantry items approaching their expiry date are identified as expiring soon, while expired items are displayed with an expired warning.