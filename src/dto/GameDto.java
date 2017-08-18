package dto;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import config.GameConfig;
import entity.GameAct;
import util.GameFunction;

public class GameDto {
	
	public static final int GAMEZONE_W = GameConfig.getSYSTEM_CONFIG().getMaxX() + 1;
	
	public static final int GAMEZONE_H = GameConfig.getSYSTEM_CONFIG().getMaxY() + 1;
	/**
	 * ���ݿ��¼
	 */
	private List<Player> dbRecoder;
	
	/**
	 * ���ؼ�¼
	 */
	private List<Player> diskRecoder;
	
	/**
	 * ��Ϸ��ͼ
	 */
	private boolean[][] gameMap;
	
	/**
	 * ���䷽��
	 */
	private GameAct gameAct;
	
	/**
	 * ��һ������
	 */
	private int next;
	
	/**
	 * �ȼ�
	 */
	private int level;
	
	/**
	 * �������÷���
	 */
	private int nowPoint;
	
	/**
	 * ����������
	 */
	private int nowRemoveLine;

	/**
	 * ��Ϸ�Ƿ��ڿ�ʼ״̬
	 */
	private boolean start;
	
	/**
	 * �Ƿ���ʾ��Ӱ
	 */
	private boolean showShadow;
	
	/**
	 * ��ͣ��־
	 */
	private boolean pause;
	
	/**
	 * �Ƿ�ʹ������
	 */
	private boolean cheat = false;
	
	/**
	 * ��������ȴ�ʱ��
	 */
	private long sleepTime;
	
	public GameDto() {
		dtoInit();
	}
	
	public void dtoInit() {
		this.gameMap = new boolean[GAMEZONE_W][GAMEZONE_H];
		this.level = 0;
		this.nowPoint = 0;
		this.nowRemoveLine = 0;
		this.pause = false;
		this.sleepTime = GameFunction.getSleepTimeByLevel(this.level);
	} 
	
	public List<Player> getDbRecoder() {
		return dbRecoder;
	}

	public void setDbRecoder(List<Player> dbRecoder) {
		this.dbRecoder = this.setFillRecode(dbRecoder);
	}

	public List<Player> getDiskRecoder() {
		return diskRecoder;
	}

	public void setDiskRecoder(List<Player> diskRecoder) {
		this.diskRecoder = this.setFillRecode(diskRecoder);;
	}

	public boolean[][] getGameMap() {
		return gameMap;
	}

	public void setGameMap(boolean[][] gameMap) {
		this.gameMap = gameMap;
	}

	public GameAct getGameAct() {
		return gameAct;
	}

	public void setGameAct(GameAct gameAct) {
		this.gameAct = gameAct;
	}

	public int getNext() {
		return next;
	}

	public void setNext(int next) {
		this.next = next;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
		//���㷽������ȴ�ʱ��
		this.sleepTime = GameFunction.getSleepTimeByLevel(this.level);
	}

	public int getNowPoint() {
		return nowPoint;
	}

	public void setNowPoint(int nowPoint) {
		this.nowPoint = nowPoint;
	}

	public int getNowRemoveLine() {
		return nowRemoveLine;
	}

	public void setNowRemoveLine(int nowRemoveLine) {
		this.nowRemoveLine = nowRemoveLine;
	}
	
	private List<Player> setFillRecode(List<Player> players) {
		if(players == null) {
			players = new ArrayList<Player>();
		}
		while(players.size() < 5) {
			players.add(new Player("No Data", 0));
		}
		Collections.sort(players);
		return players;
	}

	public boolean isStart() {
		return start;
	}

	public void setStart(boolean start) {
		this.start = start;
	}

	public boolean isShowShadow() {
		return showShadow;
	}

	public void setShowShadow(boolean showShadow) {
		this.showShadow = showShadow;
	}

	public boolean isPause() {
		return pause;
	}

	public void setPause(boolean pause) {
		this.pause = pause;
	}

	public boolean isCheat() {
		return cheat;
	}

	public void setCheat(boolean cheat) {
		this.cheat = cheat;
	}

	public long getSleepTime() {
		return sleepTime;
	}
	
}
