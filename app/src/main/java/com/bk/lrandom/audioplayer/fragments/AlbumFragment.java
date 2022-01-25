package com.bk.lrandom.audioplayer.fragments;

import java.util.ArrayList;

import com.bk.lrandom.audioplayer.R;
import com.bk.lrandom.audioplayer.TrackActivity;
import com.bk.lrandom.audioplayer.adapter.AlbumAdapter;
import com.bk.lrandom.audioplayer.conf.constants;

import com.bk.lrandom.audioplayer.models.Album;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class AlbumFragment extends Fragment {
	ArrayList<Album> albums;

	public static final AlbumFragment newInstance() {
		AlbumFragment fragment = new AlbumFragment();
		return fragment;
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
		View view = inflater.inflate(R.layout.listview_container_layout, container,
				false);
		if (getArguments() != null) {
			albums = getArguments()
					.getParcelableArrayList(constants.ALBUMS_KEY);
			if (albums != null) {
				AlbumAdapter albumAdapter = new AlbumAdapter(getActivity()
						.getApplicationContext(), R.layout.album_item_layout,
						albums);
				ListView lstAlbum = (ListView) view.findViewById(R.id.list);
				lstAlbum.setAdapter(albumAdapter);
				lstAlbum.setOnItemClickListener(new OnItemClickListener() {
					public void onItemClick(AdapterView<?> arg0, View arg1,
							int arg2, long arg3) {
						// TODO Auto-generated method stub
						Intent intent = new Intent(getActivity()
								.getApplicationContext(), TrackActivity.class);
						Bundle bundle = new Bundle();
						bundle.putInt(constants.ALBUM_ID_KEY, albums.get(arg2)
								.getId());
						intent.putExtras(bundle);
						startActivity(intent);
					}
				});
			}
		}
		return view;
	}
}
