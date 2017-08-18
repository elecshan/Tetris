package ui;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JPanel;

import config.FrameConfig;
import config.GameConfig;
import config.LayerConfig;
import control.GameControl;
import control.PlayerControl;
import dto.GameDto;

@SuppressWarnings("serial")
public class JPanelGame extends JPanel {
	
	private List<Layer> layers = null;
	
	//��ʼ����
	private JButton btnStart;
	
	//���ð���
	private JButton btnConfig;
	
	//��Ϸ������
	private GameControl gameControl;
	
	private static final int BTN_SIZE_W = GameConfig.getFRAME_CONFIG().getButtonConfig().getButtonW();

	private static final int BTN_SIZE_H = GameConfig.getFRAME_CONFIG().getButtonConfig().getButtonH();

	public JPanelGame(GameControl gameControl, GameDto dto) {
		//���ò��ֹ�����Ϊ���ɲ���
		this.setLayout(null);
		//����Ϸ������������Ϸ���
		this.gameControl = gameControl;
		//��ʼ�����
		initComponent();
		//��ʼ����
		initLayer(dto);
	}

	/**
	 * ��װ��ҿ�����
	 */
	public void setPlayerControl(PlayerControl control) {
		this.addKeyListener(control);
	} 
	
	/**
	 *��ʼ����� 
	 */
	private void initComponent() {
		//��ʼ����ʼ����
		this.btnStart = new JButton(Img.BTN_START);
		this.btnStart.setBounds(
				GameConfig.getFRAME_CONFIG().getButtonConfig().getStartX(), 
				GameConfig.getFRAME_CONFIG().getButtonConfig().getStartY(),
				BTN_SIZE_W, BTN_SIZE_H);
		//����ʼ��������¼�����
		this.btnStart.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				gameControl.start();
				//���ؽ���
				requestFocus();
			}
		});
		this.add(btnStart);
		//��ʼ�����ð���
		this.btnConfig = new JButton(Img.BTN_CONFIG);
		this.btnConfig.setBounds(
				GameConfig.getFRAME_CONFIG().getButtonConfig().getUserConfigX(), 
				GameConfig.getFRAME_CONFIG().getButtonConfig().getUserConfigY(),
				BTN_SIZE_W, BTN_SIZE_H);
		//�����ð��������¼�����
		this.btnConfig.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				gameControl.showUserConfig();
			}
		});
		this.add(btnConfig);
	}
	
	/**
	 * ��ʼ����
	 */
	private void initLayer(GameDto dto) {
		try {
			//�����Ϸ��������
			FrameConfig fConfig = GameConfig.getFRAME_CONFIG();
			//��ò�����
			List<LayerConfig> layersCfg = fConfig.getLayerConfig();
			//������Ϸ������
			layers = new ArrayList<Layer>(layersCfg.size());
			for (LayerConfig layerCfg : layersCfg) {
				//��������
				Class<?> cls = Class.forName(layerCfg.getClassName());
				//��ù��캯��
				Constructor<?> ctr = cls.getConstructor(int.class, int.class, int.class, int.class);
				//ͨ�����캯����ö���
				Layer layer = (Layer)ctr.newInstance(
						layerCfg.getX(),
						layerCfg.getY(),
						layerCfg.getWidth(),
						layerCfg.getHeight());
				//������Ϸ���ݶ���
				layer.setDto(dto);
				//�Ѵ�����layer��������б���
				layers.add(layer);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * ���ð����Ƿ����
	 * @param onOff
	 */
	public void buttonSwitch(boolean onOff) {
		this.btnConfig.setEnabled(onOff);
		this.btnStart.setEnabled(onOff);
	}
	
	@Override
	public void paintComponent(Graphics g) {
		//���û��෽��
		super.paintComponent(g);
		//ˢ����Ϸ����
		for (Layer layer : layers) {
			//ˢ�²㴰��
			layer.paint(g);
		}
	}
}
