package com.bk.lrandom.audioplayer.fragments;

import java.util.ArrayList;

import com.bk.lrandom.audioplayer.R;
import com.bk.lrandom.audioplayer.TrackActivity;
import com.bk.lrandom.audioplayer.adapter.PlaylistAdapter;
import com.bk.lrandom.audioplayer.conf.constants;
import com.bk.lrandom.audioplayer.dals.PlaylistDal;
import com.bk.lrandom.audioplayer.models.Playlist;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;

public class PlaylistFragment extends Fragment {
	ArrayList<Playlist> playlists;
	PlaylistAdapter playlistAdapter;
	ListView list;

	public static final PlaylistFragment newInstance() {
		PlaylistFragment fragment = new PlaylistFragment();
		return fragment;
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		// TODO Auto-generated method stub
		super.onCreateContextMenu(menu, v, menuInfo);
	}

	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.listview_container_layout,
				container, false);
		if (getArguments() != null) {
			playlists = getArguments().getParcelableArrayList(
					constants.PLAYLISTS_KEY);
			if (playlists != null) {
				playlistAdapter = new PlaylistAdapter(getActivity(),
						R.layout.playlist_item_layout, playlists, getActivity()
								.getSupportFragmentManager());
				list = (ListView) view.findViewById(R.id.list);
				list.setAdapter(playlistAdapter);
				list.setOnItemClickListener(new OnItemClickListener() {
					public void onItemClick(AdapterView<?> arg0, View arg1,
							int arg2, long arg3) {
						// TODO Auto-generated method stub
						Intent intent = new Intent(getActivity()
								.getApplicationContext(), TrackActivity.class);
						Bundle bundle = new Bundle();
						bundle.putInt(constants.PLAYLIST_ID_KEY,
								playlists.get(arg2).getId());
						intent.putExtras(bundle);
						startActivity(intent);
					}
				});
				registerForContextMenu(list);
			}
		}
		return view;
	}

	public void refreshViewAfterAddItem(Playlist playlist) {
		playlists.add(playlist);
		playlistAdapter.notifyDataSetChanged();
	}

	public void refreshViewAfterRemoveItem(Playlist playlist) {
		playlists.remove(playlist);
		playlistAdapter.notifyDataSetChanged();
	}

	public void refresh(Playlist playlist) {
		int index = findIndexById(playlist.getId());
		if (index >= 0) {
			playlists.get(index).setName(playlist.getName());
		}
		playlistAdapter.notifyDataSetChanged();
	}

	private int findIndexById(int id) {
		for (int i = 0; i < playlists.size(); i++) {
			if (playlists.get(i).getId() == id) {
				return i;
			}
		}
		return -1;
	}

	@Override
	public void onDestroyView() {
		// TODO Auto-generated method stub
		super.onDestroyView();
	}
}
