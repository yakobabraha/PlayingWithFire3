package wolf.playingwithfire3.ui;

import java.awt.image.BufferedImage;

import wolf.playingwithfire3.Game;
import wolf.playingwithfire3.states.GameState;
import wolf.playingwithfire3.states.State;

public class OpponentAmountButton extends UIImageButton{

	private Game game;
	private int playerAmount;
	private int opponentAmount;
	
	public OpponentAmountButton(float x, float y, int width, int height, BufferedImage[] images, Game game, int playerAmount, int opponentAmount) {
		super(x, y, width, height, images, game);
		this.game = game;
		this.playerAmount = playerAmount;
		this.opponentAmount = opponentAmount;
	}

	@Override
	public void onClick() {
		// TODO Auto-generated method stub
		game.getMouseManager().setUIManager(null);
		State.setState(new GameState(game, playerAmount, opponentAmount));
	}

}
