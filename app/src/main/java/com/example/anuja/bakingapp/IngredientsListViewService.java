package com.example.anuja.bakingapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.example.anuja.bakingapp.app.activities.MainActivity;
import com.example.anuja.bakingapp.app.common.Utils;
import com.example.anuja.bakingapp.model.Ingredients;
import com.example.anuja.bakingapp.model.RecipeModel;
import com.example.anuja.bakingapp.utilities.SharedPreferenceUtils;

import java.util.ArrayList;
import java.util.List;

public class IngredientsListViewService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new IngredientWidgetListViewFactory(getApplicationContext());
    }
}

class IngredientWidgetListViewFactory implements RemoteViewsService.RemoteViewsFactory {

    private Context context;
    List<Ingredients> ingredientsList = new ArrayList<>();
    private RecipeModel recipe;

    public IngredientWidgetListViewFactory(Context context) {
        this.context = context;
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {

        recipe = SharedPreferenceUtils.getRecipe(context);
        if (ingredientsList != null) {
            ingredientsList.clear();
        }
        ingredientsList.addAll(recipe.getIngredients());
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return ingredientsList.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {

        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.baking_widget_item);
        remoteViews.setTextViewText(R.id.tv_ingredients_widget_item,
                Utils.getStringByIngredient(ingredientsList.get(position)));

        Bundle extras = new Bundle();
        extras.putParcelable(MainActivity.INTENT_RECIPE, recipe);
        Intent intent = new Intent();
        intent.putExtras(extras);
        remoteViews.setOnClickFillInIntent(R.id.ll_item, intent);

        return remoteViews;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}
