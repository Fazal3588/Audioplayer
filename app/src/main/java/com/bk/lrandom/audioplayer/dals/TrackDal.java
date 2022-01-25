package com.bk.lrandom.audioplayer.dals;

import java.util.ArrayList;

import com.bk.lrandom.audioplayer.conf.constants;
import com.bk.lrandom.audioplayer.models.Track;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

public class TrackDal {
	DB dbOpen;
	SQLiteDatabase dbHandler;
	Context context;
	ContentResolver contentResolver;

	public TrackDal() {

	}

	public TrackDal(Context context) {
		super();
		this.context = context;
		this.contentResolver = context.getContentResolver();
	}

	public void getConnect() {
		dbOpen = new DB(context);
		dbHandler = dbOpen.getWritableDatabase();
	}

	// operation on own database
	public void insertTrack(Track track, int playlistID) {
		ContentValues values = new ContentValues();
		values.put(constants.COL_ARTIST, track.getArtist());
		values.put(constants.COL_NAME, track.getTitle());
		values.put(constants.COL_PATH, track.getPath());
		values.put(constants.COL_REF_PLAYLIST_ID, playlistID);
		values.put(constants.COL_ALBUM_ID, track.getAlbumId());
		values.put(constants.COL_TRACK_ID, track.getId());
		dbHandler.insert(constants.TBL_TRACKS, null, values);
	}

	public ArrayList<Track> getTracks() {
		String query = "SELECT * FROM " + constants.TBL_TRACKS + " ORDER BY "
				+ constants.COL_ID + " ASC";
		Cursor cursor = dbHandler.rawQuery(query, null);
		return convertOwnDBCursorToArrayList(cursor);
	}

	public ArrayList<Track> getTracksByID(int id) {
		String query = "SELECT * FROM " + constants.TBL_TRACKS + " WHERE "
				+ constants.COL_ID + "=" + id + " ORDER BY " + constants.COL_ID
				+ " DESC";
		Cursor cursor = dbHandler.rawQuery(query, null);
		return convertOwnDBCursorToArrayList(cursor);
	}

	public ArrayList<Track> getTracksByPlaylistID(int playlistID) {
		String query = "SELECT * FROM " + constants.TBL_TRACKS + " WHERE "
				+ constants.COL_REF_PLAYLIST_ID + "=" + playlistID
				+ " ORDER BY " + constants.COL_ID + " DESC";
		Cursor cursor = dbHandler.rawQuery(query, null);
		return convertOwnDBCursorToArrayList(cursor);
	}

	public boolean removeTracksByID(int id) {
		int deleted = dbHandler.delete(constants.TBL_TRACKS, constants.COL_TRACK_ID
				+ "=" + id, null);
		if (deleted > 0) {
			return true;
		} else {
			return false;
		}
	}

	public boolean removeTracksByPlaylistID(int playlistID) {
		int deleted = dbHandler.delete(constants.TBL_TRACKS,
				constants.COL_REF_PLAYLIST_ID + "=" + playlistID, null);
		if (deleted > 0) {
			return true;
		} else {
			return false;
		}
	}

	// end

	// operation on MEDIASTORE DB
	public ArrayList<Track> getTracksOnMDS() {
		Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
		String[] projection = new String[] { MediaStore.Audio.Media._ID,
				MediaStore.Audio.Media.TITLE, MediaStore.Audio.Media.ARTIST,
				MediaStore.Audio.Media.ALBUM_ID, MediaStore.Audio.Media.DATA };
		Cursor cursor = contentResolver
				.query(uri, projection, null, null, null);
		return convertMediaStoreCursorToArrayList(cursor);
	}

	public ArrayList<Track> getTracksByTitleOnMDS(String title) {
		Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
		String[] projection = new String[] { MediaStore.Audio.Media._ID,
				MediaStore.Audio.Media.TITLE, MediaStore.Audio.Media.ARTIST,
				MediaStore.Audio.Media.ALBUM_ID, MediaStore.Audio.Media.DATA };
		Cursor cursor = contentResolver.query(uri, projection,
				MediaStore.Audio.Media.TITLE + " LIKE ?", new String[] { "%"
						+ title + "%" }, null);
		return convertMediaStoreCursorToArrayList(cursor);
	}

	public ArrayList<Track> getTracksByIdOnMDS(int id) {
		Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
		String[] projection = new String[] { MediaStore.Audio.Media._ID,
				MediaStore.Audio.Media.TITLE, MediaStore.Audio.Media.ARTIST,
				MediaStore.Audio.Media.ALBUM_ID, MediaStore.Audio.Media.DATA };
		Cursor cursor = contentResolver.query(uri, projection,
				MediaStore.Audio.Media._ID + "=" + id, null, null);
		return convertMediaStoreCursorToArrayList(cursor);
	}

