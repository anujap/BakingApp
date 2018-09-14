package com.example.anuja.bakingapp.app.adapters;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.anuja.bakingapp.BR;
import com.example.anuja.bakingapp.databinding.RecipeListItemBinding;
import com.example.anuja.bakingapp.model.RecipeModel;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Adapter class to display the recipe list item
 * Reference:- http://www.vogella.com/tutorials/AndroidDatabinding/article.html
 */
public class RecipeListAdapter extends RecyclerView.Adapter<RecipeListAdapter.RecipeListViewHolder> {

    private List<RecipeModel> recipeList;
    private Context context;
    private RecipeListItemClickListener listItemClickListener;

    public interface RecipeListItemClickListener {
        void onListItemClick(Parcelable item);
    }

    public RecipeListAdapter(List<RecipeModel> recipeList, RecipeListItemClickListener listItemClickListener) {
        this.recipeList = recipeList;
        this.listItemClickListener = listItemClickListener;
    }

    @NonNull
    @Override
    public RecipeListViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        context = viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        return new RecipeListViewHolder(RecipeListItemBinding.inflate(inflater, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeListViewHolder recipeListViewHolder, int position) {
        RecipeModel recipe = recipeList.get(position);
        recipeListViewHolder.bind(recipe);
    }

    // to register a custom converter
    @BindingAdapter({"android:recipeImage"})
    public static void loadImage(ImageView imageView, String recipeImageUrl) {

        if(!TextUtils.isEmpty(recipeImageUrl)) {
            // add placeholder
            Picasso.with(imageView.getContext())
                    .load(recipeImageUrl)
                    .fit()
                    .into(imageView);
        }
    }

    @Override
    public int getItemCount() {
        if(recipeList == null)
            return 0;
        return recipeList.size();
    }

    public class RecipeListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private RecipeListItemBinding itemBinding;

        public RecipeListViewHolder(RecipeListItemBinding itemBinding) {
            super(itemBinding.getRoot());
            this.itemBinding = itemBinding;
            itemView.setOnClickListener(this);
        }


        public void bind(RecipeModel obj) {
            itemBinding.setVariable(BR.recipe_data, obj);
            itemBinding.executePendingBindings();
        }

        @Override
        public void onClick(View view) {
            RecipeModel recipe = recipeList.get(getAdapterPosition());
            listItemClickListener.onListItemClick(recipe);
        }
    }

    public void swapLists(List<RecipeModel> recipeList) {
        this.recipeList = recipeList;
        notifyDataSetChanged();
    }
}