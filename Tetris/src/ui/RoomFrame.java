package ui;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import config.Constant;
import config.GameCpHolder;

/**
 * 房间
 * @author Hqa
 * @date 2017-7-6 
 * @email 18806278373@sina.cn
 * @to TODO
 */
public class RoomFrame extends JFrame{
	FrameGame fg = (FrameGame)GameCpHolder.get("frameGame");
	private Integer roomNo;
	public RoomFrame(Integer roomNo){
		super("对战房间0" + roomNo);
		RoomDetPanel rlp = new RoomDetPanel(roomNo);
		this.setContentPane(rlp);
		this.setRoomNo(roomNo);
		fg.setLocation((int)(fg.getX() * 0.3),(int)fg.getY());
		//	fg.setExtendedState(JFrame.ICONIFIED);
		this.setLocation(fg.getX()+fg.getWidth()+5, (int)fg.getY());
		this.setSize(400,fg.getHeight()/2);
		this.setIconImage(Constant.UI_CONSTANT.COVER_IMG);
		this.setResizable(false);
		this.setAlwaysOnTop(true);
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.setBackground(null);
		this.setVisible(true);
		this.requestFocus();
		this.addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				JOptionPane.showMessageDialog(RoomFrame.this,"请从主窗口关闭游戏！","警告", JOptionPane.DEFAULT_OPTION); 
				super.windowClosing(e);
		}
		});

	}

	public static void main(String[] args) {
		new RoomFrame(1);
	}

	public Integer getRoomNo() {
		return roomNo;
	}

	public void setRoomNo(Integer roomNo) {
		this.roomNo = roomNo;
	}
}
