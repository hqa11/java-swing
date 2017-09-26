package ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import config.Constant;
import dao.UserDao;
import dto.GameDto;

/**
 * paint window
 * @author Administrator
 *
 */
public abstract class Layer {

	protected volatile int DB_IMG_HEIGHT = Constant.UI_CONSTANT.DB_IMG.getHeight(null);

	protected volatile int DISK_IMG_HEIGHT = Constant.UI_CONSTANT.DISK_IMG.getHeight(null);

	protected volatile  int POINT_IMG_HEIGHT = Constant.UI_CONSTANT.POINT_IMG.getHeight(null);

	protected volatile  int RMLINE_IMG_HEIGHT = Constant.UI_CONSTANT.RMLINE_IMG.getHeight(null);
	
	protected final int PADDING= 16;

	protected JPanel jp;

	public void setJp(JPanel jp) {
		this.jp = jp;
	}
	public JPanel getJp() {
		return jp;
	}
	/**
	 * 窗口左上角ｘ坐标
	 */
	protected int x;
	/**
	 * 窗口左上角ｙ坐标
	 */
	protected int y;
	/**
	 * 窗口宽度
	 */
	protected int w;
	/**
	 * 窗口高度
	 */
	protected int h;

	protected GameDto gameDto;

	public GameDto getGameDto() {
		return gameDto;
	}
	public void setGameDto(GameDto gameDto) {
		this.gameDto = gameDto;
	}
	protected Layer(int x, int y, int w, int h) {
		super();
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
	}
	/**
	 * 绘制窗口
	 */
	protected void createWindow(Graphics g){

		int size = Constant.UI_CONSTANT.WINDOW_BORDER_SIZE;
		Image img = Constant.WINDOW_IMG;
		int imgW = img.getWidth(null);
		int imgH = img.getHeight(null);

		//左上
		g.drawImage(img,x,y,x + size,y + size,0,0,size,size,null);
		//中上
		g.drawImage(img,x + size,y,x + w - size,y + size,size,0,imgW - size,size,null);
		//右上
		g.drawImage(img,x + w-size,y,x + w,y + size,imgW - size,0,imgW,size,null);
		//左中
		g.drawImage(img,x,y + size,x + size,y + h - size,0,size,size,imgH - size,null);
		//中
		g.drawImage(img,x + size,y + size,x + w - size,y + h - size,size,size,imgW - size,imgH - size,null);
		//右中
		g.drawImage(img,x + w - size,y + size,x + w,y + h - size,imgW - size,size,imgW,imgH - size,null);
		//左下
		g.drawImage(img,x,y + h -size,x + size,y + h,0,imgH - size,size,imgH,null);
		//中下
		g.drawImage(img,x + size,y + h - size,x + w - size,y + h,size,imgH - size,imgW - size,imgH,null);
		//右下
		g.drawImage(img,x + w - size,y + h -size,x + w,y + h,imgW - size,imgH - size,imgW,imgH,null);

	}
	/**
	 * 刷新区域具体内容
	 * @param g
	 */
	protected abstract void paint(Graphics g);

	/**
	 * 绘制图片
	 * @param num+0
	 * @param g
	 */
	protected void drawNum(int x,int y,int num,Graphics g){
		//取出每一位的数字
		int index = 0;
		for(char bit : Integer.toString(num).toCharArray()){
			int n = bit - '0';
			g.drawImage(Constant.UI_CONSTANT.NUM_IMG, this.x + x + index * 26,
					this.y + y, this.x + x + 26 + index * 26, this.y + y + 36,n * 26,0,(n + 1) * 26,36,null);
			index ++;
		}

	}

	/**
	 * 绘制值槽
	 * @param title
	 * @param num
	 * @param value
	 * @param maxValue
	 * @param g
	 */
	protected void drawRect(int expW,int rectOffSetX,int rectOffSetY,String title,String num,double value,double maxValue,Graphics g){
		g.setColor(Color.BLACK);
		g.fillRect(rectOffSetX,rectOffSetY, expW, 32);
		g.setColor(Color.WHITE);
		g.fillRect(rectOffSetX + 1, rectOffSetY + 1, expW - 2, 30);
		g.setColor(Color.BLACK);
		g.fillRect(rectOffSetX + 2, rectOffSetY + 2, expW - 4, 28);
		//显示宽度取色（即获取色条的起始点）
		int showWidth = (int)(value/maxValue * 269);
		g.drawImage(Constant.UI_CONSTANT.ZC_IMG,rectOffSetX + 2,rectOffSetY + 2,rectOffSetX + 2 + showWidth, rectOffSetY + 30,
				showWidth,0,showWidth + 1,28,null);
		g.setColor(Color.WHITE);
		g.setFont(new Font("黑体", Font.BOLD, 20));
		g.drawString(title, rectOffSetX + 4, rectOffSetY + 23);
		g.setColor(new Color(254,67, 101));
		String blank = " ";
		/*	if(num.length() < 6){
		for(int i = 0;i<6-num.length();i++)blank+=blank;
	}*/
		g.drawString(num, rectOffSetX + 100, rectOffSetY + 23);
	}

}
