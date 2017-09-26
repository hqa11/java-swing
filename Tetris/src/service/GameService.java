package service;

import java.awt.Point;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import config.Constant;
import config.GameCpHolder;
import dao.UserDao;
import dto.Game;
import dto.GameDto;
import dto.MapCell;
import dto.Player;
import entity.GameAct;
import ui.EffectLabel;
import ui.PanelGame;

public class GameService {

	private volatile GameDto gameDto;
	Game game = (Game) GameCpHolder.get("game");
	Player player = (Player) GameCpHolder.get("player");
	private UserDao dao = new UserDao();
	//特效控制器
	private EffectLabel el = (EffectLabel)GameCpHolder.get("el");

	public GameDto getGameDto() {
		return gameDto;
	}

	public void setGameDto(GameDto gameDto) {
		this.gameDto = gameDto;
	}

	public GameService(){

		this.gameDto = (GameDto) GameCpHolder.get("gameDto");;

		GameAct act = new GameAct();

		gameDto.setGameAct(act);

		//预生成下一个方块
		int nextCode = new Random().nextInt(7);

		this.gameDto.setNext(nextCode);

	}

	public void keyUp() {
		if(this.getGameDto().getIsEnd() != 0)return;
		//四方形的那个玩意儿不需要旋转
		if(this.gameDto.getGameAct().getCode() == 4) return;
		boolean can_round = this.gameDto.getGameAct().round(gameDto.getGameMap());
		if(can_round){
			playRoundMusic();
		}

	}

	public void keyDown(PanelGame pg, int key) {
		if(this.getGameDto().getIsEnd()  != 0)return;
		GameAct gameAct = this.gameDto.getGameAct();
		if(this.canMove(0, 1,gameDto.getGameMap())){
			gameAct.move(0, 1);
			if(key == 0){
				playMoveMusic();
			}
		}else{
			//如果不能再向下移动
			playDownMusic();
			//判断是否gameover
			boolean isOver = false;
			MapCell[][] map = gameDto.getGameMap();
			Point[] points = gameDto.getGameAct().getPoints();
			for (Point point : points) {
				if(point.y == 0){
					isOver=true;
				}
				if(point.y < 0){
					continue;
				}
				map[point.y][point.x].setSt(true);
				//记住当前色块的颜色
				map[point.y][point.x].setRecCode(gameDto.getGameAct().getCode());
			}

			if(isOver){
				doEndGame();
				return;
			}

			//TODO 其他升级以及消行操作
			Map<String, Object> destoryRows = destoryRows(map,pg);
			boolean delOk = (Boolean)destoryRows.get("isOk");
			Integer remove_num = (Integer)destoryRows.get("remove_num");
			if(delOk){
				afterRemoveLine(remove_num);
			}
			//刷新一个新的方块
			generateNext(gameAct);

		}

	}

	/**
	 * 消行之后
	 */
	private void afterRemoveLine(int line_num) {
		int hasPlayXMusic = 0;
		if(line_num != 1){
			//激活提示
			switch (line_num) {
			case 2:
				el.showEffect(Constant.UI_CONSTANT.X2_IMG);
				break;
			case 3:
				el.showEffect(Constant.UI_CONSTANT.X3_IMG);
				break;
			case 4:
				el.showEffect(Constant.UI_CONSTANT.PERFECT_IMG);
				break;
			default:
				break;
			}
			//隐藏特效
			hideEl();
			Constant.OTH_MUSIC_PLAYER.playMusic(Constant.UI_CONSTANT.ACT_XX);
			hasPlayXMusic = 1;
		}
		//播放消行音乐
		playDestoryRowMusic();
		//加分
		this.gameDto.removeLine();
		int nowPoint = this.gameDto.getNowPoint();
		//倍率
		int rat = line_num;
		this.gameDto.addPoints(100 * line_num * rat);
		nowPoint = this.gameDto.getNowPoint();
		//每升一级所需要的分数
		int upPoints = this.gameDto.getUpPoints();
		int beforePoints = this.gameDto.getBeforePoints();
		int cz = nowPoint-beforePoints;
		if( cz > upPoints || cz == upPoints){
			if(hasPlayXMusic == 0)Constant.ACT_MUSIC_PLAYER.playMusic(Constant.UI_CONSTANT.ACT_LEVELUP);
			this.gameDto.levelUp();
			el.showEffect(Constant.UI_CONSTANT.TX_LEVELUP_IMG);
			hideEl();
		}
	}

	/**
	 * 隐藏特效，此处有bug尚待解决
	 */
	private void hideEl() {
		new Thread(new  Runnable() {
			public void run() {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				if(GameService.this.getEl().getImg() != null)GameService.this.getEl().setImg(null);
			}
		}).start();

	}

