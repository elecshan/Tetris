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
 *������Ҽ�������
 *���ƽ���
 *������Ϸ�߼� 
 * @author xgs
 * @date 2017��8��3�� ����11:07:16
 */
public class GameControl {
	
	//��Ϸ�߼���
	private GameTetris gameService;
	
	//��Ϸ�����
	private JPanelGame panelGame;
	
	//��Ϸ���ƴ���
	private JFrameConfig frameConfig;
	
	//�����������
	private JFrameSavePoint frameSavePoint;
	
	private PlayerControl playerControl;
	
	private Data dataA;
	
	private Data dataB;
	
	//��Ϸ����Դ
	private GameDto dto = null;
	
	private Thread gameThread = null;
	
	private static final String PATH = "data/control.dat";
	
	//��Ϸ��Ϊ���ƶ�Ӧ�б�
	private HashMap<Integer, Method> actionList;
	
	public GameControl() {
		//������Ϸ����Դ
		this.dto = new GameDto();
		//������Ϸ���
		this.panelGame = new JPanelGame(this, dto);
		//������ҿ�������������Ϸ��������
		playerControl = new PlayerControl(this);
		//��װ��ҿ�����
		panelGame.setPlayerControl(playerControl);
		//������Ϸ�߼��飨������Ϸ����Դ��
		this.gameService = new GameTetris(dto);
		//ͨ�����캯����ö���
		this.dataA = (DataBase)creatDataObject(GameConfig.getDATA_CONFIG().getDataA());
		//�����ݿ��������õ���Ϸ��
		this.dto.setDbRecoder(dataA.loadData());
		//ͨ�����캯����ö���
		this.dataB = (DataDisk)creatDataObject(GameConfig.getDATA_CONFIG().getDataB());
		//�������������õ���Ϸ��
		this.dto.setDiskRecoder(dataB.loadData());
		//��ʼ����Ϸ��Ϊ
		this.setControlConfig();
		//��ʼ���û���������
		this.frameConfig = new JFrameConfig(this);
		//��ʼ�������������
		this.frameSavePoint = new JFrameSavePoint(this);
		//������Ϸ���ڣ���װ��Ϸ���
		new JFrameGame(this.panelGame);
	}

	/**
	 * �����û���������
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
	 * �������ݶ���
	 * @param cfg
	 * @return
	 */
	private Data creatDataObject(DataInterfaceConfig cfg) {
		try {
			//��������
			Class<?> cls = Class.forName(cfg.getClassName());
			//��ù��캯��
			Constructor<?> ctr = cls.getConstructor(HashMap.class);
			//ͨ����������������
			return (Data)ctr.newInstance(cfg.getParamMap());
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * �������������п���
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
	 * ��ʾ��ҿ��ƴ���
	 */
	public void showUserConfig() {
		this.frameConfig.setVisible(true);
	}

	/**
	 * �Ӵ��ڹر��¼�
	 */
	public void setOver() {
		this.panelGame.repaint();
		this.setControlConfig();
	}

	/**
	 * ��ʼ�����¼�
	 */
	public void start() {
		//��尴������Ϊ���ɵ��
		this.panelGame.buttonSwitch(false);
		//�رմ���
		this.frameConfig.setVisible(false);
		this.frameSavePoint.setVisible(false);
		//��Ϸ���ݳ�ʼ��
		this.gameService.startGame();
		//ˢ�»���
		this.panelGame.repaint();
		//�����̶߳���
		this.gameThread = new MainThread();
		//�����߳�
		this.gameThread.start();
		//ˢ�»���
		this.panelGame.repaint();
	}
	
	/**
	 * �������
	 * @param nameString
	 */
	public void savePoint(String nameString) {
		Player player = new Player(nameString, this.dto.getNowPoint());
		this.dataA.saveData(player);
		//�����ݿ��������õ���Ϸ��
		this.dto.setDbRecoder(dataA.loadData());
		this.dataB.saveData(player);
		//�������������õ���Ϸ��
		this.dto.setDiskRecoder(dataB.loadData());
		//ˢ�»���
		this.panelGame.repaint();
	}
	
	/**
	 * ʧ�ܺ�Ĳ���
	 */
	public void afterLose() {
		if(!this.dto.isCheat()) {
			//��ʾ����÷ִ���
			this.frameSavePoint.show(this.dto.getNowPoint());
		}
		//ʹ�������Ե��
		this.panelGame.buttonSwitch(true);
	}
	
	/**
	 * ˢ�»���
	 */
	public void repaint() {
		this.panelGame.repaint();
	}
	
	private class MainThread extends Thread {
		@Override
		public void run() {
			while(dto.isStart()) {
				try {
					//�ȴ�
					Thread.sleep(dto.getSleepTime());
					//�����ͣ����ִ������Ϊ
					if(dto.isPause()) {
						continue;
					}
					//��Ϸ����Ϊ����������
					gameService.mainAction();
					//ˢ�·���
					panelGame.repaint();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			afterLose();
		}
	}
}
