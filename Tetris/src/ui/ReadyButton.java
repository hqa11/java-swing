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
 * 准备/取消游戏按钮
 * @author Administrator
 *
 */
public class ReadyButton extends JButton{
private int roomNo;
//1.准备 状态 2.取消 状态
private int status = 1;
	public ReadyButton(int roomNo){
		this.roomNo = roomNo;
		this.setText("准备");
		this.setFont(new Font("微软雅黑",Font.ITALIC, 17));
		this.setBounds(60,270,80,40);
		Cursor Cusor = new Cursor(Cursor.HAND_CURSOR);
		this.setCursor(Cusor);
		this.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("click.....................");
				ReadyButton.this.setEnabled(false);
				Player player =	(Player) GameCpHolder.get("player");
				Map<String,Object> map = new HashMap<>();
				//1.判断状态
				if(ReadyButton.this.status == 1){
					//准备游戏的请求
					map.put("op",1);
				}else{
					//取消准备的请求
					map.put("op",0);
				}
				map.put("roomNo", ReadyButton.this.roomNo);
				RequestMessage message = new RequestMessage(1, 5,player, map);
				 try {
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
