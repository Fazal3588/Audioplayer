package com.bk.lrandom.audioplayer.dals;

import java.util.ArrayList;

import com.bk.lrandom.audioplayer.conf.constants;
import com.bk.lrandom.audioplayer.models.Playlist;
import com.bk.lrandom.audioplayer.models.Track;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class PlaylistDal {
	DB dbOpen;
	SQLiteDatabase dbHandler;

	public PlaylistDal() {

	}

	public PlaylistDal(Context context) {
		// TODO Auto-generated constructor stub
		super();
		dbOpen = new DB(context);
		dbHandler = dbOpen.getWritableDatabase();
	}

	public int addPlayList(String name) {
		if (name == null || name == "") {
			return 0;
		}
		ContentValues values = new ContentValues();
		values.put(constants.COL_NAME, name); // Contact Name
		// Inserting Row
		dbHandler.insert(constants.TBL_PLAYLISTS, null, values);

		String query = "SELECT " + constants.COL_ID + " FROM "
				+ constants.TBL_PLAYLISTS + " ORDER BY " + constants.COL_ID
				+ " DESC LIMIT 0,1";
		Cursor cursor = dbHandler.rawQuery(query, null);
		if (cursor.moveToFirst()) {
			return cursor.getInt(cursor.getColumnIndex(constants.COL_ID));
		} else {
			return 0;
		}
	}

	public ArrayList<Playlist> getAllPlayList() {
		String query = "SELECT * FROM " + constants.TBL_PLAYLISTS;
		Cursor cursor = dbHandler.rawQuery(query, null);
		return convertToArrayList(cursor);
	}

	public ArrayList<Playlist> getPlaylistByTitle(String title) {
		String query = "SELECT * FROM " + constants.TBL_PLAYLISTS + " WHERE "
				+ constants.COL_NAME + " LIKE ?";
		Cursor cursor = dbHandler.rawQuery(query, new String[] { "%" + title
				+ "%" });
		return convertToArrayList(cursor);
	}

	public int removePlayList(int playlistID) {
		dbHandler.delete(constants.TBL_TRACKS, constants.COL_REF_PLAYLIST_ID
				+ "=" + playlistID, null);
		return dbHandler.delete(constants.TBL_PLAYLISTS, constants.COL_ID + "="
				+ playlistID, null);
	};

	public boolean checkExistNamePlaylist(String name) {
		String query = "SELECT * FROM " + constants.TBL_PLAYLISTS + " WHERE "
				+ constants.COL_NAME + "='" + name + "'";
		Cursor cursor = dbHandler.rawQuery(query, null);
		if (cursor.moveToFirst()) {
			return true;
		} else {
			return false;
		}
	}

	public void renamePlayList(String name, int playlistID) {
		if (name != null && name != "" && playlistID != 0) {
			ContentValues values = new ContentValues();
			values.put(constants.COL_NAME, name);
			dbHandler.update(constants.TBL_PLAYLISTS, values, constants.COL_ID
					+ "=" + playlistID, null);
		}
	}

	private ArrayList<Playlist> convertToArrayList(Cursor cursor) {
		ArrayList<Playlist> playlistsList = new ArrayList<Playlist>();
		if (cursor != null && cursor.moveToFirst()) {
			do {
				Playlist playlist = new Playlist();
				playlist.setId(cursor.getInt(cursor
						.getColumnIndex(constants.COL_ID)));
				playlist.setName(cursor.getString(cursor
						.getColumnIndex(constants.COL_NAME)));
				playlistsList.add(playlist);
			} while (cursor.moveToNext());
			cursor.close();
		}
		return playlistsList;
	}

	public void close() {
		if (dbHandler != null) {
			dbHandler.close();
		}
	}
}
