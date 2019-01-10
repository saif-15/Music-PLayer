package com.dell.saif.musicplayer;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;



import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.util.Util;

public class Main3Activity extends AppCompatActivity {
    public static PlayerView playerView;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        playerView=findViewById(R.id.playerview);
        playerView.setPlayer(AudioService.player);

        if(MainActivity.song.equals(AudioService.id)){}
            else{
            intent = new Intent(this, AudioService.class);
            Util.startForegroundService(this, intent);
        }

    }
}



