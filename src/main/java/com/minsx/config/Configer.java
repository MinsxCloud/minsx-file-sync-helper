package com.minsx.config;

import java.util.List;

import com.alibaba.fastjson.JSON;
import com.minsx.util.PropertiesUtil;

public class Configer {
	
	private static final PropertiesUtil PROPERTIES_UTIL = new PropertiesUtil("config.properties");
	
	public static void setAllPathConfig(List<PathConfig> pathConfigs) {
		PROPERTIES_UTIL.set("allPath", JSON.toJSONString(pathConfigs));
	}
	
	public static List<PathConfig> getAllPathConfig() {
		return JSON.parseArray(PROPERTIES_UTIL.get("allPath"), PathConfig.class);
	}
	
	public static void setGlobalConfig(GlobalConfig globalConfig) {
		PROPERTIES_UTIL.set("globalConfig", JSON.toJSONString(globalConfig));
	}
	
	public static GlobalConfig getGlobalConfig() {
		return JSON.parseObject(PROPERTIES_UTIL.get("globalConfig"), GlobalConfig.class);
	}

	public static PropertiesUtil getPropertiesUtil() {
		return PROPERTIES_UTIL;
	}
	
}
