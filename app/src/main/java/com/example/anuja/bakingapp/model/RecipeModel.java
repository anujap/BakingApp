package com.example.anuja.bakingapp.model;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.os.Parcel;
import android.os.Parcelable;

import com.example.anuja.bakingapp.BR;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Model class for Baking recipes
 */
public class RecipeModel extends BaseObservable implements Parcelable {

    @SerializedName("id")
    @Expose
    private Integer id;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("ingredients")
    @Expose
    private List<Ingredients> ingredients;

    @SerializedName("steps")
    @Expose
    private List<Steps> steps;

    @SerializedName("servings")
    @Expose
    private Integer servings;

    @SerializedName("image")
    @Expose
    private String image;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Bindable
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        notifyPropertyChanged(BR.name);
    }

    @Bindable
    public Integer getServings() {
        return servings;
    }

    public void setServings(Integer servings) {
        this.servings = servings;
        notifyPropertyChanged(BR.servings);
    }

    @Bindable
    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
        notifyPropertyChanged(BR.image);
    }

    public List<Ingredients> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<Ingredients> ingredients) {
        this.ingredients = ingredients;
    }

    public List<Steps> getSteps() {
        return steps;
    }

    public void setSteps(List<Steps> steps) {
        this.steps = steps;
    }

    protected RecipeModel(Parcel in) {
        this.id = in.readInt();
        this.name = in.readString();
        this.ingredients = new ArrayList<Ingredients>();
        in.readList(this.ingredients, Ingredients.class.getClassLoader());
        this.steps = new ArrayList<Steps>();
        in.readList(this.steps, Steps.class.getClassLoader());
        this.servings = in.readInt();
        this.image = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.id);
        parcel.writeString(this.name);
        parcel.writeList(this.ingredients);
        parcel.writeList(this.steps);
        parcel.writeInt(this.servings);
        parcel.writeString(this.image);
    }

    public static final Parcelable.Creator<RecipeModel> CREATOR = new Parcelable.Creator<RecipeModel>() {

        @Override
        public RecipeModel createFromParcel(Parcel parcel) {
            return new RecipeModel(parcel);
        }

        @Override
        public RecipeModel[] newArray(int i) {
            return new RecipeModel[i];
        }
    };
}
