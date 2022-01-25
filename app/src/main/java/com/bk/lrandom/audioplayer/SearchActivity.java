package com.bk.lrandom.audioplayer;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

import com.bk.lrandom.audioplayer.adapter.TabsPagerAdapter;
import com.bk.lrandom.audioplayer.conf.constants;
import com.bk.lrandom.audioplayer.dals.AlbumDal;
import com.bk.lrandom.audioplayer.dals.ArtistDal;
import com.bk.lrandom.audioplayer.dals.PlaylistDal;
import com.bk.lrandom.audioplayer.dals.TrackDal;
import com.bk.lrandom.audioplayer.fragments.AlbumFragment;
import com.bk.lrandom.audioplayer.fragments.ArtistFragment;
import com.bk.lrandom.audioplayer.fragments.NoResultMatch;
import com.bk.lrandom.audioplayer.fragments.PlaylistFragment;
import com.bk.lrandom.audioplayer.fragments.TrackFragment;
import com.bk.lrandom.audioplayer.models.Album;
import com.bk.lrandom.audioplayer.models.Artist;
import com.bk.lrandom.audioplayer.models.Playlist;
import com.bk.lrandom.audioplayer.models.Track;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;

public class SearchActivity extends ActionBarParentActivity {
	FragmentManager fragmentManager;
	FragmentTransaction fragmentTransaction;
	String query = null;

	private ViewPager viewPager;
	private TabsPagerAdapter tabPagerAdapter;
	private ArrayList<Fragment> fragments;
	private ArrayList<String> titles;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.exploration_activity_layout);
		handleIntent(getIntent());
	}

	private void setTabFragments() {
		fragments = new ArrayList<Fragment>();
		titles = new ArrayList<String>();

		TrackDal trackDal = new TrackDal(this);
		ArrayList<Track> tracks = trackDal.getTracksByTitleOnMDS(query);
		Bundle bundle = new Bundle();
		if (tracks != null && !tracks.isEmpty()) {
			bundle.putParcelableArrayList(constants.TRACKS_KEY, tracks);
			TrackFragment trackFragment = TrackFragment.newInstance();
			trackFragment.setArguments(bundle);
			fragments.add(trackFragment);
		} else {
			NoResultMatch noResultMatch = NoResultMatch.newInstance();
			fragments.add(noResultMatch);
		}
		trackDal.close();
		titles.add(getResources().getString(R.string.track_label));

		ArtistFragment artistFragment = ArtistFragment.newInstance();
		ArtistDal artistDal = new ArtistDal(this);
		ArrayList<Artist> artists = artistDal.getArtistsByNameOnMDS(query);
		bundle = new Bundle();
		if (artists != null && !artists.isEmpty()) {
			bundle.putParcelableArrayList(constants.ARTISTS_KEY, artists);
			artistFragment.setArguments(bundle);
			fragments.add(artistFragment);
		} else {
			NoResultMatch noResultMatch = NoResultMatch.newInstance();
			fragments.add(noResultMatch);
		}
		artistDal.close();
		titles.add(getResources().getString(R.string.artist_label));

		AlbumFragment albumFragment = AlbumFragment.newInstance();
		AlbumDal albumDal = new AlbumDal(this);
		ArrayList<Album> albums = albumDal.getAlbumsByTitleOnMDS(query);
		bundle = new Bundle();
		if (albums != null && !albums.isEmpty()) {
			bundle.putParcelableArrayList(constants.ALBUMS_KEY, albums);
			albumFragment.setArguments(bundle);
			fragments.add(albumFragment);
		} else {
			NoResultMatch noResultMatch = NoResultMatch.newInstance();
			fragments.add(noResultMatch);
		}
		albumDal.close();
		titles.add(getResources().getString(R.string.album_label));

		PlaylistFragment playlistFragment = PlaylistFragment.newInstance();
		PlaylistDal playlistDal = new PlaylistDal(this);
		ArrayList<Playlist> playlists = playlistDal.getPlaylistByTitle(query);
		bundle = new Bundle();
		if (playlists != null && !playlists.isEmpty()) {
			bundle.putParcelableArrayList(constants.PLAYLISTS_KEY, playlists);
			playlistFragment.setArguments(bundle);
		} else {
			NoResultMatch noResultMatch = NoResultMatch.newInstance();
			fragments.add(noResultMatch);
		}
		playlistDal.close();
		titles.add(getResources().getString(R.string.playlist_label));
	}

	@Override
	protected void onNewIntent(Intent intent) {
		// TODO Auto-generated method stub
		super.onNewIntent(intent);
		handleIntent(intent);
	}

	private void handleIntent(Intent intent) {
		if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
			query = intent.getStringExtra(SearchManager.QUERY);
			setTabFragments();
			viewPager = (ViewPager) findViewById(R.id.pager);
			tabPagerAdapter = new TabsPagerAdapter(getSupportFragmentManager(),
					fragments, titles);
			viewPager.setAdapter(tabPagerAdapter);
			try {
				query = URLEncoder.encode(query, "utf-8");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
