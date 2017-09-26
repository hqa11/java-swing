package main;

import holder.ServiceHolder;
import java.util.UUID;
import javax.swing.JFrame;
import javax.swing.UIManager;
import org.jboss.netty.channel.Channel;
import service.GameService;
import socket.ClientFactory;
import ui.EffectLabel;
import ui.FrameGame;
import ui.PanelGame;
import ui.PauseButton;
import ui.StartButton;
import util.DataUtil;
import Thread.DownThread;
import Thread.NettyThread;
import config.GameCpHolder;
import control.GameControl;
import control.PlayerControl;
import dto.Game;
import dto.GameDto;
import dto.Player;

public class Main {

	public static void main(String[] args){
		//加载MAC风格主题
		try {
			System.setProperty( "Quaqua.tabLayoutPolicy","wrap");
			JFrame.setDefaultLookAndFeelDecorated(true);
			UIManager.setLookAndFeel("ch.randelshofer.quaqua.QuaquaLookAndFeel");
			//加载远程业务 
			ServiceHolder.loadServiceMethods();
			//为用户生成一个token和昵称
			Player player = new Player(UUID.randomUUID().toString(), DataUtil.getChineseName(),3);
			GameCpHolder.put("player",player);
			//默认，普通模式
			GameCpHolder.put("game",new Game());
			//开启socketChannel
			Channel channel = ClientFactory.getChannel();
			GameCpHolder.put("channel", channel);

			GameDto gameDto = new GameDto();
			//游戏实体组件
			GameCpHolder.put("gameDto", gameDto);
			PanelGame panelGame=new PanelGame(); 
			//组件容器
			GameCpHolder.put("panelGame", panelGame);
			EffectLabel el = new EffectLabel();
			//特效渲染组件
			GameCpHolder.put("el", el);
			GameService gs =new GameService();
			//业务逻辑执行器
			GameCpHolder.put("gameService",gs);
			GameControl gc = new GameControl();
			PauseButton pb = new PauseButton();
			//暂停组件
			GameCpHolder.put("pb", pb);
			//游戏控制器
			GameCpHolder.put("gameController",gc);
			PlayerControl pc =new PlayerControl();
			panelGame.setPlayerControl(pc);
			StartButton sb = new StartButton();
			//开始组件
			GameCpHolder.put("sb", sb);
			FrameGame frameGame = new FrameGame();
			//将游戏组件装入容器
			GameCpHolder.put("frameGame", frameGame);
			//开启下落线程
			Thread.sleep(1000);
			new DownThread().start();
			new NettyThread().start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}



}
