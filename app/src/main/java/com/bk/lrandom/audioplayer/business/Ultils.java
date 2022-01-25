package com.bk.lrandom.audioplayer.business;

import java.io.File;
import java.util.ArrayList;

import com.bk.lrandom.audioplayer.PlayerActivity;
import com.bk.lrandom.audioplayer.R;
import com.bk.lrandom.audioplayer.adapter.TrackAdapter;
import com.bk.lrandom.audioplayer.conf.constants;
import com.bk.lrandom.audioplayer.dals.TrackDal;
import com.bk.lrandom.audioplayer.models.Track;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

public class Ultils {
	public static String Timer(long milliseconds) {
		String finalTimer = "";
		String secondsString = "";
		int hours = (int) (milliseconds / (1000 * 60 * 60));
		int minutes = (int) (milliseconds % (1000 * 60 * 60)) / (1000 * 60);
		int seconds = (int) ((milliseconds % (1000 * 60 * 60)) % (1000 * 60) / 1000);
		if (hours > 0) {
			finalTimer = hours + ":";
		}
		if (seconds < 10) {
			secondsString = "0" + seconds;
		} else {
			secondsString = "" + seconds;
		}
		finalTimer = finalTimer + minutes + ":" + secondsString;
		return finalTimer;
	}

	public static int getProgressPercentage(long currentDuration,
			long totalDuration) {
		Double percentage = (double) 0;
		long currentSeconds = (int) (currentDuration / 1000);
		long totalSeconds = (int) (totalDuration / 1000);
		percentage = (((double) currentSeconds) / totalSeconds) * 100;
		return percentage.intValue();
	}

	public static int progressToTimer(int progress, int totalDuration) {
		int currentDuration = 0;
		totalDuration = (int) (totalDuration / 1000);
		currentDuration = (int) ((((double) progress) / 100) * totalDuration);
		return currentDuration * 1000;
	}

	public static void setRingtone(int pos, Track track, Context context) {
		File file = new File(track.getPath());
		if (file.exists()) {
			ContentValues values = new ContentValues();
			values.put(MediaStore.MediaColumns._ID, track.getId());
			values.put(MediaStore.MediaColumns.DATA, track.getPath());
			values.put(MediaStore.MediaColumns.TITLE, track.getTitle());
			values.put(MediaStore.MediaColumns.SIZE, track.getSize());
			values.put(MediaStore.MediaColumns.MIME_TYPE, "audio/*");
			values.put(MediaStore.Audio.Media.ARTIST, track.getArtist());
			values.put(MediaStore.Audio.Media.DURATION, track.getDuration());
			values.put(MediaStore.Audio.Media.IS_RINGTONE, true);
			values.put(MediaStore.Audio.Media.IS_NOTIFICATION, false);
			values.put(MediaStore.Audio.Media.IS_ALARM, false);
			values.put(MediaStore.Audio.Media.IS_MUSIC, false);
			Uri uri = MediaStore.Audio.Media.getContentUriForPath(track
					.getPath());
			TrackDal audioDAL = new TrackDal(context);
			audioDAL.removeTracksOnMDS(uri, MediaStore.MediaColumns.DATA + "='"
					+ track.getPath() + "'");
			Uri newUri = audioDAL.insertTracksOnMDS(uri, values);
			RingtoneManager.setActualDefaultRingtoneUri(context,
					RingtoneManager.TYPE_RINGTONE, newUri);
			Toast ts = Toast
					.makeText(
							context,
							track.getTitle()
									+ " "
									+ context
											.getResources()
											.getString(
													R.string.track_has_been_set_as_ringtone_msg),
							Integer.parseInt(context.getResources().getString(
									R.string.toast_time_out)));
			ts.show();
		} else {
			Toast ts = Toast.makeText(context, context.getResources()
					.getString(R.string.track_cannot_been_set_as_ringtone_msg),
					Integer.parseInt(context.getResources().getString(
							R.string.toast_time_out)));
			ts.show();
		}
	}

	public static Boolean removeTrack(final Context context, final Track track) {
		final ContentResolver contentResolver = context.getContentResolver();
		File file = new File(track.getPath());
		Uri uri = MediaStore.Audio.Media.getContentUriForPath(track.getPath());
		contentResolver.delete(uri,
				MediaStore.Audio.Media._ID + "='" + track.getId() + "'", null);
		TrackDal trackDAL = new TrackDal(context);
		trackDAL.getConnect();
		trackDAL.removeTracksByID(track.getId());
		trackDAL.close();
		boolean deleted = file.delete();
		return deleted;
	}

	public static void sendTrackToPlayer(final Context context,
			final ArrayList<Track> tracks, ListView list,
			FragmentManager fragmentManager) {
		if (!tracks.isEmpty()) {
			TrackAdapter trackAdapter = new TrackAdapter(context,
					R.layout.track_item_layout, tracks, fragmentManager);
			list.setAdapter(trackAdapter);
			list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
				@SuppressLint("InlinedApi")
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					// TODO Auto-generated method stub
					Bundle bundle = new Bundle();
					bundle.putParcelableArrayList(constants.TRACKS_KEY, tracks);
					bundle.putInt(constants.TRACK_INDEX_KEY, arg2);
					Intent intent = new Intent(context, PlayerActivity.class);
					intent.putExtras(bundle);
					intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK
							| Intent.FLAG_ACTIVITY_NEW_TASK);
					context.getApplicationContext().startActivity(intent);
				}
			});
		}
	}
}
