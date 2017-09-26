package ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import config.Constant;

public class RoomPanel extends JPanel{
private int roomNo;
private RoomLabel rb;
private RoomEntryButton reb;
//0.没人 1.不满2.满了
private int roomState = 0;
public void setRoomState(int roomState) {
	this.roomState = roomState;
}
public 	RoomPanel(int roomNo,RoomLabel rb,RoomEntryButton reb){
	this.roomNo = roomNo;
	this.setRb(rb);
	this.setReb(reb);
	this.setBorder(BorderFactory.createLineBorder(Color.black));
	this.setLayout(null);
	this.setOpaque(false);
	this.setBorder(new EmptyBorder(10, 0, 10, 10)); // 设置边距
	this.add(rb);
	this.add(reb);
//	this.setBackground(Color.yellow);
}

@Override
public void paintComponent(Graphics g) {
	Image bac = null;
	/*if(roomState == 0){
		 bac = Constant.UI_CONSTANT.NOONE_BG_IMG;
	}else if(roomState == 1){
		 bac = Constant.UI_CONSTANT.ONE_BG_IMG;
	}else{
		bac = Constant.UI_CONSTANT.FULL_BG_IMG;
	}
	ImageIcon icon = new ImageIcon(bac);  
	g.drawImage(icon.getImage(), 0,
			0,this.getWidth(),this.getHeight(),null);*/
	g.drawImage( Constant.UI_CONSTANT.BORDER_IMG, 0,
			0,this.getWidth(),this.getHeight(),5,0,Constant.UI_CONSTANT.BORDER_IMG.getWidth(null),
			Constant.UI_CONSTANT.BORDER_IMG.getHeight(null),null);
}
public RoomEntryButton getReb() {
	return reb;
}
public void setReb(RoomEntryButton reb) {
	this.reb = reb;
}
public RoomLabel getRb() {
	return rb;
}
public void setRb(RoomLabel rb) {
	this.rb = rb;
}

}