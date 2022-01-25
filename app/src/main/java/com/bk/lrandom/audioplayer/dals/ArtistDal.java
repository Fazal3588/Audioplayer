package com.bk.lrandom.audioplayer.dals;

import java.util.ArrayList;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.provider.MediaStore;

import com.bk.lrandom.audioplayer.conf.constants;
import com.bk.lrandom.audioplayer.models.Artist;
public class ArtistDal {
	DB dbOpen;
	SQLiteDatabase dbHandler;
	Context context;
	ContentResolver contentResolver;
	static Cursor cursor;

	public ArtistDal(Context context) {
		// TODO Auto-generated constructor stub
		super();
		this.context = context;
		contentResolver = context.getContentResolver();
	}

	public void getConnect(Context context) {
		dbOpen = new DB(context);
		dbHandler = dbOpen.getWritableDatabase();
	}

	public ArrayList<Artist> getArtists() {
		String query = "SELECT * FROM " + constants.TBL_PLAYLISTS;
		cursor = dbHandler.rawQuery(query, null);
		return convertOwnDBCursorToArrayList(cursor);
	}

	public ArrayList<Artist> getArtistsOnMDS() {
		Uri uri = MediaStore.Audio.Artists.EXTERNAL_CONTENT_URI;
		String[] projection = new String[] { MediaStore.Audio.Artists._ID,
				MediaStore.Audio.Artists.ARTIST };
		cursor = contentResolver.query(uri, projection, null, null, null);
		return convertMediaStoreCursorToArrayList(cursor);
	}

	public ArrayList<Artist> getArtistsByNameOnMDS(String name) {
		Uri uri = MediaStore.Audio.Artists.EXTERNAL_CONTENT_URI;
		String[] projection = new String[] { MediaStore.Audio.Artists._ID,
				MediaStore.Audio.Artists.ARTIST };
		Cursor cursor = contentResolver.query(uri, projection,
				MediaStore.Audio.Artists.ARTIST + " LIKE ?", new String[] { "%"
						+ name + "%" }, null);
		return convertMediaStoreCursorToArrayList(cursor);
	}

	private ArrayList<Artist> convertMediaStoreCursorToArrayList(Cursor cursor) {
		ArrayList<Artist> artistList = new ArrayList<Artist>();
		if (cursor != null && cursor.moveToFirst()) {
			do {
				Artist artist = new Artist();
				artist.setId(cursor.getInt(cursor
						.getColumnIndex(MediaStore.Audio.Artists._ID)));
				artist.setArtist(cursor.getString(cursor
						.getColumnIndex(MediaStore.Audio.Artists.ARTIST)));
				artistList.add(artist);
			} while (cursor.moveToNext());
			cursor.close();
		}
		return artistList;
	}

	private ArrayList<Artist> convertOwnDBCursorToArrayList(Cursor cursor) {
		ArrayList<Artist> artistList = new ArrayList<Artist>();
		if (cursor != null && cursor.moveToFirst()) {
			do {
				Artist artist = new Artist();
				artist.setId(cursor.getInt(cursor
						.getColumnIndex(constants.COL_ID)));
				artist.setArtist(cursor.getString(cursor
						.getColumnIndex(constants.COL_NAME)));
				artistList.add(artist);
			} while (cursor.moveToNext());
			cursor.close();
		}
		return artistList;
	}

	public void close() {
		if (dbHandler != null) {
			dbHandler.close();
		}
	}
}
