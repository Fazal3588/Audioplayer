package com.bk.lrandom.audioplayer.adapter;

import java.util.ArrayList;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.View;
import android.view.ViewGroup;

public class TabsPagerAdapter extends FragmentStatePagerAdapter {
	private ArrayList<Fragment> fragments;
	private ArrayList<String> titles;
	private FragmentManager fm;

	public TabsPagerAdapter(FragmentManager fm, ArrayList<Fragment> fragments,
			ArrayList<String> titles) {
		super(fm);
		// TODO Auto-generated constructor stub
		this.fragments = fragments;
		this.titles = titles;
		this.fm = fm;
	}

	@Override
	public Fragment getItem(int position) {
		// TODO Auto-generated method stub
		return this.fragments.get(position);
	}

	@Override
	public int getItemPosition(Object object) {
		// TODO Auto-generated method stub
		return POSITION_NONE;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return this.fragments.size();
	}

	@Override
	public CharSequence getPageTitle(int position) {
		// TODO Auto-generated method stub
		return this.titles.get(position);
	}

	@Override
	public void setPrimaryItem(ViewGroup container, int position, Object object) {
		super.setPrimaryItem(container, 0, object);
	}

	@Override
	public void notifyDataSetChanged() {
		// TODO Auto-generated method stub
		super.notifyDataSetChanged();
	}

	@Override
	public void destroyItem(View container, int position, Object object) {
		// TODO Auto-generated method stub
		fm.executePendingTransactions();
		fm.saveFragmentInstanceState(fragments.get(position));
	}

	public void replaceItem(int position, Fragment fragment) {
		fragments.set(position, fragment);
		this.notifyDataSetChanged();
	}
}
