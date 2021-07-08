package wolf.playingwithfire3.ui;

import java.awt.image.BufferedImage;

import wolf.playingwithfire3.Game;
import wolf.playingwithfire3.states.OpponentAmountState;
import wolf.playingwithfire3.states.StateManager;

public class PlayerAmountButton extends UIImageButton{
	
	private Game game;
	private int playerAmount;
	
	public PlayerAmountButton(float x, float y, int width, int height, BufferedImage[] images, Game game, int playerAmount, StateManager stateManager) {
		super(x, y, width, height, images, game,playerAmount+"      Player", stateManager);
		this.game = game;
		this.playerAmount = playerAmount;
		this.stateManager = stateManager;
	}

	@Override
	public void onClick() {
		game.getMouseManager().setUIManager(null);
		stateManager.setState(new OpponentAmountState(game, playerAmount,stateManager));
	}

}
