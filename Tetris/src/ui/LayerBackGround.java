package ui;

import java.awt.Graphics;

import config.Constant;

public class LayerBackGround extends Layer{

	public LayerBackGround(int x, int y, int w, int h) {
		super(x, y, w, h);
	}

	public void paint(Graphics g){
		
		g.drawImage(Constant.UI_CONSTANT.CURRENT_BG_IMG, 0,
		0,1200,700,this.getJp());
		
	}
	
}
