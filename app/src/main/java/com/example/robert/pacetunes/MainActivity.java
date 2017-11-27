package com.example.robert.pacetunes;

import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.MediaStore;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;

//import com.example.robert.pacetunes.PlayerService.MusicBinder;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    //private PlayerService musicServ;
    //private Intent playIntent;
    //private boolean musicBound = false;

    private MediaPlayerService player;
    boolean serviceBound = false;

    ArrayList<Song> songList;

    /*/ from tutorial
    private static final int STATE_PAUSED = 0;
    private static final int STATE_PLAYING = 1;
    private int mCurrentState = STATE_PAUSED;
    private MediaBrowserCompat mMediaBrowserCompat;
    private MediaControllerCompat mMediaControllerCompat;
    private Button mPlayPauseToggleButton;

    private MediaBrowserCompat.ConnectionCallback mMediaBrowserCompatConnectionCallback = new MediaBrowserCompat.ConnectionCallback() {

        @Override
        public void onConnected() {
            super.onConnected();
            try {
                mMediaControllerCompat = new MediaControllerCompat(MainActivity.this, mMediaBrowserCompat.getSessionToken());
                mMediaControllerCompat.registerCallback(mMediaControllerCompatCallback);
                mMediaControllerCompat.setMediaController(MainActivity.this, mMediaControllerCompat);
                mMediaControllerCompat.getTransportControls().playFromMediaId(String.valueOf(R.raw.heal2), null);

            } catch( RemoteException e ) {

            }
        }
    };

    private MediaControllerCompat.Callback mMediaControllerCompatCallback = new MediaControllerCompat.Callback() {

        @Override
        public void onPlaybackStateChanged(PlaybackStateCompat state) {
            super.onPlaybackStateChanged(state);
            if( state == null ) {
                return;
            }

            switch( state.getState() ) {
                case PlaybackStateCompat.STATE_PLAYING: {
                    mCurrentState = STATE_PLAYING;
                    break;
                }
                case PlaybackStateCompat.STATE_PAUSED: {
                    mCurrentState = STATE_PAUSED;
                    break;
                }
            }
        }
    };*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        Button play = (Button) findViewById(R.id.playButton);
        play.setOnClickListener(this); // calling onClick() method
        Button next = (Button) findViewById(R.id.nextButton);
        next.setOnClickListener(this);
        Button previous = (Button) findViewById(R.id.previousButton);
        previous.setOnClickListener(this);
        Button range = (Button) findViewById(R.id.rangeButton);
        range.setOnClickListener(this);

        playAudio("https://upload.wikimedia.org/wikipedia/commons/6/6c/Grieg_Lyric_Pieces_Kobold.ogg");
        //MediaPlayer mediaPlayer = MediaPlayer.create(this, R.raw.heal2);
        //mediaPlayer.start();

    }

    public void toast(String s) {
        Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.playButton:
                toast ("PLAY");
                break;
            case R.id.nextButton:
                toast("NEXT");
                break;
            case R.id.previousButton:
                toast("PREVIOUS");
                break;
            case R.id.rangeButton:
                toast("RANGE");
                break;
            default:
                break;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        /*if (playIntent == null) {
            playIntent = new Intent(this, PlayerService.class);
            bindService(playIntent, musicConnection, Context.BIND_AUTO_CREATE);
            startService(playIntent);
            Log.d("BLAH", "returned to onStart from startService");
        }*/
    }

    //Binding this Client to the AudioPlayer Service
    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            // We've bound to LocalService, cast the IBinder and get LocalService instance
            MediaPlayerService.LocalBinder binder = (MediaPlayerService.LocalBinder) service;
            player = binder.getService();
            serviceBound = true;

            Toast.makeText(MainActivity.this, "Service Bound", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            serviceBound = false;
        }
    };

    private void playAudio(String media) {
        //Check is service is active
        if (!serviceBound) {
            Intent playerIntent = new Intent(this, MediaPlayerService.class);
            playerIntent.putExtra("media", media);
            startService(playerIntent);
            bindService(playerIntent, serviceConnection, Context.BIND_AUTO_CREATE);
        } else {
            //Service is active
            //Send media with BroadcastReceiver
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
        //Log.d("BLAH", "onBackPressed");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_loadPL) {
            toast("LOAD");
            Intent intent = new Intent(this, LoadPlaylist.class);
            startActivity(intent);
        } else if (id == R.id.nav_loopmode) {
            toast("LOOP");
        } else if (id == R.id.nav_sprintmode) {
            toast("SPRINT");
        } else if (id == R.id.nav_help) {
            toast("HELP");
            Intent intent = new Intent(this, Help.class);
            startActivity(intent);
        } else if (id == R.id.nav_about) {
            toast("ABOUT");
            Intent intent = new Intent(this, About.class);
            startActivity(intent);

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putBoolean("ServiceState", serviceBound);
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        serviceBound = savedInstanceState.getBoolean("ServiceState");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (serviceBound) {
            unbindService(serviceConnection);
            //service is active
            player.stopSelf();
        }
    }

    /*public void listItemSelected(View view){
        musicServ.setSong(Integer.parseInt(view.getTag().toString()));
        musicServ.playSong();
    }*/

    private void loadAudio() {
        ContentResolver contentResolver = getContentResolver();

        Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        String selection = MediaStore.Audio.Media.IS_MUSIC + "!= 0";
        String sortOrder = MediaStore.Audio.Media.TITLE + " ASC";
        Cursor cursor = contentResolver.query(uri, null, selection, null, sortOrder);

        if (cursor != null && cursor.getCount() > 0) {
            songList = new ArrayList<>();
            while (cursor.moveToNext()) {
                String data = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA));
                String title = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE));
                String album = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM));
                String artist = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST));

                // Save to audioList
                songList.add(new Song(data, title, album, artist));
            }
        }
        cursor.close();
    }

}
