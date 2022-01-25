package com.bk.lrandom.audioplayer.services;

import java.util.ArrayList;
import java.util.Random;

import com.bk.lrandom.audioplayer.PlayerActivity;
import com.bk.lrandom.audioplayer.R;
import com.bk.lrandom.audioplayer.conf.constants;
import com.bk.lrandom.audioplayer.models.Track;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;
import android.support.v4.app.NotificationCompat;

public class AudioPlayerService extends Service implements OnCompletionListener {
	MediaPlayer mediaPlayer;
	NotificationManager notificationManager;
	boolean isRepeat, isShuffle;
	int trackIndex;
	ArrayList<Track> tracks;
	private final IBinder mBinder = new PlayerBinder();
	private int NOTIFICATION_ID = 111;
	public static final String ON_COMPLETE_BROADCAST_ACTION = "com.bk.lrandom.audioplayer.audioplayerService";
	Intent intent;

	public class PlayerBinder extends Binder {
		public AudioPlayerService getService() {
			return AudioPlayerService.this;
		}
	};

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return mBinder;
	}

	public void onCreate() {
		super.onCreate();
		// instance player object
		mediaPlayer = new MediaPlayer();
		//mediaPlayer.setOnCompletionListener(this);
		notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		intent = new Intent(ON_COMPLETE_BROADCAST_ACTION);
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		return START_STICKY;
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		try {
			if (mediaPlayer.isPlaying()) {
				mediaPlayer.stop();
			}
			mediaPlayer.release();
		} catch (Exception e) {
			// TODO: handle exception
		}
		Log.i("DESTROY SERVICE", "destroy");
	}

	public void play(int trackIndex, ArrayList<Track> tracks) {
		try {
			if (mediaPlayer != null) {
				this.tracks = tracks;
				this.trackIndex = trackIndex;
				mediaPlayer.reset();
				mediaPlayer.setDataSource(this.tracks.get(this.trackIndex)
						.getPath());
				mediaPlayer.prepare();
				mediaPlayer.start();
				showNotification();
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	};

	public void pause() {
		try {
			if (mediaPlayer.isPlaying()) {
				mediaPlayer.pause();
				cancelNotification();
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	public void resume() {
		try {
			if (mediaPlayer != null) {
				mediaPlayer.start();
				showNotification();
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	public int getTotalTime() {
		return mediaPlayer.getDuration();
	}

	public int getElapsedTime() {
		return mediaPlayer.getCurrentPosition();
	}

	public void seek(int pos) {
		mediaPlayer.seekTo(pos);
	}

	public boolean isPlay() {
		if (mediaPlayer.isPlaying()) {
			return true;
		} else {
			return false;
		}
	}

	public void setRepeat(boolean flag) {
		this.isRepeat = flag;
	}

	public void setShuffle(boolean flag) {
		this.isShuffle = flag;
	}

	public boolean getRepeat() {
		return this.isRepeat;
	}

	public boolean getShuffle() {
		return this.isShuffle;
	}

	public ArrayList<Track> getTrack() {
		return this.tracks;
	}

	public int getTrackIndex() {
		return this.trackIndex;
	}

	public void showNotification() {
		NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(
				getApplicationContext());

		Intent intent = new Intent(this, PlayerActivity.class);
		PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
				intent, 0);

		notificationBuilder.setContentTitle(tracks.get(trackIndex).getTitle())
				.setContentText(tracks.get(trackIndex).getArtist())
				.setSmallIcon(R.drawable.ic_small_logo)
				.setContentIntent(contentIntent);
		Notification notification = notificationBuilder.build();
		notificationManager.notify(NOTIFICATION_ID, notification);
		startForeground(NOTIFICATION_ID, notification);
	}

	public void cancelNotification() {
		stopForeground(true);
	}

	public void onCompletion(MediaPlayer mp) {
		// TODO Auto-generated method stub
		tracks.get(trackIndex).setSelected(false);
		Log.i("NOT NEXT", "Please next");
		if (!this.isRepeat) {
			if (!this.isShuffle) {
				if (this.trackIndex == (this.tracks.size() - 1)) {
					this.trackIndex = 0;
				} else {
					this.trackIndex += 1;
				}
			} else {
				Random rand = new Random();
				this.trackIndex = rand
						.nextInt((this.tracks.size() - 1) - 0 + 1) + 0;
			}
		}
		tracks.get(trackIndex).setSelected(true);
		play(trackIndex, tracks);
		intent.putExtra(constants.TRACK_INDEX_KEY, trackIndex + "");
		sendBroadcast(intent);
	}
}
