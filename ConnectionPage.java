package atc_project;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.Timer;

public class ConnectionPage extends JFrame implements ActionListener{
	
	RoundedJButton connect_button;
	RoundedJTextField user_name_field;
	RoundedJPasswordField password_field;
	JLabel access_label;
	
	Connection con;
	
	void executePage() throws IOException{
		setTitle("ATC/SQL_Manager");
		
		JLabel heading = new JLabel("SQL CONNECTION MANAGER");
		heading.setForeground(Color.LIGHT_GRAY);
		heading.setBounds(125, 0, 250, 175);
		heading.setFont(new Font("Calibri", Font.BOLD, 20));
		add(heading);
		
		JLabel user_name_label = new JLabel("User Name:");
		user_name_label.setForeground(Color.LIGHT_GRAY);
		user_name_label.setBounds(93, 175, 100, 30);
		add(user_name_label);
		
		JLabel password_label = new JLabel("Password:");
		password_label.setForeground(Color.LIGHT_GRAY);
		password_label.setBounds(100, 225, 100, 30);
		add(password_label);
		
		user_name_field = new RoundedJTextField(100);
		user_name_field.setBackground(Color.LIGHT_GRAY);
		user_name_field.setBounds(185, 175, 175, 30);
		user_name_field.setHorizontalAlignment(SwingConstants.CENTER);
		add(user_name_field);
		
		password_field = new RoundedJPasswordField(100);
		password_field.setBackground(Color.LIGHT_GRAY);
		password_field.setBounds(185, 225, 175, 30);
		password_field.setHorizontalAlignment(SwingConstants.CENTER);
		add(password_field);
		
		connect_button = new RoundedJButton("CONNECT");
		connect_button.setBounds(200, 300, 100, 30);
		connect_button.setBackground(Color.LIGHT_GRAY);
		connect_button.addActionListener(this);
		add(connect_button);
		
		access_label = new JLabel("");
		access_label.setForeground(Color.red);
		access_label.setBounds(225, 350, 175, 30);
		add(access_label);
		
		Container c = getContentPane();
		c.setBackground(Color.black);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(550, 200, 500, 500);
		setLayout(null);
		setVisible(true);
	}
	
	public void actionPerformed(ActionEvent ae) {
		if(ae.getSource() == connect_button) {
			String user_name = user_name_field.getText();
			String password = password_field.getText();
			con = null;
			try {
				con = DriverManager.getConnection("jdbc:mysql:///atc_database", user_name, password);			
			}catch(SQLException e) {
				System.err.println("Error in SQL\n" + e);
			}catch(Exception e) {
				System.err.println("Other error\n" + e);
			}
			if(con != null) {
				access_label.setForeground(Color.green);
				access_label.setText("SUCCESS");
				Timer timer = new Timer(500, new ActionListener() {
                    public void actionPerformed(ActionEvent e) {  
                        dispose();                                        //
                        LoginPortal page = new LoginPortal(con);          // This action gets executed when the timer gets over
                        page.executePage();								  //
                    }
                });				
				timer.setRepeats(false);
                timer.start();
                return;
			}
			else {
				access_label.setForeground(Color.red);
				access_label.setText("FAILURE");
			}
		}
	}
	
}
