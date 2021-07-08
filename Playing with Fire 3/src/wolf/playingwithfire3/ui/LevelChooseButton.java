package wolf.playingwithfire3.ui;

import java.awt.image.BufferedImage;

import wolf.playingwithfire3.Game;
import wolf.playingwithfire3.states.GameState;
import wolf.playingwithfire3.states.StateManager;

public class LevelChooseButton extends UIImageButton{

	private Game game;
	
	private int playerAmount;
	private int opponentAmount;
	private String levelName;
	
	private StateManager stateManager;
	
	public LevelChooseButton(float x, float y, int width, int height, BufferedImage[] images, Game game, int playerAmount, int opponentAmount, String levelName, StateManager stateManager) {
		super(x, y, width, height, images, game, "      Level "+levelName.substring(levelName.length() - 1), stateManager);
		
		this.game = game;
		
		this.playerAmount = playerAmount;
		this.opponentAmount = opponentAmount;
		this.levelName = levelName;
		this.stateManager = stateManager;
	}

	@Override
	public void onClick() {
		game.getMouseManager().setUIManager(null);
		stateManager.setState(new GameState(game, playerAmount, opponentAmount, levelName,stateManager));
	}

}
