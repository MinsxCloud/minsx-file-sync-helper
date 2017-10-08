package com.minsx.window;

import java.util.ArrayList;
import java.util.List;

import com.minsx.config.PathConfig;

public class WindowGetter {
	
	public static List<PathConfig> getMainWindowPathListData() {
		List<PathConfig> pathConfigs = new ArrayList<>();
		for (int i = 0; i < MainWindow.table.getItemCount(); i++) {
			pathConfigs.add(new PathConfig(MainWindow.table.getItem(i).getText(1), MainWindow.table.getItem(i).getText(2)));
		}
		return pathConfigs;
	}

}
