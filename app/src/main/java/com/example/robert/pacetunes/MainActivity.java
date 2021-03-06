package com.example.robert.pacetunes;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
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
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import static android.support.design.widget.NavigationView.OnNavigationItemSelectedListener;

public class MainActivity extends AppCompatActivity
        implements OnNavigationItemSelectedListener, View.OnClickListener {

    public static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 1;
    public static final String Broadcast_PLAY_NEW_AUDIO = "com.example.robert.pacetunes.PlayNewAudio";

    MediaPlayer mPlayer;

    boolean playState = false;
    //ArrayList<Song> currentPlaylist;
    PlayList currentPlaylist;
    Song currentSong;
    boolean loopState;
    boolean sprintState;
    boolean coachState;
    int[] targetRange = {80,100};


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

        //mPlayer = MediaPlayer.create(this, R.raw.heal2);
    }

    public void toast(String s) {
        Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
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

        //currentPlaylist = ;
        mPlayer = MediaPlayer.create(this, R.raw.heal2);
        TextView rangeText = (TextView)findViewById(R.id.rangeButton);
        rangeText.setText(targetRange[0] + "-" + targetRange[1] + " BPM");
    }

    @Override
    public void onClick(View v) {
        ToggleButton playpause = findViewById(R.id.playButton);
        switch (v.getId()) {
            case R.id.playButton:
                if (playpause.isChecked()) {
                    toast("PLAYING");
                    mPlayer.start(); }
                else{
                    toast("PAUSING");
                    mPlayer.pause(); }
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

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_loadPL) {
            toast("LOAD");
            Intent intent = new Intent(this, LoadPlaylist.class);
            startActivityForResult(intent, 1);
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1) {
            if(resultCode == Activity.RESULT_OK){
                currentPlaylist = (PlayList) data.getSerializableExtra("result");
                Toast.makeText(getApplicationContext(), currentPlaylist.getPlaylistName(), Toast.LENGTH_SHORT).show();
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }
    }

    /*
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
    */

    /*public void listItemSelected(View view){
        musicServ.setSong(Integer.parseInt(view.getTag().toString()));
        musicServ.playSong();
    }*/

    /*
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
    }*/
}
