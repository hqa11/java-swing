package control;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import config.Constant;
import config.GameCpHolder;
import dto.GameDto;
import service.GameService;
import ui.FrameGame;
import ui.PanelGame;
import ui.PauseButton;

public class PlayerControl extends KeyAdapter{

	private GameControl gc;
	private GameDto gd;
    private PauseButton pb;
	public PlayerControl() {

		this.gc = (GameControl) GameCpHolder.get("gameController");
		this.gd = (GameDto) GameCpHolder.get("gameDto");
		this.pb = (PauseButton) GameCpHolder.get("pb");
	}

	@Override
	public synchronized void keyPressed(KeyEvent e) {
		//此处交给GameControl处理
		switch (e.getKeyCode()) {
		case KeyEvent.VK_UP:
			gc.keyUp();
			break;
		case KeyEvent.VK_DOWN:
			gc.keyDown();
			break;
		case KeyEvent.VK_LEFT:
			gc.keyLeft();
			break;
		case KeyEvent.VK_RIGHT:
			gc.keyRight();
			break;
		case KeyEvent.VK_O:
			gc.changeBackGround();
			break;
		case KeyEvent.VK_P:
			pb.doClick();
			break;
		case KeyEvent.VK_C:
			
			break;
		case KeyEvent.VK_Y:
			gc.doEndChoose("y");
			break;
		case KeyEvent.VK_N:
			gc.doEndChoose("n");
			break;
		case KeyEvent.VK_F:
			//扩大主窗口,开启对战模式
			gc.openMutiModel();
			break;
		case KeyEvent.VK_R:
			//扩大主窗口,开启对战模式
			gc.reSetGame();
			break;
		default:
			break;
		}
			
		gc.getPg().repaint();
	}

	

}
