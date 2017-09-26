package Thread;

import service.GameService;
import ui.PanelGame;

public class ShThread extends Thread{
	
	private GameService gs;
	
	public ShThread(GameService gs){
		
		this.gs = gs;
		
	}
	
	@Override
	public void run() {
		while (true) {
			if(gs.getGameDto().getTd().getIsActive() == 1){
				Long now =System.currentTimeMillis();
				Long exp = gs.getGameDto().getTd().getExpireTime();
				if(now -exp > 1000){
					gs.getGameDto().getTd().setIsActive(0);
					gs.getEl().setImg(null);
				}
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
}