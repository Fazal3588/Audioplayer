package com.bk.lrandom.audioplayer.models;

public class MenuItem {
	private String name;
	private int drawableResources;
	private int uid;

	public MenuItem(String name, int drawableResources, int uid) {
		super();
		this.name = name;
		this.drawableResources = drawableResources;
		this.uid = uid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getDrawableResources() {
		return drawableResources;
	}

	public void setDrawableResources(int drawableResources) {
		this.drawableResources = drawableResources;
	}

	public int getUid() {
		return uid;
	}

	public void setUid(int uid) {
		this.uid = uid;
	}

}
