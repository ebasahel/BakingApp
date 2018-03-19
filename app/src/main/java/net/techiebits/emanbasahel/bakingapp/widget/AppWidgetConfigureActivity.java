package net.techiebits.emanbasahel.bakingapp.widget;

import android.app.Activity;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import net.techiebits.emanbasahel.bakingapp.R;
import net.techiebits.emanbasahel.bakingapp.data.RecipesModel;
import net.techiebits.emanbasahel.bakingapp.helpers.ReadingRecipes;
import net.techiebits.emanbasahel.bakingapp.helpers.RecipesListAdapter;
import net.techiebits.emanbasahel.bakingapp.views.RecipeDetailActivity;

public class AppWidgetConfigureActivity extends Activity {

    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView mRecyclerView;
    int mAppWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID;
    final Context context = AppWidgetConfigureActivity.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Set the result to CANCELED.  This will cause the widget host to cancel
        // out of the widget placement if they press the back button.
        setResult(RESULT_CANCELED);
        setContentView(R.layout.activity_app_widget_configure);

        // Find the widget id from the intent.
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            mAppWidgetId = extras.getInt(
                    AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
        }
        // If they gave us an intent without the widget id, just bail.
        if (mAppWidgetId == AppWidgetManager.INVALID_APPWIDGET_ID) {
            finish();
        }

        mRecyclerView = findViewById(R.id.recycler_widget);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        assert mRecyclerView != null;
        setupRecyclerView((RecyclerView) mRecyclerView);
    }

    //region handling list of Recipes
    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {

        //endregion

        recyclerView.setAdapter(new RecipesListAdapter(new RecipesListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(RecipesModel itemRecipe) {

                // Make sure we pass back the original appWidgetId
                Intent resultValue = new Intent();
                resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, mAppWidgetId);
                setResult(RESULT_OK, resultValue);

                // Push widget update to surface with newly set prefix
                AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
                IngredientListWidget ingredientListWidget = new IngredientListWidget();
                ingredientListWidget.updateAppWidget(context, appWidgetManager,
                        mAppWidgetId,itemRecipe.getId(),itemRecipe.getName());

                finish();

            }
        }, ReadingRecipes.getInstance(this).getRecipes()));
    }
    //endregion

}
