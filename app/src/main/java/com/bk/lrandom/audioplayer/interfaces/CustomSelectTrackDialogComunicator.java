package com.bk.lrandom.audioplayer.interfaces;

import java.util.ArrayList;

import com.bk.lrandom.audioplayer.models.Track;

public interface CustomSelectTrackDialogComunicator {
  void savePlaylist(ArrayList<Track> tracks);
}
