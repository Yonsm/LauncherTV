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

import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.text.TextUtils;


public class AppInfo {
	private final Drawable mIcon;
	private String mName;
	private final String mPackageName;
	private final String mActivityName;

	AppInfo(PackageManager packageManager, ResolveInfo resolveInfo, String packageName, String activityName) {
		mPackageName = packageName;
		mActivityName = activityName;
		mIcon = resolveInfo.loadIcon(packageManager);

		try {
			mName = resolveInfo.loadLabel(packageManager).toString();
		} catch (Exception e) {
			mName = mPackageName;
		}
	}

	public AppInfo(PackageManager packageManager, ApplicationInfo applicationInfo, String activityName) {
		mPackageName = applicationInfo.packageName;
		mActivityName = activityName;

		ResolveInfo resolveInfo;
		if (TextUtils.isEmpty(activityName)) {
			resolveInfo = null;
		}
		else {
			Intent intent = new Intent();
			intent.setComponent(new ComponentName(mPackageName, mActivityName));
			resolveInfo = packageManager.resolveActivity(intent, 0);
		}
		mIcon = resolveInfo != null ? resolveInfo.loadIcon(packageManager) : applicationInfo.loadIcon(packageManager);

		try {
			if (resolveInfo == null) {
				mName = applicationInfo.loadLabel(packageManager).toString();
			} else {
				mName = resolveInfo.loadLabel(packageManager).toString();
			}
		} catch (Exception e) {
			mName = mPackageName;
		}
	}


	@NonNull
	public String getName() {
		if (mActivityName.equals("com.alibaba.ailabs.genie.launcher/.appstore.AppStoreActivity"))
			return "全部应用";
		if (mActivityName.equals("com.alibaba.ailabs.genie.launcher/.channel.NormalChannelActivity"))
			return "视频";
		if (mName.equals("GenieLauncher"))
			return "天猫精灵";
		if (mName.equals("GenieContacts"))
			return "通话";
		if (mName != null)
			return mName;
		return ("");
	}

	public Drawable getIcon() {
		return mIcon;
	}

	public String getPackageName() {
		return mPackageName;
	}

	public String getmActivityName() {
		return mActivityName;
	}
}
