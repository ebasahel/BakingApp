package net.techiebits.emanbasahel.bakingapp.views;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import net.techiebits.emanbasahel.bakingapp.R;
import net.techiebits.emanbasahel.bakingapp.data.RecipesModel;

import java.util.ArrayList;
import java.util.List;

public class RecipeDetailFragment extends Fragment {
    //region variables
    private RecipeDetailFragment.SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;
    private TabLayout tabLayout;
    private TextView txtTitle;
    private RecipesModel recipesModel;
    //endregion

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public RecipeDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(getString(R.string.title_argument_recipe))) {
            recipesModel=getArguments().getParcelable(getString(R.string.title_argument_recipe));

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.recipe_detail, container, false);

        //region init
        mSectionsPagerAdapter = new SectionsPagerAdapter(getChildFragmentManager());
        mViewPager = (ViewPager) rootView.findViewById(R.id.container);
        tabLayout = (TabLayout) rootView.findViewById(R.id.tabs);
        txtTitle = (TextView) rootView.findViewById(R.id.title_recipe);
        //endregion

        //region Set up the ViewPager with the sections adapter.
        mSectionsPagerAdapter.addFragmentTitle(new IngredientFragment() , getString(R.string.title_ingredients));
        mSectionsPagerAdapter.addFragmentTitle(new InstructionsFragment(), getString(R.string.title_instructions));
        mViewPager.setAdapter(mSectionsPagerAdapter);

        //setup the tab layout
        tabLayout.setupWithViewPager(mViewPager);
        //endregion

        txtTitle.setText(recipesModel.getName());
        return rootView;
    }

    //region FragmentPagerAdapter
    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

//        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        private SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        private void addFragmentTitle(Fragment fragment, String title) {
            mFragmentTitleList.add(title);
        }
        @Override
        public Fragment getItem(int position) {
            Bundle arguments = new Bundle();
            arguments.putParcelable(getString(R.string.title_argument_recipe),recipesModel);
            switch (position) {
                case 0:
                    IngredientFragment ingredientFragment = new IngredientFragment();
                    ingredientFragment.setArguments(arguments);
                    return ingredientFragment;

                case 1:
                   InstructionsFragment instructionsFragment =new InstructionsFragment();
                   instructionsFragment.setArguments(arguments);
                    return instructionsFragment;

                default:
                    return null;
            }
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }

        @Override
        public int getCount() {
            return 2;
        }
    }
    //endregion
}
