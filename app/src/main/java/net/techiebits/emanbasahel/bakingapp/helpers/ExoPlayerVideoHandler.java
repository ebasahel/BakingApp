package net.techiebits.emanbasahel.bakingapp.helpers;

import android.content.Context;
import android.net.Uri;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.view.SurfaceView;

import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

/**
 * This class is built from https://medium.com/tall-programmer/fullscreen-functionality-with-android-exoplayer-5fddad45509f
 */

public class ExoPlayerVideoHandler implements  ExoPlayer.EventListener{
    private static ExoPlayerVideoHandler instance;
    private static final String TAG = ExoPlayerVideoHandler.class.getSimpleName();
    private SimpleExoPlayer mExoPlayer;
    private boolean isPlayerPlaying;
    private PlaybackStateCompat.Builder mStateBuilder;
    private MediaSessionCompat mMediaSession;

    public static ExoPlayerVideoHandler getInstance(){
        if(instance == null){
            instance = new ExoPlayerVideoHandler();
        }
        return instance;
    }

    //region init player
    public void prepareExoPlayerForUri(Context mContext, Uri mediaUri,
                                       SimpleExoPlayerView exoPlayerView){
        String userAgent="";
        if(mContext != null && mediaUri != null && exoPlayerView != null){
            if( mExoPlayer == null){
                // Create an instance of the ExoPlayer.
                TrackSelector trackSelector = new DefaultTrackSelector();
                mExoPlayer = ExoPlayerFactory.newSimpleInstance(mContext, trackSelector);
                exoPlayerView.setPlayer(mExoPlayer);
                userAgent = Util.getUserAgent(mContext, "BakingApp");
            }
            // Prepare the MediaSource.

            MediaSource mediaSource = new ExtractorMediaSource
                    (mediaUri, new DefaultDataSourceFactory(mContext, userAgent), new DefaultExtractorsFactory(),
                            null, null);
            mExoPlayer.setPlayWhenReady(true);
            // Prepare the player with the source.
            mExoPlayer.prepare(mediaSource);
            mExoPlayer.clearVideoSurface();
            mExoPlayer.setVideoSurfaceView(
                    (SurfaceView)exoPlayerView.getVideoSurfaceView());
            mExoPlayer.seekTo(mExoPlayer.getCurrentPosition() + 1);
            exoPlayerView.setPlayer(mExoPlayer);
        }
    }
    //endregion

    //region  Media Session
    /**
     * Initializes the Media Session to be enabled with media buttons, transport controls, callbacks
     * and media controller.
     */
    public void initializeMediaSession(Context mContext) {

        // Create a MediaSessionCompat.
        mMediaSession = new MediaSessionCompat(mContext, TAG);

        // Enable callbacks from MediaButtons and TransportControls.
        mMediaSession.setFlags(
                MediaSessionCompat.FLAG_HANDLES_MEDIA_BUTTONS |
                        MediaSessionCompat.FLAG_HANDLES_TRANSPORT_CONTROLS);

        // Do not let MediaButtons restart the player when the app is not visible.
        mMediaSession.setMediaButtonReceiver(null);

        // Set an initial PlaybackState with ACTION_PLAY, so media buttons can start the player.
        mStateBuilder = new PlaybackStateCompat.Builder()
                .setActions(
                        PlaybackStateCompat.ACTION_PLAY |
                                PlaybackStateCompat.ACTION_PAUSE |
                                PlaybackStateCompat.ACTION_SKIP_TO_PREVIOUS |
                                PlaybackStateCompat.ACTION_PLAY_PAUSE);

        mMediaSession.setPlaybackState(mStateBuilder.build());


        // MySessionCallback has methods that handle callbacks from a media controller.
        mMediaSession.setCallback(new MySessionCallback());

        // Start the Media Session since the activity is active.
        mMediaSession.setActive(true);

    }
    //endregion

    public void releaseVideoPlayer(){
        if(mExoPlayer != null)
        {
            mExoPlayer.release();
        }
        mExoPlayer = null;
    }

    public void goToBackground(){
        if(mExoPlayer != null){
            isPlayerPlaying = mExoPlayer.getPlayWhenReady();
            mExoPlayer.setPlayWhenReady(false);
        }
    }

    public void goToForeground(){
        if(mExoPlayer != null){
            mExoPlayer.setPlayWhenReady(isPlayerPlaying);
        }
    }

    //region exoplayer event listener
    @Override
    public void onTimelineChanged(Timeline timeline, Object manifest) {

    }

    @Override
    public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {

    }

    @Override
    public void onLoadingChanged(boolean isLoading) {

    }

    @Override
    public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
        if((playbackState == ExoPlayer.STATE_READY) && playWhenReady){
            mStateBuilder.setState(PlaybackStateCompat.STATE_PLAYING,
                    mExoPlayer.getCurrentPosition(), 1f);
        } else if((playbackState == ExoPlayer.STATE_READY)){
            mStateBuilder.setState(PlaybackStateCompat.STATE_PAUSED,
                    mExoPlayer.getCurrentPosition(), 1f);
        }
        mMediaSession.setPlaybackState(mStateBuilder.build());
    }

    @Override
    public void onRepeatModeChanged(int repeatMode) {

    }

    @Override
    public void onShuffleModeEnabledChanged(boolean shuffleModeEnabled) {

    }

    @Override
    public void onPlayerError(ExoPlaybackException error) {

    }

    @Override
    public void onPositionDiscontinuity(int reason) {

    }

    @Override
    public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {

    }

    @Override
    public void onSeekProcessed() {

    }
    //endregion

    //region MySessionCallback
    /**
     * Media Session Callbacks, where all external clients control the player.
     */
    private class MySessionCallback extends MediaSessionCompat.Callback {
        @Override
        public void onPlay() {
            mExoPlayer.setPlayWhenReady(true);
        }

        @Override
        public void onPause() {
            mExoPlayer.setPlayWhenReady(false);
        }

        @Override
        public void onSkipToPrevious() {
            mExoPlayer.seekTo(0);
        }
    }
    //endregion
}
