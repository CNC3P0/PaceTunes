package com.example.robert.pacetunes;

import java.io.File;
import java.util.Map;

/**
 * Created by Robert on 11/25/2017.
 */

public class PlayList {
    private Map<String, File> playListMap;

    //public ArrayList getPlayList() {    }

    //public void createPlayList() {    }

    /*public ArrayList<String> getPlayList() {

        ArrayList<String> arrayList=new ArrayList<String>();

        String[] proj = {"*"};
        Uri tempPlaylistURI = MediaStore.Audio.Playlists.EXTERNAL_CONTENT_URI;

        // In the next line 'this' points to current Activity.
        // If you want to use the same code in other java file then activity,
        // then use an instance of any activity in place of 'this'.

        Cursor playListCursor = PlayList.getContentResolver().query(contentUri, proj, null, null, null);

//                = PlayList.managedQuery(tempPlaylistURI, proj, null,null,null);

        if(playListCursor == null){
            System.out.println("Not having any Playlist on phone --------------");
            return arrayList;//don't have list on phone
        }

        System.gc();

        String playListName = null;

        System.out.println(">>>>>>>  CREATING AND DISPLAYING LIST OF ALL CREATED PLAYLIST  <<<<<<");

        for(int i = 0; i <playListCursor.getCount() ; i++)
        {
            playListCursor.moveToPosition(i);
            playListName = playListCursor.getString(playListCursor.getColumnIndex("name"));
            System.out.println("> " + i + "  : " + playListName );
            arrayList.add(playListName);
        }

        if(playListCursor != null)
            playListCursor.close();

        return arrayList;



    }


    //File f = new File(Environment.getExternalStorageDirectory().toString());

    /*Log.("SERVICE", "onBackPressed");

    if(f.isDirectory())
    {
        ArrayList<String> files= new ArrayList<String>();
        File file = new File(Environment.getExternalStorageDirectory() +"/"+ dirname+"/");
        File fileList[] = file.listFiles();
        for(int i=0;i<fileList.length;i++)
        {
            files.add(filelist[i].getAbsolutePath());
            //here you can get all files.
        }
    }*/
}
