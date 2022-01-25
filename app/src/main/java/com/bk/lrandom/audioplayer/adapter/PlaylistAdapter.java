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
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

import com.bk.lrandom.audioplayer.R;
import com.bk.lrandom.audioplayer.conf.constants;
import com.bk.lrandom.audioplayer.dals.PlaylistDal;
import com.bk.lrandom.audioplayer.fragments.CustomPromptDialogFragment;
import com.bk.lrandom.audioplayer.models.Playlist;
import com.bk.lrandom.audioplayer.models.Track;

public class PlaylistAdapter extends ArrayAdapter<Playlist> implements
		OnItemSelectedListener {
	private Context context;
	private int itemLayoutResource;
	private ArrayList<Playlist> playlists;
	private Spinner spinner;
	private FragmentManager fragmentManager;

	public PlaylistAdapter(Context context, int itemLayoutResource,
			ArrayList<Playlist> items, FragmentManager fragmentManager) {
		super(context, itemLayoutResource, items);
		this.itemLayoutResource = itemLayoutResource;
		this.context = context;
		this.playlists = items;
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
		Playlist playlistItem = getItem(position);
		TextView title = (TextView) view.findViewById(R.id.name);
		title.setText(playlistItem.getName());
		spinner = (Spinner) view.findViewById(R.id.menu_spinner);
		if (spinner != null) {
			spinner.setTag(Integer.valueOf(position));
			String[] menus = context.getResources().getStringArray(
					R.array.playlist_menu);
			DropdownMenuAdapter adapter = new DropdownMenuAdapter(context,
					android.R.layout.simple_list_item_1, menus);
			adapter.setDropDownViewResource(R.layout.dropdown_menu_item_layout);
			spinner.setAdapter(adapter);
			spinner.setOnItemSelectedListener(this);
		}
		CheckBox cbox = (CheckBox) view.findViewById(R.id.cbox);
		if (cbox != null) {
			if (playlistItem.getChecked()) {
				cbox.setChecked(true);
			} else {
				cbox.setChecked(false);
			}
		}
		return view;
	}

	@Override
	public void onItemSelected(AdapterView<?> view, View arg1, int position,
			long arg3) {
		// TODO Auto-generated method stub
		final int index = (Integer) view.getTag();
		switch (position) {
		case 1:
			Bundle bundle = new Bundle();
			bundle.putParcelable(constants.COMMON_KEY, playlists.get(index));
			CustomPromptDialogFragment dialog = new CustomPromptDialogFragment();
			dialog.setArguments(bundle);
			dialog.show(fragmentManager, "dialog");
			break;

		case 2:
			AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
			dialogBuilder.setMessage(context.getResources().getString(
					R.string.confirm_delete_playlist_msg));
			dialogBuilder.setPositiveButton(
					context.getResources().getString(R.string.ok_label),
					new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							PlaylistDal playlistDal = new PlaylistDal(context);
							Playlist removePlaylist = playlists.get(index);
							int row = playlistDal.removePlayList(removePlaylist
									.getId());
							if (row != 0) {
								Toast toast = Toast.makeText(
										context,
										removePlaylist.getName()
												+ " "
												+ context
														.getResources()
														.getString(
																R.string.file_deleted_result_msg),
										Integer.parseInt(context
												.getResources()
												.getString(
														R.string.toast_time_out)));
								toast.show();
								playlists.remove(index);
								notifyDataSetChanged();
							}
							playlistDal.close();
						}
					});
			dialogBuilder.setNegativeButton(
					context.getResources().getString(R.string.cancel_label),
					new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub

						}
					});
			dialogBuilder.create().show();
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
