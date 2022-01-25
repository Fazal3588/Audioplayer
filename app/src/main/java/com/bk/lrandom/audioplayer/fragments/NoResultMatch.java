package com.bk.lrandom.audioplayer.fragments;

import com.bk.lrandom.audioplayer.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class NoResultMatch extends Fragment {
	
	public static final NoResultMatch newInstance() {
	    NoResultMatch fragment = new NoResultMatch();
		return fragment;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.no_result_match_fragment, container,
				false);
		return view;
	}
}
