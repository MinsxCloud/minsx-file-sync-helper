package com.minsx.window;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.Region;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Tray;
import org.eclipse.swt.widgets.TrayItem;
import org.eclipse.wb.swt.SWTResourceManager;

import com.minsx.constant.Constant;
import com.minsx.main.ApplicationManager;

/**
 * 主窗口组件
 *
 * @Author Joker
 * @Date 2017年2月27日
 */
public class MainWindow {
	public static Shell shell;
	public static Display display;
	public static Tray tray;
	public static Table table;
	public static TrayItem trayItem;
	public static Menu trayMenu;
	public static Label minMainWindowLabel, closeMainWindowLabel,leftLogoLabel ;
	public static Label addIcoLabel,settingIcoLabel,addLabel,settingLabel,openAndCloseIcoLabel,openAndCloseLabel,stateIcoLabel;
	public static MenuItem showOrHideWindowItem, exitMenuItem,deleteItem,openSourceItem,openDestinationItem,settingItem,aboutItem;

	/**
	 * @wbp.parser.entryPoint
	 */
	public static void open() {
		try {
			display = Display.getDefault();
			ApplicationManager.doBeforeCreatingContent();
			MainWindow.createContents();
			ApplicationManager.doAfterCreatingContent();
			shell.layout();
			while (!shell.isDisposed()) {
				if (!display.readAndDispatch()) {
					display.sleep();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 创建窗口组件
	 */
	public static void createContents() {
		//shell = new Shell(display, SWT.NO_TRIM);
		shell = new Shell(display, Constant.IS_DEBUG?SWT.NONE:SWT.NO_TRIM);
		shell.setImage(SWTResourceManager.getImage(MainWindow.class, "/org/eclipse/wb/swt/logo.ico"));
		shell.setSize(547, 402);
		shell.setText("放疗云文件同步助手");
		// shell.setLayout(null);
		
		// 生成主窗体背景
		Image image = SWTResourceManager.getImage(MainWindow.class, "/org/eclipse/wb/swt/mainBackImg.png");
		// WindowSetter.setShellBackgroundImage(shell, image);
		ImageData imageData = image.getImageData();
		// 区分image是α还是β像素
		boolean α = imageData.alphaData == null ? false : true;
		Region region = new Region();
		for (int i = 0; i < imageData.width; i++) {
			for (int j = 0; j < imageData.height; j++) {
				int v = α ? imageData.getAlpha(i, j) : imageData.getPixel(i, j);
				if (α && v == 255) {
					region.add(i, j, 1, 1);
				}
				if (!α && v != 0) {
					region.add(i, j, 1, 1);
				}
			}
		}
		shell.setBackgroundImage(image);
		shell.setRegion(region);
		shell.setBackgroundMode(SWT.INHERIT_FORCE);
		shell.setVisible(true);
		// 构造系统栏控件
		tray = display.getSystemTray();
		trayItem = new TrayItem(tray, SWT.NONE);
		// 托盘图标
		Image lowerRightCorner = new Image(null, SWTResourceManager.class.getResourceAsStream("logo.ico"));
		trayItem.setImage(lowerRightCorner);
		trayItem.setVisible(Constant.IS_DEBUG?false:true);

		trayItem.setToolTipText(shell.getText());
		trayMenu = new Menu(shell, SWT.POP_UP);

		addIcoLabel = new Label(shell, SWT.NONE);
		addIcoLabel.setImage(SWTResourceManager.getImage(MainWindow.class, "/org/eclipse/wb/swt/add.png"));
		addIcoLabel.setBounds(426, 373, 18, 17);

		minMainWindowLabel = new Label(shell, SWT.NONE);
		minMainWindowLabel.setBounds(467, 0, 31, 20);

		closeMainWindowLabel = new Label(shell, SWT.NONE);
		closeMainWindowLabel.setBounds(498, 1, 48, 20);

		leftLogoLabel = new Label(shell, SWT.NONE);
		leftLogoLabel.setImage(SWTResourceManager.getImage(MainWindow.class, "/org/eclipse/wb/swt/leftLogo.png"));
		leftLogoLabel.setBounds(10, 4, 213, 40);

		addLabel = new Label(shell, SWT.NONE);
		addLabel.setForeground(SWTResourceManager.getColor(51, 153, 204));
		addLabel.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 9, SWT.BOLD));
		addLabel.setText("新增");
		addLabel.setBounds(445, 373, 26, 17);

		settingIcoLabel = new Label(shell, SWT.NONE);
		settingIcoLabel.setImage(SWTResourceManager.getImage(MainWindow.class, "/org/eclipse/wb/swt/setting.png"));
		settingIcoLabel.setBounds(482, 373, 19, 17);

		settingLabel = new Label(shell, SWT.NONE);
		settingLabel.setForeground(SWTResourceManager.getColor(51, 153, 204));
		settingLabel.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 9, SWT.BOLD));
		settingLabel.setText("设置");
		settingLabel.setBounds(501, 373, 26, 17);

		table = new Table(shell, SWT.FULL_SELECTION);
		table.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 9, SWT.NORMAL));
		table.setBounds(4, 69, 539, 285);
		table.setLinesVisible(true);
		
		Menu filePathListMenu = new Menu(shell, SWT.POP_UP);
		deleteItem = new MenuItem(filePathListMenu, SWT.PUSH);
		deleteItem.setText("删除该项");
		openSourceItem = new MenuItem(filePathListMenu, SWT.PUSH);
		openSourceItem.setText("查看来源");
		openDestinationItem = new MenuItem(filePathListMenu, SWT.PUSH);
		openDestinationItem.setText("查看目标");
		table.setMenu(filePathListMenu);
		
		TableColumn tblclmnId = new TableColumn(table, SWT.LEFT);
		tblclmnId.setWidth(41);
		tblclmnId.setText("编号");

		TableColumn tblclmnSourcepath = new TableColumn(table, SWT.NONE);
		tblclmnSourcepath.setWidth(234);
		tblclmnSourcepath.setText("来源");

		TableColumn tblclmnDestinationpath = new TableColumn(table, SWT.NONE);
		tblclmnDestinationpath.setWidth(245);
		tblclmnDestinationpath.setText("目标");
		
		Label lblNewLabel = new Label(shell, SWT.NONE);
		lblNewLabel.setImage(SWTResourceManager.getImage(MainWindow.class, "/org/eclipse/wb/swt/tableHeader.png"));
		lblNewLabel.setBounds(4, 45, 539, 24);
		
		stateIcoLabel = new Label(shell, SWT.NONE);
		stateIcoLabel.setToolTipText("已停止同步");
		stateIcoLabel.setImage(SWTResourceManager.getImage(MainWindow.class, "/org/eclipse/wb/swt/stoped.png"));
		stateIcoLabel.setBounds(10, 373, 16, 16);
		
		openAndCloseIcoLabel = new Label(shell, SWT.NONE);
		openAndCloseIcoLabel.setImage(SWTResourceManager.getImage(MainWindow.class, "/org/eclipse/wb/swt/start.png"));
		openAndCloseIcoLabel.setBounds(367, 373, 18, 17);
		
		openAndCloseLabel = new Label(shell, SWT.NONE);
		openAndCloseLabel.setForeground(SWTResourceManager.getColor(51, 153, 204));
		openAndCloseLabel.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 9, SWT.BOLD));
		openAndCloseLabel.setText("开启");
		openAndCloseLabel.setBounds(386, 373, 26, 17);

		WindowSetter.setMainWindowPathListData();

		// 鼠标hover样式
		Cursor crossCursor = MainWindow.display.getSystemCursor(SWT.CURSOR_HAND);
		MainWindow.addIcoLabel.setCursor(crossCursor);
		MainWindow.addLabel.setCursor(crossCursor);
		MainWindow.settingIcoLabel.setCursor(crossCursor);
		MainWindow.settingLabel.setCursor(crossCursor);
		MainWindow.openAndCloseLabel.setCursor(crossCursor);
		MainWindow.openAndCloseIcoLabel.setCursor(crossCursor);

		settingItem = new MenuItem(trayMenu, SWT.NONE);
		settingItem.setText("设置参数");
		settingItem.setImage(SWTResourceManager.getImage(MainWindow.class, "/org/eclipse/wb/swt/rightLowerCornerSetting.png"));
		trayMenu.setDefaultItem(settingItem);
		
		aboutItem = new MenuItem(trayMenu, SWT.NONE);
		aboutItem.setText("关于软件");
		aboutItem.setImage(SWTResourceManager.getImage(MainWindow.class, "/org/eclipse/wb/swt/about.png"));
		trayMenu.setDefaultItem(aboutItem);
		
		new MenuItem(trayMenu, SWT.SEPARATOR);
		
		showOrHideWindowItem = new MenuItem(trayMenu, SWT.NONE);
		showOrHideWindowItem.setText("隐藏程序");
		showOrHideWindowItem.setImage(SWTResourceManager.getImage(MainWindow.class, "/org/eclipse/wb/swt/show.png"));
		trayMenu.setDefaultItem(showOrHideWindowItem);
		
		//系统栏中的退出菜单，程序只能通过这个菜单退出
		exitMenuItem = new MenuItem(trayMenu, SWT.PUSH);
		exitMenuItem.setImage(SWTResourceManager.getImage(MainWindow.class, "/org/eclipse/wb/swt/exit.png"));
		exitMenuItem.setText("退出程序");
		trayItem.setImage(shell.getImage());

	}
}
