package pfe;

import java.awt.EventQueue;
import java.awt.Menu;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JSeparator;
import javax.swing.Timer;

import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.awt.event.ActionEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import java.awt.Color;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Image;

import javax.swing.border.LineBorder;
import javax.xml.parsers.ParserConfigurationException;
<<<<<<< HEAD
=======
import javax.xml.transform.TransformerException;
>>>>>>> aa687c2763f728027ebfb8d31e042c5811b41839
import javax.xml.xpath.XPathExpressionException;

import org.xml.sax.SAXException;

import javax.swing.UIManager;
import java.awt.Choice;
import javax.swing.JPopupMenu;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Menu_global {
	public String user;
	public String password;
	public String database;

	static int stop = 0;
	static JFrame frame;
	static int option = 0;
	static String db;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 * 
	 * @wbp.parser.entryPoint
	 */
	public Menu_global(String user, String password, String db, String database) {
		this.password = password;
		this.user = user;
		this.database = database;
		this.db = db;

		initialize();
		Image icon = new ImageIcon(this.getClass().getResource("/img/log.png")).getImage();
		frame.setIconImage(icon);
	}

	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 665, 460);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);

		JMenu mnNewMenu = new JMenu("Importer");
		menuBar.add(mnNewMenu);

		JMenu mnNewMenu_2 = new JMenu("Excel");
		mnNewMenu.add(mnNewMenu_2);

		JMenuItem mntmNewMenuItem_4 = new JMenuItem("LIEN");
		mnNewMenu_2.add(mntmNewMenuItem_4);

		JMenuItem mntmNewMenuItem_5 = new JMenuItem("SITE");
		mnNewMenu_2.add(mntmNewMenuItem_5);

		JMenu mnNewMenu_6 = new JMenu("CSV");
		mnNewMenu.add(mnNewMenu_6);

		JMenuItem mntmNewMenuItem = new JMenuItem("LIEN");
		mntmNewMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				option = 1;
				CSVImporter csv = new CSVImporter(option, Menu_global.this.db, Menu_global.this.user,
						Menu_global.this.password, Menu_global.this.database);
			}
		});
		mnNewMenu_6.add(mntmNewMenuItem);

		JMenuItem mntmNewMenuItem_3 = new JMenuItem("SITE");
		mntmNewMenuItem_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				option = 2;
				CSVImporter csv = new CSVImporter(option, db, Menu_global.this.user, Menu_global.this.password,
						Menu_global.this.database);
			}
		});
		mnNewMenu_6.add(mntmNewMenuItem_3);

		JMenu mnNewMenu_1 = new JMenu("Exporter ");
		menuBar.add(mnNewMenu_1);

		JMenuItem mntmExcel = new JMenuItem("Excel");
		mnNewMenu_1.add(mntmExcel);

		JMenuItem mntmCsv_1 = new JMenuItem("CSV");
		mntmCsv_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				StorageLocationChooser win_csv = new StorageLocationChooser(Menu_global.this.user,
						Menu_global.this.password, Menu_global.this.database, Menu_global.this.db);
				win_csv.setVisible(true);
			}
		});
		mnNewMenu_1.add(mntmCsv_1);

		JMenu mnNewMenu_3 = new JMenu("Chemin");
		menuBar.add(mnNewMenu_3);

		JMenuItem mntmPourUnSite = new JMenuItem("Pour un site");
		mntmPourUnSite.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				result_win win = new result_win(Menu_global.this.user, Menu_global.this.password,
						Menu_global.this.database, Menu_global.this.db);
				win.frame.setVisible(true);

			}
		});
		mnNewMenu_3.add(mntmPourUnSite);
		JPanel panel = new JPanel();
		panel.setBackground(Color.WHITE);
		panel.setBounds(167, 123, 318, 147);
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		JButton Stop_btn = new JButton("Arréter");
		Stop_btn.setFont(new Font("Tahoma", Font.BOLD, 18));
		Stop_btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				stop = 1;
			}
		});
		Stop_btn.setBounds(92, 89, 135, 47);
		panel.add(Stop_btn);

		JLabel lblNewLabel = new JLabel("Traitement en cours...");
		lblNewLabel.setFont(new Font("Arial Black", Font.BOLD, 19));
		lblNewLabel.setBounds(39, 37, 269, 30);

		Timer timer = new Timer(500, new ActionListener() {
			private int count = 0;
			private String[] dots = { ".", "..", "..." };

			public void actionPerformed(ActionEvent e) {
				count++;
				if (count >= dots.length) {
					count = 0;
				}
				lblNewLabel.setText("Traitement en cours" + dots[count]);
			}
		});
		timer.start();

		panel.add(lblNewLabel);
		panel.setVisible(false);

		JMenuItem mntmPourTous = new JMenuItem("Pour tous");
		mntmPourTous.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				panel.setVisible(true);
				Thread Thread = new Thread(new Runnable() {

					connection con = new connection();

					@Override
					public void run() {

						switch (db) {
						case "Oracle":

							int id_max = 0;
							int i = 0;

							con.connection(Menu_global.this.user, Menu_global.this.password);
							id_max = con.get_MAX();
							try {
								con.connection.close();
							} catch (SQLException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
							int index = 0;
							for (String element : con.GetALLSites()) {
								i++;
								index++;
								con.connection(Menu_global.this.user, Menu_global.this.password);
								String ip = con.get_ip_site(element);
								String[] tab = con.get_SITES(ip, con.get_ip_site(element));
								List<String> List_principal = con.STOCKAGE_Principal(tab);

								StringBuilder sb = new StringBuilder();
								for (String s : List_principal) {
									sb.append(s);

									if (s == "]]") {
										sb.append(" ");
									}
								}
								String result = sb.toString().trim();

								String query = "INSERT INTO RESULTAT VALUES (?,?,?,?)";
								PreparedStatement statement = null;

								try {
									statement = con.connection.prepareStatement(query);
									statement.setString(1, element);
									statement.setInt(2, id_max);
									Date date = new Date();
									SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
									String formattedDate = sdf.format(date);
									statement.setString(3, formattedDate);
									statement.setString(4, result);

									statement.executeUpdate();
								} catch (SQLException e1) {
									e1.printStackTrace();
								} finally {
									if (statement != null) {
										try {
											statement.close();
										} catch (SQLException e1) {
											e1.printStackTrace();
										}
									}
								}
								try {
									con.connection.close();
								} catch (SQLException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}

								if (stop == 1) {
									stop = 0;
									panel.setVisible(false);
									con.connection(Menu_global.this.user, Menu_global.this.password);
									con.delete_traitement(id_max);
									JOptionPane.showMessageDialog(null, "DELETE With Success");
									try {
										con.connection.close();
									} catch (SQLException e1) {
										// TODO Auto-generated catch block
										e1.printStackTrace();
									}

									break;
								}
								if (con.GetALLSites().lastIndexOf(element) == index) {
									panel.setVisible(false);
								}
							}

							break;
						case "MySQL":
							int id_max1 = 0;
							int i1 = 0;

							con.connection2(Menu_global.this.user, Menu_global.this.password,
									Menu_global.this.database);
							id_max1 = con.get_MAX1();
							try {
								con.connection.close();
							} catch (SQLException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
							int index1 = 0;
							for (String element : con.GetALLSites()) {
								i1++;
								index1++;
								con.connection2(Menu_global.this.user, Menu_global.this.password,
										Menu_global.this.database);
								String ip = con.get_ip_site(element);
								String[] tab = con.get_SITES(ip, con.get_ip_site(element));
								List<String> List_principal = con.STOCKAGE_Principal(tab);

								StringBuilder sb = new StringBuilder();
								for (String s : List_principal) {
									sb.append(s);

									if (s == "]]") {
										sb.append(" ");
									}
								}
								String result = sb.toString().trim();

								String query = "INSERT INTO RESULTAT  VALUES (?,?,?,?)";
								PreparedStatement statement = null;

								try {
									statement = con.connection.prepareStatement(query);
									statement.setString(1, element);
									statement.setInt(2, id_max1);
									Date date = new Date();
									SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
									String formattedDate = sdf.format(date);
									statement.setString(3, formattedDate);
									statement.setString(4, result);

									statement.executeUpdate();
								} catch (SQLException e1) {
									e1.printStackTrace();
								} finally {
									if (statement != null) {
										try {
											statement.close();
										} catch (SQLException e1) {
											e1.printStackTrace();
										}
									}
								}
								try {
									con.connection.close();
								} catch (SQLException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}

								if (stop == 1) {
									stop = 0;
									panel.setVisible(false);
									con.connection2(Menu_global.this.user, Menu_global.this.password,
											Menu_global.this.database);
									con.delete_traitement(id_max1);
									JOptionPane.showMessageDialog(null, "DELETE With Success");
									try {
										con.connection.close();
									} catch (SQLException e1) {
										// TODO Auto-generated catch block
										e1.printStackTrace();
									}

									break;
								}
								if (con.GetALLSites().lastIndexOf(element) == index1) {
									panel.setVisible(false);
								}
							}

							break;
						case "SQL Server":

							break;
						}
					}
				});
				Thread.start();
			}
		});
		mnNewMenu_3.add(mntmPourTous);

		JMenu mnNewMenu_5 = new JMenu("Configuration");
		menuBar.add(mnNewMenu_5);

		JMenuItem mntmNewMenuItem_1 = new JMenuItem("Serveur");
		mntmNewMenuItem_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.setVisible(false);
				connexion_interface window = null;
				try {
					window = new connexion_interface();
				} catch (XPathExpressionException | ParserConfigurationException | SAXException | IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				window.frame.setVisible(true);
			}
		});
		mnNewMenu_5.add(mntmNewMenuItem_1);

