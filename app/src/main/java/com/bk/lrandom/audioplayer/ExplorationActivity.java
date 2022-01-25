package com.bk.lrandom.audioplayer;

import java.util.ArrayList;
import java.util.LinkedHashSet;

import com.bk.lrandom.audioplayer.adapter.TabsPagerAdapter;
import com.bk.lrandom.audioplayer.conf.constants;
import com.bk.lrandom.audioplayer.dals.AlbumDal;
import com.bk.lrandom.audioplayer.dals.ArtistDal;
import com.bk.lrandom.audioplayer.dals.PlaylistDal;
import com.bk.lrandom.audioplayer.dals.TrackDal;
import com.bk.lrandom.audioplayer.fragments.AlbumFragment;
import com.bk.lrandom.audioplayer.fragments.ArtistFragment;
import com.bk.lrandom.audioplayer.fragments.CustomSelectTrackDialogFragment;

import com.bk.lrandom.audioplayer.fragments.PlaylistFragment;
import com.bk.lrandom.audioplayer.fragments.TrackFragment;
import com.bk.lrandom.audioplayer.interfaces.CustomPromptDialogFragmentComunicator;
import com.bk.lrandom.audioplayer.interfaces.CustomSelectPlaylistDialogComunicator;
import com.bk.lrandom.audioplayer.interfaces.CustomSelectTrackDialogComunicator;
import com.bk.lrandom.audioplayer.models.Album;
import com.bk.lrandom.audioplayer.models.Artist;
import com.bk.lrandom.audioplayer.models.Playlist;
import com.bk.lrandom.audioplayer.models.Track;
import com.google.android.gms.ads.NativeExpressAdView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v7.app.ActionBar.Tab;
import android.support.v7.app.ActionBar.TabListener;
import android.view.MenuItem;

