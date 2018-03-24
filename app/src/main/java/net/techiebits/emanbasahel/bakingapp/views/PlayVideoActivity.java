package net.techiebits.emanbasahel.bakingapp.views;

import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.exoplayer2.ui.SimpleExoPlayerView;

import net.techiebits.emanbasahel.bakingapp.R;
import net.techiebits.emanbasahel.bakingapp.helpers.ExoPlayerVideoHandler;
import net.techiebits.emanbasahel.bakingapp.helpers.InstructionsAdapter;

public class PlayVideoActivity extends AppCompatActivity  {

    private SimpleExoPlayerView mPlayerView;
    private boolean mTwoPane;
    private String mVideoURL;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_video);
        mPlayerView = findViewById(R.id.player_view);

        Intent bundle = getIntent();
        if (bundle.getExtras()!=null)
        {
            mTwoPane= bundle.getBooleanExtra(getString(R.string.is_two_pane),false);
            mVideoURL = bundle.getStringExtra(getString(R.string.video_url));
        }

        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE && !mTwoPane)
        {
            Intent intent = new Intent(this,
                    FullscreenVideoActivity.class);
            intent.putExtra(getString(R.string.video_url),mVideoURL);
            startActivity(intent);
        }else
        {
            Log.d("ExoPlayer","VideoPlayer");
            ExoPlayerVideoHandler.getInstance().prepareExoPlayerForUri(this, Uri.parse(mVideoURL), mPlayerView);
        }


    }

    @Override
    public void onResume()
    {
        super.onResume();
        Log.d("ExoPlayer","VideoPlayer-onResume");
        ExoPlayerVideoHandler.getInstance().goToForeground();


    }
    @Override
    public void onPause(){
        super.onPause();
        Log.d("ExoPlayer","VideoPlayer-onPause");
        ExoPlayerVideoHandler.getInstance().goToBackground();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ExoPlayerVideoHandler.getInstance().releaseVideoPlayer();
    }
}
