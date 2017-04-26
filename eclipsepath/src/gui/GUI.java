package gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class GUI extends JFrame{
	// -> why the f*** do we need this?
	private static final long serialVersionUID = 1L;
	
	// Main-Frame
	protected JMenuBar up;
	protected JMenu men1;
	
	// Other Modals
	JDialog login, ags;
	
	// Login-Frame
	protected JLabel serverLabel, userLabel, passwordLabel;
	protected JTextField serverField, userField;
	protected JPasswordField passwordField;
	protected JButton loginButton, exitButton;
	
	public static void main(String[] args){
		new GUI();
	}
	
	/**
	 * @author Agent77326
	 */
	protected GUI(){
		setTitle("Verteilungsalgorithmus");
		setSize(500, 400);
		setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
		Container pane = getContentPane();
		pane.setLayout(new GridLayout (4, 2));
		up = new JMenuBar();
		men1 = new JMenu("File");
		up.add(men1);
		men1.add(new JMenuItem("New"));
		men1.add(new JMenuItem("Open File..."));
		setJMenuBar(up);
		login = new JDialog(this, "Server Login", true);
		setVisible(true);
		/*String[][] table = new String[100][2];
		for(int i = 0; i < 100; i++){
			table[i] = new String[]{"Kochen","Peter"};
		}
		showTable(new String[]{"AG", "Teilnehmer"}, table);*/
		showLogin();
	}
	
	protected void showTable(String[] colName, String[][] data){
	    JDialog table = new JDialog(this, "Datatable", true);
	    table.setSize(1280, 720);
	    table.setLocationRelativeTo(null);
	    JTable t = new JTable(data, colName){
			private static final long serialVersionUID = 1L;
			public boolean isCellEditable(int rowIndex, int colIndex) {
	    		return false;
    		}
		};
	    table.getContentPane().add(new JScrollPane(t), BorderLayout.CENTER);
	    table.setVisible(true);
	}
	
	protected void showLogin(){
		login.getContentPane().setLayout(new GridLayout (4, 2));
		login.setSize(400, 160);
		login.setLocationRelativeTo(null);
		serverLabel = new JLabel("SQL-Server");
		userLabel = new JLabel("Benutzername");
		passwordLabel = new JLabel("Password");
		serverField = new JTextField();
		userField = new JTextField();
		passwordField = new JPasswordField();
		loginButton = new JButton("Login");
		loginButton.addActionListener(new LoginButtonHandler());
		exitButton = new JButton("Exit");
		exitButton.addActionListener(new ExitButtonHandler());
		login.add(serverLabel, BorderLayout.CENTER);
		login.add(serverField, BorderLayout.CENTER);
		login.add(userLabel, BorderLayout.CENTER);
		login.add(userField, BorderLayout.CENTER);
		login.add(passwordLabel, BorderLayout.CENTER);
		login.add(passwordField, BorderLayout.CENTER);
		login.add(loginButton, BorderLayout.CENTER);
		login.add(exitButton, BorderLayout.CENTER);
		login.setVisible(true);
	}
	
	public class LoginButtonHandler implements ActionListener{
		public void actionPerformed(ActionEvent e){
			
		}
	}
	
	public class ExitButtonHandler implements ActionListener{
		public void actionPerformed(ActionEvent e){
			System.exit(0);
		}
	}
}