public class ExplorationActivity extends ActionBarParentActivity implements
		TabListener, CustomPromptDialogFragmentComunicator,
		CustomSelectTrackDialogComunicator,
		CustomSelectPlaylistDialogComunicator {
	private ViewPager viewPager;
	private TabsPagerAdapter tabPagerAdapter;
	private ArrayList<Fragment> fragments;
	private ArrayList<String> titles;
	LinkedHashSet<Integer> disabledItems = new LinkedHashSet<Integer>();
	LinkedHashSet<Integer> enableItems = new LinkedHashSet<Integer>();
	TrackDal trackDal;
	String playlistName;
	Bundle bundle;

	private void setTabFragments() {
		fragments = new ArrayList<Fragment>();
		titles = new ArrayList<String>();

		TrackDal trackDal = new TrackDal(this);
		ArrayList<Track> tracks = trackDal.getTracksOnMDS();
		bundle = new Bundle();
		bundle.putParcelableArrayList(constants.TRACKS_KEY, tracks);
		TrackFragment trackFragment = TrackFragment.newInstance();
		trackFragment.setArguments(bundle);
		trackDal.close();

		ArtistFragment artistFragment = ArtistFragment.newInstance();
		ArtistDal artistDal = new ArtistDal(this);
		ArrayList<Artist> artists = artistDal.getArtistsOnMDS();
		bundle = new Bundle();
		bundle.putParcelableArrayList(constants.ARTISTS_KEY, artists);
		artistFragment.setArguments(bundle);
		artistDal.close();

		AlbumFragment albumFragment = AlbumFragment.newInstance();
		AlbumDal albumDal = new AlbumDal(this);
		ArrayList<Album> albums = albumDal.getAlbumOnMDS();
		bundle = new Bundle();
		bundle.putParcelableArrayList(constants.ALBUMS_KEY, albums);
		albumFragment.setArguments(bundle);
		albumDal.close();

		PlaylistFragment playlistFragment = PlaylistFragment.newInstance();
		PlaylistDal playlistDal = new PlaylistDal(this);
		ArrayList<Playlist> playlists = playlistDal.getAllPlayList();
		bundle = new Bundle();
		bundle.putParcelableArrayList(constants.PLAYLISTS_KEY, playlists);
		playlistFragment.setArguments(bundle);
		playlistDal.close();

		fragments.add(trackFragment);
		fragments.add(artistFragment);
		fragments.add(albumFragment);
		fragments.add(playlistFragment);

		titles.add(getResources().getString(R.string.track_label));
		titles.add(getResources().getString(R.string.artist_label));
		titles.add(getResources().getString(R.string.album_label));
		titles.add(getResources().getString(R.string.playlist_label));

		NativeExpressAdView adView = (NativeExpressAdView) findViewById(R.id.adView);
		loadAd(adView);
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}



	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.exploration_activity_layout);
		setTabFragments();
		viewPager = (ViewPager) findViewById(R.id.pager);
		tabPagerAdapter = new TabsPagerAdapter(getSupportFragmentManager(),
				fragments, titles);
		viewPager.setAdapter(tabPagerAdapter);
		setEnableItem(enableItems);
		viewPager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int position) {
				enableItems.clear();
				disabledItems.add(Integer.valueOf(R.id.btn_action_add_playlist));
				// TODO Auto-generated method stub
				switch (position) {
				// track
				case 0:

					break;

				// artirst
				case 1:
					break;

				// album
				case 2:
					break;

				// playlist
				case 3:
					disabledItems.clear();
					enableItems.add(Integer
							.valueOf(R.id.btn_action_add_playlist));
					setEnableItem(enableItems);
					break;

				default:
					break;
				}
				refreshActionBarMenu();
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub

			}
		});
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
	}

	@Override
	public void onTabReselected(Tab arg0, FragmentTransaction arg1) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onTabSelected(Tab arg0, FragmentTransaction arg1) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onTabUnselected(Tab arg0, FragmentTransaction arg1) {
		// TODO Auto-generated method stub
	}

	@Override
	public void savePlaylist(ArrayList<Track> tracks) {
		// TODO Auto-generated method stub
		PlaylistDal playlistDal = new PlaylistDal(this);
		int playlistId = playlistDal.addPlayList(this.playlistName);
		trackDal = new TrackDal(this);
		trackDal.getConnect();
		for (int i = 0; i < tracks.size(); i++) {
			trackDal.insertTrack(tracks.get(i), playlistId);
		}
		playlistDal.close();
		trackDal.close();
		if (tabPagerAdapter != null) {
			PlaylistFragment playlistFragment = (PlaylistFragment) tabPagerAdapter
					.getItem(3);
			Playlist playlist = new Playlist();
			playlist.setId(playlistId);
			playlist.setName(playlistName);
			playlistFragment.refreshViewAfterAddItem(playlist);
		}
	}

	@Override
	public void getInputTextValue(String inputText) {
		// TODO Auto-generated method stub
		this.playlistName = inputText;
		PlaylistDal playlistDal = new PlaylistDal(this);

		boolean hasExist = playlistDal.checkExistNamePlaylist(inputText);
		if (!hasExist) {
			CustomSelectTrackDialogFragment dialog = CustomSelectTrackDialogFragment
					.newInstance();
			dialog.show(getSupportFragmentManager(), "dialog");
		} else {
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setMessage(getResources().getString(R.string.exist_label));
			builder.setNegativeButton(
					getResources().getString(R.string.ok_label),
					new OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub

						}
					});
			builder.create().show();
		}
		playlistDal.close();
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

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
			case android.R.id.home:
				finish();
				break;

			default:
				break;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void renamePlaylist(String name, Playlist playlist) {
		// TODO Auto-generated method stub
		PlaylistDal playlistDal = new PlaylistDal(this);
		playlistDal.renamePlayList(name, playlist.getId());
		playlistDal.close();
		if (tabPagerAdapter != null) {
			PlaylistFragment playlistFragment = (PlaylistFragment) tabPagerAdapter
					.getItem(3);
			playlist.setName(name);
			playlistFragment.refresh(playlist);
		}
	}
}
