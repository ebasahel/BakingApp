package net.techiebits.emanbasahel.bakingapp.helpers;

import android.content.Context;
import android.net.Uri;
import android.view.SurfaceView;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
/**
 * This class is built using the help from https://medium.com/tall-programmer/fullscreen-functionality-with-android-exoplayer-5fddad45509f
 */

public class ExoPlayerVideoHandler {
    private static ExoPlayerVideoHandler instance;
    private SimpleExoPlayer mExoPlayer;
    private long mResumePosition;
    private TrackSelector trackSelector;

    public static ExoPlayerVideoHandler getInstance() {
        if (instance == null) {
            instance = new ExoPlayerVideoHandler();
        }
        return instance;
    }
    //region init player
    public void prepareExoPlayerForUri(Context mContext, Uri mediaUri,
                                       SimpleExoPlayerView exoPlayerView, long seekPosition) {
        String userAgent = "";
        if (mContext != null && mediaUri != null && exoPlayerView != null) {
            if (mExoPlayer == null) {
                // Create an instance of the ExoPlayer.
                trackSelector = new DefaultTrackSelector();
                mExoPlayer = ExoPlayerFactory.newSimpleInstance(mContext, trackSelector);
                exoPlayerView.setPlayer(mExoPlayer);
                // Prepare the MediaSource.
                userAgent = Util.getUserAgent(mContext, "BakingApp");
            }

            MediaSource mediaSource = new ExtractorMediaSource
                    (mediaUri, new DefaultDataSourceFactory(mContext, userAgent), new DefaultExtractorsFactory(),
                            null, null);
            // Prepare the player with the source.
            mExoPlayer.prepare(mediaSource);
            mExoPlayer.clearVideoSurface();
            mExoPlayer.setVideoSurfaceView(
                    (SurfaceView) exoPlayerView.getVideoSurfaceView());
            mExoPlayer.seekTo(seekPosition);
            mExoPlayer.setPlayWhenReady(true);
        }
    }
    //endregion

    public void releaseVideoPlayer() {
        if (mExoPlayer != null) {
            mExoPlayer.release();
            setResumePosition(Math.max(0, mExoPlayer.getContentPosition()));
            mExoPlayer = null;
            trackSelector = null;
        }
    }

    public long getResumePosition ()
    {
        return mResumePosition;
    }

    private void setResumePosition(long resumePosition)
    {
        mResumePosition=resumePosition;
    }

}
