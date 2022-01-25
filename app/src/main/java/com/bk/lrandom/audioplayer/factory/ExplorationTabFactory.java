package com.bk.lrandom.audioplayer.factory;

import android.content.Context;
import android.view.View;
import android.widget.TabHost.TabContentFactory;

public class ExplorationTabFactory implements TabContentFactory {

	private final Context context;

	public ExplorationTabFactory(Context context) {
		this.context = context;
	}

	public View createTabContent(String tag) {
		// TODO Auto-generated method stub
		View v = new View(context);
		v.setMinimumHeight(0);
		v.setMinimumWidth(0);
		return v;
	}
}
