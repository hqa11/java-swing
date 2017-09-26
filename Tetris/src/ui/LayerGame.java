package ui;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;

import config.Constant;
import dto.GameDto;
import dto.MapCell;

public class LayerGame extends Layer{

	private final int ACT_SIZE = 32;


	public LayerGame(int x, int y, int w, int h) {
		super(x, y, w, h);
	}

	public void paint(Graphics g){

		this.createWindow(g);

		GameDto gameDto = getGameDto();
		Point[] points = gameDto.getGameAct().getPoints();

		//绘制阴影，并且绘制提示条
		getShadow(g, points, gameDto.getGameMap(),gameDto.getGameAct().getShodow());
		//初始化地图和方块 //此处绘制网格
		initMap(g, gameDto, points);

	}


	/**
	 * 初始化地图和方块
	 * @param g
	 * @param gameDto
	 * @param points
	 * @return
	 */
	private MapCell[][] initMap(Graphics g, GameDto gameDto, Point[] points) {
		//获得方块编号
		int code =  gameDto.getGameAct().getCode();

		//截取方块位置
		int sx =  (code+1)*32;
		int ex =  (code+2)*32;
		int sy =  0;
		int ey =  32;

		//初始化方块
		for (Point point : points) {
             if(point.y < 0)continue;
			//初始化方块
			g.drawImage(Constant.UI_CONSTANT.GAME_IMG,
					this.x + point.x * ACT_SIZE +7 ,
					this.y + point.y * ACT_SIZE +7 ,
					this.x + point.x * ACT_SIZE +7 + ACT_SIZE,
					this.y + point.y * ACT_SIZE +7 + ACT_SIZE,
					sx, sy,ex ,ey, null);
		}
		//初始化地图  
		MapCell[][] map = gameDto.getGameMap();
		for (int i = 0; i< map.length;i++) {
			for(int j =0;j < map[i].length;j++){
				if(map[i][j].isSt()){
					//获取色块标号
					int recCode = map[i][j].getRecCode();
					int mapSx =  (recCode+1)*32;
					int mapEx =  (recCode+2)*32;
					g.drawImage(Constant.UI_CONSTANT.GAME_IMG,
							this.x + j * ACT_SIZE +7 ,
							this.y + i * ACT_SIZE +7 ,
							this.x + j * ACT_SIZE +7 + ACT_SIZE,
							this.y + i * ACT_SIZE +7 + ACT_SIZE,
							mapSx, sy,mapEx ,ey, null);

				}
			}
		}
		return map;
	}

	/**
	 * 初始化阴影
	 * @param g
	 * @param points
	 * @param map
	 * @param shadow 
	 */
	private void getShadow(Graphics g, Point[] points, MapCell[][] map, Point[] shadow) {

		//得到一个最大纵向平移量
		int offsetY = 0;
		int tempMin =1000;
		//此处获取方块的左右边界
		int left = 1000;
		int right = 0;
		for (int s = 0; s < points.length; s++) {
			if(points[s].x < left  || points[s].x == left)left = points[s].x;
			if(points[s].x > right  || points[s].x == right)right = points[s].x;
			if(points[s].y < 0)continue;
			for (int i = points[s].y; i< 18 ;i++) {
				boolean b = false;
				if(map[i][points[s].x].isSt()){
					//计算每一列的纵向差
					offsetY = i - points[s].y - 1;
					b= true;
					break;
				}

				if(!b){
					offsetY = 17 - points[s].y ;
				}
			}

			if(tempMin >= offsetY){
				tempMin = offsetY;
			}

		}
        if(Constant.NEED_ACT_BACKGROUND == 1){
		//绘制提示阴影
		g.drawImage(Constant.UI_CONSTANT.RECBG_IMG,
				this.x + left * ACT_SIZE +7 ,
				this.y + 7 ,
				this.x + right * ACT_SIZE + 7 +ACT_SIZE,
				this.y + this.h - 7 ,
				0, 0, 100,100, null);
        }
        if(Constant.NEED_SHADOW != 1)return;
		offsetY = tempMin;
		shadow = new Point[]{
				new Point(points[0].x, points[0].y + offsetY ),
				new Point(points[1].x, points[1].y + offsetY ),
				new Point(points[2].x, points[2].y + offsetY ),
				new Point(points[3].x, points[3].y + offsetY ),
		};

		if(offsetY <= 1)return;
		
		for (int s = 0; s < shadow.length; s++) {
	//	if(shadow[s].y < 0)continue;
		if(inAct(shadow[s], getGameDto().getGameAct().getPoints()))continue;
		g.drawImage(Constant.UI_CONSTANT.SHADOW_IMG,
				this.x + shadow[s].x * ACT_SIZE +7 ,
				this.y + shadow[s].y * ACT_SIZE +7 ,
				this.x + shadow[s].x * ACT_SIZE +7 + ACT_SIZE,
				this.y + shadow[s].y * ACT_SIZE +7 + ACT_SIZE,
				0, 0, 32,32, null);

		}
	}


	/**
	 * 判断是否在act数组中
	 * @param b
	 * @param points
	 * @return
	 */
	private boolean inAct(Point shadow, Point[] actPoints) {
		for (Point point : actPoints) {
			if(point.x == shadow.x && point.y == shadow.y){
				return true;
			}

		}

		return false;
	}

}
