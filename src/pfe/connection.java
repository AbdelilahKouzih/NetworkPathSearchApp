package pfe;

import java.sql.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileInputStream;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

<<<<<<< HEAD

=======
>>>>>>> aa687c2763f728027ebfb8d31e042c5811b41839
import java.io.File;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class connection {

	// -------variables
	static Connection connection;

	// -------Methodes -------//

	public static List<String> STOCKAGE_Principal(String[] tab) {
		List<String> List_principal = new ArrayList<String>();

		for (int i = 0; i < tab.length; i++) {
			List<String> links = new ArrayList<String>();
			links = GET_LINKS_SITE(tab[i]);
			List<String> chemin = new ArrayList<String>();
			chemin.add("[[");
			chemin.add(tab[i]);

			remplir_List_Principal2(tab, List_principal, links, chemin);
			chemin.clear();
		}
		return List_principal;
	}

	public static void remplir_List_Principal2(String[] tab, List<String> list_global, List<String> links,
			List<String> chemin) {

		for (String a : links) {

			if (chemin.contains(a)) {
				chemin.add("]]");
				list_global.addAll(chemin);
				int size = chemin.size();
				if (size >= 1) {
					chemin.subList(size - 1, size).clear();
				}
			} else {
				if (elementExists(tab, a)) {
					chemin.add("-");
					chemin.add(a);
					remplir_List_Principal2(tab, list_global, GET_LINKS_SITE(a), chemin);
				} else {
					chemin.add("-");
					chemin.add(a);
					chemin.add("]]");
					list_global.addAll(chemin);
					int size = chemin.size();
					if (size >= 3) {
						chemin.subList(size - 3, size).clear();
					}
				}
			}
		}

		int size1 = chemin.size();
		if (size1 >= 2) {
			chemin.subList(size1 - 2, size1).clear();
		}
	}

	public static List<String> GetALLSites() {
		connection("system", "anouarzerrik2003");
		List<String> liste = new ArrayList<String>();
		String query = "SELECT SITE_NAME FROM SITE ";
		Statement statement = null;
		ResultSet resultSet = null;
		try {
			statement = connection.createStatement();
			resultSet = statement.executeQuery(query);
			while (resultSet.next()) {
				String column2 = resultSet.getString(1);
				liste.add(column2);
			}

			removeDuplicates(liste);

		} catch (SQLException e1) {
			System.out.println("Error connecting to Oracle database: " + e1.getMessage());
		}

		try {
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return liste;
	}

	// -----------------------------//-----------------------------//-----------------------------//-----------------------------

	public static void GetALLSitesPaths(List<String> liste) {

		int i = 0;

		for (String element : liste) {
			i++;
			System.out.println(i);
			connection("system", "anouarzerrik2003");
			String ip = get_ip_site(element);
			String[] tab = get_SITES(ip, get_ip_site(element));
			List<String> List_principal = STOCKAGE_Principal(tab);

			StringBuilder sb = new StringBuilder();
			for (String s : List_principal) {
				sb.append(s);

				if (s == "]]") {
					sb.append(" ");
				}
			}
			String result = sb.toString().trim();

			String query = "INSERT INTO RESULTAT  VALUES (?,?,?)";
			PreparedStatement statement = null;

			try {
				statement = connection.prepareStatement(query);
				statement.setString(1, element);
				statement.setString(2, result);
				Date date = new Date();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				String formattedDate = sdf.format(date);
				statement.setString(3, formattedDate);
				statement.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				if (statement != null) {
					try {
						statement.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			}
			try {
				connection.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

	public static void GetALLSitesPaths2(String site) {

		String ip = get_ip_site(site);
		String[] tab = get_SITES(ip, get_ip_site(site));
		List<String> List_principal = STOCKAGE_Principal(tab);

		StringBuilder sb = new StringBuilder();
		for (String s : List_principal) {
			sb.append(s);

			if (s == "]]") {
				sb.append(" ");
			}
		}
		String result = sb.toString().trim();

		String query = "INSERT INTO RESULTAT  VALUES (?,?,?)";
		PreparedStatement statement = null;

		try {
			statement = connection.prepareStatement(query);
			statement.setString(1, site);
			statement.setString(2, result);
			Date date = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String formattedDate = sdf.format(date);
			statement.setString(3, formattedDate);
			statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (statement != null) {
				try {
					statement.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static List<String> supprimerPartiesRedoublees(List<String> liste, String delimiteur) {
		Set<String> set = new HashSet<>();
		List<String> resultat = new ArrayList<>();

		for (String element : liste) {
			String[] parties = element.split(delimiteur);
			String nouvelleChaine = "";
			for (String partie : parties) {
				if (!set.contains(partie)) {
					nouvelleChaine += partie + delimiteur;
					set.add(partie);
				}
			}
			if (!nouvelleChaine.isEmpty()) {
				nouvelleChaine = nouvelleChaine.substring(0, nouvelleChaine.length() - delimiteur.length());
				resultat.add(nouvelleChaine);
			}
		}

		return resultat;
	}

	public static boolean elementExists(String[] liste, String element) {
		for (String str : liste) {
			if (str.equals(element)) {
				return true;
			}
		}
		return false;
	}

	public static void removeDuplicates(List<String> array_of_sites2) {
		Set<String> set = new HashSet<>();
		int insertIndex = 0;
		for (String s : array_of_sites2) {
			if (!set.contains(s)) {
				set.add(s);
				array_of_sites2.set(insertIndex++, s);
			}
		}
		while (array_of_sites2.size() > insertIndex) {
			array_of_sites2.remove(insertIndex);
		}
	}

	// --------------------------------------------------------------------------------------------------------
	// --------------------------------------------------------------------------------------------------------

	static List<String> GET_LINKS_SITE(String site) {
		String tab[] = null;

		List<String> array_of_sites2 = new ArrayList<String>();

		Statement statement1 = null;
		Statement statement2 = null;
		ResultSet resultSet1 = null;
		ResultSet resultSet2 = null;
		String query1 = "SELECT LIEN1 FROM LIEN where LIEN2 ='" + site + "'";
		String query2 = "SELECT LIEN2 FROM LIEN where LIEN1 ='" + site + "'";
		try {
			statement1 = connection.createStatement();
			resultSet1 = statement1.executeQuery(query1);
			statement2 = connection.createStatement();
			resultSet2 = statement2.executeQuery(query2);
			while (resultSet1.next()) {
				String column1 = resultSet1.getString(1);
				array_of_sites2.add(column1);
			}

			while (resultSet2.next()) {

				String column2 = resultSet2.getString(1);
				array_of_sites2.add(column2);
			}

			removeDuplicates(array_of_sites2);

		} catch (SQLException e1) {
			System.out.println("Error connecting to Oracle database: " + e1.getMessage());
		}

		return array_of_sites2;
	}

	// --------------------------------------------------------------------------------------------------------
	// --------------------------------------------------------------------------------------------------------

	static String get_ip_site(String site) {
		String ip_site_inpute = null;

		String query = "SELECT IP FROM SITE where SITE_NAME = '" + site + "'  ";
		Statement statement = null;
		ResultSet resultSet = null;
		try {
			statement = connection.createStatement();
			resultSet = statement.executeQuery(query);
			while (resultSet.next()) {

				ip_site_inpute = resultSet.getString(1);
			}

		} catch (SQLException e1) {
			System.out.println("Error connecting to Oracle database: " + e1.getMessage());
		}
		return ip_site_inpute;
	}

	public int get_MAX() {
		int id = 0;
		String query = "SELECT nvl(MAX(ID_TRAITEMENT),0)+1 FROM RESULTAT ";
		Statement statement = null;
		ResultSet resultSet = null;
		try {
			statement = connection.createStatement();
			resultSet = statement.executeQuery(query);
			while (resultSet.next()) {

				id = resultSet.getInt(1);
			}

		} catch (SQLException e1) {
			System.out.println("Error connecting to Oracle database: " + e1.getMessage());
		}
		return id;
	}

	static int get_MAX1() {
		int id = 0;
		String query = "SELECT MAX(ID_TRAITEMENT) FROM RESULTAT ";
		Statement statement = null;
		ResultSet resultSet = null;
		try {
			statement = connection.createStatement();
			resultSet = statement.executeQuery(query);
			while (resultSet.next()) {

				id = resultSet.getInt(1);
			}

		} catch (SQLException e1) {
			System.out.println("Error connecting to Oracle database: " + e1.getMessage());
		}

		id++;
		return id;
	}

	// --------------------------------------------------------------------------------------------------------
	// --------------------------------------------------------------------------------------------------------

	static String[] get_SITES(String ip, String site_name) {
		String[] tab = null;
		List<String> array_of_sites1 = new ArrayList<String>();

		Statement statement = null;
		ResultSet resultSet = null;
		String query = "SELECT SITE_NAME FROM SITE where IP = '" + ip + "' ";
		try {
			statement = connection.createStatement();
			resultSet = statement.executeQuery(query);
			while (resultSet.next()) {
				String column1 = resultSet.getString("SITE_NAME");
				array_of_sites1.add(column1);

			}
		} catch (SQLException e1) {
			System.out.println("Error connecting to Oracle database: " + e1.getMessage());
		}
		tab = array_of_sites1.toArray(new String[0]);

		return tab;
	}

	// --------------------------------------------------------------------------------------------------------
	// --------------------------------------------------------------------------------------------------------

	static void connection(String user, String password1) {
		String jdbcUrl = "jdbc:oracle:thin:@localhost:1521:xe";
		String username = user;
		String password = password1;
		try {
			connection = DriverManager.getConnection(jdbcUrl, username, password);

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
<<<<<<< HEAD
	static void connection1(String user, String password1) {
		String jdbcUrl = "jdbc:oracle:thin:@localhost:1521:ORCL";
		String username = user;
		String password = password1;
		try {
			connection = DriverManager.getConnection(jdbcUrl, username, password);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	static void connection2(String user, String password1,String database) {
		try{ 
			   Class.forName("com.mysql.cj.jdbc.Driver");
			   connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/"+database+"",user,password1);
			  System.out.println("Connect");
			   }catch(Exception e){
			    System.out.println(e);
			   } 
=======

	static void connection2(String user, String password1, String database) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + database + "", user, password1);
			System.out.println("Connect");
		} catch (Exception e) {
			System.out.println(e);
		}
>>>>>>> aa687c2763f728027ebfb8d31e042c5811b41839
	}

	static void delete_traitement(int id) {

		Statement stmt = null;
		try {

			stmt = connection.createStatement();
			String sql = "DELETE From RESULTAT where  ID_TRAITEMENT = '" + id + "'";
			stmt.executeUpdate(sql);
			stmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

<<<<<<< HEAD
	
	static void create_table()
	{
		Statement stmt = null;
		try {
		stmt = connection.createStatement();
		//stmt.addBatch("CREATE TABLE mytable (id NUMBER, name VARCHAR2(50))");
		//stmt.addBatch("CREATE TABLE mytable1 (id NUMBER, name VARCHAR2(50))");
		String sql = "CREATE TABLE lien (LIEN1 varchar(255),LIEN2 varchar(255))";
		String sql1 = "CREATE TABLE site(SITE_NAME varchar(255),IP varchar(255))";
		String sql2 = "CREATE TABLE resultat (SITE varchar(255),ID_TRAITEMENT int,DATE_INSERT date ,CHEMIN longtext)";
		//stmt1.executeBatch();
		stmt.execute(sql);
		stmt.execute(sql1);
		stmt.execute(sql2);
        System.out.println("Database created successfully...");
     } catch (SQLException e) {
        System.out.println("Failed to create database.") ;
		
     }	
		
	}
	
	static void create_table_orcl()
	{
		Statement stmt = null;
		try {
		stmt = connection.createStatement();
		//stmt.addBatch("CREATE TABLE mytable (id NUMBER, name VARCHAR2(50))");
		//stmt.addBatch("CREATE TABLE mytable1 (id NUMBER, name VARCHAR2(50))");
		String sql = "CREATE TABLE lien (LIEN1 varchar(255),LIEN2 varchar(255))";
		String sql1 = "CREATE TABLE site(SITE_NAME varchar(255),IP varchar(255))";
		String sql2 = "CREATE TABLE resultat (SITE varchar(255),ID_TRAITEMENT int,DATE_INSERT date ,CHEMIN CLOB)";
		//stmt1.executeBatch();
		stmt.execute(sql);
		stmt.execute(sql1);
		stmt.execute(sql2);
        System.out.println("Database created successfully...");
     } catch (SQLException e) {
        System.out.println("Failed to create database.") ;
		
     }	
		
	}
	
	static void drop_table()
	{
		Statement stmt = null;
		Statement stmt1 = null;
		try {
		stmt = connection.createStatement();
		stmt1 = connection.createStatement();
		stmt.addBatch("drop TABLE site ");
		stmt.addBatch("drop TABLE lien ");
		stmt.addBatch("drop TABLE resultat ");
		stmt.executeBatch();
		//stmt1.executeBatch();
        System.out.println("Database created successfully...");
     } catch (SQLException e) {
        System.out.println("Failed to create database.") ;
     }	
	}
	
	
	public static void main(String[] args) throws SQLException {

	//connection("system", "anouarzerrik2003");
	connection2("root","anoirzerrik2003","etl");
	drop_table();
	create_table();
	
	DatabaseMetaData md = connection.getMetaData();
	ResultSet rs = md.getTables(null, null, "site", null);
	while (rs.next()) {
	  System.out.println(rs.getString(3));
	}
	
=======
	static void create_table() {
		Statement stmt = null;
		try {
			stmt = connection.createStatement();
			String sql = "CREATE TABLE lien (LIEN1 varchar(255),LIEN2 varchar(255))";
			String sql1 = "CREATE TABLE site(SITE_NAME varchar(255),IP varchar(255))";
			String sql2 = "CREATE TABLE resultat (SITE varchar(255),ID_TRAITEMENT int,DATE_INSERT date ,CHEMIN longtext)";
			stmt.execute(sql);
			stmt.execute(sql1);
			stmt.execute(sql2);
			System.out.println("Database created successfully...");
		} catch (SQLException e) {
			System.out.println("Failed to create database.");

		}

>>>>>>> aa687c2763f728027ebfb8d31e042c5811b41839
	}

	static void create_table_orcl() {
		Statement stmt = null;
		try {
			stmt = connection.createStatement();
			String sql = "CREATE TABLE lien (LIEN1 varchar(255),LIEN2 varchar(255))";
			String sql1 = "CREATE TABLE site(SITE_NAME varchar(255),IP varchar(255))";
			String sql2 = "CREATE TABLE resultat (SITE varchar(255),ID_TRAITEMENT int,DATE_INSERT date ,CHEMIN CLOB)";
			stmt.execute(sql);
			stmt.execute(sql1);
			stmt.execute(sql2);
			System.out.println("Database created successfully...");
		} catch (SQLException e) {
			System.out.println("Failed to create database.");

		}

	}

	static void drop_table() {
		Statement stmt = null;
		Statement stmt1 = null;
		try {
			stmt = connection.createStatement();
			stmt1 = connection.createStatement();
			stmt.addBatch("drop TABLE SITE ");
			stmt.addBatch("drop TABLE LIEN ");
			stmt.addBatch("drop TABLE RESULTAT ");
			stmt.executeBatch();
			// stmt1.executeBatch();
			System.out.println("delete success");
		} catch (SQLException e) {
			System.out.println("Failed to delete");
		}
	}

	static int orcl_exist() {

		int i = 0;
		try (Statement stmt = connection.createStatement();
				ResultSet rs = stmt.executeQuery(
						"SELECT COUNT(*) FROM user_tables WHERE table_name IN ('LIEN', 'SITE' , 'RESULTAT')")) {

			if (rs.next() && rs.getInt(1) == 3) {
				System.out.println("Les tables existent.");
				i = rs.getInt(1);
			} else {
			}

		} catch (SQLException e) {
			System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return i;
	}

	static int mysql_exist(String db) {
		int i = 0;
		try (Statement stmt = connection.createStatement();
				ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM information_schema.tables WHERE table_schema = '"
						+ db + "' AND table_name IN ('LIEN', 'SITE' , 'RESULTAT')")) {

			if (rs.next() && rs.getInt(1) == 3) {

				i = rs.getInt(1);
			} else {
			}

		} catch (SQLException e) {
			System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return i;
	}

}
