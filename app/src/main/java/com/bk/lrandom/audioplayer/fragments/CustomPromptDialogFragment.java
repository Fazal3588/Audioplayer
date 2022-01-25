package com.bk.lrandom.audioplayer.fragments;

import com.bk.lrandom.audioplayer.R;
import com.bk.lrandom.audioplayer.conf.constants;
import com.bk.lrandom.audioplayer.interfaces.CustomPromptDialogFragmentComunicator;
import com.bk.lrandom.audioplayer.models.Playlist;

import android.app.Activity;
import android.app.AlertDialog;
import android.support.v4.app.DialogFragment;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class CustomPromptDialogFragment extends DialogFragment {
	CustomPromptDialogFragmentComunicator listener;
	EditText inputText = null;
	Playlist playlist;

	public static CustomPromptDialogFragment newInstance() {
		CustomPromptDialogFragment frag = new CustomPromptDialogFragment();
		return frag;
	}

	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		listener = (CustomPromptDialogFragmentComunicator) activity;
		if (getArguments() != null
				&& getArguments().containsKey(constants.COMMON_KEY)) {
			playlist = getArguments().getParcelable(constants.COMMON_KEY);
		}
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		// Set an EditText view to get user input
		inputText = new EditText(getActivity());
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		if (playlist != null) {
			inputText.setText(playlist.getName());
		}
		builder.setView(inputText)
				.setTitle(
						getActivity().getResources().getString(
								R.string.name_label))
				.setPositiveButton(R.string.ok_label, null)
				.setNegativeButton(R.string.cancel_label,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int whichButton) {
							}
						});
		AlertDialog promptDialog = builder.create();
		promptDialog.show();
		promptDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(
				new View.OnClickListener() {
					public void onClick(View v) {
						// TODO Auto-generated method stub
						if (inputText.getText().length() != 0) {
							if (playlist == null) {
								listener.getInputTextValue(inputText.getText()
										.toString());
							} else {
								listener.renamePlaylist(inputText.getText()
										.toString(), playlist);
							}
							dismiss();
						}
					}
				});
		return promptDialog;
	}
}
