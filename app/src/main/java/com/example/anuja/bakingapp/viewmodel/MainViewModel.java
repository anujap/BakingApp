package com.example.anuja.bakingapp.viewmodel;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import com.example.anuja.bakingapp.model.RecipeModel;
import com.example.anuja.bakingapp.webservice.RecipeRetrofitClient;
import com.example.anuja.bakingapp.webservice.RecipeWebservice;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * This is the View Model class
 */
public class MainViewModel extends ViewModel {

    private static final String TAG = "MainViewModel";

    private RecipeWebservice recipeWebservice = RecipeRetrofitClient.getInstance().getRecipeWebservice();

    private MutableLiveData<List<RecipeModel>> recipesList;

    public MutableLiveData<List<RecipeModel>> getRecipesList() {
        if(recipesList == null)
            recipesList = new MutableLiveData<>();
        return recipesList;
    }

    /**
     * function used to download recipes
     */
    public void displayRecipes() {
        if(recipesList == null) {
            recipeWebservice.getBakingRecipes().enqueue(new Callback<List<RecipeModel>>() {
                @Override
                public void onResponse(Call<List<RecipeModel>> call, Response<List<RecipeModel>> response) {
                    if(response.isSuccessful()) {
                        List<RecipeModel> recipeModelList = response.body();

                        if(recipeModelList != null && recipeModelList.size() > 0) {
                            recipesList.postValue(recipeModelList);
                        }
                    }
                }

                @Override
                public void onFailure(Call<List<RecipeModel>> call, Throwable t) {
                    Log.e(TAG, "Error retrieving the recipes");
                }
            });
        }
    }
}
