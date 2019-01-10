package com.dell.saif.musicplayer;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;
import java.util.jar.JarEntry;

import static java.security.AccessController.getContext;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder>{

private OnItemClickListener mListener;
    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener)
    {
        mListener=listener;
    }
    private  List<SongsModel> SongsData;
    MediaMetadataRetriever mmr=new MediaMetadataRetriever();
    public MyAdapter(List<SongsModel> Songs) {
        SongsData=Songs; }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        LayoutInflater layoutInflater=LayoutInflater.from(viewGroup.getContext());
        View view=layoutInflater.inflate(R.layout.single,viewGroup,false);
        ViewHolder vh=new ViewHolder(view,mListener);
        return vh;


    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {

        final SongsModel song=SongsData.get(i);
        viewHolder.title.setText(song.getTitle());
        viewHolder.album.setText(song.getAlbum());
        viewHolder.artist.setText(song.getArtist());
        }

    @Override
    public int getItemCount() {
        return SongsData.size();
    }




    public static class ViewHolder extends RecyclerView.ViewHolder{
        public TextView title,album,artist;
        public ImageView albumArt;
        public CardView cardView;
        public ViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);

        album=itemView.findViewById(R.id.album);
        title=itemView.findViewById(R.id.title);
        artist=itemView.findViewById(R.id.artist);
        albumArt=itemView.findViewById(R.id.albumart);
        cardView=itemView.findViewById(R.id.cardview);
        cardView.setClickable(true);
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listener!=null)
                {
                    int pos=getAdapterPosition();
                    if(pos != RecyclerView.NO_POSITION);
                    {
                    listener.onItemClick(pos);}

                }

            }
        });

        }

    }

}
