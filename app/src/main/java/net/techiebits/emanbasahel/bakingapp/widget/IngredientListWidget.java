package net.techiebits.emanbasahel.bakingapp.widget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import net.techiebits.emanbasahel.bakingapp.R;
import net.techiebits.emanbasahel.bakingapp.data.RecipesModel;
import net.techiebits.emanbasahel.bakingapp.data.webservice.ApiClient;
import net.techiebits.emanbasahel.bakingapp.data.webservice.ApiInterface;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Implementation of App Widget functionality.
 */
public class IngredientListWidget extends AppWidgetProvider {

    private  int mRecipeId;
    private String mRecipeName;
    private RecipesModel mRecipesModel;
    Intent intent;
    public void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId, int recipeId, String recipeName,RecipesModel recipesModel) {
        mRecipeId=recipeId;
        mRecipeName=recipeName;
        mRecipesModel=recipesModel;
        updateRemoteViews(context,appWidgetManager,appWidgetId,recipeName,mRecipesModel);

    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateRemoteViews(context,appWidgetManager,appWidgetId,mRecipeName,mRecipesModel);
        }
        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }

    //region update widget data
    //get configuration of widget (which recipe's ingredient will be shown)
    private void updateRemoteViews(Context context, AppWidgetManager appWidgetManager,
                                  int appWidgetId,String recipeName,RecipesModel recipesModel)
    {
        intent = new Intent(context, IngredientWidgetService.class);
        Bundle extrasBundle = new Bundle();
        extrasBundle.putParcelable(context.getString(R.string.title_recipe), recipesModel);
        extrasBundle.putInt(context.getString(R.string.recipe_id), mRecipeId);
        extrasBundle.putInt(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        intent.putExtra(context.getString(R.string.bundle), extrasBundle);
        intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));
        RemoteViews rv = new RemoteViews(context.getPackageName(), R.layout.ingredient_list_widget);
        rv.setTextViewText(R.id.recipe_widget_name,recipeName);
        rv.setRemoteAdapter(R.id.list_ingredients, intent);
        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetId, R.id.list_ingredients);
        appWidgetManager.updateAppWidget(appWidgetId, rv);

    }
    //endregion

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

