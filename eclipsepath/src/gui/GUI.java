package gui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import db.DBManager;

public class GUI extends JFrame{
	// -> why the f*** do we need this?
	private static final long serialVersionUID = 1L;
	
	// Main-Frame
	protected JMenuBar up;
	protected JMenu men1;
	
	// Other Modals
	protected JDialog login, error, ags;
	
	// Login-Frame
	protected JTextField loginServerField, loginServerPortField, loginUserField, loginDatabaseField;
	protected JPasswordField loginPasswordField;
	protected JButton loginButton, exitButton;
	
	// Error-Frame
	protected JButton errorCloseButton;
	
	
	// --------------------------------------------------
	// Database
	protected DBManager dbm;
	
	
	
	
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
		pane.setLayout(new GridLayout(4, 2));
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
		login.getContentPane().setLayout(new GridLayout(6, 2));
		login.setSize(400, 160);
		login.setLocationRelativeTo(null);
		loginServerField = new JTextField();
		loginServerPortField = new JTextField();
		loginUserField = new JTextField();
		loginPasswordField = new JPasswordField();
		loginDatabaseField = new JTextField();
		loginButton = new JButton("Login");
		loginButton.addActionListener(new LoginButtonHandler());
		exitButton = new JButton("Exit");
		exitButton.addActionListener(new ExitButtonHandler());
		login.add(new JLabel("SQL-Server IP"), BorderLayout.CENTER);
		login.add(loginServerField, BorderLayout.CENTER);
		login.add(new JLabel("Server-Port"), BorderLayout.CENTER);
		login.add(loginServerPortField, BorderLayout.CENTER);
		login.add(new JLabel("Benutzername"), BorderLayout.CENTER);
		login.add(loginUserField, BorderLayout.CENTER);
		login.add(new JLabel("Password"), BorderLayout.CENTER);
		login.add(loginPasswordField, BorderLayout.CENTER);
		login.add(new JLabel("Datenbank"), BorderLayout.CENTER);
		login.add(loginDatabaseField, BorderLayout.CENTER);
		login.add(loginButton, BorderLayout.CENTER);
		login.add(exitButton, BorderLayout.CENTER);
		// Close all windows when login-frame is closed
		login.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e){
				System.exit(0);
			}
		});
		login.setVisible(true);
	}
	
	protected void showError(String msg){
	    error = new JDialog(this, "Error", true);
	    error.setSize(400, 160);
	    error.setLocationRelativeTo(null);
	    error.getContentPane().setLayout(new GridLayout(2, 1));
	    error.add(new JLabel(msg), BorderLayout.CENTER);
	    errorCloseButton = new JButton("OK");
	    errorCloseButton.addActionListener(new ErrorCloseButtonHandler());
	    error.add(errorCloseButton, BorderLayout.CENTER);
	    error.setVisible(true);
	}
	
	protected class LoginButtonHandler implements ActionListener{
		public void actionPerformed(ActionEvent e){
			String error = null;
			if(loginServerField.getText()==null || loginServerField.getText().equals("")){
				error = "Es wurden nicht alle Felder ausgefüllt";
			}
			else if(loginServerPortField.getText()==null || loginServerPortField.getText().equals("")){
				error = "Es wurden nicht alle Felder ausgefüllt";
			}
			else if(loginUserField.getText()==null || loginUserField.getText().equals("")){
				error = "Es wurden nicht alle Felder ausgefüllt";
			}
			else if(loginDatabaseField.getText()==null || loginDatabaseField.getText().equals("")){
				error = "Es wurden nicht alle Felder ausgefüllt";
			}
			else{
				if(loginPasswordField.getPassword()==null){
					error = "Es wurden nicht alle Felder ausgefüllt";
				}
				else{
					for(char s: loginPasswordField.getPassword()){
						if(s == ' '){
							error = "Es wurden nicht alle Felder ausgefüllt";
						}
					}
				} 
			}
			
			if(error!=null){
				showError(error);
			}
			else{
				dbm = new DBManager();
				dbm.connect(loginServerField.getText(),
						Integer.parseInt(loginServerPortField.getText()),
						loginUserField.getText(),
						String.valueOf(loginPasswordField.getPassword()),
						loginDatabaseField.getText());
				if(dbm.isConnected()){
					dbm.initializeJavaObjectsFromDB();
					login.dispose();
				}
				else{
					showError("Es konnte keine Verbindung zur Datenbank hergestellt werden");
				}
			}
		}
	}
	
	protected class ErrorCloseButtonHandler implements ActionListener{
		public void actionPerformed(ActionEvent e){
			error.dispose();
		}
	}
	
	protected class ExitButtonHandler implements ActionListener{
		public void actionPerformed(ActionEvent e){
			System.exit(0);
		}
	}
}
