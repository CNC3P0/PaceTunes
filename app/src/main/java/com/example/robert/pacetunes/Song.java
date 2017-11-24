package com.example.robert.pacetunes;

/**
 * Created by Robert on 11/23/2017.
 */

public class Song {
    private String title;
    private String artist;
    private String albumTitle;
    private String duration;
    private String filePath;
    private String codec;
    private int bpm;

    public Song(String path){
        filePath = path;
    }

    public String getFilePath() {
        return filePath;
    }
}
