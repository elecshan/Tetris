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
	 * 数据库记录
	 */
	private List<Player> dbRecoder;
	
	/**
	 * 本地记录
	 */
	private List<Player> diskRecoder;
	
	/**
	 * 游戏地图
	 */
	private boolean[][] gameMap;
	
	/**
	 * 下落方块
	 */
	private GameAct gameAct;
	
	/**
	 * 下一个方块
	 */
	private int next;
	
	/**
	 * 等级
	 */
	private int level;
	
	/**
	 * 现在所得分数
	 */
	private int nowPoint;
	
	/**
	 * 已消除行数
	 */
	private int nowRemoveLine;

	/**
	 * 游戏是否处于开始状态
	 */
	private boolean start;
	
	/**
	 * 是否显示阴影
	 */
	private boolean showShadow;
	
	/**
	 * 暂停标志
	 */
	private boolean pause;
	
	/**
	 * 是否使用作弊
	 */
	private boolean cheat = false;
	
	/**
	 * 方块下落等待时间
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
		//计算方块下落等待时间
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
