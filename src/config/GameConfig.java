package config;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class GameConfig implements Serializable {
	
	private static FrameConfig FRAME_CONFIG = null;
	
	private static DataConfig DATA_CONFIG = null;
	
	private static SystemConfig SYSTEM_CONFIG = null;
	
	private static final boolean IS_DEBUG = false;
	
	static {
		try {
			if(IS_DEBUG) {
				//创建XML读取器
				SAXReader reader = new SAXReader(); 
				//读取XML文件
				Document doc = reader.read("data/cfg.xml");
				//获取XML文件的根节点
				Element game = doc.getRootElement();
				//创建界面配置对象
				FRAME_CONFIG = new FrameConfig(game.element("frame"));
				//创建数据访问配置对象
				DATA_CONFIG = new DataConfig(game.element("data"));
				//创建系统配置对象
				SYSTEM_CONFIG = new SystemConfig(game.element("system"));
			} else {
				ObjectInputStream ois = new ObjectInputStream(new FileInputStream("data/framecfg.dat"));
				FRAME_CONFIG = (FrameConfig)ois.readObject();
				ois = new ObjectInputStream(new FileInputStream("data/datacfg.dat"));
				DATA_CONFIG = (DataConfig)ois.readObject();
				ois = new ObjectInputStream(new FileInputStream("data/systemcfg.dat"));
				SYSTEM_CONFIG = (SystemConfig)ois.readObject();
				ois.close();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private GameConfig() {}

	public static FrameConfig getFRAME_CONFIG() {
		return FRAME_CONFIG;
	}

	public static DataConfig getDATA_CONFIG() {
		return DATA_CONFIG;
	}

	public static SystemConfig getSYSTEM_CONFIG() {
		return SYSTEM_CONFIG;
	}

//	public static void main(String[] args) throws FileNotFoundException, IOException {
//		ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("data/datacfg.dat"));
//		oos.writeObject(DATA_CONFIG);
//		oos = new ObjectOutputStream(new FileOutputStream("data/framecfg.dat"));
//		oos.writeObject(FRAME_CONFIG);
//		oos = new ObjectOutputStream(new FileOutputStream("data/systemcfg.dat"));
//		oos.writeObject(SYSTEM_CONFIG);
//		oos.close();
//	}
}
