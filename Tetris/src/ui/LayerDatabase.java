package ui;

import java.awt.Graphics;

import config.Constant;

public class LayerDatabase extends Layer{
	
	public LayerDatabase(int x, int y, int w, int h) {
		super(x, y, w, h);
	}

	public void paint(Graphics g){
		
		this.createWindow(g);
		
		g.drawImage(Constant.UI_CONSTANT.DB_IMG, this.x + this.PADDING,
		this.y + this.PADDING,null);
		int padding = 8;
		int expW = 270;
		int offsetX = this.x + (this.w - expW)/2;
		int offsetY =15 + PADDING + this.y + DB_IMG_HEIGHT ;
		//ªÊ÷∆÷µ≤€
		for (int i = 0;i< 5;i ++) {
			this.drawRect(expW, offsetX,offsetY + i*(padding+32),"No Data", "", 0, 100, g);
		}
		
	}
	
}
