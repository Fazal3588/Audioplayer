package com.bk.lrandom.audioplayer.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Playlist implements Parcelable {
	private int id;
	private String name;
	private Boolean checked = false;

	public Boolean getChecked() {
		return checked;
	}

	public void setChecked(Boolean checked) {
		this.checked = checked;
	}

	public Playlist() {

	}

	public Playlist(int id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	public Playlist(Parcel in) {
		id = in.readInt();
		name = in.readString();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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
		dest.writeString(name);
	}

	public static final Parcelable.Creator<Playlist> CREATOR = new Parcelable.Creator<Playlist>() {

		public Playlist createFromParcel(Parcel in) {
			// TODO Auto-generated method stub
			return new Playlist(in);
		}

		public Playlist[] newArray(int size) {
			// TODO Auto-generated method stub
			return new Playlist[size];
		}

	};

}
