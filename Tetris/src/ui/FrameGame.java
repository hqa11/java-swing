package ui;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import config.Constant;
import config.GameConfig;
import config.GameCpHolder;
import dto.Game;
import dto.GameDto;
import dto.Player;

/**
 * 游戏主窗口
 * @author Administrator
 *
 */
public class FrameGame extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L; 
	
	
	private final GameConfig gameConfig= Constant.GAME_CONFIG;
	
	private volatile StartButton  sb;
	private volatile PauseButton  pb;
	private volatile PanelGame panelGame;
	
	public FrameGame(){
		sb = (StartButton) GameCpHolder.get("sb");
		panelGame = (PanelGame) GameCpHolder.get("panelGame");
		this.setTitle("Java俄罗斯方块");
		this.setIconImage(Constant.UI_CONSTANT.COVER_IMG);
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.setSize(gameConfig.getWidth(),gameConfig.getHeight());
		this.setResizable(false);
		this.setAlwaysOnTop(true);
		//居中
		Toolkit tk = Toolkit.getDefaultToolkit();
		Dimension screen = tk.getScreenSize();
		int x = (int) ((screen.getWidth() - this.getWidth())/2);
		int y = (int) ((screen.getHeight() - this.getHeight())/2) - 32;
		this.setLocation(x,y);
		this.setContentPane(panelGame);
		this.setLayout(null);
		EffectLabel el = (EffectLabel) GameCpHolder.get("el");
		el .setOb(this);
		this.getContentPane().add(el);
		pb = (PauseButton) GameCpHolder.get("pb");
		this.getContentPane().add(pb);
		this.getContentPane().add(sb);
		this.setVisible(true);
		
		this.addWindowListener(new WindowAdapter() {
			
			@Override
			public void windowClosing(WindowEvent e) {
				GameDto dto = (GameDto) GameCpHolder.get("gameDto");
				Player player = (Player) GameCpHolder.get("player");
				Game game = (Game) GameCpHolder.get("game");
				if(dto.getIsEnd() == 0 || dto.getIsEnd() == 3){
					int result = JOptionPane.showConfirmDialog(FrameGame.this, "系统检测到您正在游戏中，确定要强行退出吗？");
					if(result == JOptionPane.OK_OPTION){
						System.exit(0);
					}
				}else if(game.getGameModel() == 2  && player.getUserStatus() != 3){
					int result = JOptionPane.showConfirmDialog(FrameGame.this, "系统检测到您正在组队房间，确定要强行退出吗？");
					if(result == JOptionPane.OK_OPTION){
						System.exit(0);
					}
				}else{
					System.exit(0);
				}
				super.windowClosing(e);
			}
		});
		
	}

	public void reSetLocation() {
		Toolkit tk = Toolkit.getDefaultToolkit();
		Dimension screen = tk.getScreenSize();
		int x = (int) ((screen.getWidth() - this.getWidth())/2);
		int y = (int) ((screen.getHeight() - this.getHeight())/2) - 32;
		this.setLocation(x,y);
	}

}
