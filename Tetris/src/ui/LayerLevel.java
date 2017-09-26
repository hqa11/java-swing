package ui;

import java.awt.Graphics;

import config.Constant;

public class LayerLevel extends Layer{

	public LayerLevel(int x, int y, int w, int h) {
		super(x, y, w, h);
	}

	public void paint(Graphics g){
		
		this.createWindow(g);
		g.drawImage(Constant.UI_CONSTANT.LEVEL_IMG, this.x + this.PADDING,
		this.y + this.PADDING,null);
		this.drawNum(32, 62,this.gameDto.getNowlevel(), g);
	}
	

}
