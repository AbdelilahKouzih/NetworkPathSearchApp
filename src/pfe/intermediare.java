package pfe;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JComboBox;
import java.awt.Choice;

public class intermediare {

	JFrame frame;

	static int stop = 0;
	JLabel lblNewLabel = new JLabel("Traitement en cours...");
	public String user;
	public String password;

	public intermediare(String user, String password) {

		this.password = password;
		this.user = user;

		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 555, 341);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JButton btn1 = new JButton("Cherche site");
		btn1.setBounds(55, 96, 118, 42);
		btn1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				result_win window = new result_win(intermediare.this.user, intermediare.this.password,
						intermediare.this.password, intermediare.this.password);
				window.frame.setVisible(true);

			}
		});
		frame.getContentPane().setLayout(null);
		frame.getContentPane().add(btn1);

		// ------------------------

		JButton Stop_btn = new JButton("Stop");
		Stop_btn.setBounds(223, 250, 89, 23);
		Stop_btn.setVisible(false);
		lblNewLabel.setBounds(184, 176, 193, 42);
		lblNewLabel.setVisible(false);
		Stop_btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				stop = 1;
			}
		});
		frame.getContentPane().add(Stop_btn);

		JButton btn2 = new JButton("Traiter tous");
		btn2.setBounds(201, 96, 118, 42);
		btn2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Stop_btn.setVisible(true);
				lblNewLabel.setVisible(true);

				Thread Thread = new Thread(new Runnable() {

					@Override
					public void run() {
						int id_max = 0;
						connection con = new connection();
						int i = 0;
						con.connection(intermediare.this.user, intermediare.this.password);
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
							System.out.println(i);
							con.connection(intermediare.this.user, intermediare.this.password);
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
								statement.setInt(2, id_max);
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
								Stop_btn.setVisible(false);
								lblNewLabel.setVisible(false);
								con.connection(intermediare.this.user, intermediare.this.password);
								con.delete_traitement(id_max);
								try {
									con.connection.close();
								} catch (SQLException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
								break;

							}

						}

					}

				});
				Thread.start();
			}
		});
		frame.getContentPane().add(btn2);

		JButton btn3 = new JButton("New button");
		btn3.setBounds(355, 96, 118, 42);
		frame.getContentPane().add(btn3);

		lblNewLabel.setFont(lblNewLabel.getFont().deriveFont(lblNewLabel.getFont().getSize() + 4f));
		frame.getContentPane().add(lblNewLabel);

	}
}
