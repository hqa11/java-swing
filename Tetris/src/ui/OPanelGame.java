package ui;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import config.Constant;
import config.GameConfig;
import config.GameCpHolder;
import control.PlayerControl;
import dto.Game;
import dto.GameDto;
import dto.MapCell;

public class OPanelGame extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final GameConfig gameConfig= Constant.GAME_CONFIG;
	private List<Layer> lays = new ArrayList<>();
	private volatile GameDto gameDto;
	private Game game = (Game) GameCpHolder.get("game");
	public void setGameDto(GameDto gameDto) {
		this.gameDto = gameDto;
	}
	public GameDto getGameDto() {
		return gameDto;
	}
	private   int PAUSE_IMG_WIDTH = Constant.UI_CONSTANT.PAUSE_IMG.getWidth(null);
	private   int PAUSE_IMG_HEIGHT = Constant.UI_CONSTANT.PAUSE_IMG.getHeight(null);
	public OPanelGame(GameDto gameDto) {

		this.gameDto = gameDto;
		
		this.initLayer();

		for (Layer layer : lays) {

			//设置游戏数据对象
			layer.setJp(this);

		}

	}

	/**
	 * 初始化layer
	 */
	public void initLayer(){

		this.lays = gameConfig.getOlayers();

		for (Layer layer : lays) {

			//设置游戏数据对象
			layer.setGameDto(this.gameDto);

		}

	}
	
	/**
	 * 重新给layer设置对象实体
	 */
	public void resetLayer(){

		this.lays = gameConfig.getOlayers();

		for (Layer layer : lays) {

			//设置游戏数据对象
			layer.setGameDto(this.getGameDto());

		}

	}

	

	/**
	 * 组件绘制方法
	 */
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		//获得焦点(用于监听键盘事件)
		//this.requestFocus();
		Layer layGame = null;
		for (Layer lay : lays) {
			lay.paint(g);
			if(lay instanceof LayerGame){
				layGame = lay;
			}
		}
		if(this.gameDto == null){
			this.getParent().setVisible(false);
			return;
		}
		
		if(this.gameDto.getIsEnd() == 2){
			doBeginGame(g,layGame);
			return;
		}
		
		//暂停了
		if(this.gameDto.getIsEnd() == 3){
			System.out.println("pause-----------------");
			doPauseGame(g,layGame);
			return;
		}
		
		
		if(this.gameDto.getIsEnd() == 1){
			
			if(game.getGameModel() == 2){
				
				if(this.gameDto.getResult() == 1){
					doLoseGame(g,layGame);
					return;
				}
				
				if(this.gameDto.getResult() == 2){
					doWinGame(g,layGame);
					return;
				}

				
			}
			
			doEndGame(g,layGame);
			return;
		}
		
	
		for (Layer lay : lays) {
			lay.paint(g);
		}
	
	}

	/**
	 * 赢得比赛
	 * @param g
	 * @param layGame
	 */
	private void doWinGame(Graphics g, Layer layGame) {
		g.drawImage(Constant.UI_CONSTANT.WIN_IMG,
				130,200,284,300,0,0,154,100,null);
	}
	
	/**
	 * 输了比赛
	 * @param g
	 * @param layGame
	 */
	private void doLoseGame(Graphics g, Layer layGame) {
		//将屏幕上面所有的方块碎裂掉
				MapCell[][] map = gameDto.getGameMap();
				for (int i = 0; i< map.length;i++) {
					for(int j =0;j < map[i].length;j++){
						if(map[i][j].isSt()){
							g.drawImage(Constant.UI_CONSTANT.GAME_IMG,
									layGame.x + j * 32 +7 ,
									layGame.y + i * 32 +7 ,
									layGame.x + j * 32 +7 + 32,
									layGame.y + i * 32 +7 + 32,
									32*8,0,32*9 ,32, null);
						}						
					}
				}
				g.drawImage(Constant.UI_CONSTANT.LOSE_IMG,
						130,200,284,300,0,0,154,100,null);
	}
	/**
	 * 暂停游戏了
	 * @param g
	 * @param layGame
	 */
	private void doPauseGame(Graphics g, Layer layGame) {
		g.drawImage(Constant.UI_CONSTANT.PAUSE_IMG,
				layGame.x + 6,
				layGame.y + 6,
				layGame.x + layGame.w - 7,
				layGame.y + layGame.h - 7,
				0,0,PAUSE_IMG_WIDTH ,PAUSE_IMG_HEIGHT, this);
	}

	/**
	 * 准备开始游戏
	 * @param g
	 * @param layGame 
	 */
	private void doBeginGame(Graphics g, Layer layGame) {
		/*g.drawImage(Constant.UI_CONSTANT.COVER_IMG,
				layGame.x + 6,
				layGame.y + 6,
				layGame.x + layGame.w - 6,
				layGame.y + layGame.h - 7,
				0,0,Constant.UI_CONSTANT.COVER_IMG.getWidth(null) ,Constant.UI_CONSTANT.COVER_IMG.getHeight(null), null);*/
	}

	/**
	 * gameOver了
	 * @param g
	 * @param layGame 
	 */
	private void doEndGame(Graphics g, Layer layGame) {
		//将屏幕上面所有的方块碎裂掉
		MapCell[][] map = gameDto.getGameMap();
		for (int i = 0; i< map.length;i++) {
			for(int j =0;j < map[i].length;j++){
				if(map[i][j].isSt()){
					g.drawImage(Constant.UI_CONSTANT.GAME_IMG,
							layGame.x + j * 32 +7 ,
							layGame.y + i * 32 +7 ,
							layGame.x + j * 32 +7 + 32,
							layGame.y + i * 32 +7 + 32,
							32*8,0,32*9 ,32, null);
				}						
			}
		}
		g.drawImage(Constant.UI_CONSTANT.GAME_OVER_IMG,
				430,260,730,360,0,0,300,100,null);
		g.setColor(Color.green);
		g.drawString("是否继续游戏？(按Y/N)", layGame.x + 70, layGame.y + 360);
	}

	public void setPlayerControl(PlayerControl pc) {

		this.addKeyListener(pc);

	}


}
