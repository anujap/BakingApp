package com.example.anuja.bakingapp.app.activities;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.util.DisplayMetrics;
import android.view.View;

import com.example.anuja.bakingapp.R;
import com.example.anuja.bakingapp.app.adapters.RecipeListAdapter;
import com.example.anuja.bakingapp.databinding.ActivityMainBinding;
import com.example.anuja.bakingapp.viewmodel.MainViewModel;

/**
 * This class displays the list of baking recipes
 */
public class MainActivity extends AppCompatActivity implements RecipeListAdapter.RecipeListItemClickListener {

    protected static final String INTENT_RECIPE = "intent_recipe";

    private MainViewModel viewModel;
    private RecipeListAdapter recipeListAdapter;
    private ActivityMainBinding activityMainBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activityMainBinding = DataBindingUtil.setContentView(this
                , R.layout.activity_main);

        viewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        setUpRecyclerViews();
        retrieveBakingRecipes();
    }

    /**
     * function to setup recycler views
     */
    private void setUpRecyclerViews() {
        recipeListAdapter = new RecipeListAdapter(null, this);
        activityMainBinding.rvRecipeList.setAdapter(recipeListAdapter);
        activityMainBinding.rvRecipeList.setLayoutManager(new GridLayoutManager(this, calculateNoOfColumns()));
        activityMainBinding.pbRecipeList.setVisibility(View.VISIBLE);
    }

    /**
     * function called to get the list of baking recipes
     */
    private void retrieveBakingRecipes() {
        viewModel.displayRecipes();

        viewModel.getRecipesList().observe(this, recipeList -> {
            recipeListAdapter.swapLists(recipeList);
            activityMainBinding.pbRecipeList.setVisibility(View.GONE);
        });
    }

    @Override
    public void onListItemClick(Parcelable item) {
        Intent intent = new Intent(this, RecipeDetailsActivity.class);
        intent.putExtra(INTENT_RECIPE, item);
        startActivity(intent);
    }

    /**
     * function called to auto fit the number of columns in a grid
     */
    private int calculateNoOfColumns() {
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
        int noOfColumns = (int) (dpWidth / 280);
        return noOfColumns;
    }
}
