package ui;

import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JButton;

import config.Constant;
import config.GameCpHolder;
import dto.Player;
import dto.RequestMessage;
import holder.ServiceHolder;

/**
 * 退出按钮
 * @author Administrator
 *
 */
public class ReturnButton extends JButton{
private int roomNo;
//1.准备 状态 2.取消 状态
private int status = 1;
	public ReturnButton(int roomNo){
		this.roomNo = roomNo;
		this.setText("离开房间");
		this.setFont(new Font("微软雅黑",Font.ITALIC, 17));
		this.setBounds(250,270,120,40);
		Cursor Cusor = new Cursor(Cursor.HAND_CURSOR);
		this.setCursor(Cusor);
		this.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ReturnButton.this.setEnabled(false);
				Player player =	(Player) GameCpHolder.get("player");
				Map<String,Object> map = new HashMap<>();
				map.put("roomNo", ReturnButton.this.roomNo);
				RequestMessage message = new RequestMessage(1,6,player, map);
				 try {
					 /**
					  * 退出房间请求
					  */
						ServiceHolder.execute(message);
					
					} catch (Exception e1) {
						e1.printStackTrace();
					}
			}
		});
	}
	
	
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	
}
