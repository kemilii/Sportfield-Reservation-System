package reservationSystem.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import reservationSystem.model.Record;
import reservationSystem.model.User;
import reservationSystem.util.DbUtil;

public class RecordDao {
	/**
	 * Select the reservation records made by the specific user and add them to the record list of this user.
	 * @param user
	 * @throws SQLException
	 */
	public static void selectSpecificUserRecord(User user) throws SQLException {
		String classOrSubject = user.getClassOrSubject();
		String name = user.getName();
		
		Connection conn = DbUtil.getConnection();
		
		String sql = "select sport, place, reserve_date, reserve_time from t_record where class_or_subject = ? and name = ?";
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setString(1, classOrSubject);
		ps.setString(2, name);
		
		ResultSet rs = ps.executeQuery();
		ObservableList<Record> list = FXCollections.observableArrayList();
		while(rs.next()) {
			Record record = new Record(classOrSubject, name, rs.getString("sport"), rs.getString("place"), rs.getString("reserve_date"), rs.getString("reserve_time"));
			list.add(record);
		}
		user.setRecordList(list);
		
		DbUtil.close(conn, ps, rs);
	}
	
	/**
	 * Delete the record selected by user or administrator from database.
	 * @param selectedRecord
	 * @throws SQLException
	 */
	public static void deleteSelectedRecord(Record selectedRecord) throws SQLException {
		Connection conn = DbUtil.getConnection();
		
		String sql = "delete from t_record where sport = ? and place = ? and reserve_date = ? and reserve_time = ?";
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setString(1, selectedRecord.getSport());
		ps.setString(2, selectedRecord.getPlace());
		ps.setString(3, selectedRecord.getReserveDate());
		ps.setString(4, selectedRecord.getReserveTime());
		
		int count = ps.executeUpdate();
		System.out.println("Number of rows deleted" + count); //check the number of row deleted
		
		DbUtil.close(conn, ps, null);
	}
	
	/**
	 * Update the record edited by user in the database.
	 * @param oldRecord
	 * @param newRecord
	 * @throws SQLException
	 */
	public static void updateUserSelectedRecord(Record oldRecord, Record newRecord) throws SQLException {
		Connection conn = DbUtil.getConnection();
		
		String sql = "update t_record set class_or_subject = ?, name = ?, sport = ?, place = ?, reserve_date = ?, reserve_time = ? "
				+ "where sport = ? and place = ? and reserve_date = ? and reserve_time = ?";
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setString(1, newRecord.getClassOrSubject());
		ps.setString(2, newRecord.getName());
		ps.setString(3, newRecord.getSport());
		ps.setString(4, newRecord.getPlace());
		ps.setString(5, newRecord.getReserveDate());
		ps.setString(6, newRecord.getReserveTime());
		ps.setString(7, oldRecord.getSport());
		ps.setString(8, oldRecord.getPlace());
		ps.setString(9, oldRecord.getReserveDate());
		ps.setString(10, oldRecord.getReserveTime());
		
		int count = ps.executeUpdate();
		System.out.println("Number of rows edited: " + count); //check the number of row deleted
		
		DbUtil.close(conn, ps, null);
	}
	
	/**
	 * Find record in the record database to check if this reservation has been made.
	 * @param sport
	 * @param place
	 * @param date
	 * @param time
	 * @return Return true if it has been reserved, otherwise false.
	 * @throws SQLException
	 */
	public static boolean findRecord(String sport, String place, String date, String time) throws SQLException {
		boolean ok = false;
		Connection conn = DbUtil.getConnection();
		
		String sql = "select sport, place, reserve_date, reserve_time from t_record where sport = ? and place = ? and reserve_date = ? and reserve_time = ?";
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setString(1, sport);
		ps.setString(2, place);
		ps.setString(3, date);
		ps.setString(4, time);
		
		ResultSet rs = ps.executeQuery();
		
		if(rs.next()) {
			ok = true;
		}
		
		DbUtil.close(conn, ps, null);
		return ok;
	}
	
	/**
	 * Select the time periods those has been reserved for specific sport, place and date.
	 * @param sport
	 * @param place
	 * @param date
	 * @return Return the unavailable time periods.
	 * @throws SQLException
	 */
	public static ObservableList<String> selectUnavailableTime(String sport, String place, String date) throws SQLException{
		ObservableList<String> times = FXCollections.observableArrayList();
		
		Connection conn = DbUtil.getConnection();
		
		String sql = "select reserve_time from t_record where sport = ? and place = ? and reserve_date = ?";
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setString(1, sport);
		ps.setString(2, place);
		ps.setString(3, date);
		
		ResultSet rs = ps.executeQuery();
		
		while(rs.next()) {
			times.add(rs.getString("reserve_time").substring(0, 2));
		}
		
		DbUtil.close(conn, ps, null);
		return times;
	}
	
	/**
	 * Add a new reservation record into the database.
	 * @param classOrSubject
	 * @param name
	 * @param sport
	 * @param place
	 * @param date
	 * @param time
	 * @throws SQLException
	 */
	public static void addRecord(String classOrSubject, String name, String sport, String place, String date, String time) throws SQLException {
		Connection conn = DbUtil.getConnection();
		
		String sql = "insert into t_record(class_or_subject, name, sport, place, reserve_date, reserve_time) values(?, ?, ?, ?, ?, ?)";
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setString(1, classOrSubject);
		ps.setString(2, name);
		ps.setString(3, sport);
		ps.setString(4, place);
		ps.setString(5, date);
		ps.setString(6, time);
		
		int count = ps.executeUpdate();
		System.out.println("Number of rows added: " + count); //check the number of row added
		
		DbUtil.close(conn, ps, null);
	}
	
	/**
	 * Select all reservations those have been made.
	 * @return Return record of reservations made.
	 * @throws SQLException
	 */
	public static ObservableList<Record> selectRecord() throws SQLException {
		Connection conn = DbUtil.getConnection();
		
		String sql = "select class_or_subject, name, sport, place, reserve_date, reserve_time from t_record";
		PreparedStatement ps = conn.prepareStatement(sql);
		
		ResultSet rs = ps.executeQuery();
		ObservableList<Record> list = FXCollections.observableArrayList();
		while(rs.next()) {
			Record record = new Record(rs.getString("class_or_subject"), rs.getString("name"), rs.getString("sport"),
					rs.getString("place"), rs.getString("reserve_date"), rs.getString("reserve_time"));
			list.add(record);
		}
		
		DbUtil.close(conn, ps, rs);
		return list;
	}

}

