package atc_project;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.Timer;

import java.sql.*;

public class LoginPortal extends JFrame implements ActionListener{
	JTextField t1, t2;
	JLabel jl1, jl2;
	JLabel access_label;
	JButton b;
	
	Connection con;
	User user;
	ATC controller;
	
	public LoginPortal(Connection con) {
		this.con = con;
		this.user = null;
	}
	
	public void executePage() {
		
		setTitle("ATC/Login_Portal");
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		
		JLabel heading1 = new JLabel("AIR TRAFFIC TERMINAL");
		heading1.setForeground(Color.white);
		heading1.setBounds(screenSize.width/2-170 , screenSize.height/2 - 50, 400, 45);
		heading1.setFont(new Font("Calibri", Font.BOLD, 40));
		
		JLabel heading2 = new JLabel("LOGIN");
		heading2.setForeground(Color.white);
		heading2.setBounds(screenSize.width/2 - 30 , screenSize.height/2 - 10, 400, 45);
		heading2.setFont(new Font("Calibri", Font.BOLD, 40));
		
		
		JLabel jl1 = new JLabel("EMPLOYEE ID: ");
		JLabel jl2 = new JLabel("PASSWORD: ");
		jl1.setForeground(Color.white);
		jl2.setForeground(Color.white);
		jl1.setBounds(screenSize.width/2 -170, screenSize.height/2+65, 200, 20);
		jl2.setBounds(screenSize.width/2 -170, screenSize.height/2+115, 200, 20);
		jl1.setFont(new Font("Calibri", Font.BOLD, 18));
		jl2.setFont(new Font("Calibri", Font.BOLD, 18));
		
		t1 = new RoundedJTextField(20);
		t2 = new RoundedJPasswordField(20);
		t1.setBackground(Color.LIGHT_GRAY);
		t1.setForeground(Color.black);
		t1.setFont(new Font("Calibri", Font.BOLD, 16));
		t2.setFont(new Font("Calibri", Font.BOLD, 16));
		t2.setBackground(Color.LIGHT_GRAY);
		t2.setForeground(Color.black);
		t1.setBounds(screenSize.width/2 ,screenSize.height/2+60 ,200,30);
		t2.setBounds(screenSize.width/2 ,screenSize.height/2+110 ,200,30);
		t1.setHorizontalAlignment(SwingConstants.CENTER);
		t2.setHorizontalAlignment(SwingConstants.CENTER);
		
		b = new RoundedJButton("LOGIN");
		b.setBounds(screenSize.width/2-20, screenSize.height/2+180,85,45);
		b.setBackground(Color.LIGHT_GRAY);
		b.setFont(new Font("Calibri", Font.BOLD, 16));
		
		access_label = new JLabel("");
		access_label.setForeground(Color.GREEN);
		access_label.setBounds(screenSize.width/2 - 30, screenSize.height/2 + 250, 150, 30);
		access_label.setFont(new Font("Calibri", Font.BOLD, 16));
		
		JLabel imgLabel = new JLabel();
		Image img =  new ImageIcon(this.getClass().getResource("login_portal_logo.png")).getImage();
		imgLabel.setIcon(new ImageIcon(img));
		imgLabel.setBounds(screenSize.width/2-100, screenSize.height/2-425,600, 408);
		
		add(heading1);
		add(heading2);
		add(jl1);
		add(t1);
		add(jl2);
		add(t2);
		add(b);
		add(access_label);
		this.getContentPane().add(imgLabel);
		
		
		b.addActionListener(this); // Action Listener is an interface
		
		// These lines need to always be there
		setSize(screenSize.width, screenSize.height);
		
		Container c = getContentPane();
		c.setBackground(Color.black);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(null);
		setVisible(true);
	}
	
	public void actionPerformed(ActionEvent ae) {
		String name = t1.getText();
		String password = t2.getText();
		try {
			user = User.userVerification(Integer.parseInt(name), password, con);
		} catch (SQLException e) {
			System.out.println("Error in SQL\n" + e);
		}catch(Exception e) {
			System.out.println("Other Error\n" + e);
		}
		if(user == null) {
			access_label.setForeground(Color.red);
			access_label.setText("ACCESS DENIED");
		}
		else if(user != null) {
			controller = new ATC(user, con);
			access_label.setForeground(Color.green);
			access_label.setText("WELCOME USER");
			Timer timer = new Timer(500, new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    dispose();
                    MainPage page = new MainPage(con, user, controller);
                    page.executePage();
                }
            });				
			timer.setRepeats(false);
            timer.start();
            return;
		}
	}
}