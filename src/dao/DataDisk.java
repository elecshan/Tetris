package dao;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.List;

import dto.Player;

public class DataDisk implements Data {

	private final String filePath;
	
	public DataDisk(HashMap<String, String > param) {
		this.filePath = param.get("path");
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Player> loadData() {
		ObjectInputStream ois = null;
		List<Player> players = null;
		
		try {
			ois = new ObjectInputStream(new FileInputStream(filePath));
			players = (List<Player>)ois.readObject();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				ois.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return players;
	}

	@Override
	public void saveData(Player player) {
		//先取出数据
		List<Player> players = this.loadData();
		//向列表追加数据
		players.add(player);
		ObjectOutputStream oos = null;
		try {
			oos = new ObjectOutputStream(new FileOutputStream(filePath));
			oos.writeObject(players);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				oos.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
	}
	
//	public static void main(String[] args) {
//		List<Player> players = new ArrayList<Player>();
//		players.add(new Player("刘明", 200));
//		players.add(new Player("照明", 200));
//		players.add(new Player("残明", 500));
//		players.add(new Player("高明", 1000));
//		players.add(new Player("抡明", 150));
//		
//		ObjectOutputStream oos = null;
//		try {
//			oos = new ObjectOutputStream(new FileOutputStream(FILE_PATH));
//			oos.writeObject(players);
//		} catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//			try {
//				oos.close();
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		}
//		
//		System.out.println("111");
//	}

}
