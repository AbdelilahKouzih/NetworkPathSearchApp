package pfe;

import javax.swing.*;

public class MessageDialogExample {

	public MessageDialogExample() {
		inis();
	}

	static void inis() {
		JFrame mainFrame = new JFrame("Stop Button Example");

		JLabel messageLabel = new JLabel("Le traitement est en cours...");

		JButton stopButton = new JButton("Stop");
		stopButton.addActionListener(e -> {
			intermediare.stop = 1;
			messageLabel.setText("Le traitement a été arrêté.");
			stopButton.setEnabled(false);
		});

		JPanel mainPanel = new JPanel();
		mainPanel.add(messageLabel);
		mainPanel.add(stopButton);
		mainFrame.add(mainPanel);

		mainFrame.setSize(300, 100);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setVisible(true);
	};

}
