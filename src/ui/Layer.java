package ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;

import config.FrameConfig;
import config.GameConfig;
import dto.GameDto;

/**
 * 绘制窗口
 * @author xgs
 * @date 2017年8月2日 下午1:06:59
 */

public abstract class Layer {

	protected static final int PADDING;
	
	//边框宽度
	protected static final int BORDER;
	
	static{
		//获取游戏界面配置
		FrameConfig fConfig = GameConfig.getFRAME_CONFIG();
		PADDING = fConfig.getPadding();
		BORDER = fConfig.getBorder();
	}
	
	//窗口高度
	private static final int WINDOW_H = Img.WINDOW.getHeight(null);
	
	//窗口宽度
	private static final int WINDOW_W = Img.WINDOW.getWidth(null);
	
	//数字图片（260 * 36）宽度
	protected static final int NUMBER_W = Img.NUMBER.getWidth(null) / 10;
		
	//数字图片（260 * 36）高度
	private static final int NUMBER_H = Img.NUMBER.getHeight(null);
		
	//经验值图片高度
	protected static final int RECT_IMG_H = Img.RECT.getHeight(null);
		
	//经验值图片宽度
	private static final int RECT_IMG_W = Img.RECT.getWidth(null);
		
	//经验值槽高度
	private static final int RECT_H = 32;
	
	//设置字体
	private static final Font DE_FONT = new Font("黑体", Font.BOLD, 20);
		
	//经验值槽宽度
	private int expW;
	
	//窗口左上角x坐标
	protected int x;
	//窗口左上角y坐标	
	protected int y;
	//窗口宽度
	protected int w;
	//窗口高度
	protected int h;
	
	//游戏数据
	protected GameDto dto = null;
	
	protected Layer(int x, int y, int w, int h) {
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		this.expW = this.w - (PADDING << 1);
	}
	
	//绘制窗口
	protected void createWindow(Graphics g) {
		
		//左上、上、右上、左、左下、右、右下、下
		g.drawImage(Img.WINDOW, x, y, x + BORDER, y + BORDER, 0, 0, BORDER, BORDER, null);
		g.drawImage(Img.WINDOW, x + BORDER, y, x + w - BORDER, y + BORDER, BORDER, 0, WINDOW_W - BORDER, BORDER, null);
		g.drawImage(Img.WINDOW, x + w - BORDER, y, x + w, y + BORDER, WINDOW_W - BORDER, 0, WINDOW_W, BORDER, null);
		g.drawImage(Img.WINDOW, x, y + BORDER, x + BORDER, y + h - BORDER, 0, BORDER, BORDER, WINDOW_H - BORDER, null);
		g.drawImage(Img.WINDOW, x, y + h - BORDER, x + BORDER, y + h, 0, WINDOW_H - BORDER, BORDER, WINDOW_H, null);
		g.drawImage(Img.WINDOW, x + w - BORDER, y + BORDER, x + w, y + h - BORDER, WINDOW_W - BORDER, BORDER, WINDOW_W, WINDOW_H - BORDER, null);
		g.drawImage(Img.WINDOW, x + w - BORDER, y + h - BORDER, x + w, y + h, WINDOW_W - BORDER, WINDOW_H - BORDER, WINDOW_W, WINDOW_H, null);
		g.drawImage(Img.WINDOW, x + BORDER, y + h - BORDER, x + w - BORDER, y + h, BORDER, WINDOW_H - BORDER, WINDOW_W - BORDER, WINDOW_H, null);
	}

	public void setDto(GameDto dto) {
		this.dto = dto;
	}
	
	/**
	 * 显示数字
	 * @param x 左上角x坐标
	 * @param y 左上角y坐标
	 * @param num 要显示的数字
	 * @param maxBit 最大显示位数
	 * @param g 画笔对象
	 */
	protected void drawNumberLeftPad(int x, int y, int num, int maxBit, Graphics g) {
		String strNum = Integer.toString(num);
		//循环绘制右对齐数字
		for (int i = 0; i < maxBit; i++) {
			if(maxBit - i <= strNum.length()) {
				//获得数字在字符串中的下标
				int index = strNum.length() - maxBit + i;
				//把数字number中的每一位取出
				int bit = strNum.charAt(index) - '0';
				g.drawImage(Img.NUMBER,
						this.x + x + NUMBER_W * i, this.y + y, 
						this.x + x + NUMBER_W * (i + 1), this.y + y + NUMBER_H, 
						bit * NUMBER_W, 0, (bit + 1) * NUMBER_W, NUMBER_H, null);
			}
		}
	}
	
	/**
	 * 绘制矩形图槽
	 * @param y 图槽左上角y坐标
	 * @param title 图槽的标题
	 * @param number 
	 * @param value 
	 * @param maxValue
	 * @param g
	 */
	protected void drawRect(int y, String title, String number, double percent, Graphics g) {
		//初始化经验值槽左上角坐标
		int rect_x = this.x + PADDING;
		int rect_y = this.y + y;
		//绘制经验值槽背景
		g.setColor(Color.BLACK);
		g.fillRect(rect_x, rect_y, this.expW, RECT_H);
		g.setColor(Color.WHITE);
		g.fillRect(rect_x + 1, rect_y + 1, this.expW - 2, RECT_H - 2);
		g.setColor(Color.BLACK);
		g.fillRect(rect_x + 2, rect_y + 2, this.expW - 4, RECT_H - 4);
		//计算经验值对应宽度
		int w = (int) (percent * (this.expW - 4));
		//计算绘制图片的宽度
		int subIndex = (int) (percent * RECT_IMG_W) - 1;
		//绘制经验值槽
		g.drawImage(Img.RECT,
				rect_x + 3, rect_y + 3,
				rect_x + 3 + w, rect_y + 3 + RECT_IMG_H,
				subIndex, 0, subIndex + 1, RECT_IMG_H,
				null);
		g.setColor(Color.WHITE);
		g.setFont(DE_FONT);
		g.drawString(title, rect_x + 4, rect_y + 22);
		if(number != null) {
			g.drawString(number, rect_x + 232, rect_y + 22);
		}
	}
	
	/**
	 * 正中绘图
	 * @param img
	 * @param g
	 */
	protected void drawImagAtCenter(Image img, Graphics g) {
		int imgW = img.getWidth(null);
		int imgH = img.getHeight(null);
		int imgX = this.x + (this.w - imgW >> 1);
		int imgY = this.y + (this.h - imgH >> 1);
		g.drawImage(img, imgX, imgY, null);
	}
	
	/**
	 * 刷新游戏具体窗口
	 * @author xgs
	 * @param g
	 */
	abstract public void paint(Graphics g);
	
}
