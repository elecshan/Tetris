package ui;

import javax.swing.JFrame;

import config.FrameConfig;
import config.GameConfig;
import util.FrameUtil;

@SuppressWarnings("serial")
public class JFrameGame extends JFrame {

	public JFrameGame(JPanelGame jpanelGame) {
		//�����Ϸ��������
		FrameConfig fConfig = GameConfig.getFRAME_CONFIG();
		//���ñ���
		this.setTitle(fConfig.getTitle());
		//���Ĭ�Ϲر�����(�������)
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//���ô��ڴ�С
		this.setSize(fConfig.getWidth(), fConfig.getHeight());
		//�������û��ı䴰�ڴ�С
		this.setResizable(false);
		//��������
		FrameUtil.setFrameCenter(this);
		//����Ĭ��panel
		this.setContentPane(jpanelGame);
		//Ĭ�ϸô���Ϊ��ʾ
		this.setVisible(true);
	}
}
