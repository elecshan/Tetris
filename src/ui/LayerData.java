package ui;

import java.awt.Graphics;
import java.awt.Image;
import java.util.List;

import config.GameConfig;
import dto.Player;

public abstract class LayerData extends Layer {

	//�������λ��
	private static final int MAX_RAW = GameConfig.getDATA_CONFIG().getMaxRaw();
		
	//��ʼy����
	private static int START_Y = 0;
		
	//ֵ�ۼ��
	private static int SPA = 0;
		
	private static final int RECT_H = RECT_IMG_H + 4;
	
	@SuppressWarnings("static-access")
	public LayerData(int x, int y, int w, int h) {
		super(x, y, w, h);
		this.SPA = (this.h - (PADDING << 1) - this.RECT_H * this.MAX_RAW - Img.DB.getHeight(null)) / this.MAX_RAW;
		this.START_Y = PADDING + Img.DB.getHeight(null) + this.SPA;
	}

	/**
	 * ����ֵ��
	 * @param imgTitle ����ͼƬ
	 * @param players ��Ҫ��ʾ������
	 * @param g
	 */
	protected void showData(Image imgTitle, List<Player> players, Graphics g) {
		//���Ʊ���
		g.drawImage(imgTitle, this.x + PADDING, this.y + PADDING, null);
		//��ȡ���ڵķ���
		int nowPoint = this.dto.getNowPoint();
		//���Ʒ���ֵ��
		for (int i = 0; i < MAX_RAW; i++) {
			Player player = players.get(i);
			//��ȡ��ʷ����
			int recodePoint = player.getPoint();
			//��ȡ����
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
