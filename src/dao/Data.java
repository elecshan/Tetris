package dao;

import java.util.List;

import dto.Player;

/**
 * ���ݳ־ò�ӿ�
 * @author xgs
 * @date 2017��8��7�� ����4:43:15
 */
public interface Data {

	/**
	 * ��ȡ����
	 * @return
	 */
	List<Player> loadData();
	
	/**
	 * �洢����
	 * @param players
	 */
	void saveData(Player player);
}
