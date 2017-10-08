package com.minsx.constant;

public class Constant {
	// 软件版本
	public static final String version="1.0.0.0";
    // 软件运行目录
    public static final String SOFT_WARE_PATH = System.getProperty("user.dir").replace("\\", "/");
    // 软件配置目录
    public static final String SOFT_CONFIG_PATH = SOFT_WARE_PATH + "/config/";
    // 软件监听端口
    public static final Integer SOFT_GUI_PORT = 60009;
    // 图片路径前缀
    public static final String IMAGE_PATH_PREFIX = "/org/eclipse/wb/swt/";
    // JAVA BIN 路径
    public static final String JAVA_EXE_PATH = SOFT_WARE_PATH + "/JRE/bin/java.exe";
    // 是否正在调试
    public static final Boolean IS_DEBUG = false;
    
}
