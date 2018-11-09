package com.example.anuja.bakingapp.app.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.anuja.bakingapp.R;
import com.example.anuja.bakingapp.databinding.FragmentRecipeDetailStepsItemBinding;
import com.example.anuja.bakingapp.model.Steps;

import java.util.List;
import java.util.Locale;

/**
 * Reference - https://stackoverflow.com/questions/27194044/how-to-properly-highlight-selected-item-on-recyclerview
 */
public class RecipeDetailStepsAdapter extends RecyclerView.Adapter<RecipeDetailStepsAdapter.RecipeStepsViewHolder> {

    private List<Steps> stepsList;
    private int stepId;
    private int currentPosition;
    private Context context;
    private StepListItemClickListener listItemClickListener;

    public interface StepListItemClickListener {
        void onStepItemClick(int position);
    }

    public RecipeDetailStepsAdapter(List<Steps> stepsList, StepListItemClickListener clickListener) {
        this.stepsList = stepsList;
        this.listItemClickListener = clickListener;
    }

    @NonNull
    @Override
    public RecipeStepsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        context = viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        return new RecipeStepsViewHolder(FragmentRecipeDetailStepsItemBinding.inflate(inflater, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeStepsViewHolder recipeStepsViewHolder, int i) {
        Steps step = stepsList.get(i);
        recipeStepsViewHolder.bind(step, i);
        recipeStepsViewHolder.itemView.setSelected(currentPosition == i);
    }

    @Override
    public int getItemCount() {
        if(stepsList == null)
            return 0;
        return stepsList.size();
    }

    public class RecipeStepsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        FragmentRecipeDetailStepsItemBinding binding;

        public RecipeStepsViewHolder(FragmentRecipeDetailStepsItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            itemView.setOnClickListener(this);
        }

        public void bind(Steps step, int position) {
            stepId = step.getId();
            String description = step.getShortDescription();

            binding.tvRecipeStepDescription.setText(String.format(Locale.US, "%d. %s", stepId, description));
            if(!TextUtils.isEmpty(step.getVideoURL()))
                binding.ivRecipeStepVideoIcon.setVisibility(View.VISIBLE);
            else
                binding.ivRecipeStepVideoIcon.setVisibility(View.INVISIBLE);

            if(currentPosition == position) {
                binding.tvRecipeStepDescription.setTextColor(context.getColor(R.color.white));
                binding.rlDetailsStep.setBackgroundColor(context.getColor(R.color.colorPrimary));
            }
            else {
                binding.tvRecipeStepDescription.setTextColor(context.getColor(R.color.black));
                binding.rlDetailsStep.setBackgroundColor(context.getColor(R.color.white));
            }
        }

        @Override
        public void onClick(View view) {
            notifyItemChanged(currentPosition);
            currentPosition = getLayoutPosition();
            listItemClickListener.onStepItemClick(getAdapterPosition());
            notifyItemChanged(currentPosition);
        }
    }
}
