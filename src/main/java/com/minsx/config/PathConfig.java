package com.minsx.config;

public class PathConfig {
	
	private String sourcePath;
	private String destinationPath;
	
	public PathConfig() {}
	
	public PathConfig(String sourcePath,String destinationPath) {
		this.sourcePath=sourcePath;
		this.destinationPath=destinationPath;
	}
	
	public String getSourcePath() {
		return sourcePath;
	}
	public void setSourcePath(String sourcePath) {
		this.sourcePath = sourcePath;
	}
	public String getDestinationPath() {
		return destinationPath;
	}
	public void setDestinationPath(String destinationPath) {
		this.destinationPath = destinationPath;
	}
	
	

}
