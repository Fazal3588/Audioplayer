package com.bk.lrandom.audioplayer.adapter;

import java.util.ArrayList;

import com.bk.lrandom.audioplayer.R;
import com.bk.lrandom.audioplayer.models.Artist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class ArtistAdapter extends ArrayAdapter<Artist> {
	private Context context;
	private int itemLayoutResource;
	public ArtistAdapter(Context context, int itemLayoutResource,
			ArrayList<Artist> items) {
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
			view = inflater.inflate(itemLayoutResource, null);
		}
		Artist artist = getItem(position);
		TextView artistName = (TextView) view.findViewById(R.id.artistName);
		artistName.setText(artist.getArtist());
		return view;
	}
}
