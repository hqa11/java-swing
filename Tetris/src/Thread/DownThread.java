package Thread;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import config.GameCpHolder;
import dto.Game;
import dto.GameDto;
import dto.RequestMessage;
import holder.ServiceHolder;
import service.GameService;
import ui.PanelGame;

public class DownThread extends Thread{
	
	private volatile GameDto gameDto;
	
	private GameService gs;
	
	private PanelGame panelGame;
	
	
	public DownThread(){
		
		this.gameDto = (GameDto) GameCpHolder.get("gameDto");
		this.panelGame = (PanelGame) GameCpHolder.get("panelGame");
		this.gs = (GameService) GameCpHolder.get("gameService");
		
	}
	
	@Override
	public void run() {
		while (true) {
			if(gameDto.getIsEnd() == 0){
				gs.keyDown(panelGame,1);
			//	data.put("data","当前积分＝＝＝＝＝＝>:" + gameDto.getNowPoint());
				try {
					Thread.sleep(gameDto.getSleepTime());
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
}