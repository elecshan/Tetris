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

	private JButton btnOk = new JButton("ȷ��");
	
	private JButton btnCancel = new JButton("ȡ��");
	
	private JButton btnUser = new JButton("Ӧ��");
	
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
		//�����Ϸ������
		this.gameControl = gameControl;
		//���ñ���
		this.setTitle("����");
		//���ò��ֹ�����Ϊ�߽粼��
		this.setLayout(new BorderLayout());
		//��ʼ�����������
		this.initTexts();
		//��������
		this.add(this.creatMainPanel(), BorderLayout.CENTER);
		//��Ӱ�ť���
		this.add(this.creatButtonPanel(), BorderLayout.SOUTH);
		//���ô��ڴ�С
		this.setSize(614, 350);
		//���ò��ܸı��С
		this.setResizable(false);
		//���þ���
		FrameUtil.setFrameCenter(this);
	}

	/**
	 * ��ʼ�����������
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
	 * ��������壨ѡ���壩
	 * @return
	 */
	private JTabbedPane creatMainPanel() {
		JTabbedPane jtp = new JTabbedPane();
		jtp.addTab("��������", this.creatControlPanel());
		jtp.addTab("Ƥ������", this.creatSkinPanel());
		return jtp;
	}

	/**
	 * �������Ƥ�����
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private JPanel creatSkinPanel() {
		JPanel panel = new JPanel(new BorderLayout());
		File dir = new File(Img.GRAPHICS_PATH);
		File[] files = dir.listFiles();
		this.skinViewList = new Image[files.length];
		for(int i = 0; i < files.length; i++) {
			//����ѡ��
			this.skinData.addElement(files[i].getName());
			//����Ԥ��ͼ
			this.skinViewList[i] = new ImageIcon(files[i].getPath() + "/view.jpg").getImage();
		}
		this.skinList = new JList(this.skinData);
		//����Ĭ��ѡ���һ��
		this.skinList.setSelectedIndex(0);
		//����������
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
	 * ������ҿ����������
	 * @return
	 */
	private JPanel creatControlPanel() {
		JPanel jp = new JPanel() {
			@Override
			public void paintComponent(Graphics g) {
				g.drawImage(IMG_PSP, 0, 0, null);
			}
		};
		//���ò��ֹ�����
		jp.setLayout(null);
		//��Ӱ��������
		for (int i = 0; i < texts.length; i++) {
			jp.add(texts[i]);
		}
		return jp;
	}

	/**
	 * ������ť���
	 * @return
	 */
	private JPanel creatButtonPanel() {
		//������ť��壬������ʽ����
		JPanel jp = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		this.errorMsg.setForeground(Color.RED);
		jp.add(this.errorMsg);
		jp.add(this.btnOk);
		//��ȷ����ť�����¼�����
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
		//��ȡ����ť�����¼�����
				this.btnCancel.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						setVisible(false);
						gameControl.setOver();
					}
				});
		jp.add(this.btnUser);
		//��Ӧ�ð�ť�����¼�����
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
	 * д����Ϸ����
	 */
	private boolean writeConfig() {
		HashMap<Integer, String> keySet = new HashMap<Integer, String>();
		for (int i = 0; i < this.texts.length; i++) {
			int keyCode = this.texts[i].getKeyCode();
			if(keyCode == 0) {
				this.errorMsg.setText("���󰴼�");
				return false;
			}
			keySet.put(keyCode, this.texts[i].getMethodName());
		}
		if(keySet.size() != 8) {
			this.errorMsg.setText("�����ظ�");
			return false;
		}
		try {
			//�л�Ƥ��
			Img.setSkin(this.skinData.get(this.skinList.getSelectedIndex()).toString() + "/");
			//д���������
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
