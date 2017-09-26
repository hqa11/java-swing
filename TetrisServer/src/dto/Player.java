package dto;

import java.io.Serializable;

public class Player implements Serializable{

	public Player(String userToken, String userName,Integer userStatus) {
		super();
		this.userToken = userToken;
		this.userName = userName;
		this.userStatus = userStatus;
	}
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String userToken;
	private String userName;
	//0.未准备 1.准备中  4.已确认完成2.游戏中  3.未进入房间
	private Integer userStatus = 0;
	public Player() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getUserToken() {
		return userToken;
	}
	public void setUserToken(String userToken) {
		this.userToken = userToken;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public Integer getUserStatus() {
		return userStatus;
	}
	public void setUserStatus(Integer userStatus) {
		this.userStatus = userStatus;
	}
	
}
