package com.example.anuja.bakingapp;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.content.Context;
import android.util.Log;

import com.example.anuja.bakingapp.model.RecipeModel;
import com.example.anuja.bakingapp.utilities.SharedPreferenceUtils;

/**
 * This is a widget service class
 */
public class WidgetService extends IntentService {
    private static final String ACTION_UPDATE_WIDGETS = "com.example.anuja.bakingapp.action.update.widgets";
    private static final String ACTION_UPDATE_RECIPE = "com.example.anuja.bakingapp.action.update.recipe";
    private static final String EXTRA_RECIPE = "com.example.anuja.bakingapp.extra.recipe";

    public WidgetService() {
        super("WidgetService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (action.equals(ACTION_UPDATE_WIDGETS)) {
                handleActionUpdateWidgets();
            } else if (action.equals(ACTION_UPDATE_RECIPE)) {
                RecipeModel recipe = intent.getParcelableExtra(EXTRA_RECIPE);
                handleActionUpdateRecipe(recipe);
            }
        }
    }

    /**
     * function used to start the intent service to update the widgets.
      */
    public static void startActionUpdateIngredientsWidgets(Context context) {

        Intent intent = new Intent(context, WidgetService.class);
        intent.setAction(ACTION_UPDATE_WIDGETS);
        context.startService(intent);
    }

    /**
     * function used to start intent service to store in
     * shared preferences and update the widgets.
      */
    public static void startActionToUpdateRecipe(Context context, RecipeModel recipe) {
        Intent intent = new Intent(context, WidgetService.class);
        intent.setAction(ACTION_UPDATE_RECIPE);
        intent.putExtra(EXTRA_RECIPE, recipe);
        context.startService(intent);
    }

    // Method to handle the recipe object and start widget update service.
    private void handleActionUpdateRecipe(RecipeModel recipe) {
        SharedPreferenceUtils.storeRecipe(this, recipe);
        startActionUpdateIngredientsWidgets(this);
    }

    // Method to handle the update of widgets.
    private void handleActionUpdateWidgets() {

        // Getting the recipe from the shared preference.
        RecipeModel recipe = SharedPreferenceUtils.getRecipe(this);

        if (recipe != null) {
            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
            int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this, BakingWidgetProvider.class));

            appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.lv_ingredients_widget);

            BakingWidgetProvider.updateBakingAppWidgets(this, appWidgetManager, recipe, appWidgetIds);
        }
    }
}
