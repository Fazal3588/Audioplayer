package com.bk.lrandom.audioplayer.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bk.lrandom.audioplayer.R;
import com.bk.lrandom.audioplayer.models.Album;

public class AlbumAdapter extends ArrayAdapter<Album> {
	private Context context;
	private int itemLayoutResource;
	public AlbumAdapter(Context context, int itemLayoutResource,
			ArrayList<Album> items) {
		super(context, itemLayoutResource, items);
		this.context = context;
		this.itemLayoutResource=itemLayoutResource;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		View view = convertView;
		if (view == null) {
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = inflater.inflate(this.itemLayoutResource, null);
		}
		Album album = getItem(position);
		TextView title = (TextView) view.findViewById(R.id.title);
		title.setText(album.getTitle());
		TextView artist = (TextView) view.findViewById(R.id.artistName);
		artist.setText(album.getArtist());
		ImageView albumArt = (ImageView) view.findViewById(R.id.albumArt);
		String albumArtUri = album.getAlbumArt();
		if (albumArtUri != null) {
			albumArt.setImageURI(Uri.parse(albumArtUri));
		} else {
			albumArt.setImageResource(R.drawable.ic_album);
		}
		return view;
	}
}
