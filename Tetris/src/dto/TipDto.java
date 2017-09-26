package dto;

import java.io.Serializable;

/**
 * ÏûÏ¢dto
 * @author Administrator
 *
 */
public class TipDto  implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private volatile int isActive;
	private volatile Long expireTime;
	public int getIsActive() {
		return isActive;
	}
	public void setIsActive(int isActive) {
		this.isActive = isActive;
	}
	public Long getExpireTime() {
		return expireTime;
	}
	public void setExpireTime(Long expireTime) {
		this.expireTime = expireTime;
	}
	
}
