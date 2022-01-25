package com.bk.lrandom.audioplayer.fragments;

import java.util.ArrayList;

import com.bk.lrandom.audioplayer.R;
import com.bk.lrandom.audioplayer.business.Ultils;
import com.bk.lrandom.audioplayer.conf.constants;
import com.bk.lrandom.audioplayer.models.Track;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.ListView;
public class TrackFragment extends Fragment {
	ArrayList<Track> tracks;
	ListView list;

	public static final TrackFragment newInstance() {
		TrackFragment fragment = new TrackFragment();
		return fragment;
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
		View view = inflater.inflate(R.layout.listview_container_layout,
				container, false);
		if (getArguments() != null) {
			tracks = getArguments()
					.getParcelableArrayList(constants.TRACKS_KEY);
			if (tracks != null) {
				list = (ListView) view.findViewById(R.id.list);
				Ultils.sendTrackToPlayer(getActivity(), tracks, list,
						getActivity().getSupportFragmentManager());
			}
		}
		return view;
	}

	public void refreshUI(Track track) {
		tracks.remove(track);
		list.invalidateViews();
	}
}
