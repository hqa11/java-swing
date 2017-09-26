package ui;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;

import config.GameCpHolder;
import dto.Player;
import dto.RequestMessage;
import holder.ServiceHolder;

public class RoomEntryButton extends JButton{
private int roomNo;
public RoomEntryButton(String name,int roomNo){
	this.roomNo = roomNo;
	this.setFont(new Font("微软雅黑",Font.ITALIC, 15));
	this.setText(name);
	this.setBounds(250,15,80,35);
	Cursor Cusor = new Cursor(Cursor.HAND_CURSOR);
	this.setCursor(Cusor);
	this.addActionListener(new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			FrameGame fg = (FrameGame) GameCpHolder.get("frameGame");
		   //fg.setEnabled(false);
			System.out.println("进入房间请求........................");
			//向服务端发送进入房间请求，如果OK，则进入房间
			Map<String,Object> map = new HashMap<>();
			map.put("roomNo", RoomEntryButton.this.roomNo);
            RequestMessage message = new RequestMessage(1, 1,(Player)GameCpHolder.get("player"), map);
            try {
				ServiceHolder.execute(message);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
            RoomEntryButton.this.setFont(new Font("微软雅黑",Font.ITALIC,12));
            RoomEntryButton.this.setText("Entring..");
            RoomEntryButton.this.setForeground(Color.RED);
        //    ((JOptionPane)GameCpHolder.get("tip")).showMessageDialog(RoomEntryButton.this.getParent().getParent(), "正在进入房间，请您耐心等待....","温馨提示", JOptionPane.PLAIN_MESSAGE);
		}
	});
}
	
}
