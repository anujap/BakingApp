package com.example.anuja.bakingapp.app.activities;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.anuja.bakingapp.R;
import com.example.anuja.bakingapp.app.callback.OnRecipeStepSelected;
import com.example.anuja.bakingapp.app.common.FragmentUtils;
import com.example.anuja.bakingapp.app.fragments.RecipeDetailsFragment;
import com.example.anuja.bakingapp.model.RecipeModel;

/**
 * This class displays the recipe ingredients and steps to make
 * the recipe
 */
public class RecipeDetailsActivity extends AppCompatActivity implements OnRecipeStepSelected {

    public static final String TAG_RECIPE_DETAILS_FRAG = "recipe_details_frag";

    public static final String BUNDLE_RECIPE = "bundle_recipe";
    public static final String BUNDLE_RECIPE_POSITION = "bundle_recipe_position";

    private RecipeModel mRecipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_details);

        // get recipe item - Main Activity
        mRecipe = getIntent().getParcelableExtra(MainActivity.INTENT_RECIPE);
        if(mRecipe != null) {
            setUpActionBar();
            setUpFragmentContainer();
        }
    }

    /**
     * function called to set up action bar and its title
     */
    private void setUpActionBar() {
        ActionBar actionBar = getSupportActionBar();

        if(actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle(mRecipe.getName());
        }
    }

    /**
     * function called to set up the container and add
     * the recipe details fragment to it
     */
    private void setUpFragmentContainer() {
        RecipeDetailsFragment recipeDetailsFragment = new RecipeDetailsFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(BUNDLE_RECIPE, mRecipe);
        recipeDetailsFragment.setArguments(bundle);
        FragmentUtils.addFragment(getSupportFragmentManager(), recipeDetailsFragment, R.id.fl_recipe_details_container, TAG_RECIPE_DETAILS_FRAG);
    }

    @Override
    public void onRecipeStepSelected(RecipeModel recipe, int position) {
        RecipeDetailsFragment recipeDetailsFragment = new RecipeDetailsFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(BUNDLE_RECIPE, mRecipe);
        bundle.putInt(BUNDLE_RECIPE_POSITION, position);
        recipeDetailsFragment.setArguments(bundle);
        FragmentUtils.replaceFragment(getSupportFragmentManager(), recipeDetailsFragment
                , R.id.fl_recipe_details_container_tablet, TAG_RECIPE_DETAILS_FRAG);
    }
}
