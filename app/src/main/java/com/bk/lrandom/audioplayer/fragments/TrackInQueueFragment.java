package com.bk.lrandom.audioplayer.fragments;

import java.util.ArrayList;
import java.util.HashMap;

import com.bk.lrandom.audioplayer.R;
import com.bk.lrandom.audioplayer.TouchInterceptor;
import com.bk.lrandom.audioplayer.adapter.TrackInQueueAdapter;
import com.bk.lrandom.audioplayer.business.Ultils;
import com.bk.lrandom.audioplayer.conf.constants;
import com.bk.lrandom.audioplayer.interfaces.TrackInQueueFragmentComunicator;
import com.bk.lrandom.audioplayer.models.Track;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.AdapterContextMenuInfo;

public class TrackInQueueFragment extends Fragment {
	ArrayList<Track> tracks;
	int trackIndex = 0;
	private TrackInQueueFragmentComunicator listener;
	TouchInterceptor list;
	TrackInQueueAdapter trackAdapter;

	private TouchInterceptor.DropListener dropListener = new TouchInterceptor.DropListener() {
		public void drop(int from, int to) {
			// Assuming that item is moved up the list
			int direction = -1;
			int loop_start = from;
			int loop_end = to;

			// For instance where the item is dragged down the list
			if (from < to) {
				direction = 1;
			}

			Track target = tracks.get(from);

			for (int i = loop_start; i != loop_end; i = i + direction) {
				tracks.set(i, tracks.get(i + direction));
			}

			if (from == trackIndex) {
				trackIndex = to;
			}

			if ((trackIndex < from && trackIndex > to)
					|| (trackIndex < to && trackIndex > from)) {
				trackIndex += (-direction);
			}

			tracks.set(to, target);
			((ArrayAdapter<Track>) list.getAdapter()).notifyDataSetChanged();
			listener.getTrackIndex(trackIndex);
		};
	};

	public static final TrackInQueueFragment newInstance() {
		TrackInQueueFragment fragment = new TrackInQueueFragment();
		return fragment;
	}

	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		listener = (TrackInQueueFragmentComunicator) activity;
	}

	public void setTrackIndex(int trackIndex) {
		this.trackIndex = trackIndex;
		if (trackAdapter != null && list != null) {
			trackAdapter.notifyDataSetChanged();
			list.setSelection(trackIndex);
		}
	}

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
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.listview_track_in_queue_layout,
				container, false);
		if (getArguments() != null) {
			trackIndex = getArguments().getInt(constants.TRACK_INDEX_KEY);
			tracks = getArguments()
					.getParcelableArrayList(constants.TRACKS_KEY);
			tracks.get(trackIndex).setSelected(true);
			trackAdapter = new TrackInQueueAdapter(getActivity(),
					R.layout.track_in_queue_item_layout, tracks, getActivity()
							.getSupportFragmentManager());
			list = (TouchInterceptor) view.findViewById(R.id.list);
			list.setAdapter(trackAdapter);
			list.setSelection(trackIndex);
			list.setDropListener(dropListener);
			list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					// TODO Auto-generated method stub
					list.setSelection(arg2);
					tracks.get(trackIndex).setSelected(false);
					trackIndex = arg2;
					tracks.get(arg2).setSelected(true);
					listener.LoadTrackAndPlay(trackIndex, tracks, true);
				}
			});
		}
		return view;
	}
}
