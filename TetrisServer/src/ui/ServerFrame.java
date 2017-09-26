package ui;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import dto.ServerConfig;
import holder.FrameHolder;
import server.ServerUtil;

public class ServerFrame extends JFrame{
	private int hasStartUp = 0;
	private JTextArea console;
	JScrollPane jsp;
	public JScrollPane getJsp() {
		return jsp;
	}
	public JTextArea getConsole() {
		return console;
	}
	public void setConsole(JTextArea console) {
		this.console = console;
	}
	public ServerFrame(){
		super("TETRIS SERVER");
		this.setLocation(500,600);
		this.setSize(300,260);
		this.setContentPane(new JPanel());
		this.getContentPane().setLayout(null);
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		final JButton jb = new JButton("启动服务");
		jb.setBounds(40, 40, 120, 50);
		this.getContentPane().add(jb);
		final JLabel label = new JLabel("服务暂未启动！");
		label.setForeground(Color.red);
		label.setBounds(170, 30, 120, 60);
		this.getContentPane().add(label);

		//控制台：
		console = new JTextArea("等待数据..",20,60);
		console.setSelectedTextColor(Color.yellow);
		console.setLineWrap(true);        //激活自动换行功能 
		console.setWrapStyleWord(true);            // 激活断行不断字功能
		
//		JPanel jp  = new JPanel();
		jsp = new JScrollPane(console);
//	    jp.add(jsp);
	    console.setBounds(40, 100, 230, 120);
	    jsp.setBounds(40, 100, 230, 120);
		this.getContentPane().add(jsp);
		this.setVisible(true);
		jb.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				FrameHolder.clearConsole();
				if(hasStartUp == 0){
					//start
					ServerConfig config = null;
					try {
						config = ServerConfig.getConfig();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					ServerUtil.startUpServer(config.getHost(),config.getPort());
					label.setText("服务器启动成功！");
					label.setForeground(Color.black);
					jb.setText("关闭服务");
					FrameHolder.consoleAppend("服务启动!host："+config.getHost()+",port:"+config.getPort()+"...",2);
					hasStartUp = 1;
				}else{
					//end
					ServerConfig config = null;
					try {
						config = ServerConfig.getConfig();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					ServerUtil.stopServer();
					label.setText("服务暂未启动！");
					label.setForeground(Color.red);
					jb.setText("启动服务");
					hasStartUp = 0;
				}

			}
		});
		FrameHolder.setSf(this);
	}

	public int getHasStartUp() {
		return hasStartUp;
	}

	public void setHasStartUp(int hasStartUp) {
		this.hasStartUp = hasStartUp;
	}
}
