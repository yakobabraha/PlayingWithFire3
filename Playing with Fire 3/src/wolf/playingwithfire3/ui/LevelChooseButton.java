package wolf.playingwithfire3.ui;

import java.awt.image.BufferedImage;

import wolf.playingwithfire3.Game;
import wolf.playingwithfire3.states.GameState;
import wolf.playingwithfire3.states.State;

public class LevelChooseButton extends UIImageButton{

	private Game game;
	
	private int playerAmount;
	private int opponentAmount;
	private String levelName;
	
	public LevelChooseButton(float x, float y, int width, int height, BufferedImage[] images, Game game, int playerAmount, int opponentAmount, String levelName) {
		super(x, y, width, height, images, game, "      Level "+levelName.substring(levelName.length() - 1));
		
		this.game = game;
		
		this.playerAmount = playerAmount;
		this.opponentAmount = opponentAmount;
		this.levelName = levelName;
	}

	@Override
	public void onClick() {
		game.getMouseManager().setUIManager(null);
		State.setState(new GameState(game, playerAmount, opponentAmount, levelName));
	}

}
