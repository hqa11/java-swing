package ui;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JLabel;

public class RoomLabel  extends JLabel{
private String toolTip;
		public RoomLabel(int roomNo){
			this.setBounds(30,10,200,50);
		//	this.setForeground(new Color(212,22,122));
			this.setFont(new Font("ºÚÌå",Font.BOLD, 15));
            this.setAlignmentX(50f);
            this.setVisible(true);
        	RoomLabel.this.setToolTipText(toolTip);
	}

}
