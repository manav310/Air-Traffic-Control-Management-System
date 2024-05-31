package atc_project;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.table.DefaultTableModel;

public class FlightSchedulerPage extends JFrame implements ActionListener{

	JLabel FS1,fs1,fs2,fs3,fs4,fs5,fs6,fs7,fsf,colon1,colon2,colon3,colon4,HH1,HH2,MM1,MM2,SS1,SS2,airline1, airline2, airline5;
	JButton b1,b2,b3, airline4;
	JTextField fsTF1,fsTF2,fsTF3H,fsTF3M,fsTF3S,fsTF4H,fsTF4M,fsTF4S,fsTF5,fsTFf,fsTF6, airline3, fsTF_airline;
	JLabel fsTF7, airline6, fs_airline;
	DefaultTableModel airline7;
	JTable airline8;
	JScrollPane airline9;
	JPanel AirlineDetails;
	
	User user;
	Connection con;
	ATC controller;
	
	public FlightSchedulerPage(Connection con, User user, ATC controller) {
		this.user = user;
		this.con = con;
		this.controller = controller;	
	}

	public void executePage() {
		setTitle("ATC/Flight_Scheduler");
		
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		
		FS1 = new JLabel("FLIGHT DETAILS");
		FS1.setForeground(Color.white);
		FS1.setFont(new Font("Calibri", Font.BOLD, 30));
		FS1.setBounds(screenSize.width/2 - 50,20, 200,40);
		add(FS1);
		
		
		fs1 = new JLabel("FLIGHT NUMBER");
		fs1.setForeground(Color.white);
		fs1.setFont(new Font("Calibri", Font.BOLD, 20));
		fs1.setBounds(screenSize.width/4 - 250,100, 400,20);
		add(fs1);
		
		fsTF1 = new RoundedJTextField(20);
		fsTF1.setBounds(screenSize.width/4 + 30 , 90,180, 30);
		fsTF1.setFont(new Font("Calibri", Font.BOLD, 14));  // Replace with table as we need to display text not take input
		fsTF1.setForeground(Color.BLACK);
		fsTF1.setBackground(Color.LIGHT_GRAY);
		fsTF1.setHorizontalAlignment(SwingConstants.CENTER);
		add(fsTF1);
		
		fs2 = new JLabel("ORIGIN");
		fs2.setForeground(Color.white);
		fs2.setFont(new Font("Calibri", Font.BOLD, 20));
		fs2.setBounds(screenSize.width/4 - 250,170, 400,20);
		add(fs2);
		
		fsTF2 = new RoundedJTextField(20);
		fsTF2.setBounds(screenSize.width/4 + 30 , 160, 180, 30);
		fsTF2.setFont(new Font("Calibri", Font.BOLD, 14));  // Replace with table as we need to display text not take input
		fsTF2.setForeground(Color.BLACK);
		fsTF2.setBackground(Color.LIGHT_GRAY);
		fsTF2.setHorizontalAlignment(SwingConstants.CENTER);
		add(fsTF2);
		
		fs3 = new JLabel("ARRIVAL TIME");
		fs3.setForeground(Color.white);
		fs3.setFont(new Font("Calibri", Font.BOLD, 20));
		fs3.setBounds(screenSize.width/4 - 250,250, 400,20);
		add(fs3);
		
		fsTF3H = new RoundedJTextField(20);
		fsTF3H.setBounds(screenSize.width/4 + 30 , 240,50, 30);
		fsTF3H.setFont(new Font("Calibri", Font.BOLD, 14));  
		fsTF3H.setForeground(Color.BLACK);
		fsTF3H.setBackground(Color.LIGHT_GRAY);
		fsTF3H.setHorizontalAlignment(SwingConstants.CENTER);
		add(fsTF3H);
		
		HH1 = new JLabel("HH");
		HH1.setForeground(Color.white);
		HH1.setFont(new Font("Calibri", Font.BOLD, 20));
		HH1.setBounds(screenSize.width/4 + 40,220, 400,20);
		add(HH1);
		
		colon1 = new JLabel(":");
		colon1.setForeground(Color.white);
		colon1.setFont(new Font("Calibri", Font.BOLD, 20));
		colon1.setBounds(screenSize.width/4 + 82,245, 400,20);
		add(colon1);
		 
		fsTF3M = new RoundedJTextField(20);
		fsTF3M.setBounds(screenSize.width/4 + 90 , 240,50, 30);
		fsTF3M.setFont(new Font("Calibri", Font.BOLD, 14));  
		fsTF3M.setForeground(Color.BLACK);
		fsTF3M.setBackground(Color.LIGHT_GRAY);
		fsTF3M.setHorizontalAlignment(SwingConstants.CENTER);
		add(fsTF3M);
		
		MM1 = new JLabel("MM");
		MM1.setForeground(Color.white);
		MM1.setFont(new Font("Calibri", Font.BOLD, 20));
		MM1.setBounds(screenSize.width/4 + 95,220, 400,20);
		add(MM1);
		
		colon2 = new JLabel(":");
		colon2.setForeground(Color.white);
		colon2.setFont(new Font("Calibri", Font.BOLD, 20));
		colon2.setBounds(screenSize.width/4 + 142,245, 400,20);
		add(colon2);

		fsTF3S = new RoundedJTextField(20);
		fsTF3S.setBounds(screenSize.width/4 + 150 , 240, 50, 30);
		fsTF3S.setFont(new Font("Calibri", Font.BOLD, 14));  
		fsTF3S.setForeground(Color.BLACK);
		fsTF3S.setBackground(Color.LIGHT_GRAY);
		fsTF3S.setHorizontalAlignment(SwingConstants.CENTER);
		add(fsTF3S);
		
		SS1 = new JLabel("SS");
		SS1.setForeground(Color.white);
		SS1.setFont(new Font("Calibri", Font.BOLD, 20));
		SS1.setBounds(screenSize.width/4 + 163,220, 400,20);
		add(SS1);
		
		fs4 = new JLabel("DEPARTURE TIME");
		fs4.setForeground(Color.white);
		fs4.setFont(new Font("Calibri", Font.BOLD, 20));
		fs4.setBounds(screenSize.width/4 - 250,350, 400,20);
		add(fs4);
		
		
		fsTF4H = new RoundedJTextField(20);
		fsTF4H.setBounds(screenSize.width/4 + 30 , 340,50, 30);
		fsTF4H.setFont(new Font("Calibri", Font.BOLD, 14));  
		fsTF4H.setForeground(Color.BLACK);
		fsTF4H.setBackground(Color.LIGHT_GRAY);
		fsTF4H.setHorizontalAlignment(SwingConstants.CENTER);
		add(fsTF4H);
		
		HH2 = new JLabel("HH");
		HH2.setForeground(Color.white);
		HH2.setFont(new Font("Calibri", Font.BOLD, 20));
		HH2.setBounds(screenSize.width/4 + 40,320, 400,20);
		add(HH2);
		
		colon3 = new JLabel(":");
		colon3.setForeground(Color.white);
		colon3.setFont(new Font("Calibri", Font.BOLD, 20));
		colon3.setBounds(screenSize.width/4 + 82,345, 400,20);
		add(colon3);
		 
		fsTF4M = new RoundedJTextField(20);
		fsTF4M.setBounds(screenSize.width/4 + 90 , 340,50, 30);
		fsTF4M.setFont(new Font("Calibri", Font.BOLD, 14));  
		fsTF4M.setForeground(Color.BLACK);
		fsTF4M.setBackground(Color.LIGHT_GRAY);
		fsTF4M.setHorizontalAlignment(SwingConstants.CENTER);
		add(fsTF4M);
		
		MM2 = new JLabel("MM");
		MM2.setForeground(Color.white);
		MM2.setFont(new Font("Calibri", Font.BOLD, 20));
		MM2.setBounds(screenSize.width/4 + 95,320, 400,20);
		add(MM2);
		
		colon4 = new JLabel(":");
		colon4.setForeground(Color.white);
		colon4.setFont(new Font("Calibri", Font.BOLD, 20));
		colon4.setBounds(screenSize.width/4 + 142,345, 400,20);
		add(colon4);

		fsTF4S = new RoundedJTextField(20);
		fsTF4S.setBounds(screenSize.width/4 + 150 , 340, 50, 30);
		fsTF4S.setFont(new Font("Calibri", Font.BOLD, 14));  
		fsTF4S.setForeground(Color.BLACK);
		fsTF4S.setBackground(Color.LIGHT_GRAY);
		fsTF4S.setHorizontalAlignment(SwingConstants.CENTER);
		add(fsTF4S);
		
		SS2 = new JLabel("SS");
		SS2.setForeground(Color.white);
		SS2.setFont(new Font("Calibri", Font.BOLD, 20));
		SS2.setBounds(screenSize.width/4 + 163,320, 400,20);
		add(SS2);
		
		
		fs5 = new JLabel("CAPACITY");
		fs5.setForeground(Color.white);
		fs5.setFont(new Font("Calibri", Font.BOLD, 20));
		fs5.setBounds(screenSize.width/4 - 250,440, 400,20);
		add(fs5);
		
		fsTF5 = new RoundedJTextField(20);
		fsTF5.setBounds(screenSize.width/4 + 30 , 430,180, 30);
		fsTF5.setFont(new Font("Calibri", Font.BOLD, 14));  // Replace with table as we need to display text not take input
		fsTF5.setForeground(Color.BLACK);
		fsTF5.setBackground(Color.LIGHT_GRAY);
		fsTF5.setHorizontalAlignment(SwingConstants.CENTER);
		add(fsTF5);
		
		fsf = new JLabel("USER ID");
		fsf.setForeground(Color.white);
		fsf.setFont(new Font("Calibri", Font.BOLD, 20));
		fsf.setBounds(screenSize.width/4 - 250 ,510, 400,20);
		add(fsf);
		
		fsTFf = new RoundedJTextField(20);
		fsTFf.setBounds(screenSize.width/4 + 30 , 500,180, 30);
		fsTFf.setFont(new Font("Calibri", Font.BOLD, 14));  // Replace with table as we need to display text not take input
		fsTFf.setForeground(Color.BLACK);
		fsTFf.setBackground(Color.LIGHT_GRAY);
		fsTFf.setHorizontalAlignment(SwingConstants.CENTER);
		add(fsTFf);
		
		fs_airline = new JLabel("AIRLINE");
		fs_airline.setForeground(Color.white);
		fs_airline.setFont(new Font("Calibri", Font.BOLD, 20));
		fs_airline.setBounds(screenSize.width/4 - 250 ,580, 400,20);
		add(fs_airline);
		
		fsTF_airline = new RoundedJTextField(20);
		fsTF_airline.setBackground(Color.LIGHT_GRAY);
		fsTF_airline.setFont(new Font("Calibri", Font.BOLD, 14));
		fsTF_airline.setBounds(screenSize.width/4 + 30 ,570, 180,30);
		fsTF_airline.setHorizontalAlignment(SwingConstants.CENTER);
		add(fsTF_airline);
		
		b1 = new RoundedJButton("ADD FLIGHT");
		b1.setBounds(screenSize.width/4 - 100,630, 150,30);
		b1.setBackground(Color.LIGHT_GRAY);
		b1.addActionListener(this);
		add(b1);
		
		
		b2 = new RoundedJButton("DELETE FLIGHT");
		b2.setBounds(screenSize.width/2 + 250,200, 150,30);
		b2.setBackground(Color.LIGHT_GRAY);
		b2.addActionListener(this);
		add(b2);
		
		
		fs6 = new JLabel("FLIGHT NUMBER");
		fs6.setForeground(Color.white);
		fs6.setFont(new Font("Calibri", Font.BOLD, 20));
		fs6.setBounds(screenSize.width/2 + 100 ,120, 400,20);
		add(fs6);
			
			
		fsTF6 = new RoundedJTextField(20);
		fsTF6.setBounds(screenSize.width/2 + 340 , 120,200, 30);
		fsTF6.setFont(new Font("Calibri", Font.BOLD, 14));  // Replace with table as we need to display text not take input
		fsTF6.setForeground(Color.BLACK);
		fsTF6.setBackground(Color.LIGHT_GRAY);
		fsTF6.setHorizontalAlignment(SwingConstants.CENTER);
		add(fsTF6);
		
		
		
		fs7 = new JLabel("ACTION STATUS");
		fs7.setForeground(Color.white);
		fs7.setFont(new Font("Calibri", Font.BOLD, 20));
		fs7.setBounds(screenSize.width/4 - 150,700, 400,20);
		add(fs7);
		
		
		fsTF7 = new JLabel("");
		fsTF7.setBounds(screenSize.width/4  , 690,800, 30);
		fsTF7.setFont(new Font("Calibri", Font.BOLD, 14));  // Replace with table as we need to display text not take input
		fsTF7.setForeground(Color.BLACK);
		fsTF7.setBackground(Color.LIGHT_GRAY);
		fsTF7.setHorizontalAlignment(SwingConstants.CENTER);
		add(fsTF7);
		
		b3 = new RoundedJButton("GO BACK TO MAIN");
		b3.setBounds(screenSize.width - 180,180, 150,30);
		b3.setBackground(Color.LIGHT_GRAY);
		b3.addActionListener(this);
		add(b3);
		
		airline1 = new JLabel("AIRLINE DETAILS");
		airline1.setForeground(Color.white);
		airline1.setFont(new Font("Calibri", Font.BOLD, 20));
		airline1.setBounds(3*(screenSize.width/4) - 150,300, 400,20);
		add(airline1);
		
		airline2 = new JLabel("AIRLINE");
		airline2.setForeground(Color.white);
		airline2.setFont(new Font("Calibri", Font.BOLD, 20));
		airline2.setBounds(screenSize.width/2 + 150 ,350, 400,20);
		add(airline2);
		
		airline3 = new RoundedJTextField(20);
		airline3.setBounds(screenSize.width/2 + 340 , 340, 200, 30);
		airline3.setFont(new Font("Calibri", Font.BOLD, 14));  // Replace with table as we need to display text not take input
		airline3.setForeground(Color.BLACK);
		airline3.setBackground(Color.LIGHT_GRAY);
		airline3.setHorizontalAlignment(SwingConstants.CENTER);
		add(airline3);
		
		airline4 = new RoundedJButton("VIEW DETAILS");
		airline4.setBounds(screenSize.width/2 + 250,400, 150,30);
		airline4.setBackground(Color.LIGHT_GRAY);
		airline4.addActionListener(this);
		add(airline4);
		
		airline5 = new JLabel("AIRLINE HQ");
		airline5.setForeground(Color.white);
		airline5.setFont(new Font("Calibri", Font.BOLD, 20));
		airline5.setBounds(screenSize.width/2 + 150 ,475, 400,20);
		add(airline5);
		
		
		
		airline6 = new JLabel("");
		airline6.setBounds(screenSize.width/2 + 340 , 470, 200, 30);
		airline6.setFont(new Font("Calibri", Font.BOLD, 14));  // Replace with table as we need to display text not take input
		airline6.setForeground(Color.BLACK);
		airline6.setBackground(Color.LIGHT_GRAY);
		airline6.setHorizontalAlignment(SwingConstants.CENTER);
		add(airline6);
		
		// Airline Details Panel
		AirlineDetails = new JPanel(new BorderLayout());
		AirlineDetails.setBounds(screenSize.width/2 + 150, 525, 400, 250);
		AirlineDetails.setBackground(Color.WHITE);
		
		
		//Airline details table
		airline7 = new DefaultTableModel();
		airline7.addColumn("FLIGHT NO.");
		airline7.addColumn("ORIGIN");
		
		airline8 = new JTable(airline7){
            private static final long serialVersionUID = 1L;

            public boolean isCellEditable(int row, int column) {                
                    return false;               
            };
        };

        // Optionally, customize the appearance and behavior of the table
        airline8.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

       // Create a scroll pane and add the table to it
        airline9 = new JScrollPane(airline8);
        airline8.getColumnModel().getColumn(0).setPreferredWidth(100);
        airline8.getColumnModel().getColumn(1).setPreferredWidth(100);

        // Add the scroll pane to the frame
        getContentPane().setLayout(new BorderLayout());
        AirlineDetails.add(airline9, BorderLayout.CENTER);
	    add(AirlineDetails);
		
		JLabel imgLabel = new JLabel();
		Image img =  new ImageIcon(this.getClass().getResource("small logo.png")).getImage();
		imgLabel.setIcon(new ImageIcon(img));
			
		imgLabel.setBounds(screenSize.width-150,25 , 125, 125);
		this.getContentPane().add(imgLabel);
		
		
		setSize(screenSize.width,screenSize.height);
		Container c = getContentPane();
		c.setBackground(Color.black);
			
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(null);
		setVisible(true); 
		
	}
	
