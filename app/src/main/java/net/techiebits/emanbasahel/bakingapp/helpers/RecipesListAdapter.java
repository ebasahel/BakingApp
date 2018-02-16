package net.techiebits.emanbasahel.bakingapp.helpers;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import net.techiebits.emanbasahel.bakingapp.R;
import net.techiebits.emanbasahel.bakingapp.data.RecipesModel;
import net.techiebits.emanbasahel.bakingapp.views.RecipeDetailActivity;
import net.techiebits.emanbasahel.bakingapp.views.RecipeDetailFragment;
import net.techiebits.emanbasahel.bakingapp.views.RecipeListActivity;

import java.util.List;

/**
 * Created by emanbasahel on 16/02/2018 AD.
 */

public class RecipesListAdapter
        extends RecyclerView.Adapter<RecipesListAdapter.ViewHolder> {

    //    private final RecipeListActivity mParentActivity;
    private final List<RecipesModel> mRecipes;

    public interface OnItemClickListener {

        void onItemClick(RecipesModel itemRecipe);

    }

    OnItemClickListener mListener;

    public RecipesListAdapter(OnItemClickListener listener,
                              List<RecipesModel> recipes) {
        mRecipes = recipes;
        mListener = listener;
//        mParentActivity = parent;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recipe_list_content, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.bind(mRecipes.get(position), mListener);
    }

    @Override
    public int getItemCount() {
        return mRecipes.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        final TextView mIdView;
        final TextView mContentView;

        ViewHolder(View view) {
            super(view);
            mIdView = (TextView) view.findViewById(R.id.id_text);
            mContentView = (TextView) view.findViewById(R.id.content);
        }

        void bind(final RecipesModel recipesModel, final OnItemClickListener listener) {
            mIdView.setText(recipesModel.getId());
            mContentView.setText(recipesModel.getName());
            mIdView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mListener.onItemClick(recipesModel);
                }
            });
        }
    }
}
