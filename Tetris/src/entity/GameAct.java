package entity;

import java.awt.Point;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import config.Constant;
import dto.MapCell;

public class GameAct implements Serializable{
    
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	//方块实体
	private volatile Point[] points;
	
	private volatile Point[] shodow = new Point[4];

	//编号，区分颜色和旋转
	private volatile int code;
	
	
	public Point[] getPoints() {
		return points;
	}

	public final static List<Point[]> TYPE_CONFIG = new ArrayList<>(7);
	
	static {

		TYPE_CONFIG.add(new Point[]{new Point(4,0 - 2),new Point(3,0 - 2),new Point(5,0 - 2),new Point(6,0 - 2)});//----
		TYPE_CONFIG.add(new Point[]{new Point(4,0 - 2),new Point(3,0 - 2),new Point(5,0 - 2),new Point(4,1 - 2)});//T
		TYPE_CONFIG.add(new Point[]{new Point(4,0 - 2),new Point(3,0 - 2),new Point(5,0 - 2),new Point(3,1 - 2)});//|--
		TYPE_CONFIG.add(new Point[]{new Point(4,0 - 2),new Point(5,0 - 2),new Point(3,1 - 2),new Point(4,1 - 2)});//S
		TYPE_CONFIG.add(new Point[]{new Point(4,0 - 2),new Point(5,0 - 2),new Point(4,1 - 2),new Point(5,1 - 2)});//口
		TYPE_CONFIG.add(new Point[]{new Point(4,0 - 2),new Point(3,0 - 2),new Point(5,0 - 2),new Point(5,1 - 2)});//--|
		TYPE_CONFIG.add(new Point[]{new Point(4,0 - 2),new Point(3,0 - 2),new Point(4,1 - 2),new Point(5,1 - 2)});//Z

	}

	public GameAct(){

		init(new Random().nextInt(7));

	}

	/**
	 * 移动方法
	 * @param moveX
	 * @param moveY
	 */
	public void move(int moveX,int moveY){


		for (Point point : points) {

			point.setLocation(point.getX()+moveX, point.getY()+moveY);

		}

	}

	/**
	 * 笛卡尔旋转（顺时针90）
	 * @param map 
	 * @param moveX
	 * @param moveY
	 * A.x=0.y+0.x-B.y
	 * A.y=0.y-0.x+B.x;
	 */
	public boolean round(MapCell[][] map){


		for (int i = 1; i < points.length;i++ ) {

			int newX = points[0].y + points[0].x - points[i].y;
			int newY = points[0].y - points[0].x + points[i].x;

			if(isOverMap(newX, newY,map))return false;
		}

		for (int i = 1; i < points.length;i++) {
			int tmp_x= points[i].x;
			points[i].x= points[0].y + points[0].x - points[i].y;
			points[i].y= points[0].y - points[0].x + tmp_x;
		}
		return true;
	}

	/**
	 * 是否超出地图
	 * @param x
	 * @param y
	 * @param map 
	 * @return
	 */
	private boolean isOverMap(int x,int y, MapCell[][] map){

	//	if(x < 0 || x > 9 || y < 0 || y > 17 || map[y][x].isSt())return true;
        boolean b = false;
		if(y >= 0){
			if(x < 0 || x > 9 || y > 17 || map[y][x].isSt())b = true;
		}else{
			//y<0
			if(x < 0 || x > 9)b =  true;
		}
		
		return b;
		
	
	}

	//刷新方块
	public void init(int code) {
		this.code = code;
		Point[] pt = TYPE_CONFIG.get(code);
		this.points = new Point[pt.length];
		for (int i = 0; i < pt.length; i++) {
			points[i] = new Point(pt[i].x, pt[i].y);
		}

	}

	public int getCode() {
		return code;
	}

	public Point[] getShodow() {
		return shodow;
	}

	public void setShodow(Point[] shodow) {
		this.shodow = shodow;
	}


}
