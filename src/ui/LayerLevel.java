package ui;

import java.awt.Graphics;

public class LayerLevel extends Layer {
	
	private static final int IMG_LEVEL_W = Img.LEVEL.getWidth(null);
	
	public LayerLevel(int x, int y, int w, int h) {
		super(x, y, w, h);
	}
	
	public void paint(Graphics g) {
		this.createWindow(g);
		int centerX = this.w - IMG_LEVEL_W >> 1;
		g.drawImage(Img.LEVEL, this.x + centerX, this.y + PADDING, null);
		this.drawNumberLeftPad(centerX, 58, this.dto.getLevel(), 2, g);
	}
	
}
