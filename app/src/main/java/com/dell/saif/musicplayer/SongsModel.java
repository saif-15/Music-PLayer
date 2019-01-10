package com.dell.saif.musicplayer;

public class SongsModel {
    private String title,album,artist,data;

    SongsModel()
    {}

    public SongsModel(String title, String album, String artist, String data) {
        this.title = title;
        this.album = album;
        this.artist = artist;
        this.data = data;


    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }


    }
