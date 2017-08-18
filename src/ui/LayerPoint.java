package ui;

import java.awt.Graphics;

import config.GameConfig;

public class LayerPoint extends Layer {

	//分数最大位数
	private static final int POINT_BIT = 5;
	
	//分数显示的y坐标
	private final int pointY;
	
	//消行显示的y坐标
	private final int rmLineY;
	
	//分数显示的x坐标
	private int comX= 0;
	
	//经验值槽y坐标
	private int expY;
	
	//升级需要消除的行数
	private static final int LEVEL_UP = GameConfig.getSYSTEM_CONFIG().getLevelUp();
	
	@SuppressWarnings("static-access")
	public LayerPoint(int x, int y, int w, int h) {
		super(x, y, w, h);
		//初始化分数显示的x坐标
		this.comX= this.w - this.NUMBER_W * POINT_BIT -PADDING;
		this.pointY = PADDING;
		this.rmLineY = this.pointY + Img.POINT.getHeight(null) + PADDING;
		this.expY = this.rmLineY + Img.RMLINE.getHeight(null) + PADDING;
	}
	
	public void paint(Graphics g) {
		this.createWindow(g);
		//窗口标题（分数）
		g.drawImage(Img.POINT, this.x + PADDING, this.y + pointY, null);
		this.drawNumberLeftPad(comX, pointY, this.dto.getNowPoint(), POINT_BIT, g);
		//窗口标题（消行）
		g.drawImage(Img.RMLINE, this.x + PADDING, this.y + rmLineY, null);
		this.drawNumberLeftPad(comX, rmLineY, this.dto.getNowRemoveLine(), POINT_BIT, g);
		//绘制值槽
		int rmLine = this.dto.getNowRemoveLine();
		this.drawRect(expY, "下一级", null, (double)(rmLine % LEVEL_UP) / (double)(LEVEL_UP), g);
	}

}
