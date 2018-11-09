package com.example.anuja.bakingapp;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.example.anuja.bakingapp.app.activities.MainActivity;
import com.example.anuja.bakingapp.app.activities.RecipeDetailsActivity;
import com.example.anuja.bakingapp.model.RecipeModel;

/**
 * Implementation of App Widget functionality.
 */
public class BakingWidgetProvider extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager, RecipeModel recipe,
                                int appWidgetId) {

        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.baking_widget_provider);
        Intent intent;

        if (recipe != null) {
            // If obtained recipe is not null, the show the recipe details and navigate user to details screen when clicked on widget.

            views.setTextViewText(R.id.tv_recipe_widget_value, recipe.getName());

            Intent listService = new Intent(context, IngredientsListViewService.class);
            views.setRemoteAdapter(R.id.lv_ingredients_widget, listService);

            views.setEmptyView(R.id.lv_ingredients_widget, R.id.ll_empty_view);

            intent = new Intent(context, RecipeDetailsActivity.class);
            intent.putExtra(MainActivity.INTENT_RECIPE, recipe);

        } else {
            // If obtained recipe is null, show N/A and navigate user to RecipeListActivity to show list of recipes.
            intent = new Intent(context, MainActivity.class);
        }

        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        views.setOnClickPendingIntent(R.id.baking_app_widget_layout, pendingIntent);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        WidgetService.startActionUpdateIngredientsWidgets(context);
    }

    public static void updateBakingAppWidgets(Context context, AppWidgetManager appWidgetManager,
                                          RecipeModel recipe, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, recipe, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

