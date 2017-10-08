package com.minsx.util;

import java.io.IOException;

import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Shell;

import com.minsx.window.MessageWindow;

public class WindowUtil {
	
	public static void showMessage(Shell shell,String message) {
		MessageWindow messageWindow = new MessageWindow(shell);
		messageWindow.setTitle("提示信息 :");
		messageWindow.setMessage(message);
		messageWindow.setYesCallBack((result) -> {
			messageWindow.close();
		});
		messageWindow.setNoCallBack((result) -> {
			messageWindow.close();
		});
		messageWindow.open();
	}
	
	/**
	 * 取浏览的文件夹路径
	 * @return 选取的文件路径
	 */
	public static String browserFilePath(Shell shell) {
		// 新建文件夹（目录）对话框
		DirectoryDialog folderdlg = new DirectoryDialog(shell);
		// 设置文件对话框的标题
		folderdlg.setText("文件选择");
		// 设置初始路径
		folderdlg.setFilterPath("SystemDrive");
		// 设置对话框提示文本信息
		folderdlg.setMessage("请选择相应的文件夹");
		// 打开文件对话框，返回选中文件夹目录
		String selecteddir = folderdlg.open();
		return selecteddir;
	}
	
	public static void openDirWindow(String dir) {
		try {
			if (System.getProperty("os.name").contains("Window")) {
				Runtime.getRuntime().exec(String.format("explorer %s", dir));
			}else if (System.getProperty("os.name").contains("Linux")) {
				Runtime.getRuntime().exec(String.format("nautilus %s", dir));
			}else if (System.getProperty("os.name").contains("Mac")) {
				Runtime.getRuntime().exec(String.format("open %s", dir));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
