package com.minsx.config;

public class GlobalConfig {
	
	private Boolean isRealTime;
	
	private Integer syncInterval;
	
	private String startTime;
	
	private String endTime;

	public Boolean getIsRealTime() {
		return isRealTime;
	}

	public void setIsRealTime(Boolean isRealTime) {
		this.isRealTime = isRealTime;
	}

	public Integer getSyncInterval() {
		return syncInterval;
	}

	public void setSyncInterval(Integer syncInterval) {
		this.syncInterval = syncInterval;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

}
