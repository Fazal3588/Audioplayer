package com.bk.lrandom.audioplayer.interfaces;

import java.util.ArrayList;
import com.bk.lrandom.audioplayer.models.Playlist;
import com.bk.lrandom.audioplayer.models.Track;

public interface CustomSelectPlaylistDialogComunicator {
	void addTrackToPlaylist(Track track, ArrayList<Playlist> playlist);
}
