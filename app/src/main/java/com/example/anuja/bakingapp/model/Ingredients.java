package com.example.anuja.bakingapp.model;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.os.Parcel;
import android.os.Parcelable;

import com.example.anuja.bakingapp.BR;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Model class for recipe ingredients
 */
public class Ingredients extends BaseObservable implements Parcelable {

    @SerializedName("quantity")
    @Expose
    private double quantity;

    @SerializedName("measure")
    @Expose
    private String measure;

    @SerializedName("ingredient")
    @Expose
    private String ingredient;

    @Bindable
    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
        notifyPropertyChanged(BR.quantity);
    }

    @Bindable
    public String getMeasure() {
        return measure;
    }

    public void setMeasure(String measure) {
        this.measure = measure;
        notifyPropertyChanged(BR.measure);
    }

    @Bindable
    public String getIngredient() {
        return ingredient;
    }

    public void setIngredient(String ingredient) {
        this.ingredient = ingredient;
        notifyPropertyChanged(BR.ingredient);
    }

    protected Ingredients(Parcel in) {
        this.quantity = in.readDouble();
        this.measure = in.readString();
        this.ingredient = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeDouble(this.quantity);
        parcel.writeString(this.measure);
        parcel.writeString(this.ingredient);
    }

    public static final Parcelable.Creator<Ingredients> CREATOR = new Parcelable.Creator<Ingredients>() {
        @Override
        public Ingredients createFromParcel(Parcel parcel) {
            return new Ingredients(parcel);
        }

        @Override
        public Ingredients[] newArray(int i) {
            return new Ingredients[i];
        }
    };
}
