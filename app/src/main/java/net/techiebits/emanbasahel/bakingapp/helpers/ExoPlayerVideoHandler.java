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

import java.util.ArrayList;
import java.util.List;

/**
 * This class is built using the help from https://medium.com/tall-programmer/fullscreen-functionality-with-android-exoplayer-5fddad45509f
 */

public class ExoPlayerVideoHandler {
    private static ExoPlayerVideoHandler instance;
    private SimpleExoPlayer mExoPlayer;
    private boolean isPlayerPlaying;

    public static ExoPlayerVideoHandler getInstance() {
        if (instance == null) {
            instance = new ExoPlayerVideoHandler();
        }
        return instance;
    }
    //region init player
    public void prepareExoPlayerForUri(Context mContext, Uri mediaUri,
                                       SimpleExoPlayerView exoPlayerView) {
        String userAgent = "";
        if (mContext != null && mediaUri != null && exoPlayerView != null) {
            if (mExoPlayer == null) {
                // Create an instance of the ExoPlayer.
                TrackSelector trackSelector = new DefaultTrackSelector();
                mExoPlayer = ExoPlayerFactory.newSimpleInstance(mContext, trackSelector);
                exoPlayerView.setPlayer(mExoPlayer);
                // Prepare the MediaSource.
                userAgent = Util.getUserAgent(mContext, "BakingApp");
            }

            MediaSource mediaSource = new ExtractorMediaSource
                    (mediaUri, new DefaultDataSourceFactory(mContext, userAgent), new DefaultExtractorsFactory(),
                            null, null);
//                mExoPlayer.setPlayWhenReady(true);
            // Prepare the player with the source.
            mExoPlayer.prepare(mediaSource);
            mExoPlayer.clearVideoSurface();
            mExoPlayer.setVideoSurfaceView(
                    (SurfaceView) exoPlayerView.getVideoSurfaceView());
            mExoPlayer.seekTo(mExoPlayer.getCurrentPosition() + 1);
            exoPlayerView.setPlayer(mExoPlayer);
        }
    }
    //endregion

    public void releaseVideoPlayer() {
        if (mExoPlayer != null) {
            mExoPlayer.release();
        }
        mExoPlayer = null;
    }

    public void goToBackground() {
        if (mExoPlayer != null) {
            isPlayerPlaying = mExoPlayer.getPlayWhenReady();
            mExoPlayer.setPlayWhenReady(false);
        }
    }

    public void goToForeground() {
        if (mExoPlayer != null) {
            mExoPlayer.setPlayWhenReady(isPlayerPlaying);
        }
    }
}
