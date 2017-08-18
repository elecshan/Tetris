package control;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Set;
import java.util.Map.Entry;

import config.DataInterfaceConfig;
import config.GameConfig;
import dao.Data;
import dao.DataBase;
import dao.DataDisk;
import dto.GameDto;
import dto.Player;
import service.GameTetris;
import ui.JFrameGame;
import ui.JPanelGame;
import ui.window.JFrameConfig;
import ui.window.JFrameSavePoint;

/**
 *接收玩家键盘输入
 *控制界面
 *控制游戏逻辑 
 * @author xgs
 * @date 2017年8月3日 上午11:07:16
 */
public class GameControl {
	
	//游戏逻辑层
	private GameTetris gameService;
	
	//游戏界面层
	private JPanelGame panelGame;
	
	//游戏控制窗口
	private JFrameConfig frameConfig;
	
	//保存分数窗口
	private JFrameSavePoint frameSavePoint;
	
	private PlayerControl playerControl;
	
	private Data dataA;
	
	private Data dataB;
	
	//游戏数据源
	private GameDto dto = null;
	
	private Thread gameThread = null;
	
	private static final String PATH = "data/control.dat";
	
	//游戏行为控制对应列表
	private HashMap<Integer, Method> actionList;
	
	public GameControl() {
		//创建游戏数据源
		this.dto = new GameDto();
		//创建游戏面板
		this.panelGame = new JPanelGame(this, dto);
		//创建玩家控制器（连接游戏控制器）
		playerControl = new PlayerControl(this);
		//安装玩家控制器
		panelGame.setPlayerControl(playerControl);
		//创建游戏逻辑块（连接游戏数据源）
		this.gameService = new GameTetris(dto);
		//通过构造函数获得对象
		this.dataA = (DataBase)creatDataObject(GameConfig.getDATA_CONFIG().getDataA());
		//将数据库数据设置到游戏中
		this.dto.setDbRecoder(dataA.loadData());
		//通过构造函数获得对象
		this.dataB = (DataDisk)creatDataObject(GameConfig.getDATA_CONFIG().getDataB());
		//将本地数据设置到游戏中
		this.dto.setDiskRecoder(dataB.loadData());
		//初始化游戏行为
		this.setControlConfig();
		//初始化用户窗口配置
		this.frameConfig = new JFrameConfig(this);
		//初始化保存分数窗口
		this.frameSavePoint = new JFrameSavePoint(this);
		//创建游戏窗口，安装游戏面板
		new JFrameGame(this.panelGame);
	}

	/**
	 * 设置用户控制配置
	 */
	private void setControlConfig() {
		ObjectInputStream ois;
		try {
			this.actionList = new HashMap<Integer, Method>();
			ois = new ObjectInputStream(new FileInputStream(PATH));
			@SuppressWarnings({ "rawtypes", "unchecked" })
			HashMap<Integer, String> cfgSet = (HashMap)ois.readObject();
			ois.close();
			Set<Entry<Integer, String>> entrySet = cfgSet.entrySet();
			for(Entry<Integer, String> e : entrySet) {
				actionList.put(e.getKey(), this.gameService.getClass().getMethod(e.getValue()));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 创建数据对象
	 * @param cfg
	 * @return
	 */
	private Data creatDataObject(DataInterfaceConfig cfg) {
		try {
			//获得类对象
			Class<?> cls = Class.forName(cfg.getClassName());
			//获得构造函数
			Constructor<?> ctr = cls.getConstructor(HashMap.class);
			//通过构造器获得类对象
			return (Data)ctr.newInstance(cfg.getParamMap());
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 根据玩家输入进行控制
	 * @param keyCode
	 */
	public void actionByKeyCode(int keyCode) {
		try {
			if(this.actionList.containsKey(keyCode)) {
				this.actionList.get(keyCode).invoke(this.gameService);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} 
		this.panelGame.repaint();
	}

	/**
	 * 显示玩家控制窗口
	 */
	public void showUserConfig() {
		this.frameConfig.setVisible(true);
	}

	/**
	 * 子窗口关闭事件
	 */
	public void setOver() {
		this.panelGame.repaint();
		this.setControlConfig();
	}

	/**
	 * 开始按键事件
	 */
	public void start() {
		//面板按键设置为不可点击
		this.panelGame.buttonSwitch(false);
		//关闭窗口
		this.frameConfig.setVisible(false);
		this.frameSavePoint.setVisible(false);
		//游戏数据初始化
		this.gameService.startGame();
		//刷新画面
		this.panelGame.repaint();
		//创建线程对象
		this.gameThread = new MainThread();
		//启动线程
		this.gameThread.start();
		//刷新画面
		this.panelGame.repaint();
	}
	
	/**
	 * 保存分数
	 * @param nameString
	 */
	public void savePoint(String nameString) {
		Player player = new Player(nameString, this.dto.getNowPoint());
		this.dataA.saveData(player);
		//将数据库数据设置到游戏中
		this.dto.setDbRecoder(dataA.loadData());
		this.dataB.saveData(player);
		//将本地数据设置到游戏中
		this.dto.setDiskRecoder(dataB.loadData());
		//刷新画面
		this.panelGame.repaint();
	}
	
	/**
	 * 失败后的操作
	 */
	public void afterLose() {
		if(!this.dto.isCheat()) {
			//显示保存得分窗口
			this.frameSavePoint.show(this.dto.getNowPoint());
		}
		//使按键可以点击
		this.panelGame.buttonSwitch(true);
	}
	
	/**
	 * 刷新画面
	 */
	public void repaint() {
		this.panelGame.repaint();
	}
	
	private class MainThread extends Thread {
		@Override
		public void run() {
			while(dto.isStart()) {
				try {
					//等待
					Thread.sleep(dto.getSleepTime());
					//如果暂停，则不执行主行为
					if(dto.isPause()) {
						continue;
					}
					//游戏主行为：方块下落
					gameService.mainAction();
					//刷新方块
					panelGame.repaint();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			afterLose();
		}
	}
}
