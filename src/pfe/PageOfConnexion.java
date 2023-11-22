package pfe;

import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.prefs.Preferences;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class PageOfConnexion extends JFrame implements ActionListener {
	JLabel titleLabel, dbLabel, userLabel, passwordLabel, infoLabel, dbNameLabel;
	JTextField userField, dbNameField;
	JPasswordField passwordField;
	JComboBox<String> dbComboBox;
	JButton connectButton;
	String db;

	public PageOfConnexion() {
		setTitle("Home Page");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(400, 250);
		setLocationRelativeTo(null);

		Preferences prefs = Preferences.userNodeForPackage(PageOfConnexion.class);
		String savedUser = prefs.get("user", "");
		String savedPassword = prefs.get("password", "");
		String savedDbName = prefs.get("dbName", "");

		titleLabel = new JLabel("Choose a database to connect to:");
		dbLabel = new JLabel("Database:");
		userLabel = new JLabel("Username:");
		passwordLabel = new JLabel("Password:");
		infoLabel = new JLabel("Please enter your login information");
		dbNameLabel = new JLabel("Database name:");

		userField = new JTextField(20);
		passwordField = new JPasswordField(20);
		dbNameField = new JTextField(20);

		dbComboBox = new JComboBox<>(new String[] { "Oracle", "MySQL", "SQL Server" });
		dbComboBox.addActionListener(this);

		connectButton = new JButton("Connect");
		connectButton.addActionListener(this);

		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(6, 2));
		panel.add(titleLabel);
		panel.add(new JLabel(""));
		panel.add(dbLabel);
		panel.add(dbComboBox);
		panel.add(dbNameLabel);
		panel.add(dbNameField);
		panel.add(userLabel);
		panel.add(userField);
		panel.add(passwordLabel);
		panel.add(passwordField);
		panel.add(infoLabel);
		panel.add(connectButton);

		add(panel);
		setVisible(true);

		if (!savedUser.isEmpty()) {
			userField.setText(savedUser);
		}
		if (!savedPassword.isEmpty()) {
			passwordField.setText(savedPassword);
		}
		if (!savedDbName.isEmpty()) {
			dbNameField.setText(savedDbName);
		}
	}

	private void saveCredentials(String user, String password, String dbName, String dbType) {
		Preferences prefs = Preferences.userRoot().node(dbType);
		prefs.put("user", user);
		prefs.put("password", password);
		prefs.put("dbName", dbName);
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == dbComboBox) {
			db = (String) dbComboBox.getSelectedItem();
			switch (db) {
			case "Oracle":
				userLabel.setText("Username (Oracle):");
				break;
			case "MySQL":
				userLabel.setText("Username (MySQL):");
				break;
			case "SQL Server":
				userLabel.setText("Username (SQL Server):");
				break;
			}
			infoLabel.setText("Please enter your login information for " + db);
		} else if (e.getSource() == connectButton) {
			String user = userField.getText();
			String password = new String(passwordField.getPassword());
			String dbName = dbNameField.getText();

			switch (db) {
			case "Oracle":
				try {
					Class.forName("oracle.jdbc.driver.OracleDriver");
					String url = "jdbc:oracle:thin:@localhost:1521:xe";
					Connection con = DriverManager.getConnection(url, user, password);
					infoLabel.setText("Login successful!");
					saveCredentials("userOracle", "passwordOracle", "dbNameOracle", "Oracle");
				} catch (SQLException ex) {
					infoLabel.setText("Login failed: " + ex.getMessage());

				} catch (ClassNotFoundException ex) {
					infoLabel.setText("JDBC driver not found: " + ex.getMessage());
				}
				break;
			case "MySQL":
				try {
					Class.forName("com.mysql.cj.jdbc.Driver");
					Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + dbName + "", user,
							password);

					infoLabel.setText("Login successful!");
					saveCredentials("userMySQL", "passwordMySQL", "dbNameMySQL", "Mysql");
					System.out.println("success");
				} catch (SQLException ex) {
					infoLabel.setText("Login failed: " + ex.getMessage());
				} catch (ClassNotFoundException ex) {
					infoLabel.setText("JDBC driver not found: " + ex.getMessage());
				}
				break;
			case "SQL Server":
				try {
					Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
					String url = "jdbc:sqlserver://localhost:1433;databaseName=" + dbName + ";user=" + user
							+ ";password=" + password;
					Connection con = DriverManager.getConnection(url);
					infoLabel.setText("Login successful!");
					saveCredentials("userSQLServer", "passwordSQLServer", "dbNameSQLServer", "Sql Server");
				} catch (SQLException ex) {
					infoLabel.setText("Login failed: " + ex.getMessage());
				} catch (ClassNotFoundException ex) {
					infoLabel.setText("JDBC driver not found: " + ex.getMessage());
				}
				break;
			}
		}
	}

	public static void main(String[] args) {
		new PageOfConnexion();
	}
}
