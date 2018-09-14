package com.example.anuja.bakingapp.app.fragments;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.anuja.bakingapp.R;
import com.example.anuja.bakingapp.app.activities.RecipeDetailsActivity;
import com.example.anuja.bakingapp.app.activities.RecipeStepsActivity;
import com.example.anuja.bakingapp.app.adapters.RecipeDetailStepsAdapter;
import com.example.anuja.bakingapp.app.adapters.RecipeListAdapter;
import com.example.anuja.bakingapp.app.callback.OnRecipeStepSelected;
import com.example.anuja.bakingapp.app.common.Utils;
import com.example.anuja.bakingapp.databinding.FragmentRecipeDetailBinding;
import com.example.anuja.bakingapp.model.RecipeModel;

public class RecipeDetailsFragment extends Fragment implements RecipeDetailStepsAdapter.StepListItemClickListener {

    public static final String INTENT_RECIPE_STEP_POSITION = "intent_recipe_step_position";
    public static final String INTENT_RECIPE = "intent_recipe";

    private RecipeModel mRecipe;
    private FragmentRecipeDetailBinding recipeDetailBinding;

    private boolean isTwoPaneMode;
    private RecipeDetailStepsAdapter adapter;
    private OnRecipeStepSelected recipeStepSelectedCallback;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRetainInstance(true);
        isTwoPaneMode = getResources().getBoolean(R.bool.two_pane_mode);

        mRecipe = getArguments().getParcelable(RecipeDetailsActivity.BUNDLE_RECIPE);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            recipeStepSelectedCallback = (OnRecipeStepSelected) context;
        }catch(Exception e) {
            e.printStackTrace();
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        recipeDetailBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_recipe_detail, container, false);

        return recipeDetailBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if(mRecipe != null) {
            displayIngredients();
            setUpRecyclerView();
        }
    }

    /**
     * function to display the recipe ingredients
     */
    private void displayIngredients() {
        recipeDetailBinding.tvRecipeIngredients.setText(Html.fromHtml(Utils.parseIngredientsToString(mRecipe.getIngredients())));
    }

    /**
     * function called to set up the steps recycler view
     */
    private void setUpRecyclerView() {
        adapter = new RecipeDetailStepsAdapter(mRecipe.getSteps(), this);
        recipeDetailBinding.rvRecipeSteps.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        recipeDetailBinding.rvRecipeSteps.setAdapter(adapter);
        recipeDetailBinding.rvRecipeSteps.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        recipeDetailBinding.rvRecipeSteps.setHasFixedSize(true);
    }

    @Override
    public void onStepItemClick(int position) {
        if(!isTwoPaneMode) {
            Intent intent = new Intent(getContext(), RecipeStepsActivity.class);
            intent.putExtra(INTENT_RECIPE, mRecipe);
            intent.putExtra(INTENT_RECIPE_STEP_POSITION, position);
            startActivity(intent);
        }
        else {
            recipeStepSelectedCallback.onRecipeStepSelected(mRecipe, position);
        }

    }
}
