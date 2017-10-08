package com.minsx.window;

import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.Region;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.wb.swt.SWTResourceManager;

import com.minsx.config.Configer;
import com.minsx.config.PathConfig;

/**
 * 负责改变窗口组件状态
 *
 * @Author Joker
 * @Date 2017年2月27日
 */
public class WindowSetter {

	/**
	 * 设置右下角图标是否显示
	 */
	public static void setIcoVisible(final boolean visible) {
		MainWindow.tray.getItem(0).setVisible(visible);
	}

	/**
	 * 手动显示/隐藏主窗口
	 */
	public static void setMainWindowVisible(boolean isVisible) {
		try {
			if (isVisible) {
				MainWindow.shell.setVisible(true);
				MainWindow.shell.setActive();
				MainWindow.showOrHideWindowItem.setText("隐藏窗口");
			}else {
				MainWindow.shell.setVisible(false);
				MainWindow.showOrHideWindowItem.setText("显示窗口");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 设置主窗体背景图片
	 * @param shell 主窗体
	 * @param image 背景图片
	 */
	public static void setShellBackgroundImage(Shell shell, Image image) {
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
	}
	
	public static void setMainWindowPathListData() {
		MainWindow.table.removeAll();
		List<PathConfig> pathConfigs = Configer.getAllPathConfig();
		for (int i = 0; i < pathConfigs.size(); i++) {
			TableItem tableItem = new TableItem(MainWindow.table, SWT.NONE);
			tableItem.setText(String.valueOf(i));
			tableItem.setText(new String[] { String.valueOf(i), pathConfigs.get(i).getSourcePath(), pathConfigs.get(i).getDestinationPath()});
		}
	}
	
	
	public static void setSyncStateException(final String error) {
		MainWindow.display.asyncExec(()->{
			MainWindow.stateIcoLabel.setToolTipText(String.format("同步文件遇到异常，已停止同步\n错误原因：%s", error));
			MainWindow.stateIcoLabel.setImage(SWTResourceManager.getImage(MainWindow.class, "/org/eclipse/wb/swt/excepted.png"));
		});
	}
	
	

}
