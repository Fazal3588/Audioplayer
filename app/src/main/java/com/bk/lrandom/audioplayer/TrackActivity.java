package com.bk.lrandom.audioplayer;

import java.lang.annotation.Native;
import java.util.ArrayList;

import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View;
import android.widget.ListView;

import com.bk.lrandom.audioplayer.business.Ultils;
import com.bk.lrandom.audioplayer.conf.constants;
import com.bk.lrandom.audioplayer.dals.ArtistDal;
import com.bk.lrandom.audioplayer.dals.TrackDal;
import com.bk.lrandom.audioplayer.interfaces.CustomSelectPlaylistDialogComunicator;
import com.bk.lrandom.audioplayer.models.Playlist;
import com.bk.lrandom.audioplayer.models.Track;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.NativeExpressAdView;
import com.google.android.gms.ads.formats.NativeAdView;

public class TrackActivity extends ActionBarParentActivity implements
		CustomSelectPlaylistDialogComunicator {
	ArtistDal artistDal;
	TrackDal trackDal;
	ListView list;
	ArrayList<Track> tracks = new ArrayList<Track>();

	public void refreshUI(Track track) {
		tracks.remove(track);
		list.invalidateViews();
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		// TODO Auto-generated method stub
		super.onCreateContextMenu(menu, v, menuInfo);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.listview_container_layout);
		trackDal = new TrackDal(this);
		Bundle bundle = this.getIntent().getExtras();
		list = (ListView) findViewById(R.id.list);
		if (bundle != null) {
			if (bundle.getInt(constants.ARTIST_ID_KEY) != 0) {
				int id = bundle.getInt(constants.ARTIST_ID_KEY);
				tracks = trackDal.getTracksByArtistIdOnMDS(id);
				Ultils.sendTrackToPlayer(this, tracks, list,
						getSupportFragmentManager());
				trackDal.close();
			}

			if (bundle.getInt(constants.ALBUM_ID_KEY) != 0) {
				int id = bundle.getInt(constants.ALBUM_ID_KEY);
				tracks = trackDal.getTracksByAlbumIdOnMDS(id);
				Ultils.sendTrackToPlayer(this, tracks, list,
						getSupportFragmentManager());
				trackDal.close();
			}

			if (bundle.getInt(constants.PLAYLIST_ID_KEY) != 0) {
				int id = bundle.getInt(constants.PLAYLIST_ID_KEY);
				trackDal.getConnect();
				tracks = trackDal.getTracksByPlaylistID(id);
				Ultils.sendTrackToPlayer(this, tracks, list,
						getSupportFragmentManager());
				trackDal.close();
			}
		}

		NativeExpressAdView adView = (NativeExpressAdView) findViewById(R.id.adView);
		loadAd(adView);
	}

	@Override
	public void addTrackToPlaylist(Track track, ArrayList<Playlist> playlist) {
		// TODO Auto-generated method stub
		TrackDal trackDal = new TrackDal(this);
		trackDal.getConnect();
		for (int i = 0; i < playlist.size(); i++) {
			trackDal.insertTrack(track, playlist.get(i).getId());
		}
		trackDal.close();
	}
}
