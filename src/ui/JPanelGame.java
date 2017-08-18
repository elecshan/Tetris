package ui;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JPanel;

import config.FrameConfig;
import config.GameConfig;
import config.LayerConfig;
import control.GameControl;
import control.PlayerControl;
import dto.GameDto;

@SuppressWarnings("serial")
public class JPanelGame extends JPanel {
	
	private List<Layer> layers = null;
	
	//开始按键
	private JButton btnStart;
	
	//设置按键
	private JButton btnConfig;
	
	//游戏控制器
	private GameControl gameControl;
	
	private static final int BTN_SIZE_W = GameConfig.getFRAME_CONFIG().getButtonConfig().getButtonW();

	private static final int BTN_SIZE_H = GameConfig.getFRAME_CONFIG().getButtonConfig().getButtonH();

	public JPanelGame(GameControl gameControl, GameDto dto) {
		//设置布局管理器为自由布局
		this.setLayout(null);
		//将游戏控制器交给游戏面板
		this.gameControl = gameControl;
		//初始化组件
		initComponent();
		//初始化层
		initLayer(dto);
	}

	/**
	 * 安装玩家控制器
	 */
	public void setPlayerControl(PlayerControl control) {
		this.addKeyListener(control);
	} 
	
	/**
	 *初始化组件 
	 */
	private void initComponent() {
		//初始化开始按键
		this.btnStart = new JButton(Img.BTN_START);
		this.btnStart.setBounds(
				GameConfig.getFRAME_CONFIG().getButtonConfig().getStartX(), 
				GameConfig.getFRAME_CONFIG().getButtonConfig().getStartY(),
				BTN_SIZE_W, BTN_SIZE_H);
		//给开始按键添加事件监听
		this.btnStart.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				gameControl.start();
				//返回焦点
				requestFocus();
			}
		});
		this.add(btnStart);
		//初始化设置按键
		this.btnConfig = new JButton(Img.BTN_CONFIG);
		this.btnConfig.setBounds(
				GameConfig.getFRAME_CONFIG().getButtonConfig().getUserConfigX(), 
				GameConfig.getFRAME_CONFIG().getButtonConfig().getUserConfigY(),
				BTN_SIZE_W, BTN_SIZE_H);
		//给设置按键增加事件监听
		this.btnConfig.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				gameControl.showUserConfig();
			}
		});
		this.add(btnConfig);
	}
	
	/**
	 * 初始化层
	 */
	private void initLayer(GameDto dto) {
		try {
			//获得游戏界面配置
			FrameConfig fConfig = GameConfig.getFRAME_CONFIG();
			//获得层配置
			List<LayerConfig> layersCfg = fConfig.getLayerConfig();
			//创建游戏层数组
			layers = new ArrayList<Layer>(layersCfg.size());
			for (LayerConfig layerCfg : layersCfg) {
				//获得类对象
				Class<?> cls = Class.forName(layerCfg.getClassName());
				//获得构造函数
				Constructor<?> ctr = cls.getConstructor(int.class, int.class, int.class, int.class);
				//通过构造函数获得对象
				Layer layer = (Layer)ctr.newInstance(
						layerCfg.getX(),
						layerCfg.getY(),
						layerCfg.getWidth(),
						layerCfg.getHeight());
				//设置游戏数据对象
				layer.setDto(dto);
				//把创建的layer对象放入列表中
				layers.add(layer);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 设置按键是否可用
	 * @param onOff
	 */
	public void buttonSwitch(boolean onOff) {
		this.btnConfig.setEnabled(onOff);
		this.btnStart.setEnabled(onOff);
	}
	
	@Override
	public void paintComponent(Graphics g) {
		//调用基类方法
		super.paintComponent(g);
		//刷新游戏画面
		for (Layer layer : layers) {
			//刷新层窗口
			layer.paint(g);
		}
	}
}
