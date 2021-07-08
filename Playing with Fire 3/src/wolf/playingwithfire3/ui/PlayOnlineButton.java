package wolf.playingwithfire3.ui;

import java.awt.image.BufferedImage;

import wolf.playingwithfire3.Game;
import wolf.playingwithfire3.states.QueueState;
import wolf.playingwithfire3.states.StateManager;

public class PlayOnlineButton extends UIImageButton{

	private StateManager stateManager;
	
	public PlayOnlineButton(float x, float y, int width, int height, BufferedImage[] images, Game game, String text, StateManager stateManager) {
		super(x, y, width, height, images, game, text, stateManager);
		// TODO Auto-generated constructor stub
		this.stateManager = stateManager;
	}

	@Override
	public void onClick() {
		// TODO Auto-generated method stub
		game.getMouseManager().setUIManager(null);
		stateManager.setState(new QueueState(game,stateManager));
		
	}

}
