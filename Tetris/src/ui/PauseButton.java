package ui;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;

import ch.randelshofer.quaqua.QuaquaButtonUI;
import config.Constant;
import config.GameCpHolder;
import dto.GameDto;

public class PauseButton extends JButton{
private GameDto gd;
	public void setCanUse(boolean flag){
		if(flag){
		this.setEnabled(true);
		this.setIcon(new ImageIcon(Constant.UI_CONSTANT.PAUSEICON_IMG));
		this.setRolloverIcon(new ImageIcon(Constant.UI_CONSTANT.PAUSEICON_IMG));
		}else{
			this.setEnabled(false);
			this.setIcon(new ImageIcon(Constant.UI_CONSTANT.JSTART_IMG));
			this.setRolloverIcon(new ImageIcon(Constant.UI_CONSTANT.JSTART_IMG));
		}
	}
	
	public void reset(){
		this.setEnabled(false);
		this.setIcon(new ImageIcon(Constant.UI_CONSTANT.JSTART_IMG));
		this.setRolloverIcon(new ImageIcon(Constant.UI_CONSTANT.JSTART_IMG));
	}
	
	public PauseButton(){
		this.gd = (GameDto) GameCpHolder.get("gameDto");
		this.setIcon(new ImageIcon(Constant.UI_CONSTANT.JSTART_IMG));
		this.setRolloverIcon(new ImageIcon(Constant.UI_CONSTANT.JSTART_IMG));
		/*this.setBorderPainted(false);
		this.setFocusPainted(false);
		this.setContentAreaFilled(false);
		this.setFocusable(true);*/
		this.setBounds(960,63, 126, 75);
		this.setEnabled(false);
		Cursor Cusor = new Cursor(Cursor.HAND_CURSOR);
		this.setCursor(Cusor);
		Dimension preferredSize = new Dimension(126,75);//设置尺寸
		this.setPreferredSize(preferredSize );
		this.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(gd.getIsEnd() == 0){
					//开始游戏
					gd.setIsEnd(3);
					PauseButton.this.setIcon(new ImageIcon(Constant.UI_CONSTANT.JSTART_IMG));
					PauseButton.this.setRolloverIcon(new ImageIcon(Constant.UI_CONSTANT.JSTART_IMG));
				}else if(gd.getIsEnd() == 3){
					gd.setIsEnd(0);
					PauseButton.this.setIcon(new ImageIcon(Constant.UI_CONSTANT.PAUSEICON_IMG));
					PauseButton.this.setRolloverIcon(new ImageIcon(Constant.UI_CONSTANT.PAUSEICON_IMG));
				}
				
			}
		});
		
		
	}
	
}