<<<<<<< HEAD
		JMenuItem mntmNewMenuItem_2 = new JMenuItem("Creer les tables");
		mntmNewMenuItem_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				connection con = new connection();
				
				switch (db) {
				case "Oracle":
					con.connection(Menu_global.this.user, Menu_global.this.password);
					
=======
		JMenuItem mntmNewMenuItem_2 = new JMenuItem("Créer les tables");
		mntmNewMenuItem_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				connection con = new connection();

				switch (db) {
				case "Oracle":
					con.connection(Menu_global.this.user, Menu_global.this.password);

>>>>>>> aa687c2763f728027ebfb8d31e042c5811b41839
					con.drop_table();
					con.create_table_orcl();
					JOptionPane.showMessageDialog(null, "Create With Success");
					break;
				case "MySQL":
<<<<<<< HEAD
					con.connection2(Menu_global.this.user, Menu_global.this.password,Menu_global.this.database);
=======
					con.connection2(Menu_global.this.user, Menu_global.this.password, Menu_global.this.database);
>>>>>>> aa687c2763f728027ebfb8d31e042c5811b41839
					con.drop_table();
					con.create_table();
					JOptionPane.showMessageDialog(null, "Create With Success");
					break;
				}
			}
		});
		mnNewMenu_5.add(mntmNewMenuItem_2);
		frame.getContentPane().setLayout(null);

		connection conn = new connection();

		switch (db) {

		case "Oracle":
			conn.connection(Menu_global.this.user, Menu_global.this.password);
			if (conn.orcl_exist() != 0) {
				mntmNewMenuItem_2.setEnabled(false);
				break;
			} else {
				mntmNewMenuItem_2.setEnabled(true);
				JOptionPane.showMessageDialog(null, "Cree les tables !!!!");
			}
			break;
		case "MySQL":
			conn.connection2(Menu_global.this.user, Menu_global.this.password, Menu_global.this.database);
			if (conn.mysql_exist(Menu_global.this.database) != 0) {
				mntmNewMenuItem_2.setEnabled(false);

			} else {
				mntmNewMenuItem_2.setEnabled(true);
				JOptionPane.showMessageDialog(null, "Cree les tables !!!!");
			}
			break;
		}

	}

	private static void addPopup(Component component, final JPopupMenu popup) {
		component.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}

			public void mouseReleased(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}

			private void showMenu(MouseEvent e) {
				popup.show(e.getComponent(), e.getX(), e.getY());
			}
		});
	}
}