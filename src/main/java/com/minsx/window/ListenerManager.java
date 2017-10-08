package com.minsx.window;

import java.io.File;

import org.apache.log4j.Logger;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.events.MenuDetectEvent;
import org.eclipse.swt.events.MenuDetectListener;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.MouseTrackAdapter;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.events.ShellAdapter;
import org.eclipse.swt.events.ShellEvent;
import org.eclipse.swt.events.TraverseEvent;
import org.eclipse.swt.events.TraverseListener;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.wb.swt.SWTResourceManager;

import com.minsx.config.Configer;
import com.minsx.config.GlobalConfig;
import com.minsx.constant.Constant;
import com.minsx.core.MouseEventCallBack;
import com.minsx.main.ApplicationManager;
import com.minsx.service.SyncFileScheduleHandle;
import com.minsx.util.FileUtil;
import com.minsx.util.WindowUtil;

public class ListenerManager {

	private static boolean isMouseDownOnWindow = false;
	private static boolean isMouseDownOnTitle = false;
	private static Integer mouseDownInitialX, mouseDownInitialY;
	
	private final static Logger LOGGER = Logger.getLogger(ListenerManager.class);

	public static void addListeners() {

		MainWindow.shell.addShellListener(new ShellAdapter() {
			@Override
			public void shellClosed(ShellEvent e) {
				e.doit = false;
				openCloseAskWindow();
			}
		});

		MainWindow.minMainWindowLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				WindowSetter.setMainWindowVisible(false);
			}
		});

		MainWindow.closeMainWindowLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				openCloseAskWindow();
			}
		});

		// 右下角左键单击图标事件
		MainWindow.trayItem.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				WindowSetter.setMainWindowVisible(true);
			}
		});

		// 在系统栏图标点击鼠标右键时的事件，
		MainWindow.trayItem.addMenuDetectListener(new MenuDetectListener() {
			@Override
			public void menuDetected(MenuDetectEvent e) {
				MainWindow.trayMenu.setVisible(true);
			}
		});

		// 右下角显示程序菜单监听事件
		MainWindow.showOrHideWindowItem.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent event) {
				if (MainWindow.showOrHideWindowItem.getText().equals("显示窗口")) {
					MainWindow.shell.setVisible(true);
					MainWindow.showOrHideWindowItem.setText("隐藏窗口");
				} else {
					MainWindow.shell.setVisible(false);
					MainWindow.showOrHideWindowItem.setText("显示窗口");
				}
			}
		});

		// 右下角退出程序菜单监听事件
		MainWindow.exitMenuItem.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				openCloseAskWindow();
			}
		});
		
		MainWindow.settingItem.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				openSettingWindow();
			}
		});
		
		MainWindow.aboutItem.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				openAboutWindow();
			}
		});

		// 新增按钮鼠标单击事件
		MainWindow.addIcoLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				openAddTaskWindow();
			}
		});

		// 新增按钮鼠标单击事件
		MainWindow.addLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				openAddTaskWindow();
			}
		});

		// 设置按钮鼠标单击事件
		MainWindow.settingIcoLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				openSettingWindow();
			}
		});

		// 设置按钮鼠标单击事件
		MainWindow.settingLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				openSettingWindow();
			}
		});
		
		MainWindow.deleteItem.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				if (MainWindow.table.getSelectionIndex()==-1) {
					WindowUtil.showMessage(MainWindow.shell, "请先选择一项再进行删除！");
					return;
				}
				MainWindow.table.remove(MainWindow.table.getSelectionIndex());
				saveAllPathConfig();
			}
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
			}
		});
		
		MainWindow.openSourceItem.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				if (MainWindow.table.getSelectionIndex()==-1) {
					WindowUtil.showMessage(MainWindow.shell, "请先选择一项再进行查看！");
					return;
				}
				WindowUtil.openDirWindow(MainWindow.table.getItem(MainWindow.table.getSelectionIndex()).getText(1));
			}
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
			}
		});
		
		MainWindow.openDestinationItem.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				if (MainWindow.table.getSelectionIndex()==-1) {
					WindowUtil.showMessage(MainWindow.shell, "请先选择一项再进行查看！");
					return;
				}
				WindowUtil.openDirWindow(MainWindow.table.getItem(MainWindow.table.getSelectionIndex()).getText(2));
			}
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
			}
		});
		
		MainWindow.openAndCloseIcoLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				if (MainWindow.openAndCloseLabel.getText().equals("开启")) {
					openSync();
				}else {
					closeSync();
				}
			}
		});
		
		MainWindow.openAndCloseLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				if (MainWindow.openAndCloseLabel.getText().equals("开启")) {
					openSync();
				}else {
					closeSync();
				}
			}
		});
		

		// -----------------------------------主窗口鼠标移动监听开始--------------------------------------
		MainWindow.shell.addMouseListener(new MouseListener() {
			@Override
			public void mouseUp(MouseEvent arg0) {
				isMouseDownOnWindow = false;
			}
			@Override
			public void mouseDown(MouseEvent arg0) {
				mouseDownInitialX = arg0.x;
				mouseDownInitialY = arg0.y;
				isMouseDownOnWindow = true;
			}
			@Override
			public void mouseDoubleClick(MouseEvent arg0) {
			}
		});
		MainWindow.shell.addMouseMoveListener((event) -> {
			if (isMouseDownOnWindow) {
				MainWindow.shell.setLocation(MainWindow.shell.getLocation().x - mouseDownInitialX + event.x,
						MainWindow.shell.getLocation().y - mouseDownInitialY + event.y);
			}
		});
		
		MainWindow.leftLogoLabel.addMouseListener(new MouseListener() {
			@Override
			public void mouseUp(MouseEvent arg0) {
				isMouseDownOnTitle = false;
			}
			@Override
			public void mouseDown(MouseEvent arg0) {
				mouseDownInitialX = arg0.x;
				mouseDownInitialY = arg0.y;
				isMouseDownOnTitle = true;
			}
			@Override
			public void mouseDoubleClick(MouseEvent arg0) {
			}
		});
		MainWindow.leftLogoLabel.addMouseMoveListener((event) -> {
			if (isMouseDownOnTitle) {
				MainWindow.shell.setLocation(MainWindow.shell.getLocation().x - mouseDownInitialX + event.x,
						MainWindow.shell.getLocation().y - mouseDownInitialY + event.y);
			}
		});
		// ----------------------------------主窗口鼠标移动监听结束--------------------------------------
		// -----------------------------------图片按钮监听开始--------------------------------------
		addImgHoverListener(MainWindow.minMainWindowLabel, null, "min.png");
		addImgHoverListener(MainWindow.closeMainWindowLabel, null, "close.png");
		// ------------------------------------图片按钮监听结束----------------------------------

	}

	/**
	 * 打开关闭程序询问窗口
	 */
	public static void openCloseAskWindow() {
		MessageWindow messageWindow = new MessageWindow(MainWindow.shell);
		messageWindow.setTitle("提示信息 :");
		messageWindow.setMessage("您确定要退出程序吗？");
		messageWindow.setYesCallBack((result) -> {
			ApplicationManager.exit();
		});
		messageWindow.setNoCallBack((result) -> {
			messageWindow.close();
		});
		messageWindow.open();
	}

	/**
	 * 打开设置窗口
	 */
	public static void openSettingWindow() {
		SettingWindow settingWindow = new SettingWindow(MainWindow.shell);
		settingWindow.setYesCallBack((result) -> {
			GlobalConfig config = new GlobalConfig();
			config.setIsRealTime((Boolean)result.get("isRealTime"));
			config.setSyncInterval(Integer.valueOf(result.get("syncInterval").toString()));
			config.setStartTime(result.get("startTime").toString());
			config.setEndTime(result.get("endTime").toString());
			Configer.setGlobalConfig(config);
			settingWindow.close();
		});
		settingWindow.setNoCallBack((result) -> {
			settingWindow.close();
		});
		settingWindow.open();
		GlobalConfig globalConfig = Configer.getGlobalConfig();
		settingWindow.setIsRealTime(globalConfig.getIsRealTime());
		settingWindow.setSyncInterval(globalConfig.getSyncInterval());
		settingWindow.setStartTime(globalConfig.getStartTime());
		settingWindow.setEndTime(globalConfig.getEndTime());
	}
	
	/**
	 * 打开关于窗口
	 */
	public static void openAboutWindow() {
		AboutWindow aboutWindow = new AboutWindow(MainWindow.shell);
		aboutWindow.setYesCallBack((result) -> {
			aboutWindow.close();
		});
		aboutWindow.setNoCallBack((result) -> {
			aboutWindow.close();
		});
		aboutWindow.open();
	}
	
	/**
	 * 打开新增任务窗口
	 */
	public static void openAddTaskWindow() {
		AddTaskWindow settingWindow = new AddTaskWindow(MainWindow.shell);
		settingWindow.setYesCallBack((result) -> {
			if (result.get("sourcePath").toString().isEmpty()) {
				WindowUtil.showMessage(settingWindow.getShell(), "来源文件夹不能为空");
				return;
			}
			if (result.get("destinationPath").toString().isEmpty()) {
				WindowUtil.showMessage(settingWindow.getShell(), "目标文件夹不能为空");
				return;
			}
			if (result.get("sourcePath").toString().contains(result.get("destinationPath").toString())) {
				WindowUtil.showMessage(settingWindow.getShell(), "禁止同步：子文件夹到父文件夹");
				return;
			}
			if (!FileUtil.isEmptyDirectory(new File(result.get("destinationPath").toString()))) {
				MessageWindow messageWindow = new MessageWindow(settingWindow.getShell());
				messageWindow.setMessage("检测到目标文件夹非空，这可能会删除目标文件\n夹内容，您确定要选择该文件夹吗？");
				messageWindow.setYesCallBack((results)->{
					TableItem tableItem = new TableItem(MainWindow.table, SWT.NONE);
					tableItem.setText(String.valueOf(MainWindow.table.getItemCount()-1));
					tableItem.setText(new String[] { String.valueOf(MainWindow.table.getItemCount()-1), result.get("sourcePath").toString(), result.get("destinationPath").toString() });
					saveAllPathConfig();
					settingWindow.close();
					LOGGER.info(String.format("新增任务[%s,%s]成功", result.get("sourcePath").toString(), result.get("destinationPath").toString()));
				});
				messageWindow.setNoCallBack((results)->{
					messageWindow.close();
				});
				messageWindow.open();
				return;
			}else {
				TableItem tableItem = new TableItem(MainWindow.table, SWT.NONE);
				tableItem.setText(String.valueOf(MainWindow.table.getItemCount()-1));
				tableItem.setText(new String[] { String.valueOf(MainWindow.table.getItemCount()-1), result.get("sourcePath").toString(), result.get("destinationPath").toString() });
				saveAllPathConfig();
				settingWindow.close();
				LOGGER.info(String.format("新增任务[%s,%s]成功", result.get("sourcePath").toString(), result.get("destinationPath").toString()));
			}
		});
		settingWindow.setNoCallBack((result) -> {
			settingWindow.close();
		});
		settingWindow.open();
	}

	/**
	 * 屏蔽ESC按键
	 * @param shell 窗口
	 */
	public static void  forbidESC(Shell shell) {
		shell.addTraverseListener(new TraverseListener() {
			@Override
			public void keyTraversed(TraverseEvent arg0) {
				if (arg0.keyCode==SWT.ESC) {
					arg0.doit=false;
				}
			}
		});
	}
	
	/**
	 * 监听Label鼠标Hover效果
	 * 
	 * @param object
	 *            要监听的对象
	 * @param exitImg
	 *            鼠标移出后需要显示的图片名称
	 * @param enterImg
	 *            鼠标移上后需要显示的图片名称
	 */
	public static void addImgHoverListener(Object object, String exitImg, String enterImg) {
		if (object instanceof Label) {
			((Label) object).addMouseTrackListener(new MouseTrackAdapter() {
				@Override
				public void mouseExit(MouseEvent e) {
					if (exitImg == null) {
						((Label) object).setImage(null);
					} else {
						((Label) object).setImage(
								SWTResourceManager.getImage(MainWindow.class, Constant.IMAGE_PATH_PREFIX + exitImg));
					}
				}

				@Override
				public void mouseEnter(MouseEvent e) {
					if (enterImg == null) {
						((Label) object).setImage(null);
					} else {
						((Label) object).setImage(
								SWTResourceManager.getImage(MainWindow.class, Constant.IMAGE_PATH_PREFIX + enterImg));
					}
				}
			});
		} else if (object instanceof CLabel) {
			((CLabel) object).addMouseTrackListener(new MouseTrackAdapter() {
				@Override
				public void mouseExit(MouseEvent e) {
					if (exitImg != null) {
						((CLabel) object).setBackground(
								SWTResourceManager.getImage(MainWindow.class, Constant.IMAGE_PATH_PREFIX + exitImg));
					}
				}

				@Override
				public void mouseEnter(MouseEvent e) {
					if (enterImg != null) {
						((CLabel) object).setBackground(
								SWTResourceManager.getImage(MainWindow.class, Constant.IMAGE_PATH_PREFIX + enterImg));
					}
				}
			});
		}
	}

	/**
	 * 监听Label/CLabel鼠标Hover事件
	 * 
	 * @param object
	 *            要监听的对象
	 * @param exitCallBack
	 *            鼠标移出后回调事件
	 * @param enterCallBack
	 *            鼠标移上回调事件
	 */
	public static void addImgHoverListener(Object object, MouseEventCallBack exitCallBack,
			MouseEventCallBack enterCallBack) {
		if (object instanceof Label) {
			((Label) object).addMouseTrackListener(new MouseTrackAdapter() {
				@Override
				public void mouseExit(MouseEvent e) {
					exitCallBack.run(e);
				}

				@Override
				public void mouseEnter(MouseEvent e) {
					enterCallBack.run(e);
				}
			});
		} else if (object instanceof CLabel) {
			((CLabel) object).addMouseTrackListener(new MouseTrackAdapter() {
				@Override
				public void mouseExit(MouseEvent e) {
					exitCallBack.run(e);
				}

				@Override
				public void mouseEnter(MouseEvent e) {
					enterCallBack.run(e);
				}
			});
		}
	}

	public static void saveAllPathConfig() {
		Configer.setAllPathConfig(WindowGetter.getMainWindowPathListData());
		WindowSetter.setMainWindowPathListData();
	}
	
	public static void openSync() {
		if (Configer.getAllPathConfig().size()==0) {
			WindowUtil.showMessage(MainWindow.shell, "请先配置同步任务");
			return;
		}
		MainWindow.display.asyncExec(()->{
			MainWindow.table.setEnabled(false);
			MainWindow.stateIcoLabel.setImage(SWTResourceManager.getImage(MainWindow.class, "/org/eclipse/wb/swt/running.png"));
			MainWindow.openAndCloseIcoLabel.setImage(SWTResourceManager.getImage(MainWindow.class, "/org/eclipse/wb/swt/stop.png"));
			MainWindow.openAndCloseLabel.setText("关闭");
			MainWindow.addIcoLabel.setEnabled(false);
			MainWindow.addLabel.setEnabled(false);
			MainWindow.settingIcoLabel.setEnabled(false);
			MainWindow.settingLabel.setEnabled(false);
			MainWindow.stateIcoLabel.setToolTipText("正在同步");
		});
		//调用开始同步任务
		SyncFileScheduleHandle.openSchedule();
	}
	
	public static void closeSync() {
		MainWindow.display.asyncExec(()->{
			MainWindow.table.setEnabled(true);
			MainWindow.stateIcoLabel.setImage(SWTResourceManager.getImage(MainWindow.class, "/org/eclipse/wb/swt/stoped.png"));
			MainWindow.openAndCloseIcoLabel.setImage(SWTResourceManager.getImage(MainWindow.class, "/org/eclipse/wb/swt/start.png"));
			MainWindow.openAndCloseLabel.setText("开启");
			MainWindow.addIcoLabel.setEnabled(true);
			MainWindow.addLabel.setEnabled(true);
			MainWindow.settingIcoLabel.setEnabled(true);
			MainWindow.settingLabel.setEnabled(true);
			MainWindow.stateIcoLabel.setToolTipText("已停止同步");
		});
		//调用关闭同步任务
		SyncFileScheduleHandle.closeSchedule();
	}
	

	
	
}
