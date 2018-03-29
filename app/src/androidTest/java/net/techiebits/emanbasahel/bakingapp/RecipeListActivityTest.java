package net.techiebits.emanbasahel.bakingapp;

import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.matcher.BoundedMatcher;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import net.techiebits.emanbasahel.bakingapp.helpers.RecipesListAdapter;
import net.techiebits.emanbasahel.bakingapp.views.RecipeListActivity;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * Created by emanbasahel on 21/03/2018 AD.
 * this class has been built with help of
 * http://alexander-thiele.blogspot.in/2016/01/espresso-ui-tests-and-recyclerview.html
 */

@RunWith(AndroidJUnit4.class)
public class RecipeListActivityTest {

    @Rule
    public ActivityTestRule<RecipeListActivity> mActivityTestRule =
            new ActivityTestRule<>(RecipeListActivity.class);

    /*
      Works on phone only
     */

    @Test
    public void clickRecipeName_getRecipeIngredientList() {

       onView(withId(R.id.recipe_list)).perform(RecyclerViewActions.scrollToHolder(withHolderTitleView(mActivityTestRule.getActivity().getString(R.string.item_first_text_test))));

    }

    public static Matcher<RecyclerView.ViewHolder> withHolderTitleView(final String text) {
        return new BoundedMatcher<RecyclerView.ViewHolder, RecipesListAdapter.RecipeViewHolder>(RecipesListAdapter.RecipeViewHolder.class) {

            @Override
            public void describeTo(Description description) {
                description.appendText("No ViewHolder found with text: " + text);
            }

            @Override
            protected boolean matchesSafely(RecipesListAdapter.RecipeViewHolder item) {
                TextView timeViewText = (TextView) item.itemView.findViewById(R.id.content);
                if (timeViewText == null) {
                    return false;
                }
                return timeViewText.getText().toString().contains(text);
            }
        };
    }
}
