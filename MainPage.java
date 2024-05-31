package atc_project;

import java.sql.*;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

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

public class MainPage extends JFrame implements ActionListener{
	Connection con;
	User user;
	ATC controller;
	JFrame mainFrame;
	
	weatherAPI weatherObject = new weatherAPI();
	FlipSwitch lTB2;
	RoundedJButton LP1, LP2, myRunways, myLandedFlightsYesTransit, myLandedFlightsNoTransit;
	FlipSwitch fvB1,fvB2,fvB3,wp1;
	FlipSwitch sJet1;
	RoundedJButton ref1, myFlightsButton, ref2, ref3, confirm1, confirm2, confirm3, confirm4,logout,wpB1;
	JTable scheduledFlightsTable, flightLandedTable, runwayTable;
	DefaultTableModel sfTableModel, flTableModel, rnTableModel;
	JTextField fT1, lr1,wpT2;
	JLabel lt2, lt3, lt1,wlt2,wlt3,wlt4,wlt5, wpT1;
	JPanel statPanel1, statPanel2, statPanel3,jetStatPanel,statPanelWP;
	JLabel slt2, slt3, slt4, slt5, lTT3;
	JButton B0, B1, B2, B3, B4, B5, B6;
	JTextField lTT1, lTT2;
	int stat0, stat1, stat2, stat3, stat4, stat5, stat6;
	
	
	public MainPage(Connection con, User user, ATC controller) {
		this.con = con;
		this.user = user;
		this.controller = controller;
		this.mainFrame = this;
		stat0 = -1; stat1 = -1; stat2 = -1; stat3 = -1; stat4 = -1; stat5 = -1; stat6 = -1;
	}
	
