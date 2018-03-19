package net.techiebits.emanbasahel.bakingapp.helpers;

import android.content.Context;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import net.techiebits.emanbasahel.bakingapp.data.RecipesModel;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.List;

import static junit.framework.Assert.assertEquals;

/**
 * Created by emanbasahel on 18/03/2018 AD.
 */

public class ReadingRecipes {

    private static ReadingRecipes mInstance;
    private static Context mContext;

    public static ReadingRecipes getInstance(Context context)
    {
        mContext = context;
        if (mInstance==null)
            mInstance= new ReadingRecipes();

        return mInstance;
    }

    public List <RecipesModel> getRecipes()
    {
        //region parse json array to List using Gson
        Gson gson = new Gson();
        @SuppressWarnings("serial")
        Type collectionType = new TypeToken<List<RecipesModel>>() {}.getType();
        List<RecipesModel> recipesModels = gson.fromJson(readJSONFile(), collectionType);
        assertEquals(4, recipesModels.size());

        return recipesModels;
    }

    //region read JSON file from assets
    //snippet from http://www.codexpedia.com/android/read-a-json-file-from-the-assets-folder-in-android/
    private String readJSONFile()
    {
        String json = null;
        try {
            InputStream is = mContext.getAssets().open("recipe.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return json;
    }
    //endregion
}
