package com.example.anuja.bakingapp.app.callback;

import com.example.anuja.bakingapp.model.RecipeModel;

public interface OnRecipeStepSelected {

    void onRecipeStepSelected(RecipeModel recipe, int position);
}
