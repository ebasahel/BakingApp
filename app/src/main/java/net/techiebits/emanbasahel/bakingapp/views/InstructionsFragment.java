package net.techiebits.emanbasahel.bakingapp.views;


import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import net.techiebits.emanbasahel.bakingapp.R;
import net.techiebits.emanbasahel.bakingapp.data.RecipesModel;
import net.techiebits.emanbasahel.bakingapp.helpers.ExoPlayerVideoHandler;
import net.techiebits.emanbasahel.bakingapp.helpers.InstructionsAdapter;

public class InstructionsFragment extends Fragment implements InstructionsAdapter.VideoHandlerInterface {

    //region variables
    private View rootView;
    private RecyclerView recyclerInstruction;
    private RecyclerView.LayoutManager mLayoutManager;
    private InstructionsAdapter mAdapter;
    private RecipesModel recipesModel;
    private boolean mTwoPane;
    //endregion

    // Required empty public constructor
    public InstructionsFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_instructions, container, false);

        if (getArguments()!=null)
        {
            recipesModel=getArguments().getParcelable(getString(R.string.title_recipe));
            mTwoPane= getArguments().getBoolean(getString(R.string.is_two_pane));
        }


        //region init recyclerview
        recyclerInstruction = rootView.findViewById(R.id.recycler_steps);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mAdapter= new InstructionsAdapter(recipesModel,getActivity(),this);
        recyclerInstruction.setLayoutManager(mLayoutManager);
        recyclerInstruction.setAdapter(mAdapter);
        //endregion
        return rootView;
    }


    @Override
    public void onVideoDisplayed(String videoURL) {
        Intent intent = new Intent(getActivity(), PlayVideoActivity.class);
        intent.putExtra(getString(R.string.video_url),videoURL);
        intent.putExtra(getString(R.string.is_two_pane),mTwoPane);
        startActivity(intent);
    }
}

