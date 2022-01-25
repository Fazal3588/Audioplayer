package com.bk.lrandom.audioplayer.dals;

import com.bk.lrandom.audioplayer.conf.constants;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DB extends SQLiteOpenHelper {
	Context context;
    
	public DB(Context context) {
		super(context, constants.DB_NAME, null, constants.DB_VERSION);
		this.context = context;
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		String query = "CREATE TABLE " + constants.TBL_PLAYLISTS + "("
				+ constants.COL_ID + " INTEGER PRIMARY KEY,"
				+ constants.COL_NAME + " TEXT)";
		db.execSQL(query);

		query = "CREATE TABLE " + constants.TBL_TRACKS + "(" + constants.COL_ID
				+ " INTEGER PRIMARY KEY," + constants.COL_ARTIST + " TEXT,"
				+ constants.COL_NAME + " TEXT, " + constants.COL_PATH
				+ " TEXT, " + constants.COL_TRACK_ID + " INTEGER, "
				+ constants.COL_REF_PLAYLIST_ID + " INTEGER, "
				+ constants.COL_ALBUM_ID + " INTEGER)";
		db.execSQL(query);
		
		query = "CREATE TABLE " + constants.TBL_TRACKS + "(" + constants.COL_ID
				+ " INTEGER PRIMARY KEY," + constants.COL_ARTIST + " TEXT,"
				+ constants.COL_NAME + " TEXT, " + constants.COL_PATH
				+ " TEXT, " + constants.COL_TRACK_ID + " INTEGER, "
				+ constants.COL_REF_PLAYLIST_ID + " INTEGER, "
				+ constants.COL_ALBUM_ID + " INTEGER)";
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		db.execSQL("DROP TABLE IF EXISTS " + constants.TBL_PLAYLISTS);
		db.execSQL("DROP TABLE IF EXISTS " + constants.TBL_TRACKS);
		onCreate(db);
	}

	public void dropDatabase() {
		context.deleteDatabase(constants.DB_NAME);
	}
}
