# PaceTunes
PaceTunes is an Android music player app for runners. The feature that makes it unique and useful is active timeshifting of the music to match a selected BPM range. It is still in development and does not yet provide its target functions.

## User Requirements:
  - Android device running minimum SDK of 21
  - Your own songs, properly tagged with a BPM (beats per minute) and copied to your device
  - Your own playlists, properly formatted and copied to your device
  
## Current requirements for successful compilation:
  - Android Studio 3.0.0 (min.)
  - a sample song in \PaceTunes\app\src\main\res\raw (name of song must be lowercase with no special characters or spaces)
  
## What is working:
  - GUI is essentially complete
  - plays/pauses sample song
  - stub Help and About pages open and close properly
  - playlist load function displays any playlists contained in device's Android MediaStore database
  - playlist name and path successfully retrieved (but not yet utilized)
  
## What I tried but haven't yet gotten working
  - running as a service (so that it does not close when other apps take focus)
  - dealing with other interruptions (phone calls, etc.)  
  
## What isn't implemented yet:
  - loading of playlist tracks
  - play next/play previous
  - loading and displaying metadata
  - input/application of target range to playback speed
  - Loop Mode (i.e. continuous play)
  - Sprint Mode (randomized increase in pace based on user parameters)
  - BPM Tagger (tool for tagging BPM field of song's metadata directly from device)
  - Pace Coach (running metronome in lieu of music)
  - playlist creation