	public ArrayList<Track> getTracksByAlbumIdOnMDS(int id) {
		Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
		String[] projection = new String[] { MediaStore.Audio.Media._ID,
				MediaStore.Audio.Media.TITLE, MediaStore.Audio.Media.ARTIST,
				MediaStore.Audio.Media.ALBUM_ID, MediaStore.Audio.Media.DATA };
		Cursor cursor = contentResolver.query(uri, projection,
				MediaStore.Audio.Media.ALBUM_ID + "=" + id, null, null);
		return convertMediaStoreCursorToArrayList(cursor);
	}

	public ArrayList<Track> getTracksByAlbumOnMDS(String name) {
		Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
		String[] projection = new String[] { MediaStore.Audio.Media._ID,
				MediaStore.Audio.Media.TITLE, MediaStore.Audio.Media.ARTIST,
				MediaStore.Audio.Media.ALBUM_ID, MediaStore.Audio.Media.DATA };
		Cursor cursor = contentResolver.query(uri, projection,
				MediaStore.Audio.Media.ALBUM + "=" + name, null, null);
		return convertMediaStoreCursorToArrayList(cursor);
	}

	public ArrayList<Track> getTracksByArtistIdOnMDS(int id) {
		Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
		String[] projection = new String[] { MediaStore.Audio.Media._ID,
				MediaStore.Audio.Media.TITLE, MediaStore.Audio.Media.ARTIST,
				MediaStore.Audio.Media.ALBUM_ID, MediaStore.Audio.Media.DATA };
		Cursor cursor = contentResolver.query(uri, projection,
				MediaStore.Audio.Media.ARTIST_ID + "=" + id, null, null);
		return convertMediaStoreCursorToArrayList(cursor);
	}

	public ArrayList<Track> getTracksByArtistOnMDS(String artistName) {
		Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
		String[] projection = new String[] { MediaStore.Audio.Media._ID,
				MediaStore.Audio.Media.TITLE, MediaStore.Audio.Media.ARTIST,
				MediaStore.Audio.Media.ALBUM_ID, MediaStore.Audio.Media.DATA };
		Cursor cursor = contentResolver.query(uri, projection,
				MediaStore.Audio.Media.ARTIST + "=" + artistName, null, null);
		return convertMediaStoreCursorToArrayList(cursor);
	}

	public Uri insertTracksOnMDS(Uri uri, ContentValues values) {
		return contentResolver.insert(uri, values);
	}

	public int removeTracksOnMDS(Uri uri, String where) {
		return contentResolver.delete(uri, where, null);
	}

	// end
	private ArrayList<Track> convertMediaStoreCursorToArrayList(Cursor cursor) {
		ArrayList<Track> trackList = new ArrayList<Track>();
		if (cursor != null && cursor.moveToFirst()) {
			do {
				Track track = new Track();
				track.setId(cursor.getInt(cursor
						.getColumnIndex(MediaStore.Audio.Media._ID)));
				track.setArtist(cursor.getString(cursor
						.getColumnIndex(MediaStore.Audio.Media.ARTIST)));
				track.setTitle(cursor.getString(cursor
						.getColumnIndex(MediaStore.Audio.Media.TITLE)));
				track.setPath(cursor.getString(cursor
						.getColumnIndex(MediaStore.Audio.Media.DATA)));
				track.setAlbumId(Integer.parseInt(cursor.getString(cursor
						.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID))));

				trackList.add(track);
			} while (cursor.moveToNext());
			cursor.close();
		}
		return trackList;
	}

	private ArrayList<Track> convertOwnDBCursorToArrayList(Cursor cursor) {
		ArrayList<Track> trackList = new ArrayList<Track>();
		if (cursor.moveToFirst() && cursor != null) {
			do {
				Track track = new Track();
				track.setId(cursor.getInt(cursor
						.getColumnIndex(constants.COL_ID)));
				track.setArtist(cursor.getString(cursor
						.getColumnIndex(constants.COL_ARTIST)));
				track.setTitle(cursor.getString(cursor
						.getColumnIndex(constants.COL_NAME)));
				track.setPath(cursor.getString(cursor
						.getColumnIndex(constants.COL_PATH)));
				track.setAlbumId(cursor.getInt(cursor
						.getColumnIndex(constants.COL_ALBUM_ID)));
				trackList.add(track);
			} while (cursor.moveToNext());
			cursor.close();
		}
		return trackList;
	}

	public void close() {
		if (dbHandler != null) {
			dbHandler.close();
		}
	}
}
