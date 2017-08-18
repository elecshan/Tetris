package entity;

import java.awt.Point;
import java.util.List;

import config.GameConfig;

public class GameAct {
	
	//方块数组
	private Point[] actPoints = null;

	//方块编码
	private int typeCode; 
	
	private static final int MIN_X = GameConfig.getSYSTEM_CONFIG().getMinX();
	private static final int MAX_X = GameConfig.getSYSTEM_CONFIG().getMaxX();
	private static final int MIN_Y = GameConfig.getSYSTEM_CONFIG().getMinY();
	private static final int MAX_Y = GameConfig.getSYSTEM_CONFIG().getMaxY();
	
	//获得方块初始坐标
	private static final List<Point[]> TYPE_CONFIG = GameConfig.getSYSTEM_CONFIG().getTypeConfig();
	
	private static final List<Boolean> TYPE_ROUND = GameConfig.getSYSTEM_CONFIG().getTypeRound();
	
	public GameAct(int typeCode) {
		this.init(typeCode);		
	}
	
	public void init(int typeCode) {
		this.typeCode = typeCode;
		Point[] points = TYPE_CONFIG.get(typeCode);
		actPoints = new Point[points.length];
		for (int i = 0; i < points.length; i++) {
			actPoints[i] = new Point(points[i].x, points[i].y);
		}
	}

	public Point[] getActPoints() {
		return actPoints;
	}
	
	/**
	 * 方块移动
	 * @param moveX X轴偏移量
	 * @param moveY Y轴偏移量
	 */
	public boolean move(int moveX, int moveY, boolean[][] gameMap) {
		for (int i = 0; i < actPoints.length; i++) {
			int newX = actPoints[i].x + moveX;
			int newY = actPoints[i].y + moveY;
			if(this.isOverZone(newX, newY, gameMap)) {
				return false;
			}
		}
		for (int i = 0; i < actPoints.length; i++) {
			actPoints[i].x += moveX;
			actPoints[i].y += moveY;
		}
		return true;
	}
	
	/**
	 * 方块移动
	 * 
	 * 顺时针
	 * A.x = O.y + O.x - B.y
	 * A.y = O.y - O.x + B.x
	 */
	@SuppressWarnings("static-access")
	public void round(boolean[][] gameMap) {
		//TODO 配置文件
		if(!this.TYPE_ROUND.get(this.typeCode)) {
			return;
		}
		for (int i = 1; i < actPoints.length; i++) {
			int newX = actPoints[0].y + actPoints[0].x - actPoints[i].y;
			int newY = actPoints[0].y - actPoints[0].x + actPoints[i].x;
			if(this.isOverZone(newX, newY, gameMap)) {
				return;
			}
		}
		for (int i = 1; i < actPoints.length; i++) {
			int newX = actPoints[0].y + actPoints[0].x - actPoints[i].y;
			int newY = actPoints[0].y - actPoints[0].x + actPoints[i].x;
			actPoints[i].x = newX;
			actPoints[i].y = newY;
		}
	} 
	
	/**
	 * 判断是否超出边界
	 * @param x
	 * @param y
	 * @return
	 */
	private boolean isOverZone(int x, int y, boolean[][] gameMap) {
		return x < MIN_X || x > MAX_X || y < MIN_Y || y > MAX_Y || gameMap[x][y];
	}

	public int getTypeCode() {
		return typeCode;
	} 
	
}
