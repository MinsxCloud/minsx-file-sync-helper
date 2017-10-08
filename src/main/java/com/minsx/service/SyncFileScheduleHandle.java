package com.minsx.service;

import org.apache.log4j.Logger;

import com.minsx.config.Configer;
import com.minsx.config.GlobalConfig;
import com.minsx.schedule.Job;
import com.minsx.schedule.Schedule;
import com.minsx.schedule.ScheduleException;

public class SyncFileScheduleHandle {
	
	private static Schedule syncFileSchedule;
	
	private final static Logger LOGGER = Logger.getLogger(SyncFileScheduleHandle.class);
	
	public static void openSchedule() {
		LOGGER.info("开启同步文件任务");
		GlobalConfig globalConfig = Configer.getGlobalConfig();
		Job syncFileJob = new SyncFileJob();
		syncFileSchedule = new Schedule();
		syncFileSchedule.setStartTime(globalConfig.getStartTime());
		syncFileSchedule.setEndTime(globalConfig.getEndTime());
        syncFileSchedule.setIntervalMin(globalConfig.getSyncInterval());
        syncFileSchedule.setJob(syncFileJob);
        syncFileSchedule.setRealTimeExecute(globalConfig.getIsRealTime());
        syncFileSchedule.setStopFlag(false);
		try {
			syncFileSchedule.start();
		} catch (ScheduleException e) {
			e.printStackTrace();
		}
	}
	
	public static void closeSchedule() {
		LOGGER.info("关闭同步文件任务");
		if (syncFileSchedule!=null) {
			syncFileSchedule.stop();
		}
	}

}
