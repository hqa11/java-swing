package config;

import java.awt.Image;
import java.net.URL;
import java.util.Random;

import javax.swing.ImageIcon;

import util.XmlReader;

public class UiCons {




	private UiCons(){}

	/**
	 * Ö÷´°¿Ú±ß¿ò¿í¶È
	 */
	public final int WINDOW_BORDER_SIZE = 7;

	public  final Image SIGN_IMG = new ImageIcon("graphics/window/sign.png").getImage();
	
	public  final Image PLAYER1_IMG = new ImageIcon("graphics/face/face1.png").getImage();
	
	public  final Image PLAYER2_IMG = new ImageIcon("graphics/face/face2.png").getImage();
	
	public  final Image PLAYER3_IMG = new ImageIcon("graphics/face/face3.jpg").getImage();
	
	public  final Image DB_IMG = new ImageIcon("graphics/string/db.png").getImage();
	
	public  final Image DISK_IMG = new ImageIcon("graphics/string/disk.png").getImage();
	
	public  final Image LEVEL_IMG = new ImageIcon("graphics/string/level.png").getImage();
	
	public  final Image LEVELUP_IMG = new ImageIcon("graphics/string/levelup.png").getImage();
	
	public  final Image X2_IMG = new ImageIcon("graphics/string/x2.png").getImage();
	
	public  final Image X3_IMG = new ImageIcon("graphics/string/x3.png").getImage();
	
	public  final Image PERFECT_IMG = new ImageIcon("graphics/string/perfect.png").getImage();
	
	public  final Image BORDER_IMG = new ImageIcon("graphics/game/border.png").getImage();
	
	public  final Image GROUP_MODEL_IMG = new ImageIcon("graphics/string/groupModel.png").getImage();
	
	public   Image CURRENT_BG_IMG = new ImageIcon("graphics/background/1.gif").getImage();
	
	public   Image ROOM_BG_IMG = new ImageIcon("graphics/background/roombg.png").getImage();
	
	public   Image NOONE_BG_IMG = new ImageIcon("graphics/background/noOne.png").getImage();
	
	public   Image FULL_BG_IMG = new ImageIcon("graphics/background/full.png").getImage();
	
	public   Image ONE_BG_IMG = new ImageIcon("graphics/background/hasOne.png").getImage();
	
	public  final Image SHADOW_IMG = new ImageIcon("graphics/background/shadow.png").getImage();
	
	public  final Image GAME_IMG = new ImageIcon("graphics/game/game.jpg").getImage();
	
	public  final Image WIN_IMG = new ImageIcon("graphics/game/win.png").getImage();
	
	public  final Image LOSE_IMG = new ImageIcon("graphics/game/lose.png").getImage();
	
	public  final Image WG_IMG = new ImageIcon("graphics/game/wg.png").getImage();
	
	public  final Image COVER_IMG = new ImageIcon("graphics/game/cover.jpg").getImage();
	
	public  final Image RECBG_IMG = new ImageIcon("graphics/game/recbg.png").getImage();
	
	public  final Image NUM_IMG = new ImageIcon("graphics/string/num.png").getImage();
	
	public  final Image PAUSEICON_IMG = new ImageIcon("graphics/string/pauseIcon.png").getImage();
	
	public  final Image PAUSE_IMG = new ImageIcon("graphics/game/pause.gif").getImage();
	
	public  final Image ENTER_IMG = new ImageIcon("graphics/game/enter.png").getImage();
	
	public  final Image TX_LEVELUP_IMG = new ImageIcon("graphics/tx/levelup.png").getImage();
	
	public  final Image TX_X3_IMG = new ImageIcon("graphics/tx/x3.png").getImage();
	
	public  final Image TX_VS_IMG = new ImageIcon("graphics/tx/vs.gif").getImage();
	
	public  final Image TX_READY_IMG = new ImageIcon("graphics/tx/ready.png").getImage();
	
	public  final Image TX_X2_IMG = new ImageIcon("graphics/tx/x2.png").getImage();
	
	public  final Image GAME_OVER_IMG = new ImageIcon("graphics/string/over.png").getImage();
	
	public  final Image START_IMG = new ImageIcon("graphics/string/start.gif").getImage();
	
	public  final Image JSTART_IMG = new ImageIcon("graphics/string/s.gif").getImage();
	
	public  final Image ZC_IMG = new ImageIcon("graphics/string/zc.png").getImage();
	
	public  final Image POINT_IMG = new ImageIcon("graphics/string/point.png").getImage();
	
	public  final Image RMLINE_IMG = new ImageIcon("graphics/string/rmline.png").getImage();
	
	public  final Image DES_IMG = new ImageIcon("graphics/act/des.jpg").getImage();
	
	public static  String BG_MUSIC_01_PATH = "music/"+3+".wav";
	
	public  String BG_MUSIC_GAME_OVER= "music/gameover.wav";
	
	public  String BG_MUSIC_GAME_START= "music/3.wav";
	
	public  String BG_MUSIC_GAME_ON= "music/2.wav";
	
	public  final String ACT_MOVE = "music/act/move.wav";
	
	public  final String ACT_LOSE = "music/act/lose.wav";
	
	public  final String ACT_START = "music/act/start.wav";
	
	public  final String ACT_LEVELUP = "music/act/levelup.wav";
	
	public  final String ACT_XX = "music/act/xx.wav";
	
	public  final String ACT_ROUND ="music/act/round.wav";
	
	public  final String ACT_DOWN ="music/act/down.wav";
	
	public  final String GROUP_BG_MUSIC  = "music/group/groupBg.wav";

	public  final String IN_ROOMBG_MUSIC  = "music/group/inRoom.wav";
	
	public  final String LOSE_MUSIC = "music/group/lose.wav";
	
	public  final String WIN_MUSIC  = "music/group/win.wav";
	
	public  final String ACT_DESTORY = "music/act/destory.wav";
	
	public static UiCons newInstance(){
		
		return new UiCons();
	}
	
	public void changeBgImg(){
		
		int nextInt = new Random().nextInt(5) + 1;
		
		CURRENT_BG_IMG = new ImageIcon("graphics/background/"+nextInt+".gif").getImage();
		
	}
	
	
}
