package com.bk.lrandom.audioplayer.adapter;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

import com.bk.lrandom.audioplayer.R;
import com.bk.lrandom.audioplayer.business.Ultils;
import com.bk.lrandom.audioplayer.conf.constants;
import com.bk.lrandom.audioplayer.fragments.CustomSelectPlaylistDialogFragment;
import com.bk.lrandom.audioplayer.models.Track;

public class TrackInQueueAdapter extends ArrayAdapter<Track> implements
		OnItemSelectedListener {
	private Context context;
	private int itemLayoutResource;
	private ArrayList<Track> tracks;
	Spinner spinner;
	String[] menus;
	FragmentManager fragmentManager;

	public TrackInQueueAdapter(Context context, int itemLayoutResource,
			ArrayList<Track> items, FragmentManager fragmentManager) {
		super(context, itemLayoutResource, items);
		this.itemLayoutResource = itemLayoutResource;
		this.context = context;
		this.tracks = items;
		this.fragmentManager = fragmentManager;
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
		Track track = getItem(position);
		TextView title = (TextView) view.findViewById(R.id.title);
		title.setText(track.getTitle());
		TextView artist = (TextView) view.findViewById(R.id.artist);
		artist.setText(track.getArtist());
		if (track.getSelected()) {
			title.setTextColor(view.getResources().getColor(
					R.color.selected_item_title));
			artist.setTextColor(view.getResources().getColor(
					R.color.selected_item_title));
			view.setBackgroundColor(view.getResources().getColor(
					R.color.selected_item));
		} else {
			title.setTextColor(view.getResources().getColor(R.color.title_item));
			artist.setTextColor(view.getResources().getColor(
					R.color.artist_title_item));
			view.setBackgroundColor(view.getResources()
					.getColor(R.color.bg_app));
		}
//		spinner = (Spinner) view.findViewById(R.id.menu_spinner);
//		spinner.setTag(Integer.valueOf(position));
//		menus = context.getResources().getStringArray(R.array.track_menu);
//		DropdownMenuAdapter adapter = new DropdownMenuAdapter(context,
//				android.R.layout.simple_list_item_1, menus);
//		adapter.setDropDownViewResource(R.layout.dropdown_menu_item_layout);
//		spinner.setAdapter(adapter);
//		spinner.setOnItemSelectedListener(this);
		return view;
	}

	@Override
	public void onItemSelected(AdapterView<?> view, View arg1, int position,
			long arg3) {
		// TODO Auto-generated method stub
		final int index = (Integer) view.getTag();
		switch (position) {
		case 1:
			String dialogMsg = context.getResources().getString(
					R.string.confirm_delete_audio_msg);
			String okLabel = context.getResources()
					.getString(R.string.ok_label);
			String cancelLabel = context.getResources().getString(
					R.string.cancel_label);
			final String toastTimeout = context.getResources().getString(
					R.string.toast_time_out);

			AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
			dialogBuilder.setMessage(dialogMsg);
			dialogBuilder.setPositiveButton(okLabel,
					new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							Boolean deleted = Ultils.removeTrack(context,
									tracks.get(index));
							if (deleted) {
								String toastMsg = tracks.get(index).getTitle()
										+ " "
										+ context
												.getResources()
												.getString(
														R.string.track_has_been_set_as_ringtone_msg);
								Toast toast = Toast.makeText(context, toastMsg,
										Integer.parseInt(toastTimeout));
								toast.show();
								tracks.remove(index);
								notifyDataSetChanged();
							}
						}
					});
			dialogBuilder.setNegativeButton(cancelLabel,
					new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
						}
					});
			dialogBuilder.create().show();
			break;

		case 2:
			Ultils.setRingtone(index, tracks.get(index), context);
			break;

		case 3:
			CustomSelectPlaylistDialogFragment dialog = CustomSelectPlaylistDialogFragment
					.newInstance();
			Bundle bundle = new Bundle();
			bundle.putParcelable(constants.TRACK_INDEX_KEY, tracks.get(index));
			dialog.setArguments(bundle);
			dialog.show(fragmentManager, "dialog");
			break;

		default:
			break;
		}
		view.setSelection(0);
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub

	}
}