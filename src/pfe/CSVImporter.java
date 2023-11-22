package pfe;

import java.awt.EventQueue;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;

public class CSVImporter extends JFrame implements ActionListener {

	private JPanel contentPane;
	private static final long serialVersionUID = 1L;
	private JTextField filePathTextField;
	private JButton browseButton;
	private JButton importButton;
	private JLabel statusLabel;
	static int option = 0;
	static String db;

	public String user;
	public String password;
	public String database;
	// JFrame frame;
	static int i = 0;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
	}

	/**
	 * Create the frame.
	 */
	public CSVImporter(int option, String db, String user, String password, String database) {

		super("CSV Importer");

		this.option = option;
		this.db = db;
		this.password = password;
		this.user = user;
		this.database = database;
		Image icon = new ImageIcon(this.getClass().getResource("/img/log.png")).getImage();
		this.setIconImage(icon);

		filePathTextField = new JTextField(20);
		browseButton = new JButton("Browse");
		importButton = new JButton("Import");
		statusLabel = new JLabel("Please select a CSV file to import.");

		JPanel mainPanel = new JPanel();
		mainPanel.add(new JLabel("File path:"));
		mainPanel.add(filePathTextField);
		mainPanel.add(browseButton);
		mainPanel.add(importButton);
		mainPanel.add(statusLabel);

		Container contentPane = getContentPane();
		contentPane.add(mainPanel, BorderLayout.CENTER);

		browseButton.addActionListener(this);
		importButton.addActionListener(this);

		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		pack();
		setVisible(true);
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == browseButton) {
			JFileChooser fileChooser = new JFileChooser();
			FileNameExtensionFilter filter = new FileNameExtensionFilter("CSV files", "csv");
			fileChooser.setFileFilter(filter);
			int result = fileChooser.showOpenDialog(this);
			if (result == JFileChooser.APPROVE_OPTION) {
				filePathTextField.setText(fileChooser.getSelectedFile().getAbsolutePath());
			}
		} else if (e.getSource() == importButton) {
			String filePath = filePathTextField.getText();
			if (filePath.isEmpty()) {
				statusLabel.setText("Please select a CSV file to import.");
				return;
			}

			Thread Thread = new Thread(new Runnable() {

				@Override
				public void run() {
<<<<<<< HEAD
					  try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            String[] values;
           // importButton.enable(true);
            statusLabel.setText("Import en cour ....");
            for (int i = 0; (line = reader.readLine()) != null; i++) {
                values = line.split(";");
                
                
                
                connection con = new connection();
               // connection con1 = new connection();
                PreparedStatement pstmt;
                String sql = null;
                switch (db) {
                case "Oracle": 
					con.connection(CSVImporter.this.user, CSVImporter.this.password);
					try  {
	                	
	                	if ( option== 1) {
	                	    sql = "INSERT INTO LIEN VALUES (?, ?)";
	                   }else if(option==2) {
	                	    sql = "INSERT INTO SITE VALUES (?, ?)";
	                   }
	                    pstmt = con.connection.prepareStatement(sql);
	                    pstmt.setString(1, values[0]);
	                    pstmt.setString(2, values[1]);
	                    pstmt.executeUpdate();
	                    con.connection.close();
	                    System.out.println(i);
	                } catch (SQLException ex) {
	                    statusLabel.setText("Error: " + ex.getMessage());
	                    return;
	                }
					break;
				case "MySQL":
                
					
					try  {
						
						Class.forName("com.mysql.cj.jdbc.Driver");
						Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + CSVImporter.this.database + "",
								CSVImporter.this.user, CSVImporter.this.password);
	                	
	                	if ( option== 1) {
	                	    sql = "INSERT INTO LIEN VALUES (?, ?)";
	                   }else if(option==2) {
	                	   sql = "INSERT INTO site VALUES (?, ?)";
	                   }
	                     pstmt = conn.prepareStatement(sql);
	                    pstmt.setString(1, values[0]);
	                    pstmt.setString(2, values[1]);
	                    pstmt.executeUpdate();
	                   conn.close();
	                    System.out.println(i);
	                } catch (SQLException ex) {
	                    statusLabel.setText("Error: " + ex.getMessage());
	                    System.out.println("error");
	                    return;
	                }
                
					break;
				
                }
                
                
         //Fin de Switch
                
            }
            statusLabel.setText("Import complete.");
        } catch (Exception ex) {
            statusLabel.setText("Error: " + ex.getMessage());
        }
=======
					try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
						String line;
						String[] values;
						statusLabel.setText("Import en cour ....");
						for (int i = 0; (line = reader.readLine()) != null; i++) {
							values = line.split(";");

							connection con = new connection();
							PreparedStatement pstmt;
							String sql = null;
							switch (db) {
							case "Oracle":
								con.connection(CSVImporter.this.user, CSVImporter.this.password);
								try {

									if (option == 1) {
										sql = "INSERT INTO LIEN VALUES (?, ?)";
									} else if (option == 2) {
										sql = "INSERT INTO SITE VALUES (?, ?)";
									}
									pstmt = con.connection.prepareStatement(sql);
									pstmt.setString(1, values[0]);
									pstmt.setString(2, values[1]);
									pstmt.executeUpdate();
									con.connection.close();
									System.out.println(i);
								} catch (SQLException ex) {
									statusLabel.setText("Error: " + ex.getMessage());
									return;
								}
								break;
							case "MySQL":

								try {

									Class.forName("com.mysql.cj.jdbc.Driver");
									Connection conn = DriverManager.getConnection(
											"jdbc:mysql://localhost:3306/" + CSVImporter.this.database + "",
											CSVImporter.this.user, CSVImporter.this.password);

									if (option == 1) {
										sql = "INSERT INTO LIEN VALUES (?, ?)";
									} else if (option == 2) {
										sql = "INSERT INTO site VALUES (?, ?)";
									}
									pstmt = conn.prepareStatement(sql);
									pstmt.setString(1, values[0]);
									pstmt.setString(2, values[1]);
									pstmt.executeUpdate();
									conn.close();
									System.out.println(i);
								} catch (SQLException ex) {
									statusLabel.setText("Error: " + ex.getMessage());
									System.out.println("error");
									return;
								}

								break;

							}

						}
						statusLabel.setText("Import complete.");
					} catch (Exception ex) {
						statusLabel.setText("Error: " + ex.getMessage());
					}
>>>>>>> aa687c2763f728027ebfb8d31e042c5811b41839
				}
			});
			Thread.start();

		}
	}

}
