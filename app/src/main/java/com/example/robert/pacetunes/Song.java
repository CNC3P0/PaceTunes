package com.example.robert.pacetunes;

/**
 * Created by Robert on 11/23/2017.
 */

public class Song {
    private String data;
    private String title;
    private String artist;
    private String album;
    private String duration;
    private String filePath;
    private String codec;
    private int bpm;

    public Song(String data, String title, String album, String artist) {
        this.data = data;
        this.title = title;
        this.album = album;
        this.artist = artist;
    }

    public String getData() {return data;}

    public void setData(String data) {this.data = data;}

    public String getTitle() { return title; }

    public void setTitle(String title) {this.title = title;}

    public String getAlbum() {return album;}

    public void setAlbum() {this.album = album;}

    public String getArtist() {return artist; }

    public void setArtist() {this.artist = artist;}
}