	public void executePage() {
		setTitle("ATC/Main_Page");
		
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

		JPanel scheduleFlights =  new JPanel();
		
		scheduleFlights.setBounds(0,0,screenSize.width/3- 5,screenSize.height*2/3-5);    
		scheduleFlights.setBackground(Color.BLACK);
		 
		JLabel sf1 = new JLabel("SCHEDULED FLIGHTS");
		sf1.setForeground(Color.white);
		sf1.setFont(new Font("Calibri", Font.BOLD, 25));
		sf1.setBounds(155,10, 300,30);
		scheduleFlights.add(sf1);
		
		ref1 = new RoundedJButton("REFRESH");
		ref1.setBackground(Color.LIGHT_GRAY);
		ref1.setFont(new Font("Calibri", Font.BOLD, 16));
		ref1.setBounds(385,40,100,30);
		ref1.addActionListener(this);
		scheduleFlights.add(ref1);
		
		myFlightsButton = new RoundedJButton("MY FLIGHTS");
		myFlightsButton.setBackground(Color.LIGHT_GRAY);
		myFlightsButton.setFont(new Font("Calibri", Font.BOLD, 16));
		myFlightsButton.setBounds(25,40,130,30);
		myFlightsButton.addActionListener(this);
		scheduleFlights.add(myFlightsButton);
		
		JPanel scheduledFlightPanel = new JPanel(new BorderLayout());
		scheduledFlightPanel.setBackground(Color.LIGHT_GRAY);
		scheduledFlightPanel.setBounds(25, 75, screenSize.width/3 - 55, screenSize.height*2/3-105 );
		scheduleFlights.add(scheduledFlightPanel);
		
		sfTableModel = new DefaultTableModel();
		sfTableModel.addColumn("FLIGHT NO.");
		sfTableModel.addColumn("ORIGIN");
		sfTableModel.addColumn("ARRIVAL");
		sfTableModel.addColumn("DEPARTURE");
		sfTableModel.addColumn("CAPACITY");
		
		scheduledFlightsTable = new JTable(sfTableModel){
            private static final long serialVersionUID = 1L;

            public boolean isCellEditable(int row, int column) {                
                    return false;               
            }
        };
//
//        // Optionally, customize the appearance and behavior of the table
        scheduledFlightsTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
//
//        // Create a scroll pane and add the table to it
        JScrollPane sfTableScrollPane = new JScrollPane(scheduledFlightsTable);
        scheduledFlightsTable.getColumnModel().getColumn(0).setPreferredWidth(100);
        scheduledFlightsTable.getColumnModel().getColumn(1).setPreferredWidth(150);
        scheduledFlightsTable.getColumnModel().getColumn(2).setPreferredWidth(100);
        scheduledFlightsTable.getColumnModel().getColumn(3).setPreferredWidth(100);
        scheduledFlightsTable.getColumnModel().getColumn(4).setPreferredWidth(75);

        // Add the scroll pane to the frame
        getContentPane().setLayout(new BorderLayout());
        scheduledFlightPanel.add(sfTableScrollPane, BorderLayout.CENTER);
		
		scheduleFlights.setLayout(null);
		scheduleFlights.setVisible(true);
		add(scheduleFlights);
		
		
		
		JPanel weatherPanel = new JPanel();
		weatherPanel.setBounds(0,screenSize.height*2/3 ,screenSize.width/3-5,screenSize.height/3);
		weatherPanel.setBackground(Color.BLACK);
		
		JLabel slabel = new JLabel("SCRAMBLE JETS");
		slabel.setForeground(Color.white);
		slabel.setFont(new Font("Calibri", Font.BOLD, 18));
		slabel.setBounds(240,100,200,30);
		weatherPanel.setLayout(null);
		weatherPanel.setVisible(true);
		weatherPanel.add(slabel);
		
		wp1 = new FlipSwitch(this);
		wp1.setBackground(Color.LIGHT_GRAY);		
		wp1.setBounds(370,70,50,77);
		wp1.addActionListener(this);
		weatherPanel.add(wp1);
		
		statPanelWP = new JRoundedPanel();
		statPanelWP.setBounds(440,90,40,40);
		statPanelWP.setBackground(Color.RED);
		weatherPanel.add(statPanelWP);
		
		JLabel slabelf = new JLabel("FLIGHT NUMBER");
		slabelf.setForeground(Color.white);
		slabelf.setFont(new Font("Calibri", Font.BOLD, 18));
		slabelf.setBounds(10,20,200,20);
		weatherPanel.add(slabelf);
		
		wpT2 = new RoundedJTextField(20);
		wpT2.setBounds(180, 10,250, 30);
		wpT2.setFont(new Font("Calibri", Font.BOLD, 14));  // Replace with table as we need to display text not take input
		wpT2.setForeground(Color.BLACK);
		wpT2.setBackground(Color.LIGHT_GRAY);
		wpT2.setHorizontalAlignment(SwingConstants.CENTER);
		weatherPanel.add(wpT2);
		
		wpB1 = new RoundedJButton("CHECK WEATHER");
		wpB1.setBounds(70,50,180,30);
		wpB1.setFont(new Font("Calibri", Font.BOLD, 16));
		wpB1.setBackground(Color.LIGHT_GRAY);
		wpB1.addActionListener(this);
		weatherPanel.add(wpB1);
		
		JLabel slabel1 = new JLabel("WINDSPEED:");
		slabel1.setForeground(Color.white);
		slabel1.setFont(new Font("Calibri", Font.BOLD, 18));
		slabel1.setBounds(20,90,200,20);
		weatherPanel.add(slabel1);
		
		

		
		wlt2 = new JLabel("");
		wlt2.setBounds(140, 85,180, 30);
		wlt2.setFont(new Font("Calibri", Font.BOLD, 16));  
		wlt2.setForeground(Color.CYAN);
		wlt2.setBackground(Color.LIGHT_GRAY);
		weatherPanel.add(wlt2);
		
		JLabel slabel2 = new JLabel("TEMPERATURE:");
		slabel2.setForeground(Color.white);
		slabel2.setFont(new Font("Calibri", Font.BOLD, 18));
		slabel2.setBounds(20,120,200,20);
		weatherPanel.add(slabel2);
		
		wlt3 = new JLabel("");
		wlt3.setBounds(140, 115,180, 30);
		wlt3.setForeground(Color.CYAN);
		wlt3.setFont(new Font("Calibri", Font.BOLD, 16));  
		wlt3.setBackground(Color.LIGHT_GRAY);
		weatherPanel.add(wlt3);
		
		JLabel slabel3 = new JLabel("HUMIDITY:");
		slabel3.setForeground(Color.white);
		slabel3.setFont(new Font("Calibri", Font.BOLD, 18));
		slabel3.setBounds(20,150,200,20);
		weatherPanel.add(slabel3);
		
		wlt4 = new JLabel("");
		wlt4.setBounds(140, 145,180, 30);
		wlt4.setFont(new Font("Calibri", Font.BOLD, 16));  
		wlt4.setForeground(Color.CYAN);
		wlt4.setBackground(Color.LIGHT_GRAY);
		weatherPanel.add(wlt4);
		
		JLabel slabel4 = new JLabel("WEATHER:");
		slabel4.setForeground(Color.white);
		slabel4.setFont(new Font("Calibri", Font.BOLD, 18));
		slabel4.setBounds(20,180,200,20);
		weatherPanel.add(slabel4);
		
		wlt5 = new JLabel("");
		wlt5.setBounds(140,175,180, 30);
		wlt5.setFont(new Font("Calibri", Font.BOLD, 16));  
		wlt5.setForeground(Color.CYAN);
		wlt5.setBackground(Color.LIGHT_GRAY);
		weatherPanel.add(wlt5);
		
		
		
		 JLabel slabel5 = new JLabel("ACTION STATUS");
		 slabel5.setForeground(Color.white);
		 slabel5.setFont(new Font("Calibri", Font.BOLD, 18));
		 slabel5.setBounds(250,150, 200,20);
		 weatherPanel.add(slabel5);
		 
		 wpT1 = new JLabel("");
		 wpT1.setBounds(275,170, 200, 30);
		 wpT1.setFont(new Font("Calibri", Font.BOLD, 14));  
		 wpT1.setForeground(Color.BLACK);
		 wpT1.setBackground(Color.LIGHT_GRAY);
		 weatherPanel.add(wpT1);
		
		add(weatherPanel);

	     
		
		JPanel flightLanded =  new JPanel();
	     
	    flightLanded.setBounds(screenSize.width/3 ,0,screenSize.width*2/9,screenSize.height/2 - 5);    
	    flightLanded.setBackground(Color.BLACK); 
	     
	    JLabel fl1 = new JLabel("FLIGHTS LANDED");
	    fl1.setForeground(Color.white);
		fl1.setFont(new Font("Calibri", Font.BOLD, 25));
		fl1.setBounds(90,10, 200,30);
		flightLanded.add(fl1);
		
		myLandedFlightsYesTransit = new RoundedJButton("TRNS");
		myLandedFlightsYesTransit.setBounds(25, 45, 80, 30);
		myLandedFlightsYesTransit.setFont(new Font("Calibri", Font.BOLD, 16));
		myLandedFlightsYesTransit.setBackground(Color.LIGHT_GRAY);
		myLandedFlightsYesTransit.addActionListener(this);
		flightLanded.add(myLandedFlightsYesTransit);
		
		myLandedFlightsNoTransit = new RoundedJButton("N_TRNS");
		myLandedFlightsNoTransit.setBounds(110, 45, 100, 30);
		myLandedFlightsNoTransit.setFont(new Font("Calibri", Font.BOLD, 16));
		myLandedFlightsNoTransit.setBackground(Color.LIGHT_GRAY);
		myLandedFlightsNoTransit.addActionListener(this);
		flightLanded.add(myLandedFlightsNoTransit);
		 
		ref2 = new RoundedJButton("REFRESH");
		ref2.setFont(new Font("Calibri", Font.BOLD, 16));
		ref2.setBackground(Color.LIGHT_GRAY);
		ref2.setBounds(215,45,100,30);
		ref2.addActionListener(this);
		flightLanded.add(ref2);
		 
		JPanel flPanel = new JPanel(new BorderLayout());
		flPanel.setBackground(Color.LIGHT_GRAY);
		flPanel.setBounds(25, 85, 290, 325);
		flightLanded.add(flPanel);
		
		flTableModel = new DefaultTableModel();
		flTableModel.addColumn("FLT NO.");
		flTableModel.addColumn("FUEL");
		flTableModel.addColumn("CRG");
		flTableModel.addColumn("SFT_CK");
		
		flightLandedTable = new JTable(flTableModel){
            private static final long serialVersionUID = 1L;

            public boolean isCellEditable(int row, int column) {                
                    return false;               
            };
        };

        // Optionally, customize the appearance and behavior of the table
        flightLandedTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

       // Create a scroll pane and add the table to it
        JScrollPane flTableScrollPane = new JScrollPane(flightLandedTable);
        flightLandedTable.getColumnModel().getColumn(0).setPreferredWidth(50);
        flightLandedTable.getColumnModel().getColumn(1).setPreferredWidth(50);
        flightLandedTable.getColumnModel().getColumn(2).setPreferredWidth(50);
        flightLandedTable.getColumnModel().getColumn(3).setPreferredWidth(50);

        // Add the scroll pane to the frame
        getContentPane().setLayout(new BorderLayout());
        flPanel.add(flTableScrollPane, BorderLayout.CENTER);
	     
	    flightLanded.setLayout(null);
	    flightLanded.setVisible(true);
		add(flightLanded);
		 
		JPanel utilityVehicle =  new JPanel();
		
	     
		 utilityVehicle.setBounds(screenSize.width/3 ,screenSize.height/2,screenSize.width*2/9,screenSize.height/2);    
		 utilityVehicle.setBackground(Color.BLACK); 
		 
		 JLabel uv1 = new JLabel("UTILITY VEHICLE");
		 uv1.setForeground(Color.white);
		 uv1.setFont(new Font("Calibri", Font.BOLD, 25));
		 uv1.setBounds(90,10, 200,30);
		 utilityVehicle.add(uv1);
		 
		 JLabel rl = new JLabel("RUNWAY NUMBER");
		 rl.setForeground(Color.white);
		 rl.setFont(new Font("Calibri", Font.BOLD, 18));
		 rl.setBounds(20,40, 200,20);
		 utilityVehicle.add(rl);
		 
		 lr1 = new RoundedJTextField(20);
		 lr1.setBounds(170, 35,160, 30);
		 lr1.setFont(new Font("Calibri", Font.BOLD, 16));  // Replace with table as we need to display text not take input
		 lr1.setForeground(Color.BLACK);
		 lr1.setBackground(Color.LIGHT_GRAY);
		 lr1.setHorizontalAlignment(SwingConstants.CENTER);
		 utilityVehicle.add(lr1);
		 
		 confirm2 = new RoundedJButton("CONFIRM");
		 confirm2.setBackground(Color.LIGHT_GRAY);
		 confirm2.setFont(new Font("Calibri", Font.BOLD, 16));
		 confirm2.setBounds(230, 90, 100, 30);
		 confirm2.addActionListener(this);
		 utilityVehicle.add(confirm2);
		 
		 JLabel bl0= new JLabel("RUNWAY STATUS");
		 bl0.setForeground(Color.white);
		 bl0.setFont(new Font("Calibri", Font.BOLD, 18));
		 bl0.setBounds(80 ,95, 200,20);
		 utilityVehicle.add(bl0);							//Fire truck button
		
		 B0 =new JButton("");
		 B0.setBackground(Color.RED);
		 B0.setBounds(20,80,50,50);
		 B0.addActionListener(this);
		 utilityVehicle.add(B0);
		 
		 
//		 //BUTTON LABELS AND BUTTONS
		 JLabel bl1 = new JLabel("FIRE TRUCK");
		 bl1.setForeground(Color.white);
		 bl1.setFont(new Font("Calibri", Font.BOLD, 14));
		 bl1.setBounds(70 ,160, 200,20);
		 utilityVehicle.add(bl1);							//Fire truck button
		
		 B1 =new JButton("");
		 B1.setBackground(Color.RED);
		 B1.setBounds(20,150,40,40);
		 B1.addActionListener(this);
		 utilityVehicle.add(B1);
		 
		 
		 JLabel bl2 = new JLabel("CARGO LOADER");
		 bl2.setForeground(Color.white);
		 bl2.setFont(new Font("Calibri", Font.BOLD, 14));
		 bl2.setBounds(220,160, 200,20);
		 
		 utilityVehicle.add(bl2);
		 													//Cargo Loader button
		 B2 =new JButton("");
		 B2.setBackground(Color.GREEN);
		 B2.setBounds(170,150,40,40);
		 B2.addActionListener(this);
		 utilityVehicle.add(B2);
		 
		 
		 JLabel bl3 = new JLabel("AMBULANCE");
		 bl3.setForeground(Color.white);
		 bl3.setFont(new Font("Calibri", Font.BOLD, 16));
		 bl3.setBounds(70,220, 200,20);
		 utilityVehicle.add(bl3);
		 													//Ambulance button
		 B3 =new JButton("");
		 B3.setBackground(Color.GREEN);
		 B3.setBounds(20,210,39,39);
		 B3.addActionListener(this);
		 utilityVehicle.add(B3);
		 
		 
		 JLabel bl4 = new JLabel("FUEL BOWSER");
		 bl4.setForeground(Color.white);
		 bl4.setFont(new Font("Calibri", Font.BOLD, 16));
		 bl4.setBounds(220,220, 200,20);
		 utilityVehicle.add(bl4);
		 													//Fuel Bowser button
		 B4 = new JButton("");
		 B4.setBackground(Color.RED);
		 B4.setBounds(170,210,40,40);
		 B4.addActionListener(this);
		 utilityVehicle.add(B4);
		 
		 
		 JLabel bl5 = new JLabel("REFUELER JET");
		 bl5.setForeground(Color.white);
		 bl5.setFont(new Font("Calibri", Font.BOLD, 16));
		 bl5.setBounds(70,280, 200,20);
		 utilityVehicle.add(bl5);
		 													//Re-fueler jet button
		 B5 =new JButton("");
		 B5.setBackground(Color.GREEN);
		 B5.setBounds(20,270,39,39);
		 B5.addActionListener(this);
		 utilityVehicle.add(B5);
		 
		 
		 JLabel bl6 = new JLabel("SAFETY CHECK RIG");
		 bl6.setForeground(Color.white);
		 bl6.setFont(new Font("Calibri", Font.BOLD, 16));
		 bl6.setBounds(214,280, 200,20);
		 utilityVehicle.add(bl6);
		 													//Safety Check Rig button
		 B6 =new JButton("");
		 B6.setBackground(Color.GREEN);
		 B6.setBounds(170,270,40,40);
		 B6.addActionListener(this);
		 utilityVehicle.add(B6);
		 
		 
		 JLabel l7 = new JLabel("ACTION STATUS");
		 l7.setForeground(Color.white);
		 l7.setFont(new Font("Calibri", Font.BOLD, 18));
		 l7.setBounds(20,320, 200,20);
		 utilityVehicle.add(l7);
		 
		 lt1 = new JLabel("");
		 lt1.setBounds(150, 315,200, 30);
		 lt1.setFont(new Font("Calibri", Font.BOLD, 16));  // Replace with table as we need to display text not take input
		 lt1.setForeground(Color.BLACK);
		 lt1.setBackground(Color.LIGHT_GRAY);
		 utilityVehicle.add(lt1);
		 
		 
		 utilityVehicle.setLayout(null);
		 utilityVehicle.setVisible(true);
		 
		 add(utilityVehicle);
		 
		 JPanel flightVital =  new JPanel();
		 
		 JLabel fV1 = new JLabel("FLIGHT VITALS");
		 fV1.setForeground(Color.white);
		 fV1.setFont(new Font("Calibri", Font.BOLD, 25));
		 fV1.setBounds(110,10, 200,25);
		 flightVital.add(fV1);
		 
		 JLabel fV2 = new JLabel("FLIGHT NUMBER");
		 fV2.setForeground(Color.white);
		 fV2.setFont(new Font("Calibri", Font.BOLD, 18));
		 fV2.setBounds(20,60, 200,20);
		 flightVital.add(fV2);
		 
		 fT1 = new RoundedJTextField(20);
		 fT1.setBounds(150, 55,180, 30);
		 fT1.setFont(new Font("Calibri", Font.BOLD, 16));  
		 fT1.setForeground(Color.BLACK);
		 fT1.setHorizontalAlignment(SwingConstants.CENTER);
		 fT1.setBackground(Color.LIGHT_GRAY);
		 flightVital.add(fT1);
		 
		 JLabel fV3  = new JLabel("STATUS");
		 fV3.setForeground(Color.white);
		 fV3.setFont(new Font("Calibri", Font.BOLD, 18));
		 fV3.setBounds(70,100, 200,20);
		 flightVital.add(fV3);
		 
		 lt2 = new JLabel("");
		 lt2.setBounds(150, 95,180, 30);
		 lt2.setFont(new Font("Calibri", Font.BOLD, 16));  // Replace with table as we need to display text not take input
		 lt2.setForeground(Color.BLACK);
		 lt2.setBackground(Color.LIGHT_GRAY);
		 flightVital.add(lt2);
		 
		 confirm1 = new RoundedJButton("CONFIRM");
		 confirm1.setBackground(Color.LIGHT_GRAY);
		 confirm1.setFont(new Font("Calibri", Font.BOLD, 16));
		 confirm1.setBounds(130, 130, 100, 30);
		 confirm1.addActionListener(this);
		 flightVital.add(confirm1);
		 
		 JLabel fV4  = new JLabel("FUELING");
		 fV4.setForeground(Color.white);
		 fV4.setFont(new Font("Calibri", Font.BOLD, 20));
		 fV4.setBounds(70, 190, 75, 30);
		 flightVital.add(fV4);
		 
		 fvB1 =new FlipSwitch(this);
		 fvB1.setBackground(Color.LIGHT_GRAY);		
		 fvB1.setBounds(170,160,50,77);
		 fvB1.addActionListener(this);
		 flightVital.add(fvB1);
		 
		 statPanel1 = new JRoundedPanel();
		 statPanel1.setBounds(260,180,40,40);
		 statPanel1.setBackground(Color.RED);
		 flightVital.add(statPanel1);
		 
		 JLabel fV5  = new JLabel("CARGO");
		 fV5.setForeground(Color.white);
		 fV5.setFont(new Font("Calibri", Font.BOLD, 20));
		 fV5.setBounds(70,290, 100,30);
		 flightVital.add(fV5);

		 fvB2 =new FlipSwitch(this);
		 fvB2.setBackground(Color.LIGHT_GRAY);		
		 fvB2.setBounds(170,260,50,77);
		 fvB2.addActionListener(this);
		 flightVital.add(fvB2);
		 
		 statPanel2 = new JRoundedPanel();
		 statPanel2.setBounds(260,280,40,40);
		 statPanel2.setBackground(Color.RED);
		 flightVital.add(statPanel2);
		 
		 
		 JLabel fV6  = new JLabel("SAFETY CHECK");
		 fV6.setForeground(Color.white);
		 fV6.setFont(new Font("Calibri", Font.BOLD, 20));
		 fV6.setBounds(20,390, 200,30);
		 flightVital.add(fV6);
		 
		 statPanel3 = new JRoundedPanel();
		 statPanel3.setBounds(260,380,40,40);	//these panels need to be rounded
		 statPanel3.setBackground(Color.RED);
		 flightVital.add(statPanel3);
		 
		 fvB3 =new FlipSwitch(this);
		 fvB3.setBackground(Color.LIGHT_GRAY);		
		 fvB3.setBounds(170,360,50,77);
		 fvB3.addActionListener(this);
		 flightVital.add(fvB3);
		 
		 JLabel fv7 = new JLabel("ACTION STATUS");
		 fv7.setForeground(Color.white);
		 fv7.setFont(new Font("Calibri", Font.BOLD, 18));
		 fv7.setBounds(20,450, 200,20);
		 flightVital.add(fv7);
		 
		 lt3 = new JLabel("");
		 lt3.setBounds(160, 445,200, 30);
		 lt3.setFont(new Font("Calibri", Font.BOLD, 16));  // Replace with table as we need to display text not take input
		 lt3.setForeground(Color.BLACK);
		 lt3.setBackground(Color.LIGHT_GRAY);
		 flightVital.add(lt3);
		 
		 

		 flightVital.setBounds(screenSize.width*5/9 +5,0,screenSize.width*2/9,screenSize.height*3/5-45 );    
		 flightVital.setBackground(Color.BLACK); 
		 flightVital.setLayout(null);
		 flightVital.setVisible(true);
		 add(flightVital);
		 
		 JPanel landTakeoff =  new JPanel();
		 
		 JLabel lT1 = new JLabel("LAND :");
		 lT1.setForeground(Color.white);
		 lT1.setFont(new Font("Calibri", Font.BOLD, 22));
		 lT1.setBounds(5,10, 100,30);
		 landTakeoff.add(lT1);
		 
		 confirm3 = new RoundedJButton("CONFIRM");
		 confirm3.setBackground(Color.LIGHT_GRAY);
		 confirm3.setFont(new Font("Calibri", Font.BOLD, 16));
		 confirm3.setBounds(235, 75, 100, 30);
		 confirm3.addActionListener(this);
		 landTakeoff.add(confirm3);
		 
		 JLabel lT2 = new JLabel("FLIGHT NUMBER");
		 lT2.setForeground(Color.white);
		 lT2.setFont(new Font("Calibri", Font.BOLD, 18));
		 lT2.setBounds(20,45, 200,20);
		 landTakeoff.add(lT2);
		 
		 lTT1 = new RoundedJTextField(20);
		 lTT1.setBounds(160, 40,150, 30);
		 lTT1.setFont(new Font("Calibri", Font.BOLD, 16));  
		 lTT1.setForeground(Color.BLACK);
		 lTT1.setBackground(Color.LIGHT_GRAY);
		 lTT1.setHorizontalAlignment(SwingConstants.CENTER);
		 landTakeoff.add(lTT1);	 
		 
		 JLabel lT4 = new JLabel("OVERRIDE");
		 lT4.setForeground(Color.white);
		 lT4.setFont(new Font("Calibri", Font.BOLD, 22));
		 lT4.setBounds(20,115, 100,20);
		 landTakeoff.add(lT4);
		 
		 lTB2 =new FlipSwitch(this);
		 lTB2.setBackground(Color.LIGHT_GRAY);		
		 lTB2.setBounds(150,80,50,77);
		 lTB2.addActionListener(this);
		 landTakeoff.add(lTB2);
		 
		 JLabel lT5 = new JLabel("TAKEOFF :");
		 lT5.setForeground(Color.white);
		 lT5.setFont(new Font("Calibri", Font.BOLD, 22));
		 lT5.setBounds(5,170, 150,30);
		 landTakeoff.add(lT5);
		 
		 confirm4 = new RoundedJButton("CONFIRM");
		 confirm4.setBackground(Color.LIGHT_GRAY);
		 confirm4.setFont(new Font("Calibri", Font.BOLD, 16));
		 confirm4.setBounds(235, 230, 100, 30);
		 confirm4.addActionListener(this);
		 landTakeoff.add(confirm4);
		 
		 JLabel lT6 = new JLabel("FLIGHT NUMBER");
		 lT6.setForeground(Color.white);
		 lT6.setFont(new Font("Calibri", Font.BOLD, 18));
		 lT6.setBounds(20,200, 150,20);
		 landTakeoff.add(lT6);
		 
		 lTT2 = new RoundedJTextField(20);
		 lTT2.setBounds(160,195,150, 30);
		 lTT2.setFont(new Font("Calibri", Font.BOLD, 16));  
		 lTT2.setForeground(Color.BLACK);
		 lTT2.setBackground(Color.LIGHT_GRAY);
		 lTT2.setHorizontalAlignment(SwingConstants.CENTER);
		 landTakeoff.add(lTT2);
		 
		 JLabel lT7 = new JLabel("ACTION STATUS");
		 lT7.setForeground(Color.white);
		 lT7.setFont(new Font("Calibri", Font.BOLD, 18));
		 lT7.setBounds(5,275, 200,20);
		 landTakeoff.add(lT7);
		 
		 lTT3 = new JLabel("");
		 lTT3.setBounds(140, 270,200, 30);
		 lTT3.setFont(new Font("Calibri", Font.BOLD, 16));  // Replace with table as we need to display text not take input
		 lTT3.setForeground(Color.BLACK);
		 lTT3.setBackground(Color.LIGHT_GRAY);
		 landTakeoff.add(lTT3);
		 
		 
		 landTakeoff.setBounds(screenSize.width*5/9 +5,screenSize.height*3/5 -40,screenSize.width*2/9,screenSize.height*2/5 );    
		 landTakeoff.setBackground(Color.BLACK); 
		 landTakeoff.setLayout(null);
		 landTakeoff.setVisible(true);
		 add(landTakeoff);
		 
JPanel loginPanel =  new JPanel();
		 
		 JLabel lP1 = new JLabel("WELCOME:");
		 lP1.setForeground(Color.white);
		 lP1.setFont(new Font("Calibri", Font.BOLD, 20));
		 lP1.setBounds(20,20, 200,21);
		 loginPanel.add(lP1);
		 
		 JLabel name = new JLabel(user.getName());
		 name.setForeground(Color.green);
		 name.setFont(new Font("Calibri", Font.BOLD, 18));
		 name.setBounds(125,20, 200,21);
		 loginPanel.add(name);
		 
		 JLabel lP2 = new JLabel("EMPLOYEE ID:");
		 lP2.setForeground(Color.white);
		 lP2.setFont(new Font("Calibri", Font.BOLD, 20));
		 lP2.setBounds(20,60, 200,21);
		 loginPanel.add(lP2);
		 
		 JLabel id = new JLabel(Integer.toString(user.getEmployee_id()));
		 id.setForeground(Color.green);
		 id.setFont(new Font("Calibri", Font.BOLD, 18));
		 id.setBounds(150,60, 200,21);
		 loginPanel.add(id);
		 
		 if (user.getAccessLevel() >= 2) {
			 LP2 =new RoundedJButton("EDIT SCHEDULE");
			 LP2.setBackground(Color.LIGHT_GRAY);
			 LP2.setFont(new Font("Calibri", Font.BOLD, 16));
			 LP2.setBounds(170,100,140,30);
		 	LP2.addActionListener(this);
		 	loginPanel.add(LP2);
		 }
		 
		 if(user.getAccessLevel() > 2) {
			 LP1 =new RoundedJButton("QUERY LOG");
			 LP1.setBackground(Color.LIGHT_GRAY);
			 LP1.setFont(new Font("Calibri", Font.BOLD, 16));
			 LP1.setBounds(20,100,140,30);
			 LP1.addActionListener(this);
			 loginPanel.add(LP1);
		 }
	     
		 
		 
		 logout = new RoundedJButton("LOGOUT");
		 logout.setFont(new Font("Calibri", Font.BOLD, 16));
		 logout.setBackground(Color.LIGHT_GRAY);
		 logout.setBounds(200, 60, 100, 30);
		 logout.addActionListener(this);
		 loginPanel.add(logout);
		 
		 loginPanel.setBounds(screenSize.width*7/9 + 10 ,0,screenSize.width*2/9-10 ,screenSize.height/6);    
		 loginPanel.setBackground(Color.BLACK); 
		 loginPanel.setLayout(null);
		 loginPanel.setVisible(true);
		 add(loginPanel);
		 
		 JPanel runwayDetail =  new JPanel();
		 
		 JLabel rD1 = new JLabel("RUNWAY DETAILS");
		 rD1.setForeground(Color.white);
		 rD1.setFont(new Font("Calibri", Font.BOLD, 25));
		 rD1.setBounds(80,10, 200,30);
		 runwayDetail.add(rD1);
		 
		 myRunways = new RoundedJButton("MY RUNWAYS");
		 myRunways.setBounds(15, 40, 140, 30);
		 myRunways.setFont(new Font("Calibri", Font.BOLD, 16));
		 myRunways.setBackground(Color.LIGHT_GRAY);
		 myRunways.addActionListener(this);
		 runwayDetail.add(myRunways);
		 
		 ref3 = new RoundedJButton("REFRESH");
		 ref3.setBackground(Color.LIGHT_GRAY);
		 ref3.setFont(new Font("Calibri", Font.BOLD, 16));
		 ref3.setBounds(170,40,140,30);
		 ref3.addActionListener(this);
		 runwayDetail.add(ref3);
		 
		JPanel runwayPanel = new JPanel(new BorderLayout());
		runwayPanel.setBackground(Color.LIGHT_GRAY);
		runwayPanel.setBounds(25, 75, 270,270 );
		runwayDetail.add(runwayPanel);
		
		rnTableModel = new DefaultTableModel();
		rnTableModel.addColumn("RUNWAY NO.");
		rnTableModel.addColumn("STATUS");
		
		runwayTable = new JTable(rnTableModel){
            private static final long serialVersionUID = 1L;

            public boolean isCellEditable(int row, int column) {                
                    return false;               
            };
        };
//
//        // Optionally, customize the appearance and behavior of the table
        runwayTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
//
//        // Create a scroll pane and add the table to it
        JScrollPane runwayTableScrollPane = new JScrollPane(runwayTable);
        runwayTable.getColumnModel().getColumn(0).setPreferredWidth(100);
        runwayTable.getColumnModel().getColumn(1).setPreferredWidth(100);

        // Add the scroll pane to the frame
        getContentPane().setLayout(new BorderLayout());
        runwayPanel.add(runwayTableScrollPane, BorderLayout.CENTER);
        
		runwayDetail.setBounds(screenSize.width*7/9 +10,screenSize.height/8+41,screenSize.width*2/9,screenSize.height/2-64);    
		runwayDetail.setBackground(Color.BLACK); 
		runwayDetail.setLayout(null);
		runwayDetail.setVisible(true);
		add(runwayDetail);
		 
		JPanel radarPanel =  new JPanel();
		radarPanel.setBounds(screenSize.width*7/9 +10, screenSize.height*2/3 - 53 ,screenSize.width*2/9 ,screenSize.height/3+50);    
		radarPanel.setBackground(Color.BLACK);
		radarPanel.setLayout(null);
		radarPanel.setVisible(true);
		
		JLabel radar = new JLabel();
		
		weatherData data = weatherObject.weatherCalc("New delhi");//our fixed ATC Tower
		
		
		
		JLabel rlabel1 = new JLabel("WINDSPEED:");
		rlabel1.setForeground(Color.white);
		rlabel1.setFont(new Font("Calibri", Font.BOLD, 18));
		rlabel1.setBounds(5,10,100,20);
		radarPanel.add(rlabel1);
		
		slt2 = new JLabel(Double.toString(data.windSpeed*1.94)+" knots");
		slt2.setBounds(110,5,100, 30);
		slt2.setFont(new Font("Calibri", Font.BOLD, 16));  
		slt2.setForeground(Color.CYAN);
		slt2.setBackground(Color.LIGHT_GRAY);
		radarPanel.add(slt2);
		
		JLabel rlabel2 = new JLabel("TEMPERATURE:");
		rlabel2.setForeground(Color.white);
		rlabel2.setFont(new Font("Calibri", Font.BOLD, 18));
		rlabel2.setBounds(5,35,200,20);
		radarPanel.add(rlabel2);
		
		slt3 = new JLabel(Double.toString(data.temperature)+" *C");
		slt3.setBounds(135, 30,180, 30);
		slt3.setFont(new Font("Calibri", Font.BOLD, 16));  
		slt3.setForeground(Color.CYAN);
		slt3.setBackground(Color.LIGHT_GRAY);
		radarPanel.add(slt3);
		
		JLabel rlabel3 = new JLabel("HUMIDITY:");
		rlabel3.setForeground(Color.white);
		rlabel3.setFont(new Font("Calibri", Font.BOLD, 18));
		rlabel3.setBounds(5,60,200,20);
		radarPanel.add(rlabel3);
		
		slt4 = new JLabel(Double.toString(data.humidity)+"%");

		slt4.setBounds(110, 55,180, 30);
		slt4.setFont(new Font("Calibri", Font.BOLD, 16));  
		slt4.setForeground(Color.CYAN);
		slt4.setBackground(Color.LIGHT_GRAY);
		radarPanel.add(slt4);
		
		JLabel rlabel4 = new JLabel("WEATHER:");
		rlabel4.setForeground(Color.white);
		rlabel4.setFont(new Font("Calibri", Font.BOLD, 18));
		rlabel4.setBounds(5,85,200,20);
		radarPanel.add(rlabel4);
		
		slt5 = new JLabel(data.weather);

		slt5.setBounds(110, 80,180, 30);
		slt5.setFont(new Font("Calibri", Font.BOLD, 16));  
		slt5.setForeground(Color.CYAN);
		slt5.setBackground(Color.LIGHT_GRAY);
		radarPanel.add(slt5);
		
		
		
		radar.setBounds(80, 35, 300, 300);
		radar.setBackground(Color.black);
		Image radar_gif =  new ImageIcon(getClass().getResource("radar-ezgif.com-optimize.gif")).getImage();
		radar.setIcon(new ImageIcon(radar_gif));
		
		radarPanel.add(radar);
		
		add(radarPanel);
		  
	    setSize(screenSize.width,screenSize.height);
		Container c = getContentPane();
		c.setBackground(Color.LIGHT_GRAY);	
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(null);
		setVisible(true);
	}

