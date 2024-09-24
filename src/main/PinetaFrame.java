package main;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.Random;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.MouseInfo;
import java.awt.Robot;
import java.awt.AWTException;
import java.awt.Color;
import javax.swing.JTextField;

public class PinetaFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private JTextField textFieldMinutes;
	private JTextField textFieldSeconds;
	private JLabel lblTitle;
	private JButton btnStart;
	private JButton btnExit;
	private JLabel lblMinutes;
	private JLabel lblSeconds;
	private JLabel lblTimeToGo;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PinetaFrame frame = new PinetaFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public PinetaFrame() {
		btnStart = createButtonStart();
		btnExit = createButtonExit();
		lblTitle = createLblTitle();
		textFieldMinutes = createTextFieldMinutes();
		textFieldSeconds = createTextFieldSeconds();
		lblMinutes = createLabelMinutes();		
		lblSeconds = createLblSeconds();
		lblTimeToGo = createLblTimeToGo();
		createContentPane();
		
		btnStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				letsStart();
			}
		});
		
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
	}
	
	
	private void createContentPane() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 704, 397);
		JPanel contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		contentPane.add(lblTitle);
		contentPane.add(btnStart);
		contentPane.add(textFieldSeconds);
		contentPane.add(textFieldMinutes);
		contentPane.add(lblMinutes);
		contentPane.add(lblSeconds);
		contentPane.add(lblTimeToGo);
		contentPane.add(btnExit);
		setContentPane(contentPane);
	}
	
	private JButton createButtonExit() {
		JButton btnNewButton = new JButton("EXIT");
		btnNewButton.setForeground(new Color(255, 0, 0));
		btnNewButton.setBounds(307, 324, 69, 23);
		return btnNewButton;
	}

	private JLabel createLblTimeToGo() {
		JLabel lblTimeToGo = new JLabel("");
		lblTimeToGo.setBounds(307, 247, 69, 14);
		return lblTimeToGo;
	}

	private JLabel createLblSeconds() {
		JLabel lblSeconds = new JLabel("seconds");
		lblSeconds.setBounds(410, 114, 79, 14);
		return lblSeconds;
	}

	private JLabel createLabelMinutes() {
		JLabel lblMinutes = new JLabel("minutes");
		lblMinutes.setBounds(298, 114, 46, 14);
		return lblMinutes;
	}

	private JTextField createTextFieldSeconds() {
		JTextField textFieldSeconds = new JTextField();
		textFieldSeconds.setBounds(354, 110, 46, 23);
		textFieldSeconds.setColumns(10);
		return textFieldSeconds;
	}

	private JTextField createTextFieldMinutes() {
		JTextField textFieldMinutes = new JTextField();
		textFieldMinutes.setBounds(242, 110, 46, 23);
		textFieldMinutes.setColumns(10);
		return textFieldMinutes;
	}

	private JLabel createLblTitle() {
		JLabel lblTitle = new JLabel("CHOOSE HOW MUCH TIME OF INACTIVITY \r\nTHE MOUSE HAVE TO MOVE AFTER");
		lblTitle.setBounds(10, 26, 671, 69);
		lblTitle.setForeground(new Color(255, 0, 0));
		lblTitle.setFont(new Font("Arial Black", Font.PLAIN, 15));
		lblTitle.setVerticalAlignment(SwingConstants.TOP);
		return lblTitle;
	}

	private JButton createButtonStart() {
		JButton btnStart = new JButton("START");
		btnStart.setBounds(298, 183, 89, 23);
		return btnStart;
	}
	
	/**
	 * button start clicked method
	 */
	private void letsStart() {
		Integer minutes;
		Integer seconds;
		String minutesString = textFieldMinutes.getText();
		minutesString = isEmptyString(minutesString) ? "0" : minutesString;
		String secondsString = textFieldSeconds.getText();
		secondsString = isEmptyString(secondsString) ? "0" : secondsString;
		try {
			minutes = Integer.valueOf(minutesString);
			seconds = Integer.valueOf(secondsString);
			if(minutes < 0 || minutes > 59 || seconds < 0 || seconds > 59) {
				throw new NumberFormatException();
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(this, "Enter a valid Number", 
                    "ERROR", JOptionPane.ERROR_MESSAGE);
			return;
		}
		seconds = minutes * 60 + seconds;
		
		moveTheMouse(seconds);
	}
	
	/**
	 * Check if a String is empty or null
	 * @param obj
	 * @return true if param obj is empty or null
	 */
	private boolean isEmptyString(String obj) {
		return (obj == null) || (obj != null && obj.isEmpty());
	}
	
	
	/**
	 * manage the mouse movement
	 * @param seconds
	 */
	private void moveTheMouse(int seconds) {
		Robot robot = null;
		Random random = new Random();
		try {
			robot = new Robot();
		} catch (AWTException e) {
			e.printStackTrace();
			return;
		}
 
		int previousX = -1;
		int previousY = -1;
		int timer = seconds;
		while (true) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			int x = MouseInfo.getPointerInfo().getLocation().x;
			int y = MouseInfo.getPointerInfo().getLocation().y;
			if (x != previousX || y != previousY) {
				// si è mosso
				timer = seconds;
				// TODO mostrare la label aggiornata, non sono riuscito a farla aggiornare (colpa del btnStart.addActionListener che blocca la grafica)
				lblTimeToGo.setText(getTime(seconds));
			} else {
				// sempre fermo
				timer--;
				// TODO mostrare la label aggiornata, non sono riuscito a farla aggiornare (colpa del btnStart.addActionListener che blocca la grafica)
				lblTimeToGo.setText(getTime(timer));
				if(timer == 0) {
					robot.mouseMove(random.nextInt(250), random.nextInt(250));
					timer = seconds;
				}
			}
			previousX = x;
			previousY = y;
		}

	}

	private String getTime(int seconds) {
		int m = seconds / 60;
		int s = seconds % 60;
		return m + ":" + s;
	}
}
