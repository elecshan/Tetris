package dao;

import java.util.ArrayList;
import java.util.List;

import dto.Player;

public class DataTest implements Data {

	@Override
	public List<Player> loadData() {
		List<Player> players = new ArrayList<Player>();
		players.add(new Player("����", 200));
		players.add(new Player("����", 200));
		players.add(new Player("����", 500));
		players.add(new Player("����", 1000));
		players.add(new Player("����", 150));
		return players;
	}

	@Override
	public void saveData(Player player) {
		System.out.println();
		
	}

}
