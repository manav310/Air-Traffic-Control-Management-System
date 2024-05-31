package atc_project;

import java.awt.Color;
import java.awt.Image;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;

// ****** Always set width as 50 and height as 77 ****** //

public class FlipSwitch extends JButton {
	int on_off;
	Image on;
	Image off;
	
	public FlipSwitch(JFrame frame) {
		super();
		on =  new ImageIcon(frame.getClass().getResource("on_flip_switch.png")).getImage();
		off = new ImageIcon(frame.getClass().getResource("off_flip_switch.png")).getImage();
		this.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		this.setContentAreaFilled(false);
		this.setBackground(Color.black);
		this.setIcon(new ImageIcon(off));
		on_off = 0;
		this.setIcon(new ImageIcon(off));
	}
	
	public int getOrientation() {
		return this.on_off;
	}
	
	public void  flipOff() {
		if(on_off == 1) {
			this.setIcon(new ImageIcon(off));
			on_off = 0;
		}
	}
	public void flipOn() {
		if(on_off == 0) {
			this.setIcon(new ImageIcon(on));
			on_off = 1;
		}
	}
}
