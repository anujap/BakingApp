package com.example.anuja.bakingapp.app.activities;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.IdlingResource;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.example.anuja.bakingapp.R;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.core.StringStartsWith.startsWith;

@RunWith(AndroidJUnit4.class)
public class RecipeStepsActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    private IdlingResource mIdlingResource;

    @Before
    public void registerIdlingResource() {
        mIdlingResource = mActivityTestRule.getActivity().getIdlingResource();

        Espresso.registerIdlingResources(mIdlingResource);
    }

    @Test
    public void navigateToStepsActivity() {

        // 1st element - Nutella Pie clicked
        onView(withId(R.id.rv_recipe_list)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        // check if the ingredients text is visible.
        onView(withId(R.id.tv_recipe_ingredients)).check(matches(withText("Ingredients")));

        // clicked step-1
        onView(withId(R.id.rv_recipe_steps))
                .perform(RecyclerViewActions
                        .actionOnItemAtPosition(0, click()));

        // check if the exoplayer is visible
        onView(withId(R.id.player_view)).check(matches(isDisplayed()));

        // Previous button should be disabled
        onView(withId(R.id.previousStepBtn)).check(matches(not(isDisplayed())));

        // next button should be visible
        onView(withId(R.id.nextStepBtn)).check(matches(isDisplayed()));
    }

    @Test
    public void navigateToRecipeStepsActivityAndDisplayButtons() {
        // 1st element - Nutella Pie clicked
        onView(withId(R.id.rv_recipe_list)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        // check if the ingredients text is visible.
        onView(withId(R.id.tv_recipe_ingredients)).check(matches(withText("Ingredients")));

        // clicked step-1
        onView(withId(R.id.rv_recipe_steps))
                .perform(RecyclerViewActions
                        .actionOnItemAtPosition(1, click()));

        // check if the exoplayer is visible
        onView(withId(R.id.player_view)).check(matches(isDisplayed()));

        // Previous button should be displayed
        onView(withId(R.id.previousStepBtn)).check(matches(isDisplayed()));

        // next button should be not be displayed
        onView(withId(R.id.nextStepBtn)).check(matches(isDisplayed()));
    }

    @After
    public void unregisterIdlingResource() {
        if (mIdlingResource != null) {
            Espresso.unregisterIdlingResources(mIdlingResource);
        }
    }
}
