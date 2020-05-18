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

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Resources;
import android.util.TypedValue;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Utils {
	public static List<AppInfo> loadApplications(Context context, List<String> excludeApplications) {
		PackageManager packageManager = context.getPackageManager();
		Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
		mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
		List<ResolveInfo> intentActivities = packageManager.queryIntentActivities(mainIntent, 0);
		List<AppInfo> entries = new ArrayList<>();

		if (intentActivities != null) {
			for (ResolveInfo resolveInfo : intentActivities) {
				if (!context.getPackageName().equals(resolveInfo.activityInfo.packageName)) {
					String packageName = resolveInfo.activityInfo.packageName;
					String activityName = resolveInfo.activityInfo.name;
					if (excludeApplications == null || !excludeApplications.contains(packageName + "/" + activityName)) {
						entries.add(new AppInfo(packageManager, resolveInfo, packageName, activityName));
					}
					if (packageName.equals("com.alibaba.ailabs.genie.launcher")) {
						for (int i = 0; i < ALIGENIE_APPS.length; i++) {
							if (excludeApplications == null || !excludeApplications.contains(packageName + "/" + ALIGENIE_APPS[i][0]))
								entries.add(new AppInfo(packageManager, resolveInfo, packageName, ALIGENIE_APPS[i][0]));
						}
					}
				}
			}
		}

		Collections.sort(entries, new Comparator<AppInfo>() {
			@Override
			public int compare(AppInfo lhs, AppInfo rhs) {
				return lhs.getName().compareToIgnoreCase(rhs.getName());
			}
		});
		return entries;
	}

	public static int getPixelFromDp(Context context, int dp) {
		Resources r = context.getResources();
		return ((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
	}

	public static String[][] ALIGENIE_APPS = {
			{"genie://com.alibaba.ailabs.genie.launcher/appstore", "全部应用"},
			{"genie://com.alibaba.ailabs.genie.launcher/channel?menuBusinessType=video&menuBusinessName=%E8%A7%86%E9%A2%91&modeType=0&pkg=com.alibaba.ailabs.genie.launcher","视频"},
			{"genie://com.alibaba.ailabs.genie.launcher/channel?menuBusinessType=music&menuBusinessName=%E9%9F%B3%E4%B9%90&modeType=0&pkg=com.alibaba.ailabs.genie.launcher","音乐"},
			{"genie://com.alibaba.ailabs.genie.launcher/channel?menuBusinessType=audio&menuBusinessName=%E9%9F%B3%E9%A2%91&modeType=0&pkg=com.alibaba.ailabs.genie.launcher","音频"},
			{"genie://com.alibaba.ailabs.genie.launcher/channel?menuBusinessType=shopping&menuBusinessName=%E8%B4%AD%E7%89%A9&modeType=0&pkg=com.alibaba.ailabs.genie.launcher","购物"},
			//{"genie://com.alibaba.ailabs.genie.contacts/page/home?modeType=0&pkg=com.alibaba.ailabs.genie.contacts&missedCalls=0", "通话"},
			{"genie://com.alibaba.ailabs.genie.iot/house?pkg=com.alibaba.ailabs.genie.iot","智能家居"},
			{"genie://com.alibaba.ailabs.ar.fireeye2/fireeye?&pkg=com.alibaba.ailabs.ar.fireeye2","爱绘本"},
			{"genie://com.alibaba.ailabs.genie.albums/albums?&pkg=com.alibaba.ailabs.genie.albums","相册"},
			{"genie://com.alibaba.ailabs.genie.cook/cook?pkg=com.alibaba.ailabs.genie.cook","菜谱"},
	};
}
