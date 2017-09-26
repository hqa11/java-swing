package ui;

import java.awt.Color;
import java.awt.Graphics;

import config.Constant;

public class LayerPoints extends Layer{

	private  final int offsetX = 20;
	
	private  final int offsetY = 20;
	//间隔
	private  final int MARGIN_Y = 15;

	
	public LayerPoints(int x, int y, int w, int h) {
		super(x, y, w, h);
	}

	public void paint(Graphics g){
		
		this.createWindow(g);
		g.drawImage(Constant.UI_CONSTANT.POINT_IMG,this.x + offsetX, this.y + offsetY, null);
		g.drawImage(Constant.UI_CONSTANT.RMLINE_IMG,this.x + offsetX, this.y + offsetY + 
				POINT_IMG_HEIGHT  + MARGIN_Y, null);
		
		//绘制分数和消行
		int nowPoint = this.getGameDto().getNowPoint();
		int nowRemoveLine = this.getGameDto().getNowRemoveLine();
		this.drawNum(offsetX + 100, offsetY,nowPoint , g);
		this.drawNum(offsetX + 100, offsetY + POINT_IMG_HEIGHT + MARGIN_Y ,nowRemoveLine, g);
		//绘制值槽
		//假设消除一行加50分，每1000分升级一次
		int upPerPoints = this.gameDto.getUpPoints();
		int beforePoints = this.gameDto.getBeforePoints();
		int i = (nowPoint - beforePoints)%upPerPoints;
		if(i == 0 && nowPoint != 0)i = upPerPoints;
		int rectOffSetX = this.x + offsetX + 10;
		int rectOffSetY = this.y + offsetY + POINT_IMG_HEIGHT  + RMLINE_IMG_HEIGHT + MARGIN_Y * 2;
		int	expW = this.w - ((offsetX+10) * 2);	
	    this.drawRect(expW,rectOffSetX,rectOffSetY,"下一级",nowPoint + "/" + this.gameDto.getNextPoints(), i, upPerPoints, g);
	}
	

}
