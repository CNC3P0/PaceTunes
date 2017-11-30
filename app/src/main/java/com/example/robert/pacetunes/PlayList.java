package com.example.robert.pacetunes;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Robert on 11/25/2017.
 */

public class PlayList implements Serializable {
        private String playlistName;
        private String playlistPath;
        private ArrayList<String> songTitles;
        private ArrayList<String> songPaths;

        public PlayList(String playlistName, String playlistPath) {
            this.playlistName = playlistName;
            this.playlistPath = playlistPath;
            this.songTitles = songTitles;
            this.songPaths = songPaths;
        }

        public String getPlaylistName() {return playlistName;}
        public void setPlaylistName(String playlistName) {this.playlistName = playlistName;}

        public String getPlaylistPath() { return playlistPath; }
        public void setPlaylistPath(String playlistName) {this.playlistName = playlistName;}

        public ArrayList getSongTitles() {return songTitles;}
        public void setSongTitles() {this.songTitles = songTitles;}

        public ArrayList getSongPaths() {return songPaths; }
        public void setSongPaths() {this.songPaths = songPaths;}

        public String getSongPath(int index) {return songPaths.get(index);}
    }