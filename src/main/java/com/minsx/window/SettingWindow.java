package com.minsx.window;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.Region;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Scale;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.wb.swt.SWTResourceManager;

import com.minsx.constant.Constant;
import com.minsx.core.CallBack;
import com.minsx.core.Window;

public class SettingWindow implements Window{

	private Shell parentShell;
	private Shell shell;
	private CLabel noLabel;
	private CLabel yesLabel;
	private Scale scale;
	private Label titleLabel,syncIntervalLabel;
	private Combo startTimeCombo,endTimeCombo;
	private Button realTimeSyncRadioButton,regularTimeSyncRadioButton;
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
	public SettingWindow(Shell parentShell) {
		this.parentShell = parentShell;
		shell = new Shell(parentShell, Constant.IS_DEBUG?SWT.NONE:SWT.NO_TRIM);
		shell.setSize(419, 388);
		shell.setLocation((parentShell.getLocation().x + (parentShell.getSize().x - 419) / 2),
				(parentShell.getLocation().y + (parentShell.getSize().y - 388) / 2));
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
		titleLabel.setBounds(12, 7, 312, 20);
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
					yesCallBack.run(getConfig());
				}
			}
		});

		noLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				if (noCallBack != null) {
					noCallBack.run(getConfig());
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
		ListenerManager.forbidESC(shell);
		
		Label lblNewLabel = new Label(shell, SWT.NONE);
		lblNewLabel.setBounds(37, 107, 68, 20);
		lblNewLabel.setText("同步间隔：");
		
		Label label = new Label(shell, SWT.NONE);
		label.setText("同步模式：");
		label.setBounds(37, 66, 65, 20);
		
		realTimeSyncRadioButton = new Button(shell, SWT.RADIO);
		realTimeSyncRadioButton.setSelection(true);
		realTimeSyncRadioButton.setBounds(113, 66, 93, 20);
		realTimeSyncRadioButton.setText("实时同步");
		
		regularTimeSyncRadioButton = new Button(shell, SWT.RADIO);
		regularTimeSyncRadioButton.setText("按时间段同步");
		regularTimeSyncRadioButton.setBounds(213, 66, 129, 20);
		
		scale = new Scale(shell, SWT.NONE);
		scale.setPageIncrement(60);
		scale.setMaximum(1440);
		scale.setMinimum(1);
		scale.setSelection(60);
		scale.setBounds(29, 137, 337, 42);
		
		scale.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				syncIntervalLabel.setText(String.valueOf(scale.getSelection()));
			}
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
			}
		});
		
		Group satrtAndEndTimeSettingGroup = new Group(shell, SWT.NONE);
		satrtAndEndTimeSettingGroup.setText(" 时间段设置 ");
		satrtAndEndTimeSettingGroup.setBounds(37, 206, 323, 90);
		
		Label label_1 = new Label(satrtAndEndTimeSettingGroup, SWT.NONE);
		label_1.setText("开始时间：");
		label_1.setBounds(13, 40, 60, 20);
		
		Label label_2 = new Label(satrtAndEndTimeSettingGroup, SWT.NONE);
		label_2.setText("结束时间：");
		label_2.setBounds(166, 40, 60, 20);
		
		startTimeCombo = new Combo(satrtAndEndTimeSettingGroup, SWT.NONE);
		startTimeCombo.setItems(new String[] {"00:00", "01:00", "02:00", "03:00", "04:00", "05:00", "06:00", "07:00", "08:00", "09:00", "10:00", "11:00", "12:00", "13:00", "14:00", "15:00", "16:00", "17:00", "18:00", "19:00", "20:00", "21:00", "22:00", "23:00"});
		startTimeCombo.setBounds(75, 37, 80, 25);
		startTimeCombo.select(0);
		startTimeCombo.setText("00:00");
		
		endTimeCombo = new Combo(satrtAndEndTimeSettingGroup, SWT.NONE);
		endTimeCombo.setItems(new String[] {"00:00", "01:00", "02:00", "03:00", "04:00", "05:00", "06:00", "07:00", "08:00", "09:00", "10:00", "11:00", "12:00", "13:00", "14:00", "15:00", "16:00", "17:00", "18:00", "19:00", "20:00", "21:00", "22:00", "23:00"});
		endTimeCombo.setBounds(228, 37, 80, 25);
		endTimeCombo.select(0);
		endTimeCombo.setText("00:00");
		
		Label label_3 = new Label(shell, SWT.NONE);
		label_3.setText("(分钟)");
		label_3.setBounds(161, 107, 48, 20);
		
		syncIntervalLabel = new Label(shell, SWT.NONE);
		syncIntervalLabel.setText("60");
		syncIntervalLabel.setBounds(116, 108, 40, 17);
		
		startTimeCombo.setEnabled(false);
		endTimeCombo.setEnabled(false);
		
		realTimeSyncRadioButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				startTimeCombo.setEnabled(false);
				endTimeCombo.setEnabled(false);
			}
		});
		
		regularTimeSyncRadioButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				startTimeCombo.setEnabled(true);
				endTimeCombo.setEnabled(true);
			}
		});
	}

	public Map<String, Object> getConfig() {
		Map<String, Object> config = new HashMap<>();
		config.put("isRealTime", realTimeSyncRadioButton.getSelection());
		config.put("syncInterval", syncIntervalLabel.getText());
		config.put("startTime", startTimeCombo.getText());
		config.put("endTime", endTimeCombo.getText());
		return config;
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
	
	public void setIsRealTime(Boolean isRealTime) {
		if (isRealTime) {
			realTimeSyncRadioButton.setSelection(true);
			regularTimeSyncRadioButton.setSelection(false);
			startTimeCombo.setEnabled(false);
			endTimeCombo.setEnabled(false);
		}else {
			realTimeSyncRadioButton.setSelection(false);
			regularTimeSyncRadioButton.setSelection(true);
			startTimeCombo.setEnabled(true);
			endTimeCombo.setEnabled(true);
		}
	}
	
	public void setSyncInterval(Integer syncInterval) {
		syncIntervalLabel.setText(String.valueOf(syncInterval));
		scale.setSelection(syncInterval);
	}
	
	public void setStartTime(String startTime) {
		startTimeCombo.setText(startTime);
	}
	
	public void setEndTime(String endTime) {
		endTimeCombo.setText(endTime);
	}
}
