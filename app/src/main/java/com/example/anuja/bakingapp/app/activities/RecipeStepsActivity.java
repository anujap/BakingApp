package com.example.anuja.bakingapp.app.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;

import com.example.anuja.bakingapp.R;
import com.example.anuja.bakingapp.app.common.FragmentUtils;
import com.example.anuja.bakingapp.app.fragments.RecipeDetailsFragment;
import com.example.anuja.bakingapp.app.fragments.RecipeStepsFragment;
import com.example.anuja.bakingapp.model.RecipeModel;

public class RecipeStepsActivity extends AppCompatActivity {

    public static final String TAG_RECIPE_STEPS_FRAG = "recipe_steps_frag";
    public static final String BUNDLE_RECIPE_STEP_POSITION = "bundle_recipe_step_position";
    public static final String BUNDLE_RECIPE = "bundle_recipe";

    private int mStepPosition;
    private RecipeModel mRecipe;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_step);

        setUpActionBar();

        mStepPosition = getIntent().getIntExtra(RecipeDetailsFragment.INTENT_RECIPE_STEP_POSITION, 0);
        mRecipe = getIntent().getParcelableExtra(RecipeDetailsFragment.INTENT_RECIPE);
        setUpFragmentContainer();
    }

    /**
     * function called to set up action bar and its title
     */
    private void setUpActionBar() {
        ActionBar actionBar = getSupportActionBar();

        if(actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            if(mRecipe != null)
                actionBar.setTitle(mRecipe.getName());
        }
    }

    /**
     * function called to set up the container and add
     * the recipe details fragment to it
     */
    private void setUpFragmentContainer() {

        RecipeStepsFragment recipeStepsFragment = new RecipeStepsFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(BUNDLE_RECIPE_STEP_POSITION, mStepPosition);
        bundle.putParcelable(BUNDLE_RECIPE, mRecipe);
        recipeStepsFragment.setArguments(bundle);
        FragmentUtils.addFragment(getSupportFragmentManager(), recipeStepsFragment, R.id.step_fragment_container, TAG_RECIPE_STEPS_FRAG);
    }
}
