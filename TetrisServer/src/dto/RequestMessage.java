package dto;

import java.io.Serializable;
import java.util.Map;

public class RequestMessage implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public RequestMessage() {
		super();
	}
	//模块号 1.对战房间
	private int module;
	//命令号1.进入房间
	private int cmd;
	
	private Player player;
	
	//请求体
	private Object requestData;

	public RequestMessage(int module, int cmd,Player player, Object requestData) {
		super();
		this.module = module;
		this.cmd = cmd;
		this.setPlayer(player);
		this.requestData = requestData;
	}
	public int getModule() {
		return module;
	}
	public void setModule(int module) {
		this.module = module;
	}
	public int getCmd() {
		return cmd;
	}
	public void setCmd(int cmd) {
		this.cmd = cmd;
	}
	public Object getRequestData() {
		return requestData;
	}
	public void setRequestData(Object requestData) {
		this.requestData = requestData;
	}
	public Player getPlayer() {
		return player;
	}
	public void setPlayer(Player player) {
		this.player = player;
	}
}
