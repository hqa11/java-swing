package ui;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import config.Constant;
import config.GameCpHolder;
import dto.GameDto;

public class RoomListFrame extends JFrame{
	FrameGame fg = (FrameGame)GameCpHolder.get("frameGame");
	private RoomPanel rps[] = new RoomPanel[10];
	private JPanel head;
	private RefreshButton rb;
	public RefreshButton getRb() {
		return rb;
	}
	public void setRb(RefreshButton rb) {
		this.rb = rb;
	}
	public void setHead(JPanel head) {
		this.head = head;
	}
	public RoomPanel[] getRps() {
		return rps;
	}
	public RoomListFrame(){
		super("组队模式");
		if(fg.getX() > 100){
			fg.setLocation((int)(fg.getX() * 0.3),(int)fg.getY());
		}
		this.setLocation(fg.getX()+fg.getWidth()+10, (int)fg.getY());
		this.setSize(400,fg.getHeight());
		this.setIconImage(Constant.UI_CONSTANT.COVER_IMG);
		this.setVisible(true);
		this.setResizable(false);
		this.setAlwaysOnTop(true);
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.setBackground(null);
		RoomListPanel rlp = new RoomListPanel();
		this.setContentPane(rlp);
		this.addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				JOptionPane.showMessageDialog(RoomListFrame.this,"请从主窗口关闭游戏！","警告", JOptionPane.DEFAULT_OPTION); 
				super.windowClosing(e);
			}
		});

	}

	public void reSetLocation(){
		fg.reSetLocation();
		if(fg.getX() > 100){
			fg.setLocation((int)(fg.getX() * 0.3),(int)fg.getY());
		}
		this.setLocation(fg.getX()+fg.getWidth()+10, (int)fg.getY());
	}
	
	
	/**
	 *初始化房间 
	 */
	public void appendRooms(){
		for (int i = 0; i < 10 ; i ++) {
			this.getContentPane().add(rps[i]);
		}

		this.revalidate();
	}


}
