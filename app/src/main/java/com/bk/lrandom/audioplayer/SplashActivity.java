package com.bk.lrandom.audioplayer;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

import com.nabinbhandari.android.permissions.PermissionHandler;
import com.nabinbhandari.android.permissions.Permissions;

import java.util.ArrayList;

public class SplashActivity extends Activity{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash_layout);
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
			Permissions.check(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
							Manifest.permission.WRITE_EXTERNAL_STORAGE},
					"", new Permissions.Options()
							.setSettingsDialogTitle("Warning!").setRationaleDialogTitle("Info"),
					new PermissionHandler() {
						@Override
						public void onGranted() {
							//do your task
							new Handler().postDelayed(new Runnable() {
								@Override
								public void run() {
									Intent startActivityIntent = new Intent(SplashActivity.this, ExplorationActivity.class);
									startActivity(startActivityIntent);
									finish();
								}
							},2000);
						}

						@Override
						public void onDenied(Context context, ArrayList<String> deniedPermissions) {
							super.onDenied(context, deniedPermissions);
							finish();
						}
					});
		}else{
			new Handler().postDelayed(new Runnable() {
				@Override
				public void run() {
					Intent startActivityIntent = new Intent(SplashActivity.this, ExplorationActivity.class);
					startActivity(startActivityIntent);
					finish();
				}
			},2000);
		}
	}}
