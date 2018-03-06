package net.techiebits.emanbasahel.bakingapp.helpers;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.exoplayer2.ui.SimpleExoPlayerView;

import net.techiebits.emanbasahel.bakingapp.R;
import net.techiebits.emanbasahel.bakingapp.data.RecipesModel;
import net.techiebits.emanbasahel.bakingapp.data.Step;

/**
 * Created by emanbasahel on 26/02/2018 AD.
 */

public class InstructionsAdapter extends RecyclerView.Adapter<InstructionsAdapter.ViewHolder> {

    private RecipesModel mRecipeModel;
    private SimpleExoPlayerView mPlayerView;
    private Context mContext;
    private VideoHandlerInterface videoHandler;

    public interface VideoHandlerInterface {
        void onVideoDisplayed(String videoURL);
    }

    public InstructionsAdapter(RecipesModel recipemodle, Context context, VideoHandlerInterface videoHandlerInterface) {
        mRecipeModel = recipemodle;
        mContext = context;
        videoHandler = videoHandlerInterface;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtInstruction;

        public ViewHolder(View itemView) {
            super(itemView);
            txtInstruction = (TextView) itemView.findViewById(R.id.txt_recipe_instruction);
            mPlayerView = (SimpleExoPlayerView) itemView.findViewById(R.id.player_view);
        }

        void bind(final Step step, final VideoHandlerInterface videoHandlerInterface) {
            txtInstruction.setText(step.getDescription());
            if (step.getVideoURL().isEmpty()) {
                mPlayerView.setVisibility(View.GONE);
            } else {
                // Initialize the Media Session.
                ExoPlayerVideoHandler.getInstance().prepareExoPlayerForUri(mContext, Uri.parse(step.getVideoURL()), mPlayerView);
                videoHandlerInterface.onVideoDisplayed(step.getVideoURL());
            }

        }
    }

    @Override
    public InstructionsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.instruction_item, parent, false);
        return new InstructionsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(InstructionsAdapter.ViewHolder holder, int position) {
        holder.bind(mRecipeModel.getSteps().get(position), videoHandler);
    }

    @Override
    public int getItemCount() {
        return mRecipeModel.getSteps().size();
    }


}
