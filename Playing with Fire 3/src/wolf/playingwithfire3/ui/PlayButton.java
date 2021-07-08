package wolf.playingwithfire3.ui;

import java.awt.image.BufferedImage;

import wolf.playingwithfire3.Game;
import wolf.playingwithfire3.states.PlayerAmountState;
import wolf.playingwithfire3.states.StateManager;

public class PlayButton extends UIImageButton{

	private Game game;
	private StateManager stateManager;
	
	public PlayButton(float x, float y, int width, int height, BufferedImage[] images, Game game, StateManager stateManager) {
		super(x, y, width, height, images, game, "   Play Local", stateManager);
		this.game = game;
		this.stateManager = stateManager;
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onClick() {
		// TODO Auto-generated method stub
		game.getMouseManager().setUIManager(null);
		stateManager.setState(new PlayerAmountState(game,stateManager));
	}

}
