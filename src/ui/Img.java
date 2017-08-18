package ui;

import java.awt.Image;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;

import config.GameConfig;

public class Img {
	
	//ͼƬ·��
	public static final String GRAPHICS_PATH = "graphics/";

	private static final String BASE_PATH = "default/";
	
	//����ͼƬ
	public static Image WINDOW = null;
	
	//����ͼƬ
	public static Image NUMBER = null;
	
	//����ֵ��
	public static Image RECT = null;
	
	//���ڱ��⣨������
	public static Image SIGN = null;
	
	//���ݿ�ͼƬ
	public static Image DB = null;
	
	//���ش���ͼƬ
	public static Image DISK = null;
	
	//����ͼƬ
	public static Image ACT = null;
	
	//�ȼ�ͼƬ
	public static Image LEVEL = null;
	
	//���ڱ��⣨������
	public static Image POINT = null;
		
	//���ڱ��⣨���У�
	public static Image RMLINE = null;
	
	//��ӰͼƬ
	public static Image SHADOW = null;
	
	//��ͣͼƬ
	public static Image PAUSE = null;
	
	//��ʼ����
	public static ImageIcon BTN_START = null;
	
	//��ʼ����
	public static ImageIcon BTN_CONFIG = null;
	
	//��һ������
	public static Image[] NEXT_ACT = null;
	
	//����ͼƬ�б�
	public static List<Image> BG_LIST = null;
	
	private Img() {}
	
	public static void setSkin(String path) {
		String skinPath = GRAPHICS_PATH + path;
		//����ͼƬ
		WINDOW = new ImageIcon(skinPath + "/window/Window.png").getImage();
		
		//����ͼƬ
		NUMBER = new ImageIcon(skinPath + "/string/num.png").getImage();
		
		//����ֵ��
		RECT = new ImageIcon(skinPath + "/window/rect.png").getImage();
		
		//���ڱ��⣨������
		SIGN = new ImageIcon(skinPath + "/string/sign.png").getImage();
		
		//���ݿ�ͼƬ
		DB = new ImageIcon(skinPath + "/string/db.png").getImage();
		
		//���ش���ͼƬ
		DISK = new ImageIcon(skinPath + "/string/disk.png").getImage();
		
		//����ͼƬ
		ACT = new ImageIcon(skinPath + "/game/rect.png").getImage();
		
		//�ȼ�ͼƬ
		LEVEL = new ImageIcon(skinPath + "/string/level.png").getImage();
		
		//���ڱ��⣨������
		POINT = new ImageIcon(skinPath + "/string/point.png").getImage();
			
		//���ڱ��⣨���У�
		RMLINE = new ImageIcon(skinPath + "/string/rmline.png").getImage();
		
		//��ӰͼƬ
		SHADOW = new ImageIcon(skinPath + "/game/shadow.png").getImage();
		
		//��ͣͼƬ
		PAUSE = new ImageIcon(skinPath + "/string/pause.png").getImage();
		
		//��ʼ����
		BTN_START = new ImageIcon(skinPath + "/string/start.png");
		
		//��ʼ����
		BTN_CONFIG = new ImageIcon(skinPath + "/string/config.png");
		
		//��һ������ͼƬ
		NEXT_ACT = new Image[GameConfig.getSYSTEM_CONFIG().getTypeConfig().size()];
		for (int i = 0; i < NEXT_ACT.length; i++) {
			NEXT_ACT[i] = new ImageIcon(skinPath + "/game/" + i + ".png").getImage();
		}
		//����ͼƬ�б�
		BG_LIST = new ArrayList<Image>();
		File dir = new File(skinPath + "/background");
		File[] files = dir.listFiles();
		for (File file : files) {
			if (!file.isDirectory()) {
				BG_LIST.add(new ImageIcon(file.getPath()).getImage());
			}
		}
	}
	
	/**
	 * ��ʼ��ͼƬ
	 */
	static {
		setSkin(BASE_PATH);
	}
	
}
