package ui;

import java.awt.Graphics;

import config.Constant;

public class LayerBackGround2 extends Layer{

	public LayerBackGround2(int x, int y, int w, int h) {
		super(x, y, w, h);
	}

	public void paint(Graphics g){
		
		g.drawImage(Constant.UI_CONSTANT.ROOM_BG_IMG, 0,
		0,444,790,null);
		
	}
	
}
