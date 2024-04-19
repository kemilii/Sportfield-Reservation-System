package reservationSystem.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import reservationSystem.util.DbUtil;

public class SportfieldDao {
	/**
	 * Check if the sportfield exists according to the values of "sport" and "place".
	 * @param sport
	 * @param place
	 * @return Return true if the sportfield is found, otherwise return false.
	 * @throws SQLException
	 */
	public static boolean findSportfield(String sport, String place) throws SQLException {
		boolean ok = false;
		Connection conn = DbUtil.getConnection();
		
		String sql = "select * from t_sportfield where sport = ? and place = ?";
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setString(1, sport);
		ps.setString(2, place);
		 
		ResultSet rs = ps.executeQuery();
		 
		if(rs.next()) {
			ok = true;
		}
		
		DbUtil.close(conn, ps, null);
		return ok;
	}
	/**
	 * Select the available places for the specific sport.
	 * @param sport
	 * @return Return the places selected for the specific sport.
	 * @throws SQLException
	 */
	public static ObservableList<String> selectPlaces(String sport) throws SQLException {
		ObservableList<String> places = FXCollections.observableArrayList();
		
		Connection conn = DbUtil.getConnection();
		
		String sql = "select place from t_sportfield where sport = ?";
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setString(1, sport);
		
		ResultSet rs = ps.executeQuery();
		
		while(rs.next()) {
			places.add(rs.getString("place"));
		}
		
		DbUtil.close(conn, ps, null);
		return places;
	}
}
