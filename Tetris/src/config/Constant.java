package config;

import java.awt.Image;

import javax.swing.ImageIcon;

import control.MusicControl;
import control.MusicEffectControl;
import dao.UserDao;
import util.XmlReader;

public interface Constant {

	/**
	 * ui常量池
	 */
	public static final UiCons UI_CONSTANT = UiCons.newInstance();
	public static GameConfig GAME_CONFIG = XmlReader.analysisConfig();
	//刷新y轴偏移量
	public static final int REFRESHOFFSETY = GAME_CONFIG.getRefreshOffsetY();
	public static final int BORDER = GAME_CONFIG.getBorder();
	public static final int NEED_ACT_BACKGROUND = GAME_CONFIG.getNeedActBackGround();
	public static final int NEED_SHADOW = GAME_CONFIG.getNeedShadow();
	public static final String NETTY_HOST = GAME_CONFIG.getHost();
	public static final int NETTY_PORT = GAME_CONFIG.getPort();
	/**
	 * 主窗口图片
	 */
	public  final Image WINDOW_IMG = new ImageIcon("graphics/window/window"+BORDER+".png").getImage();
	
    public static final  MusicControl BG_MUSIC_PLAYER =MusicControl.getBgInstance();
    public static final  MusicEffectControl ACT_MUSIC_PLAYER =MusicEffectControl.getInstance();
    public static final  MusicEffectControl OTH_MUSIC_PLAYER =MusicEffectControl.getInstance();
    public static final  MusicEffectControl DOWN_MUSIC_PLAYER =MusicEffectControl.getInstance();
    public static final  MusicEffectControl DESTORY_MUSIC_PLAYER =MusicEffectControl.getInstance();
}
