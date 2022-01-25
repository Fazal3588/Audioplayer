package com.bk.lrandom.audioplayer.fragments;

import com.bk.lrandom.audioplayer.R;
import com.bk.lrandom.audioplayer.conf.constants;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class AlbumArtFragment extends Fragment {
	public static final String EXTRA_MESSAGE = "EXTRA_MESSAGE";
	private String albumArtUri;
	int trackIndex = 0;

	public static final AlbumArtFragment newInstance() {
		AlbumArtFragment frm = new AlbumArtFragment();
		return frm;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.disc_fragment_layout, container,
				false);
		if (getArguments() != null) {
			albumArtUri = getArguments().getString(constants.ALBUM_ART_URI_KEY);
			ImageView albumArt = (ImageView) view.findViewById(R.id.albumArt);
			if (albumArtUri != null && albumArtUri != "") {
				albumArt.setImageURI(Uri.parse(albumArtUri));
			} else {
				albumArt.setImageResource(R.drawable.cd);
			}
		}
		return view;
	}

	public void setAlbumArt(String albumArtUri) {
		this.albumArtUri = albumArtUri;
		if (getView() != null) {
			ImageView albumArt = (ImageView) getView().findViewById(
					R.id.albumArt);
			if (albumArtUri != null && albumArtUri != "") {
				albumArt.setImageURI(Uri.parse(albumArtUri));
			} else {
				albumArt.setImageResource(R.drawable.cd);
			}
		}
	}

	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
	}
}
