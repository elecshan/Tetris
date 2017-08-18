package ui.window;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import control.GameControl;
import util.FrameUtil;

@SuppressWarnings("serial")
public class JFrameSavePoint extends JFrame{
	
	private GameControl gameControl;
	
	//ȷ������
	private JButton btnOk = null;
	
	//�����ı���
	private JLabel lbPoint = null;
	
	//���������
	private JTextField name = null;
	
	//������Ϣ
	private JLabel errorMsg = null;
	
	public JFrameSavePoint(GameControl gameControl) {
		this.gameControl = gameControl;
		//���ñ���
		this.setTitle("�������");
		//���ô��ڴ�С
		this.setSize(256,128);
		//���ò��ɸ��Ĵ�С
		this.setResizable(false);
		//���ô��ھ�����ʾ
		FrameUtil.setFrameCenter(this);
		//���ò��ֹ�����Ϊ�߽粼��
		this.setLayout(new BorderLayout());
		//�������ڲ���
		this.creatCom();
		this.creatAction();
	}
	
	/**
	 * ��ʾ����
	 */
	public void show(int point) {
		this.lbPoint.setText("���ĵ÷֣�" + point);
		this.setVisible(true);
	}
	
	/**
	 * �����¼�����
	 */
	private void creatAction() {
		this.btnOk.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String nameString = name.getText();
				if(nameString.length() > 16 || nameString == null || nameString.equals("")) {
					errorMsg.setText("�����������");
				} else {
					gameControl.savePoint(nameString);
					setVisible(false);
				}
			}
		});
	}

	/**
	 * ��ʼ���ؼ�
	 */
	private void creatCom() {
		//����������Ϣ�ռ�
		this.errorMsg = new JLabel();
		this.errorMsg.setForeground(Color.RED);
		//������ʾ�����ı���
		JPanel north = new JPanel(new FlowLayout(FlowLayout.LEFT));
		this.lbPoint = new JLabel();
		north.add(this.lbPoint);
		north.add(this.errorMsg);
		this.add(north, BorderLayout.NORTH);
		//�������������ı���
		JPanel center = new JPanel(new FlowLayout(FlowLayout.LEFT));
		this.name = new JTextField(10);
		center.add(new JLabel("�������֣�"));
		center.add(this.name);
		this.add(center, BorderLayout.CENTER);
		//����ȷ������
		this.btnOk = new JButton("ȷ��");
		//�����ϲ����
		JPanel south = new JPanel(new FlowLayout(FlowLayout.CENTER));
		//��������ӵ������
		south.add(this.btnOk);
		this.add(south, BorderLayout.SOUTH);
	}
}
