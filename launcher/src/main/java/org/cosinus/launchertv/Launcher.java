/*
 * Simple TV Launcher
 * Copyright 2017 Alexandre Del Bigio
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.cosinus.launchertv;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;

import org.cosinus.launchertv.fragments.ApplicationFragment;

public class Launcher extends FragmentActivity {

	private ApplicationFragment mFragment;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setFullScreen();
		setContentView(R.layout.activity_launcher);

		mFragment = ApplicationFragment.newInstance();
		getSupportFragmentManager().beginTransaction()
				.replace(R.id.container, mFragment, ApplicationFragment.TAG)
				.commit();
	}

	@Override
	protected void onResume() {
		super.onResume();
		setFullScreen();
	}

	private void setFullScreen() {
		try {
			if (Build.VERSION.SDK_INT < 19) {
				getWindow().setFlags(
						WindowManager.LayoutParams.FLAG_FULLSCREEN | WindowManager.LayoutParams.FLAG_SHOW_WALLPAPER,
						WindowManager.LayoutParams.FLAG_FULLSCREEN | WindowManager.LayoutParams.FLAG_SHOW_WALLPAPER
				);
			} else {
				View decorView = getWindow().getDecorView();

				int uiOptions = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
						| View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
						| View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
						| View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
						| View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
						| View.SYSTEM_UI_FLAG_IMMERSIVE;

				decorView.setSystemUiVisibility(uiOptions);

				getWindow().setFlags(
						WindowManager.LayoutParams.FLAG_SHOW_WALLPAPER,
						WindowManager.LayoutParams.FLAG_SHOW_WALLPAPER
				);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			mFragment.openApplicationGrid();
			return true;
		}
		return false;
	}
}
