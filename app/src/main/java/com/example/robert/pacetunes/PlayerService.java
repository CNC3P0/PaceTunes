package com.example.robert.pacetunes;

import android.app.Service;
import android.content.Intent;
import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.os.PowerManager;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.ArrayList;

import static android.media.AudioManager.STREAM_MUSIC;

/**
 * Created by T00597013 on 11/23/2017.
 */

public class PlayerService extends Service implements MediaPlayer.OnPreparedListener,
        MediaPlayer.OnErrorListener, MediaPlayer.OnCompletionListener {

    private MediaPlayer player;
    private ArrayList<Song> playList;
    private int trackNumber;
    private final IBinder musicBind = new MusicBinder();

    public PlayerService() {
        super();
    }

    public PlayerService(MediaPlayer player) {
        this.player = player;
    }

    public void onCreate(){
        super.onCreate();
        trackNumber=0;
        player = new MediaPlayer();
        initializePlayer();

        Log.d("BLAH", "PlayerService created");
    }

    public void initializePlayer(){
        player.setWakeMode(getApplicationContext(), PowerManager.PARTIAL_WAKE_LOCK);
        player.setAudioStreamType(STREAM_MUSIC);
        player.setAudioAttributes(new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_MEDIA)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .build());
        player.setOnPreparedListener(this);
        player.setOnCompletionListener(this);
        player.setOnErrorListener(this);
    }

    public void setList(ArrayList<Song> theSongs){
        playList=theSongs;
    }

    public class MusicBinder extends Binder {
        PlayerService getService() {
            return PlayerService.this;
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return musicBind;
    }

    @Override
    public boolean onUnbind(Intent intent){
        player.stop();
        player.release();
        return false;
    }

    @Override
    public void onCompletion(MediaPlayer mp) {

    }

    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        return false;
    }

    @Override
    public void onPrepared(MediaPlayer mp) {

    }

    public void playSong(String filepath) {
        //player.reset();
        //Song playSong = playList.get(trackNumber);
        //String currentSong = playSong.getFilePath();
    }

}