	/**
	 * 游戏完结鸟~~~
	 */
	private void doEndGame() {
		this.gameDto.setIsEnd(1);
		Map<String, Object> map  = new HashMap<>();
		map.put("score", gameDto.getNowPoint());
		map.put("level", gameDto.getNowlevel());
		map.put("name",player.getUserName());
		//将记录写入数据库
		dao.saveUser(map);
		System.out.println("GAME OVER===============");
		new Thread(new Runnable() {
			public void run() {
				Constant.BG_MUSIC_PLAYER.stopMusic();
				if(game.getGameModel() == 2){
					Constant.ACT_MUSIC_PLAYER.playMusic(Constant.UI_CONSTANT.LOSE_MUSIC);
				}else{
					Constant.ACT_MUSIC_PLAYER.playMusic(Constant.UI_CONSTANT.ACT_LOSE);
				}
			}
		}).start();
	}

	/**
	 * 消行判断
	 * @param map
	 * @param pg 
	 * @return
	 */
	private Map<String,Object> destoryRows(MapCell[][] map, PanelGame pg) {

		//	Graphics g = pg.getGraphics();

		Map<String,Object> ret = new HashMap<>();

		boolean isOk = false;
		Integer remove_num = 0;
		for (int i = map.length - 1; i >= 0; i--) {
			boolean b = true;
			for (int j = 0; j < map[i].length;j++) {
				if(!map[i][j].isSt()){
					b = false;
					break;
				}
			}

			if(b){
				isOk = true;
				remove_num ++;
				//将所有此行上方的行向下挪动一行
				for (int  m = i; m >=0; m--) {
					for (int  s= 0; s < map[m].length; s++){
						if(m == 0){
							map[m][s].setSt(false);
						}else{
							map[m][s].setSt(map[m - 1][s].isSt());
						}
					}
				}
				i++;	
			}
			continue;

		}
		ret.put("isOk", isOk);
		ret.put("remove_num", remove_num);
		return ret;
	}



	/**
	 * 刷新并生成下一个方块
	 * @param gameAct
	 */
	private void generateNext(GameAct gameAct) {
		gameAct.init(gameDto.getNext());

		int nextCode = new Random().nextInt(7);

		this.gameDto.setNext(nextCode);
	}



	public void keyLeft() {
		if(this.getGameDto().getIsEnd()  != 0)return;
		if(this.canMove(-1, 0,gameDto.getGameMap())){
			this.gameDto.getGameAct().move(-1, 0);
			playMoveMusic();
		}
	}

	public void keyRight() {
		if(this.getGameDto().getIsEnd()  != 0)return;
		if(this.canMove(1, 0,gameDto.getGameMap())){
			this.gameDto.getGameAct().move(1, 0);
			playMoveMusic();			
		}

	}
	//边界判定
	private boolean canMove(int moveX,int moveY, MapCell[][] map){

		Point[] nowPoints = gameDto.getGameAct().getPoints();

		for (Point point : nowPoints) {
			int newX = point.x + moveX;
			int newY = point.y + moveY;
			//	if(newX < 0 || newX > 9 || newY < 0 || newY > 17 || map[newY][newX].isSt())return false;
			if(isOverMap(newX, newY, map))return false;
		}

		return true;
	}

	private void playMoveMusic(){

		Constant.ACT_MUSIC_PLAYER.playMusic(Constant.UI_CONSTANT.ACT_MOVE);

	}

	private void playRoundMusic() {


		Constant.ACT_MUSIC_PLAYER.playMusic(Constant.UI_CONSTANT.ACT_ROUND);
	}

	private void playDownMusic() {

		Constant.DOWN_MUSIC_PLAYER.playMusic(Constant.UI_CONSTANT.ACT_DOWN);
	}

	private void playDestoryRowMusic() {

		Constant.DESTORY_MUSIC_PLAYER.playMusic(Constant.UI_CONSTANT.ACT_DESTORY);

	}

	//TODO=======================测试专用方法==================================
	public void testLevelUp() {
		this.gameDto.removeLine();
		int nowPoint = this.gameDto.getNowPoint();
		this.gameDto.addPoints(50);
		nowPoint = this.gameDto.getNowPoint();
		if(nowPoint !=0 && nowPoint%1000 == 0){
			this.gameDto.levelUp();
		}
	}

	public EffectLabel getEl() {
		return el;
	}

	public void setEl(EffectLabel el) {
		this.el = el;
	}

	/**
	 * 玩家最后的选择
	 * @param key
	 */
	public void doEndChoose(String key) {
		if(this.getGameDto().getIsEnd() != 1)return;
		//退出游戏
		if("n".equals(key))System.exit(0);
		if("y".equals(key)){
			this.getGameDto().reSet();
			Constant.BG_MUSIC_PLAYER.loopMusic(Constant.UI_CONSTANT.BG_MUSIC_GAME_ON); 
		}
	}

	/**
	 * 是否超出地图
	 * @param x
	 * @param y
	 * @param map 
	 * @return
	 */
	private boolean isOverMap(int x,int y, MapCell[][] map){

		boolean  b = false;

		if(y >= 0){
			if(x < 0 || x > 9 || y > 17 || map[y][x].isSt())b = true;
		}else{
			//y<0
			if(x < 0 || x > 9)b = true;
		}
		return b;
	}

}

