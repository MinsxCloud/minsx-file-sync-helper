package com.minsx.main;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.lang.management.ManagementFactory;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;

import org.apache.log4j.Logger;

import com.minsx.constant.Constant;
import com.minsx.schedule.ThreadManager;
import com.minsx.service.SyncFileScheduleHandle;
import com.minsx.util.FileUtil;
import com.minsx.util.SystemUtil;
import com.minsx.window.ListenerManager;
import com.minsx.window.MainWindow;
import com.minsx.window.WindowSetter;

/**
 * 负责程序加载流程和退出关闭流程
 */
public class ApplicationManager {
	
	private final static Logger LOGGER = Logger.getLogger(ApplicationManager.class);

    /**
     * 启动程序
     */
    public static void startApp() {
        if (!checkLock()) {
        	LOGGER.info("未获得软件文件锁，尝试打开已启动软件");
            sendSocketToOpenLastWindow();
            System.exit(0);
        } else {
        	LOGGER.info("已获得软件文件锁，开启软件监听");
            initServerSocket();
            LOGGER.info("开始打开软件窗口");
            MainWindow.open();
            //以下代码均执行不到
        }
    }

    /**
     * 创建窗口组件前处理某些事务
     */
    public static void doBeforeCreatingContent() {
    }

    /**
     * 创建窗口组件后处理某些事务
     */
    public static void doAfterCreatingContent() {
        ListenerManager.addListeners();
    }

    /**
     * 检查是否获得锁 true:获得锁说明是第一次执行; false:没有取得锁，说明已经有一个程序在执行
     */
    @SuppressWarnings("resource")
    public static boolean checkLock() {
    	LOGGER.info("开始检查是否获得软件文件锁");
        FileLock lock = null;
        RandomAccessFile r = null;
        FileChannel fc = null;
        try {
            // 在临时文件夹创建一个临时文件，锁住这个文件用来保证应用程序只有一个实例被创建.
            String lockFilePath = Constant.SOFT_CONFIG_PATH + "/app.lock";
            File sf = new File(lockFilePath);
            if (!sf.exists()) {
                FileUtil.createFile(lockFilePath);
            }
            sf.deleteOnExit();
            sf.createNewFile();
            r = new RandomAccessFile(sf, "rw");
            fc = r.getChannel();
            lock = fc.tryLock();
            if (lock == null || !lock.isValid()) {
                // 没有得到锁
                return false;
            }
        } catch (Exception e) {
        }
        return true;
    }

    /**
     * 退出程序
     */
    public static void exit() {
    	LOGGER.info("开始准备退出程序");
    	LOGGER.info("开始关闭软件监听");
        closeServerSocket();
        LOGGER.info("开始关闭同步文件任务");
        SyncFileScheduleHandle.closeSchedule();
        LOGGER.info("开始关闭窗口主SHELL");
        MainWindow.shell.dispose();
        LOGGER.info("开始清除右下角任务图标");
        WindowSetter.setIcoVisible(false);
        // get current progress' pid and kill it
        LOGGER.info("开始关闭程序主进程，程序彻底关闭！");
        if (System.getProperty("os.name").contains("Window")) {
        	String pid = ManagementFactory.getRuntimeMXBean().getName().split("@")[0];
        	SystemUtil.excuteCmd("taskkill /F /pid " + pid);
		}
    }

    /**
     * 关闭serverSocket
     */
    @SuppressWarnings("deprecation")
    public static void closeServerSocket() {
        if (ThreadManager.serverSocketThread != null) {
            ThreadManager.serverSocketThread.stop();
        }
        try {
            if (ThreadManager.serverSocket != null) {
                ThreadManager.serverSocket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("resource")
    public static void sendSocketToOpenLastWindow() {
        try {
            new Socket(InetAddress.getLocalHost(), Constant.SOFT_GUI_PORT);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 初始化serverSocket
     */
    public static void initServerSocket() {
        ThreadManager.serverSocketThread = new Thread(() -> {
            try {
                ThreadManager.serverSocket = new ServerSocket(Constant.SOFT_GUI_PORT);
                while (true) {
                    if (ThreadManager.serverSocket.isClosed()) {
                        break;
                    }
                    // 如果监听到一个socket连接，说明程序启图再次打开一个实例，此时显示前一个窗体
                    Socket socket = ThreadManager.serverSocket.accept();
                    MainWindow.display.asyncExec(() -> {
                        WindowSetter.setMainWindowVisible(true);
                    });
                    socket.close();
                }
            } catch (IOException e) {
            }
        });
        ThreadManager.serverSocketThread.setName("serverSocketThread");
        ThreadManager.serverSocketThread.start();
    }


}