	public void actionPerformed(ActionEvent ae) {
		if(ae.getSource() == b3) {
			Timer timer = new Timer(500, new ActionListener() {
                public void actionPerformed(ActionEvent e) {      
                    dispose();                                        								  
                }
            });				
			timer.setRepeats(false);
            timer.start();
            return;
		}
		else if(ae.getSource() == b1) {
			String flightNumber = fsTF1.getText();
			String origin = fsTF2.getText();
			String arrival_time = fsTF3H.getText() + ":" + fsTF3M.getText() + ":" +fsTF3S.getText();
			String departure_time = fsTF4H.getText() + ":" + fsTF4M.getText() + ":" +fsTF4S.getText();
			String capacity = fsTF5.getText();
			String emp_id = fsTFf.getText();
			String airline = fsTF_airline.getText();
			
			if(flightNumber.isEmpty() || origin.isEmpty() || capacity.isEmpty() || emp_id.isEmpty() || airline.isEmpty() ||
					((fsTF3H.getText().isEmpty() || fsTF3M.getText().isEmpty() || fsTF3S.getText().isEmpty()) &&
					(fsTF4H.getText().isEmpty() || fsTF4M.getText().isEmpty() || fsTF4S.getText().isEmpty()))) {
				fsTF7.setForeground(Color.red);
				fsTF7.setText("INCOMPLETE DETAILS(S)");
				return;
			}
			if(origin.equals("New Delhi")) {
				arrival_time = "NULL";
			}
			try {
				if(!controller.airlineExistence(airline)) {
					fsTF7.setForeground(Color.red);
					fsTF7.setText("AIRLINE DOES NOT EXIST");
					return;
				}
				else if((fsTF3H.getText().isEmpty() || fsTF3M.getText().isEmpty() || fsTF3S.getText().isEmpty()) || arrival_time.equals("NULL")) {
					if(!controller.addToSchedule(flightNumber, origin, "NULL", departure_time, capacity, emp_id, airline)) {
						fsTF7.setForeground(Color.red);
						fsTF7.setText("ADDITION FAILED");
					}
					fsTF7.setForeground(Color.green);
					fsTF7.setText("ADDITION SUCCESSFUL");
					return;
				}
				else if(fsTF4H.getText().isEmpty() || fsTF4M.getText().isEmpty() || fsTF4S.getText().isEmpty() && !arrival_time.equals("NULL")) {
					if(!controller.addToSchedule(flightNumber, origin, arrival_time, "NULL", capacity, emp_id, airline)) {
						fsTF7.setForeground(Color.red);
						fsTF7.setText("ADDITION FAILED");
					}
					fsTF7.setForeground(Color.green);
					fsTF7.setText("ADDITION SUCCESSFUL");
					return;
				}
				else if(!arrival_time.equals("NULL")) {
					if(!controller.addToSchedule(flightNumber, origin, arrival_time, departure_time, capacity, emp_id, airline)) {
						fsTF7.setForeground(Color.red);
						fsTF7.setText("ADDITION FAILED");
					}
					fsTF7.setForeground(Color.green);
					fsTF7.setText("ADDITION SUCCESSFUL");
					return;
				}
				fsTF7.setForeground(Color.red);
				fsTF7.setText("ADDITION FAILED");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		else if(ae.getSource() == b2) {
			String flightNumber = fsTF6.getText();
			if(flightNumber.isEmpty()) {
				fsTF7.setForeground(Color.red);
				fsTF7.setText("INCOMPLETE DETAILS(S)");
				return;
			} else {
				try {
					if(controller.landCheck(flightNumber)) {
						fsTF7.setForeground(Color.red);
						fsTF7.setText("CANNOT REMOVE A FLIGHT WHICH IS CURRENTLY AT THE AIRPORT");
						return;
					}
					if(!controller.removeFromSchedule(flightNumber)) {
						fsTF7.setForeground(Color.red);
						fsTF7.setText("REMOVAL FAILED");
						return;
					}
					fsTF7.setForeground(Color.green);
					fsTF7.setText("REMOVAL SUCCESSFUL");
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		else if(ae.getSource()== airline4) {
			String airline = airline3.getText();
			//System.out.println(airline);
			try {
				String hq = controller.getAirlineHQ(airline);
				if(hq == null) {
					airline6.setForeground(Color.RED);
					airline6.setText("AIRLINE DOES NOT EXIST");
					airline7.setRowCount(0);
					return;
				}
				airline6.setForeground(Color.GREEN);
				airline6.setText(hq);
				ResultSet rs = controller.getAirlineFlights(airline);
				airline7.setRowCount(0);
				while(rs.next()) {
					Object[] rowData = {rs.getString(1), rs.getString(2)};
					airline7.addRow(rowData);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}