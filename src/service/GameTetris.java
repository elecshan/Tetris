package service;

import java.awt.Point;
import java.util.HashMap;
import java.util.Random;

import config.GameConfig;
import dto.GameDto;
import entity.GameAct;

public class GameTetris implements GameService{

	//游戏数据对象
	private GameDto dto;
	
	//随机数生成器
	private Random random = new Random();
	
	//升级需要消除的行数
	private final int levelUp = GameConfig.getSYSTEM_CONFIG().getLevelUp();
	
	//消行分数
	private static final HashMap<Integer, Integer> PLUS_POINT = GameConfig.getSYSTEM_CONFIG().getPointsMap();
	
	//方块种类个数
	private static final int MAX_TYPE = GameConfig.getSYSTEM_CONFIG().getTypeConfig().size();
	
	public GameTetris(GameDto dto) {
		this.dto = dto;
	}
	
	/**
	 * 方块操作（上）
	 */
	public boolean keyUp() {
		if(this.dto.isPause()) {
			return true;
		}
		synchronized (this.dto) {
			this.dto.getGameAct().round(this.dto.getGameMap());
		}
		return true;
	}
	
	/**
	 * 方块操作（下）
	 */
	public boolean keyDown() {
		if(this.dto.isPause()) {
			return true;
		}
		synchronized (this.dto) {
			//判断方块是否移动到底部
			if(this.dto.getGameAct().move(0, 1, this.dto.getGameMap())) {
				return false;
			}
			//获得游戏地图对象
			boolean[][] map = this.dto.getGameMap();
			//获得方块对象
			Point[] act = this.dto.getGameAct().getActPoints();
			//将方块堆积到地图对象上
			for (int i = 0; i < act.length; i++) {
				map[act[i].x][act[i].y] = true;
			}
			//判断消行，获得消除的行数
			int exp = this.plusExp();
			//加分操作
			this.plusPoint(exp);
			//创建下一个方块
			this.dto.getGameAct().init(this.dto.getNext());
			//随机生成再下一个方块
			this.dto.setNext(random.nextInt(MAX_TYPE));
			//检查游戏是否结束
			if(this.isLose()) {
				//结束游戏
				this.dto.setStart(false);
			}
		}
		return true;
	}

	/**
	 * 方块操作（左）
	 */
	public boolean keyLeft() {
		if(this.dto.isPause()) {
			return true;
		}
		synchronized (this.dto) {
			this.dto.getGameAct().move(-1, 0, this.dto.getGameMap());
		}
		return true;
	}

	/**
	 * 方块操作（右）
	 */
	public boolean keyRight() {
		if(this.dto.isPause()) {
			return true;
		}
		synchronized (this.dto) {
			this.dto.getGameAct().move(1, 0, this.dto.getGameMap());
		}
		return true;
	}

	/**
	 * 作弊按键
	 */
	@Override
	public boolean keyFunUp() {
		this.dto.setCheat(true);
		this.plusPoint(4);
		return true;
	}

	/**
	 * 瞬间下落
	 */
	@Override
	public boolean keyFunDown() {
		if(this.dto.isPause()) {
			return true;
		}
		while(!this.keyDown());
		return true;
	}

	/**
	 * 阴影开关
	 */
	@Override
	public boolean keyFunLeft() {
		this.dto.setShowShadow(!this.dto.isShowShadow());
		return true;
	}

	/**
	 * 暂停开关
	 */
	@Override
	public boolean keyFunRight() {
		if(this.dto.isStart()) {
			this.dto.setPause(!this.dto.isPause());
		}
		return true;
	}
	
	/**
	 * 消行操作
	 */
	private int plusExp() {
		int exp = 0;
		boolean[][] map = this.dto.getGameMap();
		//扫描地图，查看是否有可消行
		for (int y = 0; y < GameDto.GAMEZONE_H; y++) {
			//判断是否可以消行
			if(this.isCanRemoveLine(y, map)) {
				this.removeLine(y, map);
				exp++;
			}
		}
		return exp;
	}
	
	/**
	 * 消行处理
	 * @param y
	 * @param map
	 */
	private void removeLine(int rawNumber, boolean[][] map) {
		for (int x = 0; x < GameDto.GAMEZONE_W; x++) {
			for (int y = rawNumber; y > 0; y--) {
				map[x][y] = map[x][y - 1];
			}
			map[x][0] = false;
		}
	}

	/**
	 * 判断某一行是否可以消行
	 * @param y
	 * @param map
	 * @return
	 */
	private boolean isCanRemoveLine(int y, boolean[][] map) {
		for (int x = 0; x < GameDto.GAMEZONE_W; x++) {
			if(!map[x][y]) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * 加分操作
	 * @param exp
	 */
	@SuppressWarnings("static-access")
	private void plusPoint(int exp) {
		int level = this.dto.getLevel();
		int rmLine = this.dto.getNowRemoveLine();
		int point = this.dto.getNowPoint();
		
		if((rmLine % this.levelUp + exp) >= this.levelUp) {
			this.dto.setLevel(++level);
		}
		this.dto.setNowRemoveLine(rmLine + exp);
		this.dto.setNowPoint(point + this.PLUS_POINT.get(exp));
	}

	private boolean isLose() {
		Point[] actPoints = this.dto.getGameAct().getActPoints();
		boolean[][] map = this.dto.getGameMap();
		for (int i = 0; i < actPoints.length; i++) {
			if (map[actPoints[i].x][actPoints[i].y]) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 游戏数据初始化
	 */
	@Override
	public void startGame() {
		//随机生成下一个方块
		this.dto.setNext(random.nextInt(MAX_TYPE));
		//随机生成现在显示的方块
		this.dto.setGameAct(new GameAct(random.nextInt(MAX_TYPE)));
		//把游戏状态设为开始
		this.dto.setStart(true);
		//初始化dto
		this.dto.dtoInit();
	}

	/**
	 * 游戏主行为
	 */
	@Override
	public void mainAction() {
		this.keyDown();
	}
}
