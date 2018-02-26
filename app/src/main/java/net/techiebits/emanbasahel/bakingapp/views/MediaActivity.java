package net.techiebits.emanbasahel.bakingapp.views;

import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import net.techiebits.emanbasahel.bakingapp.R;
import net.techiebits.emanbasahel.bakingapp.helpers.ExoPlayerVideoHandler;

public class MediaActivity extends AppCompatActivity  {

    private String videoURL="https://d17h27t6h515a5.cloudfront.net/topher/2017/April/58ffd974_-intro-creampie/-intro-creampie.mp4";
    private SimpleExoPlayerView mPlayerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media);
        // Initialize the player view.
        mPlayerView = (SimpleExoPlayerView) findViewById(R.id.player_view);
        ExoPlayerVideoHandler.getInstance().prepareExoPlayerForUri(this,Uri.parse(videoURL),mPlayerView);
    }


    /**
     * Release the player when the activity is destroyed.
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        ExoPlayerVideoHandler.getInstance().releaseVideoPlayer();
    }

    @Override
    public void onResume()
    {
        super.onResume();
        ExoPlayerVideoHandler.getInstance().goToForeground();
        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
        {
            Intent intent = new Intent(MediaActivity.this,
                    FullscreenVideoActivity.class);
            intent.putExtra(getString(R.string.video_url),videoURL);
            startActivity(intent);
        }

    }
    @Override
    public void onPause(){
        super.onPause();
        ExoPlayerVideoHandler.getInstance().goToBackground();
    }



}
