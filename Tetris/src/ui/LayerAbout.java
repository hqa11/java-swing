package ui;

import java.awt.Graphics;

import config.Constant;

public class LayerAbout extends Layer{

	private int offsetX = this.x + (this.w - Constant.UI_CONSTANT.SIGN_IMG.getWidth(null))/2;
	private int offsetY = this.y + (this.h - Constant.UI_CONSTANT.SIGN_IMG.getHeight(null))/2;
	public LayerAbout(int x, int y, int w, int h) {
		super(x, y, w, h);
	}

	public void paint(Graphics g){
		
		this.createWindow(g);
		g.drawImage(Constant.UI_CONSTANT.SIGN_IMG,offsetX ,offsetY, null);
	}
	
}
