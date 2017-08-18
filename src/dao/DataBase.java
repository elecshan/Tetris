package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import dto.Player;

public class DataBase implements Data {
	
	private final String dbUrl;
	
	private final String dbUser;
	
	private final String dbPwd;
	
	private final String LOAD_SQL = "SELECT TOP 5 user_name, point FROM game_test.dbo.user_point WHERE type_id=1 ORDER BY point DESC";
	
	private final String SAVE_SQL = "INSERT INTO user_point(user_name, point, type_id) VALUES(?,?,?)";
	
	public DataBase(HashMap<String, String > param) {
		this.dbUrl = param.get("dbUrl");
		this.dbUser = param.get("dbUser");
		this.dbPwd = param.get("dbPwd");
		try {
			Class.forName(param.get("driver"));
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public List<Player> loadData() {
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		List<Player> players = new ArrayList<Player>();
		try {
			con = DriverManager.getConnection(dbUrl, dbUser, dbPwd);
			stmt = con.prepareStatement(LOAD_SQL);
			rs = stmt.executeQuery();
			while(rs.next()) {
				players.add(new Player(rs.getString(1), rs.getInt(2)));
			}	
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(con != null) con.close();
				if(stmt != null) stmt.close();
				if(rs != null) rs.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return players;
	}

	@Override
	public void saveData(Player player) {
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			con = DriverManager.getConnection(dbUrl, dbUser, dbPwd);
			stmt = con.prepareStatement(SAVE_SQL);
			stmt.setObject(1, player.getName());
			stmt.setObject(2, player.getPoint());
			stmt.setObject(3, 1);
			stmt.execute();
		} catch (Exception e) { 
			e.printStackTrace();
		} finally {
			try {
				if(con != null) con.close();
				if(stmt != null) stmt.close();
				if(rs != null) rs.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
}
