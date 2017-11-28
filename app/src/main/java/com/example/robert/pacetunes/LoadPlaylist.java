package com.example.robert.pacetunes;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class LoadPlaylist extends AppCompatActivity {

    private static final int MY_PERMISSION_REQUEST = 1;

    ArrayList<String> arrayList;

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
                //String currentLocation = playlistCursor.getString(playlistLocation);
                arrayList.add(currentName);
            }
            while (playlistCursor.moveToNext());
        }
    }

    public void doStuff() {
        listView = findViewById(R.id.playlistView);
        arrayList = new ArrayList<>();
        //getMusic();
        getPlaylists();
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arrayList);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // open music player or load song here
                String songPath = (String)parent.getItemAtPosition(position);
                //Toast.makeText(getApplicationContext(), songPath, Toast.LENGTH_SHORT).show();
                //playSongHere.playSong(songPath);
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
