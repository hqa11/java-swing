package ui;

import java.awt.Graphics;
import java.awt.GridLayout;

import javax.swing.JPanel;

import config.Constant;

public class RoomListPanel extends JPanel{
	public RoomListPanel(){
		this.setLayout(new GridLayout(11,1));
	}

@Override
protected void paintComponent(Graphics g) {
	g.drawImage(Constant.UI_CONSTANT.ROOM_BG_IMG, 0,
			0,this.getWidth(),this.getHeight(),this);
	
	g.drawImage(Constant.UI_CONSTANT.GROUP_MODEL_IMG, 30,
			3,200,60,this);
	super.paintComponent(g);
}
	
}