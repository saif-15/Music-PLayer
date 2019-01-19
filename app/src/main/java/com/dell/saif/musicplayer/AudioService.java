package com.dell.saif.musicplayer;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ConcatenatingMediaSource;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.PlayerNotificationManager;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import static com.dell.saif.musicplayer.Main3Activity.playerView;
import static com.dell.saif.musicplayer.MainActivity.title;

public class AudioService extends Service {

    public Context context=this;
    public static String id;
    public static SimpleExoPlayer player;
    public  static PlayerNotificationManager playerNotificationManager;
    private int currentWindow=0;
    private long playbackPosition=0;
    private boolean playWhenReady=true;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        if(player!=null)
            player.release();
        initializePlayer();
        return START_STICKY;
    }

    @Override
    public void onCreate() {
        super.onCreate();


}

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    public MediaSource buildMediaSource(Uri uri) {
        return new ExtractorMediaSource.Factory(
                new DefaultDataSourceFactory(this,Util.getUserAgent(this,"MediaPlayer"))).
                createMediaSource(uri);
    }

    @Override
    public void onDestroy() {
        releasePlayer();
        super.onDestroy();

    }

    public  void initializePlayer()
    {

        player = ExoPlayerFactory.newSimpleInstance(
                new DefaultRenderersFactory(this),
                new DefaultTrackSelector(), new DefaultLoadControl());
        playerView.setPlayer(player);
        playerView.setControllerHideOnTouch(false);
        playerView.setControllerShowTimeoutMs(0);
        playerView.setFastForwardIncrementMs(10000);
       playerView.setRewindIncrementMs(10000);
        player.setPlayWhenReady(playWhenReady);
        player.seekTo(currentWindow,playbackPosition);
        ConcatenatingMediaSource concatenatingMediaSource=new ConcatenatingMediaSource();
        id=MainActivity.Mylist.get(title).getData();
        Uri uri = Uri.parse(MainActivity.Mylist.get(title).getData());
        MediaSource mediaSource = buildMediaSource(uri);
        concatenatingMediaSource.addMediaSource(0,mediaSource);
        for(int i=MainActivity.Mylist.size()-1;i>title;i--){
            int flag=1;
            Uri uri1=Uri.parse(MainActivity.Mylist.get(i).getData());
            MediaSource mediaSource1=buildMediaSource(uri1);

            concatenatingMediaSource.addMediaSource(flag,mediaSource1);
            flag++;}

        player.prepare(concatenatingMediaSource, true, true);


        int NOTIFICATION_ID = 1;
        String CHANNEL_ID = "playback_channel";
        playerNotificationManager=PlayerNotificationManager.createWithNotificationChannel(this, CHANNEL_ID, R.string.channel_name, NOTIFICATION_ID, new PlayerNotificationManager.MediaDescriptionAdapter() {
            @Override
            public String getCurrentContentTitle(Player player) {
                return  MainActivity.Mylist.get(player.getCurrentWindowIndex()+title).getTitle();
            }

            @Nullable
            @Override
            public PendingIntent createCurrentContentIntent(Player player) {
                 return PendingIntent.getActivity(context,0,
                         new Intent(context,Main3Activity.class),PendingIntent.FLAG_UPDATE_CURRENT);

            }

            @Nullable
            @Override
            public String getCurrentContentText(Player player) {
                return MainActivity.Mylist.get(player.getCurrentWindowIndex()+title).getAlbum()+"-"+MainActivity.Mylist.get(player.getCurrentWindowIndex()+title).getArtist();
            }

            @Nullable
            @Override
            public Bitmap getCurrentLargeIcon(Player player, PlayerNotificationManager.BitmapCallback callback) {
                return  null;
            }
        });

        playerNotificationManager.setPriority(NotificationCompat.PRIORITY_HIGH);
        playerNotificationManager.setSmallIcon(R.drawable.notification);
        playerNotificationManager.setRewindIncrementMs(10000);
        playerNotificationManager.setFastForwardIncrementMs(10000);
        playerNotificationManager.setColor(R.color.colorPrimary);
        playerNotificationManager.setColorized(true);



        playerNotificationManager.setNotificationListener
                (new PlayerNotificationManager.NotificationListener() {
            @Override
            public void onNotificationStarted(int notificationId, Notification notification) {
               startForeground(notificationId,notification);
            }

            @Override
            public void onNotificationCancelled(int notificationId) { stopSelf(); }
        });
        playerNotificationManager.setPlayer(player);
    }


    public void releasePlayer() {
        if (player != null) {
            playerNotificationManager.setPlayer(null);
           playbackPosition = player.getCurrentPosition();
          currentWindow = player.getCurrentWindowIndex();
         playWhenReady = player.getPlayWhenReady();
            player.release();
            player = null;
        }
    }

}
