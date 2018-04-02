package net.techiebits.emanbasahel.bakingapp.views;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import net.techiebits.emanbasahel.bakingapp.R;
import net.techiebits.emanbasahel.bakingapp.data.RecipesModel;
import net.techiebits.emanbasahel.bakingapp.data.webservice.ApiClient;
import net.techiebits.emanbasahel.bakingapp.data.webservice.ApiInterface;
import net.techiebits.emanbasahel.bakingapp.helpers.RecipesListAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static junit.framework.Assert.assertEquals;

/**
 * An activity representing a list of Recipes. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link RecipeDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class RecipeListActivity extends AppCompatActivity {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;
    private List<RecipesModel> recipesModelList;
    RecyclerView recyclerView;
    private RecipesListAdapter mAdapter;
    LinearLayoutManager mLinearLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

        if (findViewById(R.id.recipe_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;
        }
        recipesModelList = new ArrayList<>();
        recyclerView = findViewById(R.id.recipe_list);
        assert recyclerView != null;
        mLinearLayoutManager= new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLinearLayoutManager);
        mAdapter= setupRecyclerView();
        recyclerView.setAdapter(mAdapter);
        getRecipes();
    }

    //region getRecipes
    public void getRecipes() {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<List<RecipesModel>> call = apiService.getRecipes();
        call.enqueue(new Callback<List<RecipesModel>>() {

            @Override
            public void onResponse(Call<List<RecipesModel>> call, Response<List<RecipesModel>> response) {
                recipesModelList.clear();
                recipesModelList.addAll(response.body());
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<RecipesModel>> call, Throwable t) {
                Toast.makeText(RecipeListActivity.this, getString(R.string.noData), Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
    //endregion
    //region handling list of Recipes
    private RecipesListAdapter setupRecyclerView() {

        //endregion

        return new RecipesListAdapter(new RecipesListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(RecipesModel itemRecipe) {
                if (mTwoPane) {
                    Bundle arguments = new Bundle();
                    arguments.putParcelable(getString(R.string.title_recipe),itemRecipe);
                    arguments.putBoolean(getString(R.string.is_two_pane),mTwoPane);
                    RecipeDetailFragment fragment = new RecipeDetailFragment();
                    fragment.setArguments(arguments);
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.recipe_detail_container, fragment)
                            .commit();
                } else {

                    Intent intent = new Intent(RecipeListActivity.this, RecipeDetailActivity.class);
                    intent.putExtra(getString(R.string.title_recipe),itemRecipe );
                    startActivity(intent);
                }
            }
        }, recipesModelList);
    }
    //endregion




}
