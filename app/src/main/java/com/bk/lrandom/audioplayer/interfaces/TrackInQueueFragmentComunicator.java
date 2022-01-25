package com.bk.lrandom.audioplayer.interfaces;

import java.util.ArrayList;

import com.bk.lrandom.audioplayer.models.Track;

public interface TrackInQueueFragmentComunicator {
	void LoadTrackAndPlay(int trackIndex, ArrayList<Track> tracks,
			Boolean setPager);

	void getTrackIndex(int trackIndex);
}
