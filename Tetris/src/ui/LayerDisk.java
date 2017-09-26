package ui;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import config.Constant;
import config.GameCache;

public class LayerDisk extends Layer{

	public LayerDisk(int x, int y, int w, int h) {
		super(x, y, w, h);
	}

	public void paint(Graphics g){
		
		this.createWindow(g);
		
		g.drawImage(Constant.UI_CONSTANT.DISK_IMG, this.x + this.PADDING,
				this.y + this.PADDING,null);
		
		
		//绘制值槽
		 paintRect(g);
	}

	
	/**
	 * 绘制本地记录
	 * @param g
	 * @return
	 */
	private double paintRect(Graphics g) {
		int padding = 8;
		int expW = 270;
		int offsetX = this.x + (this.w - expW)/2;
		int offsetY =15 + PADDING + this.y + DISK_IMG_HEIGHT;
		//将大神的分数给显示上去.
		List<Map<String, Object>> users = GameCache.localUserList;
		if(users == null) users = new ArrayList<Map<String, Object>>(5);
		double maxScore = 0;
		for (int i = 0;i< 5;i ++) {
			Map<String, Object> user = null;
			if(users.size() >= i + 1) user = users.get(i);
			if( user != null){
				int score = Integer.parseInt(user.get("score") + "");
				if(i == 0) maxScore = score;
				String scoreDesc = score + "";
				//计算比例
				if(maxScore == 0){
					score = 0 ;
					maxScore = 100;
				}else{
					if(score > 1000000) {
						score = 1000000 ;
						scoreDesc+="+";
					}
				}
				this.drawRect(expW, offsetX,offsetY + i*(padding+32),user.get("name") + "",scoreDesc + "/Lv" + user.get("level"),score-1, maxScore, g);
		}else{
			this.drawRect(expW, offsetX,offsetY + i*(padding+32),"No Data", "", 0, 100, g);
		}
		
		}
		return maxScore;
	}
}
