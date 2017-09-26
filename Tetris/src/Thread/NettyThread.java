package Thread;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import config.GameCpHolder;
import dto.Game;
import dto.GameDto;
import dto.RequestMessage;
import holder.ServiceHolder;

public class NettyThread extends Thread{
private volatile GameDto gameDto;
	
	private RequestMessage rms = new RequestMessage(2,1,null, null) ;
	
	private Map<String,Object> map = new ConcurrentHashMap<>(); 
	
	private Game game;
	
	public NettyThread(){
		
		this.gameDto = (GameDto) GameCpHolder.get("gameDto");
		this.game = (Game) GameCpHolder.get("game");
		map.put("gameDto", gameDto);
	}
	
	@Override
	public void run() {
		while (true) {
			if(gameDto.getIsEnd() != 2  && gameDto.getResult() == 0){
				if(game.getGameModel()  == 2){
					//如果是组队模式，那么发送游戏对象到服务器
					rms.setRequestData(map);
					try {
						//此处需要  加一把锁，防止在传输过程中对象值被改变
					synchronized (gameDto) {
						ServiceHolder.execute(rms);
					}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		
	}
//这个线程用于传输当前的gameDto，调用频率非常之高
	
	
	
}
