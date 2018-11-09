package com.example.anuja.bakingapp.utilities;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.anuja.bakingapp.model.RecipeModel;
import com.google.gson.Gson;

public class SharedPreferenceUtils {

    private static final String APP_SHARED_PREF_FILE_NAME = "baking_app_shared_pref";
    private static final String RECEIPE_CHANGED = "shared_pref_recently_changed_recipe";

    private SharedPreferenceUtils() {
    }

    /**
     * function used to store the recipe object in the shared preference.
      */
    public static void storeRecipe(Context context, RecipeModel recipe) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(APP_SHARED_PREF_FILE_NAME,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        String recipeString = new Gson().toJson(recipe);
        editor.putString(RECEIPE_CHANGED, recipeString);
        editor.apply();
    }

    /**
     * function used to get the recipe object from the shared preference.
     */
    public static RecipeModel getRecipe(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(APP_SHARED_PREF_FILE_NAME,
                Context.MODE_PRIVATE);

        String recipeString = sharedPreferences.getString(RECEIPE_CHANGED, null);
        return new Gson().fromJson(recipeString, RecipeModel.class);
    }
}