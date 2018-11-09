package com.example.anuja.bakingapp.app.activities;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.test.espresso.IdlingResource;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.util.DisplayMetrics;
import android.view.View;

import com.example.anuja.bakingapp.R;
import com.example.anuja.bakingapp.app.adapters.RecipeListAdapter;
import com.example.anuja.bakingapp.databinding.ActivityMainBinding;
import com.example.anuja.bakingapp.idlingResource.SimpleIdlingResource;
import com.example.anuja.bakingapp.viewmodel.MainViewModel;

/**
 * This class displays the list of baking recipes
 */
public class MainActivity extends BaseActivity implements RecipeListAdapter.RecipeListItemClickListener {

    public static final String INTENT_RECIPE = "intent_recipe";

    private MainViewModel viewModel;
    private RecipeListAdapter recipeListAdapter;
    private ActivityMainBinding activityMainBinding;

    @Nullable
    private SimpleIdlingResource mIdlingResource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activityMainBinding = DataBindingUtil.setContentView(this
                , R.layout.activity_main);

        viewModel = ViewModelProviders.of(this).get(MainViewModel.class);

        setUpRecyclerViews();

        getIdlingResource();
        if (mIdlingResource != null) {
            mIdlingResource.setIdleState(false);
        }
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

    /**
     * function called when the connectivity is available
     */
    @Override
    protected void onConnected() {
        activityMainBinding.rvRecipeList.setVisibility(View.VISIBLE);
        retrieveBakingRecipes();
    }

    /**
     * function called when the connectivity is unavailable
     */
    @Override
    protected void onDisconnected() {
        activityMainBinding.pbRecipeList.setVisibility(View.GONE);
        activityMainBinding.rvRecipeList.setVisibility(View.GONE);
        showSnackBar(activityMainBinding.clMain, R.string.no_connection_message);
    }

    /**
     * Only called from test, creates and returns a new {@link SimpleIdlingResource}.
     */
    @VisibleForTesting
    @NonNull
    public IdlingResource getIdlingResource() {
        if (mIdlingResource == null) {
            mIdlingResource = new SimpleIdlingResource();
        }
        return mIdlingResource;
    }
}
