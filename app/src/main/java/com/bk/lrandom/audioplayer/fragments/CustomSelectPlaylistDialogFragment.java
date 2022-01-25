package com.bk.lrandom.audioplayer.fragments;

import java.util.ArrayList;

import com.bk.lrandom.audioplayer.R;
import com.bk.lrandom.audioplayer.adapter.PlaylistAdapter;
import com.bk.lrandom.audioplayer.adapter.TrackAdapter;
import com.bk.lrandom.audioplayer.conf.constants;
import com.bk.lrandom.audioplayer.dals.PlaylistDal;
import com.bk.lrandom.audioplayer.dals.TrackDal;
import com.bk.lrandom.audioplayer.interfaces.CustomSelectPlaylistDialogComunicator;
import com.bk.lrandom.audioplayer.interfaces.CustomSelectTrackDialogComunicator;
import com.bk.lrandom.audioplayer.models.Playlist;
import com.bk.lrandom.audioplayer.models.Track;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.ListView;

public class CustomSelectPlaylistDialogFragment extends DialogFragment {
	PlaylistDal playlistDal;
	PlaylistAdapter playlistAdapter;
	ArrayList<Playlist> playlists;
	ArrayList<Playlist> selectedPlaylists;
	CustomSelectPlaylistDialogComunicator listener;
	Track track;

	public static final CustomSelectPlaylistDialogFragment newInstance() {
		CustomSelectPlaylistDialogFragment fragment = new CustomSelectPlaylistDialogFragment();
		return fragment;
	}

	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		listener = (CustomSelectPlaylistDialogComunicator) activity;
		if (getArguments() != null) {
			track = getArguments().getParcelable(constants.TRACK_INDEX_KEY);
		}
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		playlistDal = new PlaylistDal(getActivity().getApplicationContext());
		playlists = playlistDal.getAllPlayList();
		selectedPlaylists = new ArrayList<Playlist>();
		playlistAdapter = new PlaylistAdapter(getActivity()
				.getApplicationContext(), R.layout.playlist_item_select_layout,
				playlists, getActivity().getSupportFragmentManager());
		ListView list = new ListView(getActivity());
		list.setAdapter(playlistAdapter);
		list.setItemsCanFocus(false);
		list.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
		list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			public void onItemClick(AdapterView<?> arg0, View view, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				CheckBox cbox = (CheckBox) view.findViewById(R.id.cbox);
				if (cbox.isChecked()) {
					playlists.get(arg2).setChecked(false);
					selectedPlaylists.remove(playlists.get(arg2));
				} else {
					playlists.get(arg2).setChecked(true);
					selectedPlaylists.add(playlists.get(arg2));
				}
				;
			}
		});

		builder.setTitle(
				getResources().getString(R.string.select_playlist_label))
				.setView(list)
				.setPositiveButton(R.string.ok_label, null)
				.setNegativeButton(R.string.cancel_label,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int whichButton) {
							}
						});
		AlertDialog dialog = builder.create();
		dialog.show();
		dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(
				new View.OnClickListener() {
					public void onClick(View v) {
						// TODO Auto-generated method stub
						if (!selectedPlaylists.isEmpty()) {
							listener.addTrackToPlaylist(track,
									selectedPlaylists);
							dismiss();
						}
					}
				});
		return dialog;
	}
}
