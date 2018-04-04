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
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

/**
 * This class is built using the help from https://medium.com/tall-programmer/fullscreen-functionality-with-android-exoplayer-5fddad45509f
 */
public class ExoPlayerVideoHandler {
    private static ExoPlayerVideoHandler instance;
    private DefaultTrackSelector trackSelector;
    private long mResumePosition;

    public static ExoPlayerVideoHandler getInstance() {
        if (instance == null) {
            instance = new ExoPlayerVideoHandler();
        }
        return instance;
    }

    private SimpleExoPlayer player;
    private Uri playerUri;
    private boolean isPlayerPlaying = true;

    private ExoPlayerVideoHandler() {
    }

    public void prepareExoPlayerForUri(Context context,
                                       Uri uri,
                                       SimpleExoPlayerView exoPlayerView,
                                       long seekPosition) {
        if (context != null && uri != null && exoPlayerView != null) {
            if (!uri.equals(playerUri) || player == null) {
                // Create a new player if the player is null or
                // we want to play a new video
                trackSelector = new DefaultTrackSelector();
                player = ExoPlayerFactory.newSimpleInstance(context, trackSelector);
                // Prepare the MediaSource.
                playerUri = uri;

                // Prepare the MediaSource.
                String userAgent = Util.getUserAgent(context, "BakingApp");
                MediaSource mediaSource = new ExtractorMediaSource
                        (uri, new DefaultDataSourceFactory(context, userAgent), new DefaultExtractorsFactory(),
                                null, null);
                // Prepare the player with the source.
                player.prepare(mediaSource);
            }
            player.clearVideoSurface();
            player.setVideoSurfaceView(
                    (SurfaceView) exoPlayerView.getVideoSurfaceView());
            player.seekTo(seekPosition);
            exoPlayerView.setPlayer(player);
        }
    }

    public void releaseVideoPlayer() {
        if (player != null) {
            player.release();
        }
        player = null;
    }

    public void goToBackground() {
        if (player != null) {
            isPlayerPlaying = player.getPlayWhenReady();
            player.setPlayWhenReady(false);
            setResumePosition(player.getCurrentPosition());
        }
    }

    public void goToForeground() {
        if (player != null) {
            player.setPlayWhenReady(isPlayerPlaying);
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