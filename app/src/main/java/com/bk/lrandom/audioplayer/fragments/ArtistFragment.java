package com.bk.lrandom.audioplayer.fragments;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import com.bk.lrandom.audioplayer.R;
import com.bk.lrandom.audioplayer.TrackActivity;
import com.bk.lrandom.audioplayer.adapter.ArtistAdapter;
import com.bk.lrandom.audioplayer.conf.constants;
import com.bk.lrandom.audioplayer.models.Artist;

public class ArtistFragment extends Fragment {
	ArrayList<Artist> artist;

	public static final ArtistFragment newInstance() {
		ArtistFragment fragment = new ArtistFragment();
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
		View view = inflater.inflate(R.layout.listview_container_layout,
				container, false);
		if (getArguments() != null) {
			artist = getArguments().getParcelableArrayList(
					constants.ARTISTS_KEY);
			if (artist != null) {
				ArtistAdapter artistAdapter = new ArtistAdapter(getActivity()
						.getApplicationContext(), R.layout.artist_item_layout,
						artist);
				ListView lstArtist = (ListView) view
						.findViewById(R.id.list);
				lstArtist.setAdapter(artistAdapter);
				lstArtist.setOnItemClickListener(new OnItemClickListener() {
					public void onItemClick(AdapterView<?> arg0, View arg1,
							int arg2, long arg3) {
						// TODO Auto-generated method stub
						Intent intent = new Intent(getActivity()
								.getApplicationContext(), TrackActivity.class);
						Bundle bundle = new Bundle();
						bundle.putInt(constants.ARTIST_ID_KEY, artist.get(arg2)
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
