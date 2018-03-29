package net.techiebits.emanbasahel.bakingapp.widget;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;
import android.widget.Toast;

import net.techiebits.emanbasahel.bakingapp.R;
import net.techiebits.emanbasahel.bakingapp.data.RecipesModel;
import net.techiebits.emanbasahel.bakingapp.data.webservice.ApiClient;
import net.techiebits.emanbasahel.bakingapp.data.webservice.ApiInterface;
import net.techiebits.emanbasahel.bakingapp.helpers.ReadingRecipes;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by emanbasahel on 18/03/2018 AD.
 */

public class WidgetFactory implements RemoteViewsService.RemoteViewsFactory {

    private  Context mContext=null;
    private int appWidgetId;
    private int mRecipeId;
    private RecipesModel mRecipesModel;
    public WidgetFactory (Context context, Intent intent)
    {
        mContext=context;
        appWidgetId=intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                AppWidgetManager.INVALID_APPWIDGET_ID);
        mRecipeId=intent.getIntExtra(context.getString(R.string.recipe_id),0);
        mRecipesModel=intent.getParcelableExtra(context.getString(R.string.title_recipe));
//        recipesModelList=ReadingRecipes.getInstance(context).getRecipes();
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
        return mRecipesModel.getIngredients().size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        RemoteViews rv=new RemoteViews(mContext.getPackageName(),
                R.layout.ingredient_item);
        rv.setTextViewText(R.id.txt_ingredient, String.format(mContext.getString(R.string.text_ingredient),
                mRecipesModel.getIngredients().get(position).getQuantity(),
                mRecipesModel.getIngredients().get(position).getMeasure(),
                mRecipesModel.getIngredients().get(position).getIngredient()));

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
