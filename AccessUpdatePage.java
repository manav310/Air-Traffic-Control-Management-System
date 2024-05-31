package atc_project;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.Timer;

public class AccessUpdatePage extends JFrame implements ActionListener {
	Connection con;
	User user;
	ATC controller;
	JFrame mainFrame;
	JFrame queryPageFrame;
	
	RoundedJButton update_button;
	RoundedJTextField EmployeeID_field,AccessLevel_field;
	JLabel access_label;
	
	public AccessUpdatePage(Connection con,User user,ATC controller, JFrame mainFrame, JFrame queryPageFrame) {
		this.con = con;
		this.user = user;
		this.controller = controller;
		this.mainFrame = mainFrame;
		this.queryPageFrame = queryPageFrame;
	}
	
	
	
	void executePage() throws IOException{
		setTitle("ATC/Access_Manager");
		
		JLabel heading = new JLabel("UPDATE EMPLOYEE ACCESS");
		heading.setForeground(Color.LIGHT_GRAY);
		heading.setBounds(125, 0, 250, 175);
		heading.setFont(new Font("Calibri", Font.BOLD, 20));
		add(heading);
		
		JLabel user_name_label = new JLabel("EMPLOYEE ID:");
		user_name_label.setForeground(Color.LIGHT_GRAY);
		user_name_label.setBounds(93, 175, 100, 30);
		add(user_name_label);
		
		JLabel password_label = new JLabel("ACCESS LEVEL:");
		password_label.setForeground(Color.LIGHT_GRAY);
		password_label.setBounds(80, 225, 100, 30);
		add(password_label);
		
		EmployeeID_field = new RoundedJTextField(100);
		EmployeeID_field.setBackground(Color.LIGHT_GRAY);
		EmployeeID_field.setBounds(185, 175, 175, 30);
		EmployeeID_field.setHorizontalAlignment(SwingConstants.CENTER);
		add(EmployeeID_field);
		
		AccessLevel_field = new RoundedJTextField(100);
		AccessLevel_field.setBackground(Color.LIGHT_GRAY);
		AccessLevel_field.setBounds(185, 225, 175, 30);
		AccessLevel_field.setHorizontalAlignment(SwingConstants.CENTER);
		add (AccessLevel_field);
		
		update_button = new RoundedJButton("UPDATE");
		update_button.setBounds(200, 300, 100, 30);
		update_button.setBackground(Color.LIGHT_GRAY);
		update_button.addActionListener(this);
		add(update_button);
		
		access_label = new JLabel("");
		access_label.setForeground(Color.red);
		access_label.setBounds(210, 350, 175, 30);
		add(access_label);
		
		Container c = getContentPane();
		c.setBackground(Color.black);
		setBounds(550, 200, 500, 500);
		setLayout(null);
		setVisible(true);
	}
	public void actionPerformed(ActionEvent ae) {
		// TODO Auto-generated method stub
		
		String employeeId = EmployeeID_field.getText();
		String accesslevel = AccessLevel_field.getText();
		boolean flag =false;
		if(ae.getSource() == update_button) {
			
			try {
				  flag = controller.AccessUpdater(employeeId, accesslevel);
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}
			if(flag){	
				access_label.setText("SUCCESS");
				access_label.setForeground(Color.green);
				if(user.getEmployee_id() == Integer.parseInt(employeeId)) {
					Timer timer = new Timer(500, new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							access_label.setText("LOGGING OUT");
							access_label.setForeground(Color.green);						  
						}
					});	
				}
					Timer timer = new Timer(500, new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							mainFrame.dispose();
							queryPageFrame.dispose();
							dispose();                                        
							LoginPortal page = new LoginPortal(con);          
							page.executePage();								  
						}
					});				
				timer.setRepeats(false);
                timer.start();
                return;
			}
			access_label.setText("FAILED");
			access_label.setForeground(Color.red);
			
		}
	}

}
