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

import java.sql.*;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.Timer;
import javax.swing.table.DefaultTableModel;

public class QueryPage extends JFrame implements ActionListener {
	Connection con;
	User user;
	ATC controller;
	JFrame mainFrame;
	JFrame queryPageFrame;
	
	JLabel heading;
	JTable query_table;
	RoundedJButton refresh, back_to_main;
	JPanel query_panel;
	JButton clear_nt_flts;
	DefaultTableModel model;
	RoundedJButton updateAccess;
	
	Image on =  new ImageIcon(this.getClass().getResource("on_flip_switch.png")).getImage();
	Image off = new ImageIcon(this.getClass().getResource("off_flip_switch.png")).getImage();
	int on_off;
	
	public QueryPage(Connection con, User user, ATC controller, JFrame mainFrame) {
		this.con = con;
		this.user = user;
		this.controller = controller;
		this.mainFrame = mainFrame;
		queryPageFrame = this;
	}
	
	public void executePage() {
		setTitle("ATC/Query_Page");
		
		setTitle("Air Traffic Control");
		heading = new JLabel("QUERY LOG");
		heading.setForeground(Color.white);
		heading.setFont(new Font("Calibri", Font.BOLD, 30));
		heading.setBounds(100, 50, 150 ,50);
		
		query_panel = new JPanel(new BorderLayout());
		query_panel.setBackground(Color.gray);
		query_panel.setBounds(100, 150, 1350, 600);
		
		refresh = new RoundedJButton("REFRESH");
		refresh.setBackground(Color.gray);
		refresh.setBounds(1350, 115, 100, 30);
		refresh.setFont(new Font("Calibri", Font.BOLD, 16));
		
		updateAccess = new RoundedJButton("UPDT EMP ACC");
		updateAccess.setBackground(Color.LIGHT_GRAY);
		updateAccess.setBounds(1350, 75, 150, 30);
		updateAccess.setFont(new Font("Calibri", Font.BOLD, 16));
		updateAccess.addActionListener(this);
		
	
		model = new DefaultTableModel();
        model.addColumn("QUERY ID");
        model.addColumn("EMPLOYEE ID");
        model.addColumn("QUERY");
        model.addColumn("TIMESTAMP");

        // Create the JTable with the model
        query_table = new JTable(model){
            private static final long serialVersionUID = 1L;

            public boolean isCellEditable(int row, int column) {                
                    return false;               
            };
        };

        // Optionally, customize the appearance and behavior of the table
        query_table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        // Create a scroll pane and add the table to it
        JScrollPane scrollPane = new JScrollPane(query_table);
        query_table.getColumnModel().getColumn(0).setPreferredWidth(200);
        query_table.getColumnModel().getColumn(1).setPreferredWidth(200);
        query_table.getColumnModel().getColumn(2).setPreferredWidth(647);
        query_table.getColumnModel().getColumn(3).setPreferredWidth(300);

        // Add the scroll pane to the frame
        getContentPane().setLayout(new BorderLayout());
        query_panel.add(scrollPane, BorderLayout.CENTER);
        pack();
        
        back_to_main = new RoundedJButton("Back to Main");
        back_to_main.setBackground(Color.LIGHT_GRAY);
        back_to_main.setBounds(1350, 10, 150, 30);
        back_to_main.addActionListener(this);
        
        clear_nt_flts = new RoundedJButton("CLEAR NT FLTS");
        clear_nt_flts.setBounds(1350, 43, 150, 30);
        clear_nt_flts.setBackground(Color.LIGHT_GRAY);
        clear_nt_flts.addActionListener(this);
        
		add(heading);
		add(query_panel);
		add(refresh);
		add(back_to_main);
		add(updateAccess);
		add(clear_nt_flts);
		
		refresh.addActionListener(this);
		
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		setSize(screenSize.width,screenSize.height);
		Container c = getContentPane();
		c.setBackground(Color.black);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(null);
		setVisible(true);
	}
	
	public void actionPerformed(ActionEvent ae) {
		if(ae.getSource() == refresh) {
			model.setRowCount(0);
			try {
				ResultSet rs = controller.getQueryLog();
				while(rs.next()) {
					Object[] rowData = {rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4)};
					model.addRow(rowData);
				}
			} catch (SQLException e) {
			System.out.println("Error in SQL\n" + e);
			}
		}
		
		else if(ae.getSource() == back_to_main) {
			Timer timer = new Timer(500, new ActionListener() {
                public void actionPerformed(ActionEvent e) {   
                    dispose();								  
                }
            });				
			timer.setRepeats(false);
            timer.start();
            return;
		}
		else if(ae.getSource() == clear_nt_flts) {
			clear_nt_flts.setBackground(Color.red);
			Timer timer = new Timer(500, new ActionListener() {
                public void actionPerformed(ActionEvent e) {   
                	try {
						controller.clearNonTransit();
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
            		clear_nt_flts.setBackground(Color.green);
                }
            });
			timer.setRepeats(false);
            timer.start();
            return;
		}
		else if(ae.getSource() == updateAccess) {
			
			Timer timer  = new Timer(500,new ActionListener() {
					public void actionPerformed(ActionEvent e) {
					AccessUpdatePage page = new AccessUpdatePage(con, user, controller, mainFrame, queryPageFrame);
					try {
						page.executePage();
					} catch (Exception e1) {
						System.out.println("Error in updation access "+e1.getMessage());
					}
				}
				
			});
			timer.setRepeats(false);
			timer.start();
			return;
		}
	}
	
}
