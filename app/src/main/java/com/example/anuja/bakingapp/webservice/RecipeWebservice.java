package com.example.anuja.bakingapp.webservice;

import com.example.anuja.bakingapp.model.RecipeModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * An Interface (Retrofit turns your HTTP API into a Java interface.)
 */
public interface RecipeWebservice {

    @GET(RecipeApiConstants.RECIPE_URL_ENDPOINT)
    Call<List<RecipeModel>> getBakingRecipes();
}
