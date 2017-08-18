package dao;

import java.util.List;

import dto.Player;

/**
 * 数据持久层接口
 * @author xgs
 * @date 2017年8月7日 下午4:43:15
 */
public interface Data {

	/**
	 * 读取数据
	 * @return
	 */
	List<Player> loadData();
	
	/**
	 * 存储数据
	 * @param players
	 */
	void saveData(Player player);
}
