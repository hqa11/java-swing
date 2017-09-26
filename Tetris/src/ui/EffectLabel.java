package ui;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.ImageObserver;

import javax.swing.JLabel;

import config.Constant;

/**
 * 特效透明label
 * @author Administrator
 *
 */
public class EffectLabel extends JLabel{
	private volatile Image img;
	public void setImg(Image img) {
		this.img = img;
	}
	public Image getImg() {
		return img;
	}
	private volatile  ImageObserver ob;
	public void setOb(ImageObserver ob) {
		this.ob = ob;
	}	public EffectLabel(){
		this.setFocusable(true);
		this.setBounds(430,256,240,150);
	}

	@Override
	protected void paintComponent(Graphics g) {
		if(img == null)return;
		super.paintComponent(g);
		int w = img.getWidth(null);
		int h = img.getHeight(null);
		g.drawImage(img,
				0 ,
				0 ,
				w,
				h,
				0, 0,w,h, ob);
	}
	
	/**
	 * 展示特效
	 * @param img
	 */
	public void showEffect(Image img){
		this.img = img;
	}
	
}
