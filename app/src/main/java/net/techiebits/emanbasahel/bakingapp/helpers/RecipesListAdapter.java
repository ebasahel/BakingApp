package net.techiebits.emanbasahel.bakingapp.helpers;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import net.techiebits.emanbasahel.bakingapp.R;
import net.techiebits.emanbasahel.bakingapp.data.RecipesModel;
import java.util.List;

/**
 * Created by emanbasahel on 16/02/2018 AD.
 */

public class RecipesListAdapter
        extends RecyclerView.Adapter<RecipesListAdapter.RecipeViewHolder> {

    private final List<RecipesModel> mRecipes;

    public interface OnItemClickListener {

        void onItemClick(RecipesModel itemRecipe);

    }

    OnItemClickListener mListener;

    public RecipesListAdapter(OnItemClickListener listener,
                              List<RecipesModel> recipes) {
        mRecipes = recipes;
        mListener = listener;
    }

    @Override
    public RecipeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recipe_list_content, parent, false);
        return new RecipeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecipeViewHolder holder, int position) {
        holder.bind(mRecipes.get(position), mListener);
    }

    @Override
    public int getItemCount() {
        return mRecipes.size();
    }

    public class RecipeViewHolder extends RecyclerView.ViewHolder {
        final TextView mIdView;
        final TextView mContentView;
        final LinearLayout mLinearContainer;

        RecipeViewHolder(View view) {
            super(view);
            mIdView = (TextView) view.findViewById(R.id.id_text);
            mContentView = (TextView) view.findViewById(R.id.content);
            mLinearContainer = (LinearLayout) view.findViewById(R.id.linear_container);

        }

        void bind(final RecipesModel recipesModel, final OnItemClickListener listener) {
            mIdView.setText(String.valueOf(recipesModel.getId()));
            mContentView.setText(recipesModel.getName());
            mLinearContainer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mListener.onItemClick(recipesModel);
                }
            });
        }
    }
}
