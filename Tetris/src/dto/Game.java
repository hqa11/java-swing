package dto;

public class Game {

	/**
	 * 游戏模式　１.单人　２.组队
	 */
	private volatile Integer gameModel = 1;

	public Integer getGameModel() {
		return gameModel;
	}

	public void setGameModel(Integer gameModel) {
		this.gameModel = gameModel;
	}
	
}
