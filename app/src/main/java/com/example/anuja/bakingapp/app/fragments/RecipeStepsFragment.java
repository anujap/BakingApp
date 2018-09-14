package com.example.anuja.bakingapp.app.fragments;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.anuja.bakingapp.R;
import com.example.anuja.bakingapp.app.activities.RecipeStepsActivity;
import com.example.anuja.bakingapp.databinding.FragmentRecipeStepBinding;
import com.example.anuja.bakingapp.model.RecipeModel;
import com.example.anuja.bakingapp.viewmodel.RecipeStepsViewModel;
import com.google.android.exoplayer2.ExoPlayer;

public class RecipeStepsFragment extends Fragment {

    private static final String EXOPLAYER_VIDEO_POSITION = "exoPlayerVideoPosition";
    private static final String EXOPLAYER_PLAY_WHEN_READY = "exoPlayerPlayWhenReady";

    private FragmentRecipeStepBinding binding;

    private RecipeStepsViewModel viewModel;

    private boolean playWhenReady = true;
    private long currentVideoPosition = 0;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRetainInstance(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_recipe_step, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (savedInstanceState == null) {
            RecipeModel mRecipe = getArguments().getParcelable(RecipeStepsActivity.BUNDLE_RECIPE);
            int currentPosition = getArguments().getInt(RecipeStepsActivity.BUNDLE_RECIPE_STEP_POSITION);

            if (currentPosition != -1) {
                viewModel = new RecipeStepsViewModel(getContext(), mRecipe, currentPosition);
            }
        } else {
            playWhenReady = savedInstanceState.getBoolean(EXOPLAYER_PLAY_WHEN_READY);
            currentVideoPosition = savedInstanceState.getLong(EXOPLAYER_VIDEO_POSITION);
        }
        binding.setStepsViewModel(viewModel);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (viewModel != null) {
            String url = viewModel.getCurrentVideoUrl();
            if(!url.isEmpty()) {
                viewModel.initializeExoPlayer(viewModel.getCurrentVideoUrl(), currentVideoPosition, playWhenReady);
            }
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        if (viewModel != null) {
            ExoPlayer exoPlayer = RecipeStepsViewModel.getExoPlayer();
            currentVideoPosition = exoPlayer.getCurrentPosition();
            playWhenReady = exoPlayer.getPlayWhenReady();
            outState.putLong(EXOPLAYER_VIDEO_POSITION, currentVideoPosition);
            outState.putBoolean(EXOPLAYER_PLAY_WHEN_READY, playWhenReady);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (viewModel != null) {
            viewModel.releaseExoPlayer();
        }
    }
}
