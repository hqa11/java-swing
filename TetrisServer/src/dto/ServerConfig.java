package dto;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import main.ServerMain;

public final class ServerConfig {

	private static ServerConfig sc;
	private String host;
	private int startModel;
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}
	private int port;
	
	public static ServerConfig getConfig() throws IOException{
		if(sc != null)return sc;
		String dir = System.getProperty("user.dir");
		InputStream is = new FileInputStream(new File(dir + "/conf/server.properties"));
		Properties p = new Properties();
		p.load(is);
		sc = new ServerConfig(p.getProperty("host"),Integer.parseInt(p.getProperty("port")),Integer.parseInt(p.getProperty("startModel")));
		return sc;
	}
	private ServerConfig(String host, int port,int startModel) {
		super();
		this.host = host;
		this.port = port;
		this.startModel = startModel;
	}
	public int getStartModel() {
		return startModel;
	}
	public void setStartModel(int startModel) {
		this.startModel = startModel;
	}
	
}
