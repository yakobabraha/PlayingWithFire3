package wolf.playingwithfire3.ui;

import java.awt.image.BufferedImage;

import wolf.playingwithfire3.Game;
import wolf.playingwithfire3.states.LevelChooseState;
import wolf.playingwithfire3.states.StateManager;

public class OpponentAmountButton extends UIImageButton{

	private Game game;
	private int playerAmount;
	private int opponentAmount;
	private StateManager stateManager;
	
	public OpponentAmountButton(float x, float y, int width, int height, BufferedImage[] images, Game game, int playerAmount, int opponentAmount, StateManager stateManager) {
		super(x, y, width, height, images, game, opponentAmount+" Opponent[s]", stateManager);
		this.game = game;
		this.playerAmount = playerAmount;
		this.opponentAmount = opponentAmount;
		this.stateManager = stateManager;
	}

	@Override
	public void onClick() {
		// TODO Auto-generated method stub
		game.getMouseManager().setUIManager(null);
		stateManager.setState(new LevelChooseState(game, playerAmount, opponentAmount,stateManager));
	}

}
