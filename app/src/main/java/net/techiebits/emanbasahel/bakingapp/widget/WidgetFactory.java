package net.techiebits.emanbasahel.bakingapp.widget;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;
import net.techiebits.emanbasahel.bakingapp.R;
import net.techiebits.emanbasahel.bakingapp.data.RecipesModel;
import net.techiebits.emanbasahel.bakingapp.helpers.ReadingRecipes;
import java.util.List;

/**
 * Created by emanbasahel on 18/03/2018 AD.
 */

public class WidgetFactory implements RemoteViewsService.RemoteViewsFactory {

    private  Context mContext=null;
    private int appWidgetId;
    private int mRecipeId;
    private List<RecipesModel> recipesModelList;
    public WidgetFactory (Context context, Intent intent)
    {
        mContext=context;
        appWidgetId=intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                AppWidgetManager.INVALID_APPWIDGET_ID);
        mRecipeId=intent.getIntExtra(context.getString(R.string.recipe_id),0);
        recipesModelList= ReadingRecipes.getInstance(context).getRecipes();
    }
    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return recipesModelList.get(mRecipeId-1).getIngredients().size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        RemoteViews rv=new RemoteViews(mContext.getPackageName(),
                R.layout.ingredient_item);
        rv.setTextViewText(R.id.txt_ingredient, String.format(mContext.getString(R.string.text_ingredient),
                recipesModelList.get(mRecipeId-1).getIngredients().get(position).getQuantity(),
                recipesModelList.get(mRecipeId-1).getIngredients().get(position).getMeasure(),
                recipesModelList.get(mRecipeId-1).getIngredients().get(position).getIngredient()));

        return rv;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}