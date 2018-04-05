package net.techiebits.emanbasahel.bakingapp;

import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import net.techiebits.emanbasahel.bakingapp.views.RecipeListActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Created by emanbasahel on 21/03/2018 AD.
 */

@RunWith(AndroidJUnit4.class)
@LargeTest
public class RecipeListActivityTest {

    @Rule
    public ActivityTestRule<RecipeListActivity> mActivityTestRule =
            new ActivityTestRule<>(RecipeListActivity.class);

    /*
      Works on phone only
     */


    @Test
    public void clickRecipeName_getRecipeIngredientList() {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // First, scroll to the position that needs to be matched and click on it.
        onView(withId(R.id.recipe_list))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        // Match the text in an item below the fold and check that it's displayed.
        String NutellatTextTest = mActivityTestRule.getActivity().getResources()
                .getString(R.string.item_first_text_test);
        onView(withText(NutellatTextTest)).check(matches(isDisplayed()));

    }

}
