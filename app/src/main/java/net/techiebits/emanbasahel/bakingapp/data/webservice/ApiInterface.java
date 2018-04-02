package net.techiebits.emanbasahel.bakingapp.data.webservice;

import net.techiebits.emanbasahel.bakingapp.data.RecipesModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by emanbasahel on 25/03/2018 AD.
 */


public interface ApiInterface {
    @GET("baking.json")
    Call <List<RecipesModel>> getRecipes ();

}