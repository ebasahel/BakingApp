package net.techiebits.emanbasahel.bakingapp.views;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import net.techiebits.emanbasahel.bakingapp.R;
import net.techiebits.emanbasahel.bakingapp.data.RecipesModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class IngredientFragment extends Fragment {

    //region variables
    private RecipesModel recipesModel;
    private RecyclerView recyclerIngredients;
    private RecyclerView.LayoutManager mLayoutManager;
    private IngredientsAdapter mAdapter;
    private TextView test;
    //endregion

    public IngredientFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //region init
        View rootView =inflater.inflate(R.layout.fragment_ingredient, container, false);
        test = (TextView) rootView.findViewById(R.id.test);
        if (getArguments()!=null)
            recipesModel=getArguments().getParcelable(getString(R.string.title_argument_recipe));

        //region init recyclerview
//        recyclerIngredients = (RecyclerView) rootView.findViewById(R.id.recycler_ingredients);
//        mLayoutManager = new LinearLayoutManager(getActivity());
//        mAdapter= new IngredientsAdapter(recipesModel);
//        recyclerIngredients.setLayoutManager(mLayoutManager);
//        recyclerIngredients.setAdapter(mAdapter);
        //endregion

        //endregion
        return rootView;
    }

    //region RecyclerView Adapter class
    private class IngredientsAdapter extends RecyclerView.Adapter<IngredientsAdapter.ViewHolder>
    {

        RecipesModel mRecipeModel;
        public IngredientsAdapter(RecipesModel recipemodle)
        {
            mRecipeModel =recipemodle;
        }
        class ViewHolder extends RecyclerView.ViewHolder
        {

            TextView txtIngredient;
            public ViewHolder(View itemView) {
                super(itemView);
                txtIngredient= (TextView) itemView.findViewById(R.id.txt_ingredient);
            }
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.ingredient_item, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            holder.txtIngredient.setText(String.format(getString(R.string.text_ingredient),
                    mRecipeModel.getIngredients().get(position).getQuantity(),
                    mRecipeModel.getIngredients().get(position).getMeasure(),
                    mRecipeModel.getIngredients().get(position).getIngredient()));
        }

        @Override
        public int getItemCount() {
            return mRecipeModel.getIngredients().size();
        }


    }
    //endregion
}
