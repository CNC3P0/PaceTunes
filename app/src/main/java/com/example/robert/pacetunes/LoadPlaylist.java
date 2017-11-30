package com.example.robert.pacetunes;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class LoadPlaylist extends AppCompatActivity {

    private static final int MY_PERMISSION_REQUEST = 1;

    ArrayList<PlayList> playlistArray;
    ArrayList<String> adapterArray;

    ListView listView;

    ArrayAdapter<String> adapter;

    //PlayerService playSongHere = new PlayerService();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_playlist);
        Intent intent = getIntent();

        if (ContextCompat.checkSelfPermission(LoadPlaylist.this,
                Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(LoadPlaylist.this,
                    Manifest.permission.READ_EXTERNAL_STORAGE)) {
                ActivityCompat.requestPermissions(LoadPlaylist.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, MY_PERMISSION_REQUEST);
            }
            else {
                ActivityCompat.requestPermissions(LoadPlaylist.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, MY_PERMISSION_REQUEST);
            }
        }
        else {
            doStuff();
        }
    }

    public void getPlaylists() {
        ContentResolver contentResolver = getContentResolver();
        Uri playlistUri = MediaStore.Audio.Playlists.EXTERNAL_CONTENT_URI;
        Cursor playlistCursor = contentResolver.query(playlistUri, null, null, null, null);

        if (playlistCursor != null && playlistCursor.moveToFirst()) {
            int playlistName = playlistCursor.getColumnIndex(MediaStore.Audio.Playlists.NAME);
            int playlistLocation = playlistCursor.getColumnIndex(MediaStore.Audio.Playlists.DATA);

            do {
                String currentName = playlistCursor.getString(playlistName);
                String currentLocation = playlistCursor.getString(playlistLocation);
                PlayList playList = new PlayList(currentName, currentLocation);
                playlistArray.add(playList);
                adapterArray.add(currentName);
            }
            while (playlistCursor.moveToNext());
        }
    }

    //public PlayList loadSongs(PlayList playList) {

    public Cursor loadSongs(Context context, Long playlist_id) {
        Uri newuri = MediaStore.Audio.Playlists.Members.getContentUri("external", playlist_id);
        ContentResolver resolver = context.getContentResolver();
        String _id = MediaStore.Audio.Playlists.Members._ID;
        String audio_id = MediaStore.Audio.Playlists.Members.AUDIO_ID;
        String artist = MediaStore.Audio.Playlists.Members.ARTIST;
        String album = MediaStore.Audio.Playlists.Members.ALBUM;
        String album_id = MediaStore.Audio.Playlists.Members.ALBUM_ID;
        String title = MediaStore.Audio.Playlists.Members.TITLE;
        String duration = MediaStore.Audio.Playlists.Members.DURATION;
        String location = MediaStore.Audio.Playlists.Members.DATA;
        String composer = MediaStore.Audio.Playlists.Members.COMPOSER;
        String playorder = MediaStore.Audio.Playlists.Members.PLAY_ORDER;

        String date_modified = MediaStore.Audio.Playlists.Members.DATE_MODIFIED;
        String[] columns = {_id, audio_id, artist, album_id, album, title, duration,
                location, date_modified, playorder, composer};
        return resolver.query(newuri, columns, null, null, null);
    }

    public void doStuff() {
        listView = findViewById(R.id.playlistView);
        playlistArray = new ArrayList<PlayList>();
        adapterArray = new ArrayList<String>();

        getPlaylists();
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, adapterArray);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //loadSongs(LoadPlaylist.this ,  MediaStore.Audio.Playlists.Members.playlistId());
                //String playlistPath = playlistArray.get(position).getPlaylistPath();
                //Toast.makeText(getApplicationContext(), playlistPath, Toast.LENGTH_SHORT).show();
                Intent returnIntent = new Intent();
                returnIntent.putExtra("result", playlistArray.get(position));
                setResult(Activity.RESULT_OK, returnIntent);
                finish();
            }
        });
    }
    
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case  MY_PERMISSION_REQUEST: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ContextCompat.checkSelfPermission(LoadPlaylist.this,
                            Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                        Toast.makeText(this, "Permission granted!", Toast.LENGTH_SHORT).show();

                        doStuff();
                    }
                } else {
                    Toast.makeText(this, "No permission granted!", Toast.LENGTH_SHORT).show();
                    finish();
                }
                return;
            }
        }
    }
}
