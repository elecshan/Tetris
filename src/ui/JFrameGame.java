package ui;

import javax.swing.JFrame;

import config.FrameConfig;
import config.GameConfig;
import util.FrameUtil;

@SuppressWarnings("serial")
public class JFrameGame extends JFrame {

	public JFrameGame(JPanelGame jpanelGame) {
		//获得游戏界面配置
		FrameConfig fConfig = GameConfig.getFRAME_CONFIG();
		//设置标题
		this.setTitle(fConfig.getTitle());
		//设计默认关闭属性(程序结束)
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//设置窗口大小
		this.setSize(fConfig.getWidth(), fConfig.getHeight());
		//不允许用户改变窗口大小
		this.setResizable(false);
		//居中设置
		FrameUtil.setFrameCenter(this);
		//设置默认panel
		this.setContentPane(jpanelGame);
		//默认该窗口为显示
		this.setVisible(true);
	}
}
