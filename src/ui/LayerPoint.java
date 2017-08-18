package ui;

import java.awt.Graphics;

import config.GameConfig;

public class LayerPoint extends Layer {

	//�������λ��
	private static final int POINT_BIT = 5;
	
	//������ʾ��y����
	private final int pointY;
	
	//������ʾ��y����
	private final int rmLineY;
	
	//������ʾ��x����
	private int comX= 0;
	
	//����ֵ��y����
	private int expY;
	
	//������Ҫ����������
	private static final int LEVEL_UP = GameConfig.getSYSTEM_CONFIG().getLevelUp();
	
	@SuppressWarnings("static-access")
	public LayerPoint(int x, int y, int w, int h) {
		super(x, y, w, h);
		//��ʼ��������ʾ��x����
		this.comX= this.w - this.NUMBER_W * POINT_BIT -PADDING;
		this.pointY = PADDING;
		this.rmLineY = this.pointY + Img.POINT.getHeight(null) + PADDING;
		this.expY = this.rmLineY + Img.RMLINE.getHeight(null) + PADDING;
	}
	
	public void paint(Graphics g) {
		this.createWindow(g);
		//���ڱ��⣨������
		g.drawImage(Img.POINT, this.x + PADDING, this.y + pointY, null);
		this.drawNumberLeftPad(comX, pointY, this.dto.getNowPoint(), POINT_BIT, g);
		//���ڱ��⣨���У�
		g.drawImage(Img.RMLINE, this.x + PADDING, this.y + rmLineY, null);
		this.drawNumberLeftPad(comX, rmLineY, this.dto.getNowRemoveLine(), POINT_BIT, g);
		//����ֵ��
		int rmLine = this.dto.getNowRemoveLine();
		this.drawRect(expY, "��һ��", null, (double)(rmLine % LEVEL_UP) / (double)(LEVEL_UP), g);
	}

}
