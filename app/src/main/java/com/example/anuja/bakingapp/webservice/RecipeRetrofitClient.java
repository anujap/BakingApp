package com.example.anuja.bakingapp.webservice;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RecipeRetrofitClient {

    private static RecipeWebservice recipeWebservice;
    private static  RecipeRetrofitClient client;

    public synchronized static RecipeRetrofitClient getInstance() {

        if(client == null) {
            client = new RecipeRetrofitClient();
        }
        return client;
    }

    private RecipeRetrofitClient() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(RecipeApiConstants.RECIPE_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        recipeWebservice = retrofit.create(RecipeWebservice.class);
    }

    public static RecipeWebservice getRecipeWebservice() {
        return recipeWebservice;
    }
}
