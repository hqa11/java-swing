package main;

import javax.swing.JFrame;
import javax.swing.UIManager;

import dto.ServerConfig;
import holder.ServiceHolder;
import server.ServerUtil;
import ui.ServerFrame;

public class ServerMain {

	public static void main(String[] args) throws Exception {
		//装载调度方法
		ServiceHolder.loadServiceMethods();
		//启动服务器
		JFrame.setDefaultLookAndFeelDecorated(true);
		System.setProperty( "Quaqua.tabLayoutPolicy","wrap");
		try{
			UIManager.setLookAndFeel("ch.randelshofer.quaqua.QuaquaLookAndFeel");
		}catch(Exception e){}
		ServerConfig config = ServerConfig.getConfig();
		if(config.getStartModel() == 0){
			//窗口模式
			new ServerFrame();
		}else if(config.getStartModel() == 1){
			//cmd模式
			ServerUtil.startUpServer(config.getHost(),config.getPort());
		}
	}
	
}
