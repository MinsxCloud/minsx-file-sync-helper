package com.minsx.window;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.Region;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.wb.swt.SWTResourceManager;

import com.minsx.constant.Constant;
import com.minsx.core.CallBack;
import com.minsx.core.Window;

public class BaseWindow implements Window{

	private Shell parentShell;
	private Shell shell;
	private CLabel noLabel;
	private CLabel yesLabel;
	private Label titleLabel;
	private String title;
	private String yesButtonName;
	private String noButtonName;
	private CallBack yesCallBack;
	private CallBack noCallBack;
	private Boolean isMouseDownOnWindow;
	private Boolean isMouseDownOnTitle;
	private Integer mouseDownInitialX;
	private Integer mouseDownInitialY;

	@Override
	public void open() {
		parentShell.setEnabled(false);
		this.shell.open();
	}
	
	@Override
	public void close() {
		parentShell.setEnabled(true);
		this.shell.close();
	}

	/**
	 * @wbp.parser.entryPoint
	 */
	public BaseWindow(Shell parentShell) {
		this.parentShell = parentShell;
		shell = new Shell(parentShell, Constant.IS_DEBUG?SWT.NONE:SWT.NO_TRIM);
		shell.setSize(419, 388);
		shell.setLocation((parentShell.getLocation().x + (parentShell.getSize().x - 389) / 2),
				(parentShell.getLocation().y + (parentShell.getSize().y - 192) / 2));
		Image image = SWTResourceManager.getImage(MainWindow.class, "/org/eclipse/wb/swt/settingBackImg.png");
		// WindowSetter.setShellBackgroundImage(this.shell, image);
		ImageData imageData = image.getImageData();
		// 遍历非透明像素点
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

		titleLabel = new Label(shell, SWT.HORIZONTAL);
		titleLabel.setBounds(12, 7, 312, 24);
		shell.setText("设置");
		titleLabel.setText("设置");

		noLabel = new CLabel(shell, SWT.NONE);
		noLabel.setText("取 消");
		noLabel.setAlignment(SWT.CENTER);
		noLabel.setBackground(SWTResourceManager.getImage(MessageWindow.class, "/org/eclipse/wb/swt/noExit.png"));
		noLabel.setBounds(318, 340, 73, 24);

		yesLabel = new CLabel(shell, SWT.NONE);
		yesLabel.setAlignment(SWT.CENTER);
		yesLabel.setText("确 定");
		yesLabel.setBackground(SWTResourceManager.getImage(MessageWindow.class, "/org/eclipse/wb/swt/yesExit.png"));
		yesLabel.setBounds(232, 340, 73, 24);

		ListenerManager.addImgHoverListener(noLabel, "noExit.png", "noEnter.png");
		ListenerManager.addImgHoverListener(yesLabel, "yesExit.png", "yesEnter.png");

		yesLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				if (yesCallBack != null) {
					yesCallBack.run(null);
				}
			}
		});

		noLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				if (noCallBack != null) {
					noCallBack.run(null);
				}
			}
		});

		Label closeLabel = new Label(shell, SWT.NONE);
		closeLabel.setBounds(369, 1, 48, 20);
		ListenerManager.addImgHoverListener(closeLabel, null, "close.png");
		closeLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				close();
			}
		});

		isMouseDownOnWindow = false;
		shell.addMouseListener(new MouseListener() {
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
		shell.addMouseMoveListener((event) -> {
			if (isMouseDownOnWindow) {
				shell.setLocation(shell.getLocation().x - mouseDownInitialX + event.x,
						shell.getLocation().y - mouseDownInitialY + event.y);
			}
		});
		isMouseDownOnTitle = false;
		titleLabel.addMouseListener(new MouseListener() {
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
		titleLabel.addMouseMoveListener((event) -> {
			if (isMouseDownOnTitle) {
				shell.setLocation(shell.getLocation().x - mouseDownInitialX + event.x,
						shell.getLocation().y - mouseDownInitialY + event.y);
			}
		});
	}
	
	@Override
	public Shell getShell() {
		return shell;
	}

	public void setShell(Shell shell) {
		this.shell = shell;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
		shell.setText(this.title);
		titleLabel.setText(this.title);
	}

	public String getYesButtonName() {
		return yesButtonName;
	}

	public void setYesButtonName(String yesButtonName) {
		this.yesButtonName = yesButtonName;
		yesLabel.setText(this.yesButtonName);
	}

	public String getNoButtonName() {
		return noButtonName;
	}

	public void setNoButtonName(String noButtonName) {
		this.noButtonName = noButtonName;
		noLabel.setText(this.noButtonName);
	}

	public CallBack getYesCallBack() {
		return yesCallBack;
	}

	public void setYesCallBack(CallBack yesCallBack) {
		this.yesCallBack = yesCallBack;
	}

	public CallBack getNoCallBack() {
		return noCallBack;
	}

	public void setNoCallBack(CallBack noCallBack) {
		this.noCallBack = noCallBack;
	}

}
