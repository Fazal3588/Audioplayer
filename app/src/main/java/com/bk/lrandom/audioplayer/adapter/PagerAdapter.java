package com.bk.lrandom.audioplayer.adapter;

import java.util.ArrayList;
import java.util.List;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.view.ViewGroup;

public class PagerAdapter extends android.support.v4.app.FragmentPagerAdapter {
	// SparseArray<Fragment> registeredFragments = new SparseArray<Fragment>();
	private ArrayList<Fragment> fragments;

	public PagerAdapter(FragmentManager fm, ArrayList<Fragment> fragments) {
		super(fm);
		// TODO Auto-generated constructor stub
		this.fragments = fragments;
	}

	@Override
	public Fragment getItem(int position) {
		// TODO Auto-generated method stub
		return this.fragments.get(position);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return this.fragments.size();
	}
}
