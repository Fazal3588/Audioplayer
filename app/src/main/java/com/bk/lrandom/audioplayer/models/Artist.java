package com.bk.lrandom.audioplayer.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Artist implements Parcelable {

	private int id;
	private String artist;

	public Artist() {

	}

	public Artist(int id, String artist) {
		super();
		this.id = id;
		this.artist = artist;
	}

	public Artist(Parcel in) {
		id = in.readInt();
		artist = in.readString();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getArtist() {
		return artist;
	}

	public void setArtist(String artist) {
		this.artist = artist;
	}

	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub
		dest.writeInt(id);
		dest.writeString(artist);
	}

	public static final Parcelable.Creator<Artist> CREATOR = new Parcelable.Creator<Artist>() {

		public Artist createFromParcel(Parcel in) {
			// TODO Auto-generated method stub
			return new Artist(in);
		}

		public Artist[] newArray(int size) {
			// TODO Auto-generated method stub
			return new Artist[size];
		}
    };
}
