package net.techiebits.emanbasahel.bakingapp.views;

import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import net.techiebits.emanbasahel.bakingapp.R;
import net.techiebits.emanbasahel.bakingapp.helpers.ExoPlayerVideoHandler;

public class PlayVideoActivity extends AppCompatActivity  {

    private SimpleExoPlayerView mPlayerView;
    private boolean mTwoPane;
    private String mVideoURL;
    private int mRequestCode=100;
    private long mResumePosition;
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
            mResumePosition=0;

        }
        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE && !mTwoPane)
        {
            Intent intent = new Intent(this,
                    FullscreenVideoActivity.class);
            intent.putExtra(getString(R.string.video_url),mVideoURL);
            intent.putExtra(getString(R.string.seek_position),ExoPlayerVideoHandler.getInstance().getResumePosition());
            startActivityForResult(intent,mRequestCode);
        }


    }

    @Override
    public void onResume()
    {
        super.onResume();
        ExoPlayerVideoHandler.getInstance().prepareExoPlayerForUri(this, Uri.parse(mVideoURL), mPlayerView,mResumePosition);

    }
    @Override
    public void onPause(){
        super.onPause();
        ExoPlayerVideoHandler.getInstance().releaseVideoPlayer();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ExoPlayerVideoHandler.getInstance().releaseVideoPlayer();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(requestCode==mRequestCode) {
            if (resultCode == RESULT_OK) {
                mResumePosition = data.getLongExtra(getString(R.string.seek_position), 0);

            }
        }
    }

}
