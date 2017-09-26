package ui;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;

import config.Constant;
import config.GameCpHolder;
import dto.Game;
import dto.GameDto;

public class StartButton extends JButton{
private GameDto gd;
	
private PauseButton pb = (PauseButton) GameCpHolder.get("pb");

public void setCanUse(boolean flag){
	if(flag){
	this.setEnabled(true);
	this.setIcon(new ImageIcon(Constant.UI_CONSTANT.START_IMG));
	this.setRolloverIcon(new ImageIcon(Constant.UI_CONSTANT.START_IMG));
	}else{
		this.setEnabled(false);
		this.setIcon(new ImageIcon(Constant.UI_CONSTANT.JSTART_IMG));
		this.setRolloverIcon(new ImageIcon(Constant.UI_CONSTANT.JSTART_IMG));
	}
}

	public StartButton(){
		this.gd = (GameDto) GameCpHolder.get("gameDto");
		this.setIcon(new ImageIcon(Constant.UI_CONSTANT.START_IMG));
		this.setRolloverIcon(new ImageIcon(Constant.UI_CONSTANT.START_IMG));
		/*this.setBorderPainted(false);
		this.setFocusPainted(false);
		this.setContentAreaFilled(false);
		this.setFocusable(true);*/
		this.setOpaque(false);
		Cursor Cusor = new Cursor(Cursor.HAND_CURSOR);
		this.setCursor(Cusor);
		this.setBackground(null);
		this.setBounds(810,63, 126, 75);
		Dimension preferredSize = new Dimension(126,75);//设置尺寸
		this.setPreferredSize(preferredSize );
		this.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(gd.getIsEnd() == 2 || gd.getIsEnd() == 1){
					//开始游戏
					new Thread(new Runnable() {
						public void run() {
							Constant.ACT_MUSIC_PLAYER.playMusic(Constant.UI_CONSTANT.ACT_START);
							gd.reSet();
							try {
								Thread.sleep(300);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
							//开始游戏
							gd.setIsEnd(0);
							StartButton.this.getPb().setCanUse(true);
							Game game = (Game)GameCpHolder.get("game");
							if(game.getGameModel() == 1){
								Constant.BG_MUSIC_PLAYER.loopMusic(Constant.UI_CONSTANT.BG_MUSIC_GAME_ON);
							}else{
								Constant.BG_MUSIC_PLAYER.loopMusic(Constant.UI_CONSTANT.GROUP_BG_MUSIC); 
							}
						}
					}).start();
					StartButton.this.setIcon(new ImageIcon(Constant.UI_CONSTANT.JSTART_IMG));
					StartButton.this.setEnabled(false);
					
				}
			}
		});
		
	}

	public void reset(){
		this.setEnabled(true);
		this.setIcon(new ImageIcon(Constant.UI_CONSTANT.START_IMG));
		
	}
	
	public PauseButton getPb() {
		return pb;
	}
	
}
