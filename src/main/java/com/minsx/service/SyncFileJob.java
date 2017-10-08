package com.minsx.service;

import java.io.File;
import java.util.List;

import org.apache.log4j.Logger;

import com.minsx.config.Configer;
import com.minsx.config.PathConfig;
import com.minsx.schedule.Job;
import com.minsx.window.WindowSetter;

public class SyncFileJob implements Job{

	private final static Logger LOGGER = Logger.getLogger(SyncFileJob.class);
	
	public void executeJob() throws Exception{
		try {
			LOGGER.info("开始执行文件同步任务");
			List<PathConfig> pathConfigs = Configer.getAllPathConfig();
			for (PathConfig pathConfig : pathConfigs) {
				LOGGER.info(String.format("开始同步文件夹：[%s,%s]", pathConfig.getSourcePath(),pathConfig.getDestinationPath()));
				SyncFileService.syncDirectory(new File(pathConfig.getSourcePath()), new File(pathConfig.getDestinationPath()));
			}
			LOGGER.info("文件同步任务执行完毕");
		} catch (Exception e) {
			LOGGER.error("同步文件时遇到异常",e);
			WindowSetter.setSyncStateException(e.getMessage());
		}
		
	}
}
