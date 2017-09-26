package dto;

import java.io.Serializable;

public class MapCell implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private volatile boolean st = false;
	//当前形状占有
	private int recCode;
	public int getRecCode() {
		return recCode;
	}
	public void setRecCode(int recCode) {
		this.recCode = recCode;
	}
	public boolean isSt() {
		return st;
	}
	public void setSt(boolean st) {
		this.st = st;
	}
}
