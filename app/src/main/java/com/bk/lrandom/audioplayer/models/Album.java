package com.bk.lrandom.audioplayer.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Album implements Parcelable{
	private int id;
	private String title;
	private String artist;
	private int numberOfSongs;
	private String albumArt;

	public Album() {

	}

	public Album(Parcel in){
		super();
		id=in.readInt();
		title=in.readString();
		artist=in.readString();
		numberOfSongs=in.readInt();
		albumArt=in.readString();
	}
	
	public Album(int id, String title, String artist, int numberOfSongs,
			String albumArt) {
		super();
		this.id = id;
		this.title = title;
		this.artist = artist;
		this.numberOfSongs = numberOfSongs;
		this.albumArt = albumArt;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setArtist(String artist) {
		this.artist = artist;
	}

	public String getArtist() {
		return artist;
	}

	public int getNumberOfSongs() {
		return numberOfSongs;
	}

	public void setNumberOfSongs(int numberOfSongs) {
		this.numberOfSongs = numberOfSongs;
	}

	public String getAlbumArt() {
		return albumArt;
	}

	public void setAlbumArt(String albumArt) {
		this.albumArt = albumArt;
	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub
		dest.writeInt(id);
		dest.writeString(title);
		dest.writeString(artist);
		dest.writeInt(numberOfSongs);
		dest.writeString(albumArt);
	}

	public static final Parcelable.Creator<Album> CREATOR = new Parcelable.Creator<Album>() {

		public Album createFromParcel(Parcel in) {
			// TODO Auto-generated method stub
			return new Album(in);
		}

		public Album[] newArray(int size) {
			// TODO Auto-generated method stub
			return new Album[size];
		}

	};
	
}
