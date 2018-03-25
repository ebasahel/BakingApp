package net.techiebits.emanbasahel.bakingapp.helpers;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import net.techiebits.emanbasahel.bakingapp.R;
import net.techiebits.emanbasahel.bakingapp.data.RecipesModel;
import net.techiebits.emanbasahel.bakingapp.data.Step;


/**
 * Created by emanbasahel on 26/02/2018 AD.
 */

public class InstructionsAdapter extends RecyclerView.Adapter<InstructionsAdapter.ViewHolder> {

    private RecipesModel mRecipeModel;
    private TextView txtInstruction;
    private Context mContext;
    private VideoHandlerInterface videoHandler;
    private ImageView mVideoPlaceholder;
    private CardView stepContainer;

    public interface VideoHandlerInterface {
        void onVideoDisplayed(String videoURL);
    }

    public InstructionsAdapter(RecipesModel recipemodle, Context context,
                               VideoHandlerInterface videoHandlerInterface) {
        mRecipeModel = recipemodle;
        mContext = context;
        videoHandler = videoHandlerInterface;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

         ViewHolder(View itemView) {
            super(itemView);
            txtInstruction = (TextView) itemView.findViewById(R.id.txt_recipe_instruction);
             mVideoPlaceholder = (ImageView) itemView.findViewById(R.id.video_placeholder);
             stepContainer  = (CardView) itemView.findViewById(R.id.step_container);
        }

        void bind(final Step step, final VideoHandlerInterface videoHandlerInterface) {
            txtInstruction.setText(step.getDescription());
            //region set Thumbnail image
            if(!step.getThumbnailURL().isEmpty())
            {
                Picasso.get()
                        .load(step.getThumbnailURL())
                        .placeholder(R.drawable.drawable_no_video)
                        .error(R.drawable.drawable_no_video)
                        .into(mVideoPlaceholder);
            }
            //endregion
            //region video of the step
            //set background color of step to green if there's video to display
            //when clicks in step video will be displayed
            if (!step.getVideoURL().isEmpty()) {
                stepContainer.setCardBackgroundColor(mContext.getColor(R.color.light_green));
//                mVideoPlaceholder.setImageDrawable(mContext.getResources().getDrawable(R.drawable.drawable_play_video));
                stepContainer.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        videoHandlerInterface.onVideoDisplayed(step.getVideoURL());
                    }
                });
            }
//            else
//            {
//                mVideoPlaceholder.setImageDrawable(mContext.getResources().getDrawable(R.drawable.drawable_no_video));
//            }
            //endregion
        }
    }

    @Override
    @NonNull
    public InstructionsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.instruction_item, parent, false);
        return new InstructionsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final @NonNull InstructionsAdapter.ViewHolder holder, final int position) {
        holder.bind(mRecipeModel.getSteps().get(position), videoHandler);
    }

    @Override
    public int getItemCount() {
        return mRecipeModel.getSteps().size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }
}
