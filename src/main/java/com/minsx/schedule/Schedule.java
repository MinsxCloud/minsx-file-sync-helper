package com.minsx.schedule;

import java.time.LocalDateTime;

/**
 * Schedule Created by Joker on 2017/7/14.
 */
public class Schedule {

	private String startTime;
	private String endTime;
	private Integer intervalMin;
	private Job job;
	private Boolean isRealTimeExecute;
	private Boolean isStopFlag;
	private Thread scheduleThread;
	
	public Schedule() {}
	public Schedule(Job job) {
		this.job=job;
	}

	public void executeJob() throws Exception {
		if (isRealTimeExecute) { // 实时执行
			while (!isStopFlag) {
				job.executeJob();
				if (!isStopFlag) {
					waitIntervalMin();
				}
				Thread.sleep(1000);// 检测频率
			}
		} else {// 在规定时间段内执行
			while (!isStopFlag) {
				if (getStartTime().isBefore(getCurrentTime()) && getEndTime().isAfter(getCurrentTime())) {
					job.executeJob();
					if (!isStopFlag) {
						waitIntervalMin();
					}
				}
				Thread.sleep(1000);// 检测频率
			}
		}
	}

	private void waitIntervalMin() throws Exception {
		for (int i = 0; (i < intervalMin * 60) && (!isStopFlag); i++) {
			Thread.sleep(1000);
		}
	}

	private LocalDateTime getStartTime() {
		Integer hour = Integer.parseInt(startTime.split(":")[0]);
		Integer minute = Integer.parseInt(startTime.split(":")[1]);
		LocalDateTime currentDateTime = LocalDateTime.now();
		return currentDateTime.withHour(hour).withMinute(minute);
	}

	private LocalDateTime getEndTime() {
		Integer hour = Integer.parseInt(endTime.split(":")[0]);
		Integer minute = Integer.parseInt(endTime.split(":")[1]);
		LocalDateTime currentDateTime = LocalDateTime.now();
		LocalDateTime endDateTime = currentDateTime.withHour(hour).withMinute(minute);
		LocalDateTime newEndDateTime = null;
		if (getStartTime().isAfter(endDateTime)) {
			newEndDateTime = endDateTime.withDayOfYear(endDateTime.getDayOfYear() + 1);
		} else {
			newEndDateTime = endDateTime;
		}
		return newEndDateTime;
	}

	private LocalDateTime getCurrentTime() {
		return LocalDateTime.now();
	}

	public void start() throws ScheduleException {
		isStopFlag = false;
		// 测试任务参数是否合法
		testScheduleParameter();
		// 开始执行任务
		scheduleThread = new Thread(() -> {
			try {
				executeJob();
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
		scheduleThread.start();
	}

	public void stop() {
		isStopFlag = true;
	}

	public boolean testScheduleParameter() throws ScheduleException {
		boolean isSuccess = true;
		try {
			Integer.parseInt(startTime.split(":")[0]);
			Integer.parseInt(startTime.split(":")[1]);
		} catch (Exception e) {
			throw new ScheduleException("开始时间不合法", e.getCause());
		}
		try {
			Integer.parseInt(endTime.split(":")[0]);
			Integer.parseInt(endTime.split(":")[1]);
		} catch (Exception e) {
			throw new ScheduleException("结束时间不合法", e.getCause());
		}

		if (this.intervalMin == null) {
			throw new ScheduleException("未设置时间间隔");
		}
		if (this.job == null) {
			throw new ScheduleException("未设置Job");
		}
		if (this.isRealTimeExecute == null) {
			throw new ScheduleException("未设置是否实时执行");
		}
		return isSuccess;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public Integer getIntervalMin() {
		return intervalMin;
	}

	public void setIntervalMin(Integer intervalMin) {
		this.intervalMin = intervalMin;
	}

	public Job getJob() {
		return job;
	}

	public void setJob(Job job) {
		this.job = job;
	}

	public Boolean getRealTimeExecute() {
		return isRealTimeExecute;
	}

	public void setRealTimeExecute(Boolean realTimeExecute) {
		isRealTimeExecute = realTimeExecute;
	}

	public Boolean getStopFlag() {
		return isStopFlag;
	}

	public void setStopFlag(Boolean stopFlag) {
		isStopFlag = stopFlag;
	}

	public Thread getScheduleThread() {
		return scheduleThread;
	}

	public void setScheduleThread(Thread scheduleThread) {
		this.scheduleThread = scheduleThread;
	}
}
