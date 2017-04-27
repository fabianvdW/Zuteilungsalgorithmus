package gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import Data.AG;
import Data.Person;
import Data.Rating;
import db.DBManager;

public class GUI extends JFrame{
	// -> why the f*** do we need this?
	private static final long serialVersionUID = 1L;
	
	// Main-Frame
	protected JMenuBar up;
	protected JMenu men1, men2;
	protected JMenuItem fileExportAG, fileExportPerson, fileExit, showAG, showPerson;
	
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
		up = new JMenuBar();
		men1 = new JMenu("File");
		fileExportAG = new JMenuItem("Export AGs");
		fileExportAG.addActionListener(new ExportAGHandler());
		fileExportPerson = new JMenuItem("Export Schüler");
		fileExportPerson.addActionListener(new ExportPersonenHandler());
		fileExit = new JMenuItem("Exit");
		fileExit.addActionListener(new ExitButtonHandler());
		men1.add(fileExportAG);
		men1.add(fileExportPerson);
		men1.add(fileExit);
		up.add(men1);
		men2 = new JMenu("Zeige");
		showAG = new JMenuItem("AGs");
		showAG.addActionListener(new ShowAGHandler());
		showPerson = new JMenuItem("Schüler");
		showPerson.addActionListener(new ShowPersonHandler());
		men2.add(showAG);
		men2.add(showPerson);
		up.add(men2);
		setJMenuBar(up);
		login = new JDialog(this, "Server Login", true);
		showLogin();
	}
	
	protected void showTable(String[] colName, String[][] data){
	    JDialog table = new JDialog(this, "Datatable", false);
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
	
	protected void showAGList(){
		String[][] agList = new String[Algorithmus.Verteilungsalgorithmus.ag.size()][5];
		int i = 0;
		String teilnehmer = null;
		for(AG ag: Algorithmus.Verteilungsalgorithmus.ag){
			agList[i][0] = "" + ag.getId();
			agList[i][1] = ag.getName();
			agList[i][2] = "" + ag.getMindestanzahl();
			agList[i][3] = "" + ag.getHoechstanzahl();
			if(ag.getTeilnehmer()==null){
				agList[i][4] = "";
			}
			else{
				teilnehmer = "";
				int j = 0;
				for(Person p: ag.getTeilnehmer()){
					teilnehmer += p.getName() + (j++<ag.getTeilnehmer().size() ? ", " : "");
				}
				agList[i][4] = teilnehmer;
			}
			i++;
		}
		String[] agListHead = new String[]{"ID", "Name", "Mindestanzahl", "Höchstanzahl", "Teilnehmer"};
		showTable(agListHead, agList);
	}
	
	protected void showPersonenList(){
		String[][] persList = new String[Algorithmus.Verteilungsalgorithmus.personen.size()][5];
		int i = 0;
		String ags = null;
		for(Person p: Algorithmus.Verteilungsalgorithmus.personen){
			persList[i][0] = "" + p.getId();
			persList[i][1] = p.getName();
			if(p.getBesuchteAG()==null){
				persList[i][2] = "";
			}
			else{
				persList[i][2] = "" + p.getBesuchteAG().getName();
			}
			if(p.getRatingAL()==null){
				persList[i][3] = "";
			}
			else{
				ags = "";
				int j = 0;
				for(Rating r: p.getRatingAL()){
					ags += r.getAG().getName() + ": " + r.getRatingValue() + (j++<p.getRatingAL().size() ? ", " : "");
				}
				persList[i][3] = ags;
			}
			i++;
		}
		String[] persListHead = new String[]{"ID", "Name", "Aktuelle AG", "Gewählte AGs"};
		showTable(persListHead, persList);
	}
	
	protected void showError(String msg){
	    error = new JDialog(this, "Error", true);
	    error.setSize(300, 120);
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
						if(s==' '){
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
					Algorithmus.Verteilungsalgorithmus.ag = new ArrayList<AG>();
					Algorithmus.Verteilungsalgorithmus.personen = new ArrayList<Person>();
					dbm.initializeJavaObjectsFromDB();
					login.dispose();
					getContentPane().setLayout(new GridLayout(4, 2));
					setVisible(true);
				}
				else{
					showError("Es konnte keine Verbindung zur Datenbank hergestellt werden");
				}
			}
		}
	}
	
	protected class ExportAGHandler implements ActionListener{
		public void actionPerformed(ActionEvent e){
			JFileChooser fileChooser = new JFileChooser();
			fileChooser.setAcceptAllFileFilterUsed(false);
			fileChooser.setDialogTitle("Tabellen-Export: AG");
			fileChooser.setFileFilter(new CSVFileFilter());
			int userSelection = fileChooser.showSaveDialog(GUI.this);
			 
			if(userSelection==JFileChooser.APPROVE_OPTION){
			    File fh = fileChooser.getSelectedFile();
			    System.out.println("Save as file: " + fh.getAbsolutePath());
			}
		}
	}
	
	protected class ExportPersonenHandler implements ActionListener{
		public void actionPerformed(ActionEvent e){
			JFileChooser fileChooser = new JFileChooser();
			fileChooser.setAcceptAllFileFilterUsed(false);
			fileChooser.setDialogTitle("Tabellen-Export: Personen");
			fileChooser.setFileFilter(new CSVFileFilter());
			int userSelection = fileChooser.showSaveDialog(GUI.this);
			 
			if(userSelection==JFileChooser.APPROVE_OPTION){
			    File fh = fileChooser.getSelectedFile();
			    System.out.println("Save as file: " + fh.getAbsolutePath());
			}
		}
	}
	
	protected class ShowAGHandler implements ActionListener{
		public void actionPerformed(ActionEvent e){
			showAGList();
		}
	}
	
	protected class ShowPersonHandler implements ActionListener{
		public void actionPerformed(ActionEvent e){
			showPersonenList();
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
