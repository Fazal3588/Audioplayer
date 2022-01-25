package com.bk.lrandom.audioplayer.interfaces;

import com.bk.lrandom.audioplayer.models.Playlist;

public interface CustomPromptDialogFragmentComunicator{
  void getInputTextValue(String inputText);
  void renamePlaylist(String name,Playlist playlist);
}
