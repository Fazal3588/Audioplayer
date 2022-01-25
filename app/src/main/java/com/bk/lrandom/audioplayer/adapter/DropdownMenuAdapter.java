package com.bk.lrandom.audioplayer.adapter;


import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class DropdownMenuAdapter extends ArrayAdapter<String> {
	private Context context;

	public DropdownMenuAdapter(Context context, int resource, String[] objects) {
		super(context, resource, objects);
		// TODO Auto-generated constructor stub
		this.context = context;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		View view = super.getView(position, convertView, parent);
		TextView text = (TextView) view.findViewById(android.R.id.text1);
		text.setText("");
		return view;
	}

	@Override
	public View getDropDownView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		View view = null;

		// If this is the initial dummy entry, make it hidden
		if (position == 0) {
			TextView text = new TextView(getContext());
			text.setHeight(0);
			text.setVisibility(View.GONE);
			view = text;
		} else {
			view = super.getDropDownView(position, null, parent);
		}
		// hide scrollbar
		parent.setVerticalScrollBarEnabled(false);
		return view;
	}
}
