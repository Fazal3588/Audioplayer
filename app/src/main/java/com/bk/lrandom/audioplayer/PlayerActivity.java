package com.bk.lrandom.audioplayer;

import java.util.ArrayList;
import java.util.Random;

import com.bk.lrandom.audioplayer.business.Ultils;
import com.bk.lrandom.audioplayer.conf.constants;
import com.bk.lrandom.audioplayer.dals.AlbumDal;
import com.bk.lrandom.audioplayer.dals.TrackDal;
import com.bk.lrandom.audioplayer.fragments.TrackInQueueFragment;
import com.bk.lrandom.audioplayer.fragments.AlbumArtFragment;
import com.bk.lrandom.audioplayer.models.Album;
import com.bk.lrandom.audioplayer.models.Playlist;
import com.bk.lrandom.audioplayer.models.Track;
import com.bk.lrandom.audioplayer.services.AudioPlayerService;
import com.bk.lrandom.audioplayer.interfaces.CustomSelectPlaylistDialogComunicator;
import com.bk.lrandom.audioplayer.interfaces.TrackInQueueFragmentComunicator;
import com.google.android.gms.ads.NativeExpressAdView;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.ResultReceiver;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.SeekBar.OnSeekBarChangeListener;

public class PlayerActivity extends ActionBarParentActivity implements
		OnSeekBarChangeListener, TrackInQueueFragmentComunicator,
		CustomSelectPlaylistDialogComunicator {
	ViewPager pager;
	com.bk.lrandom.audioplayer.adapter.PagerAdapter pagerAdapter;
	AudioPlayerService audioPlayerService;
	Handler handler = new Handler();
	Intent intenService;
	ArrayList<Track> tracks;
	TextView txtTitle, txtTotalTime, txtElapsedTime, txtArtist, txtTrackTitle;
	ImageButton btnPlayAndPause, btnNext, btnPrev, btnRepeat, btnShuffle,
			btnPlaylist, btnSoundControl;
	SeekBar prgTrack;
	boolean isRepeat, isShuffle;
	int trackIndex;
	CustomResultReceiver customResultReceiver;
	Boolean newTask = false;
	Button rectangle_1;
	Button rectangle_2;

	private BroadcastReceiver onFinishTrackBroadCastReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			tracks.get(trackIndex).setSelected(false);
			trackIndex = Integer.parseInt(intent
					.getStringExtra(constants.TRACK_INDEX_KEY));
			updateUI();
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		Intent intent = new Intent(getApplicationContext(),
				AudioPlayerService.class);
		customResultReceiver = new CustomResultReceiver(null);
		intent.putExtra("receiver", customResultReceiver);
		getApplicationContext().bindService(intent, serviceConnection,
				Context.BIND_AUTO_CREATE);
		setContentView(R.layout.player_activity_layout);

		Bundle bundle = getIntent().getExtras();
		if (bundle != null) {
			newTask = true;
			tracks = bundle.getParcelableArrayList(constants.TRACKS_KEY);
			trackIndex = bundle.getInt(constants.TRACK_INDEX_KEY);
			isShuffle = false;
			isRepeat = false;
		}
		;

		NativeExpressAdView adView = (NativeExpressAdView) findViewById(R.id.adView);
		loadAd(adView);
	}// end

	private void updateUI() {
		ActionBar actionBar = getSupportActionBar();
		actionBar.setTitle(tracks.get(trackIndex).getTitle());
		btnPlayAndPause = (ImageButton) findViewById(R.id.btnPlayandPause);
		btnNext = (ImageButton) findViewById(R.id.btnNext);
		btnPrev = (ImageButton) findViewById(R.id.btnPrev);
		btnRepeat = (ImageButton) findViewById(R.id.btnRepeat);
		btnShuffle = (ImageButton) findViewById(R.id.btnShuffle);
		txtTotalTime = (TextView) findViewById(R.id.txtTotalTime);
		txtElapsedTime = (TextView) findViewById(R.id.txtElapsedTime);
		prgTrack = (SeekBar) findViewById(R.id.prgTrack);

		rectangle_1 = (Button) findViewById(R.id.rectangle_1);
		rectangle_2 = (Button) findViewById(R.id.rectangle_2);

		prgTrack.setOnSeekBarChangeListener(this);
		btnPlayAndPause.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (audioPlayerService.isPlay()) {
					audioPlayerService.pause();
					btnPlayAndPause.setImageResource(R.drawable.ic_btn_play);
				} else {
					audioPlayerService.resume();
					btnPlayAndPause.setImageResource(R.drawable.ic_btn_pause);
				}
			}
		});

		if (isRepeat) {
			btnRepeat.setImageResource(R.drawable.ic_btn_repeat_pressed);
		} else {
			btnRepeat.setImageResource(R.drawable.ic_btn_repeat);
		}

		btnRepeat.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (!isRepeat) {
					isRepeat = true;
					audioPlayerService.setRepeat(true);
					btnRepeat
							.setImageResource(R.drawable.ic_btn_repeat_pressed);
				} else {
					isRepeat = false;
					audioPlayerService.setRepeat(false);
					btnRepeat.setImageResource(R.drawable.ic_btn_repeat);
				}
			}
		});

		if (isShuffle) {
			btnShuffle.setImageResource(R.drawable.ic_btn_shuffle_pressed);
		} else {
			btnShuffle.setImageResource(R.drawable.ic_btn_shuffle);
		}

		btnShuffle.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (!isShuffle) {
					isShuffle = true;
					audioPlayerService.setShuffle(true);
					btnShuffle
							.setImageResource(R.drawable.ic_btn_shuffle_pressed);
				} else {
					isShuffle = false;
					audioPlayerService.setShuffle(false);
					btnShuffle.setImageResource(R.drawable.ic_btn_shuffle);
				}
			}
		});

		btnNext.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				// TODO Auto-generated method stub
				tracks.get(trackIndex).setSelected(false);
				if (!isShuffle) {
					if (trackIndex == (tracks.size() - 1)) {
						trackIndex = 0;
					} else {
						trackIndex += 1;
					}
				} else {
					Random rand = new Random();
					trackIndex = rand.nextInt((tracks.size() - 1) - 0 + 1) + 0;
				}
				audioPlayerService.play(trackIndex, tracks);
				updateUI();
			}
		});

		btnPrev.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				// TODO Auto-generated method stub
				tracks.get(trackIndex).setSelected(false);
				if (isShuffle) {
					if (trackIndex == 0) {
						trackIndex = tracks.size() - 1;
					} else {
						trackIndex -= 1;
					}
				} else {
					Random rand = new Random();
					trackIndex = rand.nextInt((tracks.size() - 1) - 0 + 1) + 0;
				}
				audioPlayerService.play(trackIndex, tracks);
				updateUI();
			}
		});

		if (audioPlayerService.isPlay()) {
			btnPlayAndPause.setImageResource(R.drawable.ic_btn_pause);
		} else {
			btnPlayAndPause.setImageResource(R.drawable.ic_btn_play);
		}

		updateProgress();

		if (pagerAdapter != null) {
			TrackInQueueFragment trackInQueue = (TrackInQueueFragment) (pagerAdapter
					.getItem(0));
			tracks.get(trackIndex).setSelected(true);
			trackInQueue.setTrackIndex(trackIndex);
			AlbumArtFragment albumArt = (AlbumArtFragment) (pagerAdapter
					.getItem(1));
			AlbumDal albumDal = new AlbumDal(this);
			ArrayList<Album> albums = albumDal.getAlbumsByIdOnMDS(tracks.get(
					trackIndex).getAlbumId());
			if (!albums.isEmpty()) {
				String albumArtUri = albums.get(0).getAlbumArt();
				albumArt.setAlbumArt(albumArtUri);
			}
			;
		}
	}// end update ui

	private ArrayList<Fragment> getPageFragments() {
		ArrayList<Fragment> fragmentList = new ArrayList<Fragment>();
		Bundle bundle = new Bundle();
		bundle.putParcelableArrayList(constants.TRACKS_KEY, tracks);
		bundle.putInt(constants.TRACK_INDEX_KEY, trackIndex);
		TrackInQueueFragment currentListFragment = TrackInQueueFragment
				.newInstance();
		currentListFragment.setArguments(bundle);
		fragmentList.add(currentListFragment);

		AlbumDal albumDal = new AlbumDal(this);
		ArrayList<Album> albums = albumDal.getAlbumsByIdOnMDS(tracks.get(
				trackIndex).getAlbumId());
		AlbumArtFragment albumArt = AlbumArtFragment.newInstance();
		if (!albums.isEmpty()) {
			String albumArtUri = albums.get(0).getAlbumArt();
			bundle = new Bundle();
			bundle.putString(constants.ALBUM_ART_URI_KEY, albumArtUri);
			albumArt.setArguments(bundle);
		}
		fragmentList.add(albumArt);
		return fragmentList;
	}

	@Override
	protected void onNewIntent(Intent intent) {
		// TODO Auto-generated method stub
		super.onNewIntent(intent);
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		Log.i("Destroy player", "start player");
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		if (newTask == false && audioPlayerService != null) {
			isShuffle = audioPlayerService.getShuffle();
			isRepeat = audioPlayerService.getRepeat();
			trackIndex = audioPlayerService.getTrackIndex();
			tracks.get(trackIndex).setSelected(true);
			updateUI();
		}
		registerReceiver(onFinishTrackBroadCastReceiver, new IntentFilter(
				AudioPlayerService.ON_COMPLETE_BROADCAST_ACTION));
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		unregisterReceiver(onFinishTrackBroadCastReceiver);
		Log.i("Pause", "pause");
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		Log.i("Stop", "stop");
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		Log.i("Destroy", "destroy");
	}

	private Runnable updateTime = new Runnable() {
		public void run() {
			// TODO Auto-generated method stub
			long totalDuration = audioPlayerService.getTotalTime();
			long currentDuration = audioPlayerService.getElapsedTime();
			txtTotalTime.setText("" + Ultils.Timer(totalDuration));
			txtElapsedTime.setText("" + Ultils.Timer(currentDuration));
			int progress = (int) (Ultils.getProgressPercentage(currentDuration,
					totalDuration));
			prgTrack.setProgress(progress);
			handler.postDelayed(this, 100);
		}
	};

	public void updateProgress() {
		handler.postDelayed(updateTime, 100);
	}

	public void onStartTrackingTouch(SeekBar seekBar) {
		// TODO Auto-generated method stub
		handler.removeCallbacks(updateTime);
	}

	public void onStopTrackingTouch(SeekBar seekBar) {
		// TODO Auto-generated method stub
		handler.removeCallbacks(updateTime);
		int totalDuration = audioPlayerService.getTotalTime();
		int currentPosition = Ultils.progressToTimer(seekBar.getProgress(),
				totalDuration);
		audioPlayerService.seek(currentPosition);
		updateProgress();
	}

	private ServiceConnection serviceConnection = new ServiceConnection() {

		public void onServiceDisconnected(ComponentName name) {
			// TODO Auto-generated method stub
			audioPlayerService = null;
		}

		public void onServiceConnected(ComponentName name, IBinder service) {
			// TODO Auto-generated method stub
			audioPlayerService = ((AudioPlayerService.PlayerBinder) service)
					.getService();
			if (newTask == false) {
				isShuffle = audioPlayerService.getShuffle();
				isRepeat = audioPlayerService.getRepeat();
				trackIndex = audioPlayerService.getTrackIndex();
				tracks = audioPlayerService.getTrack();
				tracks.get(trackIndex).setSelected(true);
			} else {
				audioPlayerService.play(trackIndex, tracks);
			}
			updateUI();
			ArrayList<Fragment> fragments = getPageFragments();
			pagerAdapter = new com.bk.lrandom.audioplayer.adapter.PagerAdapter(
					getSupportFragmentManager(), fragments);
			pager = (ViewPager) findViewById(R.id.viewpager);
			pager.setAdapter(pagerAdapter);
			pager.setOffscreenPageLimit(2);
			pager.setOnPageChangeListener(new OnPageChangeListener() {

				@Override
				public void onPageSelected(int position) {
					// TODO Auto-generated method stub
					switch (position) {
					case 0:
						rectangle_1
								.setBackgroundResource(R.drawable.rectangle_bg_orange);
						rectangle_2
								.setBackgroundResource(R.drawable.rectangle_bg_white);
						break;

					case 1:
						rectangle_1
								.setBackgroundResource(R.drawable.rectangle_bg_white);
						rectangle_2
								.setBackgroundResource(R.drawable.rectangle_bg_orange);
						break;

					default:
						break;
					}
				}

				@Override
				public void onPageScrolled(int arg0, float arg1, int arg2) {
					// TODO Auto-generated method stub

				}

				@Override
				public void onPageScrollStateChanged(int arg0) {
					// TODO Auto-generated method stub

				}
			});
		}
	};

	public void onProgressChanged(SeekBar seekBar, int progress,
			boolean fromUser) {
		// TODO Auto-generated method stub
	}

	public void LoadTrackAndPlay(int trackIndex, ArrayList<Track> tracks,
			Boolean updateUI) {
		try {
			this.trackIndex = trackIndex;
			audioPlayerService.play(trackIndex, tracks);
			btnPlayAndPause.setImageResource(R.drawable.ic_btn_pause);
			updateUI();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	class CustomResultReceiver extends ResultReceiver {
		public CustomResultReceiver(Handler handler) {
			super(handler);
			// TODO Auto-generated constructor stub
		}

		@Override
		protected void onReceiveResult(int resultCode, Bundle resultData) {
			// TODO Auto-generated method stub
			super.onReceiveResult(resultCode, resultData);
			if (resultCode == 200) {
				Bundle bundle = resultData;
				if (bundle.getString(constants.TRACK_INDEX_KEY) != null) {
					tracks.get(trackIndex).setSelected(false);
					trackIndex = Integer.parseInt(bundle
							.getString(constants.TRACK_INDEX_KEY));
					tracks.get(trackIndex).setSelected(true);
					audioPlayerService.play(trackIndex, tracks);
					updateUI();
				}
			}
		}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
		case android.R.id.home:
			Intent intent = new Intent(this, ExplorationActivity.class);
			startActivity(intent);
			break;

		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void getTrackIndex(int trackIndex) {
		// TODO Auto-generated method stub
		this.trackIndex = trackIndex;
	}

	@Override
	public void addTrackToPlaylist(Track track, ArrayList<Playlist> playlist) {
		// TODO Auto-generated method stub
		TrackDal trackDal = new TrackDal(this);
		trackDal.getConnect();
		for (int i = 0; i < playlist.size(); i++) {
			trackDal.insertTrack(track, playlist.get(i).getId());
		}
		trackDal.close();
	}
}
