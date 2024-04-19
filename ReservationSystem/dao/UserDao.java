package reservationSystem.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import reservationSystem.model.User;
import reservationSystem.util.DbUtil;

public class UserDao {
	/**
	 * Verify whether the user is in the database.
	 * @param user
	 * @return Return true if the user is found, otherwise return false
	 * @throws SQLException
	 */
	public static boolean verifyUser(User user) throws SQLException {
		boolean ok = false;
		try {
			//获取数据库
			Connection conn = DbUtil.getConnection(); 
			//获取数据库对象
			String sql = "select * from t_user where class_or_subject = ? and name = ?"; //"?"是占位符，一个占位符代表一个变量（parameter）
			PreparedStatement ps = conn.prepareStatement(sql); 
			ps.setString(1, user.getClassOrSubject());
			ps.setString(2, user.getName());
			//执行sql语句
			ResultSet rs = ps.executeQuery(); //这个时候sql语句会被发送给DBMS，进行compile
			//处理结果集
			if(rs.next()) {
				ok = true;
			}
			//释放资源
			DbUtil.close(conn, ps, rs);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ok;
	}
}