	public void actionPerformed(ActionEvent ae) {
		if(ae.getSource() == lTB2) {
			if(lTB2.getOrientation() == 0) {
				lTB2.flipOn();
			}
			else lTB2.flipOff();
		}
		else if(ae.getSource() == fvB1) {
			if(fvB1.getOrientation() == 0) {
				try {
					String result = controller.updateFlightVitals(fT1.getText(), "Fuel");
					if(result.equals("SUCCESS")) {
						lt3.setForeground(Color.green);
						lt3.setText(result);
						fvB1.flipOn();
						statPanel1.setBackground(Color.green);
					}
					else {
						lt3.setForeground(Color.red);
						lt3.setText(result);
					}
				} catch (SQLException e) {
					lt3.setForeground(Color.red);
					lt3.setText("ERROR IN SQL");
				}
			}
		}
		else if(ae.getSource() == fvB2) {
			if(fvB2.getOrientation() == 0) {
				try {
					String result = controller.updateFlightVitals(fT1.getText(), "Cargo");
					if(result.equals("SUCCESS")) {
						lt3.setForeground(Color.green);
						lt3.setText(result);
						fvB2.flipOn();
						statPanel2.setBackground(Color.green);
					}
					else {
						lt3.setForeground(Color.red);
						lt3.setText(result);
					}
				} catch (SQLException e) {
					lt3.setForeground(Color.red);
					lt3.setText("ERROR IN SQL");
				}
			}
		}
		else if(ae.getSource() == fvB3) {
			if(fvB3.getOrientation() == 0) {
				try {
					String result = controller.updateFlightVitals(fT1.getText(), "Safety Check");
					if(result.equals("SUCCESS")) {
						lt3.setForeground(Color.green);
						lt3.setText(result);
						fvB3.flipOn();
						statPanel3.setBackground(Color.green);
					}
					else {
						lt3.setForeground(Color.red);
						lt3.setText(result);
					}
				} catch (SQLException e) {
					lt3.setForeground(Color.red);
					lt3.setText("ERROR IN SQL");
				}
			}
		}
		else if(ae.getSource() == B0) {
			if(stat0 == 0) {
				try {
					if(!controller.updateRunwayStatus(lr1.getText(), "Free")) {
						lt1.setForeground(Color.red);
						lt1.setText("FAILURE");
					}
					lt1.setForeground(Color.green);
					lt1.setText("SUCCESS");
					B0.setBackground(Color.green);
					stat0 = 1;
				} catch (SQLException e) {
					lt1.setForeground(Color.red);
					lt1.setText("ERROR IN SQL");
				}
			}
			else if(stat0 == 1) {
				try {
					if(!controller.updateRunwayStatus(lr1.getText(), "Busy")) {
						lt1.setForeground(Color.red);
						lt1.setText("FAILURE");
					}
					lt1.setForeground(Color.green);
					lt1.setText("SUCCESS");
					B0.setBackground(Color.red);
					stat0 = 0;
				} catch (SQLException e) {
					lt1.setForeground(Color.red);
					lt1.setText("ERROR IN SQL");
				}
			}
		}
		else if(ae.getSource() == B1) {
			if(stat1 == 0) {
				try {
					if(!controller.updateUtilityVehicles(lr1.getText(), "Fire_Truck", "Free")) {
						lt1.setBackground(Color.red);
						lt1.setText("FAILURE");
					}
					lt1.setBackground(Color.green);
					lt1.setText("SUCCESS");
					B1.setBackground(Color.green);
					stat1 = 1;
				} catch (SQLException e) {
					lt1.setForeground(Color.red);
					lt1.setText("ERROR IN SQL");
				}
			}
			else if(stat1 == 1)  {
				try {
					if(!controller.updateUtilityVehicles(lr1.getText(), "Fire_Truck", "Busy")) {
						lt1.setBackground(Color.red);
						lt1.setText("FAILURE");
					}
					lt1.setBackground(Color.green);
					lt1.setText("SUCCESS");
					B1.setBackground(Color.red);
					stat1 = 0;
				} catch (SQLException e) {
					lt1.setForeground(Color.red);
					lt1.setText("ERROR IN SQL");
				}
			}
			else {
				lt1.setBackground(Color.red);
				lt1.setText("ENTER VALUE");
			}
		}
		else if(ae.getSource() == B2) {
			if(stat2 == 0) {
				try {
					if(!controller.updateUtilityVehicles(lr1.getText(), "Cargo_Loader", "Free")) {
						lt1.setBackground(Color.red);
						lt1.setText("FAILURE");
					}
					lt1.setBackground(Color.green);
					lt1.setText("SUCCESS");
					B2.setBackground(Color.green);
					stat2 = 1;
				} catch (SQLException e) {
					lt1.setForeground(Color.red);
					lt1.setText("ERROR IN SQL");
				}
			}
			else if(stat2 == 1) {
				try {
					if(!controller.updateUtilityVehicles(lr1.getText(), "Cargo_Loader", "Busy")) {
						lt1.setBackground(Color.red);
						lt1.setText("FAILURE");
					}
					lt1.setBackground(Color.green);
					lt1.setText("SUCCESS");
					B2.setBackground(Color.red);
					stat2 = 0;
				} catch (SQLException e) {
					lt1.setForeground(Color.red);
					lt1.setText("ERROR IN SQL");
				}
			}
			else {
				lt1.setBackground(Color.red);
				lt1.setText("ENTER VALUE");
			}
		}
		else if(ae.getSource() == B3) {
			if(stat3 == 0) {
				try {
					if(!controller.updateUtilityVehicles(lr1.getText(), "Ambulance", "Free")) {
						lt1.setBackground(Color.red);
						lt1.setText("FAILURE");
					}
					lt1.setBackground(Color.green);
					lt1.setText("SUCCESS");
					B3.setBackground(Color.green);
					stat3 = 1;
				} catch (SQLException e) {
					lt1.setForeground(Color.red);
					lt1.setText("ERROR IN SQL");
				}
			}
			else if(stat3 == 1)  {
				try {
					if(!controller.updateUtilityVehicles(lr1.getText(), "Ambulance", "Busy")) {
						lt1.setBackground(Color.red);
						lt1.setText("FAILURE");
					}
					lt1.setBackground(Color.green);
					lt1.setText("SUCCESS");
					B3.setBackground(Color.red);
					stat3 = 0;
				} catch (SQLException e) {
					lt1.setForeground(Color.red);
					lt1.setText("ERROR IN SQL");
				}
			}
			else {
				lt1.setBackground(Color.red);
				lt1.setText("ENTER VALUE");
			}
		}
		else if(ae.getSource() == B4) {
			if(stat4 == 0) {
				try {
					if(!controller.updateUtilityVehicles(lr1.getText(), "Fuel_Bowser", "Free")) {
						lt1.setBackground(Color.red);
						lt1.setText("FAILURE");
					}
					lt1.setBackground(Color.green);
					lt1.setText("SUCCESS");
					B4.setBackground(Color.green);
					stat4 = 1;
				} catch (SQLException e) {
					lt1.setForeground(Color.red);
					lt1.setText("ERROR IN SQL");
				}
			}
			else if(stat4 == 1)  {
				try {
					if(!controller.updateUtilityVehicles(lr1.getText(), "Fuel_Bowser", "Busy")) {
						lt1.setBackground(Color.red);
						lt1.setText("FAILURE");
					}
					lt1.setBackground(Color.green);
					lt1.setText("SUCCESS");
					B4.setBackground(Color.red);
					stat4 = 0;
				} catch (SQLException e) {
					lt1.setForeground(Color.red);
					lt1.setText("ERROR IN SQL");
				}
			}
			else {
				lt1.setBackground(Color.red);
				lt1.setText("ENTER VALUE");
			}
		}
		else if(ae.getSource() == B5) {
			if(stat5 == 0) {
				try {
					if(!controller.updateUtilityVehicles(lr1.getText(), "Refueler_Jet", "Free")) {
						lt1.setBackground(Color.red);
						lt1.setText("FAILURE");
					}
					lt1.setBackground(Color.green);
					lt1.setText("SUCCESS");
					B5.setBackground(Color.green);
					stat5 = 1;
				} catch (SQLException e) {
					lt1.setForeground(Color.red);
					lt1.setText("ERROR IN SQL");
				}
			}
			else if(stat5 == 1)  {
				try {
					if(!controller.updateUtilityVehicles(lr1.getText(), "Refueler_Jet", "Busy")) {
						lt1.setBackground(Color.red);
						lt1.setText("FAILURE");
					}
					lt1.setBackground(Color.green);
					lt1.setText("SUCCESS");
					B5.setBackground(Color.red);
					stat5 = 0;
				} catch (SQLException e) {
					lt1.setForeground(Color.red);
					lt1.setText("ERROR IN SQL");
				}
			}
			else {
				lt1.setBackground(Color.red);
				lt1.setText("ENTER VALUE");
			}
		}
		else if(ae.getSource() == B6) {
			if(stat6 == 0) {
				try {
					if(!controller.updateUtilityVehicles(lr1.getText(), "Safety_Check_Rig", "Free")) {
						lt1.setBackground(Color.red);
						lt1.setText("FAILURE");
					}
					lt1.setBackground(Color.green);
					lt1.setText("SUCCESS");
					B6.setBackground(Color.green);
					stat6 = 1;
				} catch (SQLException e) {
					lt1.setForeground(Color.red);
					lt1.setText("ERROR IN SQL");
				}
			}
			else if(stat6 == 1)  {
				try {
					if(!controller.updateUtilityVehicles(lr1.getText(), "Safety_Check_Rig", "Busy")) {
						lt1.setBackground(Color.red);
						lt1.setText("FAILURE");
					}
					lt1.setBackground(Color.green);
					lt1.setText("SUCCESS");
					B6.setBackground(Color.red);
					stat6 = 0;
				} catch (SQLException e) {
					lt1.setForeground(Color.red);
					lt1.setText("ERROR IN SQL");
				}
			}
			else {
				lt1.setBackground(Color.red);
				lt1.setText("ENTER VALUE");
			}
		}
		else if(ae.getSource() == ref1) {
			sfTableModel.setRowCount(0);
			try {
				ResultSet rs = controller.getScheduledFlights();
				while(rs.next()) {
					Object[] rowData = {rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5)};
					sfTableModel.addRow(rowData);
				}
			} catch (SQLException e) {
			System.out.println("Error in SQL\n" + e);
			}
		}
		else if(ae.getSource() == myFlightsButton) {
			sfTableModel.setRowCount(0);
			try {
				ResultSet rs = controller.getMyScheduledFlights();
				while(rs.next()) {
					Object[] rowData = {rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5)};
					sfTableModel.addRow(rowData);
				}
			} catch (SQLException e) {
			System.out.println("Error in SQL\n" + e);
			}
		}
		else if(ae.getSource() == myLandedFlightsNoTransit) {
			flTableModel.setRowCount(0);
			try {
				ResultSet rs = controller.getMyCurrentFlightsNoTransit();
				while(rs.next()) {
					Object[] rowData = {rs.getString(1), rs.getString(3), rs.getString(5), rs.getString(4)};
					flTableModel.addRow(rowData);
				}
			} catch (SQLException e) {
			System.out.println("Error in SQL\n" + e);
			}
		}
		else if(ae.getSource() == myLandedFlightsYesTransit) {
			flTableModel.setRowCount(0);
			try {
				ResultSet rs = controller.getMyCurrentFlightsYesTransit();
				while(rs.next()) {
					Object[] rowData = {rs.getString(1), rs.getString(3), rs.getString(5), rs.getString(4)};
					flTableModel.addRow(rowData);
				}
			} catch (SQLException e) {
			System.out.println("Error in SQL\n" + e);
			}
		}
		else if(ae.getSource() == ref2) {
			flTableModel.setRowCount(0);
			try {
				ResultSet rs = controller.getCurrentFlights();
				while(rs.next()) {
					Object[] rowData = {rs.getString(1), rs.getString(3), rs.getString(5), rs.getString(4)};
					flTableModel.addRow(rowData);
				}
			} catch (SQLException e) {
			System.out.println("Error in SQL\n" + e);
			}
		}
		else if(ae.getSource() == ref3) {
			rnTableModel.setRowCount(0);
			try {
				ResultSet rs = controller.getRunwayDetails();
				while(rs.next()) {
					Object[] rowData = {rs.getString(1), rs.getString(2)};
					rnTableModel.addRow(rowData);
				}
			} catch (SQLException e) {
			System.out.println("Error in SQL\n" + e);
			}
		}
		else if(ae.getSource() == myRunways) {
			rnTableModel.setRowCount(0);
			try {
				ResultSet rs = controller.getMyRunways();
				while(rs.next()) {
					Object[] rowData = {rs.getString(1), rs.getString(2)};
					rnTableModel.addRow(rowData);
				}
			} catch (SQLException e) {
			System.out.println("Error in SQL\n" + e);
			}
			
		}
		else if(ae.getSource() == logout) {
			Timer timer = new Timer(500, new ActionListener() {
                public void actionPerformed(ActionEvent e) {      
                	dispose();                                         
                    LoginPortal page = new LoginPortal(con);
                    page.executePage();								  
                }
            });				
			timer.setRepeats(false);
            timer.start();
            return;

		}
		if(user.getAccessLevel() > 2) {
			if(ae.getSource() == LP1) {
				Timer timer = new Timer(500, new ActionListener() {
                    public void actionPerformed(ActionEvent e) {                                               
                        QueryPage page = new QueryPage(con, user, controller, mainFrame);
                        page.executePage();								  
                    }
                });				
				timer.setRepeats(false);
                timer.start();
                return;
			}
		}
		if(user.getAccessLevel() >= 2) {
			if(ae.getSource() == LP2) {
				Timer timer = new Timer(500, new ActionListener() {
                    public void actionPerformed(ActionEvent e) {                                              
                        FlightSchedulerPage page = new FlightSchedulerPage(con, user, controller);
                        page.executePage();								  
                    }
                });				
				timer.setRepeats(false);
                timer.start();
                return;
			}
		}
		if(ae.getSource() == confirm1) {
			String flightNumber = fT1.getText();
			ResultSet rs;
			try {
				rs = controller.getCurrentFlight(flightNumber);
				if(!rs.next()) {
					lt3.setText("");
					lt2.setForeground(Color.RED);
					lt2.setText("FLIGHT NOT FOUND");
					fvB1.flipOff();
					fvB2.flipOff();
					fvB3.flipOff();
					statPanel1.setBackground(Color.RED);
					statPanel2.setBackground(Color.RED);
					statPanel3.setBackground(Color.RED);
				}
				else if(!controller.transitCheck(fT1.getText())) {
					lt3.setText("");
					lt2.setForeground(Color.RED);
					lt2.setText("NO TRANSIT");
					fvB1.flipOff();
					fvB2.flipOff();
					fvB3.flipOff();
					statPanel1.setBackground(Color.RED);
					statPanel2.setBackground(Color.RED);
					statPanel3.setBackground(Color.RED);
					return;
				}
				else {
					lt2.setForeground(Color.green);
					lt2.setText("FLIGHT FOUND");
					if(rs.getString(3).equals("Yes")) {
						fvB1.flipOn();
						statPanel1.setBackground(Color.green);
					}
					else {
						fvB1.flipOff();
						statPanel1.setBackground(Color.red);
					}
					if(rs.getString(5).equals("Yes")) {
						fvB2.flipOn();
						statPanel2.setBackground(Color.green);
					}
					else {
						fvB2.flipOff();
						statPanel2.setBackground(Color.red);
					}
					if(rs.getString(4).equals("Yes")) {
						fvB3.flipOn();
						statPanel3.setBackground(Color.green);
					}
					else {
						fvB3.flipOff();
						statPanel3.setBackground(Color.red);
					}
				}
			} catch (SQLException e) {
				System.out.println("Error in SQL\n" + e);
			}
		}
		else if(ae.getSource() == confirm2) {
			String runwayNumber = lr1.getText();
			try {
				if(!controller.runwayExistence(runwayNumber)) {
					lt1.setForeground(Color.red);
					lt1.setText("NO RUNWAY FOUND");
					B0.setBackground(Color.red);
					B1.setBackground(Color.red);
					B2.setBackground(Color.red);
					B3.setBackground(Color.red);
					B4.setBackground(Color.red);
					B5.setBackground(Color.red);
					B6.setBackground(Color.red);
					stat0 = 0; stat1 = 0; stat2 = 0; stat3 = 0; stat4 = 0; stat5 = 0; stat6 = 0;
					return;
				}
				if(!controller.runwayVerifier(runwayNumber)) {
					lt1.setForeground(Color.red);
					lt1.setText("ACCESS DENIED");
					B0.setBackground(Color.red);
					B1.setBackground(Color.red);
					B2.setBackground(Color.red);
					B3.setBackground(Color.red);
					B4.setBackground(Color.red);
					B5.setBackground(Color.red);
					B6.setBackground(Color.red);
					stat0 = 0; stat1 = 0; stat2 = 0; stat3 = 0; stat4 = 0; stat5 = 0; stat6 = 0;
					return;
				}
				ResultSet rs = controller.getRunwayAvailabilty(runwayNumber);
				rs.next();
				if(rs.getString(1).equals("Busy")) {
					B0.setBackground(Color.red);
					stat0 = 0;
				}
				else {
					B0.setBackground(Color.green);
					stat0 = 1;
				}
				rs = controller.getMyRunwayVehicles(runwayNumber);
				lt1.setForeground(Color.green);
				lt1.setText("RUNWAY FOUND");
				while(rs.next()) {
					if(rs.getString(1).equals("Fire_Truck")) {
						if(rs.getString(2).equals("Free")) {
							B1.setBackground(Color.green);
							stat1 = 1;
						}
						else {
							B1.setBackground(Color.red);
							stat1 = 0;
						}
					}
					else if(rs.getString(1).equals("Cargo_Loader")) {
						if(rs.getString(2).equals("Free")) {
							B2.setBackground(Color.green);
							stat2 = 1;
						}
						else {
							B2.setBackground(Color.red);
							stat2 = 0;
						}
					}
					else if(rs.getString(1).equals("Ambulance")) {
						if(rs.getString(2).equals("Free")) {
							B3.setBackground(Color.green);
							stat3 = 1;
						}
						else {
							B3.setBackground(Color.red);
							stat3 = 0;
						}
					}
					else if(rs.getString(1).equals("Fuel_Bowser")) {
						if(rs.getString(2).equals("Free")) {
							B4.setBackground(Color.green);
							stat4 = 1;
						}
						else {
							B4.setBackground(Color.red);
							stat4 = 0;
						}
					}
					else if(rs.getString(1).equals("Refueler_Jet")) {
						if(rs.getString(2).equals("Free")) {
							B5.setBackground(Color.green);
							stat5 = 1;
						}
						else {
							B5.setBackground(Color.red);
							stat5 = 0;
						}
					}
					else if(rs.getString(1).equals("Safety_Check_Rig")) {
						if(rs.getString(2).equals("Free")) {
							B6.setBackground(Color.green);
							stat6 = 1;
						}
						else {
							B6.setBackground(Color.red);
							stat6 = 0;
						}
					}
				}
			} catch (SQLException e) {
				lt1.setForeground(Color.red);
				lt1.setText("ERROR IN SQL");
			}
		}
		else if(ae.getSource() == confirm3) {
			String flightNumber = lTT1.getText();
			String isTransit = "";
			if(lTB2.getOrientation() == 0) {
				try {
					if(!controller.scheduledPlaneExistence(flightNumber)) {
						lTT3.setForeground(Color.red);
						lTT3.setText("FLIGHT NOT FOUND");
					}
					else {
						if(!controller.scheduledFlightUserVerification(flightNumber)) {
							lTT3.setForeground(Color.red);
							lTT3.setText("ACCESS DENIED");
							return;
						}
						if(controller.hasPlaneAlreadyLanded(flightNumber)) {
							lTT3.setForeground(Color.red);
							lTT3.setText("ALREADY ARRIVED");
							return;
						}
						ResultSet rs = controller.runwayAvailabilty();
						if(!rs.next()) {
							lTT3.setForeground(Color.red);
							lTT3.setText("NO RUNWAY AVAILABLE");
							return;
						}
						if(controller.isFromDelhi(flightNumber)) {
							lTT3.setForeground(Color.red);
							lTT3.setText("ORIGIN");
							return;
						}
						if(controller.findIsTransit(flightNumber)) {
							isTransit = "Yes";
						}
						else {
							isTransit = "No";
						}
						String chosen_runway = rs.getString(1);
						if(!controller.landPlane(flightNumber, isTransit, chosen_runway)) {
								lTT3.setForeground(Color.red);
								lTT3.setText("LANDING FAILED");
								return;
							}
						lTT3.setForeground(Color.green);
						lTT3.setText("FLIGHT LANDED");
						}
				} catch (SQLException e) {
					lTT3.setForeground(Color.red);
					lTT3.setText("ERROR IN SQL");
				}
			}
			else if(lTB2.getOrientation() == 1) {
				try {
					if(controller.hasPlaneAlreadyLanded(flightNumber)) {
						lTT3.setForeground(Color.red);
						lTT3.setText("ALREADY ARRIVED");
						return;
					}
					ResultSet rs = controller.runwayAvailabilty();
					if(!rs.next()) {
						lTT3.setForeground(Color.red);
						lTT3.setText("NO RUNWAY AVAILABLE");
						return;
					}
					String chosen_runway = rs.getString(1);
					if(!controller.landPlane(flightNumber, isTransit, chosen_runway)) {
						lTT3.setForeground(Color.red);
						lTT3.setText("LANDING FAILED");
						return;
					}
					lTT3.setForeground(Color.green);
					lTT3.setText("FLIGHT LANDED");
				} catch (SQLException e) {
					lTT3.setForeground(Color.red);
					lTT3.setText("ERROR IN SQL");
				}
			}
		}
		else if(ae.getSource() == confirm4) {
			String flightNumber = lTT2.getText();
			try {
				if(!controller.isFromDelhi(flightNumber)) {
					if(!controller.currentPlaneExistence(flightNumber)) {
						lTT3.setForeground(Color.red);
						lTT3.setText("FLIGHT NOT LANDED");
						return;
					}
					if(!controller.userCurrentPlaneVerification(flightNumber)) {
						lTT3.setForeground(Color.red);
						lTT3.setText("ACCESS DENIED");
						return;
					}
				}
				ResultSet rs = controller.runwayAvailabilty();
				if(!rs.next()) {
					lTT3.setForeground(Color.red);
					lTT3.setText("NO RUNWAY AVAILABLE");
					return;
				}
				String chosen_runway = rs.getString(1);
				if(!controller.isFromDelhi(flightNumber)) {
					if(!controller.transitCheck(flightNumber)) {
							lTT3.setForeground(Color.red);
							lTT3.setText("FINAL DESTINATION");
							return;
						}
					if(!controller.preFlightCheck(flightNumber)) {
						lTT3.setForeground(Color.red);
						lTT3.setText("PRE-FLIGHT CHECK FAILED");
						return;
					}
				}
				if(!controller.takeoff(flightNumber, chosen_runway)) {
					lTT3.setForeground(Color.red);
					lTT3.setText("TAKE-OFF FAILED");
					return;
				}
				lTT3.setForeground(Color.green);
				lTT3.setText("TAKE-OFF SUCCESSFUL");
			} catch (SQLException e) {
				lTT3.setForeground(Color.red);
				lTT3.setText("ERROR IN SQL");
			}
		}
		else if(ae.getSource() == wp1) {
			String flightNumber = wpT2.getText();
			if(flightNumber.isEmpty()) {
				wpT1.setForeground(Color.red);
				wpT1.setText("ENTER FLIGHT NUMBER");
				wp1.flipOff();
				statPanelWP.setBackground(Color.red);
				return;
			}
			try {
				ResultSet rs = controller.jetAvailabilty();
				if(rs == null) {
					wp1.flipOff();
					statPanelWP.setBackground(Color.red);
					wpT1.setForeground(Color.red);
					wpT1.setText("NO JET FOUND/AVAILABLE");
					return;
				}
				else {
					controller.scrambleJet(flightNumber, rs.getString(1));
					wpT1.setForeground(Color.green);
					wpT1.setText("JET SCRAMBLED(R_NO: " + rs.getString(1) + ")");
					wp1.flipOn();
					statPanelWP.setBackground(Color.green);
					return;
				}
			}catch (SQLException e) {
				wpT1.setForeground(Color.red);
				wpT1.setText("ERROR IN SQL");
			}
		}
		else if(ae.getSource() == wpB1) {
			String flightNumber = wpT2.getText();
			if(flightNumber.isEmpty()) {
				wpT1.setForeground(Color.red);
				wpT1.setText("ENTER FLIGHT NUMBER");
				return;
			}
			try {
				ResultSet rs = controller.getPlane(flightNumber);
				if(!rs.next()) {
					wpT1.setForeground(Color.red);
					wpT1.setText("INVALID FLIGHT NUMBER");
					return;
				}
				String origin = rs.getString(1);
				weatherData data = weatherObject.weatherCalc(origin);
				wlt2.setText(Double.toString(data.windSpeed * 1.94) + " kts");
				wlt3.setText(Double.toString(data.temperature) + " *C");
				wlt4.setText(Double.toString(data.humidity) + "%");
				wlt5.setText(data.weather);
				wpT1.setForeground(Color.green);
				wpT1.setText("SUCCESS");
			} catch (SQLException e) {
				wpT1.setForeground(Color.red);
				wpT1.setText("ERROR IN SQL");
			}
		}
	}
}