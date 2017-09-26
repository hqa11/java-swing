package control;

import javax.swing.JOptionPane;

import org.jboss.netty.channel.Channel;

import config.Constant;
import config.GameCpHolder;
import dto.Game;
import dto.GameDto;
import dto.Player;
import dto.RequestMessage;
import holder.ServiceHolder;
import service.GameService;
import ui.FrameGame;
import ui.PanelGame;
import ui.PauseButton;
import ui.RoomListFrame;
import ui.StartButton;

public class GameControl {


	private PanelGame pg;

	private GameService gameService;
	
	private GameDto gd;
	
	Game game = (Game) GameCpHolder.get("game");
	Player player = (Player) GameCpHolder.get("player");
	
	public PanelGame getPg() {
		return pg;
	}
	
	public GameService getGameService() {
		return gameService;
	}

	public GameControl() {

		this.pg = (PanelGame) GameCpHolder.get("panelGame");
		this.gameService = 	(GameService) GameCpHolder.get("gameService");
        this.gd = (GameDto) GameCpHolder.get("gameDto");
	}


	public void keyUp() {

		gameService.keyUp();

	}


	public void keyDown() {

		gameService.keyDown(pg,0);
	}

	public void keyLeft() {
		gameService.keyLeft();

	}

	public void keyRight() {

		gameService.keyRight();
		
	}


	/**
	 * 切换背景图片
	 */
	public void changeBackGround() {
		//窍门在于改变常量池的背景音乐常量
		Constant.UI_CONSTANT.changeBgImg();
	}

	/**
	 * 处理玩家最后的选择
	 * @param string
	 */
	public void doEndChoose(String key) {
		gameService.doEndChoose(key);
		
	}

	/**
	 * 开启对战模式
	 */
	public void openMutiModel() {
		
		FrameGame fg =  (FrameGame) GameCpHolder.get("frameGame");
		//游戏进行中,无法开启
		if(gd.getIsEnd() != 2) {
			JOptionPane.showMessageDialog(fg,"游运行中,无法进行 此操作！","警告", JOptionPane.DEFAULT_OPTION);
			return;
		}
		Channel channel = (Channel)GameCpHolder.get("channel");
		if(game.getGameModel() == 1){
			if(!channel.isConnected()){
				JOptionPane.showMessageDialog(fg,"连接失败，请检查网络是否通畅","警告", JOptionPane.DEFAULT_OPTION);
				//TODO 重连
				return;
			}
			//单人
			RoomListFrame roomListFrame = new RoomListFrame();
			GameCpHolder.put("roomListFrame", roomListFrame);
			RequestMessage reqMsg = new RequestMessage(1, 3,null, null);
			try {
				ServiceHolder.execute(reqMsg,channel);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else{
			//组队,
			if(player.getUserStatus() !=3 ){
				JOptionPane.showMessageDialog(fg,"请先退出房间！","警告", JOptionPane.DEFAULT_OPTION);
			}else{
				int result = JOptionPane.showConfirmDialog(pg, "你确认要重退出对战模式吗？");
				if(result != JOptionPane.OK_OPTION)return;
				//关闭列表,模式调整为１
				RoomListFrame roomListFrame = (RoomListFrame) GameCpHolder.get("roomListFrame");
				if(roomListFrame != null){
					roomListFrame.dispose();
				}
				game.setGameModel(1);
				//重置start按钮
				StartButton sb	= (StartButton)GameCpHolder.get("sb");
				sb.reset();
			}
			
		}
		/*FrameGame fg = (FrameGame)GameCpHolder.get("frameGame");
		fg.setSize(1700,fg.getHeight());*/
		
	}

	/**
	 *重置Game
	 */
	public void reSetGame() {
		if(game.getGameModel() == 1){
			if(gd.getIsEnd() == 0 || gd.getIsEnd() == 1){
				int result = JOptionPane.showConfirmDialog(pg, "你确认要重置游戏吗？");
				if(result == JOptionPane.OK_OPTION){
					gd.superReSet();
					Constant.BG_MUSIC_PLAYER.loopMusic(Constant.UI_CONSTANT.BG_MUSIC_01_PATH);
					StartButton sb = (StartButton) GameCpHolder.get("sb");
					sb.reset();
					PauseButton pb = (PauseButton) GameCpHolder.get("pb");
					pb.reset();
				}
			}
			
		}else{
			if(gd.getIsEnd() == 2)return;
			if(gd.getIsEnd() == 0 && gd.getResult() == 0 && player.getUserStatus() == 2){
				JOptionPane.showMessageDialog(pg,"游戏正在进行中！","警告", JOptionPane.DEFAULT_OPTION); 
			}else{
				//退回到房间
				RequestMessage reqMsg = new RequestMessage(2, 3, player, null);
				try {
					ServiceHolder.execute(reqMsg);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		
	}

	
}

