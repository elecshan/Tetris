package ui.window;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Set;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;

import control.GameControl;
import ui.Img;
import util.FrameUtil;

@SuppressWarnings("serial")
public class JFrameConfig extends JFrame {

	private JButton btnOk = new JButton("确定");
	
	private JButton btnCancel = new JButton("取消");
	
	private JButton btnUser = new JButton("应用");
	
	private JLabel errorMsg = new JLabel();
	
	private JList skinList = null;
	
	private DefaultListModel skinData = new DefaultListModel();
	
	private JPanel skinView = null;
	
	private Image[] skinViewList = null;
	
	private GameControl gameControl;

	private TextControl[] texts = new TextControl[8];
	
	private static final String PATH = "data/control.dat";
	
	private static final String[] METHOD_NAMES = {
			"keyRight","keyUp","keyLeft","keyDown",
			"keyFunLeft","keyFunUp","keyFunRight","keyFunDown",
	};
	
	private static final Image IMG_PSP = new ImageIcon("data/psp.png").getImage();

	public JFrameConfig(GameControl gameControl) {
		//获得游戏控制器
		this.gameControl = gameControl;
		//设置标题
		this.setTitle("设置");
		//设置布局管理器为边界布局
		this.setLayout(new BorderLayout());
		//初始化按键输入框
		this.initTexts();
		//添加主面板
		this.add(this.creatMainPanel(), BorderLayout.CENTER);
		//添加按钮面板
		this.add(this.creatButtonPanel(), BorderLayout.SOUTH);
		//设置窗口大小
		this.setSize(614, 350);
		//设置不能改变大小
		this.setResizable(false);
		//设置居中
		FrameUtil.setFrameCenter(this);
	}

	/**
	 * 初始化按键输入框
	 */
	private void initTexts() {
		int x = 3;
		int y = 45;
		int w = 54;
		int h = 20;
		for (int i = 0; i < 4; i++) {
			texts[i] = new TextControl(x, y + i * 32, w, h, METHOD_NAMES[i]);
		}
		x += 545;
		y += 5;
		for (int i = 0; i < 4; i++) {
			texts[i + 4] = new TextControl(x, y + i * 32, w, h, METHOD_NAMES[i + 4]);
		}
		try {
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream(PATH));
			@SuppressWarnings({ "rawtypes", "unchecked" })
			HashMap<Integer, String> cfgSet = (HashMap)ois.readObject();
			ois.close();
			Set<Entry<Integer, String>> entrySet = cfgSet.entrySet();
			for(Entry<Integer, String> e : entrySet) {
				for(TextControl t : texts) {
					if(t.getMethodName().equals(e.getValue())) {
						t.setKeyCode(e.getKey());
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 创建主面板（选项卡面板）
	 * @return
	 */
	private JTabbedPane creatMainPanel() {
		JTabbedPane jtp = new JTabbedPane();
		jtp.addTab("控制设置", this.creatControlPanel());
		jtp.addTab("皮肤设置", this.creatSkinPanel());
		return jtp;
	}

	/**
	 * 创建玩家皮肤面板
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private JPanel creatSkinPanel() {
		JPanel panel = new JPanel(new BorderLayout());
		File dir = new File(Img.GRAPHICS_PATH);
		File[] files = dir.listFiles();
		this.skinViewList = new Image[files.length];
		for(int i = 0; i < files.length; i++) {
			//增加选项
			this.skinData.addElement(files[i].getName());
			//增加预览图
			this.skinViewList[i] = new ImageIcon(files[i].getPath() + "/view.jpg").getImage();
		}
		this.skinList = new JList(this.skinData);
		//设置默认选择第一个
		this.skinList.setSelectedIndex(0);
		//增加鼠标监听
		this.skinList.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				repaint();
			}
		});
		this.skinView = new JPanel() {
			@Override
			public void paintComponent(Graphics g) {
				Image img = skinViewList[skinList.getSelectedIndex()];
				int x = this.getWidth() - img.getWidth(null) >> 1;
				int y = this.getHeight() - img.getHeight(null) >> 1;
				g.drawImage(img, x, y, null);
			}
		};
		panel.add(new JScrollPane(this.skinList), BorderLayout.WEST);
		panel.add(this.skinView, BorderLayout.CENTER);
		return panel;
	}

	/**
	 * 创建玩家控制设置面板
	 * @return
	 */
	private JPanel creatControlPanel() {
		JPanel jp = new JPanel() {
			@Override
			public void paintComponent(Graphics g) {
				g.drawImage(IMG_PSP, 0, 0, null);
			}
		};
		//设置布局管理器
		jp.setLayout(null);
		//添加按键输入框
		for (int i = 0; i < texts.length; i++) {
			jp.add(texts[i]);
		}
		return jp;
	}

	/**
	 * 创建按钮面板
	 * @return
	 */
	private JPanel creatButtonPanel() {
		//创建按钮面板，采用流式布局
		JPanel jp = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		this.errorMsg.setForeground(Color.RED);
		jp.add(this.errorMsg);
		jp.add(this.btnOk);
		//给确定按钮增加事件监听
		this.btnOk.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(writeConfig()) {
					setVisible(false);
					gameControl.setOver();
				}
			}
		});
		jp.add(this.btnCancel);
		//给取消按钮增加事件监听
				this.btnCancel.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						setVisible(false);
						gameControl.setOver();
					}
				});
		jp.add(this.btnUser);
		//给应用按钮增加事件监听
		this.btnUser.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				writeConfig();
				gameControl.repaint();
			}
		});
		return jp;
	}
	
	/**
	 * 写入游戏配置
	 */
	private boolean writeConfig() {
		HashMap<Integer, String> keySet = new HashMap<Integer, String>();
		for (int i = 0; i < this.texts.length; i++) {
			int keyCode = this.texts[i].getKeyCode();
			if(keyCode == 0) {
				this.errorMsg.setText("错误按键");
				return false;
			}
			keySet.put(keyCode, this.texts[i].getMethodName());
		}
		if(keySet.size() != 8) {
			this.errorMsg.setText("按键重复");
			return false;
		}
		try {
			//切换皮肤
			Img.setSkin(this.skinData.get(this.skinList.getSelectedIndex()).toString() + "/");
			//写入控制配置
			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(PATH));
			oos.writeObject(keySet);
			oos.close();
		} catch (Exception e) {
			e.printStackTrace();
			this.errorMsg.setText(e.getMessage());
			return false;
		}
		this.errorMsg.setText(null);
		return true;
	}

}
