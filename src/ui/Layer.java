package ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;

import config.FrameConfig;
import config.GameConfig;
import dto.GameDto;

/**
 * ���ƴ���
 * @author xgs
 * @date 2017��8��2�� ����1:06:59
 */

public abstract class Layer {

	protected static final int PADDING;
	
	//�߿���
	protected static final int BORDER;
	
	static{
		//��ȡ��Ϸ��������
		FrameConfig fConfig = GameConfig.getFRAME_CONFIG();
		PADDING = fConfig.getPadding();
		BORDER = fConfig.getBorder();
	}
	
	//���ڸ߶�
	private static final int WINDOW_H = Img.WINDOW.getHeight(null);
	
	//���ڿ��
	private static final int WINDOW_W = Img.WINDOW.getWidth(null);
	
	//����ͼƬ��260 * 36�����
	protected static final int NUMBER_W = Img.NUMBER.getWidth(null) / 10;
		
	//����ͼƬ��260 * 36���߶�
	private static final int NUMBER_H = Img.NUMBER.getHeight(null);
		
	//����ֵͼƬ�߶�
	protected static final int RECT_IMG_H = Img.RECT.getHeight(null);
		
	//����ֵͼƬ���
	private static final int RECT_IMG_W = Img.RECT.getWidth(null);
		
	//����ֵ�۸߶�
	private static final int RECT_H = 32;
	
	//��������
	private static final Font DE_FONT = new Font("����", Font.BOLD, 20);
		
	//����ֵ�ۿ��
	private int expW;
	
	//�������Ͻ�x����
	protected int x;
	//�������Ͻ�y����	
	protected int y;
	//���ڿ��
	protected int w;
	//���ڸ߶�
	protected int h;
	
	//��Ϸ����
	protected GameDto dto = null;
	
	protected Layer(int x, int y, int w, int h) {
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		this.expW = this.w - (PADDING << 1);
	}
	
	//���ƴ���
	protected void createWindow(Graphics g) {
		
		//���ϡ��ϡ����ϡ������¡��ҡ����¡���
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
	 * ��ʾ����
	 * @param x ���Ͻ�x����
	 * @param y ���Ͻ�y����
	 * @param num Ҫ��ʾ������
	 * @param maxBit �����ʾλ��
	 * @param g ���ʶ���
	 */
	protected void drawNumberLeftPad(int x, int y, int num, int maxBit, Graphics g) {
		String strNum = Integer.toString(num);
		//ѭ�������Ҷ�������
		for (int i = 0; i < maxBit; i++) {
			if(maxBit - i <= strNum.length()) {
				//����������ַ����е��±�
				int index = strNum.length() - maxBit + i;
				//������number�е�ÿһλȡ��
				int bit = strNum.charAt(index) - '0';
				g.drawImage(Img.NUMBER,
						this.x + x + NUMBER_W * i, this.y + y, 
						this.x + x + NUMBER_W * (i + 1), this.y + y + NUMBER_H, 
						bit * NUMBER_W, 0, (bit + 1) * NUMBER_W, NUMBER_H, null);
			}
		}
	}
	
	/**
	 * ���ƾ���ͼ��
	 * @param y ͼ�����Ͻ�y����
	 * @param title ͼ�۵ı���
	 * @param number 
	 * @param value 
	 * @param maxValue
	 * @param g
	 */
	protected void drawRect(int y, String title, String number, double percent, Graphics g) {
		//��ʼ������ֵ�����Ͻ�����
		int rect_x = this.x + PADDING;
		int rect_y = this.y + y;
		//���ƾ���ֵ�۱���
		g.setColor(Color.BLACK);
		g.fillRect(rect_x, rect_y, this.expW, RECT_H);
		g.setColor(Color.WHITE);
		g.fillRect(rect_x + 1, rect_y + 1, this.expW - 2, RECT_H - 2);
		g.setColor(Color.BLACK);
		g.fillRect(rect_x + 2, rect_y + 2, this.expW - 4, RECT_H - 4);
		//���㾭��ֵ��Ӧ���
		int w = (int) (percent * (this.expW - 4));
		//�������ͼƬ�Ŀ��
		int subIndex = (int) (percent * RECT_IMG_W) - 1;
		//���ƾ���ֵ��
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
	 * ���л�ͼ
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
	 * ˢ����Ϸ���崰��
	 * @author xgs
	 * @param g
	 */
	abstract public void paint(Graphics g);
	
}
