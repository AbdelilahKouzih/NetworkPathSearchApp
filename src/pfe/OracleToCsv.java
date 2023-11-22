package pfe;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;

import oracle.jdbc.driver.OracleConnection;

public class OracleToCsv {

	public static void main(String[] args) {

		String url = "jdbc:oracle:thin:@localhost:1521:xe?useServerPrepStmts=false"; // include useServerPrepStmts=false
																						// option
		String user = "system"; // replace with your own database username
		String password = "anouarzerrik2003"; // replace with your own database password
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		FileWriter writer = null;
		try {
			conn = DriverManager.getConnection(url, user, password);
			String query = "SELECT * FROM RESULTAT"; // replace with your own table name
			stmt = conn.prepareStatement(query);
			rs = stmt.executeQuery();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
			String timestamp = sdf.format(new Date());
			String fileName = "C:\\Users\\UTENTE\\Desktop\\csv\\csv" + timestamp + ".csv"; // replace with your own file
																							// name prefix
			writer = new FileWriter(fileName);
			ResultSetMetaData metadata = rs.getMetaData();
			int numColumns = metadata.getColumnCount();
			for (int i = 1; i <= numColumns; i++) {
				writer.write(metadata.getColumnName(i));
				if (i != numColumns) {
					writer.write(",");
				}
			}
			writer.write("\n");
			while (rs.next()) {
				StringBuilder sb = new StringBuilder();
				for (int i = 1; i <= numColumns; i++) {
					sb.append(rs.getString(i));
					if (i != numColumns) {
						sb.append(",");
					}
				}
				writer.write(sb.toString());
				writer.write("\n");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (stmt != null) {
					stmt.close();
				}
				if (conn != null) {
					conn.close();
				}
				if (writer != null) {
					writer.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}