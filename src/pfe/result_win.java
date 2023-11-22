package pfe;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import java.awt.Font;
import java.awt.Image;

import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.awt.event.ActionEvent;
import java.awt.SystemColor;

public class result_win {

	public String user;
	public String password;
	static String db;
	public String database;

	JFrame frame;
	private JTextField textField;
	JTextArea textArea = new JTextArea();
	static Connection connection;

	// -----------------------------//-----------------------------//-----------------------------//-----------------------------

	public static List<String> GetALLSites() {
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

		return liste;
	}

	// -----------------------------//-----------------------------//-----------------------------//-----------------------------

	public static void GetALLSitesPaths(List<String> liste) {

		for (String element : liste) {
			String ip = get_ip_site(element);
			String[] tab = get_SITES(ip, get_ip_site(element));
			List<String> List_principal = STOCKAGE_Principal(tab);

			String joinedString = String.join(",", List_principal);

			String query = "INSERT INTO RESULTAT (SITE,CHEMIN) VALUES (?,?)";
			PreparedStatement statement = null;

			try {
				statement = connection.prepareStatement(query);
				statement.setString(1, element);
				statement.setString(2, joinedString);
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
	}

	// -----------------------------//-----------------------------//-----------------------------//-----------------------------

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

	// --------------------------------------------------------------------------------------------------------
	// --------------------------------------------------------------------------------------------------------

	static String[] get_SITES(String ip, String site_name) {
		String[] tab = null;
		int nb = 0;
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
				nb++;
			}
		} catch (SQLException e1) {
			System.out.println("Error connecting to Oracle database: " + e1.getMessage());
		}
		tab = array_of_sites1.toArray(new String[0]);
		return tab;
	}

	//// -----------------------------------------

	static void connection11(String user, String password1) {
		String jdbcUrl = "jdbc:oracle:thin:@localhost:1521:xe";
		String username = user;
		String password = password1;
		try {
			connection = DriverManager.getConnection(jdbcUrl, username, password);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Launch the application.
	 */

	static void connection(String password1, String user) {
		String jdbcUrl = "jdbc:oracle:thin:@localhost:1521:xe";
		String username = user;
		String password = password1;
		try {
			connection = DriverManager.getConnection(jdbcUrl, username, password);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	static void connection2(String user, String password1, String database) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + database + "", user, password1);
			// System.out.println("Connect");
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public result_win(String user, String password, String database, String db) {
		this.password = password;
		this.user = user;
		this.database = database;
		this.db = db;

		initialize(user, password, database, db);
		Image icon = new ImageIcon(this.getClass().getResource("/img/log.png")).getImage();
		frame.setIconImage(icon);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(String user, String password, String database, String db) {
		frame = new JFrame();
		frame.getContentPane().setBackground(SystemColor.inactiveCaption);
		frame.setBounds(100, 100, 647, 472);

		frame.getContentPane().setLayout(null);

		JLabel lblNewLabel = new JLabel("Site :");
		lblNewLabel.setForeground(SystemColor.text);
		lblNewLabel.setFont(new Font("Arial Black", Font.PLAIN, 15));
		lblNewLabel.setBounds(401, 154, 46, 14);
		frame.getContentPane().add(lblNewLabel);

		textField = new JTextField();
		textField.setForeground(SystemColor.desktop);
		textField.setBounds(401, 179, 193, 28);
		frame.getContentPane().add(textField);
		textField.setColumns(10);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 0, 361, 433);
		frame.getContentPane().add(scrollPane);

		JTextArea textArea = new JTextArea();
		textArea.setEditable(false);
		textArea.setFont(new Font("Monospaced", Font.BOLD, 14));
		scrollPane.setViewportView(textArea);

		JButton btnNewButton = new JButton("Chercher");
		btnNewButton.setForeground(SystemColor.activeCaptionText);
		btnNewButton.setBackground(SystemColor.text);
		btnNewButton.setFont(new Font("Arial Black", Font.PLAIN, 13));
		btnNewButton.setBounds(384, 229, 145, 35);
		frame.getContentPane().add(btnNewButton);

		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				switch (db) {
				case "Oracle":
					connection11(user, password);
					// connection2(user,password,database);
					String site = textField.getText();
					String ip = get_ip_site(site);
					String[] tab = get_SITES(ip, get_ip_site(site));
					List<String> List_principal = new ArrayList<String>();
					List_principal = STOCKAGE_Principal(tab);

					textArea.setText("");
					for (String element : List_principal) {

						if (element != "]]") {

							textArea.append(element);
						} else {
							textArea.append(element + "\n");
						}
					}

				case "MySQL":

					connection2(user, password, database);
					String site1 = textField.getText();
					String ip1 = get_ip_site(site1);
					String[] tab1 = get_SITES(ip1, get_ip_site(site1));
					List<String> List_principal1 = new ArrayList<String>();
					List_principal1 = STOCKAGE_Principal(tab1);

					textArea.setText("");
					for (String element : List_principal1) {

						if (element != "]]") {

							textArea.append(element);
						} else {
							textArea.append(element + "\n");
						}

					}
				}

			}
		});
		btnNewButton.setFont(new Font("Arial Black", Font.PLAIN, 13));
		btnNewButton.setBounds(434, 235, 145, 35);
		frame.getContentPane().add(btnNewButton);
		frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
	}
}
