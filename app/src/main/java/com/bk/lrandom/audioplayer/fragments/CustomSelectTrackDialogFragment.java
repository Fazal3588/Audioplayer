package com.bk.lrandom.audioplayer.fragments;

import java.util.ArrayList;

import com.bk.lrandom.audioplayer.R;
import com.bk.lrandom.audioplayer.adapter.TrackAdapter;
import com.bk.lrandom.audioplayer.dals.TrackDal;
import com.bk.lrandom.audioplayer.interfaces.CustomSelectTrackDialogComunicator;
import com.bk.lrandom.audioplayer.models.Track;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.support.v4.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.ListView;

public class CustomSelectTrackDialogFragment extends DialogFragment {
	TrackDal trackDal;
	TrackAdapter trackAdapter;
	ArrayList<Track> tracks;
	ArrayList<Track> selectedTracks;
	CustomSelectTrackDialogComunicator listener;

	public static final CustomSelectTrackDialogFragment newInstance() {
		CustomSelectTrackDialogFragment fragment = new CustomSelectTrackDialogFragment();
		return fragment;
	}

	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		listener = (CustomSelectTrackDialogComunicator) activity;
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		trackDal = new TrackDal(getActivity().getApplicationContext());
		tracks = trackDal.getTracksOnMDS();
		selectedTracks = new ArrayList<Track>();
		trackAdapter = new TrackAdapter(getActivity().getApplicationContext(),
				R.layout.track_item_select_layout, tracks, getActivity()
						.getSupportFragmentManager());
		ListView list = new ListView(getActivity());
		list.setAdapter(trackAdapter);
		list.setItemsCanFocus(false);
		list.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
		list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			public void onItemClick(AdapterView<?> arg0, View view, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				CheckBox cbox = (CheckBox) view.findViewById(R.id.cbox);
				if (cbox.isChecked()) {
					tracks.get(arg2).setChecked(false);
					selectedTracks.remove(tracks.get(arg2));
				} else {
					tracks.get(arg2).setChecked(true);
					selectedTracks.add(tracks.get(arg2));
				}
				;
			}
		});

		builder.setTitle(getResources().getString(R.string.select_track_label))
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
						if (!selectedTracks.isEmpty()) {
							listener.savePlaylist(selectedTracks);
							dismiss();
						}
					}
				});
		return dialog;
	}
}
