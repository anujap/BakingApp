package com.example.anuja.bakingapp.app.common;

import com.example.anuja.bakingapp.model.Ingredients;

import java.util.List;

public class Utils {

    public static String parseIngredientsToString(List<Ingredients> ingredientsList) {
        StringBuilder builder = new StringBuilder();

        int count = 1;
        builder.append("<b>")
                .append("Ingredients List")
                .append("</b>")
                .append("<br /> <br />");
        for(Ingredients ingredients : ingredientsList) {
            builder.append(count)
                    .append(". ")
                    .append(ingredients.getIngredient())
                    .append(" (")
                    .append(ingredients.getQuantity())
                    .append(" " + ingredients.getMeasure() + ")")
                    .append("<br />");
            count++;
        }

        return builder.toString();
    }
}
