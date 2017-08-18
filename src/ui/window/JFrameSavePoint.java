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
	
	//确定按键
	private JButton btnOk = null;
	
	//分数文本框
	private JLabel lbPoint = null;
	
	//名字输入框
	private JTextField name = null;
	
	//错误信息
	private JLabel errorMsg = null;
	
	public JFrameSavePoint(GameControl gameControl) {
		this.gameControl = gameControl;
		//设置标题
		this.setTitle("保存分数");
		//设置窗口大小
		this.setSize(256,128);
		//设置不可更改大小
		this.setResizable(false);
		//设置窗口居中显示
		FrameUtil.setFrameCenter(this);
		//设置布局管理器为边界布局
		this.setLayout(new BorderLayout());
		//创建窗口布局
		this.creatCom();
		this.creatAction();
	}
	
	/**
	 * 显示分数
	 */
	public void show(int point) {
		this.lbPoint.setText("您的得分：" + point);
		this.setVisible(true);
	}
	
	/**
	 * 创建事件监听
	 */
	private void creatAction() {
		this.btnOk.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String nameString = name.getText();
				if(nameString.length() > 16 || nameString == null || nameString.equals("")) {
					errorMsg.setText("名字输入错误");
				} else {
					gameControl.savePoint(nameString);
					setVisible(false);
				}
			}
		});
	}

	/**
	 * 初始化控件
	 */
	private void creatCom() {
		//创建错误信息空间
		this.errorMsg = new JLabel();
		this.errorMsg.setForeground(Color.RED);
		//创建显示分数文本框
		JPanel north = new JPanel(new FlowLayout(FlowLayout.LEFT));
		this.lbPoint = new JLabel();
		north.add(this.lbPoint);
		north.add(this.errorMsg);
		this.add(north, BorderLayout.NORTH);
		//创建输入名字文本框
		JPanel center = new JPanel(new FlowLayout(FlowLayout.LEFT));
		this.name = new JTextField(10);
		center.add(new JLabel("输入名字："));
		center.add(this.name);
		this.add(center, BorderLayout.CENTER);
		//创建确定按键
		this.btnOk = new JButton("确定");
		//创建南部面板
		JPanel south = new JPanel(new FlowLayout(FlowLayout.CENTER));
		//将按键添加到面板中
		south.add(this.btnOk);
		this.add(south, BorderLayout.SOUTH);
	}
}
