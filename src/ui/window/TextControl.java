package ui.window;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JTextField;

@SuppressWarnings("serial")
public class TextControl extends JTextField{
	
	private int keyCode;
	
	private final String methodName;

	public TextControl(int x, int y, int w, int h, String methodName) {
		//设置文本框位置
		this.setBounds(x, y, w, h);
		//初始化方法名
		this.methodName = methodName;
		//增加事件监听
		this.addKeyListener(new KeyListener() {
			public void keyTyped(KeyEvent e) {}
			@Override
			public void keyReleased(KeyEvent e) {
				String str = KeyEvent.getKeyText(e.getKeyCode());
				keyCode = e.getKeyCode();
				setText(str);
			}
			public void keyPressed(KeyEvent e) {}
		});
	}

	public int getKeyCode() {
		return keyCode;
	}

	public void setKeyCode(int keyCode) {
		this.keyCode = keyCode;
		setText(KeyEvent.getKeyText(this.keyCode));
	}

	public String getMethodName() {
		return methodName;
	}
	
}
