package ui;

import java.awt.Graphics;
import java.awt.Point;

import config.GameConfig;
import entity.GameAct;

public class LayerGame extends Layer {
	
	//方块大小
	private static final int SIZE_ROL = GameConfig.getFRAME_CONFIG().getSizeRol();
	
	private static final int LEFT_SIZE = GameConfig.getSYSTEM_CONFIG().getMinX();
	
	private static final int RIGHT_SIZE = GameConfig.getSYSTEM_CONFIG().getMaxX();
	
	private static final int LOSE_IDX = GameConfig.getFRAME_CONFIG().getLoseIdx();
	
	public LayerGame(int x, int y, int w, int h) {
		super(x, y, w, h);
	}
	
	public void paint(Graphics g) {
		this.createWindow(g);
		GameAct act = this.dto.getGameAct();
		if(act != null) {
			//获得方块数组
			Point[] points = act.getActPoints();
			//绘制阴影
			this.drawShadow(points, g);
			//绘制活动方块
			this.drawMainAct(points, g);
		}
		//绘制地图
		this.drawMap(g);
		if(this.dto.isPause()) {
			this.drawImagAtCenter(Img.PAUSE, g);
		}
	}
	
	/**
	 * 绘制地图
	 * @param g
	 */
	private void drawMap(Graphics g) {
		boolean[][] map = this.dto.getGameMap();
		//计算当前堆积方块颜色
		int level = this.dto.getLevel();
		int imgIndex = level == 0 ? 0 : (level - 1) % GameConfig.getSYSTEM_CONFIG().getTypeConfig().size() + 1;
		//输的情况
		if(!this.dto.isStart()) {
			imgIndex = LOSE_IDX;
		}
		for (int x = 0; x < map.length; x++) {
			for (int y = 0; y < map[x].length; y++) {
				if(map[x][y]) {
					this.drawActByPoint(x, y, imgIndex, g);
				}
			}
		}
	}

	/**
	 * 绘制活动方块
	 * @param g
	 */
	private void drawMainAct(Point[] points, Graphics g) {
		//获得方块类型编号
		int actCode = this.dto.getGameAct().getTypeCode();
		actCode = this.dto.isStart() ? actCode : LOSE_IDX - 1;
		//打印方块
		for (int i = 0; i < points.length; i++) {
			this.drawActByPoint(points[i].x, points[i].y, actCode + 1, g);
		}
	}

	/**
	 * 绘制阴影
	 * @param points
	 * @param isShowShadow
	 * @param g
	 */
	private void drawShadow(Point[] points, Graphics g) {
		if(!this.dto.isShowShadow()) {
			return;
		}
		int leftX = RIGHT_SIZE;
		int rightX = LEFT_SIZE;
		for (Point point : points) {
			leftX = point.x > leftX ? leftX : point.x;
			rightX = point.x > rightX ? point.x : rightX;
		}
		g.drawImage(Img.SHADOW,
				this.x + BORDER + leftX * (1 << SIZE_ROL), this.y + BORDER, 
				(rightX - leftX + 1) * (1 << SIZE_ROL), this.h - (BORDER << 1), null); 
	}

	/**
	 * 绘制方块
	 * @param x
	 * @param y
	 * @param ImgIndex
	 * @param g
	 */
	private void drawActByPoint(int x, int y, int ImgIndex, Graphics g) {
		g.drawImage(Img.ACT, 
				this.x + (x << SIZE_ROL) + BORDER, 
				this.y + (y << SIZE_ROL) + BORDER, 
				this.x + (x + 1 << SIZE_ROL) + BORDER, 
				this.y + (y + 1 << SIZE_ROL) + BORDER,
				ImgIndex << SIZE_ROL, 0, (ImgIndex + 1) << SIZE_ROL, 1 << SIZE_ROL, null);
	}

}
