package com.bk.lrandom.audioplayer.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Track implements Parcelable {
	private int id;
	private String title;
	private String artist;
	private String path;
	private String size;
	private String duration;
	private String extension;
	private int albumId;
	private int rawId;

	public int getRawId() {
		return rawId;
	}

	public void setRawId(int rawId) {
		this.rawId = rawId;
	}

	private Boolean selected = false;
	private Boolean checked = false;

	public Boolean getChecked() {
		return checked;
	}

	public void setChecked(Boolean checked) {
		this.checked = checked;
	}

	public Boolean getSelected() {
		return selected;
	}

	public void setSelected(Boolean selected) {
		this.selected = selected;
	}

	public Track() {

	}

	public Track(int id, String title, String artist, String path, String size,
			String duration, String extension, int albumId) {
		super();
		this.id = id;
		this.title = title;
		this.artist = artist;
		this.path = path;
		this.size = size;
		this.duration = duration;
		this.extension = extension;
		this.albumId = albumId;
	}

	public Track(Parcel in) {
		id = in.readInt();
		title = in.readString();
		artist = in.readString();
		path = in.readString();
		size = in.readString();
		duration = in.readString();
		extension = in.readString();
		albumId = in.readInt();
		rawId = in.readInt();
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

	public String getArtist() {
		return artist;
	}

	public void setArtist(String artist) {
		this.artist = artist;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	public String getExtension() {
		return extension;
	}

	public void setExtension(String extension) {
		this.extension = extension;
	}

	public int getAlbumId() {
		return albumId;
	}

	public void setAlbumId(int albumId) {
		this.albumId = albumId;
	}

	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub
		dest.writeInt(id);
		dest.writeString(title);
		dest.writeString(artist);
		dest.writeString(path);
		dest.writeString(size);
		dest.writeString(duration);
		dest.writeString(extension);
		dest.writeInt(albumId);
		dest.writeInt(rawId);
	}

	public static final Parcelable.Creator<Track> CREATOR = new Parcelable.Creator<Track>() {

		public Track createFromParcel(Parcel in) {
			// TODO Auto-generated method stub
			return new Track(in);
		}

		public Track[] newArray(int size) {
			// TODO Auto-generated method stub
			return new Track[size];
		}

	};
}
