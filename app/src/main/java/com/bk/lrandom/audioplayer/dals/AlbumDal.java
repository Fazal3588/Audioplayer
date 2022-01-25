package com.bk.lrandom.audioplayer.dals;

import java.util.ArrayList;

import com.bk.lrandom.audioplayer.conf.constants;
import com.bk.lrandom.audioplayer.models.Album;
import com.bk.lrandom.audioplayer.models.Artist;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

public class AlbumDal {
	DB dbOpen;
	SQLiteDatabase dbHandler;
	Context context;
	ContentResolver contentResolver;

	public AlbumDal() {

	}

	public void getConnect(Context context) {
		dbOpen = new DB(context);
		dbHandler = dbOpen.getWritableDatabase();
	}

	public AlbumDal(Context context) {
		super();
		this.context = context;
		this.contentResolver = context.getContentResolver();
	}

	public ArrayList<Album> getAlbum() {
		dbHandler = dbOpen.getWritableDatabase();
		String query = "SELECT * FROM " + constants.TBL_PLAYLISTS;
		Cursor cursor = dbHandler.rawQuery(query, null);
		return convertOwnDBCursorToArrayList(cursor);
	}

	public ArrayList<Album> getAlbumOnMDS() {
		Uri uri = MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI;
		String[] projection = new String[] { MediaStore.Audio.Albums._ID,
				MediaStore.Audio.Albums.ALBUM, MediaStore.Audio.Albums.ARTIST,
				MediaStore.Audio.Albums.ALBUM_ART };
		Cursor cursor = contentResolver
				.query(uri, projection, null, null, null);
		return convertMediaStoreCursorToArrayList(cursor);
	}

	public ArrayList<Album> getAlbumsByIdOnMDS(int id) {
		Uri uri = MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI;
		String[] projection = new String[] { MediaStore.Audio.Albums._ID,
				MediaStore.Audio.Albums.ALBUM, MediaStore.Audio.Albums.ARTIST,
				MediaStore.Audio.Albums.ALBUM_ART };
		Cursor cursor = contentResolver.query(uri, projection,
				MediaStore.Audio.Albums._ID + "=" + id, null, null);
		return convertMediaStoreCursorToArrayList(cursor);
	}

	public ArrayList<Album> getAlbumsByTitleOnMDS(String title) {
		Uri uri = MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI;
		String[] projection = new String[] { MediaStore.Audio.Albums._ID,
				MediaStore.Audio.Albums.ALBUM, MediaStore.Audio.Albums.ARTIST,
				MediaStore.Audio.Albums.ALBUM_ART };
		Cursor cursor = contentResolver.query(uri, projection,
				MediaStore.Audio.Albums.ALBUM + " LIKE ?", new String[] { "%"
						+ title + "%" }, null);
		return convertMediaStoreCursorToArrayList(cursor);
	}

	private ArrayList<Album> convertMediaStoreCursorToArrayList(Cursor cursor) {
		ArrayList<Album> albumList = new ArrayList<Album>();
		if (cursor != null && cursor.moveToFirst()) {
			do {
				Album album = new Album();
				album.setId(cursor.getInt(cursor
						.getColumnIndex(MediaStore.Audio.Albums._ID)));
				album.setTitle(cursor.getString(cursor
						.getColumnIndex(MediaStore.Audio.Albums.ALBUM)));
				album.setArtist(cursor.getString(cursor
						.getColumnIndex(MediaStore.Audio.Albums.ARTIST)));
				album.setAlbumArt(cursor.getString(cursor
						.getColumnIndex(MediaStore.Audio.Albums.ALBUM_ART)));
				albumList.add(album);
				Log.i("ALBUM", album.getId() + "");
			} while (cursor.moveToNext());
			cursor.close();
		}
		return albumList;
	}

	private ArrayList<Album> convertOwnDBCursorToArrayList(Cursor cursor) {
		ArrayList<Album> albumList = new ArrayList<Album>();
		if (cursor != null && cursor.moveToFirst()) {
			do {
				Album album = new Album();
				album.setId(cursor.getInt(cursor
						.getColumnIndex(constants.COL_ID)));
				album.setTitle(cursor.getString(cursor
						.getColumnIndex(constants.COL_NAME)));
			} while (cursor.moveToNext());
			cursor.close();
		}
		return albumList;
	}

	public void close() {
		if (dbHandler != null) {
			dbHandler.close();
		}
	}
}
