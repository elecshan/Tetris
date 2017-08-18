package ui;

import java.awt.Graphics;
import java.awt.Image;
import java.util.List;

import config.GameConfig;
import dto.Player;

public abstract class LayerData extends Layer {

	//最大数字位数
	private static final int MAX_RAW = GameConfig.getDATA_CONFIG().getMaxRaw();
		
	//起始y坐标
	private static int START_Y = 0;
		
	//值槽间距
	private static int SPA = 0;
		
	private static final int RECT_H = RECT_IMG_H + 4;
	
	@SuppressWarnings("static-access")
	public LayerData(int x, int y, int w, int h) {
		super(x, y, w, h);
		this.SPA = (this.h - (PADDING << 1) - this.RECT_H * this.MAX_RAW - Img.DB.getHeight(null)) / this.MAX_RAW;
		this.START_Y = PADDING + Img.DB.getHeight(null) + this.SPA;
	}

	/**
	 * 绘制值槽
	 * @param imgTitle 标题图片
	 * @param players 需要显示的数据
	 * @param g
	 */
	protected void showData(Image imgTitle, List<Player> players, Graphics g) {
		//绘制标题
		g.drawImage(imgTitle, this.x + PADDING, this.y + PADDING, null);
		//获取现在的分数
		int nowPoint = this.dto.getNowPoint();
		//绘制分数值槽
		for (int i = 0; i < MAX_RAW; i++) {
			Player player = players.get(i);
			//获取历史数据
			int recodePoint = player.getPoint();
			//获取比例
			double percent = (double)nowPoint / recodePoint;
			percent = percent > 1 ? 1.0 : percent;
			String recodePointString = recodePoint == 0 ? null : Integer.toString(recodePoint);
			this.drawRect(START_Y + (RECT_H + SPA) * i, 
					player.getName(), recodePointString, 
					percent, g);
		}
	}
	
	@Override
	public abstract void paint(Graphics g);
	
}
