package ui;

import java.awt.Image;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;

import config.GameConfig;

public class Img {
	
	//图片路径
	public static final String GRAPHICS_PATH = "graphics/";

	private static final String BASE_PATH = "default/";
	
	//窗口图片
	public static Image WINDOW = null;
	
	//数字图片
	public static Image NUMBER = null;
	
	//经验值槽
	public static Image RECT = null;
	
	//窗口标题（分数）
	public static Image SIGN = null;
	
	//数据库图片
	public static Image DB = null;
	
	//本地磁盘图片
	public static Image DISK = null;
	
	//方块图片
	public static Image ACT = null;
	
	//等级图片
	public static Image LEVEL = null;
	
	//窗口标题（分数）
	public static Image POINT = null;
		
	//窗口标题（消行）
	public static Image RMLINE = null;
	
	//阴影图片
	public static Image SHADOW = null;
	
	//暂停图片
	public static Image PAUSE = null;
	
	//开始按键
	public static ImageIcon BTN_START = null;
	
	//开始按键
	public static ImageIcon BTN_CONFIG = null;
	
	//下一个方块
	public static Image[] NEXT_ACT = null;
	
	//背景图片列表
	public static List<Image> BG_LIST = null;
	
	private Img() {}
	
	public static void setSkin(String path) {
		String skinPath = GRAPHICS_PATH + path;
		//窗口图片
		WINDOW = new ImageIcon(skinPath + "/window/Window.png").getImage();
		
		//数字图片
		NUMBER = new ImageIcon(skinPath + "/string/num.png").getImage();
		
		//经验值槽
		RECT = new ImageIcon(skinPath + "/window/rect.png").getImage();
		
		//窗口标题（分数）
		SIGN = new ImageIcon(skinPath + "/string/sign.png").getImage();
		
		//数据库图片
		DB = new ImageIcon(skinPath + "/string/db.png").getImage();
		
		//本地磁盘图片
		DISK = new ImageIcon(skinPath + "/string/disk.png").getImage();
		
		//方块图片
		ACT = new ImageIcon(skinPath + "/game/rect.png").getImage();
		
		//等级图片
		LEVEL = new ImageIcon(skinPath + "/string/level.png").getImage();
		
		//窗口标题（分数）
		POINT = new ImageIcon(skinPath + "/string/point.png").getImage();
			
		//窗口标题（消行）
		RMLINE = new ImageIcon(skinPath + "/string/rmline.png").getImage();
		
		//阴影图片
		SHADOW = new ImageIcon(skinPath + "/game/shadow.png").getImage();
		
		//暂停图片
		PAUSE = new ImageIcon(skinPath + "/string/pause.png").getImage();
		
		//开始按键
		BTN_START = new ImageIcon(skinPath + "/string/start.png");
		
		//开始按键
		BTN_CONFIG = new ImageIcon(skinPath + "/string/config.png");
		
		//下一个方块图片
		NEXT_ACT = new Image[GameConfig.getSYSTEM_CONFIG().getTypeConfig().size()];
		for (int i = 0; i < NEXT_ACT.length; i++) {
			NEXT_ACT[i] = new ImageIcon(skinPath + "/game/" + i + ".png").getImage();
		}
		//背景图片列表
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
	 * 初始化图片
	 */
	static {
		setSkin(BASE_PATH);
	}
	
}
