package com.dell.saif.musicplayer;

import android.app.Service;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;

import android.graphics.Bitmap;
import android.net.Uri;

import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import java.util.*;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;


import static android.provider.MediaStore.Audio.*;

public class MainActivity extends AppCompatActivity {
    FloatingActionButton fab;
    private Toolbar toolbar;
    public static int title;
    public static String song;
       public static List<SongsModel> Mylist=new ArrayList<>();
        RecyclerView recyclerView;
        RecyclerView.LayoutManager layoutManager;
        MyAdapter madapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fab=findViewById(R.id.fab);
        toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        recyclerView=findViewById(R.id.recyclerview);
        layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        Mylist=allFiles(this);
        if(Mylist!=null){
        madapter=new MyAdapter(Mylist);
        recyclerView.setAdapter(madapter);
        madapter.setOnItemClickListener(new MyAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int pos) {

                title=pos;
                song=Mylist.get(pos).getData();
                Intent it=new Intent(MainActivity.this,Main3Activity.class);
                startActivity(it);
            }
        });
    }
    else
        {
            Intent it=new Intent(MainActivity.this,Blank.class);
            startActivity(it);
            MainActivity.this.finish();
        }

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(MainActivity.song!=null){
                Intent it=new Intent(MainActivity.this,Main3Activity.class);
                startActivity(it);}
            }
        });
    }
    private List<SongsModel> allFiles( final Context context)
    {   final List<SongsModel>AudioList=new ArrayList<>();

        Uri uri = Media.EXTERNAL_CONTENT_URI;
        String[] projection = {AudioColumns.DATA,AudioColumns.TITLE,AudioColumns.ALBUM,AudioColumns.ARTIST};

        Cursor cursor = context.getContentResolver().query(uri,projection,null,null,null);

         if(cursor!=null) {
             while (cursor.moveToNext()) {

                SongsModel songsModel=new SongsModel();


                 songsModel.setData(cursor.getString(0));
                 songsModel.setTitle(cursor.getString(1));
                 songsModel.setAlbum(cursor.getString(2));
                 songsModel.setArtist(cursor.getString(3));

                 Log.e("Data :" ,songsModel.getData());
                 Log.e("Title :" ,songsModel.getTitle());
                 Log.e("Album :" ,songsModel.getAlbum());
                 Log.e("Artist :",songsModel.getArtist());
            //     Log.e("AlbumArt :",songsModel.getAlbumart());
                 AudioList.add(songsModel);
             }
                 cursor.close();
             }
         return AudioList;

    }

    @Override
    protected void onDestroy() {

        AudioService.playerNotificationManager.setPlayer(null);
        super.onDestroy();
    }
}
