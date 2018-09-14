package com.example.anuja.bakingapp.viewmodel;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.view.View;
import android.widget.ImageView;

import com.example.anuja.bakingapp.R;
import com.example.anuja.bakingapp.model.RecipeModel;
import com.example.anuja.bakingapp.model.Steps;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.RenderersFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.squareup.picasso.Picasso;

public class RecipeStepsViewModel {

    private ObservableField<String> strInstructions;
    private ObservableInt previousButtonVisibility;
    private ObservableInt nextButtonVisibility;
    private ObservableBoolean isExoPlayerInitialized;
    private ObservableField<String> stepThumbnailUrl;

    private RecipeModel mRecipe;
    private int currentStepPosition;

    private static ExoPlayer mExoPlayer;
    private Context context;

    private String mCurrentVideoUrl;

    public RecipeStepsViewModel(Context context, RecipeModel mRecipe, int currentStepPosition) {
        this.context = context;
        this.mRecipe = mRecipe;
        this.currentStepPosition = currentStepPosition;

        strInstructions = new ObservableField<>("");
        previousButtonVisibility = new ObservableInt(View.GONE);
        nextButtonVisibility = new ObservableInt(View.GONE);
        isExoPlayerInitialized = new ObservableBoolean(false);
        stepThumbnailUrl = new ObservableField<>("");
        initializeData(mRecipe.getSteps().get(currentStepPosition));
    }

    public ObservableField<String> getStrInstructions() {
        return strInstructions;
    }

    public ObservableInt getPreviousButtonVisibility() {
        return previousButtonVisibility;
    }

    public ObservableInt getNextButtonVisibility() {
        return nextButtonVisibility;
    }

    public ObservableBoolean getIsExoPlayerInitialized() {
        return isExoPlayerInitialized;
    }

    public ObservableField<String> getStepThumbnailUrl() {
        return stepThumbnailUrl;
    }

    public String getCurrentVideoUrl() {
        return mCurrentVideoUrl;
    }

    public static ExoPlayer getExoPlayer() {
        return mExoPlayer;
    }

    private void initializeData(Steps step) {
        if (step != null) {
            mCurrentVideoUrl = step.getVideoURL();
            stepThumbnailUrl.set(step.getThumbnailURL());
            strInstructions.set(step.getDescription());
            previousStepBtnVisibility();
            nextStepBtnVisibility();
        }
    }

    public void initializeExoPlayer(String videoUrl, long videoPosition, boolean playWhenReady) {
        if (mExoPlayer == null) {

            RenderersFactory renderersFactory = new DefaultRenderersFactory(context);
            TrackSelector trackSelector = new DefaultTrackSelector();
            LoadControl loadControl = new DefaultLoadControl();
            mExoPlayer = ExoPlayerFactory.newSimpleInstance(renderersFactory, trackSelector, loadControl);
        }

        Uri uri = Uri.parse(videoUrl);
        MediaSource mediaSource = buildMediaSource(uri);
        mExoPlayer.prepare(mediaSource, true, false);
        mExoPlayer.seekTo(videoPosition);
        mExoPlayer.setPlayWhenReady(playWhenReady);
        isExoPlayerInitialized.set(true);
        isExoPlayerInitialized.notifyChange();
    }

    private MediaSource buildMediaSource(Uri videoUri) {

        // Measures bandwidth during playback. Can be null if not required.
        DefaultBandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();

        // Produces DataSource instances through which media data is loaded.
        DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(context,
                Util.getUserAgent(context, "BakingApp"), bandwidthMeter);

        // This is the MediaSource representing the media to be played.
        return new ExtractorMediaSource.Factory(dataSourceFactory)
                .createMediaSource(videoUri);
    }

    public void releaseExoPlayer() {
        if (mExoPlayer != null) {
            mExoPlayer.stop();
            mExoPlayer.release();
            mExoPlayer = null;
        }
    }

    @BindingAdapter("setPlayerView")
    public static void setPlayerView(PlayerView playerView, boolean isExoPlayerInitialized) {
        if (playerView != null) {

            if (!isExoPlayerInitialized) {
                playerView.setDefaultArtwork(BitmapFactory.decodeResource(playerView.getResources(),
                        R.drawable.placeholder));
            } else {
                playerView.setPlayer(mExoPlayer);
            }
        }
    }

    public void onPreviousBtnClicked(View view) {
        currentStepPosition = currentStepPosition - 1;
        if (mRecipe.getSteps().get(currentStepPosition) != null) {
            Steps previousStep = mRecipe.getSteps().get(currentStepPosition);
            initializeData(previousStep);
            if (!previousStep.getVideoURL().isEmpty()) {
                initializeExoPlayer(previousStep.getVideoURL(), 0, true);
            }
        }
    }

    public void onNextBtnClicked(View view) {
        currentStepPosition = currentStepPosition + 1;
        if (mRecipe.getSteps().get(currentStepPosition) != null) {
            Steps nextStep = mRecipe.getSteps().get(currentStepPosition);
            initializeData(nextStep);
            if (!nextStep.getVideoURL().isEmpty()) {
                initializeExoPlayer(nextStep.getVideoURL(), 0, true);
            }
        }
    }

    // Method to handle previous button visibility depending on the current step.
    private void previousStepBtnVisibility() {

        if (currentStepPosition == 0) {
            // Selected step position is the first position
            previousButtonVisibility.set(View.GONE);
        } else {
            previousButtonVisibility.set(View.VISIBLE);
        }
    }

    // Method to handle next button visibility depending on the current step.
    private void nextStepBtnVisibility() {
        if (currentStepPosition >= mRecipe.getSteps().size() - 1) {
            // If the selected step position is the last position
            nextButtonVisibility.set(View.GONE);
        } else {
            nextButtonVisibility.set(View.VISIBLE);
        }
    }

    @BindingAdapter("setStepThumbnail")
    public static void setStepThumbnail(ImageView imageView, String path) {
        if (imageView != null && path != null && !path.isEmpty()) {
            // If the path is not empty.
            Picasso.with(imageView.getContext())
                    .load(path)
                    .placeholder(R.drawable.recipe_vector)
                    .error(R.drawable.recipe_vector)
                    .into(imageView);

        } else {
            // If the path is empty.
            imageView.setImageResource(R.drawable.recipe_vector);
        }
    }
}
